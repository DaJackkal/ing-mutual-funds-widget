package com.personal.mutualfunds.client;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.personal.mutualfunds.domain.Fund;
import com.personal.mutualfunds.domain.NNIPFund;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arnold.Gentz on 12-Jun-16.
 */
public class FundDeserializer implements JsonDeserializer<List<NNIPFund>> {

    private static final String PORTFOLIO_CODE = "portfolioCode";
    private static final String NAME = "fundName";
    private static final String CURRENCY = "currency";
    private static final String CURRENT_PRICE = "currentNav";

    @Override
    public List<NNIPFund> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        List<NNIPFund> funds = new ArrayList<>();

        for (int i = 0; i < json.getAsJsonArray().size(); i++){
            JsonObject jsonObject = json.getAsJsonArray().get(i).getAsJsonObject();
            final String portfolioCode = jsonObject.get(PORTFOLIO_CODE).getAsString();
            final String name = jsonObject.get(NAME).getAsString();
            final String currency = jsonObject.get(CURRENCY).getAsString();
            final double currentPrice = jsonObject.get(CURRENT_PRICE).getAsDouble();
            funds.add(new NNIPFund(portfolioCode, name, currency, currentPrice));
        }

        return funds;
    }

}
