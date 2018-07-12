package udacity.viktor.bakingappfinal.UI.Activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import udacity.viktor.bakingappfinal.Data.Networking.Models.Recipe;
import udacity.viktor.bakingappfinal.R;
import udacity.viktor.bakingappfinal.Repository.Resource;
import udacity.viktor.bakingappfinal.Repository.Status;
import udacity.viktor.bakingappfinal.UI.Adapters.MainActivityAdapter;
import udacity.viktor.bakingappfinal.ViewModels.MainViewModel;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    MainViewModel mainViewModel;
    List<Recipe> mRecipesList;
    MainActivityAdapter mainActivityAdapter;

    @Inject
    ViewModelProvider.Factory ViewModelFactory;

    @Inject
    SharedPreferences sharedPreferences;

    private void InitializeAdapter()
    {

        mainActivityAdapter = new MainActivityAdapter(MainActivity.this);
        recyclerView.setAdapter(mainActivityAdapter);
        if(recyclerView.getTag().equals("tablet_mode"))
        {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 3);
            recyclerView.setLayoutManager(gridLayoutManager);
        }
        else
        {

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
            recyclerView.setLayoutManager(linearLayoutManager);
        }


    }
    private void NotifyAdapter() {
        mainActivityAdapter.setRecipeList(mRecipesList);
        mainActivityAdapter.notifyDataSetChanged();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recycler_view);
        InitializeAdapter();
        //USING CUSTOM FACTORY IN ORDER TO CREATE VIEWMODEL WITH CUSTOM CONSTRUCTOR
        mainViewModel = ViewModelProviders.of(this, ViewModelFactory).get(MainViewModel.class);
        HandleClick();
        mainViewModel.getRecipes().observe(this, listResource -> {

            assert listResource != null;
            Log.d("result", String.valueOf(listResource.status));
            if (listResource.status == Status.LOADING) {
                           }

                           else if (listResource.status == Status.SUCCESS) {
                mRecipesList = listResource.data;
                NotifyAdapter();
                progressBar.setVisibility(View.INVISIBLE);
            } else if (listResource.status == Status.ERROR) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(this, "Internet connection lost, could not fetch data", Toast.LENGTH_LONG).show();
            }
        });
        ;


    }
    private void HandleClick()
    {
       mainActivityAdapter.setOnItemCLickListener(new MainActivityAdapter.OnItemCLickListener() {
           @Override
           public void OnItemClick(int position) {
               Intent intent = new Intent(MainActivity.this, DetailsOfRecipeActivity.class);
               Bundle bundle = new Bundle();
               bundle.putString("single_recipe_name", mRecipesList.get(position).getName());
               bundle.putParcelableArrayList("recipe_steps",  (ArrayList<? extends Parcelable>)
                       mRecipesList.get(position).getSteps());
               bundle.putInt("recipe_id", position);
               bundle.putParcelableArrayList("recipe_ingridients",  (ArrayList<? extends Parcelable>)
                       mRecipesList.get(position).getIngredients());
               intent.putExtra("values", bundle);
               startActivity(intent);

           }
       });
    }
}
