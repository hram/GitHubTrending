package hram.githubtrending;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;

import org.junit.Rule;

import java.io.IOException;
import java.io.InputStream;

import hram.githubtrending.di.AppComponent;
import hram.githubtrending.di.DaggerAppComponent;
import hram.githubtrending.di.TestNetworkModule;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okio.Buffer;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by hram on 24.06.2017.
 */

public class BaseMockTest extends BaseTest {

    @Rule
    public final MockWebServer mServer = new MockWebServer();

    @Override
    public void setUp() {
        super.setUp();
        App.getInstance().setAppComponent(buildComponent());
    }

    @NonNull
    MockResponse getResponse(@NonNull String fileName) throws IOException {
        InputStream in = InstrumentationRegistry.getContext().getResources().getAssets().open(fileName);
        assertThat(in, is(notNullValue()));

        return new MockResponse().setBody(new Buffer().readFrom(in));
    }

    @NonNull
    private AppComponent buildComponent() {
        return DaggerAppComponent.builder()
                .networkModule(new TestNetworkModule(mServer))
                .build();
    }
}
