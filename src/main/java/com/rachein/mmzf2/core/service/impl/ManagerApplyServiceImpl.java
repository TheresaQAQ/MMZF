package com.rachein.mmzf2.core.service.impl;

import com.rachein.mmzf2.core.service.IUserService;
import com.rachein.mmzf2.entity.DB.ManagerApply;
import com.rachein.mmzf2.core.mapper.ManagerApplyMapper;
import com.rachein.mmzf2.core.service.IManagerApplyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rachein.mmzf2.entity.DB.User;
import com.rachein.mmzf2.entity.RO.ManagerApplyRo;
import com.rachein.mmzf2.entity.RO.ManagerPaddingRo;
import com.rachein.mmzf2.entity.enums.IdentityEnum;
import com.rachein.mmzf2.entity.enums.RoleEnum;
import com.rachein.mmzf2.entity.enums.StateEnum;
import com.rachein.mmzf2.utils.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 吴远健
 * @since 2023-02-05
 */
@Service
@Slf4j
@Transactional
public class ManagerApplyServiceImpl extends ServiceImpl<ManagerApplyMapper, ManagerApply> implements IManagerApplyService {

    @Autowired
    private IUserService userService;

    @Override
    public void add(ManagerApplyRo ro) {
        ManagerApply managerApply = new ManagerApply();
        managerApply.setState(StateEnum.PADDING);
        managerApply.setOpenid(ro.getUserId());
        managerApply.setType(ro.getType());
        managerApply.setFile(ro.getFileUrl());
        log.info("【添加】管理员申请");
        save(managerApply);
        //发送审核信息给申请人
        MessageUtil.applicationResult("管理员认证审核", "等待审核", "无", ro.getUserId(), LocalDateTime.now(), null);
    }

    @Override
    public void padding(ManagerPaddingRo ro) {
        log.info(ro.toString());
        //数据库表中根据申请的编号进行查询:
        lambdaUpdate().eq(ManagerApply::getId, ro.getApplyId())
                .set(ManagerApply::getState, ro.getIsPass() ? StateEnum.NORMAL : StateEnum.REFUSE)
                .set(ManagerApply::getCheckerOpenid, ro.getCheckerOpenid())
                .set(ManagerApply::getRemark, ro.getRemark())
                .update();
        log.info("【审核】管理员");
        //查询对应的申请人id：
        ManagerApply managerApply = lambdaQuery().eq(ManagerApply::getId, ro.getApplyId())
                .select(ManagerApply::getOpenid, ManagerApply::getType).one();
        //更新user表的用户身份
        userService.lambdaUpdate().eq(User::getOpenid, managerApply.getOpenid()).set(ro.getIsPass(), User::getManagerId, managerApply.getType()).update();
        //发送审核信息给申请人
        MessageUtil.applicationResult("管理员认证审核", ro.getIsPass() ? "通过" : "驳回", ro.getRemark(), managerApply.getOpenid(), LocalDateTime.now(), null);
    }
}
