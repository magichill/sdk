package com.bingdou.core.service.user;

import com.bingdou.core.cache.IUserCacheManager;
import com.bingdou.core.constants.UserConstants;
import com.bingdou.core.model.SafeInfo;
import com.bingdou.core.model.User;
import com.bingdou.core.model.UserToken;
import com.bingdou.core.repository.user.UserBaseDao;
import com.bingdou.tools.DateUtil;
import com.bingdou.tools.LogContext;
import com.bingdou.tools.ValidateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaoshan on 16-10-25.
 * 用户基础服务类
 */
@Service
public class UserBaseService {

    @Autowired
    private UserBaseDao userBaseDao;

    @Autowired
    private IUserCacheManager userCacheManager;

    /**
     * 通过手机号获取用户详细信息
     *
     * @param mobile 手机号
     * @return 用户对象
     */
    public User getDetailByMobile(String mobile) {
        if (StringUtils.isEmpty(mobile)
                || !ValidateUtil.isMobileNumber(mobile)) {
            return null;
        }
        return userBaseDao.getDetailByMobile(mobile);
    }

    /**
     * 通过登录名获取用户详细信息
     *
     * @param loginName 用户名
     * @return 用户对象
     */
    public User getDetailByLoginName(String loginName) {
        if (StringUtils.isEmpty(loginName))
            return null;
        return userBaseDao.getDetailByLoginName(loginName);
    }

    /**
     * 通过email获取用户详细信息
     *
     * @param email 邮箱
     * @return 用户对象
     */
    public User getDetailByEmail(String email) {
        if (StringUtils.isEmpty(email) || !ValidateUtil.isEmail(email))
            return null;
        return userBaseDao.getDetailByEmail(email);
    }

    /**
     * 通过用户ID获取用户详细信息
     *
     * @param userId 用户ID
     * @return 用户对象
     */
    public User getDetailById(Integer userId) {
        if (userId == null)
            return null;
        return userBaseDao.getDetailById(userId);
    }

    /**
     * 根据账号/邮箱/手机号获取用户详细信息
     *
     * @param account 账号/邮箱/手机号
     * @return 用户对象
     */
    public User getUserDetailByAccount(String account) {
        if (StringUtils.isEmpty(account))
            return null;
        User user;
        if (ValidateUtil.isMobileNumber(account)) {
            user = getDetailByMobile(account);
            if (user == null) {
                user = getDetailByLoginName(account);
            }
        } else if (account.contains("@")) {
            user = getDetailByEmail(account);
            if (user == null) {
                user = getDetailByLoginName(account);
            }
        } else {
            user = getDetailByLoginName(account);
        }
        return user;
    }

//    /**
//     * 更新用户余额
//     */
//    public void updateMoneyById(Integer userId, int oldUserMoney, MoneyLog moneyLog) throws Exception {
//        if (userId == null || userId <= 0 || moneyLog == null) {
//            throw new Exception("更新用户余额错误");
//        }
//        if (oldUserMoney != moneyLog.getMoneyBalance()) {
//            int count = userBaseDao.updateMoneyById(userId, moneyLog.getMoneyBalance());
//            if (count < 1)
//                throw new Exception("更新用户余额错误");
//            moneyLogDao.add(moneyLog);
//        }
//    }
//
//    public void insertOrUpdateVirtualMoney(int userId, int userVirtualMoneyOld, int osIndex,
//                                           MoneyLog moneyLog) throws Exception {
//        if (userId <= 0 || moneyLog == null || osIndex < 0) {
//            throw new Exception("插入(更新)用户币游戏余额错误");
//        }
//        Integer virtualMoneyFen = userBaseDao.getVirtualMoney(userId, osIndex);
//        if (virtualMoneyFen == null) {
//            LogContext.instance().info("没有游戏币余额记录,插入");
//            userBaseDao.insertVirtualMoney(userId, osIndex, moneyLog.getMoneyBalance());
//            moneyLogDao.add(moneyLog);
//        } else {
//            if (userVirtualMoneyOld != moneyLog.getMoneyBalance()) {
//                LogContext.instance().info("有游戏币余额记录,更新");
//                int count = userBaseDao.updateVirtualMoney(userId, osIndex, moneyLog.getMoneyBalance());
//                if (count < 1)
//                    throw new Exception("更新用户币游戏余额错误");
//                moneyLogDao.add(moneyLog);
//            }
//        }
//    }

