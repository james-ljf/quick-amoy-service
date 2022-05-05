package com.kuaipin.search.server.constants.singleton;

import com.kuaipin.search.server.util.LuceneUtil;
import org.apache.lucene.analysis.Analyzer;

/**
 * 单例Jcseg分词实例
 * @Author: ljf
 * @DateTime: 2022/4/10 11:51
 */
public class SingletonAnalyzer {

    private SingletonAnalyzer(){

    }

    private static class SingleAnalyzer{
        private static final Analyzer ANALYZER = LuceneUtil.buildAnalyzer();
    }

    public static Analyzer buildJcsegAnalyzer(){
        return SingleAnalyzer.ANALYZER;
    }

}
