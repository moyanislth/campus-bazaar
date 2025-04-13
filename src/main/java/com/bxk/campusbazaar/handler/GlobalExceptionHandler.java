package com.bxk.campusbazaar.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {

//    // 捕捉日期转换失败异常:ConversionFailedException
//    @ExceptionHandler(ConversionFailedException.class)
//    public Response<String> handleConversionFailed(ConversionFailedException ex) {
//        return Response.fail("无效的日期格式: " + ex.getValue());
//    }
}