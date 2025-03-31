package com.bxk.campusbazaar.tools;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一响应格式
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Responce {
    Integer code;
    String message;

    public static Responce success(){
        return new Responce();
    }

    public static Responce success(int code, String message){
        return new Responce(code, message);
    }

    public static Responce fail(){
        return new Responce();
    }

    public static Responce fail(int code, String message){
        return new Responce(code, message);
    }
}
