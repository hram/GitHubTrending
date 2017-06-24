package hram.githubtrending;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpView;

/**
 * @author Evgeny Khramov
 */

public interface BaseView extends MvpView {

    void showProgress();

    void showEmpty();

    void showError(@NonNull Throwable throwable);

    void showContent();
}
