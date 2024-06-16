package ru.practicum.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.model.dto.*;
import ru.practicum.service.CommentService;
import ru.practicum.service.EventService;
import ru.practicum.service.RequestService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
@Validated
public class PrivateEventController {

    private final EventService eventService;
    private final RequestService requestService;
    private final CommentService commentService;

    @GetMapping("/{userId}/events")
    public List<EventShortDto> find(
            @PathVariable Long userId,
            @RequestParam(required = false, defaultValue = "0") @Min(0) Integer from,
            @RequestParam(required = false, defaultValue = "10") @Min(1) Integer size
    ) {
        return eventService.findEventsByUser(userId, from, size);
    }

    @PostMapping("/{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto create(@PathVariable Long userId, @Valid @RequestBody NewEventDto eventDTO) {
        return eventService.create(userId, eventDTO);
    }

    @GetMapping("/{userId}/events/{eventId}")
    public EventFullDto get(@PathVariable Long userId, @PathVariable Long eventId) {
        return eventService.findUserEventById(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public EventFullDto update(@PathVariable Long userId, @PathVariable Long eventId,
                               @Valid @RequestBody UpdateEventUserRequestDto body) {
        return eventService.updateUserEvent(userId, eventId, body);
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> find(@PathVariable Long userId,
                                              @PathVariable Long eventId) {
        return requestService.findRequestsByUserEvent(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests")
    public EventRequestStatusUpdateResult update(@PathVariable Long userId,
                                                 @PathVariable Long eventId,
                                                 @Valid @RequestBody EventRequestStatusUpdateRequest body
    ) {
        return requestService.updateRequestsByUserEvent(userId, eventId, body);
    }

    @PostMapping("/{userId}/events/{eventId}/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto create(
            @PathVariable("userId") Long userId,
            @PathVariable("eventId") Long eventId,
            @Valid @RequestBody CreateCommentDto comment
    ) {
        return commentService.create(userId, eventId, comment);
    }

    @PatchMapping("/{userId}/events/{eventId}/comment/{commentId}")
    public CommentDto update(
            @PathVariable("userId") Long userId,
            @PathVariable("eventId") Long eventId,
            @PathVariable("commentId") Long commentId,
            @Valid @RequestBody CreateCommentDto comment
    ) {
        return commentService.update(userId, eventId, commentId, comment);
    }
}
