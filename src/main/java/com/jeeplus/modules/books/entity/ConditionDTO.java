package com.jeeplus.modules.books.entity;

public class ConditionDTO {
    public String cname;
    public String sex;
    public String state;
    public String sss;

    public ConditionDTO() {
    }

    public ConditionDTO(String cname, String sex, String state, String sss) {
        this.cname = cname;
        this.sex = sex;
        this.state = state;
        this.sss = sss;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSss() {
        return sss;
    }

    public void setSss(String sss) {
        this.sss = sss;
    }

    @Override
    public String toString() {
        return "conditionDTO{" +
                "cname='" + cname + '\'' +
                ", sex='" + sex + '\'' +
                ", state='" + state + '\'' +
                ", sss='" + sss + '\'' +
                '}';
    }

}
