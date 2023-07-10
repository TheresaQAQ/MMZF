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
public enum StateEnum {
    NO(0, "未进行"),
    PADDING(1, "待审核"),
    REFUSE(2, "驳回"),
    NORMAL(3, "通过");


    @EnumValue
    private final Integer val;
    @JsonValue
    private final String description;

    StateEnum(Integer val, String description) {
        this.val = val;
        this.description = description;
    }
}
