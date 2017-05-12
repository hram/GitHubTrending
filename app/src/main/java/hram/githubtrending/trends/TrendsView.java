package hram.githubtrending.trends;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import hram.githubtrending.viewmodel.RepositoryViewModel;

/**
 * @author Evgeny Khramov
 */
public interface TrendsView extends MvpView {

    void setRepositories(@NonNull List<RepositoryViewModel> list);

    void setRefreshing(boolean refreshing);
}
