package com.example.demo.pojo.easyexcel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.demo.pojo.converter.GenderConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tangcanming
 * @date 2024/1/16 15:12
 * @describe
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("\"demo_data\"")
public class DemoData implements Serializable {
    @ExcelProperty("字符串标题")
    private String string;
    @ExcelProperty("日期标题")
    private Date date;
    @ExcelProperty("数字标题")
    @TableField(value = "double_data")
//    private Double double_data;
    private Double doubleData;

    /**
     * 忽略这个字段
     */
    @ExcelIgnore
    private String ignore;

    /**
     * 自定义内容转换器
     * */
    @ExcelProperty(value = "性别", converter = GenderConverter.class)
    private Integer gender;
}
