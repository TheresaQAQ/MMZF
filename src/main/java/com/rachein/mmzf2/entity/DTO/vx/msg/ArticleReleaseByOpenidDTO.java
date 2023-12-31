package com.rachein.mmzf2.entity.DTO.vx.msg;

import lombok.Data;

import java.util.Map;
import java.util.Set;

/**
 * @Author 计算机科学系 吴远健
 * @Date 2022/11/10
 * @Description
 */
@Data
public class ArticleReleaseByOpenidDTO {
    private Set<String> touser;
    private Map<String, String> mpnews;
    private String msgtype = "mpnews";
    private Integer send_ignore_reprint = 1;
}

