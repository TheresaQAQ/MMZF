package com.rachein.mmzf2.entity.DB;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rachein.mmzf2.entity.enums.StateEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 高中生
 * </p>
 *
 * @author 吴远健
 * @since 2022-11-26
 */
@Data
@TableName("senior_student")
@ApiModel(value = "SeniorStudent对象", description = "高中生")
public class SeniorStudent implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("在读学校")
    @TableField("school")
    private String school;

    @ApiModelProperty("手机号")
    @TableField("phone")
    private String phone;

    @ApiModelProperty("出生日期")
    @TableField("birthday")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @ApiModelProperty("毕业年份")
    @TableField("graduate_year")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String graduateYear;

    @ApiModelProperty("选科方向")
    @TableField("major")
    private String major;

    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    @TableField(value = "gmt_modified", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;

    @ApiModelProperty("状态0：未审核")
    @TableField("state")
    private StateEnum status;

    @TableField("nickname")
    private String nickname;

    @TableId
    private String openid;

    @TableField(exist = false)
    private String type = "高中生";

}
