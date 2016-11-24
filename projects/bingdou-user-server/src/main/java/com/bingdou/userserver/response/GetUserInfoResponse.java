package com.bingdou.userserver.response;

import com.google.gson.annotations.SerializedName;

/**
 * ��ȡ�û���Ϣ������Ӧ
 */
public class GetUserInfoResponse extends LoginResponse {

    @SerializedName("total_props_num")
    private int totalPropsCount = 0;
    @SerializedName("total_vouhcer_num")
    private int totalVoucherCount = 0;

    public int getTotalPropsCount() {
        return totalPropsCount;
    }

    public void setTotalPropsCount(int totalPropsCount) {
        this.totalPropsCount = totalPropsCount;
    }

    public int getTotalVoucherCount() {
        return totalVoucherCount;
    }

    public void setTotalVoucherCount(int totalVoucherCount) {
        this.totalVoucherCount = totalVoucherCount;
    }
}
