package com.emobi.healthlife.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 13-02-2017.
 */

public class RegisterResultPOJO {
    @SerializedName("id")
    String id;
    @SerializedName("actcode")
    String actcode;
    @SerializedName("intime")
    String intime;
    @SerializedName("referrer")
    String referrer;
    @SerializedName("usercode")
    String usercode;
    @SerializedName("active")
    String active;
    @SerializedName("login")
    String login;
    @SerializedName("name")
    String name;
    @SerializedName("user_ip")
    String user_ip;
    @SerializedName("emailid")
    String emailid;
    @SerializedName("mailstatus")
    String mailstatus;
    @SerializedName("mobileno")
    String mobileno;
    @SerializedName("homeaddress")
    String homeaddress;
    @SerializedName("password")
    String password;
    @SerializedName("dob")
    String dob;
    @SerializedName("sex")
    String sex;
    @SerializedName("usertype")
    String usertype;
    @SerializedName("address")
    String address;
    @SerializedName("school")
    String school;
    @SerializedName("course")
    String course;
    @SerializedName("usergroup")
    String usergroup;
    @SerializedName("protocol")
    String protocol;
    @SerializedName("user_lat")
    String user_lat;
    @SerializedName("user_long")
    String user_long;
    @SerializedName("language")
    String language;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActcode() {
        return actcode;
    }

    public void setActcode(String actcode) {
        this.actcode = actcode;
    }

    public String getIntime() {
        return intime;
    }

    public void setIntime(String intime) {
        this.intime = intime;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_ip() {
        return user_ip;
    }

    public void setUser_ip(String user_ip) {
        this.user_ip = user_ip;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getMailstatus() {
        return mailstatus;
    }

    public void setMailstatus(String mailstatus) {
        this.mailstatus = mailstatus;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getHomeaddress() {
        return homeaddress;
    }

    public void setHomeaddress(String homeaddress) {
        this.homeaddress = homeaddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getUsergroup() {
        return usergroup;
    }

    public void setUsergroup(String usergroup) {
        this.usergroup = usergroup;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getUser_lat() {
        return user_lat;
    }

    public void setUser_lat(String user_lat) {
        this.user_lat = user_lat;
    }

    public String getUser_long() {
        return user_long;
    }

    public void setUser_long(String user_long) {
        this.user_long = user_long;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "RegisterResultPOJO{" +
                "id='" + id + '\'' +
                ", actcode='" + actcode + '\'' +
                ", intime='" + intime + '\'' +
                ", referrer='" + referrer + '\'' +
                ", usercode='" + usercode + '\'' +
                ", active='" + active + '\'' +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", user_ip='" + user_ip + '\'' +
                ", emailid='" + emailid + '\'' +
                ", mailstatus='" + mailstatus + '\'' +
                ", mobileno='" + mobileno + '\'' +
                ", homeaddress='" + homeaddress + '\'' +
                ", password='" + password + '\'' +
                ", dob='" + dob + '\'' +
                ", sex='" + sex + '\'' +
                ", usertype='" + usertype + '\'' +
                ", address='" + address + '\'' +
                ", school='" + school + '\'' +
                ", course='" + course + '\'' +
                ", usergroup='" + usergroup + '\'' +
                ", protocol='" + protocol + '\'' +
                ", user_lat='" + user_lat + '\'' +
                ", user_long='" + user_long + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}
