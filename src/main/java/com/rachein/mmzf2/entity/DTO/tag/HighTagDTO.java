package com.rachein.mmzf2.entity.DTO.tag;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author 计算机科学系 吴远健
 * @Date 2023/1/2
 * @Description
 */
@Data
public class HighTagDTO {
    private Boolean isTo;
    private LocalDateTime[] graduateDate;
    private String major;
}
