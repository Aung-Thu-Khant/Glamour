package com.scet.saloonspot.models;

public class MySaloon {
    String name;
    String address;
    String mobileNo;
    String aera;
    String mail;

    String pass;

    public MySaloon() {
    }

    public MySaloon(String name, String address, String mobileNo, String aera, String mail, String pass) {
        this.name = name;
        this.address = address;
        this.mobileNo = mobileNo;
        this.aera = aera;
        this.mail = mail;
        this.pass = pass;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getAera() {
        return aera;
    }

    public void setAera(String aera) {
        this.aera = aera;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
