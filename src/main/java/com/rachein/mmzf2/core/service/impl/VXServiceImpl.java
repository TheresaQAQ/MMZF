package com.rachein.mmzf2.core.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSON;
import com.rachein.mmzf2.core.service.ISubService;
import com.rachein.mmzf2.core.service.IUserService;
import com.rachein.mmzf2.core.service.IVXService;

import com.rachein.mmzf2.entity.DB.*;
import com.rachein.mmzf2.entity.enums.IdentityEnum;
import com.rachein.mmzf2.entity.enums.RoleEnum;
import com.rachein.mmzf2.entity.enums.StateEnum;
import com.rachein.mmzf2.exception.GlobalException;
import com.rachein.mmzf2.result.CodeMsg;
import com.rachein.mmzf2.utils.AccessTokenUtil;
import com.rachein.mmzf2.utils.HttpRequestUtils;
import com.rachein.mmzf2.utils.MessageUtil;
import com.rachein.mmzf2.utils.XmlUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * @Author 计算机科学系 吴远健
 * @Date 2022/10/27
 * @Description
 */
@Service
@Slf4j
public class VXServiceImpl implements IVXService {

    @Value("${wechat.appid}")
    private String appid;

    @Value("${wechat.msg.application}")
    private String mobanid;

    @Value("${wechat.appsecret}")
    private String appsecret;

    @Value("${path.url.activities}")
    private String activities_url;

    @Value("${URL.client.user.info.fill}")
    private String path_user_fill;

    @Value("${URL.client.manager.apply}")
    private String path_manager_apply;

    @Value("${URL.client.user.info.get}")
    private String path_user_info_get;

    @Autowired
    private ISubService subService;

    @Autowired
    private IUserService userService;

