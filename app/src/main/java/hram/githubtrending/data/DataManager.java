package hram.githubtrending.data;

import android.support.annotation.NonNull;

import com.github.florent37.retrojsoup.RetroJsoup;

import java.util.ArrayList;
import java.util.List;

import hram.githubtrending.model.Language;
import hram.githubtrending.model.RepositoryModel;
import hram.githubtrending.model.TimeSpan;
import hram.githubtrending.model.Trending;
import hram.githubtrending.viewmodel.RepositoryViewModel;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * @author Evgeny Khramov
 */

public class DataManager {

    private static DataManager sDataManager;

    private final DatabaseHelper mDatabaseHelper;

    public static DataManager getInstance() {
        if (sDataManager == null) {
            sDataManager = new DataManager(new DatabaseHelper());
        }
        return sDataManager;
    }

    private DataManager(DatabaseHelper databaseHelper) {
        mDatabaseHelper = databaseHelper;
    }

    @NonNull
    public Single<List<RepositoryViewModel>> getRepositories(@Language String language, @TimeSpan String timeSpan) {
        final Trending trending = new RetroJsoup.Builder()
                .url(String.format("https://github.com/trending/%s?since=%s", language, timeSpan))
                //.client(okHttpClient)
                .build()
                .create(Trending.class);

        return trending.getJava()
                .flatMap(this::mapToViewModel)
                .toList();
    }

    @NonNull
    private Observable<List<RepositoryViewModel>> mapToViewModel(List<RepositoryModel> list) {
        final List<RepositoryViewModel> items = new ArrayList<>(list.size());
        for (RepositoryModel model : list) {
            items.add(RepositoryViewModel.create(model));
        }
        return Observable.just(items);
    }

    private Observable<RepositoryViewModel> mapToViewModel(RepositoryModel item) {
        return Observable.just(RepositoryViewModel.create(item));
    }
}
