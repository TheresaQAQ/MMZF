package com.rachein.mmzf2.entity.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @Author 华南理工大学 吴远健
 * @Date 2022/9/20
 * @Description
 */
@Data
public class FileVo {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String url;
    private String alt;
    private String href;
//    private String vx_url;
}
