package hram.githubtrending.trends;

import android.support.annotation.NonNull;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import hram.githubtrending.viewmodel.RepositoryViewModel;

/**
 * @author Evgeny Khramov
 */
public interface TrendsView extends MvpView {

    void setRepositories(@NonNull List<RepositoryViewModel> list);

    void setRefreshing(boolean refreshing);

    void attachToRecyclerView(@NonNull ItemTouchHelper itemTouchHelper);

    void removeItem(int position);
}
