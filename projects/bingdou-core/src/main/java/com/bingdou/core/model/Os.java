package com.bingdou.core.model;

/**
 */
public enum Os {

    ANDROID(1, "android"), IOS(2, "IOS"), SERVER(3, "SERVER");

    private int index;
    private String name;

    Os(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Os getOsByIndex(int index) {
        if (index == Os.ANDROID.getIndex()) {
            return Os.ANDROID;
        } else if (index == Os.IOS.getIndex()) {
            return Os.IOS;
        }
        return null;
    }

    public static String getOsNameByIndex(int index) {
        String osStr = "";
        if (index == Os.ANDROID.getIndex()) {
            osStr = Os.ANDROID.getName();
        } else if (index == Os.IOS.getIndex()) {
            osStr = Os.IOS.getName();
        }
        return osStr;
    }

    public static int getIndexByOsName(String osName) {
        int osIndex = -1;
        if (Os.ANDROID.getName().equals(osName)) {
            osIndex = Os.ANDROID.getIndex();
        } else if (Os.IOS.getName().equals(osName)) {
            osIndex = Os.IOS.getIndex();
        }
        return osIndex;
    }
}
