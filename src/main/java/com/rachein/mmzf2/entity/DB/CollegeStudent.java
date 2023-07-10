package com.rachein.mmzf2.entity.DB;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rachein.mmzf2.config.JsonStringArrayTypeHandler;
import com.rachein.mmzf2.entity.enums.StateEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 在读大学生
 * </p>
 *
 * @author 吴远健
 * @since 2022-11-26
 */
@Data
@TableName(autoResultMap = true, value="college_student")
@ApiModel(value = "CollegeStudent对象", description = "在读大学生")
public class CollegeStudent implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId
    private String openid;

    private String stage;
    @ApiModelProperty("姓名")
    @TableField("nickname")
    private String nickname;

    @ApiModelProperty("籍贯")
    @TableField("address")
    private String address;

    @ApiModelProperty("选择题，共青团员、中共党员（含预备党员）、群众三选一）")
    @TableField("zhengzhimianmao")
    private String zhengzhimianmao;

    @ApiModelProperty("电话号码")
    @TableField("phone")
    private String phone;

    @ApiModelProperty("出生年月")
    @TableField("birthday")
    private LocalDate birthday;

    @ApiModelProperty("在读高校")
    @TableField("school")
    private String school;

    @ApiModelProperty("就读专业")
    @TableField("major")
    private String major;

    @ApiModelProperty("毕业年月")
    @TableField("graduate_date")
    private LocalDate graduateDate;

    @ApiModelProperty("回乡发展意愿（选择题：强烈、一般、无）")
    @TableField("huixiangyiyuan")
    private String huixiangyiyuan;

    @ApiModelProperty("毕业就业方向（选择题：公务员/事业编、自主创业、企业就业）")
    @TableField("jiuyefangxiang")
    private String jiuyefangxiang;

    @ApiModelProperty("希望接受信息希望接受信息（多选择题：联谊活动；创业管理；招聘实习；技能培训；婚恋交友；志愿服务）")
    @TableField(value = "xiwangjieshouxingxi", typeHandler = FastjsonTypeHandler.class)
    @JsonProperty("xiwangjieshouxingxi")
    private List<String> xiwangjieshouxingxi;

    @ApiModelProperty("状态0：未审核")
    @TableField("status")
    private StateEnum status;

    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    @TableField(value = "gmt_modified", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;

    @ApiModelProperty("照片")
    @TableField("photo")
    private String photo;

    @TableField(exist = false)
    private String type = "在读大学生";

}
