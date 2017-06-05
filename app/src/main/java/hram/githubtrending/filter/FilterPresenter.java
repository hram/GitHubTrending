package hram.githubtrending.filter;

import android.os.Build;
import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import hram.githubtrending.data.DataManager;
import hram.githubtrending.viewmodel.FilterViewModel;
import hram.githubtrending.viewmodel.LanguageViewModel;
import hram.githubtrending.viewmodel.LanguagesAndTimeSpan;
import hram.githubtrending.viewmodel.TimeSpanViewModel;
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

    private FilterViewModel mViewModel;

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
        mViewModel.languageItems.addAll(item.getLanguages());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mViewModel.setCheckedLanguage(item.getLanguages().stream().filter(LanguageViewModel::isChecked).findFirst().orElse(null));
        } else {
            for (LanguageViewModel model : item.getLanguages()) {
                if (model.isChecked()) {
                    mViewModel.setCheckedLanguage(model);
                    break;
                }
            }
        }

        mViewModel.timeSpanItems.clear();
        mViewModel.timeSpanItems.addAll(item.getTimeSpans());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mViewModel.setCheckedTimeSpan(item.getTimeSpans().stream().filter(TimeSpanViewModel::isChecked).findFirst().orElse(null));
        } else {
            for (TimeSpanViewModel model : item.getTimeSpans()) {
                if (model.isChecked()) {
                    mViewModel.setCheckedTimeSpan(model);
                    break;
                }
            }
        }

        mViewModel.isButtonNextEnabled.set(mViewModel.getCheckedLanguage() != null && mViewModel.getCheckedTimeSpan() != null);
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
        mViewModel.isButtonNextEnabled.set(mViewModel.getCheckedLanguage() != null && mViewModel.getCheckedTimeSpan() != null);
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
        mViewModel.isButtonNextEnabled.set(mViewModel.getCheckedLanguage() != null && mViewModel.getCheckedTimeSpan() != null);
        DataManager.getInstance().setSearchParamsLanguage(item.getHref());
        DataManager.getInstance().setSearchParamsLanguageName(item.getName());
        getViewState().setFilterWasChanged(!mLanguage.equals(DataManager.getInstance().getParams().getLanguage()) || !mTimeSpan.equals(DataManager.getInstance().getParams().getTimeSpan()));
    }
}
