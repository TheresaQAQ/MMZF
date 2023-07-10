package com.rachein.mmzf2.entity.DB;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.rachein.mmzf2.entity.enums.DraftStateEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author 吴远健
 * @since 2023-01-04
 */
@Getter
@Setter
@TableName("draft_release_log")
@ApiModel(value = "DraftReleaseLog对象", description = "")
public class DraftReleaseLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("draft_id")
    private Long draftId;

    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    @TableField("gmt_modify")
    private LocalDateTime gmtModify;

    @ApiModelProperty("申请人姓名")
    @TableField("applicant")
    private String applicant;

    @ApiModelProperty("申请人微信号")
    @TableField("applicant_openid")
    private String applicantOpenid;

    @ApiModelProperty("状态 默认为1：待审核")
    @TableField("state")
    private DraftStateEnum state;

    @TableField("remark")
    private String remark;


}
