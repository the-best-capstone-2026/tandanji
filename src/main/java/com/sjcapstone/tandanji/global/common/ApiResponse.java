package com.sjcapstone.tandanji.global.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {

    private ApiResponseStatus status;
    private T data;
    private String message;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(ApiResponseStatus.SUCCESS, data, null);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(ApiResponseStatus.ERROR, null, message);
    }
}
