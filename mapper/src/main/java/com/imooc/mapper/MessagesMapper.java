package com.imooc.mapper;

import com.imooc.pojo.Messages;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import utils.MyMapper;

import java.util.List;

@Repository
public interface MessagesMapper extends MyMapper<Messages> {
    /**
     * @Description: 条件查询所有新闻列表
     */
    public List<Messages> queryAllMsgs(@Param("title") String title,
                                         @Param("id") String id);
//
//    /**
//     * @Description: 查询关注的新闻
//     */
//    public List<Messages> queryMyFollowMsgs(String userId);
//
//    /**
//     * @Description: 查询点赞新闻
//     */
//    public List<Messages> queryMyLikeMsgs(@Param("userId") String userId);
//
//    /**
//     * @Description: 对新闻喜欢的数量进行累加
//     */
//    public void addMsgLikeCount(String msgId);
//
//    /**
//     * @Description: 对新闻喜欢的数量进行累减
//     */
//    public void reduceMsgLikeCount(String msgId);
}