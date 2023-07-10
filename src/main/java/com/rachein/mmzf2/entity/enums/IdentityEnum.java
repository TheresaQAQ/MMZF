package com.rachein.mmzf2.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @Author 计算机科学系 吴远健
 * @Date 2022/11/9
 * @Description
 */
@Getter
public enum IdentityEnum {
    I0(0, "未认证"),
    I1(1, "高中生"),
    I2(2, "在读大学生"),
    I3(3, "毕业青年"),
    I4(4, "合作单位");

    @EnumValue
    private final Integer val;

    @JsonValue
    private final String desc;

    IdentityEnum(Integer val, String desc) {
        this.val = val;
        this.desc = desc;
    }




}
