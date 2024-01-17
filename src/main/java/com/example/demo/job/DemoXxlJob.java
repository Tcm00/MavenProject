package com.example.demo.job;

import com.xxl.job.core.executor.XxlJobExecutor;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.util.DateUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author tangcanming
 * @date 2024/1/15 17:04
 * @describe
 */

public class DemoXxlJob extends IJobHandler implements InitializingBean {
    @Override
    public void execute() throws Exception {
        String format = DateUtil.format(new Date(), "yyyyMMdd HH:mm:ss");
        System.out.println("当前时间:" + format);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("创建对象");
        XxlJobExecutor.registJobHandler("DemoXxlJob", this);
    }

    @Override
    public void init() throws Exception {
        System.out.println("开始初始化了。。。");
        super.init();
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("开始结束了。。。");
        super.destroy();
    }
}
