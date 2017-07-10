package hram.githubtrending;

import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.v7.app.AppCompatActivity;

import com.squareup.spoon.Spoon;

import org.junit.Before;
import org.junit.Rule;

import java.util.Collection;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.runner.lifecycle.Stage.RESUMED;

/**
 * @author Evgeny Khramov
 */

public class BaseTest {

    private AppCompatActivity mCurrentActivity;

    @Before
    public void setUp() {
        //TestUtils.clearAppData();
    }

    public AppCompatActivity getActivity() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Collection resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(RESUMED);
            if (resumedActivities.iterator().hasNext()) {
                mCurrentActivity = (AppCompatActivity) resumedActivities.iterator().next();
            }
        } else {
            //Don't transform to lambda
            getInstrumentation().runOnMainSync(new Runnable() {
                @Override
                public void run() {
                    Collection resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(RESUMED);
                    if (resumedActivities.iterator().hasNext()) {
                        mCurrentActivity = (AppCompatActivity) resumedActivities.iterator().next();
                    }
                }
            });
        }

        return mCurrentActivity;
    }

    protected void screenShot(@NonNull String text) {
        try {
            Spoon.screenshot(getActivity(), text);
        } catch (RuntimeException e) {
            // do nothing
        }
    }
}
