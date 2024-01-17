package com.example.demo.controller;

import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.pojo.DemoData;
import com.example.demo.pojo.ResultBody;
import com.example.demo.service.EasyExcelService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author tangcanming
 * @date 2024/1/16 14:25
 * @describe
 */
@Slf4j
@RestController
@RequestMapping("/easyExcel")
public class EasyExcelController {
    public Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private EasyExcelService easyExcelService;

    @RequestMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response, @RequestParam(value = "size") long size) {
        long startTime = System.currentTimeMillis(); // 或者 System.nanoTime();

        QueryWrapper<DemoData> queryWrapper = Wrappers.query();
        Map<String, Object> map = easyExcelService.getMap(queryWrapper);
        List<DemoData> list = easyExcelService.list();
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        logger.error("构件参数消耗时间==>" + duration + " ms");

//        String fileName = TestFileUtil.getPath() + "write" + System.currentTimeMillis() + ".xlsx";
        String format = DateUtil.format(new Date(System.currentTimeMillis()), "yyyyMMdd HH:mm:ss");
        String fileName = "write" + format + ".xlsx";
        // 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        try {
            response.setHeader("Content-disposition", "attachment;filename="
                    + new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
            EasyExcel.write(response.getOutputStream(), DemoData.class)
                    .excelType(ExcelTypeEnum.XLSX)
                    .sheet("人员信息").doWrite(list);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping("/importExcel")
    public ResultBody importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        return import1(file);
    }

    public ResultBody import1(MultipartFile file) throws IOException {
        List<DemoData> demoDataList = new ArrayList<>();
        ExcelReader excelReader = EasyExcel.read(file.getInputStream(), DemoData.class, new AnalysisEventListener<DemoData>() {
            @Override
            public void invoke(DemoData demoData, AnalysisContext analysisContext) {
                log.info("解析数据为：" + demoData.toString());
                demoDataList.add(demoData);
            }
            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                log.info("解析完成。。。");
                //sava数据库
            }
        }).build();
        ReadSheet sheet = EasyExcel.readSheet(0).build();
        excelReader.read(sheet);
        excelReader.finish();
        return ResultBody.success("单个sheet页导入成功");
    }
}
