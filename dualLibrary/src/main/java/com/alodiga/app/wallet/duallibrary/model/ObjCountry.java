package com.alodiga.app.wallet.duallibrary.model;

/**
 * Created by anyeli on 19/09/17.
 */

public class ObjCountry {

    private String id;
    private String name;
    private String alternativeName2;
    private String alternativeName3;
    private String code;
    private String shortName;
    private String iso;

    public ObjCountry(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public ObjCountry() {
    }



    public ObjCountry(String id, String name, String alternativeName3, String code, String shortName) {
        this.id = id;
        this.name = name;
        this.alternativeName3 = alternativeName3;
        this.code = code;
        this.shortName = shortName;
    }


    public ObjCountry(String id, String name, String alternativeName3, String code, String shortName, String iso) {
        this.id = id;
        this.name = name;
        this.alternativeName3 = alternativeName3;
        this.code = code;
        this.shortName = shortName;
        this.iso = iso;
    }

    public ObjCountry(String id, String name, String alternativeName2, String alternativeName3, String code, String shortName, String iso) {
        this.id = id;
        this.name = name;
        this.alternativeName2 = alternativeName2;
        this.alternativeName3 = alternativeName3;
        this.code = code;
        this.shortName = shortName;
        this.iso = iso;
    }

    public String getAlternativeName2() {
        return alternativeName2;
    }

    public void setAlternativeName2(String alternativeName2) {
        this.alternativeName2 = alternativeName2;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getAlternativeName3() {
        return alternativeName3;
    }

    public void setAlternativeName3(String alternativeName3) {
        this.alternativeName3 = alternativeName3;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