    /**
     * 通过用户cpid或者id集合获取主键ID集合
     *
     * @param userIdList cpid或者id集合
     * @return 主键ID集合
     */
    public List<Integer> getIdsByUserIdList(List<String> userIdList) {
        if (userIdList == null || userIdList.isEmpty())
            return new ArrayList<Integer>();
        return userBaseDao.getIdsByUserIdList(userIdList);
    }

    /**
     * 通过用户主键ID集合获取用户列表
     *
     * @param idList 主键ID集合
     * @return 用户列表
     */
    public List<User> getUserInfoListByIdList(List<Integer> idList) {
        if (idList == null || idList.isEmpty())
            return new ArrayList<User>();
        return userBaseDao.getUserInfoListByIdList(idList);
    }

    /**
     * 检查用户token(客户端用)
     */

    public boolean checkUserToken4Client(int userId, String inputToken, String inputDevice,
                                         SafeInfo safeInfo) throws Exception {
        LogContext.instance().info("检查客户端令牌逻辑");
        return checkUserToken(userId, inputToken, inputDevice, safeInfo);
    }

    /**
     * 检查用户token(服务器端用)
     */
    public boolean checkUserToken4Server(int userId, String inputToken) {
        LogContext.instance().info("检查服务端令牌逻辑");
        return checkUserToken(userId, inputToken, "", null);
    }

    /**
     * 获取用户token对象
     */
    public UserToken getUserTokenObject(int userId) {
        LogContext.instance().info("获取TOKEN对象逻辑");
        UserToken userTokenFromRedis = userCacheManager.getUserToken(userId);
        if (userTokenFromRedis != null) {
            LogContext.instance().info("从缓存读取TOKEN对象");
            UserToken userToken = new UserToken();
            userToken.setToken(userTokenFromRedis.getToken());
            String vToken = userBaseDao.getUserVToken(userId);
            userToken.setValidateToken(vToken == null ? "" : vToken);
            if (StringUtils.isNotEmpty(userTokenFromRedis.getDevice()))
                userToken.setDevice(userTokenFromRedis.getDevice());
            if (StringUtils.isNotEmpty(userTokenFromRedis.getRequestSource()))
                userToken.setRequestSource(userTokenFromRedis.getRequestSource());
            return userToken;
        }
        LogContext.instance().info("从DB读取TOKEN对象");
        long expiredTime = DateUtil.getCurrentTimeSeconds() - UserConstants.USER_TOKEN_EXPIRE_SECONDS;
        UserToken userTokenFromDB = userBaseDao.getUserToken(userId, expiredTime);
        LogContext.instance().info("从DB读取的TOKEN对象是否为空:" + (userTokenFromDB == null));
        return userTokenFromDB;
    }

