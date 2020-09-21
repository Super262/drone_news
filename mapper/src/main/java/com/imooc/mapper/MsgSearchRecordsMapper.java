package com.imooc.mapper;

import com.imooc.pojo.MsgSearchRecords;
import org.springframework.stereotype.Repository;
import utils.MyMapper;

import java.util.List;

@Repository
public interface MsgSearchRecordsMapper extends MyMapper<MsgSearchRecords> {
    public List<String> getHotwords();
}