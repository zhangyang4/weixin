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
        sendMsg();
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
        String url = "https://apis.tianapi.com/tianqi/index";
        Map<String, Object> map = new HashMap<>();
        map.put("key", "90e607d5420b8832003c22ee15424609");
        map.put("city", "华龙区");
        map.put("type", "7");
        String result = HttpUtil.post(url, map);
        JSONObject jsonObject = JSON.parseObject(result);
        JSONObject result2 = (JSONObject) jsonObject.get("result");
        JSONArray list = (JSONArray) result2.get("list");
        JSONObject mt0 = (JSONObject) list.get(0);
        JSONObject mt = (JSONObject) list.get(1);

        List<Map<String, Object>> result3 = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        //明日天气
        map1.put("value", mt.get("weather"));
        result3.add(map1);
        //日期
        Map<String, Object> map2 = new HashMap<>();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(mt0.get("date"));
        sb2.append(" " + mt0.get("week"));
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
        map5.put("value", mt.get("highest")+"℃");
        result3.add(map5);
        //最低气温
        Map<String, Object> map6 = new HashMap<>();
        map6.put("value", mt.get("lowest")+"℃");
        result3.add(map6);
        //课表
        Map<String, Object> school = new HashMap<>();
        school.put("星期一","下午第1节");
        school.put("星期二","下午第4、5节");
        school.put("星期三","下午第3、4节");
        school.put("星期四","上午第5节");
        school.put("星期五","下午第1节");
        school.put("星期六","大补：上午第3、4节；小补：上午第3节");
        school.put("星期日","上午第3节");
        Map<String, Object> map7 = new HashMap<>();
        map7.put("value", school.get(mt.get("week")));
        result3.add(map7);

        Map<String, Object> map8 = new HashMap<>();
        String o = (String)mt.get("tips");
        map8.put("value",o.split("，")[0]+"。");
        result3.add(map8);

        return result3;
    }

    /**
     * 每日情话
     * @return
     */
    public static String getLoveStr(){
        String url = "https://apis.tianapi.com/saylove/index";
        Map<String, Object> map = new HashMap<>();
        map.put("key", "90e607d5420b8832003c22ee15424609");
        String result = HttpUtil.get(url, map);
        JSONObject jsonObject = JSON.parseObject(result);
        JSONObject result2 = (JSONObject) jsonObject.get("result");
        return result2.get("content").toString();
    }



    /**
     * 发送模板消息
     */
    public static void sendMsg() {
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + get_access_token();
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        List<Map<String, Object>> list = getWeather();

        map.put("topcolor", "#FF0000");
        map.put("touser", "omwSH6oxME8J4aQUkovgxcISSd1w");
        map.put("template_id", "FNvVPOEMZrAniwg48x5q5zm5EpniOvTBnJsBgwRMwhU");
        map.put("url", "http://weixin.qq.com/download");

        Map<String, Object> weather = new HashMap<>();
        weather.put("value", list.get(0).get("value"));
        weather.put("color", "#173177");
        data.put("weather", weather);

        Map<String, Object> tomorrow = new HashMap<>();
        tomorrow.put("value", list.get(1).get("value"));
        tomorrow.put("color", "#173177");
        data.put("tomorrow", tomorrow);

        Map<String, Object> between = new HashMap<>();
        between.put("value", list.get(2).get("value"));
        between.put("color", "#173177");
        data.put("between", between);

        Map<String, Object> birthday = new HashMap<>();
        birthday.put("value", list.get(3).get("value"));
        birthday.put("color", "#173177");
        data.put("birthday", birthday);

        Map<String, Object> highest = new HashMap<>();
        highest.put("value", list.get(4).get("value"));
        highest.put("color", "#173177");
        data.put("highest", highest);

        Map<String, Object> lowest = new HashMap<>();
        lowest.put("value", list.get(5).get("value"));
        lowest.put("color", "#FAEBD7");
        data.put("lowest", lowest);

        Map<String, Object> loveStr = new HashMap<>();
        loveStr.put("value", getLoveStr());
        loveStr.put("color", "#FAEBD7");
        data.put("loveStr", loveStr);

        Map<String, Object> school = new HashMap<>();
        school.put("value", list.get(6).get("value"));
        school.put("color", "#FAEBD7");
        data.put("school", school);

        Map<String, Object> tips = new HashMap<>();
        tips.put("value", list.get(7).get("value"));
        tips.put("color", "#FAEBD7");
        data.put("tips", tips);

        Map<String, Object> area = new HashMap<>();
        area.put("value", "濮阳市");
        area.put("color", "#FAEBD7");
        data.put("area", area);

        map.put("data", data);
        String post = HttpUtil.post(url, JSON.toJSONString(map));
    }


}
