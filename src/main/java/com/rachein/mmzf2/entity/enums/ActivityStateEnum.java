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
public enum ActivityStateEnum {
    NO(0, "banned"),
    PADDING(1, "待审核"),
    NOTSTART(2, "未开始"),
    HADING(3, "进行中"),
    FINISHED(4, "已结束");


    @EnumValue
    private final Integer val;
    @JsonValue
    private final String description;

    ActivityStateEnum(Integer val, String description) {
        this.val = val;
        this.description = description;
    }
}
