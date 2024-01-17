package com.example.demo.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.mapper.EasyExcelMapper;
import com.example.demo.pojo.easyexcel.DemoData;
import com.example.demo.service.EasyExcelService;
import org.springframework.stereotype.Service;

/**
 * @author tangcanming
 * @date 2024/1/16 17:33
 * @describe
 */
@Service
public class EasyExcelServiceImpl extends ServiceImpl<EasyExcelMapper, DemoData> implements EasyExcelService {

}
