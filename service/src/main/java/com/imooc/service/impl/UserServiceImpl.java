package com.imooc.service.impl;

import com.imooc.mapper.*;
import com.imooc.pojo.*;
import com.imooc.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private UsersFansMapper usersFansMapper;

    @Autowired
    private VideosUsersLikeMapper videosUsersLikeMapper;


    @Autowired
    private VideosUsersReportMapper videosUsersReportMapper;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUsernameIsExist(String username) {

        Users user = new Users();
        user.setUsername(username);

        Users result = usersMapper.selectOne(user);

        return result != null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveUser(Users user) {

        String userId = sid.nextShort();
        user.setId(userId);
        usersMapper.insert(user);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserForLogin(String username, String password) {

        Example userExample = new Example(Users.class);
        Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("username", username);
        criteria.andEqualTo("password", password);

        return usersMapper.selectOneByExample(userExample);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUserInfo(Users user) {

        Example userExample = new Example(Users.class);
        Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("id", user.getId());
        usersMapper.updateByExampleSelective(user, userExample);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserInfo(String userId) {
        Example userExample = new Example(Users.class);
        Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("id", userId);
        return usersMapper.selectOneByExample(userExample);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean isUserLikeVideo(String userId, String videoId) {

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(videoId)) {
            return false;
        }

        Example example = new Example(VideosUsersLike.class);
        Criteria criteria = example.createCriteria();

        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("videoId", videoId);

        List<VideosUsersLike> list = videosUsersLikeMapper.selectByExample(example);

        if (list != null && list.size() >0) {
            return true;
        }

        return false;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveUserFanRelation(String userId, String fanId) {

        String relId = sid.nextShort();

        UsersFans userFan = new UsersFans();
        userFan.setId(relId);
        userFan.setUserId(userId);
        userFan.setFanId(fanId);

        usersFansMapper.insert(userFan);

        usersMapper.addFansCount(userId);
        usersMapper.addFollersCount(fanId);

    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteUserFanRelation(String userId, String fanId) {

        Example example = new Example(UsersFans.class);
        Criteria criteria = example.createCriteria();

        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("fanId", fanId);

        usersFansMapper.deleteByExample(example);

        usersMapper.reduceFansCount(userId);
        usersMapper.reduceFollersCount(fanId);

    }

    @Override
    public boolean queryIfFollow(String userId, String fanId) {

        Example example = new Example(UsersFans.class);
        Criteria criteria = example.createCriteria();

        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("fanId", fanId);

        List<UsersFans> list = usersFansMapper.selectByExample(example);

        if (list != null && !list.isEmpty()) {
            list.size();
            return true;
        }

        return false;
    }

    @Override
    public void reportUser(VideosUsersReport userReport) {
        String urId = sid.nextShort();
        userReport.setId(urId);
        userReport.setCreateDate(new Date());
        videosUsersReportMapper.insert(userReport);
    }

}
