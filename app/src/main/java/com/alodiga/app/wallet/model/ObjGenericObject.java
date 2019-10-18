package com.alodiga.app.wallet.model;

/**
 * Created by anyeli on 19/09/17.
 */

public class ObjGenericObject {

    private String id;
    private String name;

    public ObjGenericObject(String name, String id) {
        this.name = name;
        this.id = id;
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
