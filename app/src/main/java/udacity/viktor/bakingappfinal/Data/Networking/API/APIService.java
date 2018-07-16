package udacity.viktor.bakingappfinal.Data.Networking.API;


import android.arch.lifecycle.LiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import udacity.viktor.bakingappfinal.Data.Networking.Models.Recipe;
import udacity.viktor.bakingappfinal.R;

/**
 * Created by Belal on 14/04/17.
 */

public interface APIService {

    String BASE_URL = APIUrl.BASE_URL;
    @GET("baking.json")
    LiveData<ApiResponse<List<Recipe>>> getRecipes();


}
