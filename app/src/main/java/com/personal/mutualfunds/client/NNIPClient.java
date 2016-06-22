package com.personal.mutualfunds.client;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.personal.mutualfunds.domain.Fund;
import com.personal.mutualfunds.domain.NNIPFund;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;

//TODO: generic
public class NNIPClient {

    //TODO: out
    private final static String NNIP_SERVICE = "https://nknb2xyowi.execute-api.eu-west-1.amazonaws.com/prod/funds?language=ro&country=RO&audience=PRIVATE&strategy=&_=1462630992531";

    public List<NNIPFund> getFundsJson() throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(NNIP_SERVICE)
                .build();

        //TODO: caching
        final String rawJSON = client.newCall(request).execute().body().string();

        Log.d("HTTP", rawJSON);

        Type listOfBase = new TypeToken<Fund>(){}.getType();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Fund.class, new FundDeserializer());
        Gson gson = gsonBuilder.create();
        List<NNIPFund> resp = gson.fromJson(rawJSON, listOfBase);

        Log.d("HTTP", String.valueOf(resp.size()));

        return resp;
    }

}
