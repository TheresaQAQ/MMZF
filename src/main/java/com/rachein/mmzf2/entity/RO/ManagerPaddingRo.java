package com.rachein.mmzf2.entity.RO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Author 计算机科学系 吴远健
 * @Date 2023/2/7
 * @Description
 */
@Data
public class ManagerPaddingRo {
    private Boolean isPass;
    @JsonProperty("id")
    private Long applyId;
    private String checkerOpenid;
    private String remark;
}
