package com.rachein.mmzf2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @Author 华南理工大学 吴远健
 * @Date 2022/9/26
 * @Description
 */
@Configuration
public class ResourceConfig implements WebMvcConfigurer {

    @Value("${path.file.local}")
    private String path;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("res/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("i/**").addResourceLocations("file:" + path);
    }

    /***
     * 扩展MVC框架的消息转换器
     * @param converters
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        //创建消息转化器对象
        MappingJackson2HttpMessageConverter messageConverter=new MappingJackson2HttpMessageConverter();
        //设置对象转换器，底层使用Jackson将java对象转为json
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        //将上面的消息转换器对象追加到mvc框架的转换器容器中(IOC)
        converters.add(0,messageConverter);
    }
}