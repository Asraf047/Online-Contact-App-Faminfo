package com.example.myapplication;


public class Upload2 {
    private String mImageUrl,userEmail,userName,phone,blood,home,work,birth,fb,linkin,presentStudy,presentWork;

    public Upload2() {

    }

    public Upload2(String mImageUrl, String userEmail, String name, String phone, String blood, String home, String work, String birth, String fb, String linkin, String presentStudy, String presentWork) {
        this.mImageUrl = mImageUrl;
        this.userEmail = userEmail;
        if (name.trim().equals("")) { name = "No Name"; }
        this.userName = name;
        this.phone = phone;
        this.blood = blood;
        this.home = home;
        this.work = work;
        this.birth = birth;
        this.fb = fb;
        this.linkin = linkin;
        this.presentStudy = presentStudy;
        this.presentWork = presentWork;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getFb() {
        return fb;
    }

    public void setFb(String fb) {
        this.fb = fb;
    }

    public String getLinkin() {
        return linkin;
    }

    public void setLinkin(String linkin) {
        this.linkin = linkin;
    }

    public String getPresentStudy() {
        return presentStudy;
    }

    public void setPresentStudy(String presentStudy) {
        this.presentStudy = presentStudy;
    }

    public String getPresentWork() {
        return presentWork;
    }

    public void setPresentWork(String presentWork) {
        this.presentWork = presentWork;
    }
}