package com.example.commonresponse.exception.openapi;

import com.example.commonresponse.exception.BusinessException;
import com.example.commonresponse.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.OK)
public class ApiException extends BusinessException {

    public ApiException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ApiException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

}
