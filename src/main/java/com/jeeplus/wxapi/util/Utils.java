package com.jeeplus.wxapi.util;

import com.jeeplus.wxapi.exception.CustomRuntimeException;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;


public class Utils {
    private static String randomCode = "0123456789abcdefghijklmnopqrstuvwxyz";
    private static Random random = new Random();

    /**
     * 给日期添加指定分钟
     *
     * @param date  日期
     * @param value 分钟数
     */
    public static Date addMinute(Date date, int value) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.add(Calendar.MINUTE, value);
        return now.getTime();
    }

    /**
     * 对象转换为字符串
     *
     * @param o
     */
    public static String toString(Object o) {
        if (o == null) {
            return "";
        }
        return o.toString();
    }

    /**
     * 对象转换为double类型
     *
     * @param o
     */
    public static double toDouble(Object o) {
        try {
            return Double.parseDouble(toString(o));
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 对象转换为int类型
     *
     * @param o
     */
    public static int toInt(Object o) {
        try {
            return Integer.parseInt(toString(o));
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 省略多余字符串
     *
     * @param str
     * @param len
     * @return
     */
    public static String cutString(String str, int len) {
        if (str != null && str.length() > len) {
            return str.substring(0, len) + "...";
        }
        return str;
    }

    /**
     * SHA1加密
     *
     * @param
     */
    public static String SHA1(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA");
            digest.update(str.getBytes());
            byte messageDigest[] = digest.digest();
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取微信通用随机字符串
     *
     * @throws Exception
     */
    public static String getWxNonceStr() {
        return getRandomCode(20, 32);
    }

    /**
     * 获取随机字符串
     *
     * @param minLen 最小长度
     * @param maxLen 最大长度
     * @throws Exception
     */
    public static String getRandomCode(int minLen, int maxLen) {
        if (maxLen < minLen) {
            throw new CustomRuntimeException("最大字符串长度不能小于最小字符串长度");
        }
        int len = 0;
        if (minLen == maxLen) {
            len = minLen;
        } else {
            do {
                len = random.nextInt(maxLen + 1);
            } while (len < minLen);
        }
        String code = "";
        for (int i = 0; i < len; i++) {
            code += randomCode.charAt(random.nextInt(randomCode.length()));
        }
        return code;
    }

    public static void main(String[] args) {
        /*
         * int count = 12; float totalMoney = 1500; float[] moneyArr; float
         * surplus = 0;
         *
         * do { moneyArr = new float[count]; for (int i = 0; i < count; i++) {
         * int money; do { money = random.nextInt((int) totalMoney); } while
         * (money < 1 || money > (totalMoney / (count - 1))); moneyArr[i] =
         * money; } } while ((surplus = getSurplus(moneyArr, totalMoney)) < 0);
         *
         * float avg = surplus / count;
         *
         * for (int i = 0; i < moneyArr.length; i++) { moneyArr[i] += avg; }
         *
         * float total = 0; for (float f : moneyArr) { System.out.print(f + ",
         * "); total += f; } System.out.println("\n" + total);
         */
        Object o = null;
        double d = -1;
        try {
            d = Double.parseDouble(toString(o));
        } catch (Exception e) {
            d = 3;
        }
        System.out.println(d);
    }

    public static float getSurplus(float[] arr, float totalMoney) {
        float total = 0;
        for (float f : arr) {
            total += f;
        }
        return totalMoney - total;
    }

    /**
     * url编码
     *
     * @param url
     * @return
     */
    public static String encodeURL(String url) {
        if (url == null) return "";
        try {
            // 先解码再编码，防止url已经编码的情况下二次编码
            return URLEncoder.encode(decodeURL(url), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return url;
        }
    }

    /**
     * url解码
     *
     * @param url
     */
    public static String decodeURL(String url) {
        if (url == null) return "";
        try {
            return URLDecoder.decode(url, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return url;
        }
    }

    /**
     * 获取本机IP
     *
     * @return
     * @throws UnknownHostException
     */
    public static String getHostIp() {
        InetAddress netAddress = null;
        try {
            netAddress = InetAddress.getLocalHost();
            String ip = netAddress.getHostAddress();
            return ip;
        } catch (UnknownHostException e) {
            return "0.0.0.0";
        }
    }
    public static String urlEnodeUTF8(String str) {
        String result = str;
        try {
            result = URLEncoder.encode(str, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}