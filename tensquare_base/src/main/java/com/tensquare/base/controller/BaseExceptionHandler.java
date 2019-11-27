package com.tensquare.base.controller;

import entity.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * 公共异常处理类
 */
@ControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        if (e instanceof MethodArgumentNotValidException){
            //javabean校验异常
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
            List<ObjectError> errors=exception.getBindingResult().getAllErrors();
            return Result.error(errors.get(0).getDefaultMessage());
        }else if(e instanceof ConstraintViolationException){
            //方法参数校验异常
            String message=e.getMessage();
            //"findAll.mobile: 手机号格式不正确"
            return Result.error(message.split(":")[1]);
        }
        return Result.error(e.getMessage());
    }
}
