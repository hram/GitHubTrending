package hram.githubtrending.di;

import android.support.annotation.NonNull;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/**
 * @author Evgeny Khramov
 */
@Module
public class NetworkModule {

    @Provides
    @Singleton
    HttpUrl provideBaseUrl() {
        return HttpUrl.parse("https://github.com");
    }

    @NonNull
    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
    }
}
