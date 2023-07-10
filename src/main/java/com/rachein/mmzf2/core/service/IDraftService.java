package com.rachein.mmzf2.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rachein.mmzf2.entity.DB.Draft;
import com.rachein.mmzf2.entity.DB.DraftReleaseLog;
import com.rachein.mmzf2.entity.DB.ReleaseTag;
import com.rachein.mmzf2.entity.RO.DraftCheckRo;

import java.util.List;
import java.util.Map;

/**
 * @Author 华南理工大学 吴远健
 * @Date 2022/9/21
 * @Description
 */
public interface IDraftService extends IService<Draft> {

    void application(ReleaseTag ro);

    void check(DraftCheckRo ro);

    List<DraftReleaseLog> listApplication();

    Map<String, Object> listTag(Long draftId);

    List<Map<String, Object>> listDrawing();

    Map<String, Object> add();

    void getPublicURL(String publishId);

}
