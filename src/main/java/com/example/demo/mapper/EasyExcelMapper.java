package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.pojo.easyexcel.DemoData;
import org.springframework.stereotype.Repository;

/**
 * @author tangcanming
 * @date 2024/1/16 17:35
 * @describe
 */
@Repository
public interface EasyExcelMapper extends BaseMapper<DemoData> {
}
