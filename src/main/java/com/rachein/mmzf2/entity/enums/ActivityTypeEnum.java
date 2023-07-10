package com.rachein.mmzf2.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @Author 计算机科学系 吴远健
 * @Date 2022/10/4
 * @Description
 */
@Getter
public enum ActivityTypeEnum {
    OT(0, "其他类型"),
    ZP(1, "招聘类型");

    @EnumValue
    private final Integer val;
    @JsonValue
    private final String description;

    ActivityTypeEnum(Integer val, String description) {
        this.val = val;
        this.description = description;
    }
}
