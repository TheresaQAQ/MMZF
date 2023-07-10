package com.rachein.mmzf2.core.controller;

import com.rachein.mmzf2.entity.RO.ArticleAddRo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

/**
 * @Author 计算机科学系 吴远健
 * @Date 2022/11/29
 * @Description
 */
@RestController
@Slf4j
public class TestController {

    @PostMapping("api/test")
    public void test(@RequestBody ArticleAddRo ro) {
        log.error(ro.getContent());
        String s1 = HtmlUtils.htmlEscape(ro.getContent());
        log.error(s1);
        String s2 = HtmlUtils.htmlUnescape(s1);
        log.error(s2);
    }
}
