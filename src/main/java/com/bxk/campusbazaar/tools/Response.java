package com.bxk.campusbazaar.tools;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {
    private int code;
    private String message;
    private T data;

    public static <T> Response<T> fail() {
        return new Response<>(-1, "请求失败", null);
    }

    public static <T> Response<T> fail(T data) {
        return new Response<>(-1, "请求失败", data);
    }

    public static <T> Response<T> success() {
        return new Response<>(200, "请求成功", null);
    }

    public static <T> Response<T> success(T data) {
        return new Response<>(200, "请求成功", data);
    }
}