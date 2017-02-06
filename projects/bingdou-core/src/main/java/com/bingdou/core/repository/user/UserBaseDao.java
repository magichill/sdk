package com.bingdou.core.repository.user;

import com.bingdou.core.mapper.user.IUserBaseMapper;
import com.bingdou.core.model.User;
import com.bingdou.core.model.UserToken;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户数据DAO
 */
@Repository
public class UserBaseDao {

    @Autowired
    private IUserBaseMapper userBaseMapper;

    public Integer getIdByLoginName(String loginName) {
        return userBaseMapper.getIdByLoginName(loginName);
    }

    public User getDetailByMobile(String mobile) {
        Integer userId = getIdByMobile(mobile);
        if (userId == null)
            return null;
        return userBaseMapper.getDetailById(userId);
    }

    public User getDetailByLoginName(String loginName) {
        Integer userId = getIdByLoginName(loginName);
        if (userId == null)
            return null;
        return userBaseMapper.getDetailById(userId);
    }

    public User getDetailByEmail(String email) {
        Integer userId = getIdByEmail(email);
        if (userId == null)
            return null;
        return userBaseMapper.getDetailById(userId);
    }

    public List<Integer> getIdsByUserIdList(List<String> userIdList) {
        if (userIdList == null || userIdList.isEmpty())
            return new ArrayList<Integer>();
        return userBaseMapper.getIdsByUserIdList(userIdList);
    }

    public List<User> getUserInfoListByIdList(List<Integer> idList) {
        if (idList == null || idList.isEmpty())
            return new ArrayList<User>();
        return userBaseMapper.getUserInfoListByIdList(idList);
    }

    public UserToken getUserToken(Integer userId, long expiredTime) {
        if (userId <= 0)
            return null;
        return userBaseMapper.getUserToken(userId, expiredTime);
    }

    public String getUserVToken(int userId) {
        return userBaseMapper.getUserVToken(userId);
    }

    public boolean addOrUpdateUserToken(Integer userId, String token, String validateToken, String device,
                                        String requestSource) {
        if (userId == null || userId <= 0 || StringUtils.isEmpty(token) || StringUtils.isEmpty(validateToken)) {
            return false;
        }
        if (existUserToken(userId)) {
            userBaseMapper.updateUserToken(userId, validateToken, token, device, requestSource);
        } else {
            userBaseMapper.insertUserToken(userId, validateToken, token, device, requestSource);
        }
        return true;
    }

    public boolean updateUserValidateToken(Integer userId, String validateToken) {
        if (userId == null || userId <= 0 || StringUtils.isEmpty(validateToken)) {
            return false;
        }
        int count = userBaseMapper.updateUserValidateToken(validateToken, userId);
        return count == 1;
    }

    public int clearUserToken(Integer userId) {
        if (userId == null || userId <= 0)
            return 0;
        return userBaseMapper.clearUserToken(userId);
    }

    public User getDetailById(Integer userId) {
        return userBaseMapper.getDetailById(userId);
    }

    public int updateMoneyById(Integer userId, Integer money) {
        return userBaseMapper.updateMoneyById(userId, money);
    }

    public Integer getIdByCpId(String cpId) {
        return userBaseMapper.getIdByCpId(cpId);
    }

    public void addUserIndex(User user) {
        userBaseMapper.addUserIndex(user);
    }

    public int updateCpIdById(User user) {
        return userBaseMapper.updateCpIdById(user);
    }

    public void addUser(User user, String appId) {
        userBaseMapper.addUser(user.getId(), user.getLoginName(), user.getWxOpenId(), user.getPassword(),
                user.getSalt(), appId, user.getCpId(), user.getMobile(),user.getAvatar(),user.getSignature());
    }

    public void addUserDetail(Integer userId, String ip, String uid, String ua, int safeLevel) {
        userBaseMapper.addUserDetail(userId, ip, uid, ua, safeLevel);
    }

    private boolean existUserToken(Integer userId) {
        int count = userBaseMapper.existUserToken(userId);
        return count > 0;
    }

    private Integer getIdByMobile(String mobile) {
        return userBaseMapper.getIdByMobile(mobile);
    }

    private Integer getIdByEmail(String email) {
        return userBaseMapper.getIdByEmail(email);
    }

    public void clearLoginError(int userId) {
        userBaseMapper.clearLoginError(userId);
    }

    public int updatePassword(int userId, String encodedPassword, String salt) {
        return userBaseMapper.updatePassword(userId, encodedPassword, salt);
    }

    public boolean isExistsLoginName(String loginName) {
        int count = userBaseMapper.getLoginNameCount(loginName);
        return count > 0;
    }

    public Integer getUserIdCountByDevice(String device) {
        return userBaseMapper.getUserIdCountByDevice(device);
    }

    public int getCountByNickname(String nickname) {
        return userBaseMapper.getCountByNickname(nickname);
    }

    public int updateNickname4Member(int userId, String nickname) {
        return userBaseMapper.updateNickname4Member(userId, nickname);
    }

    public int updateNickname4Index(int userId, String nickname) {
        return userBaseMapper.updateNickname4Index(userId, nickname);
    }

    public Integer getVirtualMoney(int userId, int osIndex) {
        return userBaseMapper.getVirtualMoneyFen(userId, osIndex);
    }

    public int updateVirtualMoney(int userId, int osIndex, int moneyFen) {
        return userBaseMapper.updateVirtualMoney(userId, osIndex, moneyFen);
    }

    public void insertVirtualMoney(int userId, int osIndex, int moneyFen) {
        userBaseMapper.insertVirtualMoney(userId, osIndex, moneyFen);
    }

}
