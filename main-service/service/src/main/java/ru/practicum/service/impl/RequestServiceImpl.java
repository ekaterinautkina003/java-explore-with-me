package ru.practicum.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.exception.AlreadyExistsException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.RequestMapper;
import ru.practicum.model.EventRequestStatus;
import ru.practicum.model.EventStatus;
import ru.practicum.model.dto.EventRequestStatusUpdateRequest;
import ru.practicum.model.dto.EventRequestStatusUpdateResult;
import ru.practicum.model.dto.ParticipationRequestDto;
import ru.practicum.model.entity.Event;
import ru.practicum.model.entity.Request;
import ru.practicum.model.entity.User;
import ru.practicum.repository.EventRepository;
import ru.practicum.repository.RequestRepository;
import ru.practicum.repository.UserRepository;
import ru.practicum.service.RequestService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static ru.practicum.model.ApplicationConstant.*;

@Service
@AllArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final RequestMapper requestMapper;

    @Override
    public ParticipationRequestDto create(Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NAME, userId.toString()));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(EVENT_NAME, eventId.toString()));

        if (requestRepository.existsByRequesterIdAndEventId(userId, eventId)
                || !event.getState().equals(EventStatus.PUBLISHED)
                || (event.getParticipantLimit() > 0
                && event.getConfirmedRequests().equals(event.getParticipantLimit()))
                || event.getInitiator().getId().equals(userId)
        ) {
            throw new AlreadyExistsException();
        }

        EventRequestStatus status = EventRequestStatus.PENDING;
        if (event.getParticipantLimit().equals(0) || !event.isRequestModeration()) {
            status = EventRequestStatus.CONFIRMED;
        }
        Request request = Request.builder()
                .status(status)
                .event(event)
                .requester(user)
                .build();

        Request savedRequest = requestRepository.save(request);

        if (!event.isRequestModeration()) {
            Integer count = event.getConfirmedRequests();
            event.setConfirmedRequests(count + 1);
            eventRepository.saveAndFlush(event);
        }

        return requestMapper.toRequestDto(savedRequest);
    }

    @Override
    public ParticipationRequestDto cancel(Long userId, Long requestId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NAME, userId.toString()));

        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException(REQUEST_NAME, userId.toString()));

        Event event = eventRepository.findById(request.getEvent().getId())
                .orElseThrow(
                        () -> new NotFoundException(EVENT_NAME, request.getEvent().getId().toString()));

        if (request.getStatus().equals(EventRequestStatus.CONFIRMED)) {
            Integer count = event.getConfirmedRequests();
            event.setConfirmedRequests(count - 1);
            eventRepository.saveAndFlush(event);
        }

        request.setStatus(EventRequestStatus.CANCELED);
        Request savedRequest = requestRepository.saveAndFlush(request);

        return requestMapper.toRequestDto(savedRequest);
    }

    @Override
    public List<ParticipationRequestDto> findAllByUserId(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NAME, userId.toString()));

        return requestRepository.findAllByRequesterId(userId).stream()
                .map(requestMapper::toRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ParticipationRequestDto> findRequestsByUserEvent(Long userId, Long eventId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NAME, userId.toString()));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(EVENT_NAME, eventId.toString()));

        if (!event.getInitiator().getId().equals(userId)) {
            throw new AlreadyExistsException();
        }
        return requestRepository.findAllByEventId(eventId).stream()
                .map(requestMapper::toRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventRequestStatusUpdateResult updateRequestsByUserEvent(Long userId, Long eventId,
                                                                    EventRequestStatusUpdateRequest body
    ) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NAME, userId.toString()));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(EVENT_NAME, eventId.toString()));

        List<Request> requests = requestRepository.findAllById(body.getRequestIds());

        if (!event.getInitiator().getId().equals(userId)
                || event.getConfirmedRequests().equals(event.getParticipantLimit())
                || !isValidRequestStatus(requests)
                || !event.isRequestModeration()
        ) {
            throw new AlreadyExistsException();
        }

        List<Request> confirmedRequests = new ArrayList<>();
        List<Request> rejectedRequests = new ArrayList<>();

        AtomicReference<Integer> count = new AtomicReference<>(
                event.getParticipantLimit() - event.getConfirmedRequests());

        if (body.getStatus().equals(EventRequestStatus.REJECTED) || count.get().equals(0)) {
            rejectedRequests.addAll(requests);
            requests.forEach(request -> {
                request.setStatus(EventRequestStatus.REJECTED);
                requestRepository.saveAndFlush(request);
            });
            return requestMapper.toEventRequestStatusUpdateResult(confirmedRequests, rejectedRequests);
        }

        requests.forEach(request -> {
            if (count.get() > 0) {
                request.setStatus(body.getStatus());
                requestRepository.saveAndFlush(request);

                event.setConfirmedRequests(event.getConfirmedRequests() + 1);
                eventRepository.saveAndFlush(event);

                confirmedRequests.add(request);
                count.getAndSet(count.get() - 1);
            } else {
                request.setStatus(EventRequestStatus.REJECTED);
                requestRepository.saveAndFlush(request);
                rejectedRequests.add(request);
            }

        });

        return requestMapper.toEventRequestStatusUpdateResult(confirmedRequests, rejectedRequests);
    }

    private boolean isValidRequestStatus(List<Request> requests) {
        return requests.stream()
                .map(Request::getStatus)
                .anyMatch(it -> it.equals(EventRequestStatus.PENDING));
    }
}
