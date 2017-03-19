package com.bingdou.core.mapper.user;

import com.bingdou.core.model.User;
import com.bingdou.core.model.UserToken;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by gaoshan on 16-10-25.
 */
public interface IUserBaseMapper {

    Integer getIdByMobile(String mobile);

    User getDetailById(@Param("userId") Integer userId);

    Integer getIdByLoginName(String loginName);

    Integer getIdByEmail(String email);

    Integer getIdByCpId(String cpId);

    int updateMoneyById(@Param("userId") Integer userId, @Param("money") Integer money);

    List<Integer> getIdsByUserIdList(@Param("userIdList") List<String> userIdList);

    List<User> getUserInfoListByIdList(@Param("idList") List<Integer> idList);

    List<User> getCertificateUserList();

    int existUserToken(@Param("userId") Integer userId);

    UserToken getUserToken(@Param("userId") Integer userId, @Param("expiredTime") long expiredTime);

    String getUserVToken(@Param("userId") int userId);

    void insertUserToken(@Param("userId") Integer userId, @Param("validateToken") String validateToken,
                         @Param("token") String token, @Param("device") String device,
                         @Param("requestSource") String requestSource);

    int updateUserToken(@Param("userId") Integer userId, @Param("validateToken") String validateToken,
                        @Param("token") String token, @Param("device") String device,
                        @Param("requestSource") String requestSource);

    int updateUserValidateToken(@Param("validateToken") String validateToken, @Param("userId") Integer userId);

    int clearUserToken(Integer userId);

    void addUserIndex(User user);

    int updateCpIdById(User user);

    void addUser(@Param("userId") Integer userId, @Param("loginName") String loginName,@Param("nickname") String nickName,
                 @Param("wxOpenId") String wxOpenId, @Param("gender") int gender,@Param("password") String password,
                 @Param("salt") String salt, @Param("appId") String appId, @Param("cpId") String cpId,
                 @Param("mobile") String mobile,@Param("avatar") String avatar,
                 @Param("signature") String signature);

    void addUserDetail(@Param("userId") Integer userId, @Param("ip") String ip, @Param("uid") String uid,
                       @Param("ua") String ua, @Param("safeLevel") int safeLevel);

    void clearLoginError(@Param("userId") int userId);

    int updatePassword(@Param("userId") int userId, @Param("encodedPassword") String encodedPassword, @Param("salt") String salt);

    int getLoginNameCount(@Param("loginName") String loginName);

    Integer getUserIdCountByDevice(String device);

    int getCountByNickname(String nickname);

    int updateNickname4Member(@Param("userId") int userId, @Param("nickname") String nickname,
                               @Param("gender") Integer gender,@Param("signature") String signature,
                              @Param("avatar") String avatar);

    int updateNickname4Index(@Param("userId") int userId, @Param("nickname") String nickname,
                             @Param("gender") Integer gender,@Param("signature") String signature,
                             @Param("avatar") String avatar);

    Integer getVirtualMoneyFen(@Param("userId") int userId, @Param("os") int osIndex);

    int updateVirtualMoney(@Param("userId") int userId, @Param("os") int osIndex,
                           @Param("moneyFen") int moneyFen);

    void insertVirtualMoney(@Param("userId") int userId, @Param("os") int osIndex,
                            @Param("moneyFen") int moneyFen);
}
