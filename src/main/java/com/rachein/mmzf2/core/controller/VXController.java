package com.rachein.mmzf2.core.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.rachein.mmzf2.core.service.IUserService;
import com.rachein.mmzf2.core.service.IVXService;
import com.rachein.mmzf2.core.service.impl.UserQueue;
import com.rachein.mmzf2.core.service.impl.VXServiceImpl;
import com.rachein.mmzf2.result.Result;
import com.rachein.mmzf2.utils.MessageUtil;
import com.rachein.mmzf2.utils.XmlUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author 华南理工大学 吴远健
 * @Date 2022/9/20
 * @Description
 */
@RestController
@Slf4j
public class VXController {

    @Autowired
    private IUserService userService;

    @Value("${wechat.appid}")
    private String appid;

    @Autowired
    private IVXService vxService;

    @GetMapping("/wechat")
    //微信认证测试
    public String check(String signature, String timestamp, String nonce, String echostr) {
        System.out.println(signature);
        return echostr;
    }

    @PostMapping(value = "/wechat" , produces = {"application/xml;charset=utf-8"})
    public Object doRun(HttpServletRequest request)throws IOException {
        return vxService.run(request);
    }

//    @ApiOperation("返回微信认证所需参数")
//    @GetMapping("/profile")
//    public Result<Map<String, String>> profile() {
//        //appid	是	公众号的唯一标识
//        //获取公众号的appsecret
//        //scope = snsapi_base
//        //response_type	是	返回类型，请填写code
//        Map<String, String> map = new HashMap<>();
//        map.put("appid", appid);
//        map.put("scope", "snsapi_base");
//        map.put("response_type", "code");
//        return Result.success(map);
//    }

    @ApiOperation("用code获取accessToken")
    @PostMapping("/get/access_token")
    public Result<Object> getWebAccessToken(@RequestBody String code, HttpServletRequest request) {
        log.error("换actoken");
        String code2 = code.replace("=", "");
        SaTokenInfo tokenInfo = vxService.getWebACTokenByCode(code2);
        log.info(tokenInfo.toString());
        return Result.success(tokenInfo);
    }

    @GetMapping("/login")
    public boolean login2() {
        StpUtil.login("1");
        return StpUtil.isLogin();
    }



//    @GetMapping("/login")
//    public void login(@RequestParam("url") String url) {
//        vxService.login(url);
//    }

}
