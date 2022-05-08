package com.kuaipin.common.util;

import cn.hutool.core.lang.ObjectId;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * 生成id工具类
 * @author dz0400449
 */
public class IdUtils {

    private IdUtils(){}

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
    public static String simpleUuid() {
        return IdUtil.simpleUUID();
    }

    /**
     * 生成的UUID是带-的字符串
     * @return  String
     */
    public static String randomUuid() {
        return IdUtil.randomUUID();
    }

    /**
     * 雪花算法id
     * @return  Long
     */
    public static Long snowflakeId(){
        Snowflake snowflake = IdUtil.getSnowflake();
        return snowflake.nextId();
    }

}
