package com.rachein.mmzf2.core.controller;

import com.rachein.mmzf2.core.service.IActivityService;
import com.rachein.mmzf2.entity.DB.Activity;
import com.rachein.mmzf2.entity.RO.ActivityAddRo;
import com.rachein.mmzf2.entity.VO.ActivityVo;
import com.rachein.mmzf2.entity.enums.ActivityTypeEnum;
import com.rachein.mmzf2.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 华南理工大学 吴远健
 * @Date 2022/9/20
 * @Description
 */
@Api(tags = "活动模块")
@RestController
@RequestMapping("activity")
public class ActivityController {

    @Autowired
    private IActivityService activityService;

    @ApiOperation("添加活动")
    @PostMapping("/add")
    public Result<Long> add(@RequestBody ActivityAddRo ro) {
        Long id = activityService.save(ro);
        return Result.success(id);
    }

    @ApiOperation("活动列表")
    @GetMapping("/all")
    public Result<List<ActivityVo>> listActivity() {
        return Result.success(activityService.listA());
    }

    @ApiOperation("【招聘类型】活动列表")
    @GetMapping("/all/zp")
    public Result<List<Activity>> listActivityZP() {
        return Result.success(activityService.listByType(ActivityTypeEnum.ZP));
    }

    @ApiOperation("【招聘类型】活动列表")
    @GetMapping("/all/ot")
    public Result<List<Activity>> listActivityOT() {
        return Result.success(activityService.listByType(ActivityTypeEnum.OT));
    }


    @ApiOperation("列出某活动报名人的列表")
    @GetMapping("/{activity_id}/user")
    public void listUser(@PathVariable("activity_id") Long activityId) {
        activityService.listUser(activityId);
    }

    @ApiOperation("报名活动")
    @PostMapping("/participate")
    public void participation(@RequestParam("activity_id") Long activityId) {

    }
}
