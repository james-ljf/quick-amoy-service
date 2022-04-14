package com.kuaipin.user.server.convert;

import com.kuaipin.common.entity.Page;
import com.kuaipin.common.util.BeanCopyUtils;
import com.kuaipin.user.api.entity.SearchRecordDTO;
import com.kuaipin.user.server.entity.po.SearchRecord;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 分页结果转换器
 * @Author: ljf
 * @DateTime: 2021/12/14 14:42
 */
@Component
public class PageConvert {

    /**
     * 将搜索记录分页结果实体转换为搜索记录分页结果DTO实体
     * @param searchRecordPage  搜索记录分页结果PO实体
     * @return  搜索记录分页结果DTO实体
     */
    public Page<SearchRecordDTO> convertSearchRecordToDTO(Page<SearchRecord> searchRecordPage){
        Page<SearchRecordDTO> result = new Page<>();
        // 属性值设置
        result.setPageNum(searchRecordPage.getPageNum());
        result.setPageSize(searchRecordPage.getPageSize());
        result.setTotalCount(searchRecordPage.getTotalCount());
        List<SearchRecordDTO> searchRecordDTOList = BeanCopyUtils.copyListProperties(searchRecordPage.getResultList(), SearchRecordDTO::new);
        result.setResultList(searchRecordDTOList);
        return result;
    }

}
