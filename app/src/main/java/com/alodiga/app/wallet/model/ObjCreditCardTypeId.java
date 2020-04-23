package com.alodiga.app.wallet.model;

public class ObjCreditCardTypeId {

      private String IsEnabled;
      private String id;
      private String lenghCVV;
      private String name;

    public ObjCreditCardTypeId() {
    }

    public ObjCreditCardTypeId(String isEnabled, String id, String lenghCVV, String name) {
        IsEnabled = isEnabled;
        this.id = id;
        this.lenghCVV = lenghCVV;
        this.name = name;
    }

    public String getIsEnabled() {
        return IsEnabled;
    }

    public void setIsEnabled(String isEnabled) {
        IsEnabled = isEnabled;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLenghCVV() {
        return lenghCVV;
    }

    public void setLenghCVV(String lenghCVV) {
        this.lenghCVV = lenghCVV;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
