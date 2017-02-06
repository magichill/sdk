package com.bingdou.core.mapper.pay;

import com.bingdou.core.model.UserVipGrade;
import org.apache.ibatis.annotations.Param;

public interface IVipGradeMapper {

    Float getVipRechargeAmountByLevelId(int levelId);

    UserVipGrade getUserVipGradeInfo(int userId);

    int updateUserVip(@Param("userId") int userId, @Param("levelId") int levelId, @Param("money") float money);

    int updateUserVipMoney(@Param("userId") int userId, @Param("money") float money);

    void insertUserVip(@Param("userId") int userId, @Param("levelId") int levelId, @Param("money") float money);

    Integer getVipLevelByRechargeAmount(float rechargeAmount);
}
