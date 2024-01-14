package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.pojo.StudentEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tangcanming
 * @date 2023/12/29 10:50
 * @describe
 */
@Repository
public interface PerformanceMapper extends BaseMapper<StudentEntity> {
    StudentEntity selectById(String id);

    void deleteAll();

    int mySaveBatch(@Param(value = "list") List<StudentEntity> list);
}