    @Override
    public String run(HttpServletRequest request) {
        String fromUserName;
        String toUserName;
        String reply = null;
        try {
            Map<String, String> map = XmlUtil.getMap(request.getInputStream());
            String msgType = map.get("MsgType");
            fromUserName = map.get("FromUserName");
            toUserName = map.get("ToUserName");
            System.out.println(map);
            if (msgType.equals("event")){
                String event = map.get("Event");
                System.out.println(map);
                switch (event) {
                    case "subscribe":
                        reply = "❤信宜青年系统在此感谢你的订阅hua\n\n<a href=\"" + path_user_fill + "\">✨请完善信息以获取更匹配的信宜活动信息>>></a>";
                        log.info(fromUserName);
                        UserQueue.QUEUE.push(fromUserName);
                        break;
                    case "unsubscribe":
                        log.info("触发不再关注事件");
                        break;
                    case "CLICK":
                        String key = map.get("EventKey");
                        switch (key) {
                            case "sub::yiqing":
                                //疫情防控信息推送：
                                reply = subService.risk();
                                break;
                            case "sub::weather":
                                //疫情防控信息推送：
                                reply = subService.weather();
                                break;
                            case "user::getInfo":
                                //个人信息查询:
                                reply = getUserInfo(fromUserName);
                                break;
                            case "sys::enter":
                                //进入系统：
                                reply = enterSys(fromUserName);
                                break;
                            case "sys::manager-apply":
                                //申请管理员：
                                reply = applyManager(fromUserName);
                                break;
                            case "user::getStatus":
                                //查看当前身份认证的状态:
                                reply = getUserInfoStatus(fromUserName);
                                break;
                            case "sys::help":
                                //帮助文档:
                                reply = getHelp();
                                break;
                            case "user::fill":
                                reply = "点击进行填充或者更新信息哦\n<a href=\"" + path_user_fill + "\">点击我进行填充信息认证，方便推送到更多符合自己的生活!</a>";
                                break;
                        }
                        break;
                }
            }
            else if (msgType.equals("text")) {
                //文本消息：
                String content = map.get("Content");
                if (content.equals("查询信宜未来3小时天气")) {
                    reply = subService.weather_future();
                }
            }
            return MessageUtil.formatMsg(fromUserName, toUserName, 1, "text", reply);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getHelp() {
        return "\uD83C\uDF88信宜系统使用帮助\n" +
                "1、信宜青年在使用本平台之前，为了获取更多更符合自身的信息活动，请点击【信宜青年】中的[填充或更新个人信息认证]，进行身份的认证。\n\n" +
                "2、点击【信宜生活】的天气，可以获取今日的天气状况哦！⛅\n\n" +
                "4、想了解或者报名参加近期活动，或者想查看参加的活动，可以点击【信宜青年】中的活动列表进行查看。\n\n" +
                "5、信宜青年平台为亲爱的信宜市青年量身定做，会不定期地发布一些活动和社区公告推文。☁\n\n" +
                "6、如果想要查看自身的认证的信息，请点击【信宜青年】中的[个人状态]进行查询个人状态，点击[个人信息]可以查看填写或者认证的个人信息哦！\n\n" +
                "7、❤为了更好地建设信宜，共建美好家乡，欢迎广大的信宜青年加入到我们的平台维护中，成为宣传家乡的有志青年！如果有这方面的想法欢迎\n\n" +
                "点击【管理】按钮中的[申请管理员]进行提交认证材料，对待管理员审核即可，发放管理守则和回信。\n\n" +
                "8、对于企业用户，如果想进行宣传企业或者招纳贤士，也请先加入到本平台的管理队伍中，获取发布推文的资格。\n\n" +
                "也请点击【管理】按钮中的[申请管理员]进行提交认证材料，对待管理员审核即可，发放管理守则和回信。\n\n" +
                "9、对于系统管理人员，可以进入系统进行平台的维护与更新，进行推文的编辑与推送！\n\n";
    }

    private String getUserInfoStatus(String openid) {
        User user = userService.lambdaQuery().eq(User::getOpenid, openid)
                .select(User::getState, User::getGmtModified)
                .one();
        if (Objects.isNull(user)) throw new GlobalException(CodeMsg.USER_NOT_FOUND);
        StateEnum state = user.getState();
        //如果没有填充信息
        if (state == StateEnum.NO) {
            return "你还没填充信息哦\n<a href=\"" + path_user_fill + "\">点击我进行填充信息认证，方便推送到更多符合自己的生活!</a>";
        }
        String status = "";
        String msg = "";
        String url = null;
        if (state == StateEnum.PADDING) {
            //设置消息
            status = StateEnum.PADDING.getDescription();
            msg = "别着急哦，请等待审核人员的审核，审核时间大概会在30分钟左右哦！";
            url = path_user_info_get + "?model=preview";
        }
        else if (state == StateEnum.NORMAL) {
            //设置消息
            status = StateEnum.NORMAL.getDescription();
            msg = "亲爱的信宜会员，您已经完成信息认证了!\n信宜青年系统为您推送更符合你的生活！";
            url = path_user_info_get;
        }
        else if (state == StateEnum.REFUSE) {
            //设置消息
            status = StateEnum.REFUSE.getDescription();
            msg = "亲爱的用户，请根据备注进行查看自己提交的信息是否有误，请修改一下再次进行提交即可！";
        }
        //微信发送消息
        MessageUtil.applicationResult("信息审核", status, "无", openid, mobanid, user.getGmtModified(), url);
        return msg;
    }

    private String applyManager(String openid) {
        //先检测是不是管理员：
        try {
            RoleEnum roleId = userService.lambdaQuery().eq(User::getOpenid, openid)
                    .select(User::getRoleId)
                    .one()
                    .getRoleId();
            if (roleId != RoleEnum.COMMON) {
                //如果已经是管理员了：
                return "您已经是信宜系统的管理员了，不能重复申请哦！";
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(CodeMsg.USER_NOT_FOUND);
        }
        //申请管理员
        return "<a href=\"https://baidu.com\">点击进行申请管理员吧！</a>";
    }

    private String enterSys(String openid) {
        //查是否有权限：
        try {
            RoleEnum roleId = userService.lambdaQuery().eq(User::getOpenid, openid).select(User::getRoleId).one().getRoleId();
            if (roleId == RoleEnum.COMMON) {
                //无权限访问
                return "只有系统管理员才可以进入管理系统哦。\n" +
                        "<a href=\"" + path_manager_apply + "\">加入信宜青年公众管理系统，为建设信宜市添加动力</a>";
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(CodeMsg.USER_NOT_FOUND);
        }
        return "欢迎回来!\n<a href=\"https://baidu.com\">点击进入管理系统</a>";
    }


    public String getUserInfo(String openid) {
        Map<String, Object> infoMap = userService.getInfoByOpenid(openid, StateEnum.NORMAL);
        if (Objects.isNull(infoMap.get("body"))) {
            return "您还未进行填写相关信息哦，<a href=\"" + path_user_fill + "\">请点击这里进行补充信息哦</a>";
        }
        //如果是高中生：
        IdentityEnum type = (IdentityEnum) infoMap.get("type");
        log.info(type.getDesc());
        if (type.equals(IdentityEnum.I1)) {
            SeniorStudent data = (SeniorStudent) infoMap.get("body");
            return String.format("个人信息如下：\n" +
                    "身份：高中生\n" +
                    "姓名：%s\n" +
                    "电话号码：%s\n" +
                    "出生年月：%s\n" +
                    "高中毕业年份：%s\n" +
                    "就读学校：%s\n" +
                    "选科方向：%s\n" +
                    "\n" +
                    "如果填错或者要修改更新\n" +
                    "<a href=\"%s\">点我进行修改信息✔</a>\n\n" +
                    "<a href=\"%s\">查看详情</a>\n",
                    data.getNickname(), data.getPhone(), data.getBirthday(), data.getGraduateYear(), data.getSchool(), data.getMajor(), path_user_fill, path_user_info_get);
        }
        else if (type.equals(IdentityEnum.I2)) {
            CollegeStudent data = (CollegeStudent) infoMap.get("body");
            return String.format("个人信息如下：\n" +
                            "身份：在读大学生\n" +
                            "姓名：%s\n" +
                            "籍贯：%s\n" +
                            "政治面貌：%s\n" +
                            "电话号码：%s\n" +
                            "出生年月：%s\n" +
                            "在读高校：%s\n" +
                            "在读专业：%s\n" +
                            "在读阶段：%s\n" +
                            "预毕业年月：%s\n" +
                            "回乡发展意愿：%s\n" +
                            "毕业就业方向：%s\n" +
                            "\n" +
                            "如果填错或者要修改更新\n" +
                            "<a href=\"%s\">点我进行修改信息✔</a>\n\n" +
                            "<a href=\"%s\">查看详情</a>\n",
                            data.getNickname(), data.getAddress(), data.getZhengzhimianmao(), data.getPhone(), data.getBirthday(), data.getSchool(),
                    data.getMajor(), data.getStage(), data.getGraduateDate(), data.getHuixiangyiyuan(), data.getJiuyefangxiang(), path_user_fill, path_user_info_get);
        }
        else if (type.equals(IdentityEnum.I3)) {
            GraduatePerson data = (GraduatePerson) infoMap.get("body");
            return String.format("个人信息如下：\n" +
                            "身份：在读大学生\n" +
                            "姓名：%s\n" +
                            "籍贯：%s\n" +
                            "政治面貌：%s\n" +
                            "电话号码：%s\n" +
                            "出生年月：%s\n" +
                            "毕业院校：%s\n" +
                            "毕业专业：%s\n" +
                            "毕业年份：%s\n" +
                            "婚姻情况：%s\n" +
                            "就业情况：%s\n" +
                            "所在城市：%s\n" +
                            "\n" +
                            "如果填错或者要修改更新\n" +
                            "<a href=\"%s\">点我进行修改信息✔</a>\n\n" +
                            "<a href=\"%s\">查看详情</a>\n",
                            data.getNickname(), data.getAddress(), data.getZhengzhimianmao(), data.getPhone(), data.getBirthday(), data.getSchool(),
                    data.getMajor(), data.getGraduateDate(), data.getHunyin(), data.getJiuyeqingkuang(), data.getAddress(), path_user_fill, path_user_info_get);
        }
        else if (type.equals(IdentityEnum.I4)) {
            Company data = (Company) infoMap.get("body");
            return String.format("个人信息如下：\n" +
                            "身份：单位\n" +
                            "单位名称：%s\n" +
                            "联系电话：%s\n" +
                            "单位所在地：%s\n" +
                            "单位性质：%s\n" +
                            "单位代表：%s\n" +
                            "单位注册时间：%s\n" +
                            "注册资本：%s元\n" +
                            "\n" +
                            "如果填错或者要修改更新\n" +
                            "<a href=\"%s\">点我进行修改信息✔</a>\n\n" +
                            "<a href=\"%s\">查看详情</a>\n",
                            data.getName(), data.getPhone(), data.getAddress(), data.getQuality(),
                    data.getMasterName(), data.getRegisterDate(), data.getZiben(), path_user_fill, path_user_info_get);
        }
        return "您尚未完成信息认证。";
    }


    public SaTokenInfo getWebACTokenByCode (String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appid +"&secret=" + appsecret + "&code=" + code + "&grant_type=authorization_code";
        SaTokenInfo tokenInfo = null;
        try {
            System.out.println(code);
            Response response = HttpRequestUtils.get(url);
            if (Objects.requireNonNull(response).isSuccessful()) {
                String responseBody = Objects.requireNonNull(response.body()).string();
                String openid = JSON.parseObject(responseBody).getString("openid");
                //sa-token登录：
                StpUtil.login(openid);
                tokenInfo = StpUtil.getTokenInfo();
                log.info(openid + "已登录!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new GlobalException(CodeMsg.USER_LOGIN_ERROR);
        }
        return tokenInfo;
    }



    public String uploadCover(File file) {
        String url = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token="+ AccessTokenUtil.getToken() +"&type=thumb";
        Response response = HttpRequestUtils.post(url, file, "file");
        try {
            String response_json = response.body() != null ? response.body().string() : null;
            log.info(response_json);
            return JSON.parseObject(response_json).getString("media_id");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void menu() {
        String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + AccessTokenUtil.getToken();
        String body = String.format("{\"button\":[{\"name\":\"信宜青年\",\"sub_button\":[{\"type\":\"click\",\"name\":\"操作帮助\",\"key\":\"sys::help\"},{\"type\":\"cl" +
                "ick\",\"name\":\"填充或更新信息\",\"key\":\"user::fill\"},{\"type\":\"click\",\"name\":\"查看信息\",\"key\":\"user::getInfo\"},{\"type\":\"c" +
                "lick\",\"name\":\"查看状态\",\"key\":\"user::getStatus\"},{\"type\":\"view\",\"name\":\"活动列表\",\"url\":\"%s\"}]},{\"name\":\"管理\",\"sub_b" +
                "utton\":[{\"type\":\"click\",\"name\":\"申请管理员\",\"key\":\"sys::manager-apply\"},{\"type\":\"click\",\"name\":\"管理系统\",\"key\":" +
                "\"sys::enter\"}]},{\"name\":\"信宜\",\"sub_button\":[{\"type\":\"click\",\"name\":\"茂名今日天气\",\"key\":\"sub::weather\"}]}]}", activities_url);
        try {
            Response response = HttpRequestUtils.post(url, body);
            if (response.isSuccessful()) {
                if (Objects.requireNonNull(response.body()).string().contains("\"errcode\":0")) {
                    log.info("【预加载】菜单设置成功！");
                }
            }
            else {
                throw new GlobalException(CodeMsg.SERVER_ERROR);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String articleMaterialUpload(File file) {
        String url = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=" + AccessTokenUtil.getToken();
        String response_json = "";
        try {
            Response response = HttpRequestUtils.post(url, file, "media");
            response_json = Objects.requireNonNull(response.body()).string();
            log.info("【素材上传】" + response_json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSON.parseObject(response_json).getString("url");
    }
}
