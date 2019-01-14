package com.jeeplus.modules.ebook.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
    /**
     * 计算指定字符串的MD5值
     *
     * @param str 指定的字符串
     * @return
     */
    public static String encodeMD5(String str) {
        MessageDigest md = null;
        String dstr = null;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes("UTF-8"));
            byte[] b = md.digest();
            System.out.println("md5数组长度 = " + b.length);
            dstr = new BigInteger(1, b).toString(16);
            System.out.println("md5值 = " + dstr);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return dstr;
    }
}
