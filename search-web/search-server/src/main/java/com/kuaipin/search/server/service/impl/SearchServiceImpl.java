package com.kuaipin.search.server.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.kuaipin.common.entity.Response;
import com.kuaipin.search.server.entity.response.GoodsInfoVO;
import com.kuaipin.search.server.external.RecommendComponent;
import com.kuaipin.search.server.service.SearchService;
import org.apache.lucene.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.*;

/**
 * @Author: ljf
 * @DateTime: 2022/3/22 14:49
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private RecommendComponent recommendComponent;

    private static ThreadPoolExecutor execThreadPool = new ThreadPoolExecutor(50, 300,
            1L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(100),
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    @Override
    public Response<Object> goodsRecommendPanel(Long uid) throws ExecutionException, InterruptedException {
        if (uid == null){
            // 用户没有登录的情况(①召回评论数高的，②根据系统近期搜索记录召回商品，③召回含有“新品”、“潮流”关键词的商品)
            Future<List<GoodsInfoVO>> hotGoodsFuture = execThreadPool.submit(() -> recommendComponent.getHotGoods());
            return Response.success(hotGoodsFuture.get());


        }
        // 用户登录了的情况（获取用户近期的搜索记录，通过搜索记录关键词去召回同类商品）
        return null;
    }

}
