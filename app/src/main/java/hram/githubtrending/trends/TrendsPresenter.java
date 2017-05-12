package hram.githubtrending.trends;

import android.support.annotation.NonNull;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import hram.githubtrending.data.DataManager;
import hram.githubtrending.model.Language;
import hram.githubtrending.model.TimeSpan;
import hram.githubtrending.viewmodel.LanguageViewModel;
import hram.githubtrending.viewmodel.RepositoryViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Evgeny Khramov
 */
@InjectViewState
public class TrendsPresenter extends MvpPresenter<TrendsView> {

    public void getRepositories() {
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadRepositories(true);
    }

    public void loadRepositories(boolean isRefreshing) {
        getViewState().setRefreshing(isRefreshing);
        DataManager.getInstance().getRepositories(Language.JAVA, TimeSpan.DAILY)
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

    //@DebugLog
    private void handleLanguages(@NonNull List<LanguageViewModel> list) {

    }

    private void handleError(@NonNull Throwable throwable) {
        Log.e("TrendsActivity", throwable.getMessage());
    }
}
