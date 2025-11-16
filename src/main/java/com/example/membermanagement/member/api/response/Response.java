package com.example.membermanagement.member.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Response<T> {
    private String resultCode;
    private T result;

    public static Response<Void> error(String errorCode) {
        return new Response<>(errorCode, null);
    }

    public static Response<Void> sucess() {
        return new Response<>("success", null);
    }

    public static <T> Response<T> sucess(T result) {
        return new Response<>("success", result);
    }
}
