package com.example.demo.advice;

import com.example.demo.pojo.ResultBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.auth.message.AuthException;

/**
 * @author tangcanming
 * @date 2024/1/14 22:12
 * @describe
 */
@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(AuthException.class)
    public ResultBody authExceptionHandler(AuthException e) {
        log.info("异常为：" + e.getMessage());
        return ResultBody.error(e.getMessage());
    }
}
