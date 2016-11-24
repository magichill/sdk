package com.bingdou.core.repository.user;

import com.bingdou.core.mapper.user.ILostPasswordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LostPasswordDao {

    @Autowired
    private ILostPasswordMapper lostPasswordMapper;

    public int updatePassword(int userId, String encodedPassword, String salt) {
        return lostPasswordMapper.updatePassword(userId, encodedPassword, salt);
    }
}
