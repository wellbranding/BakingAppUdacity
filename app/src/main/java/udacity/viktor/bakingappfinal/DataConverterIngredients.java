package udacity.viktor.bakingappfinal;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import udacity.viktor.bakingappfinal.Data.Networking.Models.Ingredient;
import udacity.viktor.bakingappfinal.Data.Networking.Models.Step;

public class DataConverterIngredients {
    @TypeConverter
    public String fromCountryLangList(List<Ingredient> countryLang) {
        if (countryLang == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Ingredient>>() {
        }.getType();
        String json = gson.toJson(countryLang, type);
        return json;
    }

    @TypeConverter
    public List<Ingredient> toCountryLangList(String countryLangString) {
        if (countryLangString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Ingredient>>() {
        }.getType();
        List<Ingredient> countryLangList = gson.fromJson(countryLangString, type);
        return countryLangList;
    }
}
