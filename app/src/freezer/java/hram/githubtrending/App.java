package hram.githubtrending;


import fr.xebia.android.freezer.Freezer;

/**
 * @author Evgeny Khramov
 */
public class App extends BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();

        Freezer.onCreate(this);
    }
}