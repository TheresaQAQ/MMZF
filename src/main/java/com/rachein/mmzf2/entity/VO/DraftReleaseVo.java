package com.rachein.mmzf2.entity.VO;

import lombok.Data;

/**
 * @Author 计算机科学系 吴远健
 * @Date 2023/1/4
 * @Description
 */
@Data
public class DraftReleaseVo {
    private String draftId;
    private String remark;
    private String state;
    private String applicant;
    private String applicantOpenid;
    private String applicationTime;
}
