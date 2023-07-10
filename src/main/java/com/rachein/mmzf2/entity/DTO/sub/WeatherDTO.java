package com.rachein.mmzf2.entity.DTO.sub;

import com.rachein.mmzf2.entity.DTO.api.Weather;
import com.rachein.mmzf2.entity.DTO.api.WeatherWarming;
import lombok.Data;

import java.util.List;

/**
 * @Author 计算机科学系 吴远健
 * @Date 2022/12/2
 * @Description
 */
@Data
public class WeatherDTO {
    private String updateTime;
    private String fxLink;
    private List<Weather> data;
    private List<WeatherWarming> warmings;
}
