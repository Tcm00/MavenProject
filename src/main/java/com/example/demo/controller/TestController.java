package com.example.demo.controller;


import com.example.demo.pojo.Info;
import org.apache.catalina.User;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TestController {

    @RequestMapping("/test/{str}")
    public String test(@PathVariable("str") String str) {
        return "hello word " + str;
    }

    @RequestMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        String str = "用户名：" + username + "  密码：" + password;
        System.out.println(str);
        System.out.println(new Date());
        return str;
    }

    @RequestMapping("/import")
    public void exportList(@RequestPart("file") MultipartFile file) throws IOException {
        System.out.println("我进来了");
//        List<Info> userList = EasyExcel.read(file.getInputStream())
//                .head(Info.class)
//                .sheet(0)
//                .sheetName("test1")
//                .headRowNumber(2)
//                .autoTrim(true)
//                .doReadSync();
//        for (Info info : userList) {
//            System.out.println(info);
//        }
//        writeListToTxtFile(userList, "file.txt");
//        System.out.println("导入成功!" + userList.size());
    }

    public static void writeListToTxtFile(List<Info> userList, String filePath) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));

            int i = 0;
            for (Info student : userList) {
//                writer.write("序号:" + i + ",");
                writer.write("姓名: " + student.getStudentName() + ",");
                writer.write("学号: " + student.getStudentId() + ",");
                writer.write("身份证号: " + student.getCardId() + ",");
                writer.write("电话: " + student.getPhone() + ",");
                writer.newLine(); // 空行分隔不同学生数据
                i++;
            }
            writer.close();
            System.out.println("List数据已写入文件!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/excel")
    public void exportEmployees(HttpServletResponse response) {
        String path = "C:\\Mypoject\\login_test_java_02\\file.txt";
        try {
            // 模拟从数据库或其他数据源获取员工数据
            List<Info> infoList = readTxtFile(path);

            // 设置响应头
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=employees.xlsx");

            // 创建工作簿
            Workbook workbook = new XSSFWorkbook();
            CreationHelper createHelper = workbook.getCreationHelper();
            Sheet sheet = workbook.createSheet("导出txt表格");

            // 创建标题行
            Row headerRow = sheet.createRow(0);
            String[] headers = {"姓名", "学号", "身份证号", "联系电话"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // 填充数据
            int rowNum = 1;
            for (Info info : infoList) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(info.getStudentName().substring(info.getStudentName().indexOf(":") + 1));
                row.createCell(1).setCellValue(info.getStudentId().substring(info.getStudentId().indexOf(":") + 1));
                row.createCell(2).setCellValue(info.getCardId().substring(info.getCardId().indexOf(":") + 1));
                row.createCell(3).setCellValue(info.getPhone().substring(info.getPhone().indexOf(":") + 1));
            }

            // 将工作簿写入响应输出流
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=employees.xlsx");
            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Info> readTxtFile(String filePath) {
        List<Info> userList = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(","); // 假设数据以逗号分隔
                if (parts.length >= 3) {
                    String userName = parts[0].trim();
                    String studentId = parts[1].trim();
                    String cardId = parts[2].trim();
                    String phone = parts[3].trim();

                    Info userInfo = new Info(1, userName, studentId, cardId, phone);
                    userList.add(userInfo);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userList;
    }

}
