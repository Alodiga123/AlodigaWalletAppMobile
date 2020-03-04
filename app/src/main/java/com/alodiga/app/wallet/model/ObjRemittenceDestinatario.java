package com.alodiga.app.wallet.model;

public class ObjRemittenceDestinatario {

    private String name;
    private String lastName;
    private String Telephone;
    private String location;
    private String state;
    private String city;
    private String codeZip;
    private String av;

    public ObjRemittenceDestinatario(String name, String lastName, String telephone, String location, String state, String city, String codeZip, String av) {
        this.name = name;
        this.lastName = lastName;
        Telephone = telephone;
        this.location = location;
        this.state = state;
        this.city = city;
        this.codeZip = codeZip;
        this.av = av;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCodeZip() {
        return codeZip;
    }

    public void setCodeZip(String codeZip) {
        this.codeZip = codeZip;
    }

    public String getAv() {
        return av;
    }

    public void setAv(String av) {
        this.av = av;
    }
}
