package com.imooc.service.impl;

import com.imooc.mapper.VideosBgmsMapper;
import com.imooc.pojo.VideosBgms;
import com.imooc.service.BgmService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BgmServiceImpl implements BgmService {

	@Autowired
	private VideosBgmsMapper videosBgmsMapper;
	
	@Autowired
	private Sid sid;
	
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public List<VideosBgms> queryBgmList() {
		return videosBgmsMapper.selectAll();
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public VideosBgms queryBgmById(String bgmId) {
		List<VideosBgms> list=queryBgmList();
		VideosBgms e=new VideosBgms();
		for(VideosBgms vb:list){
			if(vb.getId().equals(bgmId)){
				e=vb;
			}
		}
		return e;
	}

}
