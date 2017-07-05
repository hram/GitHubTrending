package hram.githubtrending;


import io.realm.Realm;

/**
 * @author Evgeny Khramov
 */
public class App extends BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
    }
}