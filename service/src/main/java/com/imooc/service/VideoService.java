package com.imooc.service;

import com.imooc.pojo.Videos;
import com.imooc.pojo.VideosComments;
import utils.PagedResult;

import java.util.List;

public interface VideoService {
	
	/**
	 * @Description: 保存视频
	 */
	public String saveVideo(Videos video);
	
	/**
	 * @Description: 修改视频的封面
	 */
	public void updateVideo(String videoId, String coverPath);
	
	/**
	 * @Description: 分页查询视频列表
	 */
	public PagedResult getAllVideos(Videos video, Integer isSaveRecord,
									Integer page, Integer pageSize);

	/**
	 * @Description: 查询我喜欢的视频列表
	 */
	public PagedResult queryMyLikeVideos(String userId, Integer page, Integer pageSize);

	/**
	 * @Description: 查询我关注的人的视频列表
	 */
	public PagedResult queryMyFollowVideos(String userId, Integer page, Integer pageSize);

	/**
	 * @Description: 获取热搜词列表
	 */
	public List<String> getHotwords();
	
	/**
	 * @Description: 用户喜欢/点赞视频
	 */
	public void userLikeVideo(String userId, String videoId, String videoCreaterId);
	
	/**
	 * @Description: 用户不喜欢/取消点赞视频
	 */
	public void userUnLikeVideo(String userId, String videoId, String videoCreaterId);
	
	/**
	 * @Description: 用户留言
	 */
	public void saveComment(VideosComments comment);
	
	/**
	 * @Description: 留言分页
	 */
	public PagedResult getAllComments(String videoId, Integer page, Integer pageSize);


	/**
	 * @Description: 获得视频上传者的ID
	 */
	public Videos getVideo(String videoId);

	/**
	 * @Description: 获得视频收藏者的ID
	 */
	public List<String> getUserLikeId(String videoId);

	/**
	 * @Description: 获得相关举报信息的ID
	 */
	public List<String> getReportId(String videoId);

	/**
	 * @Description: 获得相关评论信息的ID
	 */
	public List<String> getCommentId(String videoId);

	/**
	 * @Description: 删除相关的评论信息
	 */
	public void deleteComments(String videoId);

	/**
	 * @Description: 删除相关的举报信息
	 */
	public void deleteReports(String videoId);


	/**
	 * @Description: 删除视频的信息
	 */
	public void deleteVideo(String videoId);
}


