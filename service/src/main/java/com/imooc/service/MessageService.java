package com.imooc.service;

import com.imooc.pojo.Messages;
import utils.PagedResult;

import java.util.List;

public interface MessageService {
    /**
     * @Description: 添加新闻
     */
    public String addMsg(Messages Msg);

    /**
     * @Description: 分页查询新闻列表
     */
    public PagedResult getAllMsgs(Messages msg, Integer isSaveRecord,
                                    Integer page, Integer pageSize);

    /**
     * @Description: 分页查询新闻列表
     */
    public String getMsgPath(String id);


    /**
     * @Description: 获取热搜词列表
     */
    public List<String> getHotwords();
}
