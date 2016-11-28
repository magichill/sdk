package com.bingdou.tools;

import com.bingdou.tools.constants.KeyGroup;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.Random;

/**
 * Created by gaoshan on 16-10-25.
 */
public class CodecUtils {

    private CodecUtils() {
    }

    public static String desEncode(String key, String data) {
        try {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(key.getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, random);
            return Hex.encodeHexString(cipher.doFinal(data.getBytes("UTF-8")));
        } catch (Exception e) {
            LogContext.instance().error(e);
        }
        return "";
    }

    public static String aesEncode(String data, KeyGroup keyGroup) throws Exception {
        byte[] aesKey = StringUtils.getBytesUtf8(keyGroup.getEncodeKey());
        SecretKeySpec key = new SecretKeySpec(aesKey, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        byte[] byteContent = data.getBytes("utf-8");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] result = cipher.doFinal(byteContent);
        return Base64.encodeBase64String(result);
    }

    public static String aesDecode(String data, KeyGroup keyGroup) throws Exception {
        byte[] aesKey = StringUtils.getBytesUtf8(keyGroup.getEncodeKey());
        SecretKeySpec key = new SecretKeySpec(aesKey, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return StringUtils.newStringUtf8(cipher.doFinal(Base64.decodeBase64(data)));
    }

    public static String getMySign(String data, KeyGroup keyGroup) throws Exception {
        return DigestUtils.md5Hex(data + keyGroup.getSignKey());
    }

    public static String getRequestUUID() {
        StringBuilder uuidBuilder = new StringBuilder();
        uuidBuilder.append(DateUtil.format(new Date(), DateUtil.YYYYMMDDHHMMSSSSS))
                .append("_").append(NumberUtil.getRandomNum(100000, 999999));
        return uuidBuilder.toString();
    }

    /**
     * 生成SALT
     */
    public static String generateRandCode() {
        String str = "abcdefghijklmmopqrstuvwxyz0123456789";
        Random random = new Random(System.currentTimeMillis());
        int length = 6;
        char[] tmp = new char[length];
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(str.length());
            char c = str.charAt(index);
            tmp[i] = c;
        }
        return new String(tmp);
    }

    public static String generateValidateCode() {
        Random random = new Random();
        int length = 4;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(random.nextInt(9));
        }
        return result.toString();
    }

    public static String rsaSign(String content, String privateKey) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
                    Base64.decodeBase64(privateKey));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey generatePrivate = keyFactory.generatePrivate(priPKCS8);
            java.security.Signature signature = java.security.Signature
                    .getInstance("SHA1WithRSA");
            signature.initSign(generatePrivate);
            signature.update(content.getBytes("UTF-8"));
            byte[] signed = signature.sign();
            return Base64.encodeBase64String(signed);
        } catch (Exception e) {
            LogContext.instance().error(e, "RSA签名失败");
        }
        return "";
    }

    public static boolean verifyRsaSign(String content, String sign, String publicKey) {
        try {
            LogContext.instance().info("对方提供的签名:" + sign);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = Base64.decodeBase64(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            java.security.Signature signature = java.security.Signature.getInstance("RSA");
            signature.initVerify(pubKey);
            signature.update(content.getBytes("UTF-8"));
            return signature.verify(Base64.decodeBase64(sign));
        } catch (Exception e) {
            LogContext.instance().error(e, "验证RSA签名失败");
        }
        return false;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String base = "eyJpdGVtcyI6W3sicGVyc2lzdGVudElkIjoiMTAxMTdjMDk2YmJlNDRlNjRiNzdiM2YwYzUyYWQzOTRhYTFmIiwic3RyZWFtbmFtZSI6ImxpdmUtMTIzMTIzLmZsdiIsIm9wcyI6IndzcmVjb3JkL2Zsdi9hdXRvY3V0dGltZS8zL3Zjb2RlYy9jb3B5L2Fjb2RlYy9jb3B5fHNhdmVhcy9jM0JoWTJVdGRtbGtaVzg2SkNoemRISmxZVzF1WVcxbEtTMGtLSFJwYldWemRHRnRjQ2s9IiwiYnVja2V0Ijoic3BhY2UtdmlkZW8iLCJjb2RlIjoxLCJkZXNjIjoiZmlsZU9wZXJhdGVBY3RpdmUiLCJlcnJvciI6bnVsbCwia2V5cyI6WyJzcGFjZS12aWRlbzpsaXZlLTEyMzEyMy0tMjAxNjExMjQxNDE5NDEuZmx2Il0sInVybHMiOlsiaHR0cDovL2NuY3BsYXliYWNrLmJpbmdkb3UudHYvbGl2ZS0xMjMxMjMtLTIwMTYxMTI0MTQxOTQxLmZsdiJdLCJkZXRhaWwiOlt7ImtleSI6InNwYWNlLXZpZGVvOmxpdmUtMTIzMTIzLS0yMDE2MTEyNDE0MTk0MS5mbHYiLCJ1cmwiOiJodHRwOi8vY25jcGxheWJhY2suYmluZ2RvdS50di9saXZlLTEyMzEyMy0tMjAxNjExMjQxNDE5NDEuZmx2IiwiZHVyYXRpb24iOjU0Ljk3NywiaGFzaCI6ImxzZlQ5UDhTTDgtVkQxdDEtUVZCWm9PdnNIamgiLCJmc2l6ZSI6MTI3NTU1ODcsInN0YXJ0VGltZSI6IjIwMTYxMTI0MTQxOTQxIiwiZW5kVGltZSI6IjIwMTYxMTI0MTQyMTA2IiwiYml0X3JhdGUiOm51bGwsInJlc29sdXRpb24iOiIzMjRYMTgyIn1dfV0sImJhdGNoX25vdGlmeV9pZCI6bnVsbH0=";
        System.out.println(new String(Base64.decodeBase64(base),"UTF-8"));
    }
}
