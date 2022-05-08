package com.kuaipin.user.server.repository.impl;

import com.github.pagehelper.PageInfo;
import com.kuaipin.common.constants.ErrorEnum;
import com.kuaipin.common.entity.Page;
import com.kuaipin.user.server.entity.po.BrowseRecord;
import com.kuaipin.user.server.entity.po.SearchRecord;
import com.kuaipin.user.server.repository.RecordRepository;
import com.kuaipin.user.server.repository.impl.mapper.RecordMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: ljf
 * @DateTime: 2021/12/13 14:39
 */
@Repository
public class RecordRepositoryImpl implements RecordRepository {

    private final Logger logger = LoggerFactory.getLogger(RecordRepositoryImpl.class);

    @Resource
    private RecordMapper dbSearchRecordProxy;

    @Override
    public Page<SearchRecord> getAllSearchRecord(Long uId) {
        List<SearchRecord> searchRecordList = dbSearchRecordProxy.selectAllSearchRecord(uId);
        if (CollectionUtils.isNotEmpty(searchRecordList)){
            // 将结果分页
            PageInfo<SearchRecord> pageInfo = new PageInfo<>(searchRecordList);
            return new Page<>(pageInfo.getTotal(), searchRecordList);
        }
        return null;
    }

    @Override
    public List<SearchRecord> getSearchRecordByTime(Long uid, Integer size) {
        return dbSearchRecordProxy.selectSearchRecordByTime(uid, size);
    }

    @Override
    public SearchRecord getSearchRecord(String keyword, Long uId) {
        return dbSearchRecordProxy.selectSearchRecord(keyword, uId);
    }

    @Override
    public List<BrowseRecord> getBrowseRecordByUid(Long uid, Integer size) {
        return dbSearchRecordProxy.selectBrowseRecordByUid(uid, size);
    }

    @Override
    public Page<BrowseRecord> getAllBrowseRecord(Long uId) {
        List<BrowseRecord> browseRecordList = dbSearchRecordProxy.selectAllBrowseRecord(uId);
        if (CollectionUtils.isNotEmpty(browseRecordList)){
            PageInfo<BrowseRecord> pageInfo = new PageInfo<>(browseRecordList);
            return new Page<>(pageInfo.getTotal(), browseRecordList);
        }
        return null;
    }

    @Override
    public BrowseRecord getBrowseRecord(Long goodsNumber, Long uId) {
        return dbSearchRecordProxy.selectBrowseRecordByGoodsNumber(goodsNumber, uId);
    }

    @Override
    public int setSearchRecord(SearchRecord searchRecord) {
        int num = dbSearchRecordProxy.insertSearchRecord(searchRecord);
        if (num == 0){
            logger.error("[{}.addSearchRecord error] : msg={}, req={}", ErrorEnum.DATABASE_ERROR.getCode(), ErrorEnum.DATABASE_ERROR.getMsg(), searchRecord);
            return -1;
        }
        return num;
    }

    @Override
    public int setBrowseRecord(BrowseRecord browseRecord) {
        return dbSearchRecordProxy.insertBrowseRecord(browseRecord);
    }

    @Override
    public int modifySearchRecord(SearchRecord searchRecord) {
        return dbSearchRecordProxy.updateSearchRecord(searchRecord);
    }

    @Override
    public int modifyBrowseRecord(BrowseRecord browseRecord) {
        return dbSearchRecordProxy.updateBroseRecord(browseRecord);
    }

    @Override
    public int cancelSearchRecord(Long searchId, Long uId) {
        return dbSearchRecordProxy.deleteSearchRecord(searchId, uId);
    }

    @Override
    public int cancelBrowseRecord(Long browseId, Long uId) {
        return dbSearchRecordProxy.deleteBrowseRecord(browseId, uId);
    }

    @Override
    public List<BrowseRecord> findBrowseRecordInNear(Long uId) {
        return dbSearchRecordProxy.selectNearBrowseRecord(uId);
    }

    @Override
    public List<BrowseRecord> findBrowseRecordInLater(Long uId) {
        return dbSearchRecordProxy.selectLaterBrowseRecord(uId);
    }
}
