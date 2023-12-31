package com.rachein.mmzf2.config;

import com.rachein.mmzf2.core.service.IArticleService;
import com.rachein.mmzf2.core.service.ISubService;
import com.rachein.mmzf2.entity.DB.Article;
import com.rachein.mmzf2.entity.DTO.api.Weather;
import com.rachein.mmzf2.entity.DTO.sub.WeatherDTO;
import com.rachein.mmzf2.redis.RedisService;
import com.rachein.mmzf2.redis.myPrefixKey.ArticleKey;
import com.rachein.mmzf2.utils.WeatherUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @Author 计算机科学系 吴远健
 * @Date 2022/10/9
 * @Description 定时器
 */
@Component
@Slf4j
public class ScheduleTime {

    @Autowired
    private RedisService redisService;
    @Autowired
    private IArticleService articleService;

    @Autowired
    private ISubService subService;
    /**
     * redis mysql 同步数据
     */
    @Scheduled(cron = "0/5 * * * * ? ")
    public void redisMysqlSync() {
        //推文的文章同步:
        articleSync();
    }

    /**
     * 推文同步
     */
    private void articleSync() {
        //从集合中获取任务
        Set<String> ids = redisService.getKeysByPrefix("ArticleKey:" + ArticleKey.PREFIX);
        //用来收集对应的article缓冲对象
//        log.info("开始数据同步!>>");
        if (ids.size() != 0) {
            //从redis中读取
            for (String articleId : ids) {
                Article article = redisService.get(ArticleKey.getById, articleId, Article.class);
                log.info(article.toString());
                redisService.delete(ArticleKey.getById, articleId); //这个删除很关键
                articleService.updateById(article);
            }
            //同步到mysql中
            log.info("文章更新同步到mysql成功！");
        }
    }

    /**
     * 天气实时获取
     */
    private void weather() {
       subService.weather();
    }

}
