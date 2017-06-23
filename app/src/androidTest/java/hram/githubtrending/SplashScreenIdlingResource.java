package hram.githubtrending;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.test.espresso.IdlingResource;

import hram.githubtrending.splash.SplashActivity;

/**
 * @author Evgeny Khramov
 */
public class SplashScreenIdlingResource implements IdlingResource {

    private final BaseTest mBaseTest;

    private ResourceCallback mResourceCallback;

    public SplashScreenIdlingResource(@NonNull BaseTest baseTest) {
        mBaseTest = baseTest;
    }

    @Override
    public String getName() {
        return SplashScreenIdlingResource.class.getName();
    }

    @Override
    public boolean isIdleNow() {
        final Activity activity = mBaseTest.getActivity();
        if (activity == null) {
            return false;
        }

        boolean isIdle = !(activity instanceof SplashActivity);
        if (isIdle && mResourceCallback != null) {
            mResourceCallback.onTransitionToIdle();
        }

        return isIdle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        mResourceCallback = callback;
    }
}
