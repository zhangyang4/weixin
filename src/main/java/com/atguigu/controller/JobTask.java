package com.atguigu.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.atguigu.WxDemo;

/**
 * @author : zhangjian
 * @Project : weixin
 * @ClassName : JobTask
 * @createTime : 2024/6/19 14:50
 */
@Configuration
@Component
public class JobTask {
//    @Scheduled(cron="0/5 * * ? * *")
//    public void getMsg() {
//        WxDemo.sendMsg();
//    }

    //每天上午10点推送
    @Scheduled(cron="0 10 11 * * ?")
    public void getMsg12() {
        WxDemo.sendMsg();
    }

    //每天下午5点推送
    @Scheduled(cron="0 0 17 * * ?")
    public void getMsg13() {
        WxDemo.sendMsg();
    }

}
