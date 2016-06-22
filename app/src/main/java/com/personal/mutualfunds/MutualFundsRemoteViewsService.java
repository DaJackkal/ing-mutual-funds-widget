package com.personal.mutualfunds;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by Arnold.Gentz on 11-Jun-16.
 */
public class MutualFundsRemoteViewsService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new MutualFundsRemoteViewsFactory(this.getApplicationContext(), intent));
    }
}
