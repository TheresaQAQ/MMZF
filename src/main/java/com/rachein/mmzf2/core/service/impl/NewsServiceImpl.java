package com.rachein.mmzf2.core.service.impl;

import com.rachein.mmzf2.core.service.IUserService;
import com.rachein.mmzf2.entity.DB.News;
import com.rachein.mmzf2.core.mapper.NewsMapper;
import com.rachein.mmzf2.core.service.INewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rachein.mmzf2.entity.DB.User;
import com.rachein.mmzf2.entity.RO.NewsRo;
import com.rachein.mmzf2.entity.enums.DraftMethodEnum;
import com.rachein.mmzf2.utils.MessageUtil;
import com.rachein.mmzf2.utils.ReleaseConditionSelectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 吴远健
 * @since 2023-01-06
 */
@Service
@Transactional
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements INewsService {

    @Value("${msg.model.diy}")
    private String application_msg_template_id;


    @Autowired
    private IUserService userService;

    @Override
    public void release(NewsRo ro) {
        List<String> toUsers = ReleaseConditionSelectUtils.listUserIdsByCondition(ro.getReleaseTag());
        System.out.println(toUsers);
        MessageUtil.diy(ro.getMessage(), toUsers, application_msg_template_id);

    }
}
