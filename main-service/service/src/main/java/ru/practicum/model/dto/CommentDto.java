package ru.practicum.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {

    private Long id;
    private Long eventId;
    private Long authorId;
    private String text;
}