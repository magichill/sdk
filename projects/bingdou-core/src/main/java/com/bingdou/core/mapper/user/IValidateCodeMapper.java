package com.bingdou.core.mapper.user;


import com.bingdou.core.model.ValidateCode;
import org.apache.ibatis.annotations.Param;

public interface IValidateCodeMapper {

    ValidateCode getValidateCode(@Param("mobileOrEmail") String mobileOrEmail, @Param("purpose") int purpose,
                                 @Param("type") int type);

    void insertValidateCode(ValidateCode validateCode);

    int clearValidateCode(@Param("mobileOrEmail") String mobileOrEmail, @Param("purpose") int purpose,
                          @Param("type") int type);

    int updateValidateCode(ValidateCode validateCode);

}
