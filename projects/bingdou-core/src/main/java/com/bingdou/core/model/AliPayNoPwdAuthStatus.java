package com.bingdou.core.model;

/**
 * ֧����������Ȩ״̬
 * Created by gaoshan on 16/12/28.
 */
public enum AliPayNoPwdAuthStatus {

    /**
     * ��Լ
     */
    CANCEL(-1, "��Լ"),
    /**
     * δǩԼ,����ǩԼ����
     */
    CREATE_SIGN(0, "δǩԼ��Ĭ��ֵ"),
    /**
     * �Ѿ�ǩԼ
     */
    SIGNED(1, "NORMAL"),
    /**
     * �ݴ�
     */
    TEMPORARY(2, "TEMP"),
    /**
     * ��ͣ
     */
    PAUSE(3, "STOP");

    private int index;
    private String name;

    AliPayNoPwdAuthStatus(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public static AliPayNoPwdAuthStatus getByName(String name) {
        for (AliPayNoPwdAuthStatus status : AliPayNoPwdAuthStatus.values()) {
            if (status.getName().equals(name)) {
                return status;
            }
        }
        return null;
    }

    public static AliPayNoPwdAuthStatus getByIndex(int index) {
        for (AliPayNoPwdAuthStatus status : AliPayNoPwdAuthStatus.values()) {
            if (index == status.getIndex()) {
                return status;
            }
        }
        return null;
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
}