    /**
     * 更新token信息
     */
    public boolean updateToken(User user, String device, SafeInfo safeInfo, boolean isRegister) {
        LogContext.instance().info("更新TOKEN逻辑");
        if (user == null)
            return false;
        if (!isRegister) {
            LogContext.instance().info("不是注册请求,进入检查是否是同设备和TOKEN不顶号配置检查");
            UserToken userToken = getUserTokenObject(user.getId());
            boolean checkTokenOnlyByUserId = false;
            boolean isNoCheckTokenDevice = false;
            if (userToken != null) {
                checkTokenOnlyByUserId = checkTokenOnlyByUserId(safeInfo, userToken.getRequestSource());
                isNoCheckTokenDevice = StringUtils.isNotEmpty(device) && device.equals(userToken.getDevice());
            }
            if (userToken != null && (isNoCheckTokenDevice || checkTokenOnlyByUserId)) {
                LogContext.instance().info("不更新TOKEN,更新VTOKEN");
                String vToken = user.generateUserValidateToken();
                boolean updateSuccess = userBaseDao.updateUserValidateToken(user.getId(), vToken);
                boolean updateCacheSuccess = false;
                if (updateSuccess) {
                    updateCacheSuccess = userCacheManager.setUserValidateToken(user.getId(), vToken);
                    user.setvToken(vToken);
                }
                LogContext.instance().info("更新VTOKEN缓存结果:" + updateCacheSuccess);
                user.setToken(userToken.getToken());
                return true;
            }
        }
        String token = user.generateUserToken();
        String validateToken = user.generateUserValidateToken();
        boolean updateDBTokenResult = userBaseDao.addOrUpdateUserToken(user.getId(), token,
                validateToken, device, safeInfo.getRequestSource());
        LogContext.instance().info("更新DB TOKEN");
        if (!updateDBTokenResult) {
            LogContext.instance().error("更新DB TOKEN失败");
            return false;
        }
        boolean updateRedisUserTokenResult = userCacheManager.setUserToken(user.getId(), token,
                device, safeInfo.getRequestSource());
        boolean updateRedisUserVTokenResult = userCacheManager.setUserValidateToken(user.getId(), validateToken);
        boolean isSuccess = updateRedisUserTokenResult && updateRedisUserVTokenResult;
        user.setToken(token);
        user.setvToken(validateToken);
        LogContext.instance().info("更新缓存TOKEN");
        if (!isSuccess) {
            LogContext.instance().error("更新缓存TOKEN失败");
            return false;
        }
        return true;
    }

    /**
     * 注销token信息
     */
    public boolean deleteToken(int userId) {
        boolean updateDBResult = false;
        try {
            LogContext.instance().info("删除token逻辑");
            LogContext.instance().info("清空数据库token");
            int updateCount = userBaseDao.clearUserToken(userId);
            updateDBResult = updateCount > 0;
            LogContext.instance().info("db token:" + updateDBResult);
            LogContext.instance().info("清空缓存服务器token");
            boolean updateRedisUserTokenResult = userCacheManager.deleteUserToken(userId);
            boolean updateRedisUserVTokenResult = userCacheManager.deleteUserValidateToken(userId);
            LogContext.instance().info("redis token result:" + updateRedisUserTokenResult);
            LogContext.instance().info("redis vtoken result:" + updateRedisUserVTokenResult);
            boolean result = updateDBResult && updateRedisUserTokenResult && updateRedisUserVTokenResult;
            LogContext.instance().info("注销token结果:" + result);
        } catch (Exception e) {
            LogContext.instance().error(e, "清空TOKEN失败");
        }
        return updateDBResult;
    }

    public boolean createUser(User user, String appId, String ip, String uid, String ua, int safeLevel) throws Exception {
        LogContext.instance().info("插入新用户数据");
        userBaseDao.addUserIndex(user);
        if (user.getId() <= 0) {
            LogContext.instance().error("插入用户索引记录失败");
            return false;
        }
        LogContext.instance().info("插入用户索引记录成功");
        user.setCpId(user.generateCpId());
        LogContext.instance().info("更新用户索引记录");
        int updateCpIdByIdCount = userBaseDao.updateCpIdById(user);
        if (updateCpIdByIdCount < 1) {
            throw new Exception("更新用户索引记录失败");
        }
        LogContext.instance().info("插入用户记录");
        userBaseDao.addUser(user, appId);
        LogContext.instance().info("插入用户详细记录");
        userBaseDao.addUserDetail(user.getId(), ip, uid, ua, safeLevel);
        return true;
    }

