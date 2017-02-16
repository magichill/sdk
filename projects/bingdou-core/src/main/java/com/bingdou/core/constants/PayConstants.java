package com.bingdou.core.constants;

/**
 * ֧������
 * Created by gaoshan on 16/12/24.
 */
public class PayConstants {

    private PayConstants() {
    }

    public static final String[] AIBEI_APPLICATION_APPIDS = {"10d2c77d26e97fdc30d84c0964a2ab80"
            , "e3e03829e76339dcd644304378cb1da7", "35bde924210f77c1b3c56393374c496a"
            , "c2baeaa6c18090c4f705d3467a143316", "07cb138d191fba887c6f25bcd14a8624"};

    /**
     * ֧����������Ȩ��Ϣ����
     */
    public static final String ALI_PAY_NO_PWD_AUTH_TITLE = "֧��������֧����Ȩ";
    /**
     * ֧����������Ȩ��Ϣ����
     */
    public static final String ALI_PAY_NO_PWD_AUTH_CONTENT = "֧����������Ȩ�ɹ�";
    /**
     * ƻ��Ӧ����֧��ƾ֤��֤��ȷ����
     */
    public static final int APPLE_PAY_VERIFY_SUCCESS = 0;
    /**
     * ƻ��Ӧ����֧��ƾ֤��֤���󷵻�(���ƾ֤)
     */
    public static final int APPLE_PAY_VERIFY_MULTI_ERROR = 1;
    /**
     * ƻ��Ӧ����֧��ƾ֤��֤���󷵻�(ƾ֤�Ѿ������)
     */
    public static final int APPLE_PAY_VERIFY_DEAL_ERROR = 2;
    /**
     * ƻ��Ӧ����֧��ƾ֤��֤���󷵻�(ƻ��״̬�쳣)
     */
    public static final int APPLE_PAY_VERIFY_APPLE_STATUS_ERROR = 3;
    /**
     * ƻ��Ӧ����֧��ƾ֤��֤���󷵻�(����������)
     */
    public static final int APPLE_PAY_VERIFY_APPLE_MONEY_ERROR = 4;
    /**
     * ƻ��Ӧ����֧��ƾ֤��֤��ȷ
     */
    public static final int APPLE_PAY_VERIFY_SUCCESS_RETURN_CODE = 0;
    /**
     * ƻ��Ӧ����֧��ƾ֤��֤�ӿڳ�ʱʱ��(����)
     */
    public static final int APPLE_PAY_VERIFY_TIME_OUT = 1200;
    /**
     * �䷵����������
     */
    public static final int CHARGE_BACK_CARD_PROP_TYPE = 1;
    /**
     * ��ҳ֧����ʱ(��)
     */
    public static final int WEB_PAY_TYPE_TIME_OUT_SECONDS = 1800;
    /**
     * ֧�����������״̬��
     */
    public static final String ALI_CALL_RESULT_FINISHED_CODE = "TRADE_FINISHED";
    /**
     * ֧�������׳ɹ�״̬��
     */
    public static final String ALI_CALL_RESULT_SUCCESS_CODE = "TRADE_SUCCESS";
    /**
     * ֧�����ȴ�����״̬��
     */
    public static final String ALI_CALL_RESULT_WAIT_CODE = "WAIT_BUYER_PAY";
    /**
     * CHINAPAY�������׳ɹ�״̬��
     */
    public static final String CHINA_PAY_UNION_CALL_RESULT_SUCCESS_CODE = "0000";
    /**
     * ���������䷵����
     */
    public static final String SPECIAL_ACTIVITY_TITLE = "�����ҳ�ֵ�����";
    /**
     * ���������䷵����
     */
    public static final String SPECIAL_ACTIVITY_CONTENT = "1Ԫ�𣬳�ֵ�����Ҿ���100%����";
    /**
     * ��������APP ID
     */
    public static final String SPECIAL_ACTIVITY_APP_IDS = "74925a529cfde58e441c386dbf88ec94,289e7752c4c6c0d8e14b2f5823c7056a";
    /**
     * ����������
     */
    public static final String SPECIAL_ACTIVITY_CHANNEL = "000001690";
}
