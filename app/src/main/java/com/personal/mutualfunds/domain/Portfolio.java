package com.personal.mutualfunds.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Portfolio {

    private String name;
    private List<Fund> funds = new ArrayList<>();

    private Map<String, Double> currencyOriginalAmountMap = new HashMap<>();
    private Map<String, Double> currencyCurrentAmountMap = new HashMap<>();

    private double totalOriginalAmount;
    private double totalCurrentAmount;
    private double profitAmount;
    private double profitPercentage;

    public Portfolio(final String name){
        this.name = name;
    }

    public void addFund(final Fund fund){
        funds.add(fund);

        final String fundCurrency = fund.getNnipFund().getCurrency();

        if (!currencyOriginalAmountMap.containsKey(fundCurrency)){
            currencyOriginalAmountMap.put(fundCurrency, fund.getMyFund().getOriginalAmount());
        } else {
            currencyOriginalAmountMap.put(fundCurrency, currencyOriginalAmountMap.get(fundCurrency) + fund.getMyFund().getOriginalAmount());
        }

        if (!currencyCurrentAmountMap.containsKey(fundCurrency)){
            currencyCurrentAmountMap.put(fundCurrency, fund.getCurrentAmount());
        } else {
            currencyCurrentAmountMap.put(fundCurrency, currencyCurrentAmountMap.get(fundCurrency) + fund.getCurrentAmount());
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Fund> getFunds() {
        return funds;
    }

    public void setFunds(List<Fund> funds) {
        this.funds = funds;
    }

    public Map<String, Double> getCurrencyCurrentAmountMap() {
        return currencyCurrentAmountMap;
    }

    public Map<String, Double> getCurrencyOriginalAmountMap() {
        return currencyOriginalAmountMap;
    }
}
