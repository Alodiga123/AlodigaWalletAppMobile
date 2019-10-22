package com.alodiga.app.wallet.model;

public class ObjtopUpInfos_IsOpenRange {

    String commissionPercent;
    String country;
    String coutryId;
    String destinationCurrency;
    String increment;
    String isOpenRange;
    String maximumAmount;
    String minimumAmount;
    String operatorid;
    String opertador;
    String skuid;
    String wholesalePrice;

    public ObjtopUpInfos_IsOpenRange(String commissionPercent, String country, String coutryId, String destinationCurrency, String increment, String isOpenRange, String maximumAmount, String minimumAmount, String operatorid, String opertador, String skuid, String wholesalePrice) {
        this.commissionPercent = commissionPercent;
        this.country = country;
        this.coutryId = coutryId;
        this.destinationCurrency = destinationCurrency;
        this.increment = increment;
        this.isOpenRange = isOpenRange;
        this.maximumAmount = maximumAmount;
        this.minimumAmount = minimumAmount;
        this.operatorid = operatorid;
        this.opertador = opertador;
        this.skuid = skuid;
        this.wholesalePrice = wholesalePrice;
    }

    public String getCommissionPercent() {
        return commissionPercent;
    }

    public void setCommissionPercent(String commissionPercent) {
        this.commissionPercent = commissionPercent;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCoutryId() {
        return coutryId;
    }

    public void setCoutryId(String coutryId) {
        this.coutryId = coutryId;
    }

    public String getDestinationCurrency() {
        return destinationCurrency;
    }

    public void setDestinationCurrency(String destinationCurrency) {
        this.destinationCurrency = destinationCurrency;
    }

    public String getIncrement() {
        return increment;
    }

    public void setIncrement(String increment) {
        this.increment = increment;
    }

    public String getIsOpenRange() {
        return isOpenRange;
    }

    public void setIsOpenRange(String isOpenRange) {
        this.isOpenRange = isOpenRange;
    }

    public String getMaximumAmount() {
        return maximumAmount;
    }

    public void setMaximumAmount(String maximumAmount) {
        this.maximumAmount = maximumAmount;
    }

    public String getMinimumAmount() {
        return minimumAmount;
    }

    public void setMinimumAmount(String minimumAmount) {
        this.minimumAmount = minimumAmount;
    }

    public String getOperatorid() {
        return operatorid;
    }

    public void setOperatorid(String operatorid) {
        this.operatorid = operatorid;
    }

    public String getOpertador() {
        return opertador;
    }

    public void setOpertador(String opertador) {
        this.opertador = opertador;
    }

    public String getSkuid() {
        return skuid;
    }

    public void setSkuid(String skuid) {
        this.skuid = skuid;
    }

    public String getWholesalePrice() {
        return wholesalePrice;
    }

    public void setWholesalePrice(String wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }
}
