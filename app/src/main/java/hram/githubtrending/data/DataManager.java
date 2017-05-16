package hram.githubtrending.data;

import android.support.annotation.NonNull;

import com.github.florent37.retrojsoup.RetroJsoup;

import java.util.ArrayList;
import java.util.List;

import hram.githubtrending.data.db.DatabaseHelper;
import hram.githubtrending.data.model.Language;
import hram.githubtrending.data.model.Repository;
import hram.githubtrending.data.network.NetworkHelper;
import hram.githubtrending.data.network.Trending;
import hram.githubtrending.viewmodel.LanguageViewModel;
import hram.githubtrending.viewmodel.RepositoryViewModel;
import hugo.weaving.DebugLog;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * @author Evgeny Khramov
 */

public class DataManager {

    private static DataManager sDataManager;

    private final DatabaseHelper mDatabaseHelper;

    private final NetworkHelper mNetworkHelper;

    @NonNull
    public static DataManager getInstance() {
        if (sDataManager == null) {
            sDataManager = new DataManager(new DatabaseHelper(), new NetworkHelper());
        }
        return sDataManager;
    }

    private DataManager(@NonNull DatabaseHelper databaseHelper, @NonNull NetworkHelper networkHelper) {
        mDatabaseHelper = databaseHelper;
        mNetworkHelper = networkHelper;
    }

    // TODO add loading from DB and only if DB empty load from network
    @DebugLog
    @NonNull
    public Single<List<RepositoryViewModel>> getRepositories(@NonNull String language, @NonNull String timeSpan) {
        final Trending trending = new RetroJsoup.Builder()
                .url(String.format("https://github.com/trending/%s?since=%s", language, timeSpan))
                //.client(okHttpClient)
                .build()
                .create(Trending.class);

        return trending.getRepositories()
                .flatMap(item -> setLanguageAndTimeSpan(item, language, timeSpan))
                .flatMap(this::saveToDataBase)
                .flatMap(this::mapToViewModel)
                .toList();
    }

    // TODO add loading from DB and only if DB empty load from network
    @NonNull
    public Single<List<LanguageViewModel>> getLanguages() {
        final Trending trending = new RetroJsoup.Builder()
                .url("https://github.com/trending/")
                //.client(okHttpClient)
                .build()
                .create(Trending.class);

        return trending.getLanguages()
                .flatMap(this::mapToViewModel)
                .toList();
    }

    @NonNull
    private Observable<List<RepositoryViewModel>> mapToViewModel(List<Repository> list) {
        final List<RepositoryViewModel> items = new ArrayList<>(list.size());
        for (Repository model : list) {
            items.add(RepositoryViewModel.create(model));
        }
        return Observable.just(items);
    }

    @DebugLog
    @NonNull
    private Observable<RepositoryViewModel> mapToViewModel(@NonNull Repository item) {
        return Observable.just(RepositoryViewModel.create(item));
    }

    @DebugLog
    @NonNull
    private Observable<Repository> saveToDataBase(@NonNull Repository item) {
        mDatabaseHelper.saveRepository(item);
        return Observable.just(item);
    }

    @DebugLog
    @NonNull
    private Observable<Repository> setLanguageAndTimeSpan(@NonNull Repository item, @NonNull String language, @NonNull String timeSpan) {
        item.setLanguage(language);
        item.setTimeSpan(timeSpan);
        return Observable.just(item);
    }

    private Observable<LanguageViewModel> mapToViewModel(Language item) {
        return Observable.just(LanguageViewModel.create(item));
    }
}
