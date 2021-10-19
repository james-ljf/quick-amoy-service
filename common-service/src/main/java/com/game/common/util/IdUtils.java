package com.game.common.util;

import cn.hutool.core.lang.ObjectId;
import cn.hutool.core.util.IdUtil;

/**
 * 生成id工具类
 * @author dz0400449
 */
public class IdUtils {

    /**
     * 生成类似mongodb的ObjectId，  数据库char(24)存储
     * @return  String
     */
    public static String objectId() {
        return ObjectId.next();
    }

    /**
     * 生成的是不带-的UUID
     * @return  String
     */
    public static String simpleUUID() {
        return IdUtil.simpleUUID();
    }

    /**
     * 生成的UUID是带-的字符串
     * @return  String
     */
    public static String randomUUID() {
        return IdUtil.randomUUID();
    }

}
