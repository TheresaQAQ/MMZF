package com.rachein.mmzf2.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rachein.mmzf2.core.service.ISubService;
import com.rachein.mmzf2.entity.DTO.api.Weather;
import com.rachein.mmzf2.entity.DTO.sub.WeatherDTO;
import com.rachein.mmzf2.entity.DTO.sub.YiqingDTO;
import com.rachein.mmzf2.utils.HttpRequestUtils;
import com.rachein.mmzf2.utils.WeatherUtil;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author 计算机科学系 吴远健
 * @Date 2022/11/29
 * @Description
 */
@Service
public class ISubServiceImpl implements ISubService {

    private final String localID = "101282005";
    private DateTimeFormatter df_h = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");

    @Override
    public String risk() {
        String url = "https://c.m.163.com/ug/api/wuhan/app/manage/track-map?cityId=440900";
        Map<String, String> headers = new HashMap<>();
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36");
        String data = JSON.parseObject(HttpRequestUtils.get(url, headers)).getJSONObject("data").getString("items");
        data = data.substring(1, data.length()-1);
        JSONObject jsonObject = JSON.parseObject(data);
        //获取关注的地区
        List<YiqingDTO> poiList = JSON.parseArray(jsonObject.getString("poiList"), YiqingDTO.class);
//        System.out.println(poiList);
        //计算高风险地区数量：
        int risk_count = 0;
        for (YiqingDTO dto : poiList) {
            if (dto.getType().equals("2")) {
                risk_count++;
            }
        }
        //获取更新时间
        String update_time = jsonObject.getString("leavePolicyDate");
        //获取查询的城市
        String city = jsonObject.getString("city");
        //获取出行政策
        String leavePolicyList = jsonObject.getString("leavePolicyList");
        //获取反城政策
        String backPolicyList = jsonObject.getString("backPolicyList");
        return String.format("%s\n" +
                "\uD83D\uDD14%s——疫情防控\n" +
                "\n" +
                "【%d个高风险地区】\n" +
                "-----------------------------\n" +
                "%s\n" +
                "-----------------------------\n" +
                "\n" +
                "\uD83D\uDD14\n" +
                "出城政策：\n" +
                "%s\n" +
                "\n" +
                "数据来源：当地卫健委和权威网站 发布时间：%s", update_time, city, risk_count, poiList, leavePolicyList, update_time);
    }

    @SneakyThrows
    @Override
    public String weather() {
        //获取实时
        //获取未来
        return weather_now();

    }

    @Override
    public String weather_now() {
        WeatherDTO now = WeatherUtil.now(localID);
        Weather now_data = now.getData().get(0);
        WeatherDTO warm = WeatherUtil.warm(localID);
        return String.format("☁☁\n" +
                "【茂名信宜市】今日实时天气\n" +
                "\uD83D\uDD14⚠【预警】%s\n" +
                "\n" +
                "【时间】 %s\n" +
                "天气：%s ☔\n" +
                "气温：%s° \uD83D\uDCA6\n" +
                "能见度：%s公里\n" +
                "体感温度：%s°\n" +
                "\n" +
                "\n" +
                "\uD83D\uDCAC<a href=\"%s\">点我打开页面看具体信息哦</a>\n" +
                "\uD83D\uDD0E<a href=\"weixin://bizmsgmenu?msgmenucontent=%s&msgmenuid=20041124\">点我查看 未来3小时的天气预测</a>\n" +
                "\n" +
                "数据来源：中国气象局",
                "NULL",
                now_data.getObsTime().substring(0,13).replaceAll("T", " ")+"时", now_data.getText(),now_data.getTemp(),now_data.getVis(),now_data.getFeelsLike(), now.getFxLink(),
                "查询信宜未来3小时天气");
    }

    @Override
    public String weather_future() {
        WeatherDTO futureHour = WeatherUtil.futureHour(localID);
        List<Weather> weathers = futureHour.getData();
        Weather weather1 = weathers.get(0);
        Weather weather2 = weathers.get(1);
        Weather weather3 = weathers.get(2);
        return String.format("⭐⭐\n" +
                "【茂名信宜市】今日未来3小时的天气预报\n" +
                "~~~~~~~~~~~~~~~~~~~~~\n" +
                "⭐\n" +
                "时间：%s\n" +
                "温度：%s°\n" +
                "天气：%s\n" +
                "下雨的概率：%s\n" +
                "~~~~~~~~~~~~~~~~~~~~~\n" +
                "⭐\n" +
                "时间：%s\n" +
                "温度：%s°\n" +
                "天气：%s\n" +
                "下雨的概率：%s\n" +
                "~~~~~~~~~~~~~~~~~~~~~\n" +
                "⭐\n" +
                "时间：%s\n" +
                "温度：%s°\n" +
                "天气：%s\n" +
                "下雨的概率：%s\n" +
                "\n" +
                "\uD83D\uDCAC<a href=\"%s\">点我打开页面看具体信息哦</a>\n" +
                "数据来源：中国气象局",
                timeTransfer(weather1.getFxTime()), weather1.getTemp(), weather1.getText(), weather1.getPop(),
                timeTransfer(weather2.getFxTime()), weather2.getTemp(), weather2.getText(), weather2.getPop(),
                timeTransfer(weather3.getFxTime()), weather3.getTemp(), weather3.getText(), weather3.getPop(),
                futureHour.getFxLink()
                );
    }

    private String timeTransfer(String d1) {
        return d1.substring(0,13).replaceAll("T", " ")+"时";
    }
}
