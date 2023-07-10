package com.rachein.mmzf2.entity.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.rachein.mmzf2.entity.DB.Activity;
import lombok.Data;

/**
 * @Author 华南理工大学 吴远健
 * @Date 2022/9/21
 * @Description
 */
@Data
public class ArticleInfoVo {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String content;
    private String author;
    private String title;
    @JsonProperty("cover")
    private String coverUrl;
    private ActivityVo activity;
}
