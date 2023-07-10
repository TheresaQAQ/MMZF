package com.rachein.mmzf2.entity.DTO.tag;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author 计算机科学系 吴远健
 * @Date 2023/1/2
 * @Description
 */
@Data
public class CollegeTagDTO {
    private Boolean isTo;
    private String address;
    private String zhengzhimianmao;
    private LocalDateTime[] birthday;
    private String major;
    private String stage;
    private LocalDateTime[] graduateDate;
    private String huixiangyiyuan;
    private String jiuyefangxiang;
    private String[] xiwangjieshouxingxi;
}
