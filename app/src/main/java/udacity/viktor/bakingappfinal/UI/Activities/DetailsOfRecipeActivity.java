package udacity.viktor.bakingappfinal.UI.Activities;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import udacity.viktor.bakingappfinal.Data.Networking.Models.Ingredient;
import udacity.viktor.bakingappfinal.Data.Networking.Models.Step;
import udacity.viktor.bakingappfinal.R;
import udacity.viktor.bakingappfinal.UI.Fragments.DetailStepFragment;
import udacity.viktor.bakingappfinal.UI.Fragments.DetailsListListener;
import udacity.viktor.bakingappfinal.UI.Fragments.DetailsListStepFragment;
import udacity.viktor.bakingappfinal.Widget.BakingAppWidget;

import static java.security.AccessController.getContext;

public class DetailsOfRecipeActivity extends AppCompatActivity
implements DetailsListStepFragment.OnFragmentInteractionListener, DetailStepFragment.OnFragmentInteractionListener,
       DetailsListListener, HasSupportFragmentInjector
{

    private static final String BACK_STACK_ROOT_TAG = "root_fragment";
    private boolean mTwoPane;
    private List<Step> stepList;
    private List<Ingredient> ingredientList;
    private int recipeId;
    Bundle bundle;
    String recipe_name;

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Inject
    SharedPreferences sharedPreferences;

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_of_recipe_activity);


      //  if(findViewById(R.id.)
        //
        // )

         bundle = getIntent().getBundleExtra("values");
        stepList = bundle.getParcelableArrayList("recipe_steps");
        recipe_name = bundle.getString("single_recipe_name");
        recipeId = bundle.getInt("recipe_id");
        ingredientList = bundle.getParcelableArrayList("recipe_ingridients");


        if(findViewById(R.id.recipe_information_container)!=null)
        {
            mTwoPane = true;
        }
        else mTwoPane = false;

        Toolbar toolbar = findViewById(R.id.toolbar);
       // if(mTwoPane)
        {
            toolbar.setNavigationOnClickListener(v -> onBackPressed());
            setSupportActionBar(toolbar);
           getSupportActionBar().setTitle(recipe_name);
          getSupportActionBar().setDisplayShowHomeEnabled(true);
          getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            Button button = findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sharedPreferences.edit().putInt(getString(
                            R.string.prefs_widget_recipe_id), recipeId).apply();
                    sharedPreferences.edit().putString(getString(
                            R.string.prefs_widget_recipe_name), recipe_name).apply();
       AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(DetailsOfRecipeActivity.this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(DetailsOfRecipeActivity.this, BakingAppWidget.class));


        BakingAppWidget bakingAppWidget = new BakingAppWidget();
           bakingAppWidget.updateAppWidget(DetailsOfRecipeActivity.this, appWidgetManager, appWidgetIds);
                    appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.recipe_list_widget);
                    Toast.makeText(DetailsOfRecipeActivity.this, "Display widget and view ingredients ", Toast.LENGTH_SHORT).show();
                }
            });


        }


        if(savedInstanceState==null)
        InitializeViews(mTwoPane);
        if(mTwoPane)
        {

        }



    }

    private void InitializeViews(boolean mTwoPane)
    {
        if(mTwoPane)
        {
            DetailStepFragment detailStepFragment = new DetailStepFragment();
            Bundle bundle = new Bundle();
           bundle.putParcelableArrayList("steps", (ArrayList<? extends Parcelable>) stepList);
             bundle.putInt("current_step", 0);
            bundle.putBoolean("is_tablet", true);
            detailStepFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().
                    add(R.id.recipe_information_container, detailStepFragment).commit();

            DetailsListStepFragment detailsListStepFragment = new DetailsListStepFragment();
            this.bundle.putBoolean("is_tablet", true);
            detailsListStepFragment.setArguments(this.bundle);
          //  detailsListStepFragment.setFragmentListener(this);
            getSupportFragmentManager().beginTransaction().
                    add(R.id.recipe_steps_container, detailsListStepFragment).commit();


        }
        else
        {
            DetailsListStepFragment detailsListStepFragment = new DetailsListStepFragment();
            this.bundle.putBoolean("is_tablet", false);
            detailsListStepFragment.setArguments(bundle);

           // detailsListStepFragment.setFragmentListener(this);
        getSupportFragmentManager().popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            getSupportFragmentManager().beginTransaction().
                    add(R.id.recipe_steps_container, detailsListStepFragment).
                    commit();

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            ; // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            Log.i("MainActivity", "popping backstack");
            fm.popBackStack();
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");
            super.onBackPressed();
        }
    }



    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void setCurrentStep(int index) {
        if(!mTwoPane)
        {
            DetailStepFragment detailStepFragment = new DetailStepFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("steps", (ArrayList<? extends Parcelable>) stepList);
            bundle.putInt("current_step", index);
            bundle.putBoolean("is_tablet", false);
            detailStepFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.recipe_steps_container, detailStepFragment).
            addToBackStack(null).commit();

        }
        else
        {
            DetailStepFragment detailStepFragment = new DetailStepFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("steps", (ArrayList<? extends Parcelable>) stepList);
            bundle.putInt("current_step", index);
            bundle.putBoolean("is_tablet", true);
            detailStepFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.recipe_information_container, detailStepFragment).
                    commit();

        }

    }
}
