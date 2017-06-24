package hram.githubtrending;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import hram.githubtrending.di.AppComponent;
import hram.githubtrending.di.DaggerAppComponent;
import hram.githubtrending.di.TestNetworkModule;
import hram.githubtrending.selectlanguage.SelectLanguageActivity;
import hram.githubtrending.splash.SplashActivity;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.fail;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.Is.is;

/**
 * @author Evgeny Khramov
 */
@RunWith(AndroidJUnit4.class)
public class SplashTest extends BaseMockTest {

    @Rule
    public ActivityTestRule<SplashActivity> mActivityRule = new ActivityTestRule<>(SplashActivity.class, true, false);

    @Test
    public void testLoadDataSuccess() throws InterruptedException, IOException {

        mServer.setDispatcher(new SuccessDispatcher(this));

        mActivityRule.launchActivity(new Intent());

        final SplashScreenIdlingResource idlingResource = new SplashScreenIdlingResource(this);
        try {

            Espresso.registerIdlingResources(idlingResource);

            try {
                onView(withId(R.id.animation_view))
                        .check(matches(isDisplayed()));
                fail();
            } catch (NoMatchingViewException e) {
                // do nothing
            }

        } finally {
            Espresso.unregisterIdlingResources(idlingResource);
        }

        assertThat(getActivity(), instanceOf(SelectLanguageActivity.class));

        assertThat(mServer.getRequestCount(), is(2));
        assertThat(mServer.takeRequest().getPath(), is("/trending/"));
        assertThat(mServer.takeRequest().getPath(), is("/trending/"));
    }
}
