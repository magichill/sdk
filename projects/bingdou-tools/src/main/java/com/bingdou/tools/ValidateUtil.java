package com.bingdou.tools;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.MySQLCodec;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证工具类
 */
public class ValidateUtil {

    private static final String MOBILE_PATTERN = "^[0-9]{11}$";
    private static final String EMAIL_PATTERN = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
    private static final String DIGITAL_PATTERN = "[0-9]+";
    private static final String NICK_NAME_PATTERN = "^[\\x{4e00}-\\x{9fa5}A-Za-z0-9_]+$";

    private static final MySQLCodec MY_SQL_CODEC = new MySQLCodec(MySQLCodec.Mode.STANDARD);

    /**
     * 是否为手机号格式
     */
    public static boolean isMobileNumber(String str) {
        Pattern pattern = Pattern.compile(MOBILE_PATTERN);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 是否为邮箱格式
     */
    public static boolean isEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * 是否为数字
     */
    public static boolean isInteger(String num) {
        Pattern p = Pattern.compile(DIGITAL_PATTERN);
        Matcher m = p.matcher(num);
        return m.matches();
    }

    /**
     * 是否是合法昵称
     */
    public static boolean isValidNickname(String nickname) {
        if (nickname.length() > 30)
            return false;
        Pattern p = Pattern.compile(NICK_NAME_PATTERN);
        Matcher m = p.matcher(nickname);
        return m.matches();
    }

    public static String filterSqlValue(Object value) {
        return ESAPI.encoder().encodeForSQL(MY_SQL_CODEC, value + "");
    }

}
