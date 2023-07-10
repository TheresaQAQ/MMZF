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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rachein.mmzf2.entity.enums.StateEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 已毕业青年
 * </p>
 *
 * @author 吴远健
 * @since 2022-11-26
 */
@Data
@TableName(value = "graduate_person", autoResultMap = true)
@ApiModel(value = "GraduatePerson对象", description = "已毕业青年")
public class GraduatePerson implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId
    private String openid;


    @TableField("nickname")
    private String nickname;

    @ApiModelProperty("籍贯")
    @TableField("address")
    private String address;
    private String major;

    @ApiModelProperty("政治面貌（选择题，共青团员、中共党员（含预备党员）、群众三选一）")
    @TableField("zhengzhimianmao")
    private String zhengzhimianmao;

    @ApiModelProperty("电话号码")
    @TableField("phone")
    private String phone;

    @ApiModelProperty("出生年月")
    @TableField("birthday")
    private LocalDate birthday;

    @ApiModelProperty("毕业院校")
    @TableField("school")
    private String school;

    @ApiModelProperty("毕业年份")
    @TableField("graduate_date")
    private LocalDate graduateDate;

    @ApiModelProperty("婚姻情况")
    @TableField("hunyin")
    private String hunyin;

    @ApiModelProperty("所在城市")
    @TableField("now_in")
    private String nowIn;

    @ApiModelProperty("回乡发展意愿：强烈/一般/无）")
    @TableField("huixiangfazhanyiyuan")
    private String huixiangfazhanyiyuan;

    @ApiModelProperty("选择题：公务员/事业编；企业就业；待业中；创业中")
    @TableField("jiuyeqingkuang")
    private String jiuyeqingkuang;

    @ApiModelProperty("希望接受信息希望接受信息（多选择题：联谊活动；创业管理；招聘实习；技能培训；婚恋交友；志愿服务）")
    @TableField(value = "xiwangjieshouxingxi", typeHandler = FastjsonTypeHandler.class)
    @JsonProperty("xiwangjieshouxingxi")
    private List<String> xiwangxinxi;

    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    @TableField(value = "gmt_modified", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;

    @ApiModelProperty("照片")
    @TableField("photo")
    private String photo;

    @TableField("status")
    private StateEnum status;

    @TableField(exist = false)
    private String type = "毕业青年";


}
