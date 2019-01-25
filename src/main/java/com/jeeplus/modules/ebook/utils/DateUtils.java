package com.jeeplus.modules.ebook.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/***
 * 日期工具类
 *
 * @author shijing
 *
 */
public class DateUtils {

    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);

    /***
     * Date类型转换成XMLGregorianCalendar类型
     *
     * @param date
     * @return
     */
    public static XMLGregorianCalendar convertToXMLGregorianCalendar(Date date) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        XMLGregorianCalendar gc = null;
        try {
            gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (Exception e) {
            logger.error("Date类型转换成XMLGregorianCalendar类型出错：" + e);
        }
        return gc;
    }

    /***
     * XMLGregorianCalendar类型转换成Date类型
     *
     * @param cal
     * @return
     * @throws Exception
     */
    public static Date convertToDate(XMLGregorianCalendar cal) throws Exception {
        GregorianCalendar ca = cal.toGregorianCalendar();
        return ca.getTime();
    }

    /**
     * String 转 Date
     * 2015年3月25日上午11:27:14
     * auther:shijing
     *
     * @param str    日期字符串
     * @param format 转换格式
     * @return Date
     */
    public static Date stringToDate(String str, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = dateFormat.parse(str);
        } catch (ParseException e) {
            logger.error("String类型 转 Date类型出错：" + e);
        }
        return date;
    }

    /**
     * Date 转 String
     * auther: shijing
     * 2015年3月25日上午11:28:14
     *
     * @param date   日期
     * @param format 转换格式
     * @return
     */
    public static String dateToString(Date date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        String strDate = null;
        try {
            if (date != null) {
                strDate = dateFormat.format(date);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("Date类型 转 String类型出错：" + e);
        }
        return strDate;
    }

}
