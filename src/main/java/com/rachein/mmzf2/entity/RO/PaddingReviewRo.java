package com.rachein.mmzf2.entity.RO;

import com.rachein.mmzf2.entity.enums.IdentityEnum;
import lombok.Data;

/**
 * @Author 计算机科学系 吴远健
 * @Date 2022/12/25
 * @Description 审核RO类
 */
@Data
public class PaddingReviewRo {
    private String openid;
    private String remark;
    private Boolean isPass;
    private IdentityEnum userType;
    private String checker;
}
