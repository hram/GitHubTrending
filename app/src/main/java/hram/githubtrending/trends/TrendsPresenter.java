package hram.githubtrending.trends;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import hram.githubtrending.BuildConfig;
import hram.githubtrending.data.DataManager;
import hram.githubtrending.viewmodel.RepositoriesViewModel;
import hram.githubtrending.viewmodel.RepositoryViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Evgeny Khramov
 */
@InjectViewState
public class TrendsPresenter extends MvpPresenter<TrendsView> implements RepositoryViewModel.OnItemClickListener {

    public static final int CHANGE_FILTER = 405;

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

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        mRepositoriesViewModel = new RepositoriesViewModel(this);
        getViewState().setViewModel(mRepositoriesViewModel);
        getViewState().setTitle(String.format("%s %s trends", DataManager.getInstance().getParams().getLanguageName(), DataManager.getInstance().getParams().getTimeSpan()));
        loadRepositories();
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

    private void loadRepositories() {
        getViewState().setRefreshing(true);
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
        getViewState().setRefreshing(false);
        if (list.isEmpty()) {
            getViewState().showEmpty();
        } else {
            mRepositoriesViewModel.setItems(list);
            getViewState().showContent();
        }
    }

    private void handleError(@NonNull Throwable throwable) {
        getViewState().setRefreshing(false);
    }

    private void handleHideResult(@NonNull RepositoryViewModel viewModel, int position, boolean hided, Boolean result) {
        if (!result) {
            return;
        }

        if (hided) {
            mRepositoriesViewModel.removeItem(position);
            getViewState().sowRemoveUndo(viewModel, position, "Репозиторий больше не будет отображаться в списке");
            if (mRepositoriesViewModel.items.isEmpty()) {
                getViewState().showEmpty();
            }
        } else {
            if (mRepositoriesViewModel.items.isEmpty()) {
                getViewState().showContent();
            }
            mRepositoriesViewModel.items.add(position, viewModel);
        }
    }

    private void handleHideError(@NonNull Throwable throwable) {
        getViewState().setRefreshing(false);
        getViewState().showError(throwable);
    }

    public void onUndoRemove(@NonNull RepositoryViewModel viewModel, int position) {
        DataManager.getInstance().setHided(viewModel.getId(), false)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> TrendsPresenter.this.handleHideResult(viewModel, position, false, res), this::handleHideError);

    }

    @Override
    public void onItemClick(@NonNull RepositoryViewModel item) {
        getViewState().openScreen(new Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.URL_BASE + item.getId())));
    }

    void onActivityResult(int requestCode, int resultCode) {
        if (requestCode == CHANGE_FILTER && resultCode == Activity.RESULT_OK) {
            refresh();
            getViewState().setTitle(String.format("%s %s trends", DataManager.getInstance().getParams().getLanguageName(), DataManager.getInstance().getParams().getTimeSpan()));
        }
    }
}
