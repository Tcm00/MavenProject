package com.example.demo.controller;

import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.listener.DemoDataListener;
import com.example.demo.listener.DemoHeadDataListener;
import com.example.demo.pojo.easyexcel.DemoData;
import com.example.demo.pojo.ResultBody;
import com.example.demo.service.EasyExcelService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
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

    /**
     * 单个sheet导出
     *
     * @param response
     * @param size
     */
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
        String format = DateUtil.format(new Date(System.currentTimeMillis()), "yyyyMMddHHmmss");
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

    /**
     * 多个sheet页导出
     *
     * @param response
     * @throws IOException
     */
    @GetMapping("/manySheet")
    public void exportManySheet(HttpServletResponse response) throws IOException {
        OutputStream outputStream = response.getOutputStream();
        ExcelWriter writer = EasyExcel.write(outputStream, DemoData.class).excelType(ExcelTypeEnum.XLSX).build();
        try {
            String format = DateUtil.format(new Date(System.currentTimeMillis()), "yyyyMMddHHmmss");
            String fileName = "many" + format + ".xlsx";
            this.setExcelResponseProp(response, fileName);
            // 模拟根据条件在数据库分页查询数据
            for (int j = 1; j <= 5; j++) {
                List<DemoData> list = easyExcelService.list();
                //创建新的sheet页
                WriteSheet writeSheet = EasyExcel.writerSheet("用户信息" + j).build();
                //将list集合中的对象写到对应的sheet中去
                writer.write(list, writeSheet);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            writer.finish();
            outputStream.flush();
            outputStream.close();
        }
    }

    private void setExcelResponseProp(HttpServletResponse response, String fileName) {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename="
                + new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
    }

    @RequestMapping("/importExcel")
    public ResultBody importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        complexHeaderRead(file);
        return ResultBody.success();
    }

    public ResultBody import0(MultipartFile file) throws IOException {
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

    // 写法1：JDK8+ ,不用额外写一个DemoDataListener
    public ResultBody import1(MultipartFile file) throws IOException {
        List<DemoData> demoDataList = new ArrayList<>();
        EasyExcel.read(file.getInputStream(), DemoData.class, new PageReadListener<DemoData>(dataList -> {
            for (DemoData demoData : dataList) {
                Gson gson = new Gson();
                log.info("读取到一条数据{}", gson.toJson(demoData));
                demoDataList.add(demoData);
            }
        })).sheet().doRead();
        return ResultBody.success("成功");
    }

    // 写法2：
    // 匿名内部类 不用额外写一个DemoDataListener
    public ResultBody import2(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), DemoData.class, new ReadListener<DemoData>() {

            /**
             * 单次缓存的数据量
             */
            public static final int BATCH_COUNT = 100;

            /**
             *临时存储
             */
            private List<DemoData> cachedDataList = new ArrayList<>(BATCH_COUNT);

            @Override
            public void invoke(DemoData demoData, AnalysisContext analysisContext) {
                cachedDataList.add(demoData);
                if (cachedDataList.size() >= BATCH_COUNT) {
                    saveData();
                    // 存储完成清理 list
                    cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                saveData();
            }

            private void saveData() {
                log.info("{}条数据，开始存储数据库！", cachedDataList.size());
                easyExcelService.saveBatch(cachedDataList);
                log.info("存储数据库成功！");
            }
        }).sheet().doRead();
        return ResultBody.success("成功");
    }

    // 写法3：
    // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
    public ResultBody import3(MultipartFile file) throws IOException {// 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
        EasyExcel.read(file.getInputStream(), DemoData.class, new DemoDataListener(easyExcelService)).sheet().doRead();
        return ResultBody.success("成功");
    }
    /**
     * 读多个或者全部sheet,这里注意一个sheet不能读取多次，多次读取需要重新读取文件
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>
     * 2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link DemoDataListener}
     * <p>
     * 3. 直接读即可
     */
    public void repeatedRead(MultipartFile file) throws IOException {
        // 读取全部sheet
        // 这里需要注意 DemoDataListener的doAfterAllAnalysed 会在每个sheet读取完毕后调用一次。然后所有sheet都会往同一个DemoDataListener里面写
        EasyExcel.read(file.getInputStream(), DemoData.class, new DemoDataListener()).doReadAll();
        // 写法1
        try (ExcelReader excelReader = EasyExcel.read(file.getInputStream()).build()) {
            // 这里为了简单 所以注册了 同样的head 和Listener 自己使用功能必须不同的Listener
            ReadSheet readSheet1 =
                    EasyExcel.readSheet(0).head(DemoData.class).registerReadListener(new DemoDataListener()).build();
            ReadSheet readSheet2 =
                    EasyExcel.readSheet(1).head(DemoData.class).registerReadListener(new DemoDataListener()).build();
            // 这里注意 一定要把sheet1 sheet2 一起传进去，不然有个问题就是03版的excel 会读取多次，浪费性能
            excelReader.read(readSheet1, readSheet2);
        }
    }

    /**
     * 多行头
     *
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>
     * 2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link DemoDataListener}
     * <p>
     * 3. 设置headRowNumber参数，然后读。 这里要注意headRowNumber如果不指定， 会根据你传入的class的{@link ExcelProperty#value()}里面的表头的数量来决定行数，
     * 如果不传入class则默认为1.当然你指定了headRowNumber不管是否传入class都是以你传入的为准。
     */
    public void complexHeaderRead(MultipartFile file) throws IOException {
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet
        EasyExcel.read(file.getInputStream(), DemoData.class, new DemoDataListener()).sheet(2)
                // 这里可以设置1，因为头就是一行。如果多行头，可以设置其他值。不传入也可以，因为默认会根据DemoData 来解析，他没有指定头，也就是默认1行
                .headRowNumber(3).doRead();
    }

    /**
     * 读取表头数据
     *
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>
     * 2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link DemoHeadDataListener}
     * <p>
     * 3. 直接读即可
     */
    public void headerRead(MultipartFile file)  {
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet
        try {
            EasyExcel.read(file.getInputStream(), DemoData.class, new DemoHeadDataListener()).sheet(2).doRead();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
