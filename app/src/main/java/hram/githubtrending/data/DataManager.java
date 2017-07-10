package hram.githubtrending.data;

import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.viethoa.models.AlphabetItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import hram.githubtrending.App;
import hram.githubtrending.data.db.DatabaseHelper;
import hram.githubtrending.data.model.Language;
import hram.githubtrending.data.model.Repository;
import hram.githubtrending.data.model.SearchParams;
import hram.githubtrending.data.model.TimeSpan;
import hram.githubtrending.data.network.NetworkHelper;
import hram.githubtrending.data.prefepences.PreferencesHelper;
import hram.githubtrending.viewmodel.FilterViewModel;
import hram.githubtrending.viewmodel.LanguageViewModel;
import hram.githubtrending.viewmodel.LanguagesAndTimeSpan;
import hram.githubtrending.viewmodel.RepositoryViewModel;
import hram.githubtrending.viewmodel.SelectLanguageViewModel;
import hram.githubtrending.viewmodel.SelectTimeSpanViewModel;
import hram.githubtrending.viewmodel.TimeSpanViewModel;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Evgeny Khramov
 */

public class DataManager {

    private static DataManager sDataManager;

    @Inject
    DatabaseHelper mDatabaseHelper;

    @Inject
    NetworkHelper mNetworkHelper;

    @Inject
    PreferencesHelper mPreferencesHelper;

    @NonNull
    private SearchParams mParams;

    @NonNull
    public static DataManager getInstance() {
        if (sDataManager == null) {
            sDataManager = new DataManager();
        }
        return sDataManager;
    }

    private DataManager() {
        App.getInstance().getAppComponent().inject(this);
        mParams = mPreferencesHelper.getSearchParams();
    }

    public void clearAppData() {
        mPreferencesHelper.clearAppData();
        mDatabaseHelper.clearAppData();
    }

    @NonNull
    public SearchParams getParams() {
        return mParams;
    }

    @NonNull
    public Observable<List<RepositoryViewModel>> getRepositories() {
        return mDatabaseHelper.getRepositoriesObservable(mParams.getLanguage(), mParams.getTimeSpan())
                .flatMap(this::ifEmptyThenGetRepositoriesFromNetwork)
                .flatMap(this::mapRepositoryToViewModel);
    }

    @NonNull
    public Observable<List<RepositoryViewModel>> refreshRepositories() {
        return mNetworkHelper.getRepositories(mParams.getLanguage(), mParams.getTimeSpan())
                .flatMap(repositories -> mDatabaseHelper.saveRepositories(repositories, mParams.getLanguage(), mParams.getTimeSpan()))
                .flatMap(this::mapRepositoryToViewModel);
    }

    @NonNull
    public Observable<RepositoryViewModel> getRepository(@NonNull String id) {
        return mDatabaseHelper.getRepositoryObservable(id)
                .flatMap(this::mapToViewModel);
    }

    @NonNull
    public Observable<List<LanguageViewModel>> getLanguages() {
        //return Observable.defer(() -> getLanguagesEmpty());
        return mDatabaseHelper.getLanguagesObservable()
                .flatMap(this::ifEmptyThenGetLanguagesFromNetwork)
                .flatMap(this::mapLanguageToViewModel)
                .onErrorReturn(this::logThrowableAndReturnEmptyList);
    }

