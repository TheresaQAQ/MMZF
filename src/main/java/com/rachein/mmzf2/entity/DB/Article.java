package com.rachein.mmzf2.entity.DB;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 文章
 * </p>
 *
 * @author 吴远健
 * @since 2022-09-30
 */
@Data
@TableName("t_article")
@ApiModel(value = "Article对象", description = "文章")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty("articleId")
    private Long id;

    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    @TableField(value = "gmt_modified", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;

    @ApiModelProperty("状态: 0/1 -> 禁用/正常")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("内容（富文本）")
    @TableField("content")
    private String content;

    @ApiModelProperty("作者")
    @TableField("author")
    private String author;

    @ApiModelProperty("推文id号")
    @TableField("draft_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long draftId;

    @ApiModelProperty("活动id号")
    @TableField("activity_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long activityId;

    @ApiModelProperty("封面地址url")
    @TableField("cover_path")
    @JsonProperty("cover")
    private String coverUrl;

    @ApiModelProperty("阅读全文的url")
    @TableField("content_source_url")
    private String contentSourceUrl;

    @ApiModelProperty("图文消息的封面图片素材id（一定是永久MediaID） ")
    @TableField("thumb_media_id")
    private String thumbMediaId;

    @ApiModelProperty("Uint32 是否打开评论，0不打开(默认)，1打开 ")
    @TableField("need_open_comment")
    private Integer needOpenComment;

    @ApiModelProperty("Uint32 是否粉丝才可评论，0所有人可评论(默认)，1粉丝才可评论")
    @TableField("only_fans_can_comment")
    private Integer onlyFansCanComment;

    @ApiModelProperty("是否显示封面，1为显示，0为不显示")
    @TableField("show_cover_pic")
    private Integer showCoverPic;

    @ApiModelProperty("标题")
    private String title;

    private String digest;


}
