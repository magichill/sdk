package com.bingdou.core.mapper.pay;

import com.bingdou.core.model.AliPayNoPwdAuth;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * ÷ß∏∂±¶√‚√‹ ⁄»®
 * Created by gaoshan on 16/12/28.
 */
public interface IAliPayNoPwdAuthMapper {

    int getStatusCountByStatus(@Param("userId") int userId, @Param("status") int status);

    AliPayNoPwdAuth getAuthInfo(@Param("userId") int userId);

    int updateSignStatus(@Param("userId") int userId, @Param("status") int status);

    void createAuth(@Param("userId") int userId, @Param("externalSignNo") String externalSignNo);

    AliPayNoPwdAuth getUserIdByExternalSignNo(String externalSignNo);

    int updateByExternalSignNo(Map<String, String> signMap);

    void insertAuthSignDetail(Map<String, String> signMap);

}
