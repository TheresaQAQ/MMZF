package com.rachein.mmzf2.utils;

import com.rachein.mmzf2.entity.DTO.vx.msg.DiyMessage;
import com.rachein.mmzf2.entity.DTO.vx.msg.NewsContentDTO;
import com.rachein.mmzf2.entity.DTO.vx.msg.NewsContentDetailsDTO;
import com.rachein.mmzf2.entity.DTO.vx.msg.NewsReleaseDTO;
import okhttp3.Response;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * 格式xml文件
 */
public class MessageUtil {

    public static String padding_model_id;

    /**
     * 格式化消息
     * @param toUser
     * @param fromUser
     * @param createTime
     * @param msgType
     * @param content
     * @return
     */
    public static String formatMsg(String toUser, String fromUser, int createTime, String msgType, String content) {
        String str = "<xml>" +
                "  <ToUserName><![CDATA[%s]]></ToUserName>" +
                "  <FromUserName><![CDATA[%s]]></FromUserName>" +
                "  <CreateTime>%d</CreateTime>" +
                "  <MsgType><![CDATA[%s]]></MsgType>" +
                "  <Content><![CDATA[%s]]></Content>" +
                "</xml>";

                //<xml>
                //  <ToUserName><![CDATA[toUser]]></ToUserName>
                //  <FromUserName><![CDATA[fromUser]]></FromUserName>
                //  <CreateTime>1348831860</CreateTime>
                //  <MsgType><![CDATA[text]]></MsgType>
                //  <Content><![CDATA[this is a test]]></Content>
                //  <MsgId>1234567890123456</MsgId>
                //  <MsgDataId>xxxx</MsgDataId>
                //  <Idx>xxxx</Idx>
                //</xml>

        return String.format(str, toUser, fromUser, createTime, msgType, content);
    }


    public static void applicationResult(String type, String result, String remark, String toUser, String templateId, LocalDateTime dateTime, String link) {
        NewsContentDTO contentDTO = new NewsContentDTO(new NewsContentDetailsDTO(type),
                new NewsContentDetailsDTO(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm").format(dateTime)),
                new NewsContentDetailsDTO(result),
                new NewsContentDetailsDTO( remark));
        //
        NewsReleaseDTO releaseDTO = new NewsReleaseDTO();
        releaseDTO.setUrl(link);
        releaseDTO.setTouser(toUser);
        releaseDTO.setTemplate_id(templateId);
        releaseDTO.setData(contentDTO);
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + AccessTokenUtil.getToken();
//        System.out.println(releaseDTO);
        try {
            Response post = HttpRequestUtils.post(url, releaseDTO);
            String string = post.body().string();
//            System.out.println(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void applicationResult(String type, String result, String remark, String toUser, LocalDateTime dateTime, String link) {
        NewsContentDTO contentDTO = new NewsContentDTO(new NewsContentDetailsDTO(type),
                new NewsContentDetailsDTO(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm").format(dateTime)),
                new NewsContentDetailsDTO(result),
                new NewsContentDetailsDTO( remark));
        //
        NewsReleaseDTO releaseDTO = new NewsReleaseDTO();
        releaseDTO.setUrl(link);
        releaseDTO.setTouser(toUser);
        releaseDTO.setTemplate_id(padding_model_id);
        releaseDTO.setData(contentDTO);
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + AccessTokenUtil.getToken();
//        System.out.println(releaseDTO);
        try {
            Response post = HttpRequestUtils.post(url, releaseDTO);
            String string = post.body().string();
//            System.out.println(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void diy(DiyMessage message, List<String> toUsers, String modelId) {
        //内容本体
        NewsContentDTO contentDTO = new NewsContentDTO(
                new NewsContentDetailsDTO(message.getFirstData()),
                new NewsContentDetailsDTO(message.getDescription()),
                new NewsContentDetailsDTO(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm\n").format(LocalDateTime.now())),
                new NewsContentDetailsDTO(message.getContent()),
                new NewsContentDetailsDTO(message.getRemark())
        );
        //
        NewsReleaseDTO releaseDTO = new NewsReleaseDTO();
        releaseDTO.setUrl(message.getUrl());
        releaseDTO.setTemplate_id(modelId);
        releaseDTO.setData(contentDTO);
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + AccessTokenUtil.getToken();
        toUsers.forEach(user -> {
            releaseDTO.setTouser(user);
            Response post = HttpRequestUtils.post(url, releaseDTO);
        });

    }

}
