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
 * VIP�ȼ�������
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
            throw new Exception("�Ƿ��û�ID");
        }
        //�û��ȼ��߼�����ѯvip������
        if (safeService.isInVipBlackList(userId)) {
            LogContext.instance().warn("�û���VIP�������б���,����ȡʵ��VIP�ȼ�");
            UserVipGrade userVipGrade = new UserVipGrade();
            userVipGrade.setInBlackList(true);
            return userVipGrade;
        }
        //��user_vip�����ѯ���û���vip��Ϣ���û�Ϊ�գ�����VIP�ĳ�ֵ��Ϣ
        UserVipGrade userVipGrade = vipGradeDao.getUserVipGradeInfo(userId);
        if (userVipGrade == null) {
            LogContext.instance().info("�û�������VIP,����VIP1������Ϣ");
            userVipGrade = new UserVipGrade();
            float level1RechargeAmount = getVipRechargeAmountByLevelId(1);
            userVipGrade.setNewVipUser(true);
            userVipGrade.setUserLevelId(0);
            userVipGrade.setNextLevelNeedRechargeAmount(level1RechargeAmount);
            userVipGrade.setNextLevelRechargeAmount(level1RechargeAmount);
            return userVipGrade;
        }
        LogContext.instance().info("�û�VIP�ȼ�:" + userVipGrade);
        //�ж��Ƿ��Դﵽ���ȼ������û�ﵽ��������һ����ĳ�ֵ���
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
        LogContext.instance().info("�����û�VIP��Ϣ");
        if (userVipGrade.isInBlackList()) {
            LogContext.instance().warn("�û���VIP��������,ֱ�ӽ���");
            return;
        }
        int vipLevelId;
        boolean isLevelUp = false;
        float amountYuan;
        if (userVipGrade.isNewVipUser()) {
            LogContext.instance().info("�û�����VIP�û�,�����¼�¼");
            amountYuan = NumberUtil.convertYuanFromFen(payAmount);
            LogContext.instance().info("��ֵ���(Ԫ):" + amountYuan, "��ֵ���(��):" + payAmount);
            vipLevelId = getVipLevelByRechargeAmount(amountYuan);
            if (vipLevelId > 0) {
                LogContext.instance().info("VIP�ȼ�����(0-" + vipLevelId + "),���ͳ䷵��");
                isLevelUp = true;
//                chargeBackService.giveProps(0, vipLevelId, userId);
            }
        } else {
            LogContext.instance().info("�û�������VIP�û�,���¼�¼");
            amountYuan = userVipGrade.getMoney() + NumberUtil.convertYuanFromFen(payAmount);
            LogContext.instance().info("��ֵ���(Ԫ):" + amountYuan, "��ֵ���(��):" + payAmount);
            vipLevelId = getVipLevelByRechargeAmount(amountYuan);
            if (vipLevelId > userVipGrade.getUserLevelId()) {
                LogContext.instance().info("VIP�ȼ�����(" + userVipGrade.getUserLevelId()
                        + "-" + vipLevelId + "),���ͳ䷵��");
                isLevelUp = true;
//                chargeBackService.giveProps(userVipGrade.getUserLevelId(), vipLevelId, userId);
            }
        }
        boolean updateVipResult = insertOrUpdateUserVipInfo(isLevelUp, userVipGrade.isNewVipUser(),
                amountYuan, userId, vipLevelId);
        LogContext.instance().info("����(����)VIP��Ϣ���:" + updateVipResult);
        if (!updateVipResult)
            LogContext.instance().error("����(����)VIP��Ϣ����");
    }

    private float getVipRechargeAmountByLevelId(int levelId) {
        Float amount = vipGradeDao.getVipRechargeAmountByLevelId(levelId);
        return amount == null ? 0 : amount;
    }

}
