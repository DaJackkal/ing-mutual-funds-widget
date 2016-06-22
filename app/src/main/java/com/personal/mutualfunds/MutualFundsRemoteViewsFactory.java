package com.personal.mutualfunds;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import com.personal.mutualfunds.domain.Fund;
import com.personal.mutualfunds.domain.Portfolio;
import com.personal.mutualfunds.service.FundService;
import com.personal.mutualfunds.util.MoneyUtil;

import java.text.DecimalFormat;
import java.util.concurrent.ExecutionException;

import static com.personal.mutualfunds.util.MoneyUtil.FOUR_DECIMALS;
import static com.personal.mutualfunds.util.MoneyUtil.TWO_DECIMALS;
import static com.personal.mutualfunds.util.MoneyUtil.formatAmount;

/**
 * Created by Arnold.Gentz on 11-Jun-16.
 */
public class MutualFundsRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;

    private FundService fundService = FundService.getInstance();
    private Portfolio portfolio;

    public MutualFundsRemoteViewsFactory(Context context, Intent intent) {
        Toast.makeText(context, "PLM", Toast.LENGTH_SHORT).show();
        this.context = context;
        final int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        Log.d("AppWidgetId", String.valueOf(appWidgetId));
    }

    @Override
    public void onCreate() {
        try {
            portfolio = fundService.getFunds();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDataSetChanged() {
        //funds = getFunds();
    }

    @Override
    public void onDestroy() {
        portfolio = null;
    }

    @Override
    public int getCount() {
        return portfolio.getFunds().size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Log.d("WidgetCreatingView", "WidgetCreatingView");
        RemoteViews remoteView = new RemoteViews(context.getPackageName(),
                R.layout.row);

        Fund currentFund = portfolio.getFunds().get(position);

        remoteView.setTextViewText(R.id.fundName, currentFund.getMyFund().getName());
        remoteView.setTextViewText(R.id.fundUnits, formatAmount(currentFund.getMyFund().getUnits(), FOUR_DECIMALS) + " @ " + formatAmount(currentFund.getMyFund().getOriginalPrice(), FOUR_DECIMALS) + " " + currentFund.getNnipFund().getCurrency());
        remoteView.setTextViewText(R.id.fundCurrentPrice, formatAmount(currentFund.getNnipFund().getCurrentPrice(), FOUR_DECIMALS));
        remoteView.setTextViewText(R.id.fundProfitAmount, formatAmount(currentFund.getProfit(), TWO_DECIMALS));
        remoteView.setTextViewText(R.id.fundProfitPercentage, formatAmount(currentFund.getProfitPercentage(), TWO_DECIMALS));
        remoteView.setTextColor(R.id.fundProfitPercentage, currentFund.getProfitPercentage() >= 0 ? Color.GREEN : Color.RED);

        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

}
