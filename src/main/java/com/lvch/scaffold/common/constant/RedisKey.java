package com.lvch.scaffold.common.constant;

/**
 * @author April
 */
public class RedisKey {
    private static final String BASE_KEY = "basescaffold:";

    /**
     * 用户token存放
     */
    public static final String USER_TOKEN_STRING = "userToken:uid_%d";

    /**
     * 用户的信息更新时间
     */
    public static final String USER_MODIFY_STRING = "userModify:uid_%d";

    /**
     * 用户的信息汇总
     */
    public static final String USER_SUMMARY_STRING = "userSummary:uid_%d";

    /**
     * 保存Open id
     */
    public static final String OPEN_ID_STRING = "openid:%s";

    public static String getKey(String key, Object... objects) {
        return BASE_KEY + String.format(key, objects);
    }

}
