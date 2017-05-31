package hram.githubtrending.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import hram.githubtrending.BR;
import hram.githubtrending.R;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * @author Evgeny Khramov
 */

public class FilterViewModel implements LanguageViewModel.OnItemClickListener, TimeSpanViewModel.OnItemClickListener {

    private final LanguageViewModel.OnItemClickListener mLanguageListener;

    private final TimeSpanViewModel.OnItemClickListener mTimeSpanListener;

    public final ObservableList<LanguageViewModel> languageItems = new ObservableArrayList<>();

    public final ItemBinding<LanguageViewModel> languageItemBinding = ItemBinding.<LanguageViewModel>of(BR.item, R.layout.item_language)
            .bindExtra(BR.listener, this);

    public final ObservableList<TimeSpanViewModel> timeSpanItems = new ObservableArrayList<>();

    public final ItemBinding<TimeSpanViewModel> timeSpanItemBinding = ItemBinding.<TimeSpanViewModel>of(BR.item, R.layout.item_time_span)
            .bindExtra(BR.listener, this);

    public ObservableBoolean isForFirstSetup = new ObservableBoolean(false);

    public ObservableBoolean isButtonNextEnabled = new ObservableBoolean(false);

    private TimeSpanViewModel mCheckedTimeSpan;

    private LanguageViewModel mCheckedLanguage;

    public FilterViewModel(@NonNull LanguageViewModel.OnItemClickListener languageListener, @NonNull TimeSpanViewModel.OnItemClickListener timeSpanListener) {
        mLanguageListener = languageListener;
        mTimeSpanListener = timeSpanListener;
    }

    @Override
    public void onItemClick(@NonNull TimeSpanViewModel item) {
        mTimeSpanListener.onItemClick(item);
    }

    @Override
    public void onItemClick(@NonNull LanguageViewModel item) {
        mLanguageListener.onItemClick(item);
    }

    @Nullable
    public TimeSpanViewModel getCheckedTimeSpan() {
        return mCheckedTimeSpan;
    }

    public void setCheckedTimeSpan(@NonNull TimeSpanViewModel checkedTimeSpan) {
        mCheckedTimeSpan = checkedTimeSpan;
    }

    @Nullable
    public LanguageViewModel getCheckedLanguage() {
        return mCheckedLanguage;
    }

    public void setCheckedLanguage(@NonNull LanguageViewModel checkedLanguage) {
        mCheckedLanguage = checkedLanguage;
    }
}
