package com.kuaipin.search.server.constants.singleton;


import com.kuaipin.search.server.util.LuceneUtil;
import org.apache.lucene.store.Directory;

/**
 * 单例Lucene内存目录对象
 * @Author: ljf
 * @DateTime: 2021/12/12 23:18
 */
public class SingletonDirectory {

    private SingletonDirectory(){}

    private static class SingleRamDirectory{
        private static final Directory DIRECTORY = LuceneUtil.buildRAMDirectory();
    }
    private static class SingleFsDirectory{
        private static final Directory DIRECTORY = LuceneUtil.buildFSDirectory("D:\\毕设\\index-lucene");
    }

    /**
     * 内部类方法获取RAM单例
     * @return 索引目录
     */
    public static Directory buildRamDirectory(){
        return SingleRamDirectory.DIRECTORY;
    }

    /**
     * 内部类方法获取FS单例
     * @return  索引目录
     */
    public static Directory buildFsDirectory(){
        return SingleFsDirectory.DIRECTORY;
    }
}
