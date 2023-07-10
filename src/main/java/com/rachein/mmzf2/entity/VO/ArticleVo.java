package com.rachein.mmzf2.entity.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @Author 华南理工大学 吴远健
 * @Date 2022/9/21
 * @Description
 */
@Data
public class ArticleVo {
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty("articleId")
    private Long id;
    @JsonProperty("cover")
    private String coverUrl;
    private String title;
}
