package ru.practicum.controller;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.model.dto.EventFullDto;
import ru.practicum.model.dto.UpdateEventAdminRequestDto;
import ru.practicum.service.CommentService;
import ru.practicum.service.EventService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/admin/events")
@Validated
public class AdminEventController {

    private final EventService eventService;
    private final CommentService commentService;

    @GetMapping
    List<EventFullDto> findEvents(@RequestParam(required = false) List<Long> users, @RequestParam(required = false) List<String> states, @RequestParam(required = false) List<Long> categories, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd, @RequestParam(required = false, defaultValue = "0") @Min(0) Integer from, @RequestParam(required = false, defaultValue = "10") @Min(1) Integer size) {
        return eventService.find(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("/{eventId}")
    EventFullDto updateEvent(@PathVariable Long eventId, @Valid @RequestBody UpdateEventAdminRequestDto body) {
        return eventService.update(eventId, body);
    }

    @DeleteMapping("/{eventId}/comment/{commentId}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long eventId, @PathVariable Long commentId) {
        commentService.delete(eventId, commentId);
    }
}
