package com.rachein.mmzf2.entity.RO;

import com.rachein.mmzf2.entity.DB.ReleaseTag;
import com.rachein.mmzf2.entity.DTO.vx.msg.DiyMessage;
import lombok.Data;

import java.util.List;

/**
 * @Author 计算机科学系 吴远健
 * @Date 2023/1/6
 * @Description
 */
@Data
public class NewsRo {
    private DiyMessage message;
    private String modelId;
    private Boolean isAll;
    private ReleaseTag releaseTag;
}
