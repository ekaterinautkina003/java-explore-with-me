package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.practicum.exception.AlreadyExistsException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.model.EventStatus;
import ru.practicum.model.dto.CommentDto;
import ru.practicum.model.dto.CreateCommentDto;
import ru.practicum.model.entity.Comment;
import ru.practicum.model.entity.Event;
import ru.practicum.model.entity.User;
import ru.practicum.repository.CommentRepository;
import ru.practicum.repository.EventRepository;
import ru.practicum.repository.UserRepository;
import ru.practicum.service.CommentService;

import static ru.practicum.model.ApplicationConstant.*;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CommentRepository commentRepository;

    @Override
    public CommentDto update(Long userId, Long eventId, Long commentId, CreateCommentDto createCommentDto) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NAME, userId.toString()));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(EVENT_NAME, eventId.toString()));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(COMMENT_NAME, eventId.toString()));
        if (!event.getState().equals(EventStatus.PUBLISHED)
                || !comment.getAuthor().getId().equals(userId)
        ) {
            throw new AlreadyExistsException();
        }
        comment.setText(createCommentDto.getText());
        return modelMapper.map(commentRepository.saveAndFlush(comment), CommentDto.class);
    }

    @Override
    public CommentDto create(Long userId, Long eventId, CreateCommentDto createCommentDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NAME, userId.toString()));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(EVENT_NAME, eventId.toString()));
        if (!event.getState().equals(EventStatus.PUBLISHED)) {
            throw new AlreadyExistsException();
        }
        Comment comment = Comment.builder()
                .author(user)
                .event(event)
                .text(createCommentDto.getText())
                .build();
        return modelMapper.map(commentRepository.save(comment), CommentDto.class);
    }

    @Override
    public void delete(Long eventId, Long commentId) {
        eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(EVENT_NAME, eventId.toString()));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(COMMENT_NAME, commentId.toString()));
        commentRepository.deleteById(comment.getId());
    }
}
