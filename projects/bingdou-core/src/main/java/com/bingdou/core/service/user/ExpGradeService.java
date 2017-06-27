package com.bingdou.core.service.user;

import com.bingdou.core.constants.UserConstants;
import com.bingdou.core.model.UserExpGrade;
import com.bingdou.core.repository.user.ExpGradeDao;
import com.bingdou.core.service.safe.SafeService;
import com.bingdou.tools.LogContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by gaoshan on 26/05/17.
 */
@Service
public class ExpGradeService {

    @Autowired
    private ExpGradeDao expGradeDao;
    @Autowired
    private SafeService safeService;


    public boolean insertOrUpdateExpGradeInfo(boolean isLevelUp, boolean isNewVipUser, int expValue, int userId, int levelId) {
        if (isNewVipUser) {
            expGradeDao.insertUserExp(userId, levelId, expValue);
            return true;
        }
        int count;
        if (isLevelUp) {
            count = expGradeDao.updateUserExp(userId, levelId, expValue);
        } else {
            count = expGradeDao.updateUserExpValue(userId, expValue);
        }
        return count >= 1;
    }

    public UserExpGrade getUserExpGradeInfo(int userId) throws Exception {
        if (userId <= 0) {
            throw new Exception("非法用户ID");
        }
        //用户等级逻辑，查询vip黑名单
//        if (safeService.isInVipBlackList(userId)) {
//            LogContext.instance().warn("用户在VIP黑名单列表中,不获取实际VIP等级");
//            UserExpGrade UserExpGrade = new UserExpGrade();
//            return UserExpGrade;
//        }
        //在exp_grade表里查询该用户的经验信息，用户为空，返回VIP的充值信息
        UserExpGrade userExpGrade = expGradeDao.getUserExpGrade(userId);
        if (userExpGrade == null) {
            LogContext.instance().info("用户还不是VIP,返回VIP1升级信息");
            userExpGrade = new UserExpGrade();
            int level1RechargeAmount = getExpAmountByLevelId(1);
            userExpGrade.setNewVipUser(true);
            userExpGrade.setUserLevelId(0);
            userExpGrade.setNextLevelExpAmount(level1RechargeAmount);
            userExpGrade.setNextLevelNeedExpAmount(level1RechargeAmount);
            return userExpGrade;
        }
        LogContext.instance().info("用户经验等级:" + userExpGrade);
        //判断是否以达到最大等级，如果没达到，计算下一级别的充值金额
        if (userExpGrade.getUserLevelId() >= UserConstants.TOP_VIP_LEVEL_ID) {
            userExpGrade.setNextLevelExpAmount(0);
            userExpGrade.setNextLevelNeedExpAmount(0);
        } else {
            int nextLevelRechargeAmount = getExpAmountByLevelId(userExpGrade.getUserLevelId() + 1);
            userExpGrade.setNextLevelNeedExpAmount(nextLevelRechargeAmount - userExpGrade.getExpAmount());
            userExpGrade.setNextLevelExpAmount(nextLevelRechargeAmount);
        }
        return userExpGrade;
    }

    public int getExpLevelByExpAmount(int amount) {
        Integer expLevelId = expGradeDao.getVipLevelByExpAmount(amount);
        return expLevelId == null ? 0 : expLevelId;
    }

    public void dealExp(int expAmount, int userId, UserExpGrade userExpGrade) throws Exception {
        LogContext.instance().info("处理用户经验信息");
        int vipLevelId;
        int increaseExp;
        boolean isLevelUp = false;
        if (userExpGrade.isNewVipUser()) {
            LogContext.instance().info("用户是新用户,插入新记录");
            increaseExp = expAmount;
            LogContext.instance().info("经验增长" + increaseExp);
            vipLevelId = getExpLevelByExpAmount(increaseExp);
            if (vipLevelId > 0) {
                LogContext.instance().info("经验等级上升(0-" + vipLevelId + ")");
                isLevelUp = true;
            }
        } else {
            LogContext.instance().info("用户不是新用户,更新记录");
            increaseExp = userExpGrade.getExpAmount() + expAmount;
            LogContext.instance().info("经验增长:" + expAmount);
            vipLevelId = getExpLevelByExpAmount(increaseExp);
            if (vipLevelId > userExpGrade.getUserLevelId()) {
                LogContext.instance().info("经验等级上升(" + userExpGrade.getUserLevelId()
                        + "-" + vipLevelId + ")");
                isLevelUp = true;
            }
        }
        boolean updateVipResult = insertOrUpdateExpGradeInfo(isLevelUp, userExpGrade.isNewVipUser(),
                increaseExp, userId, vipLevelId);
        LogContext.instance().info("更新(插入)用户经验信息结果:" + updateVipResult);
        if (!updateVipResult)
            LogContext.instance().error("更新(插入)用户经验信息错误");
    }

    private int getExpAmountByLevelId(int levelId) {
        Integer amount = expGradeDao.getExpAdmountByLevelId(levelId);
        return amount == null ? 0 : amount;
    }
}
