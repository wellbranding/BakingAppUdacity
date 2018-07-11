package udacity.viktor.bakingappfinal.Dagger2;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import udacity.viktor.bakingappfinal.UI.Activities.MainActivity;

@Module
public abstract class MainActivityModule {
    @ContributesAndroidInjector()
    abstract MainActivity contributeMainActivity();
}
