package com.bingdou.userserver.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 修改密码请求类
 */
public class ModifyPasswordRequest extends BaseRequest {

    /**
     * 用户账号:用户名 / CP ID
     */
    @SerializedName("account")
    private String account;
    /**
     * 旧密码
     */
    @SerializedName("old_password")
    private String oldPassword;
    /**
     * 新密码
     */
    @SerializedName("new_password")
    private String newPassword;
    /**
     * 验证码
     */
    @SerializedName("validation_code")
    private String validationCode;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getValidationCode() {
        return validationCode;
    }

    public void setValidationCode(String validationCode) {
        this.validationCode = validationCode;
    }

    @Override
    protected String getLoggerName() {
        return "ModifyPasswordRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        ModifyPasswordRequest request = JsonUtil.jsonStr2Bean(requestString, ModifyPasswordRequest.class);
        this.account = request.getAccount();
        this.oldPassword = request.getOldPassword();
        this.newPassword = request.getNewPassword();
        this.validationCode = request.getValidationCode();
        return request;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
