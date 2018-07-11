package udacity.viktor.bakingappfinal.UI.Activities;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

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

public class DetailsOfRecipeActivity extends AppCompatActivity
implements DetailsListStepFragment.OnFragmentInteractionListener, DetailStepFragment.OnFragmentInteractionListener,
       DetailsListListener, HasSupportFragmentInjector
{

    private boolean mTwoPane;
    private List<Step> stepList;
    private List<Ingredient> ingredientList;
    Bundle bundle;
    String recipe_name;

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        setContentView(R.layout.details_of_recipe_activity);
      //  if(findViewById(R.id.)
        //
        // )
         bundle = getIntent().getBundleExtra("values");
        stepList = bundle.getParcelableArrayList("recipe_steps");
        recipe_name = bundle.getString("single_recipe_name");
        getSupportActionBar().setTitle(recipe_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ingredientList = bundle.getParcelableArrayList("recipe_ingridients");

        if(findViewById(R.id.recipe_information_container)!=null)
        {
            mTwoPane = true;
        }
        else mTwoPane = false;



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
                    replace(R.id.recipe_information_container, detailStepFragment).commit();

            DetailsListStepFragment detailsListStepFragment = new DetailsListStepFragment();
            detailsListStepFragment.setArguments(this.bundle);
          //  detailsListStepFragment.setFragmentListener(this);
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.recipe_steps_container, detailsListStepFragment).commit();


        }
        else
        {
            DetailsListStepFragment detailsListStepFragment = new DetailsListStepFragment();
            detailsListStepFragment.setArguments(bundle);
           // detailsListStepFragment.setFragmentListener(this);
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.recipe_steps_container, detailsListStepFragment).commit();

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
                    replace(R.id.recipe_steps_container, detailStepFragment).commit();

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
                    replace(R.id.recipe_information_container, detailStepFragment).commit();

        }

    }
}
