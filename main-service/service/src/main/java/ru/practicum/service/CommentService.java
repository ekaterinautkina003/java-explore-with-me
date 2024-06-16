package ru.practicum.service;

import ru.practicum.model.dto.CommentDto;
import ru.practicum.model.dto.CreateCommentDto;

public interface CommentService {

    CommentDto update(Long userId, Long eventId, Long commentId, CreateCommentDto comment);

    CommentDto create(Long userId, Long eventId, CreateCommentDto comment);

    void delete(Long eventId, Long commentId);
}
