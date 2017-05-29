package hram.githubtrending.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.florent37.retrojsoup.RetroJsoup;

import java.util.ArrayList;
import java.util.List;

import hram.githubtrending.data.db.DatabaseHelper;
import hram.githubtrending.data.model.Language;
import hram.githubtrending.data.model.Repository;
import hram.githubtrending.data.model.SearchParams;
import hram.githubtrending.data.model.TimeSpan;
import hram.githubtrending.data.network.NetworkHelper;
import hram.githubtrending.data.network.Trending;
import hram.githubtrending.viewmodel.LanguageViewModel;
import hram.githubtrending.viewmodel.RepositoryViewModel;
import hram.githubtrending.viewmodel.TimeSpanViewModel;
import hugo.weaving.DebugLog;
import io.reactivex.Observable;

/**
 * @author Evgeny Khramov
 */

public class DataManager {

    private static DataManager sDataManager;

    private final DatabaseHelper mDatabaseHelper;

    private final NetworkHelper mNetworkHelper;

    private SearchParams mParams;

    @NonNull
    public static DataManager getInstance() {
        if (sDataManager == null) {
            sDataManager = new DataManager(new DatabaseHelper(), new NetworkHelper(), new SearchParams("java", "daily"));
        }
        return sDataManager;
    }

    private DataManager(@NonNull DatabaseHelper databaseHelper, @NonNull NetworkHelper networkHelper, SearchParams params) {
        mDatabaseHelper = databaseHelper;
        mNetworkHelper = networkHelper;
        mParams = params;
    }

    public SearchParams getParams() {
        return mParams;
    }

    public void setParams(SearchParams params) {
        mParams = params;
    }

    // TODO add loading from DB and only if DB empty load from network
    @DebugLog
    @NonNull
    public Observable<List<RepositoryViewModel>> getRepositories() {
        return mDatabaseHelper.getRepositoriesObservable(mParams.getLanguage(), mParams.getTimeSpan())
                .flatMap(this::ifEmptyThenFromNetwork)
                .flatMap(this::mapToViewModel);
    }

    @DebugLog
    @NonNull
    public Observable<List<RepositoryViewModel>> refreshRepositories() {
        return mNetworkHelper.getRepositories(mParams.getLanguage(), mParams.getTimeSpan())
                .flatMap(repositories -> mDatabaseHelper.saveRepositories(repositories, mParams.getLanguage(), mParams.getTimeSpan()))
                .flatMap(this::mapToViewModel);
    }

    @DebugLog
    @NonNull
    public Observable<RepositoryViewModel> getRepository(@NonNull String id) {
        return mDatabaseHelper.getRepositoryObservable(id)
                .flatMap(this::mapToViewModel);
    }

    // TODO add loading from DB and only if DB empty load from network
    @NonNull
    public Observable<List<LanguageViewModel>> getLanguages() {
        final Trending trending = new RetroJsoup.Builder()
                .url("https://github.com/trending/")
                //.client(okHttpClient)
                .build()
                .create(Trending.class);

        return trending.getLanguages()
                .flatMap(this::mapToViewModel)
                .toList().toObservable();
    }

    // TODO add loading from DB and only if DB empty load from network
    @NonNull
    public Observable<List<TimeSpanViewModel>> getTimeSpans() {
        final Trending trending = new RetroJsoup.Builder()
                .url("https://github.com/trending/")
                //.client(okHttpClient)
                .build()
                .create(Trending.class);

        return trending.getTimeSpans()
                .flatMap(this::mapToViewModel)
                .toList().toObservable();
    }

    @NonNull
    private Observable<List<RepositoryViewModel>> mapToViewModel(List<Repository> list) {
        final List<RepositoryViewModel> items = new ArrayList<>(list.size());
        for (Repository model : list) {
            items.add(RepositoryViewModel.create(model));
        }
        return Observable.just(items);
    }

    private Observable<RepositoryViewModel> mapToViewModel(Repository item) {
        return Observable.just(RepositoryViewModel.create(item));
    }

//    @DebugLog
//    @NonNull
//    private Observable<RepositoryViewModel> mapToViewModel(@NonNull Repository item) {
//        return Observable.just(RepositoryViewModel.create(item));
//    }

    @DebugLog
    @NonNull
    public Observable<Repository> saveToDataBase(@NonNull Repository item) {
        mDatabaseHelper.saveRepository(item);
        return Observable.just(item);
    }

    @DebugLog
    @NonNull
    public Observable<Boolean> setHided(@NonNull String id, boolean hided) {
        return mDatabaseHelper.getRepositoryObservable(id)
                .flatMap(repository -> setHidedAndSave(repository, hided));
    }

    @NonNull
    private Observable<LanguageViewModel> mapToViewModel(@NonNull Language item) {
        return Observable.just(LanguageViewModel.create(item, true));
    }

    @NonNull
    private Observable<TimeSpanViewModel> mapToViewModel(@NonNull TimeSpan item) {
        return Observable.just(TimeSpanViewModel.create(item));
    }

    @DebugLog
    private Observable<List<Repository>> ifEmptyThenFromNetwork(@NonNull List<Repository> list) {
        if (list.isEmpty()) {
            return mNetworkHelper.getRepositories(mParams.getLanguage(), mParams.getTimeSpan())
                    .flatMap(repositories -> mDatabaseHelper.saveRepositories(repositories, mParams.getLanguage(), mParams.getTimeSpan()));
        } else {
            return Observable.just(list);
        }
    }

    @DebugLog
    @NonNull
    private Observable<Boolean> setHidedAndSave(@Nullable Repository repository, boolean hided) {
        if (repository == null) {
            return Observable.just(false);
        }

        repository.setHided(hided);
        saveToDataBase(repository);
        return Observable.just(true);
    }
}
