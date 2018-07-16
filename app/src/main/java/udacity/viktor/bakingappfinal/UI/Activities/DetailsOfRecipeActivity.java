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
import android.widget.TextView;
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
        DetailsListListener, HasSupportFragmentInjector {

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


        bundle = getIntent().getBundleExtra(getString(R.string.values));
        stepList = bundle.getParcelableArrayList(getString(R.string.recipe_steps));
        recipe_name = bundle.getString(getString(R.string.single_recipe_name));
        recipeId = bundle.getInt(getString(R.string.recipe_id));
        ingredientList = bundle.getParcelableArrayList(getString(R.string.recipe_ingredients));


        if (findViewById(R.id.recipe_information_container) != null) {
            mTwoPane = true;
        } else mTwoPane = false;

        Toolbar toolbar = findViewById(R.id.toolbar);
        // if(mTwoPane)
        {
            toolbar.setNavigationOnClickListener(v -> onBackPressed());
            setSupportActionBar(toolbar);
            TextView toolbarTitle = findViewById(R.id.toolbar_title);
            toolbarTitle.setText(recipe_name);
            Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
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
                    Toast.makeText(DetailsOfRecipeActivity.this, R.string.display_widget_toast, Toast.LENGTH_SHORT).show();
                }
            });


        }


        if (savedInstanceState == null)
            InitializeViews(mTwoPane);
        if (mTwoPane) {

        }


    }

    private void InitializeViews(boolean mTwoPane) {
        if (mTwoPane) {
            DetailStepFragment detailStepFragment = new DetailStepFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(getString(R.string.steps), (ArrayList<? extends Parcelable>) stepList);
            bundle.putInt(getString(R.string.current_step), 0);
            bundle.putBoolean(getString(R.string.is_tablet), true);
            detailStepFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().
                    add(R.id.recipe_information_container, detailStepFragment).commit();

            DetailsListStepFragment detailsListStepFragment = new DetailsListStepFragment();
            this.bundle.putBoolean(getString(R.string.is_tablet), true);
            detailsListStepFragment.setArguments(this.bundle);
            getSupportFragmentManager().beginTransaction().
                    add(R.id.recipe_steps_container, detailsListStepFragment).commit();


        } else {
            DetailsListStepFragment detailsListStepFragment = new DetailsListStepFragment();
            this.bundle.putBoolean(getString(R.string.is_tablet), false);
            detailsListStepFragment.setArguments(bundle);
            getSupportFragmentManager().popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            getSupportFragmentManager().beginTransaction().
                    add(R.id.recipe_steps_container, detailsListStepFragment).
                            commit();

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 1) {
            int count = fm.getBackStackEntryCount();
            for (int i = 0; i < count; ++i) {
                fm.popBackStackImmediate();
            }
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void setCurrentStep(int index, boolean firstTime) {
        if (!mTwoPane) {
            DetailStepFragment detailStepFragment = new DetailStepFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(getString(R.string.steps), (ArrayList<? extends Parcelable>) stepList);
            bundle.putInt(getString(R.string.current_step), index);
            bundle.putBoolean(getString(R.string.is_tablet), false);
            detailStepFragment.setArguments(bundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            if (firstTime) {
                fragmentManager.beginTransaction().
                        replace(R.id.recipe_steps_container, detailStepFragment, getString(R.string.root_fragment)).
                        addToBackStack(null).commit();
            } else {
                fragmentManager.beginTransaction().
                        replace(R.id.recipe_steps_container, detailStepFragment, getString(R.string.new_fragment)).
                        addToBackStack(null).commit();
            }

        } else {
            DetailStepFragment detailStepFragment = new DetailStepFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(getString(R.string.steps), (ArrayList<? extends Parcelable>) stepList);
            bundle.putInt(getString(R.string.current_step), index);
            bundle.putBoolean(getString(R.string.is_tablet), true);
            detailStepFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.recipe_information_container, detailStepFragment).

                    commit();

        }

    }
}
