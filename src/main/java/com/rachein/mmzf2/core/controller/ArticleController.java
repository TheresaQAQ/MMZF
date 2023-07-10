package com.rachein.mmzf2.core.controller;

import com.rachein.mmzf2.core.service.IArticleService;
import com.rachein.mmzf2.core.service.IDraftService;
import com.rachein.mmzf2.entity.DB.Article;
import com.rachein.mmzf2.entity.RO.ArticleAddRo;
import com.rachein.mmzf2.entity.RO.UpdateAuthorRo;
import com.rachein.mmzf2.entity.RO.UpdateTitleRo;
import com.rachein.mmzf2.entity.RO.UploadNetImgRo;
import com.rachein.mmzf2.entity.VO.ArticleInfoVo;
import com.rachein.mmzf2.entity.VO.FileVo;
import com.rachein.mmzf2.entity.VO.WangEResultVo;
import com.rachein.mmzf2.exception.GlobalException;
import com.rachein.mmzf2.result.CodeMsg;
import com.rachein.mmzf2.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author 华南理工大学 吴远健
 * @Date 2022/9/20
 * @Description
 */
@Api(tags = "图文模块")
@RestController
class ArticleController {

    @Autowired
    private IArticleService articleService;

    @Autowired
    private IDraftService draftService;

    @ApiOperation(value = "上传推文封面", notes = "最近更新：永久图片素材新增后，将带有 URL 返回给开发者，开发者可以在腾讯系域名内使用（腾讯系域名外使用，图片将被屏蔽）。\n" +
            "缩略图（thumb）：64KB，支持 JPG 格式")
    @PostMapping("article/cover/upload/{articleId}")
    public Result<Map<String, String>> uploadCover(MultipartFile file, @PathVariable("articleId") Long articleId) {
        FileVo vo = articleService.coverUpload(file, articleId);
        Map<String, String> map = new HashMap<>();
        map.put("url", vo.getUrl());
        return Result.success("更换封面成功！",map);
    }

    @ApiOperation(value = "上传图文消息内的图片获取URL【订阅号与服务号认证后均可用】",
            tags = "请注意，本接口所上传的图片不占用公众号的素材库中图片数量的5000个的限制。图片仅支持jpg/png格式，大小必须在1MB以下。")
    @PostMapping("article/material/upload/{articleId}")
    public WangEResultVo uploadContentMaterial(MultipartFile file, @PathVariable Long articleId) {
        FileVo vo = articleService.materialUpload(file, articleId);
        WangEResultVo resultVo = new WangEResultVo();
        resultVo.setData(vo);
        return resultVo;
    }

    @ApiOperation("下载网络图片并且上传到微信服务器中")
    @PostMapping("article/material/uploadNet")
    public Result<String> downloadNetImg(@RequestBody UploadNetImgRo ro) {
        String netUrl = articleService.materialUpload(ro);
        return Result.success("success", netUrl);
    }

    @ApiOperation("追加文章")
    @PostMapping("article/add")
    public Result<Article> create(@RequestBody ArticleAddRo ro) {
        Article article = articleService.createArticle(ro);
        return Result.success(article);
    }

    @ApiOperation("删除草稿中的文章【根据文章的id】")
    @GetMapping("article/remove/{article_id}")
    public Result<Object> removeArticle(@PathVariable("article_id") Long articleId) {
        articleService.removeArticleById(articleId);
        return Result.success("删除成功！");
    }

    @ApiOperation("更新草稿箱中的文章")
    @PostMapping("article/content/update")
    public Result<String> update(@RequestBody ArticleAddRo updateRo) {
        articleService.updateContentById(updateRo);
        return Result.success("更新草稿成功！");
    }

    @ApiOperation("获取文章详情")
    @GetMapping("article")
    public Result<ArticleInfoVo> getArticleInfo(@RequestParam String articleId) {
        ArticleInfoVo articleInfoVo = articleService.getArticleInfoById(articleId);
        return Result.success(articleInfoVo);
    }

    @ApiOperation("更改文章标题")
    @PostMapping("article/title/update")
    public Result<Object> updateTile(@RequestBody UpdateTitleRo ro) {
        if (ro.getArticleId() == null) {
            throw new GlobalException(CodeMsg.BIND_ERROR);
        }
        articleService.updateTitle(ro.getArticleId(), ro.getTitle());
        return Result.success("标题修改成功");
    }

    @ApiOperation("更改作者")
    @PostMapping("article/author/update")
    public Result<Object> updateAuthor(@RequestBody UpdateAuthorRo ro) {
        articleService.updateAuthor(ro.getArticle_id(), ro.getAuthor());
        return Result.success("作者更改成功");
    }

}
