package com.bingdou.core.helper;

import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 设备信息请求对象
 */
public class DeviceInfo {

    @SerializedName("mac")
    private String mac;
    @SerializedName("imei")
    private String imei;
    @SerializedName("imsi")
    private String imsi;
    @SerializedName("net_type")
    private String netType;
    @SerializedName("os")
    private int os;
    @SerializedName("os_version")
    private String osVersion;
    @SerializedName("brand")
    private String brand;
    @SerializedName("model")
    private String model;
    @SerializedName("base_band")
    private String baseBand;
    @SerializedName("kernel")
    private String kernel;
    @SerializedName("lac")
    private String lac;
    @SerializedName("cell_id")
    private String cellId;
    @SerializedName("ios_info")
    private IosInfo iosInfo;
    @SerializedName("android_info")
    private AndroidInfo androidInfo;

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getNetType() {
        return netType;
    }

    public void setNetType(String netType) {
        this.netType = netType;
    }

    public int getOs() {
        return os;
    }

    public void setOs(int os) {
        this.os = os;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public IosInfo getIosInfo() {
        return iosInfo;
    }

    public void setIosInfo(IosInfo iosInfo) {
        this.iosInfo = iosInfo;
    }

    public AndroidInfo getAndroidInfo() {
        return androidInfo;
    }

    public void setAndroidInfo(AndroidInfo androidInfo) {
        this.androidInfo = androidInfo;
    }

    public String getBaseBand() {
        return baseBand;
    }

    public void setBaseBand(String baseBand) {
        this.baseBand = baseBand;
    }

    public String getKernel() {
        return kernel;
    }

    public void setKernel(String kernel) {
        this.kernel = kernel;
    }

    public String getLac() {
        return lac;
    }

    public void setLac(String lac) {
        this.lac = lac;
    }

    public String getCellId() {
        return cellId;
    }

    public void setCellId(String cellId) {
        this.cellId = cellId;
    }

    public class IosInfo {

        @SerializedName("udid")
        private String udid;
        @SerializedName("open_udid")
        private String openUdid;
        @SerializedName("idfa")
        private String idfa;
        @SerializedName("idfv")
        private String idfv;
        @SerializedName("ios_model")
        private int iosModel;
        @SerializedName("breakout")
        private int breakout;

        public String getUdid() {
            return udid;
        }

        public void setUdid(String udid) {
            this.udid = udid;
        }

        public String getOpenUdid() {
            return openUdid;
        }

        public void setOpenUdid(String openUdid) {
            this.openUdid = openUdid;
        }

        public String getIdfa() {
            return idfa;
        }

        public void setIdfa(String idfa) {
            this.idfa = idfa;
        }

        public String getIdfv() {
            return idfv;
        }

        public void setIdfv(String idfv) {
            this.idfv = idfv;
        }

        public int getIosModel() {
            return iosModel;
        }

        public void setIosModel(int iosModel) {
            this.iosModel = iosModel;
        }

        public int getBreakout() {
            return breakout;
        }

        public void setBreakout(int breakout) {
            this.breakout = breakout;
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
        }
    }

    public class AndroidInfo {

        @SerializedName("android_id")
        private String androidId;
        @SerializedName("android_serial_number")
        private String androidSerialNumber;

        public String getAndroidId() {
            return androidId;
        }

        public void setAndroidId(String androidId) {
            this.androidId = androidId;
        }

        public String getAndroidSerialNumber() {
            return androidSerialNumber;
        }

        public void setAndroidSerialNumber(String androidSerialNumber) {
            this.androidSerialNumber = androidSerialNumber;
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }

}