    /**
     * 通过cpid或者登录名或者主键ID获取用户详细信息
     */
    public User getDetailByIdOrCpIdOrLoginName(String input) {
        if (StringUtils.isEmpty(input)) {
            return null;
        }
        if (input.startsWith(UserConstants.CP_PREFIX)) {
            Integer userId = getIdByCpId(input);
            if (userId == null) {
                return getDetailByLoginName(input);
            } else {
                return getDetailById(userId);
            }
        } else {
            User user = getDetailByLoginName(input);
            if (user == null && ValidateUtil.isInteger(input))
                user = getDetailById(Integer.parseInt(input));
            return user;
        }
    }

    /**
     * 登陆错误清零
     */
    public void clearLoginError(int userId) {
        userBaseDao.clearLoginError(userId);
    }

    /**
     * 修改密码
     */
    public int updatePassword(int userId, String encodedPassword, String salt) {
        return userBaseDao.updatePassword(userId, encodedPassword, salt);
    }

    /**
     * 登录名是否存在
     */
    public boolean isExistsLoginName(String loginName) {
        return userBaseDao.isExistsLoginName(loginName);
    }

    /**
     * 根据设备获取用户
     *
     * @param device 设备(游客登录逻辑用)
     * @return 用户信息
     */
    public User getUserByDevice(String device) {
        if (StringUtils.isEmpty(device))
            return null;
        Integer userId = userBaseDao.getUserIdCountByDevice(device);
        if (userId == null || userId <= 0)
            return null;
        return userBaseDao.getDetailById(userId);
    }

    public boolean existNickname(String nickname) throws Exception {
        if (StringUtils.isEmpty(nickname)) {
            throw new Exception("昵称为空");
        }
        int count = userBaseDao.getCountByNickname(nickname);
        return count > 0;
    }

    public boolean updateNickname(int userId, String nickname) {
        if (userId <= 0 || StringUtils.isEmpty(nickname))
            return false;
        int updateIndexCount = userBaseDao.updateNickname4Index(userId, nickname);
        int updateMemberCount = userBaseDao.updateNickname4Member(userId, nickname);
        return (updateIndexCount + updateMemberCount) >= 2;
    }

    public int getVirtualMoney(int userId, int osIndex, boolean isNewUser) {
        if (userId <= 0 || isNewUser)
            return 0;
        Integer money = userBaseDao.getVirtualMoney(userId, osIndex);
        return money == null ? 0 : money;
    }

    /**
     * 检查用户token
     */
    private boolean checkUserToken(int userId, String inputToken, String inputDevice, SafeInfo safeInfo) {
        if (userId <= 0 || StringUtils.isEmpty(inputToken))
            return false;
        UserToken userToken = getUserTokenObject(userId);
        if (userToken == null) {
            return false;
        }
        boolean isFromClient = StringUtils.isNotEmpty(inputDevice);
        String device = "";
        String tokenRequestSource = "";
        String token = userToken.getToken();
        if (isFromClient) {
            device = userToken.getDevice();
            tokenRequestSource = userToken.getRequestSource();
        }
        boolean checkTokenOnlyByUserId = false;
        if (safeInfo != null && isFromClient) {
            checkTokenOnlyByUserId = checkTokenOnlyByUserId(safeInfo, tokenRequestSource);
        }
        if (isFromClient && !checkTokenOnlyByUserId)
            return inputToken.equals(token) && inputDevice.equals(device);
        else
            return inputToken.equals(token);
    }

    private boolean checkTokenOnlyByUserId(SafeInfo safeInfo, String tokenRequestSource) {
        LogContext.instance().info("获取TOKEN配置");
        String vRequestSources = safeInfo.getValidTokenOnlyUserIdRs();
        if (StringUtils.isEmpty(vRequestSources)) {
            return false;
        }
        String[] vRequestSourceArray = vRequestSources.split(",");
        if (vRequestSourceArray.length == 0) {
            return false;
        }
        for (String str : vRequestSourceArray) {
            if (str.equals(tokenRequestSource)) {
                return true;
            }
        }
        return false;
    }

    private Integer getIdByCpId(String cpId) {
        if (StringUtils.isEmpty(cpId))
            return null;
        return userBaseDao.getIdByCpId(cpId);
    }
}
