package ru.practicum.service;

import ru.practicum.model.dto.EventRequestStatusUpdateRequest;
import ru.practicum.model.dto.EventRequestStatusUpdateResult;
import ru.practicum.model.dto.ParticipationRequestDto;

import java.util.List;

public interface RequestService {

    ParticipationRequestDto create(Long userId, Long eventId);

    ParticipationRequestDto cancel(Long userId, Long requestId);

    List<ParticipationRequestDto> findAllByUserId(Long userId);

    List<ParticipationRequestDto> findRequestsByUserEvent(Long userId, Long eventId);

    EventRequestStatusUpdateResult updateRequestsByUserEvent(Long userId, Long eventId,
                                                             EventRequestStatusUpdateRequest body);

}
