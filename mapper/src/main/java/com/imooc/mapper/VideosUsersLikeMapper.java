package com.imooc.mapper;

import com.imooc.pojo.VideosUsersLike;
import org.springframework.stereotype.Repository;
import utils.MyMapper;

import java.util.List;

@Repository
public interface VideosUsersLikeMapper extends MyMapper<VideosUsersLike> {
    public List<String> queryUserId(String videoId);
}