package udacity.viktor.bakingappfinal.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import udacity.viktor.bakingappfinal.Data.Networking.Models.Ingredient;
import udacity.viktor.bakingappfinal.Data.Networking.Models.Recipe;
import udacity.viktor.bakingappfinal.Data.Networking.Models.Step;


@Database(entities = {Step.class, Recipe.class, Ingredient.class}, version = MainDatabase.VERSION)
public abstract class MainDatabase extends RoomDatabase {

    public abstract RecipesMainDao recipeDao();

    static final int VERSION = 6;

//    private static MainDatabase INSTANCE;
//
//
//    public static MainDatabase getDatabase(final Context context) {
//        if (INSTANCE == null) {
//            synchronized (MainDatabase.class) {
//                if (INSTANCE == null) {
//                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
//                           MainDatabase.class, "word_database1")
//                            .build();
//
//                }
//            }
//        }
//        return INSTANCE;
//    }
}
