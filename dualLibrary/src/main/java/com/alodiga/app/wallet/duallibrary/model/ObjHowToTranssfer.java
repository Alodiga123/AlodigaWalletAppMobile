package com.alodiga.app.wallet.duallibrary.model;

/**
 * Created by anyeli on 19/09/17.
 */

public class ObjHowToTranssfer {

    private String id;
    private String name;


    public ObjHowToTranssfer(String id, String name) {
        this.id = id;
        this.name = name;
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
