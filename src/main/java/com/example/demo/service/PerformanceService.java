package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.pojo.StudentEntity;

import java.util.ArrayList;

/**
 * @author tangcanming
 * @date 2023/12/29 10:51
 * @describe
 */
public interface PerformanceService extends IService<StudentEntity> {


    StudentEntity selectById(String id);

    void deleteAll();

    int mySaveBatch(ArrayList<StudentEntity> list, int i) throws InterruptedException;
}
