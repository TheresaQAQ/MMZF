package com.rachein.mmzf2.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @Author 计算机科学系 吴远健
 * @Date 2022/11/30
 * @Description
 */
@Getter
public enum RiskEnum {
    SENIOR_STUDENT(0, "低风险"),
    JUNIOR_STUDENT(2, "高风险");

    @EnumValue
    private final Integer val;
    @JsonValue
    private final String name;

    RiskEnum(Integer val, String name) {
        this.val = val;
        this.name = name;
    }
}
