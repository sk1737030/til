package com.example.commonresponse.exception.openapi;

import com.example.commonresponse.exception.ErrorCode;

public class AlreadyRegisteredMaxOpenApiKey extends ApiException {

    public AlreadyRegisteredMaxOpenApiKey(ErrorCode errorCode) {
        super(errorCode);
    }

    public AlreadyRegisteredMaxOpenApiKey(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}

