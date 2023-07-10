package com.rachein.mmzf2.entity.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author 计算机科学系 吴远健
 * @Date 2022/12/5
 * @Description
 */
@Data
public class ActivityVo {
    @JsonProperty("activityId")
    private Long id;
    private String title;
    @JsonProperty("participateTimeStart")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime participateTimeStart;
    @JsonProperty("participateTimeEnd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime participateTimeEnd;
    @JsonProperty("timeStart")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime timeStart;
    @JsonProperty("timeEnd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime timeEnd;
    private String articleUrl;
    @JsonProperty("articleId")
    private Long articleId;
    @JsonProperty("cover")
    private String photo;
    private String type;
    private String address;
    private String remark;
}
