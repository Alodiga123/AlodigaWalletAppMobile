package com.example.c2paplicationmobile;

/**
 * Created by anyeli on 19/09/17.
 */

public class ObjTransferMoney {

    private  String id;
    private String name;


    public ObjTransferMoney(String id, String name) {
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
