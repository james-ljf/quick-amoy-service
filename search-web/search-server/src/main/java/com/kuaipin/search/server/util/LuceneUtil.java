package com.kuaipin.search.server.util;

import java.io.IOException;
import java.nio.file.Paths;

import com.kuaipin.search.server.constants.singleton.SingletonAnalyzer;
import com.kuaipin.search.server.constants.singleton.SingletonDirectory;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.search.suggest.analyzing.AnalyzingInfixSuggester;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.lionsoul.jcseg.ISegment;
import org.lionsoul.jcseg.analyzer.JcsegAnalyzer;
import org.lionsoul.jcseg.dic.ADictionary;
import org.lionsoul.jcseg.dic.DictionaryFactory;
import org.lionsoul.jcseg.segmenter.SegmenterConfig;

/**
 * Lucene常用工具类
 *
 * @Author ljf
 * @Date 2021/11/10 17:41
 */
@Slf4j
public class LuceneUtil {

    /**
     * 创建存储目录（磁盘）
     *
     * @param filePath 索引存放在磁盘的路径
     */
    public static Directory buildFSDirectory(String filePath) {
        try {
            return FSDirectory.open(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建存储目录（内存）
     */
    public static Directory buildRAMDirectory() {
        return new RAMDirectory();
    }

    public static AnalyzingInfixSuggester buildSuggester() {
        Directory directory = SingletonDirectory.buildRamDirectory();

        try {
            return new AnalyzingInfixSuggester(directory, SingletonAnalyzer.buildJcsegAnalyzer());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 创建分词器
     * @return  分词器
     */
    public static Analyzer buildAnalyzer(){
        SegmenterConfig config = new SegmenterConfig(true);
        ADictionary aDictionary = DictionaryFactory.createDefaultDictionary(config);
        return new JcsegAnalyzer(ISegment.Type.MOST, config, aDictionary);
    }

    /**
     * 创建索引读取工具
     */
    public static IndexReader buildIndexReader() {
        Directory directory = SingletonDirectory.buildFsDirectory();
        if (directory == null) {
            return null;
        }
        try {
            // 索引读取工具
            return DirectoryReader.open(directory);
        } catch (Exception e) {
            log.warn("[401.build indexReader]: error={}, directory={}", e, directory);
        }
        return null;
    }

    /**
     * 创建索引搜索对象工具
     */
    public static IndexSearcher buildIndexSearcher(IndexReader reader) {
        return new IndexSearcher(reader);
    }


    /**
     * 创建写入索引对象
     *
     * @param directory 索引存放目录
     */
    public static IndexWriter buildIndexWriter(Directory directory) {
        IndexWriter indexWriter = null;
        SegmenterConfig segmenterConfig = new SegmenterConfig(true);
        ADictionary aDictionary = DictionaryFactory.createDefaultDictionary(segmenterConfig);
        try {
            IndexWriterConfig config = new IndexWriterConfig(new JcsegAnalyzer(ISegment.Type.SIMPLE, segmenterConfig, aDictionary));
            // 设置索引打开方式
            config.setOpenMode(OpenMode.CREATE_OR_APPEND);
            // 设置关闭之前先提交
            config.setCommitOnClose(true);
            indexWriter = new IndexWriter(directory, config);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return indexWriter;
    }

    /**
     * 关闭索引文件生成对象
     *
     * @param indexWriter 写入索引对象
     */
    public static void close(IndexWriter indexWriter) {
        if (indexWriter != null) {
            try {
                indexWriter.close();
            } catch (IOException e) {
                log.warn("[indexWriter close]: error={}, indexWriter={}", e, indexWriter);
            }
        }
    }

    /**
     * 关闭索引文件读取对象
     *
     * @param reader 索引读取工具
     */
    public static void close(IndexReader reader) {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 高亮标签
     *
     * @param query     查询对象
     */
    public static Highlighter getHighlighter(Query query) {
        SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<span style='color:red'>", "</span>");
        QueryScorer scorer = new QueryScorer(query);
        Highlighter highlighter = new Highlighter(formatter, scorer);
        highlighter.setTextFragmenter(new SimpleSpanFragmenter(scorer));
        return highlighter;
    }
}