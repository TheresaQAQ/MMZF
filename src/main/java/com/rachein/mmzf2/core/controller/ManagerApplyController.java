package com.rachein.mmzf2.core.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.rachein.mmzf2.core.service.IManagerApplyService;
import com.rachein.mmzf2.entity.DB.ManagerApply;
import com.rachein.mmzf2.entity.RO.ManagerApplyRo;
import com.rachein.mmzf2.entity.RO.ManagerPaddingRo;
import com.rachein.mmzf2.entity.enums.StateEnum;
import com.rachein.mmzf2.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 吴远健
 * @since 2023-02-05
 */
@RestController
@RequestMapping("manager")
@Api(tags = "管理员")
public class ManagerApplyController {

    @Autowired
    private IManagerApplyService managerApplyService;

    @ApiOperation("【申请】管理员")
    @PostMapping("/apply")
    public Result<Object> apply(@RequestBody ManagerApplyRo ro) {
        ro.setUserId(StpUtil.getLoginIdAsString());
        managerApplyService.add(ro);
        return Result.success("申请成功！请等待一级管理员的审核。");
    }

    @ApiOperation("【通过】管理员申请")
    @PostMapping("/padding")
    public Result<Object> pass(@RequestBody ManagerPaddingRo ro) {
        managerApplyService.padding(ro);
        return Result.success("审核成功！");
    }

    @ApiOperation("【查询】管理员申请列表")
    @GetMapping("/apply/list")
    public Result<List<ManagerApply>> list() {
        List<ManagerApply> list = managerApplyService.lambdaQuery().eq(ManagerApply::getState, StateEnum.PADDING).list();
        return Result.success(list);
    }

    @ApiOperation("【查询】管理员审核日志列表")
    @GetMapping("/log/list")
    public Result<List<ManagerApply>> log() {
        List<ManagerApply> list = managerApplyService.lambdaQuery().eq(ManagerApply::getState, StateEnum.NORMAL).or().eq(ManagerApply::getState, StateEnum.REFUSE).list();
        return Result.success(list);
    }

}
