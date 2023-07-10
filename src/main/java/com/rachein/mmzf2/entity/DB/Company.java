package com.rachein.mmzf2.entity.DB;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rachein.mmzf2.entity.enums.StateEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 单位注册
 * </p>
 *
 * @author 吴远健
 * @since 2022-11-26
 */
@Data
@TableName("company")
@ApiModel(value = "Company对象", description = "单位注册")
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String openid;

    @ApiModelProperty("单位名称")
    @TableField("name")
    private String name;

    @TableField("phone")
    @JsonProperty("contact")
    private String phone;

    @ApiModelProperty("单位所在地（选择题 信宜/其他；信宜的选择以下几个镇")
    @TableField("address")
    private String address;

    @ApiModelProperty("性质国企  集体  私企  合资  股份  外企 社团  政府  事业  其它：")
    @TableField("quality")
    private String quality;

    @ApiModelProperty("代表人名称")
    @TableField("master_name")
    private String masterName;

    @ApiModelProperty("代表人电话")
    @TableField("master_phone")
    private String masterPhone;

    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    @ApiModelProperty("营业执照附件上传：")
    @TableField("attach")
    private String photo;
    @TableField("register_date")
    private String registerDate;

    @ApiModelProperty("状态0：未审核")
    @TableField("status")
    private StateEnum status;

    private String ziben;

    @TableField(exist = false)
    private String type = "合作单位";

}
