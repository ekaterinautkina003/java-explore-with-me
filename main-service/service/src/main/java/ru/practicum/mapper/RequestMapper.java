package ru.practicum.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.model.dto.EventRequestStatusUpdateResult;
import ru.practicum.model.dto.ParticipationRequestDto;
import ru.practicum.model.entity.Request;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RequestMapper {

    public ParticipationRequestDto toRequestDto(Request request) {
        return ParticipationRequestDto.builder()
                .created(request.getCreated())
                .event(request.getEvent().getId())
                .id(request.getId())
                .requester(request.getRequester().getId())
                .status(request.getStatus().name())
                .build();
    }

    public EventRequestStatusUpdateResult toEventRequestStatusUpdateResult(
            List<Request> confirmedRequests, List<Request> rejectedRequests) {
        return EventRequestStatusUpdateResult.builder()
                .confirmedRequests(confirmedRequests.stream()
                        .map(this::toRequestDto)
                        .collect(Collectors.toList()))
                .rejectedRequests(rejectedRequests.stream()
                        .map(this::toRequestDto)
                        .collect(Collectors.toList()))
                .build();
    }
}
