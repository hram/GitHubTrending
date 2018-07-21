package hram.githubtrending;

import android.support.annotation.NonNull;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;

import hram.githubtrending.di.AppComponent;
import hram.githubtrending.di.DaggerAppComponent;
import hram.githubtrending.di.NetworkModule;
import hram.githubtrending.di.TestDataModule;

/**
 * @author Evgeny Khramov
 */

public class TestApplication extends BaseApp {

    @NonNull
    @Override
    protected AppComponent buildComponent() {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setApplicationId("1:526981172258:android:9ec5caf344692920")
                .setApiKey("AIzaSyC1rSfCI3y097qQNhdX0pv2obYS6K0Frrk")
                .setDatabaseUrl("https://trending-4070b.firebaseio.com")
                .setGcmSenderId("526981172258")
                .setStorageBucket("trending-4070b.appspot.com")
                .setProjectId("trending-4070b")
                .build();

        final FirebaseApp app = FirebaseApp.initializeApp(this, options, FirebaseApp.DEFAULT_APP_NAME);

        return DaggerAppComponent.builder()
                .networkModule(new NetworkModule())
                .dataModule(new TestDataModule(FirebaseDatabase.getInstance(app)))
                .build();
    }
}
