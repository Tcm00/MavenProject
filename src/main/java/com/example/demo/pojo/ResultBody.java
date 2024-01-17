package com.example.demo.pojo;

import cn.hutool.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

/**
 * @author tangcanming
 * @date 2024/1/14 22:37
 * @describe
 */
@Data
@AllArgsConstructor
public class ResultBody<T> {
    private int code;
    private String msg;
    private T data;
    private long timestamp;

    protected ResultBody (){
        this.timestamp = System.currentTimeMillis();
    }
    private static <T> ResultBody<T> of(int code, String msg, T data) {
        ResultBody<T> result = new ResultBody<>();
        result.code = code;
        result.msg = msg;
        result.data = data;
        result.timestamp=System.currentTimeMillis();
        return result;
    }

    public static <T> ResultBody<T> success() {
        return success("处理成功");
    }

    public static <T> ResultBody<T> success(String msg) {
        return of(HttpStatus.HTTP_OK, msg, null);
    }

    public static <T> ResultBody<T> success(String msg, T data) {
        return of(HttpStatus.HTTP_OK, msg, data);
    }

    public static <T> ResultBody<T> success(T data) {
        return of(HttpStatus.HTTP_OK, "处理成功", data);
    }

    public static <T> ResultBody<T> success(int code, String msg) {
        return of(code, msg, null);
    }


    public static <T> ResultBody<T> error(int code, String msg) {
        return of(code, msg, null);
    }

    public static <T> ResultBody<T> error(String msg) {
        return error(HttpStatus.HTTP_INTERNAL_ERROR, msg);
    }

    public static <T> ResultBody<T> error(String msg, T data) {
        return error(HttpStatus.HTTP_INTERNAL_ERROR, msg, data);
    }

    public static <T> ResultBody<T> error(int code, String msg, T data) {
        return of(code, msg, data);
    }

}
