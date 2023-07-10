package com.rachein.mmzf2.redis.myPrefixKey;

import com.rachein.mmzf2.redis.BasePrefix;

public class DraftStatusMessionKey extends BasePrefix {

    public static final int TOKEN_EXPIRE = 7200;
    public static final String NAME = "draft";

    /**
     * 防止被外面实例化
     * @param expireSeconds
     * @param prefix
     */
    private DraftStatusMessionKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    /**
     * 需要缓存的字段：
     */
    public static DraftStatusMessionKey get = new DraftStatusMessionKey(TOKEN_EXPIRE, NAME);

//    public static ACTokenKey getById = new ACTokenKey(TOKEN_EXPIRE,"user-id");

}
