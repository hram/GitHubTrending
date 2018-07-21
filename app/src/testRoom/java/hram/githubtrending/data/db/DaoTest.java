package hram.githubtrending.data.db;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import hram.githubtrending.BaseApp;
import hram.githubtrending.DaoTestApplication;
import hram.githubtrending.di.TestDataModule;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE, sdk = 21, application = DaoTestApplication.class)
public class DaoTest {


    private TestDataModule mTestDataModule;

    /**
     * It's need for correct save unique repository by primary key
     */
    private int mCounterId;

    @Before
    @CallSuper
    public void setup() {
        mTestDataModule = new TestDataModule(BaseApp.getInstance());
        mCounterId = 0;
    }

    public TestDataModule getTestDataModule() {
        return mTestDataModule;
    }


    @After
    @CallSuper
    public void release() {
        mTestDataModule.close();
    }

    @NonNull
    protected int nextId() {
        return mCounterId++;
    }
}
