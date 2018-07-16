package udacity.viktor.bakingappfinal.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import java.util.List;

import javax.inject.Inject;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import udacity.viktor.bakingappfinal.Dagger2.AppExecutors;
import udacity.viktor.bakingappfinal.Data.Networking.API.APIService;
import udacity.viktor.bakingappfinal.Data.Networking.API.ApiResponse;
import udacity.viktor.bakingappfinal.Data.Networking.API.LiveDataCallAdapterFactory;
import udacity.viktor.bakingappfinal.Data.Networking.API.RetrofitFactory;
import udacity.viktor.bakingappfinal.Data.Networking.Models.Recipe;
import udacity.viktor.bakingappfinal.db.MainDatabase;
import udacity.viktor.bakingappfinal.db.RecipesMainDao;

public class RecipiesRepository {

    APIService webservice;
    RecipesMainDao userDao;
    APIService apiService;
    AppExecutors appExecutors;
    MainDatabase mainDatabase;

    @Inject
    RecipiesRepository(AppExecutors appExecutors, APIService apiService, MainDatabase mainDatabase) {
        userDao = mainDatabase.recipeDao();
        this.appExecutors = appExecutors;
        this.mainDatabase = mainDatabase;
        this.apiService = apiService;

    }

    public LiveData<Resource<List<Recipe>>> loadRecipes() {
        return new NetworkBoundResource<List<Recipe>,List<Recipe>>(appExecutors) {
            @Override
            protected void saveCallResult(@NonNull List<Recipe> item) {
                userDao.insertAllRecipes(item);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Recipe> data) {
                return data == null || data.size() == 0;
            }

            @NonNull @Override
            protected LiveData<List<Recipe>> loadFromDb() {
                return userDao.getRecipes();
            }

            @NonNull @Override
            protected LiveData<ApiResponse<List<Recipe>>> createCall() {

                return RetrofitFactory.create().getRecipes();
            }
        }.getAsLiveData();
    }
}

