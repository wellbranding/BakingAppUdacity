package udacity.viktor.bakingappfinal.Dagger2;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import udacity.viktor.bakingappfinal.UI.Activities.DetailsOfRecipeActivity;
import udacity.viktor.bakingappfinal.UI.Activities.MainActivity;
import udacity.viktor.bakingappfinal.UI.Fragments.DetailStepFragment;

@Module
public abstract class DetailsOfRecipeActivityModule {
    @ContributesAndroidInjector(modules = DetailsStepFragmentModule.class)
    abstract DetailsOfRecipeActivity contributeDetailsOfRecipeActivity();

}
