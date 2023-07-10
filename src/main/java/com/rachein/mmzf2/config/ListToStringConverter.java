package com.rachein.mmzf2.config;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

/**
 * @Author 计算机科学系 吴远健
 * @Date 2023/1/3
 * @Description
 */
public class ListToStringConverter implements Converter<List<String>, String> {

    @Override
    public String convert(List<String> strings) {
        return StringUtils.join(strings, "-");
    }
}
