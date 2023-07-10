package com.rachein.mmzf2.entity.RO;

import com.rachein.mmzf2.entity.enums.RoleEnum;
import lombok.Data;

/**
 * @Author 计算机科学系 吴远健
 * @Date 2023/2/5
 * @Description
 */
@Data
public class ManagerApplyRo {
    private String fileUrl;
    private String userId;
    private RoleEnum type;
}
