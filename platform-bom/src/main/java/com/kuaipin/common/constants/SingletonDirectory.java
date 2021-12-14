package com.kuaipin.common.constants;

import com.kuaipin.common.util.lucene.LuceneUtil;
import org.apache.lucene.store.Directory;

/**
 * 单例Lucene内存目录对象
 * @Author: ljf
 * @DateTime: 2021/12/12 23:18
 */
public class SingletonDirectory {
    private SingletonDirectory(){}

    private static class SingleDirectory{
        private static final Directory DIRECTORY = LuceneUtil.buildRAMDirectory();
    }

    /**
     * 内部类方法获取单例
     */
    public static Directory getDirectory(){
        return SingleDirectory.DIRECTORY;
    }
}
