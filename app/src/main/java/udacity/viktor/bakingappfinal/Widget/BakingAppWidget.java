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

import static dagger.android.AndroidInjection.inject;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {

    @Inject
    SharedPreferences sharedPreferences;



    public void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId[]) {
        inject(this, context);
        for (int singleId : appWidgetId) {
            Intent intent = new Intent(context, MainActivity.class);
            Intent serviceIntent = new Intent(context, BakingAppWidgetService.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(
                    context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
            views.setRemoteAdapter(R.id.recipe_list_widget, serviceIntent);
            views.setPendingIntentTemplate(R.id.recipe_list_widget, pendingIntent);
            views.setOnClickPendingIntent(R.id.parent_relative_layout_widget, pendingIntent);

            String title = sharedPreferences.getString(context.getString(R.string.prefs_widget_recipe_name),
                    "Add recipe in oder to see");

            views.setTextViewText(R.id.recipe_title_widget, title);
            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(singleId, views);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        inject(this, context);
        // There may be multiple widgets active, so update all of them

            updateAppWidget(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
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

