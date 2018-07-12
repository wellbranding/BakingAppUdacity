package udacity.viktor.bakingappfinal.Widget;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import udacity.viktor.bakingappfinal.Data.Networking.Models.Ingredient;
import udacity.viktor.bakingappfinal.Data.Networking.Models.Recipe;
import udacity.viktor.bakingappfinal.R;
import udacity.viktor.bakingappfinal.db.MainDatabase;

public class BakingAppWidgetService extends RemoteViewsService {

    @Inject
    SharedPreferences sharedPreferences;
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent)
    {
        return new BakingAppWidgetRemoteViewsFactory(getApplicationContext(), sharedPreferences);
    }

    @Override
    public void onCreate() {
        AndroidInjection.inject(this);
        super.onCreate();
    }

    //    public void onClickAddWidget() {
//        PrefsUtil.setWidgetTitle(getContext(), recipe.getName());
//        PrefsUtil.setWidgetRecipeId(getContext(), recipe.getId());
//
//        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getContext());
//        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
//                new ComponentName(getContext(), BakingAppWidget.class));
//
//        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_listview_ingredients);
//
//        BakingAppWidget.updateAppWidget(getContext(), appWidgetManager, appWidgetIds);
//    }
    class BakingAppWidgetRemoteViewsFactory implements BakingAppWidgetService.RemoteViewsFactory
    {

        MainDatabase mainDatabase;
        Context mContext;
       SharedPreferences sharedPreferences;
        List<Ingredient> ingredientList;


        public BakingAppWidgetRemoteViewsFactory(Context context, SharedPreferences sharedPreferences)
        {
            mainDatabase = Room.databaseBuilder(context, MainDatabase.class,"baking_app" )
                    .build();
            mContext = context;
            this.sharedPreferences = sharedPreferences;


        }


        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
           int idFromRecipe = sharedPreferences.getInt(mContext.getString(
                   R.string.prefs_widget_recipe_id), -1);
           if(idFromRecipe>=0)
           {

               Recipe recipeFromId = mainDatabase.recipeDao().getRecipe(idFromRecipe);

               if(recipeFromId!=null)
               {
                   ingredientList = new ArrayList<>();
                   ingredientList.addAll(recipeFromId.getIngredients());

               }


           }

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
           if(ingredientList==null)
               return 0;
           return ingredientList.size();

        }

        @Override
        public RemoteViews getViewAt(int position) {
            if (position == AdapterView.INVALID_POSITION || ingredientList == null) {
                return null;
            }

            String formated = ingredientList.get(position).getIngredient() + " " +
                    ingredientList.get(position).getQuantity() + " " +
                    ingredientList.get(position).getMeasure();


            RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.text_view_widget);
            remoteViews.setTextViewText(R.id.list_view_item_widget,
                    formated );
            Intent fillIntent = new Intent();
            remoteViews.setOnClickFillInIntent(R.id.list_view_item_widget, fillIntent );
            return  remoteViews;
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
            return true;
        }
    }
}
