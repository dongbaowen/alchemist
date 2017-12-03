package com.alchemist.controller.filter;

import com.alchemist.common.Response;
import com.alchemist.exception.BusinessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Baowen on 2017/12/3.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Response handleException(BusinessException e) {
        return Response.createByError();
    }
}
