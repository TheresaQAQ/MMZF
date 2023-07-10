package com.rachein.mmzf2.core.controller;

import com.rachein.mmzf2.core.service.IUserService;
import com.rachein.mmzf2.entity.DB.*;
import com.rachein.mmzf2.entity.RO.ManagerApplyRo;
import com.rachein.mmzf2.entity.RO.PaddingReviewRo;
import com.rachein.mmzf2.entity.RO.UserCheckRo;
import com.rachein.mmzf2.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author 计算机科学系 吴远健
 * @Date 2022/12/25
 * @Description
 */
@RestController
@Api(tags = "审核模块")
@Slf4j
public class PaddingController {

    @Autowired
    private IUserService userService;

    @ApiOperation("列出用户申请名单")
    @GetMapping("padding/user/list")
    public Result<List<Object>> listUsers() {
        List<Object> padding = userService.listPadding();
        return Result.success(padding);
    }

//    @ApiOperation("获取申请的名单")
//    @GetMapping("user/padding")
//    public Result<List<User>> listPadding() {
//        List<User> padding = userService.listPadding();
//        return Result.success("success", padding);
//    }

    @ApiOperation("填充信息【高中生】")
    @PostMapping("user/info/fill/senior")
    public Result<Object> fillInfo(@RequestBody SeniorStudent info) {
        userService.updateInfo(info);
        return Result.success("提交成功！");
    }

    @ApiOperation("填充信息【在读大学生】")
    @PostMapping("user/info/fill/college")
    public Result<Object> fillInfo(@RequestBody CollegeStudent info) {
        userService.updateInfo(info);
        return Result.success("提交成功！");
    }

    @ApiOperation("填充信息【毕业学生】")
    @PostMapping("user/info/fill/graduate")
    public Result<Object> fillInfo(@RequestBody GraduatePerson info) {
//        log.info(info.toString());
        userService.updateInfo(info);
        return Result.success("提交成功！");

    }

    @ApiOperation("填充信息【单位】")
    @PostMapping("user/info/fill/company")
    public Result<Object> fillInfo(@RequestBody Company info) {
//        log.info(info.toString());
        userService.updateInfo(info);
        return Result.success("提交成功！");
    }

    @ApiOperation("【用户】入会审核")
    @PostMapping("padding/user")
    public Result<String> userPass(@RequestBody PaddingReviewRo ro) {
        log.info(ro.toString());
        userService.padding(ro);
        return Result.success("审核成功！");
    }

//    @ApiOperation("【管理员申请】")
//    @PostMapping("apply/manager")
//    public void managerPass(@RequestBody ManagerApplyRo ro) {
//        userService.managerApply(ro);
//    }


}
