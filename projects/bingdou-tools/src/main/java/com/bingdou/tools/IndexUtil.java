package com.bingdou.tools;

/**
 * Created by gaoshan on 16-11-8.
 */
public class IndexUtil {

    public static int getTableNumber(int id) throws Exception {
        if (id > 0 && id <= 5000000) {
            return 1;
        } else if (id > 5000000 && id <= 10000000) {
            return 2;
        } else if (id > 10000000 && id <= 15000000) {
            return 3;
        } else if (id > 15000000 && id <= 20000000) {
            return 4;
        } else if (id > 20000000 && id <= 25000000) {
            return 5;
        } else if (id > 25000000 && id <= 30000000) {
            return 6;
        } else if (id > 30000000 && id <= 35000000) {
            return 7;
        } else if (id > 35000000 && id <= 40000000) {
            return 8;
        } else if (id > 40000000 && id <= 45000000) {
            return 9;
        } else if (id > 45000000 && id <= 50000000) {
            return 10;
        }
        throw new Exception("子表个数已到达边界");
    }
}
