package com.kuaipin.user.server.service.dubbo;

import com.kuaipin.common.entity.Page;
import com.kuaipin.common.entity.dto.PageDTO;
import com.kuaipin.common.util.ObjectUtil;
import com.kuaipin.user.api.entity.BrowseRecordDTO;
import com.kuaipin.user.api.entity.SearchRecordDTO;
import com.kuaipin.user.api.service.RecordProcessService;
import com.kuaipin.user.server.convert.PageConvert;
import com.kuaipin.user.server.entity.po.BrowseRecord;
import com.kuaipin.user.server.entity.po.SearchRecord;
import com.kuaipin.user.server.service.RecordService;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 * 搜索记录Dubbo接口中转实现层
 * @Author: ljf
 * @DateTime: 2021/12/13 14:57
 */
@DubboService
public class RecordProcessServiceImpl implements RecordProcessService {

    private final Logger log = LoggerFactory.getLogger(RecordProcessServiceImpl.class);

    @Autowired
    private PageConvert pageConvert;

    @Autowired
    private RecordService recordService;

    @Override
    public Page<SearchRecordDTO> allSearchRecord(Long uId, PageDTO pageDTO) {
        log.info("[2201.allSearchRecord rpc api]: req = {}", pageDTO);
        // 查询所有搜索记录
        Page<SearchRecord> searchRecordPage = recordService.allSearchRecord(uId, pageDTO);
        Page<SearchRecordDTO> result = pageConvert.convertSearchRecordToDTO(searchRecordPage);
        log.info("[2201.allSearchRecord success]: result = {}", result);
        return result;
    }

    @Override
    public List<SearchRecordDTO> latelySearchHistory(Long uId, Integer size) {
        log.info("[2202.latelySearchHistory rpc api]: req = {}, {}", uId, size);
        List<SearchRecord> searchRecordList = recordService.latelySearchRecord(uId, size);
        List<SearchRecordDTO> result = ObjectUtil.copyList(searchRecordList, SearchRecordDTO::new);
        log.info("[2202.latelySearchHistory success]: result = {}", result);
        return result;
    }

    @Override
    public List<BrowseRecordDTO> latelyBrowseRecord(Long uId, Integer size) {
        log.info("[2203.latelyBrowseRecord rpc api]: req = {}, {}", uId, size);
        List<BrowseRecord> browseRecordList = recordService.latelyBrowseRecord(uId, size);
        List<BrowseRecordDTO> result = ObjectUtil.copyList(browseRecordList, BrowseRecordDTO::new);
        log.info("[2203.latelyBrowseRecord success]: result = {}", result);
        return result;
    }

    @Override
    public int increaseSearchRecord(SearchRecordDTO searchRecordDTO) {
        log.info("[2204.increaseSearchRecord rpc api]: req = {}", searchRecordDTO);
        return recordService.increaseSearchRecord(searchRecordDTO);
    }

}
