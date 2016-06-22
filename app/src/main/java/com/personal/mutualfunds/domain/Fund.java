package com.personal.mutualfunds.domain;

public class Fund {

    private MyFund myFund;
    private NNIPFund nnipFund;

    //Computed values
    private double currentAmount;
    private double profit;
    private double profitPercentage;

    public Fund(MyFund myFund, NNIPFund nnipFund){
        this.myFund = myFund;
        this.nnipFund = nnipFund;
        this.currentAmount = myFund.getUnits() * nnipFund.getCurrentPrice();
        this.profit = this.currentAmount - myFund.getOriginalAmount();
        this.profitPercentage = this.currentAmount * 100/myFund.getOriginalAmount() - 100;
    }

    public MyFund getMyFund() {
        return myFund;
    }

    public NNIPFund getNnipFund() {
        return nnipFund;
    }

    public double getCurrentAmount() {
        return currentAmount;
    }

    public double getProfit() {
        return profit;
    }

    public double getProfitPercentage() {
        return profitPercentage;
    }
}
