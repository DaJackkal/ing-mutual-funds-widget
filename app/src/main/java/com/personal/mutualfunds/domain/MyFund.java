package com.personal.mutualfunds.domain;

/**
 * Created by Arnold.Gentz on 19-Jun-16.
 */
public class MyFund {

    private String name;
    private double units;
    private double originalPrice;
    private double originalAmount;

    public MyFund(String name, double units, double originalPrice) {
        this.name = name;
        this.units = units;
        this.originalPrice = originalPrice;
        this.originalAmount = this.units * this.originalPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getUnits() {
        return units;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public double getOriginalAmount() {
        return originalAmount;
    }
}
