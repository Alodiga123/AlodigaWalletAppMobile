package com.example.c2paplicationmobile;



public class ObjTransaction {

    private String transactionId;
    private String productName;
    private String transactionType;
    private String createionDate;
    private String amount;
    private String tax;
    private String commision;
    private String userDestination;



    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getCreateionDate() {
        return createionDate;
    }

    public void setCreateionDate(String createionDate) {
        this.createionDate = createionDate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getCommision() {
        return commision;
    }

    public void setCommision(String commision) {
        this.commision = commision;
    }

    public String getUserDestination() {
        return userDestination;
    }

    public void setUserDestination(String userDestination) {
        this.userDestination = userDestination;
    }
}