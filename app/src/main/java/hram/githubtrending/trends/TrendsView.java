package hram.githubtrending.trends;

import android.support.annotation.NonNull;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import hram.githubtrending.viewmodel.RepositoriesViewModel;
import hram.githubtrending.viewmodel.RepositoryViewModel;

/**
 * @author Evgeny Khramov
 */
public interface TrendsView extends MvpView {

    void setViewModel(RepositoriesViewModel viewModel);

    void setRefreshing(boolean refreshing);

    void attachToRecyclerView(@NonNull ItemTouchHelper itemTouchHelper);

    @StateStrategyType(SkipStrategy.class)
    void sowRemoveUndo(@NonNull RepositoryViewModel viewModel, int position, @NonNull String text);
}
