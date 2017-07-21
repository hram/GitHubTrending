package hram.githubtrending.trends;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import hram.githubtrending.BaseView;
import hram.githubtrending.viewmodel.RepositoriesViewModel;
import hram.githubtrending.viewmodel.RepositoryViewModel;

/**
 * @author Evgeny Khramov
 */
public interface TrendsView extends BaseView {

    void setTitle(@NonNull String title);

    void setViewModel(@NonNull RepositoriesViewModel viewModel);

    void setRefreshing(boolean refreshing);

    void attachToRecyclerView(@NonNull ItemTouchHelper itemTouchHelper);

    @StateStrategyType(SkipStrategy.class)
    void sowRemoveUndo(@NonNull RepositoryViewModel viewModel, int position, @NonNull String text);

    @StateStrategyType(SkipStrategy.class)
    void openScreen(@NonNull Intent intent);
}
