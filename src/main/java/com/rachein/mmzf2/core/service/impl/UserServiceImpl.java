package com.rachein.mmzf2.core.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rachein.mmzf2.core.mapper.UserMapper;
import com.rachein.mmzf2.core.mapper.ViewAutoUserFillMapper;
import com.rachein.mmzf2.core.service.*;
import com.rachein.mmzf2.entity.DB.*;
import com.rachein.mmzf2.entity.RO.ManagerApplyRo;
import com.rachein.mmzf2.entity.RO.PaddingReviewRo;
import com.rachein.mmzf2.entity.RO.UserCheckRo;
import com.rachein.mmzf2.entity.VO.UserVo;
import com.rachein.mmzf2.entity.enums.IdentityEnum;
import com.rachein.mmzf2.entity.enums.StateEnum;
import com.rachein.mmzf2.exception.GlobalException;
import com.rachein.mmzf2.result.CodeMsg;
import com.rachein.mmzf2.utils.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author 物联网工程系 ITAEM 唐奕泽
 * @Description
 * @date 2022/9/21 21:03
 */
@Service
@Transactional
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements IUserService {

    @Value("${URL.client.user.info.get}")
    private String path_user_info;

    @Autowired
    private IStudentHighInfoService studentHighInfoService;

    @Autowired
    private IStudentLow3InfoService studentLow3InfoService;

    @Autowired
    private IStudentLow12InfoService studentLow12InfoService;

    @Autowired
    private ViewAutoUserFillMapper viewAutoUserFillMapper;

    @Autowired
    private ISeniorStudentService seniorStudentService;

    @Autowired
    private ICollegeStudentService collegeStudentService;

    @Autowired
    private ICompanyService companyService;

    @Autowired
    private IGraduatePersonService graduatePersonService;


    @Value("${wechat.msg.application}")
    private String mobanid;


    @Override
    public List<UserVo> listUserByIds(List<String> ids) {
        return null;
    }


    @Override
    public List<User> listUser() {
        List<User> list = lambdaQuery().eq(User::getState, StateEnum.NORMAL).list();
        return list;
    }

    @Override
    public void check(UserCheckRo ro) {
        //保存数据库
        lambdaUpdate().eq(User::getOpenid, ro.getOpenid())
                .set(User::getState, ro.getIs()? StateEnum.NORMAL: StateEnum.NO)
                .update();
        //微信发送消息
        MessageUtil.applicationResult("信息审核", ro.getIs()?"通过":"驳回", ro.getRemark(), ro.getOpenid(), mobanid, LocalDateTime.now(), null);
    }

    @Override
    public Map<String, Object> getInfoByOpenid(String openid, StateEnum stateEnum) {
        //封装信息：
        Map<String, Object> res = new HashMap<>();
        //从user表中获取对应的用户
        User user = lambdaQuery().eq(User::getOpenid, openid).select(User::getType, User::getState).one();
        //如果用户还未通过审核，自然没有信息
        log.info(user.toString());
//        if (stateEnum != null || user.getState() != stateEnum) {
//            throw new GlobalException(CodeMsg.USER_NOT_FOUND);
//        }
        /*分类获取信息*/
        //高中生
        if (user.getType() == IdentityEnum.I1) {
            SeniorStudent info = seniorStudentService.lambdaQuery().eq(SeniorStudent::getOpenid, openid).one();
            if (Objects.isNull(info)) throw new GlobalException(CodeMsg.USER_NOT_FOUND);
            res.put("type", IdentityEnum.I1);
            res.put("body", info);
        }
        //大学生
        else if (user.getType() == IdentityEnum.I2) {
            CollegeStudent info = collegeStudentService.lambdaQuery().eq(CollegeStudent::getOpenid, openid).one();
            if (Objects.isNull(info)) throw new GlobalException(CodeMsg.USER_NOT_FOUND);
            log.info(info.toString());
            res.put("type", IdentityEnum.I1);
            res.put("body", info);
        }
        //毕业青年
        else if (user.getType() == IdentityEnum.I3) {
            GraduatePerson info = graduatePersonService.lambdaQuery().eq(GraduatePerson::getOpenid, openid).one();
            if (Objects.isNull(info)) throw new GlobalException(CodeMsg.USER_NOT_FOUND);
            res.put("type", IdentityEnum.I1);
            res.put("body", info);
        }
        //合作单位
        else if (user.getType() == IdentityEnum.I4) {
            Company info = companyService.lambdaQuery().eq(Company::getOpenid, openid).one();
            if (Objects.isNull(info)) throw new GlobalException(CodeMsg.USER_NOT_FOUND);
            res.put("type", IdentityEnum.I1);
            res.put("body", info);
        }
        else {
            throw new GlobalException(CodeMsg.USER_NOT_FOUND);
        }
        return res;
    }

    @Override
    public void updateInfo(SeniorStudent info) {
        //微信登录
        info.setOpenid(StpUtil.getLoginIdAsString());
        //从user表中找到这个用户：
        if (!lambdaQuery().eq(User::getOpenid, info.getOpenid()).exists()) throw new GlobalException(CodeMsg.USER_NOT_FOUND);
        //设置状态为已审核：
        info.setStatus(StateEnum.NORMAL);
        lambdaUpdate().eq(User::getOpenid, info.getOpenid()).set(User::getState, StateEnum.NORMAL).set(User::getType, IdentityEnum.I1).update();
        //保存
        seniorStudentService.saveOrUpdate(info);
        //微信公众号发送消息到指定的账号：
        MessageUtil.applicationResult("信息审核已提交", "已通过", "无", info.getOpenid(), mobanid, LocalDateTime.now(), path_user_info + "?model=preview");
    }

    @Override
    public void updateInfo(GraduatePerson info) {
        //微信登录
        info.setOpenid(StpUtil.getLoginIdAsString());
        //从user表中找到这个用户：
        if (!lambdaQuery().eq(User::getOpenid, info.getOpenid()).exists()) throw new GlobalException(CodeMsg.USER_NOT_FOUND);
        //设置状态为已审核：
        info.setStatus(StateEnum.NORMAL);
        lambdaUpdate().eq(User::getOpenid, info.getOpenid()).set(User::getType, IdentityEnum.I3).set(User::getState, StateEnum.NORMAL).update();
        //保存
        graduatePersonService.saveOrUpdate(info);
//        微信公众号发送消息到指定的账号：
        MessageUtil.applicationResult("信息审核已提交", "已通过", "无", info.getOpenid(), mobanid, LocalDateTime.now(), path_user_info + "?model=preview");
    }

    @Override
    public void updateInfo(CollegeStudent info) {
        info.setOpenid(StpUtil.getLoginIdAsString());
        //从user表中找到这个用户：
        if (!lambdaQuery().eq(User::getOpenid, info.getOpenid()).exists()) throw new GlobalException(CodeMsg.USER_NOT_FOUND);
        //捆绑openid
        //设置状态为已审核：
        info.setStatus(StateEnum.NORMAL);
        lambdaUpdate().eq(User::getOpenid, info.getOpenid()).set(User::getState, StateEnum.NORMAL).set(User::getType, IdentityEnum.I2).update();
        //保存
        collegeStudentService.saveOrUpdate(info);
        //微信公众号发送消息到指定的账号：
        MessageUtil.applicationResult("信息审核已提交", "已通过", "无", info.getOpenid(), mobanid, LocalDateTime.now(), path_user_info + "?model=preview");
    }

    @Override
    public void updateInfo(Company info) {
        //微信登录
        info.setOpenid(StpUtil.getLoginIdAsString());
        //从user表中找到这个用户：
        if (!lambdaQuery().eq(User::getOpenid, info.getOpenid()).exists()) throw new GlobalException(CodeMsg.USER_NOT_FOUND);
        //设置状态为待审核：
        info.setStatus(StateEnum.PADDING);
        lambdaUpdate().eq(User::getOpenid, info.getOpenid()).set(User::getType, IdentityEnum.I4).set(User::getState, StateEnum.PADDING).update();
        //保存
        companyService.saveOrUpdate(info);
        //微信公众号发送消息到指定的账号：
        MessageUtil.applicationResult("信息审核已提交", "等待一级管理员审核", "无", info.getOpenid(), mobanid, LocalDateTime.now(), path_user_info + "?model=preview");
    }

    @Override
    public List listPadding() {
//        List list1 = seniorStudentService.lambdaQuery().eq(SeniorStudent::getStatus, UserStateEnum.PADDING).list();
//        List list2 = collegeStudentService.lambdaQuery().eq(CollegeStudent::getStatus, UserStateEnum.PADDING).list();
//        List list3 = graduatePersonService.lambdaQuery().eq(GraduatePerson::getStatus, UserStateEnum.PADDING).list();
        List list4 = companyService.lambdaQuery().eq(Company::getStatus, StateEnum.PADDING).list();
//        return CollectionUtil.combineO(list1, list2, list3, list4);
        return list4;
    }

    @Override
    public Boolean getStatus(String openid) {
        return lambdaQuery().eq(User::getOpenid, openid)
                .select(User::getIsFillInfo)
                .one()
                .getIsFillInfo();
    }

    @Override
    public void padding(PaddingReviewRo ro) {
        System.out.println(ro.toString());
        //更新用户状态：
        lambdaUpdate().eq(User::getOpenid, ro.getOpenid()).set(User::getState, ro.getIsPass()? StateEnum.NORMAL: StateEnum.REFUSE).update();
        //更新对应的状态
//        if (ro.getUserType() == IdentityEnum.I1) {
//            //senior
//            seniorStudentService.lambdaUpdate().eq(SeniorStudent::getOpenid, ro.getOpenid()).eq(SeniorStudent::getStatus, StateEnum.PADDING)
//                    .set(SeniorStudent::getStatus, ro.getIsPass()? StateEnum.NORMAL: StateEnum.REFUSE).update();
//        }
//        else if (ro.getUserType() == IdentityEnum.I2) {
//            //college
//            collegeStudentService.lambdaUpdate().eq(CollegeStudent::getOpenid, ro.getOpenid()).eq(CollegeStudent::getStatus, StateEnum.PADDING)
//                    .set(CollegeStudent::getStatus, ro.getIsPass()? StateEnum.NORMAL: StateEnum.REFUSE).update();
//        }
//        else if (ro.getUserType() == IdentityEnum.I3) {
//            //graduate
//            graduatePersonService.lambdaUpdate().eq(GraduatePerson::getOpenid, ro.getOpenid()).eq(GraduatePerson::getStatus, StateEnum.PADDING)
//                    .set(GraduatePerson::getStatus, ro.getIsPass()? StateEnum.NORMAL: StateEnum.REFUSE).update();
//        }
//        else
            if (ro.getUserType() == IdentityEnum.I4) {
            //company
            companyService.lambdaUpdate().eq(Company::getOpenid, ro.getOpenid()).eq(Company::getStatus, StateEnum.PADDING)
                    .set(Company::getStatus, ro.getIsPass()? StateEnum.NORMAL: StateEnum.REFUSE).update();
        }
        //发消息
        MessageUtil.applicationResult("信息审核", ro.getIsPass()?"通过":"驳回", ro.getRemark(), ro.getOpenid(), mobanid, LocalDateTime.now(), null);
    }

    @Override
    public void managerApply(ManagerApplyRo ro) {

    }

}
