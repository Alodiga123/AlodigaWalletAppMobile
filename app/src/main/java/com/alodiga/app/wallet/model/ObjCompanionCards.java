package com.alodiga.app.wallet.model;

public class ObjCompanionCards {
    private String id;
    private String nameCard;
    private String numberCard;
    private String numberCardEncrip;
    private String parentId;
    private  int imageCard;

    public ObjCompanionCards(String id, String nameCard, String numberCard, String parentId, String numberCardEncrip, int image) {
        this.id = id;
        this.nameCard = nameCard;
        this.numberCard = numberCard;
        this.parentId = parentId;
        this.numberCardEncrip= numberCardEncrip;
        this.imageCard= image;
    }

    public int getImageCard() {
        return imageCard;
    }

    public void setImageCard(int imageCard) {
        this.imageCard = imageCard;
    }

    public String getNumberCardEncrip() {
        return numberCardEncrip;
    }

    public void setNumberCardEncrip(String numberCardEncrip) {
        this.numberCardEncrip = numberCardEncrip;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameCard() {
        return nameCard;
    }

    public void setNameCard(String nameCard) {
        this.nameCard = nameCard;
    }

    public String getNumberCard() {
        return numberCard;
    }

    public void setNumberCard(String numberCard) {
        this.numberCard = numberCard;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
