package com.rachein.mmzf2.core.service.impl;

import com.rachein.mmzf2.core.mapper.ActivityMapper;
import com.rachein.mmzf2.core.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rachein.mmzf2.entity.DB.Activity;
import com.rachein.mmzf2.entity.DB.Article;
import com.rachein.mmzf2.entity.DB.TableHead;
import com.rachein.mmzf2.entity.DB.UserActivity;
import com.rachein.mmzf2.entity.RO.ActivityAddRo;
import com.rachein.mmzf2.entity.VO.ActivityVo;
import com.rachein.mmzf2.entity.VO.ArticleVo;
import com.rachein.mmzf2.entity.enums.ActivityStateEnum;
import com.rachein.mmzf2.entity.enums.ActivityTypeEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 活动 服务实现类
 * </p>
 *
 * @author 吴远健
 * @since 2022-09-30
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements IActivityService {

    @Autowired
    private ITableHeadVoService tableHeadVoService;

    @Autowired
    private IUserActivityService userActivityService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IArticleService articleService;

    @Override
    public Long save(ActivityAddRo ro) {
        //保存到mysql中：
        Activity activity = new Activity();
        BeanUtils.copyProperties(ro, activity);
        //设置状态
        activity.setStatus(ActivityStateEnum.PADDING);
        //绑定文章id
        saveOrUpdate(activity);
        return activity.getId();
    }

    @Override
    public Map<String, Object> listUser(Long activityId) {
        Map<String, Object> map = new HashMap<>();
        //head:
        List<TableHead> head = tableHeadVoService.lambdaQuery()
                .eq(TableHead::getBelong, "activity::user::list")
                .list();
        //body:
        List<UserActivity> body = userActivityService.lambdaQuery()
                .select(UserActivity::getGender, UserActivity::getTime_application, UserActivity::getOpenId, UserActivity::getPhone)
                .list();
        map.put("head", head);
        map.put("body", body);
        return map;
    }

    @Override
    public List<ActivityVo> listA() {
        //先从数据库查询
        return lambdaQuery().select(Activity::getId, Activity::getParticipateTimeEnd, Activity::getParticipateTimeStart, Activity::getStatus,
                Activity::getTimeEnd, Activity::getParticipateTimeStart, Activity::getArticleId, Activity::getTitle, Activity::getAddress, Activity::getTimeStart)
                .list()
                .stream().map(o -> {
                    ActivityVo vo = new ActivityVo();
                    BeanUtils.copyProperties(o, vo);
                    //从article中查询信息
//                    vo.setPhoto(articleService.lambdaQuery().eq(Article::getId, o.getArticleId()).select(Article::getCoverPath).one().getCoverPath());
                    return vo;
                }).collect(Collectors.toList());
    }

    @Override
    public List<Activity> listByType(ActivityTypeEnum type) {
        List<Activity> list = lambdaQuery().eq(Activity::getType, type).list();
        return list;
    }
}
