package ru.practicum.exception;

public class AlreadyExistsException extends RuntimeException {

    public AlreadyExistsException() {
        super("Modify Conflict");
    }
}
