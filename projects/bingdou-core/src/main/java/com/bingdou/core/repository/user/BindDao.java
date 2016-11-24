package com.bingdou.core.repository.user;

import com.bingdou.core.mapper.user.IBindMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BindDao {

    @Autowired
    private IBindMapper bindMapper;

    public int updateMobile(int userId, String mobile) {
        return bindMapper.updateMobile(userId, mobile)
                + bindMapper.updateMobileInMemberIndex(userId, mobile);
    }

    public int updateEmail(int userId, String email) {
        return bindMapper.updateEmail(userId, email)
                + bindMapper.updateEmailInMemberIndex(userId, email);
    }

    public int updateSafeLevel(int userId, int safeLevel) {
        return bindMapper.updateSafeLevel(userId, safeLevel);
    }

}
