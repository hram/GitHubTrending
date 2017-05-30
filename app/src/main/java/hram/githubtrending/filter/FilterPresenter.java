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
public class FilterPresenter extends MvpPresenter<FilterView> implements LanguageViewModel.OnItemClickListener, TimeSpanViewModel.OnItemClickListener {

    private String mLanguage;

    private String mTimeSpan;

    FilterViewModel mViewModel;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        mLanguage = DataManager.getInstance().getParams().getLanguage();
        mTimeSpan = DataManager.getInstance().getParams().getTimeSpan();

        mViewModel = new FilterViewModel(this, this);
        getViewState().setViewModel(mViewModel);

        Observable.zip(DataManager.getInstance().getLanguages(), DataManager.getInstance().getTimeSpans(), this::mapToLanguagesAndTimeSpan)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleLanguagesAndTimeSpan, this::handleError);
    }

    private void handleLanguagesAndTimeSpan(@NonNull LanguagesAndTimeSpan item) {
        mViewModel.languageItems.clear();
        mViewModel.languageItems.addAll(item.mLanguages);
        mViewModel.setCheckedLanguage(item.mLanguages.stream().filter(LanguageViewModel::isChecked).findFirst().orElse(null));

        mViewModel.timeSpanItems.clear();
        mViewModel.timeSpanItems.addAll(item.mTimeSpans);
        mViewModel.setCheckedTimeSpan(item.mTimeSpans.stream().filter(TimeSpanViewModel::isChecked).findFirst().orElse(null));
    }

    private void handleError(@NonNull Throwable throwable) {

    }

    private LanguagesAndTimeSpan mapToLanguagesAndTimeSpan(List<LanguageViewModel> languages, List<TimeSpanViewModel> timeSpans) {
        return new LanguagesAndTimeSpan(languages, timeSpans);
    }

    @Override
    public void onItemClick(@NonNull TimeSpanViewModel item) {
        if (item.isChecked()) {
            return;
        }

        if (mViewModel.getCheckedTimeSpan() != null) {
            mViewModel.getCheckedTimeSpan().setChecked(false);
        }
        item.setChecked(true);
        mViewModel.setCheckedTimeSpan(item);
        DataManager.getInstance().setSearchParamsTimeSpan(item.getHref());
        getViewState().setFilterWasChanged(!mLanguage.equals(DataManager.getInstance().getParams().getLanguage()) || !mTimeSpan.equals(DataManager.getInstance().getParams().getTimeSpan()));
    }

    @Override
    public void onItemClick(@NonNull LanguageViewModel item) {
        if (item.isChecked()) {
            return;
        }

        if (mViewModel.getCheckedLanguage() != null) {
            mViewModel.getCheckedLanguage().setChecked(false);
        }
        item.setChecked(true);
        mViewModel.setCheckedLanguage(item);
        DataManager.getInstance().setSearchParamsLanguage(item.getHref());
        getViewState().setFilterWasChanged(!mLanguage.equals(DataManager.getInstance().getParams().getLanguage()) || !mTimeSpan.equals(DataManager.getInstance().getParams().getTimeSpan()));
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
