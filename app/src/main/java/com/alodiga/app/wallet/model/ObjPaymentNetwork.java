package com.alodiga.app.wallet.model;

public class ObjPaymentNetwork {

                        private String id;
                        private String name;
                        private String enabled;
                        ObjCountry country;

    public ObjPaymentNetwork() {
    }

    public ObjPaymentNetwork(String id, String name, String enabled, ObjCountry country) {
        this.id = id;
        this.name = name;
        this.enabled = enabled;
        this.country = country;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public ObjCountry getCountry() {
        return country;
    }

    public void setCountry(ObjCountry country) {
        this.country = country;
    }
}
