package ru.practicum.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventViewStats {

    private String app;
    private String uri;
    private Long hits;
}
