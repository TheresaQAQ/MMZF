package com.rachein.mmzf2.core.service;

import com.rachein.mmzf2.entity.DB.Activity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rachein.mmzf2.entity.RO.ActivityAddRo;
import com.rachein.mmzf2.entity.VO.ActivityVo;
import com.rachein.mmzf2.entity.VO.ArticleVo;
import com.rachein.mmzf2.entity.enums.ActivityTypeEnum;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 活动 服务类
 * </p>
 *
 * @author 吴远健
 * @since 2022-09-30
 */
public interface IActivityService extends IService<Activity> {

    Long save(ActivityAddRo ro);

    Map<String, Object> listUser(Long activityId);

    List<ActivityVo> listA();

    List<Activity> listByType(ActivityTypeEnum zp);
}
