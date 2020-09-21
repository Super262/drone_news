package com.imooc.mapper;

import com.imooc.pojo.SearchRecords;
import org.springframework.stereotype.Repository;
import utils.MyMapper;

import java.util.List;

@Repository
public interface SearchRecordsMapper extends MyMapper<SearchRecords> {
    public List<String> getHotwords();
}