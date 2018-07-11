package udacity.viktor.bakingappfinal.Dagger2;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import udacity.viktor.bakingappfinal.Widget.BakingAppWidget;
import udacity.viktor.bakingappfinal.Widget.BakingAppWidgetService;

@Module
public abstract class BakingAppWidgetModule {
    @ContributesAndroidInjector()
    abstract BakingAppWidget contributeBakingAppWidget();
}
