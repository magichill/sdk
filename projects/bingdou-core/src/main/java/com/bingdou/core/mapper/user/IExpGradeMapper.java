package com.bingdou.core.mapper.user;

import com.bingdou.core.model.UserExpGrade;
import org.apache.ibatis.annotations.Param;

/**
 * Created by gaoshan on 17/4/28.
 */
public interface IExpGradeMapper {

    Float getExpAmountByLevelId(int levelId);

    UserExpGrade getUserExpGradeInfo(int userId);

    int updateUserExp(@Param("userId") int userId, @Param("levelId") int levelId, @Param("expValue") long expValue);

    int updateUserExpValue(@Param("userId") int userId, @Param("expValue") long expValue);

    void insertUserExp(@Param("userId") int userId, @Param("levelId") int levelId, @Param("expValue") long expValue);

    Integer getVipLevelByExpAmount(Integer expAmount);
}
