package com.bingdou.core.repository.user;

import com.bingdou.core.mapper.user.IExpGradeMapper;
import com.bingdou.core.model.UserExpGrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by gaoshan on 25/05/17.
 */
@Repository
public class ExpGradeDao {

    @Autowired
    private IExpGradeMapper expGradeMapper;

    public Integer getExpAdmountByLevelId(Integer levelId){
        return expGradeMapper.getExpAmountByLevelId(levelId);
    }

    public UserExpGrade getUserExpGrade(Integer userId){
        return expGradeMapper.getUserExpGradeInfo(userId);
    }

    public Integer getVipLevelByExpAmount(Integer expAmount){
        return expGradeMapper.getVipLevelByExpAmount(expAmount);
    }

    public void insertUserExp(Integer userId,Integer levelId,Integer expValue){
        expGradeMapper.insertUserExp(userId,levelId,expValue);
    }

    public int updateUserExp(Integer userId,Integer levelId,Integer expValue){
        return expGradeMapper.updateUserExp(userId,levelId,expValue);
    }

    public int updateUserExpValue(Integer userId,Integer expValue){
        return expGradeMapper.updateUserExpValue(userId,expValue);
    }
}
