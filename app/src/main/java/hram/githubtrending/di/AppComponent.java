package hram.githubtrending.di;

import javax.inject.Singleton;

import dagger.Component;
import hram.githubtrending.data.DataManager;
import hram.githubtrending.data.network.RetroJsoupNetworkHelper;

/**
 * @author Evgeny Khramov
 */
@Component(modules = {NetworkModule.class, DataModule.class})
@Singleton
public interface AppComponent {

    // TODO видимо надо 2 компанента
    void inject(RetroJsoupNetworkHelper networkHelper);

    void inject(DataManager dataManager);
}
