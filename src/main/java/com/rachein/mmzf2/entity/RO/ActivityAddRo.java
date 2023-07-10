package com.rachein.mmzf2.entity.RO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Author 计算机科学系 吴远健
 * @Date 2022/10/5
 * @Description
 */
@Data
public class ActivityAddRo {
    @JsonProperty("activityId")
    private Long id;
    private String title;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone="GMT+8")
    private LocalDateTime timeStart;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone="GMT+8")
    private LocalDateTime timeEnd;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone="GMT+8")
    private LocalDateTime participateTimeStart;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone="GMT+8")
    private LocalDateTime participateTimeEnd;
    private String type;
    private String address;
    private String remark;
    @JsonProperty("articleId")
    private Long articleId;
}
