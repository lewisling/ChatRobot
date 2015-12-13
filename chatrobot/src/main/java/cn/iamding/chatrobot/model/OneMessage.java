package cn.iamding.chatrobot.model;

import java.util.Date;

/**
 * Created by Xuding on 2015/12/13 19:54
 * 一条消息的模型
 */
public class OneMessage {
    private From from;//消息来源
    private String text;//消息文本
    private Date date;//消息日期
    private String sendMan;//发送人

    public OneMessage() {
    }

    public OneMessage(From from, String text) {
        this.from = from;
        this.text = text;
    }

    public From getFrom() {
        return from;
    }

    public void setFrom(From from) {
        this.from = from;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSendMan() {
        return sendMan;
    }

    public void setSendMan(String sendMan) {
        this.sendMan = sendMan;
    }

    public enum From {NET, LOCAL}//
}
