package com.rachein.mmzf2.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * @Author 计算机科学系 吴远健
 * @Date 2023/1/8
 * @Description
 */
public class ImgSrcReplaceUtils {

    private final static String REG = "<img\\s*([^>]*)\\s*src=\\\"(.*?)\\\"\\s*([^>]*)>";

    /**
     * 获取相应路径
     * @param originStr
     * @return
     */
    public static String replace(String originStr, Map<String, String> vxUrls) {
        Pattern pattern = Pattern.compile(REG, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(originStr);
        String content = originStr;
        //找到对应的字符串
        int i = 0;
        while (matcher.find()) {
            //添加图片路径
//            i++;
            String src = matcher.group(2);//group(2)代表正则表达式里面的第二个括号内容，这里是src
            //调用微信上传图文图片接口，返回url：
            System.out.println(src);
            String vxUrl = vxUrls.get(src);
            System.out.println(vxUrl);
            //将这个路径替换
//            content = content.replace(src, vxUrl);
            content = content.replaceAll(src, "http://mmbiz.qpic.cn/mmbiz_png/m4Z70gPC8jJRy5JqyUwcEgRYZZeL3OiazjA54jOBlfoz1ZPugsJN3vrzlIz2XV0oTeyvHt4beKam9p9FD3SBCnQ/0");
//            System.out.println();
        }
//        System.out.println(i);
        return content;
    }
}
