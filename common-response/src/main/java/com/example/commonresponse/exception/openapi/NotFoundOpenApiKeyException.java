package com.example.commonresponse.exception.openapi;


import com.example.commonresponse.exception.ErrorCode;

public class NotFoundOpenApiKeyException extends ApiException {

    public NotFoundOpenApiKeyException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public NotFoundOpenApiKeyException(ErrorCode errorCode) {
        super(errorCode);
    }
}
