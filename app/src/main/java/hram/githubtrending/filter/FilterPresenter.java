package hram.githubtrending.filter;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import hram.githubtrending.data.DataManager;
import hram.githubtrending.viewmodel.FilterViewModel;
import hram.githubtrending.viewmodel.LanguageViewModel;
import hram.githubtrending.viewmodel.TimeSpanViewModel;
import hugo.weaving.DebugLog;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Evgeny Khramov
 */
@InjectViewState
public class FilterPresenter extends MvpPresenter<FilterView> {

    FilterViewModel mViewModel;

    @DebugLog
    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        mViewModel = new FilterViewModel();
        getViewState().setViewModel(mViewModel);

        Observable.zip(DataManager.getInstance().getLanguages(), DataManager.getInstance().getTimeSpans(), this::mapToLanguagesAndTimeSpan)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleLanguagesAndTimeSpan, this::handleError);
    }

    @DebugLog
    private void handleLanguagesAndTimeSpan(@NonNull LanguagesAndTimeSpan item) {
        mViewModel.languageItems.clear();
        mViewModel.languageItems.addAll(item.mLanguages);

        mViewModel.timeSpanItems.clear();
        mViewModel.timeSpanItems.addAll(item.mTimeSpans);
    }

    @DebugLog
    private void handleError(@NonNull Throwable throwable) {

    }

    @DebugLog
    private LanguagesAndTimeSpan mapToLanguagesAndTimeSpan(List<LanguageViewModel> languages, List<TimeSpanViewModel> timeSpans) {
        return new LanguagesAndTimeSpan(languages, timeSpans);
    }

    private class LanguagesAndTimeSpan {

        private List<LanguageViewModel> mLanguages;

        private List<TimeSpanViewModel> mTimeSpans;

        public LanguagesAndTimeSpan(List<LanguageViewModel> languages, List<TimeSpanViewModel> timeSpans) {
            mLanguages = languages;
            mTimeSpans = timeSpans;
        }
    }
}
