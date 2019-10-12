package com.example.myapplication;

/**
 * Created by AMANULLAH on 05-Feb-18.
 */

public class Contact {
    String name;
    int image;
    String phone;

    public Contact() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public int getImage() {return image;}

    public String getPhone() {
        return phone;
    }
}
