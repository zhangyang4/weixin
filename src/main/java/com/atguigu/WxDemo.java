package com.atguigu;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

/**
 * @author : zhangjian
 * @Project : LearnSpringBoot
 * @ClassName : WxDemo
 * @createTime : 2024/6/18 16:30
 */
public class WxDemo {
    public static void main(String[] args) {
        List<String> users = new ArrayList<>();
        users.add("omwSH6oxME8J4aQUkovgxcISSd1w");//我
//        users.add("omwSH6npJ8TLgkXybtWHtNBnZIdk");
        for(String user:users){
            WxDemo.sendMsg(user);
        }
    }




    /**
     * 获取accessToken
     *
     * @return
     */
    public static String get_access_token() {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx6f9b8062bcb5120f&secret=9d301c28bbeee0575957d006ab555ed5";
        String token = HttpUtil.get(url);
        Map map = JSON.parseObject(token, Map.class);
        return map.get("access_token").toString();
    }


    /**
     * 获取天气
     *
     * @return
     */
    public static List<Map<String, Object>> getWeather() {
        String url = "https://restapi.amap.com/v3/weather/weatherInfo?key=a41b93c7c6a54a07a2c2cca673aefabe&city=410902&extensions=all";
        Map<String, Object> map = new HashMap<>();
        String result = HttpUtil.get(url, map);
        JSONObject jsonObject = JSON.parseObject(result);
        JSONArray list = (JSONArray) jsonObject.get("forecasts");
        JSONObject list2 = (JSONObject) list.get(0);
        JSONArray weatherData = (JSONArray)list2.get("casts");

        JSONObject mt0 = (JSONObject) weatherData.get(0);
        JSONObject mt1 = (JSONObject) weatherData.get(1);
        List<Map<String, Object>> result3 = new ArrayList<>();
        //明日天气
        Map<String, Object> map1 = new HashMap<>();
        map1.put("value", mt1.get("dayweather"));
        result3.add(map1);
        //日期
        Map<String, Object> map2 = new HashMap<>();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(mt0.get("date"));
        sb2.append(" 星期" + mt0.get("week"));
        map2.put("value", sb2.toString());
        result3.add(map2);
        //在一起天数
        String date1 = "2016-06-03";
        String date2 = DateUtil.format(new Date(), "yyyy-MM-dd");
        long between = DateUtil.between(DateUtil.parse(date1), DateUtil.parse(date2), DateUnit.DAY);
        Map<String, Object> map3 = new HashMap<>();
        map3.put("value", between);
        result3.add(map3);
        //女生生日
        date1 = "2024-12-27";
        date2 = DateUtil.format(new Date(), "yyyy-MM-dd");
        between = DateUtil.between(DateUtil.parse(date1), DateUtil.parse(date2), DateUnit.DAY);
        Map<String, Object> map4 = new HashMap<>();
        map4.put("value", between);
        result3.add(map4);
        //最高气温
        Map<String, Object> map5 = new HashMap<>();
        map5.put("value", mt1.get("daytemp")+"℃");
        result3.add(map5);
        //最低气温
        Map<String, Object> map6 = new HashMap<>();
        map6.put("value", mt1.get("nighttemp")+"℃");
        result3.add(map6);
        //课表
        Map<String, Object> school = new HashMap<>();
        school.put("1","下午第1节");
        school.put("2","下午第4、5节");
        school.put("3","下午第3、4节");
        school.put("4","上午第5节");
        school.put("5","下午第1节");
        school.put("6","大补：上午第3、4节；小补：上午第3节");
        school.put("7","上午第3节");
        Map<String, Object> map7 = new HashMap<>();
        map7.put("value", school.get(mt1.get("week")));
        result3.add(map7);
        //地区
        Map<String, Object> map8 = new HashMap<>();
        map8.put("value", "濮阳市 " + list2.get("city").toString());
        result3.add(map8);
        //男生生日
        date1 = "2024-10-29";
        date2 = DateUtil.format(new Date(), "yyyy-MM-dd");
        between = DateUtil.between(DateUtil.parse(date1), DateUtil.parse(date2), DateUnit.DAY);
        Map<String, Object> map9 = new HashMap<>();
        map9.put("value", between);
        result3.add(map9);
        //今日天气
        Map<String, Object> map10 = new HashMap<>();
        map10.put("value", mt0.get("dayweather"));
        result3.add(map10);
        //今日最高温
        Map<String, Object> map11 = new HashMap<>();
        map11.put("value", mt0.get("daytemp")+"℃");
        result3.add(map11);
        //今日最低温
        Map<String, Object> map12 = new HashMap<>();
        map12.put("value", mt0.get("nighttemp")+"℃");
        result3.add(map12);

        return result3;
    }



