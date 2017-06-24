package hram.githubtrending.di;

import android.support.annotation.NonNull;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockWebServer;

/**
 * @author Evgeny Khramov
 */
public class TestNetworkModule extends NetworkModule {

    private final MockWebServer mServer;

    public TestNetworkModule(@NonNull MockWebServer server) {
        mServer = server;
    }

    @Override
    HttpUrl provideBaseUrl() {
        return mServer.url("/");
    }
}
