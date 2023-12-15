package com.example.demo.controller;



import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.example.demo.pojo.StudentEntity;
import org.apache.poi.ss.usermodel.Workbook;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;


/**
 * @author tangcanming
 * @date 2023/11/02/15:05
 */
@RequestMapping("/EasyPoi")
@RestController
public class excelController {

    @RequestMapping("/excelOut")
    public void exportEmployees(HttpServletResponse response) {
        ArrayList<StudentEntity> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DateTime dateTime = DateUtil.offsetDay(new Date(), 0);
            DateTime dateTime1 = DateUtil.offsetDay(new Date(), 1);
            list.add(new StudentEntity(String.valueOf(i), "学生姓名" + i, i, dateTime, dateTime1));
        }

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("计算机一班学生", "学生"),
                StudentEntity.class, list);

    }

}
