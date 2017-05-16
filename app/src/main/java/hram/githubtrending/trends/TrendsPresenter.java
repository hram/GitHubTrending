package hram.githubtrending.trends;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import hram.githubtrending.data.DataManager;
import hram.githubtrending.viewmodel.LanguageViewModel;
import hram.githubtrending.viewmodel.RepositoryViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Evgeny Khramov
 */
@InjectViewState
public class TrendsPresenter extends MvpPresenter<TrendsView> {

    private ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            getViewState().removeItem(viewHolder.getAdapterPosition());
        }
    };

    private ItemTouchHelper mTouchHelper;

    public void getRepositories() {
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadRepositories(true);
    }

    @Override
    public void attachView(TrendsView view) {
        super.attachView(view);
        mTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        getViewState().attachToRecyclerView(mTouchHelper);
    }

    @Override
    public void detachView(TrendsView view) {
        super.detachView(view);
        mTouchHelper.attachToRecyclerView(null);
    }

    public void loadRepositories(boolean isRefreshing) {
        getViewState().setRefreshing(isRefreshing);
        // TODO
        DataManager.getInstance().getRepositories("java", "daily")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleRepositories, this::handleError);
    }

    public void refresh() {
        loadRepositories(true);
    }

    private void handleRepositories(@NonNull List<RepositoryViewModel> list) {
        getViewState().setRepositories(list);
        getViewState().setRefreshing(false);

        DataManager.getInstance().getLanguages()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleLanguages, this::handleError);
    }

    private void handleLanguages(@NonNull List<LanguageViewModel> list) {

    }

    private void handleError(@NonNull Throwable throwable) {
        Log.e("TrendsActivity", throwable.getMessage());
    }
}
