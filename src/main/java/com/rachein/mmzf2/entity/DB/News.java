package com.rachein.mmzf2.entity.DB;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.rachein.mmzf2.entity.enums.DraftMethodEnum;
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
 * @since 2023-01-06
 */
@Getter
@Setter
@TableName("news")
@ApiModel(value = "News对象", description = "")
public class News implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    @TableField("gmt_modify")
    private LocalDateTime gmtModify;

    @TableField("applicant_name")
    private String applicantName;

    @TableField("applicant_openid")
    private String applicantOpenid;

    @ApiModelProperty("默认0 群发")
    @TableField("release_type")
    private DraftMethodEnum releaseType;

    @ApiModelProperty("发布内容")
    @TableField(value = "content", typeHandler = FastjsonTypeHandler.class)
    private List<String> content;


}
