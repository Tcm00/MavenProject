package com.example.demo.aop;

import com.example.demo.annotation.Antishake;
import com.example.demo.utils.RedisUtil;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author tangcanming
 * @date 2024/1/11 16:18
 * @describe
 */
@Aspect
@Component
@Slf4j
public class AntiShakeAOP {
    @Autowired
    private RedisUtil redisUtil;

    @Around(value = "@annotation(com.example.demo.annotation.Antishake)")
    public Object antiShake(ProceedingJoinPoint pjp) {
        // 获取调用方法的信息和签名信息
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        // 获取方法
        Method method = signature.getMethod();
        // 获取注解中的参数
        Antishake antishake = method.getAnnotation(Antishake.class);
        Object[] args = pjp.getArgs();
        Gson gson = new Gson();
        // 查询redis中是否存在对应关系 setUser
        String key = method.getName() + gson.toJson(args);
        Boolean aBoolean = redisUtil.hasKey(key);
        HttpServletResponse response = getResponse();

        log.error("redis是否有建===> {},{}", key, aBoolean);
        if (!aBoolean) {
            redisUtil.setCacheObject(key, key);
            redisUtil.expire(key, antishake.value(), TimeUnit.MILLISECONDS);
            try {
                return pjp.proceed();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        } else {
            log.error("请勿重复点击");
            // 设置 HTTP 响应提示
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json;charset=UTF-8");
            try {
                response.getWriter().write("请勿重复点击");
                response.getWriter().flush();
                response.getWriter().close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return null;
        }
    }

    private HttpServletResponse getResponse() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            return attributes.getResponse();
        }
        throw new IllegalStateException("Unable to retrieve HttpServletResponse.");
    }
}
