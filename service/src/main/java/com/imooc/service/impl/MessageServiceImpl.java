package com.imooc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.mapper.MessagesMapper;
import com.imooc.mapper.MsgSearchRecordsMapper;
import com.imooc.pojo.Messages;
import com.imooc.pojo.MsgSearchRecords;
import com.imooc.service.MessageService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import utils.PagedResult;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessagesMapper msgMapper;

    @Autowired
    private MsgSearchRecordsMapper msgSearchRecordsMapper;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String addMsg(Messages msg) {
        msgMapper.insertSelective(msg);
        return msg.getId();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public PagedResult getAllMsgs(Messages msg, Integer isSaveRecord, Integer page, Integer pageSize) {
        // 保存热搜词
        String title = msg.getTitle();
        String msgId = msg.getId();
        if (isSaveRecord != null && isSaveRecord == 1) {
            MsgSearchRecords record = new MsgSearchRecords();
            String recordId = sid.nextShort();
            record.setId(recordId);
            record.setContent(title);
            msgSearchRecordsMapper.insert(record);
        }

        PageHelper.startPage(page, pageSize);
        List<Messages> list = msgMapper.queryAllMsgs(title, msgId);

        PageInfo<Messages> pageList = new PageInfo<>(list);

        PagedResult pagedResult = new PagedResult();
        pagedResult.setPage(page);
        pagedResult.setTotal(pageList.getPages());
        pagedResult.setRows(list);
        pagedResult.setRecords(pageList.getTotal());

        return pagedResult;
    }

    @Override
    public String getMsgPath(String id) {
        Messages msg=msgMapper.selectByPrimaryKey(id);
        return msg.getPath();
    }

    @Override
    public List<String> getHotwords() {
        return msgSearchRecordsMapper.getHotwords();
    }
}
