package com.example.demo.pojo.easyexcel;

/**
 * @author tangcanming
 * @date 2024/1/17 17:34
 * @describe
 */

import groovy.transform.EqualsAndHashCode;
import lombok.Data;

import java.util.Date;

/**
 * 基础数据类.这里的排序和excel里面的排序一致
 *
 * @author Jiaju Zhuang
 **/
@Data
@EqualsAndHashCode
public class ExceptionDemoData {
    /**
     * 用日期去接字符串 肯定报错
     */
    private Date date;
}
