package com.kuaipin.search.server.constants;



/**
 * 推荐的规则与数量枚举
 * @Author: ljf
 * @DateTime: 2022/3/24 18:48
 */
public enum RecommendRuleConstants {


    // 关键词数组
    TERM_LIST(new String[]{"新品", "新款", "潮流", "促销", "直播", "特卖", "精品", "清仓"}),
    // 评论数范围头部
    START_COMMENT_NUM(20000),
    // 评论数范围尾部
    END_COMMENT_NUM(100000),
    // 未登录的推荐列表召回数量
    NOT_LOGIN_RECALL_NUMBER(20),
    // 已登录的推荐列表召回数量
    LOGIN_RECALL_NUMBER(30);


    private final Object type;

    RecommendRuleConstants(Object obj) {
        this.type = obj;
    }

    public Object getType(){
        return this.type;
    }


}
