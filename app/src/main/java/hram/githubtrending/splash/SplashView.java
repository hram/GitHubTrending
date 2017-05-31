package hram.githubtrending.splash;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpView;

import hram.githubtrending.viewmodel.SplashViewModel;

/**
 * @author Evgeny Khramov
 */

public interface SplashView extends MvpView {

    void setViewModel(@NonNull SplashViewModel viewModel);

    void openTrendsScreen();

    void openFilterScreen();
}
