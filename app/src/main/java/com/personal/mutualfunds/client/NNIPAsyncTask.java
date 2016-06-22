package com.personal.mutualfunds.client;

import android.os.AsyncTask;
import android.util.Log;

import com.personal.mutualfunds.domain.NNIPFund;

import java.io.IOException;
import java.util.List;

public class NNIPAsyncTask extends AsyncTask<String, Void, List<NNIPFund>> {

    private NNIPClient client = new NNIPClient();

    @Override
    protected List<NNIPFund> doInBackground(String... params) {
        try {
            return client.getFundsJson();
        } catch (IOException e) {
            Log.e("NNIPAsyncTask", "Something went wrong with the NNIP call", e);
        }
        return null;
    }
}
