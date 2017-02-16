package com.bingdou.core.service.pay;

import com.bingdou.core.constants.UserConstants;
import com.bingdou.core.model.UserVipGrade;
import com.bingdou.core.repository.pay.VipGradeDao;
import com.bingdou.core.service.safe.SafeService;
import com.bingdou.tools.LogContext;
import com.bingdou.tools.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * VIP等级服务类
 * Created by gaoshan on 17/1/14.
 */
@Service
public class VipGradeService {

    @Autowired
    private VipGradeDao vipGradeDao;
    @Autowired
    private SafeService safeService;
//    @Autowired
//    private ChargeBackService chargeBackService;

    public boolean insertOrUpdateUserVipInfo(boolean isLevelUp, boolean isNewVipUser, float money, int userId, int levelId) {
        if (isNewVipUser) {
            vipGradeDao.insertUserVip(userId, levelId, money);
            return true;
        }
        int count;
        if (isLevelUp) {
            count = vipGradeDao.updateUserVip(userId, levelId, money);
        } else {
            count = vipGradeDao.updateUserVipMoney(userId, money);
        }
        return count >= 1;
    }

    public UserVipGrade getUserVipGradeInfo(int userId) throws Exception {
        if (userId <= 0) {
            throw new Exception("非法用户ID");
        }
        //用户等级逻辑，查询vip黑名单
        if (safeService.isInVipBlackList(userId)) {
            LogContext.instance().warn("用户在VIP黑名单列表中,不获取实际VIP等级");
            UserVipGrade userVipGrade = new UserVipGrade();
            userVipGrade.setInBlackList(true);
            return userVipGrade;
        }
        //在user_vip表里查询该用户的vip信息，用户为空，返回VIP的充值信息
        UserVipGrade userVipGrade = vipGradeDao.getUserVipGradeInfo(userId);
        if (userVipGrade == null) {
            LogContext.instance().info("用户还不是VIP,返回VIP1升级信息");
            userVipGrade = new UserVipGrade();
            float level1RechargeAmount = getVipRechargeAmountByLevelId(1);
            userVipGrade.setNewVipUser(true);
            userVipGrade.setUserLevelId(0);
            userVipGrade.setNextLevelNeedRechargeAmount(level1RechargeAmount);
            userVipGrade.setNextLevelRechargeAmount(level1RechargeAmount);
            return userVipGrade;
        }
        LogContext.instance().info("用户VIP等级:" + userVipGrade);
        //判断是否以达到最大等级，如果没达到，计算下一级别的充值金额
        if (userVipGrade.getUserLevelId() >= UserConstants.TOP_VIP_LEVEL_ID) {
            userVipGrade.setNextLevelNeedRechargeAmount(0f);
            userVipGrade.setNextLevelRechargeAmount(0f);
        } else {
            float nextLevelRechargeAmount = getVipRechargeAmountByLevelId(userVipGrade.getUserLevelId() + 1);
            userVipGrade.setNextLevelNeedRechargeAmount(nextLevelRechargeAmount - userVipGrade.getMoney());
            userVipGrade.setNextLevelRechargeAmount(nextLevelRechargeAmount);
        }
        return userVipGrade;
    }

    public int getVipLevelByRechargeAmount(float amount) {
        Integer vipLevelId = vipGradeDao.getVipLevelByRechargeAmount(amount);
        return vipLevelId == null ? 0 : vipLevelId;
    }

    public void dealVip(int payAmount, int userId, UserVipGrade userVipGrade) throws Exception {
        LogContext.instance().info("处理用户VIP信息");
        if (userVipGrade.isInBlackList()) {
            LogContext.instance().warn("用户在VIP黑名单中,直接结束");
            return;
        }
        int vipLevelId;
        boolean isLevelUp = false;
        float amountYuan;
        if (userVipGrade.isNewVipUser()) {
            LogContext.instance().info("用户是新VIP用户,插入新记录");
            amountYuan = NumberUtil.convertYuanFromFen(payAmount);
            LogContext.instance().info("充值金额(元):" + amountYuan, "充值金额(分):" + payAmount);
            vipLevelId = getVipLevelByRechargeAmount(amountYuan);
            if (vipLevelId > 0) {
                LogContext.instance().info("VIP等级上升(0-" + vipLevelId + "),赠送充返卡");
                isLevelUp = true;
//                chargeBackService.giveProps(0, vipLevelId, userId);
            }
        } else {
            LogContext.instance().info("用户不是新VIP用户,更新记录");
            amountYuan = userVipGrade.getMoney() + NumberUtil.convertYuanFromFen(payAmount);
            LogContext.instance().info("充值金额(元):" + amountYuan, "充值金额(分):" + payAmount);
            vipLevelId = getVipLevelByRechargeAmount(amountYuan);
            if (vipLevelId > userVipGrade.getUserLevelId()) {
                LogContext.instance().info("VIP等级上升(" + userVipGrade.getUserLevelId()
                        + "-" + vipLevelId + "),赠送充返卡");
                isLevelUp = true;
//                chargeBackService.giveProps(userVipGrade.getUserLevelId(), vipLevelId, userId);
            }
        }
        boolean updateVipResult = insertOrUpdateUserVipInfo(isLevelUp, userVipGrade.isNewVipUser(),
                amountYuan, userId, vipLevelId);
        LogContext.instance().info("更新(插入)VIP信息结果:" + updateVipResult);
        if (!updateVipResult)
            LogContext.instance().error("更新(插入)VIP信息错误");
    }

    private float getVipRechargeAmountByLevelId(int levelId) {
        Float amount = vipGradeDao.getVipRechargeAmountByLevelId(levelId);
        return amount == null ? 0 : amount;
    }

}
