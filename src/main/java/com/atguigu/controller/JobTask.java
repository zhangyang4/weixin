package com.atguigu.controller;

import com.atguigu.WxDemo;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author : zhangjian
 * @Project : weixin
 * @ClassName : JobTask
 * @createTime : 2024/6/19 14:50
 */
@Configuration
@Component
public class JobTask {
    @Scheduled(cron="0/5 * * ? * *")
    public void getMsg() {
        WxDemo.sendMsg();
    }
}
