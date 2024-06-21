package com.atguigu.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.atguigu.WxDemo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : zhangjian
 * @Project : weixin
 * @ClassName : JobTask
 * @createTime : 2024/6/19 14:50
 *  @Scheduled(cron="0/5 * * ? * *")
 */
@Configuration
@Component
public class JobTask {

    @Scheduled(cron="0 10 09 * * ?")
    public void getMsg11() {
        List<String> users = new ArrayList<>();
        users.add("omwSH6oxME8J4aQUkovgxcISSd1w");
        users.add("omwSH6npJ8TLgkXybtWHtNBnZIdk");
        for(String user:users){
            WxDemo.sendMsg(user);
        }
    }


    @Scheduled(cron="0 0 12 * * ?")
    public void getMsg13() {
        List<String> users = new ArrayList<>();
        users.add("omwSH6oxME8J4aQUkovgxcISSd1w");
        users.add("omwSH6npJ8TLgkXybtWHtNBnZIdk");
        for(String user:users){
            WxDemo.sendMsg(user);
        }
    }

    @Scheduled(cron="0 0 23 * * ?")
    public void getMsg14() {
        List<String> users = new ArrayList<>();
        users.add("omwSH6oxME8J4aQUkovgxcISSd1w");
        users.add("omwSH6npJ8TLgkXybtWHtNBnZIdk");
        for(String user:users){
            WxDemo.sendMsg(user);
        }
    }

}
