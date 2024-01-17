package com.example.demo.controller;



//import cn.afterturn.easypoi.excel.ExcelExportUtil;
//import cn.afterturn.easypoi.excel.entity.ExportParams;
//import cn.afterturn.easypoi.excel.entity.TemplateExportParams;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author tangcanming
 * @date 2023/11/02/15:05
 */
@RequestMapping("/EasyPoi")
@RestController
public class EasyPoiController {
//
//    @RequestMapping("/excelOut")
//    public void exportEmployees(HttpServletResponse response) {
//        ArrayList<StudentEntity> list = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            DateTime dateTime = DateUtil.offsetDay(new Date(), 0);
//            DateTime dateTime1 = DateUtil.offsetDay(new Date(), 1);
//            list.add(new StudentEntity(String.valueOf(i), "学生姓名" + i, i, dateTime, dateTime1));
//        }
//
//        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("计算机一班学生", "学生"),
//                StudentEntity.class, list);
//
//        try {
////            response.flushBuffer();
//            workbook.write(response.getOutputStream());
//            response.setContentType("application/vnd.ms-excel");
////            response.setHeader("Content-Disposition", "attachment; filename=example.xlsx");
//            response.setHeader("Content-Disposition", "attachment; filename=计算机一班学生工作表.xlsx");
//            response.flushBuffer();
//        } catch (IOException e) {
//            throw new RuntimeException("导出异常");
//        }
//
//    }
//
//    @RequestMapping("/excelOutList")
//    public void exportEmployeesList(HttpServletResponse response) {
//
//        ArrayList<CourseEntity> entityArrayList = new ArrayList<>();
//        CourseEntity courseEntity = new CourseEntity();
//        courseEntity.setId("001");
//        courseEntity.setName("课程名称001");
//
//        TeacherEntity teacherEntity = new TeacherEntity();
//        teacherEntity.setId("t0011");
//        teacherEntity.setName("代课老师001");
//
//        courseEntity.setMathTeacher(teacherEntity);
//
//        ArrayList<StudentEntity> list = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            DateTime dateTime = DateUtil.offsetDay(new Date(), 0);
//            DateTime dateTime1 = DateUtil.offsetDay(new Date(), 1);
//            list.add(new StudentEntity(String.valueOf(i), "学生姓名" + i, i, dateTime, dateTime1));
//        }
//        courseEntity.setStudents(list);
//
//        entityArrayList.add(courseEntity);
//
//        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("2412312",  "sheet页001"),
//                CourseEntity.class, entityArrayList);
//
//        try {
////            response.flushBuffer();
//            workbook.write(response.getOutputStream());
//            response.setContentType("application/vnd.ms-excel");
////            response.setHeader("Content-Disposition", "attachment; filename=example.xlsx");
//            response.setHeader("Content-Disposition", "attachment; filename=工作表2412312.xlsx");
//            response.flushBuffer();
//        } catch (IOException e) {
//            throw new RuntimeException("导出异常");
//        }
//
//    }

}
