package hram.githubtrending.trends;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import hram.githubtrending.data.DataManager;
import hram.githubtrending.model.Language;
import hram.githubtrending.model.TimeSpan;
import hram.githubtrending.ui.BasePresenter;
import hram.githubtrending.viewmodel.RepositoryViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Evgeny Khramov
 */

public class TrendsPresenter extends BasePresenter<TrendsView> {

    public void getRepositories() {
    }

    public void refresh() {
        DataManager.getInstance().getRepositories(Language.JAVA, TimeSpan.DAILY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResult, this::handleError);
    }

    private void handleResult(@NonNull List<RepositoryViewModel> list) {
        if (!isViewAttached()) {
            return;
        }

        getMvpView().setRepositories(list);
        getMvpView().setRefreshing(false);
    }

    private void handleError(@NonNull Throwable throwable) {
        Log.e("TrendsActivity", throwable.getMessage());
    }
}
