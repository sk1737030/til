package com.example.commonresponse.exception.openapi;


import com.example.commonresponse.exception.ErrorCode;

public class AlreadyOpenApiKeyException extends ApiException {

    public AlreadyOpenApiKeyException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AlreadyOpenApiKeyException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
