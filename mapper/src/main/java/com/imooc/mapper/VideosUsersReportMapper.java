package com.imooc.mapper;

import com.imooc.pojo.VideosUsersReport;
import org.springframework.stereotype.Repository;
import utils.MyMapper;

import java.util.List;

@Repository
public interface VideosUsersReportMapper extends MyMapper<VideosUsersReport> {
    public List<String> queryReportId(String videoId);
}