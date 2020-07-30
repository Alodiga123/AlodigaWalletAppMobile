package com.alodiga.app.wallet.duallibrary.model;

public class ObjTopUpInfos {

    String commissionPercent;
    String country;
    String coutryId;
    String denomination;
    String denominationReceiver;
    String denominationSale;
    String destinationCurrency;
    String isOpenRange;
    String operatorid;
    String opertador;
    String skuid;
    String wholesalePrice;

    public ObjTopUpInfos(String commissionPercent, String country, String coutryId, String denomination, String denominationReceiver, String denominationSale, String destinationCurrency, String isOpenRange, String operatorid, String opertador, String skuid, String wholesalePrice) {
        this.commissionPercent = commissionPercent;
        this.country = country;
        this.coutryId = coutryId;
        this.denomination = denomination;
        this.denominationReceiver = denominationReceiver;
        this.denominationSale = denominationSale;
        this.destinationCurrency = destinationCurrency;
        this.isOpenRange = isOpenRange;
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

    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public String getDenominationReceiver() {
        return denominationReceiver;
    }

    public void setDenominationReceiver(String denominationReceiver) {
        this.denominationReceiver = denominationReceiver;
    }

    public String getDenominationSale() {
        return denominationSale;
    }

    public void setDenominationSale(String denominationSale) {
        this.denominationSale = denominationSale;
    }

    public String getDestinationCurrency() {
        return destinationCurrency;
    }

    public void setDestinationCurrency(String destinationCurrency) {
        this.destinationCurrency = destinationCurrency;
    }

    public String getIsOpenRange() {
        return isOpenRange;
    }

    public void setIsOpenRange(String isOpenRange) {
        this.isOpenRange = isOpenRange;
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
