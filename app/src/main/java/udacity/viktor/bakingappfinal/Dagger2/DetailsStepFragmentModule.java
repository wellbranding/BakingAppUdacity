package udacity.viktor.bakingappfinal.Dagger2;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import udacity.viktor.bakingappfinal.UI.Activities.DetailsOfRecipeActivity;
import udacity.viktor.bakingappfinal.UI.Fragments.DetailStepFragment;
import udacity.viktor.bakingappfinal.UI.Fragments.DetailsListStepFragment;

@Module
public abstract class DetailsStepFragmentModule {
    @ContributesAndroidInjector()
    abstract DetailStepFragment contributeDetailStepFragmenty();

    @ContributesAndroidInjector()
    abstract DetailsListStepFragment contributeDetailsListStepFragmenty();
}
