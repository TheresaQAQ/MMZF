package com.rachein.mmzf2.core.service;

import com.rachein.mmzf2.entity.DB.News;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rachein.mmzf2.entity.RO.NewsRo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 吴远健
 * @since 2023-01-06
 */
public interface INewsService extends IService<News> {

    void release(NewsRo ro);
}
