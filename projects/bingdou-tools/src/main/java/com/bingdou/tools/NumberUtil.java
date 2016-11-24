package com.bingdou.tools;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by gaoshan on 16-10-25.
 */
public class NumberUtil {

    /**
     * 在最小值到最大值之前获取随机数
     *
     * @param min 最小值
     * @param max 最大值
     * @return 随机数
     */
    public static long getRandomNum(long min, long max) {
        return Math.round(min + Math.random() * (max - min));
    }

    public static String formatNumber(BigDecimal number) {
        DecimalFormat df = new DecimalFormat("0.00");
        float num = number.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        return df.format(num);
    }

    public static float formatFloat(float number) {
        return new BigDecimal(number).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    public static float divide(BigDecimal divide, BigDecimal divisor) {
        divide = divide.setScale(2, BigDecimal.ROUND_HALF_UP);
        divisor = divisor.setScale(2, BigDecimal.ROUND_HALF_UP);
        return divide.divide(divisor, 2, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    public static int multiplyInt(BigDecimal multiplierOne, BigDecimal multiplierTwo) {
        return multiply(multiplierOne, multiplierTwo).intValue();
    }

    public static float multiplyFloat(BigDecimal multiplierOne, BigDecimal multiplierTwo) {
        return formatFloat(multiply(multiplierOne, multiplierTwo).floatValue());
    }

    public static int convertFenFromYuan(float yuan) {
        BigDecimal bgYuan = new BigDecimal(yuan);
        BigDecimal bigDecimal = new BigDecimal(100);
        return multiply(bgYuan, bigDecimal).intValue();
    }

    public static float convertYuanFromFen(int fen) {
        BigDecimal bgFen = new BigDecimal(fen);
        BigDecimal bigDecimal = new BigDecimal(100);
        return divide(bgFen, bigDecimal);
    }

    private static BigDecimal multiply(BigDecimal multiplierOne, BigDecimal multiplierTwo) {
        multiplierOne = multiplierOne.setScale(2, BigDecimal.ROUND_HALF_UP);
        multiplierTwo = multiplierTwo.setScale(2, BigDecimal.ROUND_HALF_UP);
        return multiplierOne.multiply(multiplierTwo);
    }
}
