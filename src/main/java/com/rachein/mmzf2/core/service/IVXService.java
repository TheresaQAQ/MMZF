package com.rachein.mmzf2.core.service;

import cn.dev33.satoken.stp.SaTokenInfo;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

/**
 * @Author 计算机科学系 吴远健
 * @Date 2022/10/19
 * @Description
 */
public interface IVXService {

    /**
     * 根据openId进行推送推文
     */
    default Boolean articleSendByOpenId(List<String> openIds) {
        return null;
    }

    /**
     * 群发推文
     */
    default Boolean articleSend() {
        return null;
    }

    /**
     * 发送消息
     *
     * @Param modelId -> 消息模板编号
     * @Param openIds -> 发送对象
     */
    default Boolean messageSendByOpenIds(String modelId, List<String> openIds) {
        return null;
    }

    /**
     * 上传推文封面
     *
     * @param url
     * @return 微信服务器url
     */
    default Boolean articleCoverUpload(String url) {
        return null;
    }

    /**
     * 上传图文所需材料
     *
     * @param url
     * @return 微信服务器url
     */
    default String articleMaterialUpload(File file) {
        return null;
    }

    /**
     * 上传草稿
     *
     * @return media_id
     */
    default String DraftSave(String draftId) {
        return null;
    }

    /**
     * 菜单
     */
    default void menu() {}

    SaTokenInfo getWebACTokenByCode (String code);

    String run(HttpServletRequest request);

}
