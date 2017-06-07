package hram.githubtrending.splash;

import android.support.annotation.NonNull;

import hram.githubtrending.BaseView;
import hram.githubtrending.viewmodel.SplashViewModel;

/**
 * @author Evgeny Khramov
 */

public interface SplashView extends BaseView {

    void setViewModel(@NonNull SplashViewModel viewModel);

    void openTrendsScreen();

    void openFilterScreen();
}
