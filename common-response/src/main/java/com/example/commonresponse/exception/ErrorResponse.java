package com.example.commonresponse.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

    private final boolean success = false;
    private FieldError error;

    private ErrorResponse(final ErrorCode code) {
        this.error = FieldError.from(code);
    }

    private ErrorResponse(final ErrorCode code, final FieldError error) {
        this(code);
        this.error = error;
    }

    public static ErrorResponse of(ErrorCode code, BindingResult bindingResult) {
        return new ErrorResponse(code, FieldError.of(bindingResult));
    }


    public static ErrorResponse from(ErrorCode code) {
        return new ErrorResponse(code);
    }


    private long currentTime() {
        return LocalDateTime.now().atZone(ZoneId.of("UTC")).toInstant().toEpochMilli();
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class FieldError {

        private String code;
        private String message;

        private FieldError(ErrorCode code) {
            this.code = code.getCode();
            this.message = code.getMessage();
        }

        public static FieldError of(final BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();

            return fieldErrors.stream()
                .map(error -> new FieldError(
                    error.getCode(),
                    error.getRejectedValue() == null ? "" : error.getRejectedValue().toString()))
                .findFirst()
                .orElseGet(() -> new FieldError(ErrorCode.API_INTERNAL_SERVER_ERROR));
        }

        public static FieldError from(ErrorCode code) {
            return new FieldError(code);
        }
    }


}
