package udacity.viktor.bakingappfinal.Data.Networking.Models;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

public class RecipyForRoom {

    @Embedded
    public Recipe recipe;

    @Relation(parentColumn = "id",
            entityColumn = "recipeId")
    public List<Ingredient> ingredients;

    @Relation(parentColumn = "id",
            entityColumn = "recipeId")
    public List<Step> steps;

}
