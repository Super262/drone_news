package com.imooc.mapper;

import com.imooc.pojo.VO.VideosCommentsVO;
import com.imooc.pojo.VideosComments;
import org.springframework.stereotype.Repository;
import utils.MyMapper;

import java.util.List;

@Repository
public interface VideosCommentsMapperCustom extends MyMapper<VideosComments> {
	
	public List<VideosCommentsVO> queryComments(String videoId);
}