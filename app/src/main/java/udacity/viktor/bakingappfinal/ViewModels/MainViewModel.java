package udacity.viktor.bakingappfinal.ViewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;


import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import udacity.viktor.bakingappfinal.Data.Networking.API.RetrofitFactory;
import udacity.viktor.bakingappfinal.Data.Networking.Models.Recipe;
import udacity.viktor.bakingappfinal.Repository.RecipiesRepository;
import udacity.viktor.bakingappfinal.Repository.Resource;

public class MainViewModel extends ViewModel {


    LiveData<Resource<List<Recipe>>> recipes;
    private final RecipiesRepository repository;


    @Inject
    public MainViewModel(RecipiesRepository recipiesRepository) {
        this.repository = recipiesRepository;
    }

    public LiveData<Resource<List<Recipe>>> getRecipes() {
        if (recipes == null) {
            recipes = repository.loadRecipes();
        }

        return recipes;
    }
}
