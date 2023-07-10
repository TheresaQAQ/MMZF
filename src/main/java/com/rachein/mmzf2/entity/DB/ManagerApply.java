package com.rachein.mmzf2.entity.DB;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rachein.mmzf2.entity.enums.RoleEnum;
import com.rachein.mmzf2.entity.enums.StateEnum;
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
 * @since 2023-02-05
 */
@Getter
@Setter
@TableName("manager_apply")
@ApiModel(value = "ManagerApply对象", description = "")
public class ManagerApply implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("openid")
    private String openid;

    @ApiModelProperty("状态 默认待审核")
    @TableField("state")
    private StateEnum state;

    @TableField(value = "gmt_modified", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;

    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
    @JsonFormat(pattern = " yyyy-MM-dd HH时mm分")
    private LocalDateTime gmtCreate;

    @TableField(value = "type")
    private RoleEnum type;

    @TableField(value = "checker_openid")
    private String checkerOpenid;

    private String remark;
    @JsonProperty("fileUrl")
    private String file;


}
