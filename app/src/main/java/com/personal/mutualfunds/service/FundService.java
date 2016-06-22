package com.personal.mutualfunds.service;

import com.personal.mutualfunds.client.NNIPAsyncTask;
import com.personal.mutualfunds.domain.Fund;
import com.personal.mutualfunds.domain.MyFund;
import com.personal.mutualfunds.domain.NNIPFund;
import com.personal.mutualfunds.domain.Portfolio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by Arnold.Gentz on 18-Jun-16.
 */
//TODO: refactor this mess and make it nice
public class FundService {

    private static final String PREFIX = "NN (L) ";
    private static final String SUFFIX_SEPARATOR = " - ";

    //TODO: temporary - out to configuration screen
    private static final Map<String, MyFund> MY_FUND_MAP = new HashMap<>();
    static {
        MY_FUND_MAP.put("L104_XCA", new MyFund("International Romanian Bond", 5.8500, 1709.4700));
        MY_FUND_MAP.put("L172_XCA", new MyFund("Euro Fixed Income", 1.4130, 530.6500));
        MY_FUND_MAP.put("L504_XCA", new MyFund("Euro Credit", 4.5820, 163.6800));
        MY_FUND_MAP.put("L142_XCA", new MyFund("European Real Estate", 0.9940, 1005.5600));
        MY_FUND_MAP.put("L164_XCA", new MyFund("Global Sustainable Equity", 2.9060, 258.0600));
        MY_FUND_MAP.put("L132_XCA", new MyFund("Euro High Dividend", 1.7220, 435.4100));
    }

    private static FundService INSTANCE;

    private FundService(){}

    public static FundService getInstance(){
        if (INSTANCE == null){
            return new FundService();
        }
        return INSTANCE;
    }

    public Portfolio getFunds() throws ExecutionException, InterruptedException {
        List<NNIPFund> funds = new NNIPAsyncTask().execute("call-me-maybe").get();
        return filterFunds(funds);
    }

    private Portfolio filterFunds(final List<NNIPFund> allFunds){
        Portfolio portfolio = new Portfolio("My Portfolio");

        for (final NNIPFund nnipFund : allFunds){
            if (isMyFund(nnipFund)){
                portfolio.addFund(toFund(nnipFund));
            }
        }

        return portfolio;
    }

    private boolean isMyFund(final NNIPFund fund){
        return MY_FUND_MAP.containsKey(fund.getCode());
    }

    private Fund toFund(final NNIPFund nnipFund){
        return new Fund(toMyFund(nnipFund), nnipFund);
    }

    private MyFund toMyFund(final NNIPFund fund){
        MyFund myFund = MY_FUND_MAP.get(fund.getCode());
        myFund.setName(getViewFundName(fund));
        return myFund;
    }

    private String getViewFundName(final NNIPFund fund){
        final int indexOfActualNameStart = fund.getName().indexOf(PREFIX) + PREFIX.length();
        return fund.getName().substring(indexOfActualNameStart).split(SUFFIX_SEPARATOR)[0];
    }

}
