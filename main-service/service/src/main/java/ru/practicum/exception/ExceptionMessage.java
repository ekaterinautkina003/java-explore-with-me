package ru.practicum.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionMessage {
    OBJECT_NOT_FOUND_REASON("The required object was not found."),
    UPDATE_CONFLICT_REASON("For the requested operation the conditions are not met."),
    REQUEST_VALIDATION_REASON("Incorrectly made request."),
    CONFLICT_REASON("Integrity constraint has been violated.");

    private String message;
}
