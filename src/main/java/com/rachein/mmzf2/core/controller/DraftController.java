package com.rachein.mmzf2.core.controller;

import com.rachein.mmzf2.core.service.IArticleService;
import com.rachein.mmzf2.core.service.IDraftService;
import com.rachein.mmzf2.entity.DB.Draft;
import com.rachein.mmzf2.entity.DB.DraftReleaseLog;
import com.rachein.mmzf2.entity.DB.ReleaseTag;
import com.rachein.mmzf2.entity.RO.DraftCheckRo;
import com.rachein.mmzf2.entity.VO.ArticleVo;
import com.rachein.mmzf2.entity.enums.DraftStateEnum;
import com.rachein.mmzf2.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author 计算机科学系 吴远健
 * @Date 2022/11/7
 * @Description
 */
@Api(tags = "推文模块")
@RestController
public class DraftController {

    @Autowired
    private IDraftService draftService;

    @Autowired
    private IArticleService articleService;

    @ApiOperation("创建推文")
    @GetMapping("/draft/add")
    private Result<Map<String, Object>> draftCreate() {
        Map<String, Object> map = draftService.add();
        return Result.success(map);
    }

    @ApiOperation("查看某一草稿的文章粗略")
    @GetMapping("draft")
    public Result<List<ArticleVo>> listArticleByDraftId(@RequestParam("draftId") Long draftId) {
        List<ArticleVo> articleVos = articleService.listArticleByDraftId(draftId);
        return Result.success(articleVos);
    }

    @ApiOperation("获取推文申请列表")
    @GetMapping("/draft/release/log")
    public Result<List<DraftReleaseLog>> listApplication() {
        return Result.success(draftService.listApplication());
    }

    @ApiOperation("获取推文的状态")
    @GetMapping("/draft/state")
    public Result<Object> getState(@RequestParam Long draftId) {
        DraftStateEnum state = draftService.lambdaQuery().eq(Draft::getId, draftId).select(Draft::getState).one().getState();
        return Result.success(state);
    }

    @ApiOperation("推文草稿箱获取")
    @GetMapping("/draft/all")
    public Result<List<Map<String, Object>>> list() {
        List<Map<String, Object>> ans = draftService.listDrawing();
        return Result.success(ans);
    }

    @ApiOperation("【申请发布】推文")
    @PostMapping("draft/publish/application")
    public Result<String> application(@RequestBody ReleaseTag ro) {
        draftService.application(ro);
        return Result.success("申请成功！请等待一级管理员审核。3s后为您自动跳转到状态页面");
    }

    @ApiOperation("获取推文对应发布的标签")
    @GetMapping("draft/tag/{draft_id}")
    public Result<Map<String, Object>> listTag(@PathVariable("draft_id") Long draftId) {
        Map<String, Object> map = draftService.listTag(draftId);
        return Result.success(map);
    }

    @ApiOperation("管理员审核推文")
    @PostMapping("draft/public/check")
    public Result<Object> check(@RequestBody DraftCheckRo ro) {
        draftService.check(ro);
        return Result.success("success");
    }

    @ApiOperation("删除整个推文【根据推文id】")
    @GetMapping("draft/remove/{draft_id}")
    public Result<Object> removeDraft(@PathVariable("draft_id") Long draftId) {
        articleService.removeDraftByDraftId(draftId);
        return Result.success("删除成功");
    }


}
