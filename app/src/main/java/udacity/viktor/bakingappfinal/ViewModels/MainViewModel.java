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
import udacity.viktor.bakingappfinal.Data.Networking.Models.RecipyForRoom;
import udacity.viktor.bakingappfinal.Repository.RecipiesRepository;
import udacity.viktor.bakingappfinal.Repository.Resource;

public class MainViewModel extends ViewModel {


    LiveData<Resource<List<Recipe>>> recipes;
    private final RecipiesRepository repository;

//    public MainViewModel(@NonNull Application application) {
//
//        super(application);
//        repository = new RecipiesRepository(application);
//
//    }

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
//
//    public LiveData<List<Recipe>> getRecipes() {
//        if (recipes == null) {
//            recipes =repository.loadRecipes();
//         //   recipes = new MutableLiveData<>();
//         // QueryRecipes();
//        }
//        return recipes;
//
//    }
//    private MutableLiveData<List<Recipe>> QueryRecipes()
//    {
//
//       // recipes = new MutableLiveData<>();
//        Call<List<Recipe>> call = RetrofitFactory.create().getRecipes();
//        call.enqueue(new Callback<List<Recipe>>() {
//            @Override
//            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
//
//                recipes.postValue(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<List<Recipe>> call, Throwable t) {
//                Log.d("error", t.getMessage());
//
//            }
//        });
//        return recipes;
//
//
//
//
//        }




}
