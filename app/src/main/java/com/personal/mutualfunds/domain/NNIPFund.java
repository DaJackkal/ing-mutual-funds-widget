package com.personal.mutualfunds.domain;

/**
 * Created by Arnold.Gentz on 19-Jun-16.
 */
public class NNIPFund {

    private String code;
    private String name;
    private String currency;
    private double currentPrice;

    public NNIPFund(String code, String name, String currency, double currentPrice) {
        this.code = code;
        this.name = name;
        this.currency = currency;
        this.currentPrice = currentPrice;
    }

    public String getCode() {
        return code;
    }

    public String getName(){
        return name;
    }

    public String getCurrency() {
        return currency;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }
}
