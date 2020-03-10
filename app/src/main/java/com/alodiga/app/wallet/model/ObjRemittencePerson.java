package com.alodiga.app.wallet.model;

public class ObjRemittencePerson {

    private String name;
    private String lastName;
    private String Telephone;
    private ObjGenericObject location;
    private ObjGenericObject state;
    private ObjGenericObject city;
    private String codeZip;
    private String av;

    public ObjRemittencePerson() {
    }

    public ObjRemittencePerson(String name, String lastName, String telephone, ObjGenericObject location, ObjGenericObject state, ObjGenericObject city, String codeZip, String av) {
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

    public ObjGenericObject getLocation() {
        return location;
    }

    public void setLocation(ObjGenericObject location) {
        this.location = location;
    }

    public ObjGenericObject getState() {
        return state;
    }

    public void setState(ObjGenericObject state) {
        this.state = state;
    }

    public ObjGenericObject getCity() {
        return city;
    }

    public void setCity(ObjGenericObject city) {
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
