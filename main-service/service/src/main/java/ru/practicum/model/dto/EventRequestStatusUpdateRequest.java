package ru.practicum.model.dto;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.model.EventRequestStatus;

import java.util.List;

@Getter
@Setter
public class EventRequestStatusUpdateRequest {

    private List<Long> requestIds;
    private EventRequestStatus status;
}
