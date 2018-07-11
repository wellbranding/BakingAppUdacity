package udacity.viktor.bakingappfinal.Dagger2;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import udacity.viktor.bakingappfinal.ViewModels.MainViewModel;

@Module
public abstract class ViewModelsModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelsFactory(ViewModelFactory viewModelFactory);


    @Binds
    @IntoMap
    @ViewModelsKey(MainViewModel.class)
    abstract ViewModel bindMainViewModel(MainViewModel mainViewModel);
}
