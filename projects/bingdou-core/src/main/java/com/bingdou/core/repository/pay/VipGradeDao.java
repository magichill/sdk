package com.bingdou.core.repository.pay;

import com.bingdou.core.mapper.pay.IVipGradeMapper;
import com.bingdou.core.model.UserVipGrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class VipGradeDao {

    @Autowired
    private IVipGradeMapper vipGradeMapper;

    public Float getVipRechargeAmountByLevelId(int vipLevelId) {
        return vipGradeMapper.getVipRechargeAmountByLevelId(vipLevelId);
    }

    public UserVipGrade getUserVipGradeInfo(int userId) {
        return vipGradeMapper.getUserVipGradeInfo(userId);
    }

    public Integer getVipLevelByRechargeAmount(float amount) {
        return vipGradeMapper.getVipLevelByRechargeAmount(amount);
    }

    public void insertUserVip(int userId, int levelId, float money) {
        vipGradeMapper.insertUserVip(userId, levelId, money);
    }

    public int updateUserVip(int userId, int levelId, float money) {
        return vipGradeMapper.updateUserVip(userId, levelId, money);
    }

    public int updateUserVipMoney(int userId, float money) {
        return vipGradeMapper.updateUserVipMoney(userId, money);
    }
}
