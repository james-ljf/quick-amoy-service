package com.kuaipin.search.server.service.dubbo;

import com.kuaipin.common.entity.Page;
import com.kuaipin.common.entity.dto.PageDTO;
import com.kuaipin.search.api.entity.SearchRecordDTO;
import com.kuaipin.search.api.service.SearchRecordProcessService;
import com.kuaipin.search.server.convert.PageConvert;
import com.kuaipin.search.server.service.SearchRecordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * 搜索记录Dubbo接口中转实现层
 * @Author: ljf
 * @DateTime: 2021/12/13 14:57
 */
@Slf4j
@DubboService
public class SearchRecordProcessServiceImpl implements SearchRecordProcessService  {

    @Resource
    private PageConvert pageConvert;

    @Autowired
    private SearchRecordService searchRecordService;

    @Override
    public Page<SearchRecordDTO> findAllSearchRecord(PageDTO pageDTO) {
        log.info("[230.findAllSearchRecord dubbo api]: req = " + pageDTO);
        // 查询所有搜索记录
        Page<SearchRecordDTO> result = searchRecordService.findAllSearchRecord(pageDTO);
        log.info("[230.findAllSearchRecord success]: result = " + result);
        return result;
    }

}
