package com.example.demo.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.pojo.KingdeeData;
import com.example.demo.pojo.MyParam;
import com.example.demo.pojo.StudentEntity;
import com.example.demo.service.PerformanceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author tangcanming
 * @date 2023/12/29 10:09
 * @describe
 */
@Log4j2
@RestController
@RequestMapping("/bigData")
public class PerformanceController {

    @Autowired
    private PerformanceService performanceService;

    @ResponseBody
    @PostMapping("/saveTest")
    public String saveTest(@RequestBody MyParam<StudentEntity> myParam) {
        String id = "000";
//        StudentEntity byId = performanceService.getById(id);
        QueryWrapper<StudentEntity> wrapper = new QueryWrapper<>();
        wrapper.ne("id", "000");
        boolean remove = performanceService.remove(wrapper);
        ArrayList<StudentEntity> list = new ArrayList<>();

        for (long i = 0; i < myParam.getSize(); i++) {
            StudentEntity studentEntity = new StudentEntity(String.valueOf(i), "name" + i, (int) (i % 2), new Date(), new Date());
            list.add(studentEntity);
        }
        int i = 0;
        String Str = "";
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(10); // 任务队列
        LinkedBlockingQueue<Runnable> linkedBlockingQueue = new LinkedBlockingQueue<>(); // 任务队列
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 10, 5000, TimeUnit.MILLISECONDS, linkedBlockingQueue);
        long startTime = System.currentTimeMillis(); // 或者 System.nanoTime();
        for (int i1 = 0; i1 < myParam.getSize(); i1 += myParam.getLimit()) {
            List<StudentEntity> studentEntities;
            if (i1 + myParam.getLimit() >= list.size()) {
                studentEntities = list.subList(i1, list.size());
            } else {
                studentEntities = list.subList(i1, i1 + myParam.getLimit());
            }
//            boolean b = performanceService.saveBatch(studentEntities, 1000);
            Thread thread = new Thread(() -> {
                performanceService.saveBatch(studentEntities, 1000);
            });
            poolExecutor.execute(thread);
        }
        poolExecutor.shutdown();
//        try {
//            // 等待所有任务执行完成或者超时
//            if (!poolExecutor.awaitTermination(1000, TimeUnit.SECONDS)) {
//                // 如果超时（可选），可以进行一些处理
//                poolExecutor.shutdownNow(); // 可以尝试强制关闭线程池
//            }
//        } catch (InterruptedException e) {
//            // 处理中断异常
//            Thread.currentThread().interrupt();
//        }

//        performanceService.saveBatch(list, 1000);
//        i = performanceService.mySaveBatch(list, 1000);
        long endTime = System.currentTimeMillis(); // 或者 System.nanoTime();
        long duration = endTime - startTime;

        return Str + "消耗时间：" + duration + " ms   \n" + "插入成功：" + i + " 条  \n";
//        StudentEntity studentEntity = performanceService.selectById(id);
//        return studentEntity.toString();
    }

    @PostMapping("/saveJson")
    public String saveJson(@RequestBody MyParam<StudentEntity> myParam) {
        log.info(myParam.getEntityList().size() + " 条==>json接收成功!");
        return myParam.getEntityList().size() + " 条==>json接收成功!";
    }

    @PostMapping("/requestJson")
    public String requestJson(@RequestBody MyParam<StudentEntity> myParam) {
        ArrayList<StudentEntity> list = new ArrayList<>();

        for (long i = 0; i < myParam.getSize(); i++) {
            StudentEntity studentEntity = new StudentEntity(String.valueOf(i), "name" + i, (int) (i % 2) + 1, new Date(), new Date());
            list.add(studentEntity);
        }
//        Gson gson = new Gson();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        String jsonBody = gson.toJson(list);
//        String jsonBody = "[]";
        try {
            ObjectMapper mapper = new ObjectMapper();
            jsonBody = mapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println("开始了");
        long startTime = System.currentTimeMillis(); // 或者 System.nanoTime();
//        String json = "{\"entityList\": [" + stringBuffer.toString() + "],\"size\": 100,\"limit\": 100}";
        String json = "{\"entityList\": " + jsonBody + ",\"size\": 100,\"limit\": 100}";
        long endTime = System.currentTimeMillis(); // 或者 System.nanoTime();
        long duration = endTime - startTime;
        return HttpUtil.post("127.0.0.1:7090/bigData/saveJson", json) + "\n接口消耗时间：" + duration + "ms";
    }


    @PostMapping("/requestKingDee")
    public String requestKingdee(@RequestBody MyParam<KingdeeData> myParam) {
        final String access_token = "679008189806542848_9Sf1MJLve7ja7MWbirfxe5w44iv1nGRQs7AqjCHieOpAMsoiIdDpnnsB7KPJk68MymPxRUDTka3Y87RXnz39NSj3P1InX84t5l3e";
        final String url = "http://localhost:8080/ierp/kapi/v2/cqkd/cqkd_xiaodusys/cqkd_postjson/requestTest";
        List<KingdeeData> list = new ArrayList<>();
        for (int i = 0; i < myParam.getSize(); i++) {
            KingdeeData kingdeeData = new KingdeeData("item_" + i, "A", "name" + i, String.valueOf(i % 2), "2024-01-05", "2024-01-05");
            list.add(kingdeeData);
        }
        Gson gson = new Gson();
        String body = gson.toJson(list);
        String json = "{\"data\": " + body + "}";
        long startTime = System.currentTimeMillis(); // 或者 System.nanoTime();
        HttpResponse response = HttpRequest.post(url)
                .header("access_token", access_token)
                .body(json)
                .execute();
        long endTime = System.currentTimeMillis(); // 或者 System.nanoTime();
        long duration = endTime - startTime;
        int status = response.getStatus();
        System.out.println("Status Code:" + status);
        return "接口耗时：" + duration + " ms\n" + response.body();
    }

    @PostMapping("/saveOrupdate")
    public String saveOrupdate(@RequestBody MyParam<StudentEntity> myParam) {
        StudentEntity studentEntity = myParam.getEntityList().get(0);
        LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 10, 10, TimeUnit.SECONDS, workQueue);
        Thread thread1 = new Thread(() -> {
            String id = save(studentEntity, "线程1");
            if (id != null) {
                studentEntity.setId(id);
            }
        });
        Thread thread2 = new Thread(() -> {
            studentEntity.setName("小明2");
            String id = save(studentEntity, "线程2");
            if (id != null) {
                studentEntity.setId(id);
            }
        });
        Thread thread3 = new Thread(() -> {
            studentEntity.setName("小明3");
            String id = save(studentEntity, "线程3");
            if (id != null) {
                studentEntity.setId(id);
            }
        });
        poolExecutor.execute(thread1);
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        poolExecutor.execute(thread2);
        poolExecutor.execute(thread3);
        poolExecutor.shutdown();
        return studentEntity.toString();
    }
    private final Object saveLock = new Object();

    public String save(StudentEntity studentEntity, String threadName) {
        StudentEntity student = new StudentEntity(studentEntity);
        System.out.println(threadName + "==>" + student);
        synchronized (saveLock) {
            if (studentEntity.getId() != null) {
                performanceService.updateById(student);
            } else {
                performanceService.save(student);
            }
            return student.getId();
        }
    }
}
