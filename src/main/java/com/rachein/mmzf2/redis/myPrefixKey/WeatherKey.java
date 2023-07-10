package com.rachein.mmzf2.redis.myPrefixKey;

import com.rachein.mmzf2.redis.BasePrefix;

public class WeatherKey extends BasePrefix {

    public static final int TOKEN_EXPIRE = 3600; // 一个小时
    public static final String NOW_PREFIX = "now";
    public static final String FUTURE_PREFIX = "future";

    /**
     * 防止被外面实例化
     * @param expireSeconds
     * @param prefix
     */
    private WeatherKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    /**
     * 需要缓存的字段：
     */
    public static WeatherKey now = new WeatherKey(TOKEN_EXPIRE, NOW_PREFIX);  //实时天气
    public static WeatherKey future = new WeatherKey(TOKEN_EXPIRE,FUTURE_PREFIX);  //未来3小时


}
