package com.kuaipin.search.server.service.dubbo;

import com.kuaipin.common.entity.Page;
import com.kuaipin.common.entity.Response;
import com.kuaipin.common.entity.dto.PageDTO;
import com.kuaipin.search.api.entity.SearchRecordDTO;
import com.kuaipin.search.api.service.SearchRecordProcessService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * 搜索记录Dubbo接口中转实现层
 * @Author: ljf
 * @DateTime: 2021/12/13 14:57
 */
@Slf4j
@DubboService
public class SearchRecordProcessServiceImpl implements SearchRecordProcessService  {

    @Override
    public Response<Page<SearchRecordDTO>> findAllSearchRecord(PageDTO pageDTO) {
        log.info("[230.get api parameter]: pageDTO = " + pageDTO);
        return null;
    }

}
