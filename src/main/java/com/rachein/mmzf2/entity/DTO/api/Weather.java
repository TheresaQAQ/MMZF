package com.rachein.mmzf2.entity.DTO.api;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author 计算机科学系 吴远健
 * @Date 2022/12/2
 * @Description
 */
@Data
public class Weather {
    /**
     * now.obsTime 数据观测时间
     * hourly.fxTime 预报时间
     * hourly.temp 温度，默认单位：摄氏度
     * hourly.icon 天气状况和图标的代码，图标可通过天气状况和图标下载
     * hourly.text 天气状况的文字描述，包括阴晴雨雪等天气状态的描述
     * hourly.wind360 风向360角度
     * hourly.windDir 风向
     * hourly.windScale 风力等级
     * hourly.windSpeed 风速，公里/小时
     * hourly.humidity 相对湿度，百分比数值
     * hourly.precip 当前小时累计降水量，默认单位：毫米
     * hourly.pop 逐小时预报降水概率，百分比数值，可能为空
     * hourly.pressure 大气压强，默认单位：百帕
     * hourly.cloud 云量，百分比数值。可能为空
     * hourly.dew 露点温度。可能为空
     * refer.sources 原始数据来源，或数据源说明，可能为空
     * refer.license 数据许可或版权声明，可能为空
     *
     */
    private String obsTime;
    private String fxTime;
    private String temp;
    private String text;
    private String windScale;
    private String feelsLike;
    private String pop;
    private String vis;
}
