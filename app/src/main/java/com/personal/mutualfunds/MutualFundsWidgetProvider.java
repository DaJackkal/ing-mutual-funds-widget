package com.personal.mutualfunds;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.personal.mutualfunds.domain.Portfolio;
import com.personal.mutualfunds.service.FundService;
import com.personal.mutualfunds.util.MoneyUtil;

import java.util.concurrent.ExecutionException;

public class MutualFundsWidgetProvider extends AppWidgetProvider {

    private static final String REFRESH_ACTION = "refreshAction";
    private static final String EUR = "EUR";
    private static final String RON = "RON";

    private FundService fundService = FundService.getInstance();

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        Toast.makeText(context, "OnUpdate", Toast.LENGTH_SHORT).show();

        try {
            final Portfolio portfolio = fundService.getFunds();

            //TODO: refactor it
            final double eurProfitLoss = portfolio.getCurrencyCurrentAmountMap().get(EUR) - portfolio.getCurrencyOriginalAmountMap().get(EUR);
            final double ronProfitLoss = portfolio.getCurrencyCurrentAmountMap().get(RON) - portfolio.getCurrencyOriginalAmountMap().get(RON);

            final double ronProfitLossPercentage = portfolio.getCurrencyCurrentAmountMap().get(RON) * 100 / portfolio.getCurrencyOriginalAmountMap().get(RON) - 100;
            final double eurProfitLossPercentage = portfolio.getCurrencyCurrentAmountMap().get(EUR) * 100 / portfolio.getCurrencyOriginalAmountMap().get(EUR) - 100;

            for(int i=0;i<appWidgetIds.length;i++)
            {
                RemoteViews rv = new RemoteViews(context.getPackageName(),
                        R.layout.funds);

                Intent intent = new Intent(context, MutualFundsRemoteViewsService.class);
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
                intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

                rv.setRemoteAdapter(R.id.fundsList, intent);
//                rv.setOnClickPendingIntent(R.id.refresh, getPendingSelfIntent(context, appWidgetIds[i], REFRESH_ACTION));

                rv.setTextViewText(R.id.plRon, MoneyUtil.formatAmount(ronProfitLoss, MoneyUtil.TWO_DECIMALS) + " (" + MoneyUtil.formatAmount(ronProfitLossPercentage, MoneyUtil.TWO_DECIMALS) + "%)");
                rv.setTextViewText(R.id.plEur, MoneyUtil.formatAmount(eurProfitLoss, MoneyUtil.TWO_DECIMALS) + " (" + MoneyUtil.formatAmount(eurProfitLossPercentage, MoneyUtil.TWO_DECIMALS) + "%)");
                rv.setTextColor(R.id.plRon, ronProfitLoss > 0 ? Color.GREEN : Color.RED);
                rv.setTextColor(R.id.plEur, eurProfitLoss > 0 ? Color.GREEN : Color.RED);

                appWidgetManager.updateAppWidget(appWidgetIds[i], rv);
            }
            super.onUpdate(context, appWidgetManager, appWidgetIds);

            Toast.makeText(context, "Updating the funds", Toast.LENGTH_SHORT).show();
        } catch (ExecutionException e) {
            //TODO: show nice UI error and log it and make the scope of the catch smaller
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        super.onReceive(context, intent);

        if (REFRESH_ACTION.equals(intent.getAction())) {

//            Toast.makeText(context, "Refreshing", Toast.LENGTH_SHORT).show();

//            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
//
//            RemoteViews remoteViews;
//            ComponentName watchWidget;
//
//            remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
//            watchWidget = new ComponentName(context, Widget.class);
//
//            remoteViews.setTextViewText(R.id.sync_button, "TESTING");
//
//            appWidgetManager.updateAppWidget(watchWidget, remoteViews);

        }
    }

    private PendingIntent getPendingSelfIntent(final Context context, final int appWidgetId, final String action){
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, appWidgetId, intent, 0);
    }
}