    /**
     * 发送模板消息
     */
    public static void sendMsg(String user) {
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + get_access_token();
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        List<Map<String, Object>> list = getWeather();
        //获取当前系统时间小时
        int hour = DateUtil.hour(new Date(), true);

        map.put("topcolor", "#FF0000");
        map.put("touser", user);
        if(hour < 12){
            map.put("template_id", "9DXiX5HbBCsIKhd8w9Nowq4VgPsBTG3rNDTPzWkHUkw");
        }else if(hour == 12){
            map.put("template_id", "sfTatZS8OjDTdXyOLmreCTwmsiWjbZe8RJSFHSgXl-g");
        }else if(hour == 23){
            map.put("template_id", "W7-HDvPPvlMrFuR5VcwOtrxFbPn00s0TgC3edCoWa-U");
        }else{
            map.put("template_id", "W7-HDvPPvlMrFuR5VcwOtrxFbPn00s0TgC3edCoWa-U");
        }
        map.put("url", "http://weixin.qq.com/download");

        //明日天气
        Map<String, Object> weather = new HashMap<>();
        weather.put("value", list.get(0).get("value"));
        weather.put("color", "#173177");
        data.put("weather", weather);
        //日期
        Map<String, Object> tomorrow = new HashMap<>();
        tomorrow.put("value", list.get(1).get("value"));
        tomorrow.put("color", "#173177");
        data.put("tomorrow", tomorrow);
        //在一起天数
        Map<String, Object> between = new HashMap<>();
        between.put("value", list.get(2).get("value"));
        between.put("color", "#173177");
        data.put("between", between);
        //女生生日
        Map<String, Object> birthday = new HashMap<>();
        birthday.put("value", list.get(3).get("value"));
        birthday.put("color", "#173177");
        data.put("birthday", birthday);
        //最高气温
        Map<String, Object> highest = new HashMap<>();
        highest.put("value", list.get(4).get("value"));
        highest.put("color", "#173177");
        data.put("highest", highest);
        //最低气温
        Map<String, Object> lowest = new HashMap<>();
        lowest.put("value", list.get(5).get("value"));
        lowest.put("color", "#FAEBD7");
        data.put("lowest", lowest);
        //课表
        Map<String, Object> school = new HashMap<>();
        school.put("value", list.get(6).get("value"));
        school.put("color", "#FAEBD7");
        data.put("school", school);
        //地区
        Map<String, Object> area = new HashMap<>();
        area.put("value", list.get(7).get("value"));
        area.put("color", "#FAEBD7");
        data.put("area", area);
        //注意
        Map<String, Object> tips = new HashMap<>();
        if(list.get(0).get("value").toString().contains("雨")){
            tips.put("value","明日有雨记得拿伞哦" );
        }else if(Integer.parseInt(list.get(4).get("value").toString().split("℃")[0]) > 38){
            tips.put("value","明日高温多喝水哦" );
        }else if(Integer.parseInt(list.get(4).get("value").toString().split("℃")[0]) < 20 ){
            tips.put("value","明日降温注意保暖哦" );
        }else {
            tips.put("value","开开心心一整天喽" );
        }
        tips.put("color", "#FAEBD7");
        data.put("tips", tips);
        //男生生日
        Map<String, Object> birthdayT = new HashMap<>();
        birthdayT.put("value", list.get(8).get("value"));
        birthdayT.put("color", "#FAEBD7");
        data.put("birthdayT", birthdayT);
        //今日气温
        Map<String, Object> today = new HashMap<>();
        today.put("value", list.get(9).get("value"));
        today.put("color", "#FAEBD7");
        data.put("today", today);
        //今日最高温
        Map<String, Object> hightW = new HashMap<>();
        hightW.put("value", list.get(10).get("value"));
        hightW.put("color", "#FAEBD7");
        data.put("hightW", hightW);
        //今日最低温
        Map<String, Object> lowW = new HashMap<>();
        lowW.put("value", list.get(11).get("value"));
        lowW.put("color", "#FAEBD7");
        data.put("lowW", lowW);
        //注意
        Map<String, Object> tipsT = new HashMap<>();
        if(list.get(9).get("value").toString().contains("雨")){
            tipsT.put("value","今日有雨记得拿伞哦" );
        }else if(Integer.parseInt(list.get(10).get("value").toString().split("℃")[0]) > 38){
            tipsT.put("value","今日高温多喝水哦" );
        }else if(Integer.parseInt(list.get(10).get("value").toString().split("℃")[0]) < 20 ){
            tipsT.put("value","今日降温注意保暖哦" );
        }else {
            tipsT.put("value","开开心心一整天喽" );
        }
        tipsT.put("color", "#FAEBD7");
        data.put("tipsT", tipsT);


        map.put("data", data);
        String post = HttpUtil.post(url, JSON.toJSONString(map));
        System.out.println("推送成功");
    }


}
