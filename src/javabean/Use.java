package javabean;
/*
作者：ypl
创建时间：2018/8/26-21:24-2018
*/
//javaBean

import java.util.Date;

public class Use {
    private String uid;//key
    private String name;//用户名字
    private String phone;//手机号码
    private String email;//邮箱
    private String password;//密码
    private String headUrl;//用户头像的url
    private Date date;//注册时间

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    private String signature;//签名

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "用户Id:" + this.uid + "\t用户的名字：" + this.name + "\t用户的手机号码:" + this.phone
                + "\t邮箱：" + this.email + "\t密码：" + this.password + "\tdate:" + this.date;
    }
}
