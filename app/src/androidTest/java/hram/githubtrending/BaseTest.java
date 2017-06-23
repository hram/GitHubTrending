package hram.githubtrending;

import android.os.Looper;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.v7.app.AppCompatActivity;

import org.junit.Before;

import java.util.Collection;

import hram.githubtrending.util.TestUtils;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.runner.lifecycle.Stage.RESUMED;

/**
 * @author Evgeny Khramov
 */

public class BaseTest {

    private AppCompatActivity mCurrentActivity;

    @Before
    public void setUp() {
        TestUtils.clearAppData();
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
}
