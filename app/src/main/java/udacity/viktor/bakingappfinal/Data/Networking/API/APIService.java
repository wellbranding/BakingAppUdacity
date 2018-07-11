package udacity.viktor.bakingappfinal.Data.Networking.API;


import android.arch.lifecycle.LiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import udacity.viktor.bakingappfinal.Data.Networking.Models.Recipe;

/**
 * Created by Belal on 14/04/17.
 */

public interface APIService {

     String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/";
    @GET("topher/2017/May/59121517_baking/baking.json")
   LiveData<ApiResponse<List<Recipe>>> getRecipes();



}
