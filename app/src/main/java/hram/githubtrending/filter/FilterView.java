package hram.githubtrending.filter;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpView;

import hram.githubtrending.viewmodel.FilterViewModel;

/**
 * @author Evgeny Khramov
 */
public interface FilterView extends MvpView {

    void setViewModel(@NonNull FilterViewModel viewModel);
}
