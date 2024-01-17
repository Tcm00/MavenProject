package com.example.demo.pojo.enumTool;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

/**
 * @author tangcanming
 * @date 2024/1/17 9:55
 * @describe
 */
@Getter
@AllArgsConstructor
public enum GenderEnum {
    /**
     * 未知
     */
    UNKNOWN(0, "未知"),

    /**
     * 男性
     */
    MALE(1, "男性"),

    /**
     * 女性
     */
    FEMALE(2, "女性");

    private final Integer value;

    @JsonFormat
    private final String description;

    public static GenderEnum convert(Integer value) {
//        用于为给定元素创建顺序流
//        values:获取枚举类型的对象数组
        return Stream.of(values())
                .filter(bean -> bean.value.equals(value))
                .findAny()
                .orElse(UNKNOWN);
    }

    public static GenderEnum convert(String description) {
        return Stream.of(values())
                .filter(bean -> bean.description.equals(description))
                .findAny()
                .orElse(UNKNOWN);
    }

}