    private Observable<List<LanguageViewModel>> getLanguagesEmpty() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // do nothing
        }

        return Observable.just(new ArrayList<>());
    }

    private Observable<List<LanguageViewModel>> getLanguagesError() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // do nothing
        }

        return Observable.error(new Exception("this is a message"));
    }

    @NonNull
    public Observable<List<TimeSpanViewModel>> getTimeSpans() {
        return mDatabaseHelper.getTimeSpansObservable()
                .flatMap(this::ifEmptyThenGetTimeSpansFromNetwork)
                .flatMap(this::mapTimeSpanToViewModel)
                .onErrorReturn(this::logThrowableAndReturnEmptyList);
    }

    @NonNull
    private ArrayList logThrowableAndReturnEmptyList(@NonNull Throwable throwable) {
        // TODO log to fabric
        return new ArrayList<>();
    }

    @NonNull
    private Observable<List<RepositoryViewModel>> mapRepositoryToViewModel(List<Repository> list) {
        final List<RepositoryViewModel> items = new ArrayList<>(list.size());
        for (Repository model : list) {
            items.add(RepositoryViewModel.create(model));
        }
        return Observable.just(items);
    }

    @NonNull
    private Observable<List<LanguageViewModel>> mapLanguageToViewModel(@NonNull List<Language> list) {
        final List<LanguageViewModel> items = new ArrayList<>(list.size());
        for (Language model : list) {
            items.add(LanguageViewModel.create(model, Uri.parse(model.getHref()).getLastPathSegment().equalsIgnoreCase(mParams.getLanguage())));
        }
        return Observable.just(items);
    }

    @NonNull
    private Observable<List<TimeSpanViewModel>> mapTimeSpanToViewModel(@NonNull List<TimeSpan> list) {
        final List<TimeSpanViewModel> items = new ArrayList<>(list.size());
        for (TimeSpan model : list) {
            items.add(TimeSpanViewModel.create(model, Uri.parse(model.getHref()).getQueryParameter("since").equalsIgnoreCase(mParams.getTimeSpan())));
        }
        return Observable.just(items);
    }

    private Observable<RepositoryViewModel> mapToViewModel(Repository item) {
        return Observable.just(RepositoryViewModel.create(item));
    }

    @NonNull
    public Observable<Repository> saveToDataBase(@NonNull Repository item) {
        mDatabaseHelper.saveRepository(item);
        return Observable.just(item);
    }

    @NonNull
    public Observable<Boolean> setHided(@NonNull String id, boolean hided) {
        return mDatabaseHelper.getRepositoryObservable(id)
                .flatMap(repository -> setHidedAndSave(repository, hided));
    }

    @NonNull
    private Observable<LanguageViewModel> mapToViewModel(@NonNull Language item) {
        return Observable.just(LanguageViewModel.create(item, Uri.parse(item.getHref()).getLastPathSegment().equalsIgnoreCase(mParams.getLanguage())));
    }

    @NonNull
    private Observable<TimeSpanViewModel> mapToViewModel(@NonNull TimeSpan item) {
        return Observable.just(TimeSpanViewModel.create(item, Uri.parse(item.getHref()).getQueryParameter("since").equalsIgnoreCase(mParams.getTimeSpan())));
    }

    @NonNull
    private Observable<List<Repository>> ifEmptyThenGetRepositoriesFromNetwork(@NonNull List<Repository> list) {
        if (list.isEmpty()) {
            return mNetworkHelper.getRepositories(mParams.getLanguage(), mParams.getTimeSpan())
                    .flatMap(repositories -> mDatabaseHelper.saveRepositories(repositories, mParams.getLanguage(), mParams.getTimeSpan()));
        } else {
            return Observable.just(list);
        }
    }

    @NonNull
    private Observable<List<Language>> ifEmptyThenGetLanguagesFromNetwork(@NonNull List<Language> list) {
        if (list.isEmpty()) {
            return mNetworkHelper.getLanguages()
                    .flatMap(mDatabaseHelper::saveLanguages);
        } else {
            return Observable.just(list);
        }
    }

    @NonNull
    private Observable<List<TimeSpan>> ifEmptyThenGetTimeSpansFromNetwork(@NonNull List<TimeSpan> list) {
        if (list.isEmpty()) {
            return mNetworkHelper.getTimeSpans()
                    .flatMap(mDatabaseHelper::saveTimeSpans);
        } else {
            return Observable.just(list);
        }
    }

    @NonNull
    private Observable<Boolean> setHidedAndSave(@Nullable Repository repository, boolean hided) {
        if (repository == null) {
            return Observable.just(false);
        }

        repository.setHided(hided);
        saveToDataBase(repository);
        return Observable.just(true);
    }

    public void setSearchParamsLanguage(@NonNull String language) {
        mParams.setLanguage(Uri.parse(language).getLastPathSegment());
        mPreferencesHelper.setSearchParams(mParams);
    }

    public void setSearchParamsLanguageName(@NonNull String languageName) {
        mParams.setLanguageName(languageName);
        mPreferencesHelper.setSearchParams(mParams);
    }

    public void setSearchParamsTimeSpan(@NonNull String timeSpan) {
        mParams.setTimeSpan(Uri.parse(timeSpan).getQueryParameter("since"));
        mPreferencesHelper.setSearchParams(mParams);
    }

    @NonNull
    public Observable<SelectLanguageViewModel> getSelectLanguageViewModel(@NonNull LanguageViewModel.OnItemClickListener languageListener) {
        return getLanguages()
                .flatMap(list -> mapLanguagesToSelectLanguageViewModel(languageListener, list));
    }

    @NonNull
    private Observable<SelectLanguageViewModel> mapLanguagesToSelectLanguageViewModel(@NonNull LanguageViewModel.OnItemClickListener listener, @NonNull List<LanguageViewModel> list) {
        final SelectLanguageViewModel viewModel = new SelectLanguageViewModel(listener);
        viewModel.languageItems.addAll(list);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            viewModel.setCheckedLanguage(list.stream().filter(LanguageViewModel::isChecked).findFirst().orElse(null));
        } else {
            for (LanguageViewModel model : list) {
                if (model.isChecked()) {
                    viewModel.setCheckedLanguage(model);
                    break;
                }
            }
        }

        final List<String> strAlphabets = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            final String name = list.get(i).getName();
            if (TextUtils.isEmpty(name) || name.trim().isEmpty()) {
                continue;
            }

            final String word = name.substring(0, 1).toUpperCase();
            if (!strAlphabets.contains(word)) {
                strAlphabets.add(word);
                viewModel.alphabetItems.add(new AlphabetItem(i, word, false));
            }
        }

        viewModel.isButtonNextEnabled.set(viewModel.getCheckedLanguage() != null);
        return Observable.just(viewModel);
    }

    @NonNull
    public Observable<SelectTimeSpanViewModel> getSelectTimeSpanViewModel(@NonNull TimeSpanViewModel.OnItemClickListener languageListener) {
        return getTimeSpans()
                .flatMap(list -> mapTimeSpansToSelectTimeSpanViewModel(languageListener, list));
    }

    @NonNull
    private Observable<SelectTimeSpanViewModel> mapTimeSpansToSelectTimeSpanViewModel(@NonNull TimeSpanViewModel.OnItemClickListener listener, @NonNull List<TimeSpanViewModel> list) {
        final SelectTimeSpanViewModel viewModel = new SelectTimeSpanViewModel(listener);
        viewModel.timeSpanItems.clear();
        viewModel.timeSpanItems.addAll(list);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            viewModel.setCheckedTimeSpan(list.stream().filter(TimeSpanViewModel::isChecked).findFirst().orElse(null));
        } else {
            for (TimeSpanViewModel model : list) {
                if (model.isChecked()) {
                    viewModel.setCheckedTimeSpan(model);
                    break;
                }
            }
        }

        viewModel.isButtonNextEnabled.set(viewModel.getCheckedTimeSpan() != null);
        return Observable.just(viewModel);
    }

    @NonNull
    public Observable<FilterViewModel> getFilterViewModel(@NonNull LanguageViewModel.OnItemClickListener languageListener, @NonNull TimeSpanViewModel.OnItemClickListener timeSpanListener) {
        return Observable.zip(DataManager.getInstance().getLanguages(), DataManager.getInstance().getTimeSpans(), this::mapToLanguagesAndTimeSpan)
                .flatMap(languagesAndTimeSpan -> Observable.just(mapLanguagesAndTimeSpanToViewModel(languagesAndTimeSpan, languageListener, timeSpanListener)));
    }

    @NonNull
    private LanguagesAndTimeSpan mapToLanguagesAndTimeSpan(@NonNull List<LanguageViewModel> languages, @NonNull List<TimeSpanViewModel> timeSpans) {
        return new LanguagesAndTimeSpan(languages, timeSpans);
    }

    @NonNull
    private FilterViewModel mapLanguagesAndTimeSpanToViewModel(@NonNull LanguagesAndTimeSpan languagesAndTimeSpan, @NonNull LanguageViewModel.OnItemClickListener languageListener, @NonNull TimeSpanViewModel.OnItemClickListener timeSpanListener) {
        final FilterViewModel viewModel = new FilterViewModel(languageListener, timeSpanListener);
        viewModel.languageItems.addAll(languagesAndTimeSpan.getLanguages());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            viewModel.setCheckedLanguage(languagesAndTimeSpan.getLanguages().stream().filter(LanguageViewModel::isChecked).findFirst().orElse(null));
        } else {
            for (LanguageViewModel model : languagesAndTimeSpan.getLanguages()) {
                if (model.isChecked()) {
                    viewModel.setCheckedLanguage(model);
                    break;
                }
            }
        }

        viewModel.timeSpanItems.addAll(languagesAndTimeSpan.getTimeSpans());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            viewModel.setCheckedTimeSpan(languagesAndTimeSpan.getTimeSpans().stream().filter(TimeSpanViewModel::isChecked).findFirst().orElse(null));
        } else {
            for (TimeSpanViewModel model : languagesAndTimeSpan.getTimeSpans()) {
                if (model.isChecked()) {
                    viewModel.setCheckedTimeSpan(model);
                    break;
                }
            }
        }

        final List<String> strAlphabets = new ArrayList<>();
        for (int i = 0; i < languagesAndTimeSpan.getLanguages().size(); i++) {
            final String name = languagesAndTimeSpan.getLanguages().get(i).getName();
            if (TextUtils.isEmpty(name) || name.trim().isEmpty()) {
                continue;
            }

            final String word = name.substring(0, 1).toUpperCase();
            if (!strAlphabets.contains(word)) {
                strAlphabets.add(word);
                viewModel.alphabetItems.add(new AlphabetItem(i, word, false));
            }
        }

        viewModel.isButtonNextEnabled.set(viewModel.getCheckedLanguage() != null && viewModel.getCheckedTimeSpan() != null);
        return viewModel;
    }
}
