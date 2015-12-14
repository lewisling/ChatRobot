package cn.iamding.chatrobot.model;

import java.util.Date;

/**
 * Created by Xuding on 2015/12/13 19:54
 * 一条消息的模型
 */
public class OneMessage {
    private From from;//消息来源
    private String content;//消息内容
    private Date date;//消息日期
    private String sendMan;//发送人
    private int contentType;//内容分类,1表示文字，2表示图片链接

    public OneMessage() {
    }

    public OneMessage(From from, String content) {
        this(from,content,1);
    }
    public OneMessage(From from, String content, int contentType) {
        this.from = from;
        this.content = content;
        this.contentType = contentType;
    }

    public From getFrom() {
        return from;
    }

    public void setFrom(From from) {
        this.from = from;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public enum From {NET, LOCAL}//
}
