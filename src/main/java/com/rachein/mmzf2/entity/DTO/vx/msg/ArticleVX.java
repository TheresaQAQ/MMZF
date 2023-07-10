package com.rachein.mmzf2.entity.DTO.vx.msg;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Author 计算机科学系 吴远健
 * @Date 2022/11/10
 * @Description
 */
@Data
public class ArticleVX {
    private String thumbMediaId;
    private String author;
    private String title;
    private String contentSourceUrl;
    private String content;
    private String digest;
    private Integer showCoverPic;
    private Integer needOpenComment;
    private Integer onlyFansCanComment;
}
