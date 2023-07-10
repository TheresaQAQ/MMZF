package com.rachein.mmzf2.core.controller;

import com.rachein.mmzf2.core.service.INewsService;
import com.rachein.mmzf2.entity.RO.NewsRo;
import com.rachein.mmzf2.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 吴远健
 * @since 2023-01-06
 */
@RestController
public class NewsController {

    @Autowired
    private INewsService newsService;

    @PostMapping("news/release")
    @ApiOperation("发布消息")
    public Result<Object> release(@RequestBody NewsRo ro) {
        newsService.release(ro);
        return Result.success("发布成功！");
    }
}
