package com.bingdou.core.repository.user;

import com.bingdou.core.mapper.user.IValidateCodeMapper;
import com.bingdou.core.model.ValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ValidateCodeDao {

    @Autowired
    private IValidateCodeMapper validateCodeMapper;

    public ValidateCode getValidateCode(String mobileOrEmail, int purpose, int type) {
        return validateCodeMapper.getValidateCode(mobileOrEmail, purpose, type);
    }

    public void insertValidateCode(ValidateCode validateCode) {
        validateCodeMapper.insertValidateCode(validateCode);
    }

    public int clearValidateCode(String mobileOrEmail, int purpose, int type) {
        return validateCodeMapper.clearValidateCode(mobileOrEmail, purpose, type);
    }

    public int updateValidateCode(ValidateCode validateCode) {
        return validateCodeMapper.updateValidateCode(validateCode);
    }

}
