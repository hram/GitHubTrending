package hram.githubtrending.splash;

import hram.githubtrending.selectlanguage.SelectLanguageActivityTv;
import hram.githubtrending.trends.TrendsActivity;

public class SplashActivityTv extends SplashActivity implements SplashView {

    @Override
    public void openTrendsScreen() {
        TrendsActivity.start(this);
        finish();
    }

    @Override
    public void openFilterScreen() {
        SelectLanguageActivityTv.start(this);
        finish();
    }
}
