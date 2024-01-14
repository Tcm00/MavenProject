package com.example.demo.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ExcelTarget("studentEntity")
@TableName("\"StudentEntity\"")
public class StudentEntity implements Serializable {
    /**
     * id
     */
    private String id;
    /**
     * 学生姓名
     */
    @Excel(name = "学生姓名", height = 20, width = 30, isImportField = "true_st")
    private String name;
    /**
     * 学生性别
     */
    @Excel(name = "学生性别", replace = {"男_1", "女_2"}, suffix = "生", isImportField = "true_st")
    private int sex;
    @DateTimeFormat(pattern = "yyyy-MM-dd") // 设置日期格式
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Excel(name = "出生日期", databaseFormat = "yyyyMMddHHmmss", format = "yyyy-MM-dd", isImportField = "true_st", width = 20)
    private Date birthday;
    @DateTimeFormat(pattern = "yyyy-MM-dd") // 设置日期格式
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Excel(name = "进校日期", databaseFormat = "yyyyMMddHHmmss", format = "yyyy-MM-dd")
    private Date registration_date;

    public StudentEntity(StudentEntity other) {
        this.id = other.id;
        this.name = other.name;
        this.sex = other.sex;
        this.birthday = (other.birthday != null) ? new Date(other.birthday.getTime()) : null;
        this.registration_date = (other.registration_date != null) ? new Date(other.registration_date.getTime()) : null;
        // 如果属性是可变对象，确保进行深拷贝，以避免共享引用问题
    }
}
