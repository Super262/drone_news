package com.imooc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.mapper.*;
import com.imooc.pojo.*;
import com.imooc.pojo.VO.VideosCommentsVO;
import com.imooc.pojo.VO.VideosVO;
import com.imooc.service.VideoService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import utils.PagedResult;
import utils.TimeAgoUtils;

import java.util.Date;
import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {

	@Autowired
	private VideosMapper videosMapper;
	
	@Autowired
	private UsersMapper usersMapper;
	
	@Autowired
	private VideosMapperCustom videosMapperCustom;
	
	@Autowired
	private SearchRecordsMapper searchRecordsMapper; 
	
	@Autowired
	private VideosUsersLikeMapper videosUsersLikeMapper;
	
	@Autowired
	private VideosCommentsMapper videosCommentsMapper;
	
	@Autowired
	private VideosCommentsMapperCustom commentMapperCustom;

	@Autowired
	private VideosUsersReportMapper videosUsersReportMapper;

	
	@Autowired
	private Sid sid;
	
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public String saveVideo(Videos video) {
		
		String id = sid.nextShort();
		video.setId(id);
		videosMapper.insertSelective(video);
		
		return id;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void updateVideo(String videoId, String coverPath) {
		
		Videos video = new Videos();
		video.setId(videoId);
		video.setCoverPath(coverPath);
		videosMapper.updateByPrimaryKeySelective(video);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public PagedResult getAllVideos(Videos video, Integer isSaveRecord,
									Integer page, Integer pageSize) {
		
		// 保存热搜词
		String desc = video.getVideoDesc();
		String userId = video.getUserId();
		if (isSaveRecord != null && isSaveRecord == 1) {
			SearchRecords record = new SearchRecords();
			String recordId = sid.nextShort();
			record.setId(recordId);
			record.setContent(desc);
			searchRecordsMapper.insert(record);
		}
		
		PageHelper.startPage(page, pageSize);
		List<VideosVO> list = videosMapperCustom.queryAllVideos(desc, userId);
		
		PageInfo<VideosVO> pageList = new PageInfo<>(list);
		
		PagedResult pagedResult = new PagedResult();
		pagedResult.setPage(page);
		pagedResult.setTotal(pageList.getPages());
		pagedResult.setRows(list);
		pagedResult.setRecords(pageList.getTotal());
		
		return pagedResult;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public PagedResult queryMyLikeVideos(String userId, Integer page, Integer pageSize) {
		PageHelper.startPage(page, pageSize);
		List<VideosVO> list = videosMapperCustom.queryMyLikeVideos(userId);
				
		PageInfo<VideosVO> pageList = new PageInfo<>(list);
		
		PagedResult pagedResult = new PagedResult();
		pagedResult.setTotal(pageList.getPages());
		pagedResult.setRows(list);
		pagedResult.setPage(page);
		pagedResult.setRecords(pageList.getTotal());
		
		return pagedResult;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public PagedResult queryMyFollowVideos(String userId, Integer page, Integer pageSize) {
		PageHelper.startPage(page, pageSize);
		List<VideosVO> list = videosMapperCustom.queryMyFollowVideos(userId);
				
		PageInfo<VideosVO> pageList = new PageInfo<>(list);
		
		PagedResult pagedResult = new PagedResult();
		pagedResult.setTotal(pageList.getPages());
		pagedResult.setRows(list);
		pagedResult.setPage(page);
		pagedResult.setRecords(pageList.getTotal());
		
		return pagedResult;
	}


	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public List<String> getHotwords() {
		return searchRecordsMapper.getHotwords();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void userLikeVideo(String userId, String videoId, String videoCreaterId) {
		// 1. 保存用户和视频的喜欢点赞关联关系表
		String likeId = sid.nextShort();
		VideosUsersLike vul = new VideosUsersLike();
		vul.setId(likeId);
		vul.setUserId(userId);
		vul.setVideoId(videoId);
		videosUsersLikeMapper.insert(vul);

		// 2. 视频喜欢数量累加
		videosMapperCustom.addVideoLikeCount(videoId);

		// 3. 用户受喜欢数量的累加
		usersMapper.addReceiveLikeCount(videoCreaterId);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void userUnLikeVideo(String userId, String videoId, String videoCreaterId) {
		// 1. 删除用户和视频的喜欢点赞关联关系表

		Example example = new Example(VideosUsersLike.class);
		Criteria criteria = example.createCriteria();

		criteria.andEqualTo("userId", userId);
		criteria.andEqualTo("videoId", videoId);

		videosUsersLikeMapper.deleteByExample(example);

		// 2. 视频喜欢数量累减
		videosMapperCustom.reduceVideoLikeCount(videoId);

		// 3. 用户受喜欢数量的累减
		usersMapper.reduceReceiveLikeCount(videoCreaterId);

	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void saveComment(VideosComments comment) {
		String id = sid.nextShort();
		comment.setId(id);
		comment.setCreateTime(new Date());
		videosCommentsMapper.insert(comment);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public PagedResult getAllComments(String videoId, Integer page, Integer pageSize) {
		
		PageHelper.startPage(page, pageSize);
		
		List<VideosCommentsVO> list = commentMapperCustom.queryComments(videoId);
		
			for (VideosCommentsVO c : list) {
				String timeAgo = TimeAgoUtils.format(c.getCreateTime());
				c.setTimeAgoStr(timeAgo);
			}
		
		PageInfo<VideosCommentsVO> pageList = new PageInfo<>(list);
		
		PagedResult grid = new PagedResult();
		grid.setTotal(pageList.getPages());
		grid.setRows(list);
		grid.setPage(page);
		grid.setRecords(pageList.getTotal());
		
		return grid;
	}

	@Override
	public Videos getVideo(String videoId) {
		return videosMapper.selectByPrimaryKey(videoId);
	}

	@Override
	public List<String> getUserLikeId(String videoId) {
		return videosUsersLikeMapper.queryUserId(videoId);
	}

	@Override
	public List<String> getReportId(String videoId) {
		return videosUsersReportMapper.queryReportId(videoId);
	}

	@Override
	public List<String> getCommentId(String videoId) {
		return videosCommentsMapper.queryCommentsId(videoId);
	}

	@Override
	public void deleteComments(String videoId) {
		List<String> commentID=getCommentId(videoId);
		for(String id:commentID){
			videosCommentsMapper.deleteByPrimaryKey(id);
		}
	}

	@Override
	public void deleteReports(String videoId) {
		List<String> reportID=getReportId(videoId);
		for(String id:reportID){
			videosUsersReportMapper.deleteByPrimaryKey(id);
		}
	}

	@Override
	public void deleteVideo(String videoId) {
		videosMapper.deleteByPrimaryKey(videoId);
	}

}
