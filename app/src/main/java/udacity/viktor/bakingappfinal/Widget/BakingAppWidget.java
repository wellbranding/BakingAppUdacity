package udacity.viktor.bakingappfinal.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import udacity.viktor.bakingappfinal.R;
import udacity.viktor.bakingappfinal.UI.Activities.MainActivity;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {

    @Inject
    SharedPreferences sharedPreferences;



    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, SharedPreferences sharedPreferences) {


        Intent intent = new Intent(context, MainActivity.class);
        Intent serviceIntent = new Intent(context, BakingAppWidgetService.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Construct the RemoteViews object

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);

        views.setRemoteAdapter(R.id.recipe_list_widget, serviceIntent );
        views.setPendingIntentTemplate(R.id.recipe_list_widget, pendingIntent);
        views.setOnClickPendingIntent(R.id.parent_relative_layout_widget, pendingIntent);

        int received = sharedPreferences.getInt("required", 0);
        views.setTextViewText(R.id.recipe_title_widget, String.valueOf(received));
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, sharedPreferences);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AndroidInjection.inject(this, context);
        super.onReceive(context, intent);

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

