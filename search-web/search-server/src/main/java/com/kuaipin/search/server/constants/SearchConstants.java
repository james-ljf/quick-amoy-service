package com.kuaipin.search.server.constants;

/**
 * 搜索常量
 * @Author: ljf
 * @DateTime: 2021/12/13 14:35
 */
public interface SearchConstants {

    /**
     * 查询用户搜索历史或浏览历史的数量
     */
    int HISTORY_SIZE = 5;

    /**
     * redis队列数据过期时间
     */
    long TIMER = 60 * 60 * 24L;

    /**
     * 用户未登录的情况
     * 已推荐的商品数据存放在redis的List的key
     */
    String RECOMMEND_KEY = "recommended_goods";

    /**
     * 用户登录的情况
     * 已推荐的商品数据存放在redis的List的key
     */
    String LOGIN_RECOMMEND_KEY = "login_recommended_goods";

    /**
     * 旗舰店商家
     */
    String IS_FLAGSHIP = "1";

    /**
     * 搜索记录是否有结果
     */
    String HAS_RESULT = "有";
    String NOT_RESULT = "无";

    /**
     * 搜索联想显示的推荐词数量
     */
    int ASSOCIATIONAL_NUM = 7;

    /**
     * 搜索联想推荐词最低数量
     */
    int MIN_ASSOCIATIONAL_NUM = 4;

    /**
     * 关键词搜索的每路召回数量
     */
    int SEARCH_SIZE = 150;

    /**
     * 坑位逻辑业务常量
     */
    String G_NAME = "goods_name_list";
    String S_NAME = "s_type_name_list";
    /**
     * 坑位逻辑数组
     */
    String[] MASK = new String[]{G_NAME, G_NAME, S_NAME, G_NAME, S_NAME, G_NAME, G_NAME, G_NAME, S_NAME, G_NAME};

    int MIN_LIMIT = 40;

}
