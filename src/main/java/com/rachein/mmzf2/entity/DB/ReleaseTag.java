package com.rachein.mmzf2.entity.DB;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author 吴远健
 * @since 2023-01-03
 */
@Data
@TableName(value = "draft_release_tag", autoResultMap = true)
@ApiModel(value = "DraftReleaseTag对象", description = "")
public class ReleaseTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("draft_id")
    @JsonProperty("draftId")
    private Long draftId;

    @ApiModelProperty("是否推送给高中生 默认：0/否")
    @TableField("high_is_to")
    @JsonProperty("high_isTo")
    private Boolean highIsTo;

    @ApiModelProperty("是否推送给在读大学生 默认：0/否")
    @TableField("college_is_to")
    @JsonProperty("college_isTo")
    private Boolean collegeIsTo;

    @ApiModelProperty("是否推送给毕业青年 默认：0/否")
    @TableField("graduate_is_to")
    @JsonProperty("graduate_isTo")
    private Boolean graduateIsTo;

    @TableField(value = "high_graduate", typeHandler = FastjsonTypeHandler.class)
    @JsonProperty(value = "high_graduateDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private List<LocalDate> highGraduate;
//
    @TableField(value = "high_major", typeHandler = FastjsonTypeHandler.class)
    @JsonProperty("high_major")
    private List<String> highMajor;

    @TableField(value = "college_address", typeHandler = FastjsonTypeHandler.class)
    @JsonProperty("college_address")
    private List<String> collegeAddress;

    @TableField(value = "college_zhengzhimianmao", typeHandler = FastjsonTypeHandler.class)
    @JsonProperty("college_zhengzhimianmao")
    private List<String> collegeZhengzhimianmao;

    @TableField(value = "college_birthday", typeHandler = FastjsonTypeHandler.class)
    @JsonProperty("college_birthday")
    private List<LocalDate> collegeBirthday;

    @TableField(value = "college_major", typeHandler = FastjsonTypeHandler.class)
    @JsonProperty("college_major")
    private List<String> collegeMajor;

    @TableField(value = "college_stage", typeHandler = FastjsonTypeHandler.class)
    @JsonProperty("college_stage")
    private List<String> collegeStage;

    @TableField(value = "college_graduate_date", typeHandler = FastjsonTypeHandler.class)
    @JsonProperty("college_graduateDate")
    private List<LocalDate> collegeGraduateDate;

    @TableField(value = "college_huixiangyiyuan", typeHandler = FastjsonTypeHandler.class)
    @JsonProperty("college_huixiangyiyuan")
    private List<String> collegeHuixiangyiyuan;

    @TableField(value = "college_jiuyefangxiang", typeHandler = FastjsonTypeHandler.class)
    @JsonProperty("college_jiuyefangxiang")
    private List<String> collegeJiuyefangxiang;

    @TableField(value = "college_xiwangjieshouxingxi", typeHandler = FastjsonTypeHandler.class)
    @JsonProperty("college_xiwangjieshouxingxi")
    private List<String> collegeXiwangjieshouxingxi;

    @TableField(value = "graduate_address", typeHandler = FastjsonTypeHandler.class)
    @JsonProperty("graduate_address")
    private List<String> graduateAddress;

    @TableField(value = "graduate_zhengzhimianmao", typeHandler = FastjsonTypeHandler.class)
    @JsonProperty("graduate_zhengzhimianmao")
    private List<String> graduateZhengzhimianmao;

    @TableField(value = "graduate_birthday", typeHandler = FastjsonTypeHandler.class)
    @JsonProperty("graduate_birthday")
    private List<LocalDate> graduateBirthday;

    @TableField(value = "graduate_major", typeHandler = FastjsonTypeHandler.class)
    @JsonProperty("graduate_major")
    private List<String> graduateMajor;

    @TableField(value = "graduate_stage", typeHandler = FastjsonTypeHandler.class)
    @JsonProperty("graduate_stage")
    private List<String> graduateStage;

    @TableField(value = "graduate_graduate_date", typeHandler = FastjsonTypeHandler.class)
    @JsonProperty("graduate_graduateDate")
    private List<LocalDate> graduateGraduateDate;

    @TableField(value = "graduate_xiwangjieshouxingxi", typeHandler = FastjsonTypeHandler.class)
    @JsonProperty("graduate_xiwangjieshouxingxi")
    private List<String> graduateXiwangjieshouxingxi;

    @TableField(value = "graduate_now_in", typeHandler = FastjsonTypeHandler.class)
    @JsonProperty("graduate_nowIn")
    private List<String> graduateNowIn;

    @TableField(value = "graduate_jiuyeqingkuang", typeHandler = FastjsonTypeHandler.class)
    @JsonProperty("graduate_jiuyeqingkuang")
    private List<String> graduateJiuyeqingkuang;

    @TableField(value = "graduate_huixiangyiyuan", typeHandler = FastjsonTypeHandler.class)
    @JsonProperty("graduate_huixiangyiyuan")
    private List<String> graduateHuixiangyiyuan;

    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    @TableField(value = "gmt_modified", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;

    @TableField("applicant_openid")
    private String applicantOpenid;

    @TableField("is_all")
    private Boolean isAll;

}
