package com.alodiga.app.wallet.model;

public class ObjRemittencePerson {

    private String name;
    private String lastName;
    private String secondname;
    private String secondSurmane;
    private String Telephone;
    private ObjGenericObject location;
    private ObjGenericObject state;
    private ObjGenericObject city;
    private String codeZip;
    private String av;
    private String email;

    public ObjRemittencePerson() {
    }

    public ObjRemittencePerson(String name, String secondname, String lastName, String secondSurmane, String telephone, ObjGenericObject location, ObjGenericObject state, ObjGenericObject city, String codeZip, String av, String email) {
        this.name = name;
        this.secondname = secondname;
        this.lastName = lastName;
        this.secondSurmane = secondSurmane;
        Telephone = telephone;
        this.location = location;
        this.state = state;
        this.city = city;
        this.codeZip = codeZip;
        this.av = av;
        this.email=email;
    }

    public void setSecondname(String secondname) {
        this.secondname = secondname;
    }

    public void setSecondSurmane(String secondSurmane) {
        this.secondSurmane = secondSurmane;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSecondname() {
        return secondname;
    }

    public String getSecondSurmane() {
        return secondSurmane;
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
