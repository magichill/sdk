package com.bingdou.core.repository.user;

import com.bingdou.core.mapper.user.IFocusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by gaoshan on 17/1/31.
 */
@Repository
public class FocusDao {

    @Autowired
    private IFocusMapper focusMapper;

    public boolean checkFocusInfo(int userId,int followId){
        int count = focusMapper.checkFocusInfo(userId,followId);
        return count > 0;
    }
    public int getFollowerCount(int userId){
        return focusMapper.getFollowerCount(userId);
    }

    public int getFansCount(int userId){
        return focusMapper.getFansCount(userId);
    }

    public void insertFocus(int userId,int followId){
        focusMapper.insertFocusInfo(userId,followId);
    }

    public void updateFocusInfo(int userId,int followId,int status){
        focusMapper.updateFocusInfo(userId,followId,status);
    }
}
