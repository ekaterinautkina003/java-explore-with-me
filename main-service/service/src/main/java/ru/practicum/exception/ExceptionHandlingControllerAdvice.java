package ru.practicum.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
public class ExceptionHandlingControllerAdvice {

    private static final DateTimeFormatter EXCEPTION_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse handleValidationExceptions(NotFoundException ex) {
        return ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.name())
                .reason(ExceptionMessage.OBJECT_NOT_FOUND_REASON.getMessage())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now().format(EXCEPTION_DATETIME_FORMATTER))
                .build();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponse handleValidationExceptions(ConstraintViolationException ex) {
        return ErrorResponse.builder()
                .status(HttpStatus.CONFLICT.name())
                .reason(ExceptionMessage.CONFLICT_REASON.getMessage())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now().format(EXCEPTION_DATETIME_FORMATTER))
                .build();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(AlreadyExistsException.class)
    public ErrorResponse handleValidationExceptions(AlreadyExistsException ex) {
        return ErrorResponse.builder()
                .status(HttpStatus.CONFLICT.name())
                .reason(ExceptionMessage.UPDATE_CONFLICT_REASON.getMessage())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now().format(EXCEPTION_DATETIME_FORMATTER))
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.name())
                .reason(ExceptionMessage.REQUEST_VALIDATION_REASON.getMessage())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now().format(EXCEPTION_DATETIME_FORMATTER))
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ErrorResponse handleTypeMismatchExceptions(RuntimeException ex) {
        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.name())
                .reason(ExceptionMessage.REQUEST_VALIDATION_REASON.getMessage())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now().format(EXCEPTION_DATETIME_FORMATTER))
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ErrorResponse handleValidationExceptions(ValidationException ex) {
        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.name())
                .reason(ExceptionMessage.REQUEST_VALIDATION_REASON.getMessage())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now().format(EXCEPTION_DATETIME_FORMATTER))
                .build();
    }
}
