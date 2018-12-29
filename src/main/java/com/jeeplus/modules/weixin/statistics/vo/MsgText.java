package com.jeeplus.modules.weixin.statistics.vo;

public class MsgText {
    private String ref_date;//	数据的日期，需在begin_date和end_date之间
    private String ref_hour;//	数据的小时，包括从000到2300，分别代表的是[000,100)到[2300,2400)，即每日的第1小时和最后1小时
    private Integer msg_type;//	消息类型，代表含义如下： 1代表文字 2代表图片 3代表语音 4代表视频 6代表第三方应用消息（链接消息）
    private Integer msg_user;//	上行发送了（向公众号发送了）消息的用户数
    private Integer msg_count;//	上行发送了消息的消息总数
    private String count_interval;//	当日发送消息量分布的区间，0代表 “0”，1代表“1-5”，2代表“6-10”，3代表“10次以上”
    private Integer int_page_read_count;//	图文页的阅读次数
    private String ori_page_read_user;//	原文页（点击图文页“阅读原文”进入的页面）的阅读人数，无原文页时此处数据为0

    public String getRef_date() {
        return ref_date;
    }

    public void setRef_date(String ref_date) {
        this.ref_date = ref_date;
    }

    public String getRef_hour() {
        return ref_hour;
    }

    public void setRef_hour(String ref_hour) {
        this.ref_hour = ref_hour;
    }

    public Integer getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(Integer msg_type) {
        this.msg_type = msg_type;
    }

    public Integer getMsg_user() {
        return msg_user;
    }

    public void setMsg_user(Integer msg_user) {
        this.msg_user = msg_user;
    }

    public Integer getMsg_count() {
        return msg_count;
    }

    public void setMsg_count(Integer msg_count) {
        this.msg_count = msg_count;
    }

    public String getCount_interval() {
        return count_interval;
    }

    public void setCount_interval(String count_interval) {
        this.count_interval = count_interval;
    }

    public Integer getInt_page_read_count() {
        return int_page_read_count;
    }

    public void setInt_page_read_count(Integer int_page_read_count) {
        this.int_page_read_count = int_page_read_count;
    }

    public String getOri_page_read_user() {
        return ori_page_read_user;
    }

    public void setOri_page_read_user(String ori_page_read_user) {
        this.ori_page_read_user = ori_page_read_user;
    }
}
