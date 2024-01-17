package com.example.demo.pojo;


//import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyInfo implements Serializable {

    private int id;

//    @Excel(name = "学生姓名",height = 20,width = 30,isImportField = "true_st")
    private String name;

    private String student_id;

    private String id_number;

    private String phone_number;
}
