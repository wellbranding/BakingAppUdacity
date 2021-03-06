package udacity.viktor.bakingappfinal.Dagger2;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import udacity.viktor.bakingappfinal.App;
import udacity.viktor.bakingappfinal.Data.Networking.API.APIService;
import udacity.viktor.bakingappfinal.Data.Networking.API.LiveDataCallAdapterFactory;
import udacity.viktor.bakingappfinal.db.MainDatabase;
import udacity.viktor.bakingappfinal.db.RecipesMainDao;

@Module(includes = ViewModelsModule.class)
public class AppModule {
    @Provides
    @Singleton
    APIService apiService_create() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .client(client)
                .build();
        return retrofit.create(APIService.class);
    }

    @Singleton
    @Provides
    MainDatabase mainDatabase_create(Application application) {
        return Room.databaseBuilder(application, MainDatabase.class, "baking_app1").build();
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Application app) {
        return PreferenceManager.getDefaultSharedPreferences(app);
    }
}
