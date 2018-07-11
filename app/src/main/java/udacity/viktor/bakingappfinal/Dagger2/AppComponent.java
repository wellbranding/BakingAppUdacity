package udacity.viktor.bakingappfinal.Dagger2;


import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import udacity.viktor.bakingappfinal.App;
import udacity.viktor.bakingappfinal.UI.Fragments.DetailStepFragment;
import udacity.viktor.bakingappfinal.Widget.BakingAppWidgetService;

@Singleton
@Component(modules = {MainActivityModule.class,
        AndroidInjectionModule.class, DetailsOfRecipeActivityModule.class,
        RemoteViewsService.class, BakingAppWidgetModule.class,
        AppModule.class})


public interface AppComponent {
    void inject(App app);
    @Component.Builder
    interface  Builder
    {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();

    }

}

