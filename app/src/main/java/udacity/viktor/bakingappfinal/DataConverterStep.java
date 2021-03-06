package udacity.viktor.bakingappfinal;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import udacity.viktor.bakingappfinal.Data.Networking.Models.Step;

public class DataConverterStep {
    @TypeConverter
    public String fromRoom(List<Step> roomStep) {
        if (roomStep == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Step>>() {
        }.getType();
        return gson.toJson(roomStep, type);
    }

    @TypeConverter
    public List<Step> toSteps(String step) {
        if (step == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Step>>() {
        }.getType();
        return gson.fromJson(step, type);
    }
}
