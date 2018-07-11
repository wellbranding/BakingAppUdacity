package udacity.viktor.bakingappfinal.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import java.util.List;

import udacity.viktor.bakingappfinal.Data.Networking.Models.Ingredient;
import udacity.viktor.bakingappfinal.Data.Networking.Models.Recipe;
import udacity.viktor.bakingappfinal.Data.Networking.Models.RecipyForRoom;
import udacity.viktor.bakingappfinal.Data.Networking.Models.Step;

@Dao
public interface RecipesMainDao {
    @Query("SELECT * FROM Recipe")
    public abstract LiveData<List<Recipe>> getRecipes();

    @Transaction
    @Query("SELECT * FROM Recipe WHERE id = :id")
    public abstract Recipe getRecipe(int id);

    @Insert
    public abstract  void insertAllRecipes(List<Recipe> recipeList);
}



