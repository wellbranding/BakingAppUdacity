package udacity.viktor.bakingappfinal;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import udacity.viktor.bakingappfinal.Data.Networking.Models.Step;

public class DataConverterStep {
    @TypeConverter
    public String fromCountryLangList(List<Step> countryLang) {
        if (countryLang == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Step>>() {
        }.getType();
        String json = gson.toJson(countryLang, type);
        return json;
    }

    @TypeConverter
    public List<Step> toCountryLangList(String countryLangString) {
        if (countryLangString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Step>>() {
        }.getType();
        List<Step> countryLangList = gson.fromJson(countryLangString, type);
        return countryLangList;
    }
}
