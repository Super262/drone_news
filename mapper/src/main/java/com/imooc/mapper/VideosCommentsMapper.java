package com.imooc.mapper;

import com.imooc.pojo.VideosComments;
import org.springframework.stereotype.Repository;
import utils.MyMapper;

import java.util.List;

@Repository
public interface VideosCommentsMapper extends MyMapper<VideosComments> {
    public List<String> queryCommentsId(String videoId);
}