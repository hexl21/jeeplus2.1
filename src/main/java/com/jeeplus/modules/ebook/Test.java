package com.jeeplus.modules.ebook;


import com.jeeplus.common.utils.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
    public static void main(String[] args) throws ParseException {

//        System.out.println("开始：" + System.currentTimeMillis());
//        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
//        long id = idWorker.nextId();
//        String s = (id + "").substring(8);
//        System.out.println("id ==> "+id);
//        System.out.println("s  ==> "+s);

//        //方法 一
//        long l = System.currentTimeMillis();
//        //方法 二
//        long timeInMillis = Calendar.getInstance().getTimeInMillis();
//        //方法 三
//        long time = new Date().getTime();
//        System.out.println("方法 一"+l);
//        System.out.println("方法 二"+timeInMillis);
//        System.out.println("方法 三"+time);
//        UUID uuid = UUID.randomUUID();
//        uuid = uuid.split('-');
//        System.out.println(uuid);


//        String uuids = UUID.randomUUID().toString().replaceAll("\\-", "");
//        System.out.println(uuids);
//        History history=new History();
//        System.out.println();

//        Date day = new Date();
//        System.out.println(day);
//
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        System.out.println(format.format(day));
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String s = "2011-07-09 ";
//        Date date =null;
//        try {
//            date = simpleDateFormat.parse(s);
//            System.out.println(date);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

//        //获得一个时间格式的字符串
//        String dateStr = "2016-12-31";
//        //获得SimpleDateFormat类，我们转换为yyyy-MM-dd的时间格式
//        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            //使用SimpleDateFormat的parse()方法生成Date
//            Date date = sf.parse(dateStr);
//            //打印Date
//            System.out.println(date);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

//        SystemService.entryptPassword
//
//        KeyStore.PasswordProtection uniUser;
//        SystemService.validatePassword("123456", uniUser.getPassword());
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        try
//        {
//            Date d1 = df.parse("2004-03-26 13:31:40");
//            Date d2 = df.parse("2004-01-02 11:30:24");
//            long diff = d1.getTime() - d2.getTime();
//            long days = diff / (1000 * 60 * 60 * 24);
//            System.out.println(days);
//        }catch (Exception e){
//        }

//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        java.util.Date now = df.parse("2004-03-26 13:31:40");
//        java.util.Date date=df.parse("2004-01-02 11:30:24");
//        long l=now.getTime()-date.getTime();
//        long day=l/(24*60*60*1000);
//        long hour=(l/(60*60*1000)-day*24);
//        long min=((l/(60*1000))-day*24*60-hour*60);
//        long s=(l/1000-day*24*60*60-hour*60*60-min*60);
//        System.out.println(""+day+"天"+hour+"小时"+min+"分"+s+"秒");

//        Calendar cal = Calendar.getInstance();
//        cal.setTime(new Date());
//        Date time = cal.getTime();
//        System.out.println(time);
//        cal.add(Calendar.WEDNESDAY, -1);
//        System.out.println(DateUtils.date2String("yyyy-MM-dd", cal.getTime()));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 日期格式
        Date date = dateFormat.parse("2017-12-25 13:31:00"); // 指定日期
        Date newDate = addDate(date, 10); // 指定日期加上10天
        System.out.println(dateFormat.format(date));// 输出格式化后的日期
        System.out.println(dateFormat.format(newDate));

        Date date1 = dateFormat.parse("2017-12-25 13:31:00"); // 指定日期
        Date date2 = dateFormat.parse("2018-01-04 13:31:00"); // 指定日期
        double distanceOfTwoDate = DateUtils.getDistanceOfTwoDate(date1, date2);
        System.out.println(distanceOfTwoDate);

    }

    public static Date addDate(Date date, long day) throws ParseException {
        long time = date.getTime(); // 得到指定日期的毫秒数
        day = day * 24 * 60 * 60 * 1000; // 要加上的天数转换成毫秒数
        time += day; // 相加得到新的毫秒数
        return new Date(time); // 将毫秒数转换成日期
    }
}
