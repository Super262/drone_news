package com.imooc.service;

import com.imooc.pojo.VideosBgms;
import java.util.List;


public interface BgmService {
	
	/**
	 * @Description: 查询背景音乐列表
	 */
	public List<VideosBgms> queryBgmList();
	
	/**
	 * @Description: 根据id查询bgm信息
	 */
	public VideosBgms queryBgmById(String bgmId);
}
