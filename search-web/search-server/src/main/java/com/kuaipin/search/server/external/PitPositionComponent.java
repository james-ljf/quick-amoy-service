package com.kuaipin.search.server.external;

import com.kuaipin.common.annotation.SystemTime;
import com.kuaipin.search.server.constants.SearchConstants;
import com.kuaipin.search.server.entity.response.GoodsInfoVO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 坑位逻辑业务
 * @Author: ljf
 * @DateTime: 2022/4/5 17:00
 */
@Component
public class PitPositionComponent {

    /**
     * 搜索结果填坑，当前坑位没有数据填坑时默认填为广告位
     * @param listMap   不同key类型数组的商品集合
     * @param analyzerList  顺位替补商品集合
     * @return  填坑完成的商品列表
     */
    @SystemTime
    public List<GoodsInfoVO> searchPitFilling(Map<String, List<GoodsInfoVO>> listMap, List<GoodsInfoVO> analyzerList){
        // 存放填坑最终结果
        List<GoodsInfoVO> filledList = new ArrayList<>();
        for (String keyType : SearchConstants.MASK) {
            List<GoodsInfoVO> goodsInfoVOList = listMap.get(keyType);
            // 如果当前的商品召回没数据填坑，则使用顺位替补规则
            if (CollectionUtils.isEmpty(goodsInfoVOList)){
                GoodsInfoVO advertisingGoods = analyzerList.get(0);
                while(filledList.contains(advertisingGoods)){
                    analyzerList.remove(0);
                    advertisingGoods = analyzerList.get(0);
                }
                // 设置为广告位
                advertisingGoods.setIsAdvertising("1");
                filledList.add(advertisingGoods);
                // 原集合删除元素
                analyzerList.remove(0);
                continue;
            }
            // 获取对应数组的第一个商品
            GoodsInfoVO goodsInfoVO = goodsInfoVOList.get(0);
            // 如果已经填过坑的就会在当前集合删除，并且遍历下一个，保证填坑会有数据
            while(filledList.contains(goodsInfoVO)){
                listMap.get(keyType).remove(0);
                goodsInfoVO = goodsInfoVOList.get(0);
            }
            filledList.add(goodsInfoVO);
            // 添加成功后在原集合进行删除
            listMap.get(keyType).remove(0);
        }
        // 存放剩余的商品并去重
        Set<GoodsInfoVO> allGoodsSet = new HashSet<>();
        allGoodsSet.addAll(listMap.get(SearchConstants.G_NAME));
        allGoodsSet.addAll(listMap.get(SearchConstants.S_NAME));
        // 将已填坑的商品在set中删除，防止后面的商品与已填坑的商品重复
        filledList.forEach(allGoodsSet::remove);
        filledList.addAll(allGoodsSet);
        return filledList;
    }

}
