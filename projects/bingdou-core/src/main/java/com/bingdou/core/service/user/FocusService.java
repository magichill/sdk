package com.bingdou.core.service.user;

import com.bingdou.core.model.User;
import com.bingdou.core.repository.user.FocusDao;
import com.bingdou.tools.LogContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by gaoshan on 17/2/14.
 */
@Service
public class FocusService {

    @Autowired
    private FocusDao focusDao;

    /**
     * 查看用户关注记录是否存在
     * @param user
     * @param followerUser
     * @return
     */
    public Integer isFocusInfoExist(User user,User followerUser){
        LogContext.instance().info("检查用户关注信息是否存在");
        return checkFocusInfoStatus(user.getId(),followerUser.getId());
    }

    public Integer checkFocusInfoStatus(int userId,int followerId){
        return focusDao.checkFocusInfoStatus(userId,followerId);
    }

    /**
     * 更新用户关注状态
     * @param user
     * @param followerUser
     * @param status
     */
    public void updateFocusInfo(User user,User followerUser,int status){
        LogContext.instance().info("更新用户关注状态");
        focusDao.updateFocusInfo(user.getId(), followerUser.getId(),status);
    }

    /**
     * 插入用户关注信息
     * @param user
     * @param followerUser
     */
    public void insertFocusInfo(User user,User followerUser){
        LogContext.instance().info("插入用户关注信息");
        focusDao.insertFocus(user.getId(), followerUser.getId());
    }

    /**
     * 查询关注用户数量
     * @param userId
     * @return
     */
    public int getFollowerCount(int userId){
        LogContext.instance().info("查询关注用户数量");
        return focusDao.getFollowerCount(userId);
    }

    /**
     * 查询粉丝数量
     * @param userId
     * @return
     */
    public int getFansCount(int userId){
        LogContext.instance().info("查询用户粉丝数量");
        return focusDao.getFansCount(userId);
    }

    public List<User> getFollower(int userId){
        LogContext.instance().info("查询用户粉丝列表");
        return focusDao.getFollower(userId);
    }

    public List<User> getFollowing(int userId){
        LogContext.instance().info("查询用户关注列表");
        return focusDao.getFollowing(userId);
    }
}
