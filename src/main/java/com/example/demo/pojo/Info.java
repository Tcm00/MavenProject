package com.example.demo.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author tangcanming
 * @date 2023/11/02/13:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Info implements Serializable {
    @ExcelProperty(index = 0)
    private int id;
    @ExcelProperty(value = "学生姓名")
    private String studentName;
    @ExcelProperty(value = "学号")
    private String studentId;
    @ExcelProperty(value = "身份证号")
    private String cardId;
    @ExcelProperty(value = "手机号")
    private String phone;
}
