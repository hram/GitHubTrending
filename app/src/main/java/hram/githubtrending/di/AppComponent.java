package hram.githubtrending.di;

import javax.inject.Singleton;

import dagger.Component;
import hram.githubtrending.data.network.NetworkHelper;

/**
 * @author Evgeny Khramov
 */
@Component(modules = {NetworkModule.class})
@Singleton
public interface AppComponent {

    void inject(NetworkHelper networkHelper);
}
