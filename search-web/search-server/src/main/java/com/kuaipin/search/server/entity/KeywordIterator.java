package com.kuaipin.search.server.entity;

import com.alibaba.fastjson.JSONObject;
import com.kuaipin.search.server.entity.response.GoodsInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.suggest.InputIterator;
import org.apache.lucene.util.BytesRef;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 商品信息迭代器
 * 用于搜索词联想
 * 必须配置的key：payload、weight、key
 * @Author: ljf
 * @DateTime: 2022/3/27 15:47
 */
@Slf4j
public class KeywordIterator implements InputIterator {

    private Iterator<JSONObject> iterator = null;
    private JSONObject object;
    public KeywordIterator(Iterator<JSONObject> iterator){
        super();
        this.iterator = iterator;
    }

    @Override
    public long weight() {
        return (long) object.get("weight");
    }

    @Override
    public BytesRef payload() {
       try{
           ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
           ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
           // 将商品的名字序列化存入载荷中
           objectOutputStream.writeObject(object.get("payload"));
           objectOutputStream.close();
       }catch (IOException e){
           log.error("[4050.KeywordIterator error] : e = {}", e.toString());
       }
       return null;
    }

    @Override
    public boolean hasPayloads() {
        return true;
    }

    /**
     * 过滤域
     * @return  过滤集合
     */
    @Override
    public Set<BytesRef> contexts() {
        return new HashSet<>();
    }

    @Override
    public boolean hasContexts() {
        return true;
    }

    @Override
    public BytesRef next(){
        if(iterator.hasNext()){
            object=iterator.next();
            try{
                String key = (String) object.get("key");
                // 设定key作为推荐词返回
                return new BytesRef(key.getBytes(StandardCharsets.UTF_8));
            }catch (Exception e){
                throw new RuntimeException("无法转换成UTF-8, error = {}", e);
            }
        }else{
            return null;
        }
    }
}
