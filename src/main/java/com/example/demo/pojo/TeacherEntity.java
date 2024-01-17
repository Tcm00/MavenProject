package com.example.demo.pojo;

//import cn.afterturn.easypoi.excel.annotation.Excel;
//import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@ExcelTarget("teacherEntity")
public class TeacherEntity implements java.io.Serializable {
    private String id;
    /**
     * name
     */
//    @Excel(name = "主讲老师_major,代课老师_absent", orderNum = "1", isImportField = "true_major,true_absent",needMerge = true)
    private String name;
}
