package ru.practicum.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotFoundException extends RuntimeException {

    public NotFoundException(String entity, String id) {
        super(String.format("%s with id=%s was not found", entity, id));
    }
}
