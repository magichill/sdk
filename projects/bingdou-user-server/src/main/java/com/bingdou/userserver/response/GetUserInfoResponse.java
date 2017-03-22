package com.bingdou.userserver.response;

import com.bingdou.core.model.User;
import com.bingdou.userserver.constant.ResponseConstant;
import com.google.gson.annotations.SerializedName;

/**
 * 获取用户信息请求响应
 */
public class GetUserInfoResponse extends UserProfileResponse {

    @SerializedName("focus_status")
    private int focusStatus;

    public int getFocusStatus() {
        return focusStatus;
    }

    public void setFocusStatus(int focusStatus) {
        this.focusStatus = focusStatus;
    }

    public void parseFromUser(User user, int certificationStatus, int focusStatus){
        if (user == null)
            return;
        setUserPrimaryId(user.getId());
        setNickName(user.getNickName());
        setGender(user.getGender());
        setLevel(user.getVipLevel());
        setAvatar(user.getAvatar());
        setCpIdOrId(user.getCpId());
        setSignature(user.getSignature()==null? ResponseConstant.DEFAULT_SIGNATURE:user.getSignature());
        setCertificationStatus(certificationStatus);
        setFocusStatus(focusStatus);
        setSignature(user.getSignature());
        setMobile(user.getMobile());
        setCity("");
        setOpenId("");
        setLongtitude(0f);
        setLatitude(0f);

    }
//    @SerializedName("total_props_num")
//    private int totalPropsCount = 0;
//    @SerializedName("total_vouhcer_num")
//    private int totalVoucherCount = 0;
//
//    public int getTotalPropsCount() {
//        return totalPropsCount;
//    }
//
//    public void setTotalPropsCount(int totalPropsCount) {
//        this.totalPropsCount = totalPropsCount;
//    }
//
//    public int getTotalVoucherCount() {
//        return totalVoucherCount;
//    }
//
//    public void setTotalVoucherCount(int totalVoucherCount) {
//        this.totalVoucherCount = totalVoucherCount;
//    }
}
