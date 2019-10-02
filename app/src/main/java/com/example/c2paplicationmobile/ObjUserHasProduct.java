package com.example.c2paplicationmobile;

/**
 * Created by anyeli on 19/09/17.
 */

public class ObjUserHasProduct {

    private String id;
    private String name;
    private String imageSource;
    private Float currentBalance;

    public ObjUserHasProduct(String name) {
        this.name = name;
    }


    public ObjUserHasProduct(String id, String name, String imageSource) {
        this.id = id;
        this.name = name;
        this.imageSource = imageSource;
    }

    public ObjUserHasProduct(String id, String name, String imageSource, Float currentBalance) {
        this.id = id;
        this.name = name;
        this.imageSource = imageSource;
        this.currentBalance = currentBalance;
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

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public Float getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Float currentBalance) {
        this.currentBalance = currentBalance;
    }
}
