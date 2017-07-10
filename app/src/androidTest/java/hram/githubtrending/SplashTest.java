package hram.githubtrending;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import hram.githubtrending.selectlanguage.SelectLanguageActivity;
import hram.githubtrending.splash.SplashActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
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

        screenShot("splash_with_loading");

        waitLoading();

        screenShot("select_language_after_splash");
        assertThat(getActivity(), instanceOf(SelectLanguageActivity.class));

        assertThat(mServer.getRequestCount(), is(2));
        assertThat(mServer.takeRequest().getPath(), is("//trending/"));
        assertThat(mServer.takeRequest().getPath(), is("//trending/"));
    }

    @Test
    public void testError404() throws InterruptedException, IOException {
        mServer.enqueue(getResponse("404.html").setResponseCode(404).throttleBody(64 * 1024, 125, TimeUnit.MILLISECONDS)); // 500 Kbps
        mServer.enqueue(getResponse("404.html").setResponseCode(404).throttleBody(64 * 1024, 125, TimeUnit.MILLISECONDS)); // 500 Kbps
        mActivityRule.launchActivity(new Intent());
        waitLoading();
        testEmptyScreen();
    }

    @Test
    public void testSocketTimeoutException() throws InterruptedException, IOException {
        mActivityRule.launchActivity(new Intent());
        waitLoading();
        testEmptyScreen();
    }

    private void waitLoading() {
        final SplashScreenIdlingResource idlingResource = new SplashScreenIdlingResource(this);
        try {

            Espresso.registerIdlingResources(idlingResource);

            try {
                onView(withText("bla_bla"))
                        .check(matches(isDisplayed()));
                fail();
            } catch (NoMatchingViewException e) {
                // do nothing
            }

        } finally {
            Espresso.unregisterIdlingResources(idlingResource);
        }
    }

    private void testEmptyScreen() {
        screenShot("empty_screen");
        onView(withId(R.id.frame_layout_empty))
                .check(matches(isDisplayed()));
        onView(withId(R.id.text_title))
                .check(matches(withText("Что то пошло не так (((")));
        onView(withId(R.id.text_content))
                .check(matches(withText("Не удалось получить список языков. Мы уже в курсе. Помощь уже в пути.")));
    }
}
