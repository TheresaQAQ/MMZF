package com.rachein.mmzf2.core.controller;

import com.rachein.mmzf2.core.service.IUserService;
import com.rachein.mmzf2.entity.DB.User;
import com.rachein.mmzf2.entity.enums.StateEnum;
import com.rachein.mmzf2.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 华南理工大学 吴远健
 * @Date 2022/9/20
 * @Description
 */
@Api(tags = "用户模块")
@Slf4j
@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @ApiOperation("获取用户info")
    @GetMapping("user/info/{openId}")
    public Result<Object> read(@PathVariable String openId) {
        Object info = userService.getInfoByOpenid(openId, StateEnum.NORMAL).get("body");
        return Result.success(info);
    }

    @ApiOperation("预览提交的信息")
    @GetMapping("user/info/preview/{openId}")
    public Result<Object> preview(@PathVariable String openId) {
        Object info = userService.getInfoByOpenid(openId, null).get("body");
        return Result.success(info);
    }

    @ApiOperation("会员列表")
    @GetMapping("user/all")
    public Result<List<User>> listUser() {
        List<User> users = userService.listUser();
        System.out.println(users);
        return Result.success(users);
    }


}
