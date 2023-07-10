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
public enum RoleEnum {
    COMMON(0, "会员"),
    MANAGERII(1, "二级管理员"),
    MANAGERI(2, "一级管理员");

    @EnumValue
    private final Integer val;
    @JsonValue
    private final String name;

    RoleEnum(Integer val, String name) {
        this.val = val;
        this.name = name;
    }
}
