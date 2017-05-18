package hram.githubtrending.trends;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import hram.githubtrending.data.DataManager;
import hram.githubtrending.data.model.SearchParams;
import hram.githubtrending.viewmodel.LanguageViewModel;
import hram.githubtrending.viewmodel.RepositoriesViewModel;
import hram.githubtrending.viewmodel.RepositoryViewModel;
import hugo.weaving.DebugLog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Evgeny Khramov
 */
@InjectViewState
public class TrendsPresenter extends MvpPresenter<TrendsView> {

    private RepositoriesViewModel mRepositoriesViewModel;

    private ItemTouchHelper.SimpleCallback mSimpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            int position = viewHolder.getAdapterPosition();
            RepositoryViewModel viewModel = mRepositoriesViewModel.items.get(position);
            DataManager.getInstance().setHided(viewModel.getId(), true)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(res -> TrendsPresenter.this.handleHideResult(viewModel, position, true, res), TrendsPresenter.this::handleHideError);
        }
    };

    private ItemTouchHelper mTouchHelper;

    @DebugLog
    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        mRepositoriesViewModel = new RepositoriesViewModel();
        getViewState().setViewModel(mRepositoriesViewModel);
        DataManager.getInstance().setParams(new SearchParams("java", "daily"));
        loadRepositories(true);
    }

    @Override
    public void attachView(TrendsView view) {
        super.attachView(view);
        mTouchHelper = new ItemTouchHelper(mSimpleItemTouchCallback);
        getViewState().attachToRecyclerView(mTouchHelper);
    }

    @Override
    public void detachView(TrendsView view) {
        super.detachView(view);
        mTouchHelper.attachToRecyclerView(null);
    }

    private void loadRepositories(boolean isRefreshing) {
        getViewState().setRefreshing(isRefreshing);
        DataManager.getInstance().getRepositories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleRepositories, this::handleError);
    }

    void refresh() {
        getViewState().setRefreshing(true);
        DataManager.getInstance().refreshRepositories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleRepositories, this::handleError);
    }

    private void handleRepositories(@NonNull List<RepositoryViewModel> list) {
        mRepositoriesViewModel.setItems(list);
        getViewState().setRefreshing(false);

        DataManager.getInstance().getLanguages()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleLanguages, this::handleError);
    }

    private void handleLanguages(@NonNull List<LanguageViewModel> list) {

    }

    @DebugLog
    private void handleError(@NonNull Throwable throwable) {
        mRepositoriesViewModel.error.set(throwable.getMessage());
        mRepositoriesViewModel.hasError.set(true);
    }

    private void handleHideResult(@NonNull RepositoryViewModel viewModel, int position, boolean hided, Boolean result) {
        if (!result) {
            return;
        }

        if (hided) {
            mRepositoriesViewModel.removeItem(position);
            getViewState().sowRemoveUndo(viewModel, position, "Репозиторий больше не будет отображаться в списке");
        } else {
            mRepositoriesViewModel.items.add(position, viewModel);
        }
    }

    @DebugLog
    private void handleHideError(@NonNull Throwable throwable) {
        mRepositoriesViewModel.error.set(throwable.getMessage());
        mRepositoriesViewModel.hasError.set(true);
    }

    public void onUndoRemove(@NonNull RepositoryViewModel viewModel, int position) {
        DataManager.getInstance().setHided(viewModel.getId(), false)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> TrendsPresenter.this.handleHideResult(viewModel, position, false, res), this::handleHideError);

    }
}
