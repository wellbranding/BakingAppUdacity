package udacity.viktor.bakingappfinal.Dagger2;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import udacity.viktor.bakingappfinal.UI.Activities.DetailsOfRecipeActivity;
import udacity.viktor.bakingappfinal.Widget.BakingAppWidgetService;

@Module
public abstract class RemoteViewsService {
    @ContributesAndroidInjector()
    abstract BakingAppWidgetService contributeRemoteViewsService();
}
