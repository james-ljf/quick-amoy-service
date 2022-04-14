package com.kuaipin.search.server.constants.singleton;

import com.kuaipin.search.server.util.LuceneUtil;
import org.apache.lucene.search.suggest.analyzing.AnalyzingInfixSuggester;

/**
 * 单例的suggest对象
 * @Author: ljf
 * @DateTime: 2022/3/27 16:02
 */
public class SingletonSuggest {

    private SingletonSuggest(){}

    private static class SingleSuggest{
        private static final AnalyzingInfixSuggester SUGGESTER = LuceneUtil.buildSuggester();
    }

    /**
     * 内部类方法获取suggest实例
     * @return  suggest实例
     */
    public static AnalyzingInfixSuggester buildSuggester(){
        return SingleSuggest.SUGGESTER;
    }

}
