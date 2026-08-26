package com.ghoul.weather.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(InvalidClientDataException.class)
    ResponseEntity<ErrorResponse> handleInvalidClientData(InvalidClientDataException ex, HttpServletRequest request) {
        return response(HttpStatus.BAD_REQUEST, "Invalid client address", ex.getMessage(), request);
    }

    @ExceptionHandler({IpResolutionException.class, InvalidExternalResponseException.class, UpstreamServiceException.class})
    ResponseEntity<ErrorResponse> handleUpstreamFailure(RuntimeException ex, HttpServletRequest request) {
        log.warn("Upstream request failed: {}", ex.getMessage());
        return response(HttpStatus.BAD_GATEWAY, "Upstream service failure",
                "The weather provider could not complete the request.", request);
    }

    @ExceptionHandler(UpstreamTimeoutException.class)
    ResponseEntity<ErrorResponse> handleTimeout(UpstreamTimeoutException ex, HttpServletRequest request) {
        log.warn("Upstream request timed out: {}", ex.getMessage());
        return response(HttpStatus.GATEWAY_TIMEOUT, "Upstream timeout",
                "The weather provider did not respond in time.", request);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ErrorResponse> handleUnexpected(Exception ex, HttpServletRequest request) {
        log.error("Unhandled request failure", ex);
        return response(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error",
                "An unexpected error occurred.", request);
    }

    private ResponseEntity<ErrorResponse> response(HttpStatus status, String error, String message,
                                                    HttpServletRequest request) {
        return ResponseEntity.status(status).body(new ErrorResponse(
                Instant.now().toString(), status.value(), error, message, request.getRequestURI()));
    }
}
