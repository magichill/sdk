package com.bingdou.core.repository.user;

import com.bingdou.core.mapper.user.IFocusMapper;
import com.bingdou.core.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gaoshan on 17/1/31.
 */
@Repository
public class FocusDao {

    @Autowired
    private IFocusMapper focusMapper;

    public Integer checkFocusInfoStatus(int userId,int followId){
        Integer status = focusMapper.checkFocusInfoStatus(userId,followId);
        return status;
    }
    public Integer getFollowerCount(int userId){
        return focusMapper.getFollowerCount(userId);
    }

    public Integer getFansCount(int userId){
        return focusMapper.getFansCount(userId);
    }

    public void insertFocus(int userId,int followId){
        focusMapper.insertFocusInfo(userId,followId);
    }

    public void updateFocusInfo(int userId,int followId,int status){
        focusMapper.updateFocusInfo(userId,followId,status);
    }

    public List<User> getFollower(int userId,int start,int limit){
        return focusMapper.getFollower(userId,start,limit);
    }

    public List<User> getFollowing(int userId,int start,int limit){
        return focusMapper.getFollowing(userId, start,limit);
    }

    public List<User> getPopularUsers() {
        return focusMapper.getPopularUsers();
    }


}
