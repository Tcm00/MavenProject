package com.example.demo.service.Impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.mapper.PerformanceMapper;
import com.example.demo.pojo.StudentEntity;
import com.example.demo.service.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author tangcanming
 * @date 2023/12/29 11:15
 * @describe
 */
@Service
public class PerformanceServiceImpl extends ServiceImpl<PerformanceMapper, StudentEntity> implements PerformanceService {

    @Autowired
    private PerformanceMapper performanceMapper;

    @Override
    public StudentEntity selectById(String id) {
        return performanceMapper.selectById(id);
    }

    @Override
    public void deleteAll() {
        performanceMapper.deleteAll();
    }

    @Override
    public int mySaveBatch(ArrayList<StudentEntity> list, int batchSize){
        int res = 0;
        for (int i = 0; i < list.size(); i += batchSize) {
            List<StudentEntity> studentEntities;
            if (i + batchSize >= list.size()) {
                studentEntities = list.subList(i, list.size());
            } else {
                studentEntities = list.subList(i, i + batchSize);
            }
            int i2 = performanceMapper.mySaveBatch(studentEntities);
            res += i2;
//            Thread thread = new Thread(() -> {
//                performanceMapper.mySaveBatch(list);
//            });
//            thread.start();
        }
        return res;
    }
}