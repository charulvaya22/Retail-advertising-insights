package com.dunnhumby.insights.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidTimeRangeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleInvalidTimeRange(
            InvalidTimeRangeException ex) {
        return Map.of(
                "timestamp", Instant.now(),
                "error", "INVALID_TIME_RANGE",
                "message", ex.getMessage()
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleBadRequest(
            IllegalArgumentException ex) {
        return Map.of(
                "timestamp", Instant.now(),
                "error", "BAD_REQUEST",
                "message", ex.getMessage()
        );
    }
}
