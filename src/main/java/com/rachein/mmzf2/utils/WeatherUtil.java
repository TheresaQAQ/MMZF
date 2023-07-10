package com.rachein.mmzf2.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rachein.mmzf2.entity.DTO.api.Weather;
import com.rachein.mmzf2.entity.DTO.api.WeatherWarming;
import com.rachein.mmzf2.entity.DTO.sub.WeatherDTO;
import lombok.SneakyThrows;

import java.util.*;

/**
 * @Author 计算机科学系 吴远健
 * @Date 2022/12/2
 * @Description
 */
public class WeatherUtil {

    /**
     * 获取当前城市天气
     * @param localID 城市id号
     * @return
     */
    @SneakyThrows
    public static WeatherDTO now(String localID) {
        WeatherDTO dto = new WeatherDTO();
        String url ="https://devapi.qweather.com/v7/weather/now?location=" + localID + "&lang=zh&key=e7d2fae925a049a1a3cdb01bbe5884b4";
        String response_json = Objects.requireNonNull(Objects.requireNonNull(HttpRequestUtils.get(url)).body()).string();
        System.out.println(response_json);
        JSONObject jsonObject = JSON.parseObject(response_json);
        dto.setFxLink(jsonObject.getString("fxLink"));
        dto.setData(new ArrayList<>());
        dto.getData().add(jsonObject.getObject("now", Weather.class));
        return dto;
    }

    /**
     * 获取未来24小时的天气
     * @param localId
     * @return
     */
    @SneakyThrows
    public static WeatherDTO futureHour(String localID) {
        WeatherDTO dto = new WeatherDTO();
        String url = "https://devapi.qweather.com/v7/weather/24h?location=" + localID + "&key=e7d2fae925a049a1a3cdb01bbe5884b4";
        JSONObject jsonObject = JSON.parseObject(Objects.requireNonNull(Objects.requireNonNull(HttpRequestUtils.get(url)).body()).string());
        dto.setFxLink(jsonObject.getString("fxLink"));
        dto.setData(JSON.parseArray(jsonObject.getString("hourly"), Weather.class));
        return dto;
    }

    /**
     * 获取当前城市的预警
     * @param localID
     * @return
     */
    @SneakyThrows
    public static WeatherDTO warm(String localID) {
        String url = "https://devapi.qweather.com/v7/warning/now?lang=zh&location=" + localID + "&key=e7d2fae925a049a1a3cdb01bbe5884b4";
        String response_json = HttpRequestUtils.get(url).body().string();
        JSONObject jsonObject = JSON.parseObject(response_json);
        List<WeatherWarming> warming = JSONObject.parseArray(jsonObject.getString("warning"), WeatherWarming.class);
        if (warming.size() == 0) return null;
        WeatherDTO dto = new WeatherDTO();
        dto.setFxLink(jsonObject.getString("fxLink"));
        dto.setWarmings(warming);
        return dto;
    }


    private static Map<String, Object> result(JSONObject jsonObject) {
        Map<String, Object> map = new HashMap<>();
        map.put("update_time", jsonObject.getString("updateTime"));
        map.put("fxLink", jsonObject.getString("fxLink"));
        map.put("data", JSON.parseArray(jsonObject.getString("hourly"), Weather.class));
        return map;
    }

}
