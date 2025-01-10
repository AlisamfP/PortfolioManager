/*
Author: Alisa Palson
Date created: 11/25/2024
Purpose: Final Course Project
 */ 

public class TransactionHistory {
    private String ticker; // Will store ticker of the Stock or CASH
    private String transDate; // Date when the transaction occurred
    private String transType; // Type of transaction BUY/SELL for stock, DEPOSIT/WITHDRAW for CASH
    private double qty; // Quantity for the transaction
    private double costBasis; // Cost Basis of stock. For CASH this will be 1.00

    public TransactionHistory(String ticker, String transDate, String transType, double qty, double costBasis){
        this.ticker = ticker;
        this.transDate = transDate;
        this.transType = transType;
        this.qty = qty;
        this.costBasis = costBasis;
    }
    public TransactionHistory(){
        ticker = "N/A";
        transDate = "N/A";
        transType = "N/A";
        qty = 0.0;
        costBasis = 0.0;
    }

    public void setTicker(String ticker){
        this.ticker = ticker;
    }

    public String getTicker(){
        return ticker;
    }

    public void setTransDate(String transDate){
        this.transDate = transDate;
    }

    public String getTransDate(){
        return transDate;
    }

    public void setTransType(String transType){
        this.transType = transType;
    }

    public String getTransType(){
        return transType;
    }

    public void setQty(double qty){
        this.qty = qty;
    }

    public double getQty(){
        return qty;
    }

    public void setCostBasis(double costBasis){
        this.costBasis = costBasis;
    }

    public double getCostBasis(){
        return costBasis;
    }
}