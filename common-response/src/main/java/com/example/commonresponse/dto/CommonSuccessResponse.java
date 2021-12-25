package com.example.commonresponse.dto;

import lombok.Getter;

@Getter
public class CommonSuccessResponse<T> {

    private final boolean success;
    private final T result;

    public CommonSuccessResponse(T result) {
        this.success = true;
        this.result = result;
    }

}
