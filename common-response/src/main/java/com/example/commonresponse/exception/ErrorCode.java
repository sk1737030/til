package com.example.commonresponse.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {
    OPEN_API_ALREADY_REGISTERED(200, "A000", "OPEN API Already registered"),
    NOT_FOUND_OPEN_API_KEY(200, "A001", "Not Found Open Api Key"),
    API_BAD_REQUEST(400, "A000", "API BAD REQUEST"),
    API_UNAUTHORIZED(401, "A001", "Invalid Api Request."),
    API_INTERNAL_SERVER_ERROR(500, "I003", "Occur Internal Server Error");

    private final int status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

}