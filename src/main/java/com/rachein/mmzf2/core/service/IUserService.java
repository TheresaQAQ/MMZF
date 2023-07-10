package com.rachein.mmzf2.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rachein.mmzf2.entity.DB.*;
import com.rachein.mmzf2.entity.RO.ManagerApplyRo;
import com.rachein.mmzf2.entity.RO.PaddingReviewRo;
import com.rachein.mmzf2.entity.RO.UserCheckRo;
import com.rachein.mmzf2.entity.VO.UserVo;
import com.rachein.mmzf2.entity.enums.StateEnum;

import java.util.List;
import java.util.Map;

/**
 * @Author 华南理工大学 吴远健
 * @Date 2022/9/20
 * @Description
 */
public interface IUserService extends IService<User> {

    List<UserVo> listUserByIds(List<String> ids);

    List<User> listUser();

    void check(UserCheckRo ro);

    Map<String, Object> getInfoByOpenid(String openid, StateEnum stateEnum);

    void updateInfo(SeniorStudent info);

    void updateInfo(GraduatePerson info);

    void updateInfo(CollegeStudent info);

    void updateInfo(Company info);

    List<Object> listPadding();

    Boolean getStatus(String openid);

    void padding(PaddingReviewRo ro);

    void managerApply(ManagerApplyRo ro);
}
