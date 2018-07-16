package udacity.viktor.bakingappfinal;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import udacity.viktor.bakingappfinal.Data.Networking.Models.Ingredient;

public class DataConverterIngredients {
    @TypeConverter
    public String fromRoom(List<Ingredient> roomValue) {
        if (roomValue == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Ingredient>>() {
        }.getType();
        return gson.toJson(roomValue, type);
    }

    @TypeConverter
    public List<Ingredient> toIngredients(String ingredient) {
        if (ingredient == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Ingredient>>() {
        }.getType();
        return gson.fromJson(ingredient, type);
    }
}
