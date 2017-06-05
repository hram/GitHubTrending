package hram.githubtrending.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import hram.githubtrending.BR;
import hram.githubtrending.R;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * @author Evgeny Khramov
 */
public class SelectLanguageViewModel implements LanguageViewModel.OnItemClickListener {

    private final LanguageViewModel.OnItemClickListener mLanguageListener;

    public final ObservableList<LanguageViewModel> languageItems = new ObservableArrayList<>();

    public final ItemBinding<LanguageViewModel> languageItemBinding = ItemBinding.<LanguageViewModel>of(BR.item, R.layout.item_language)
            .bindExtra(BR.listener, this);

    public ObservableBoolean isButtonNextEnabled = new ObservableBoolean(false);

    public final ObservableField<String> error = new ObservableField<>();

    public ObservableBoolean hasError = new ObservableBoolean(false);

    private LanguageViewModel mCheckedLanguage;

    @NonNull
    public static SelectLanguageViewModel createEmpty() {
        return new SelectLanguageViewModel();
    }

    public static SelectLanguageViewModel createWithError(@NonNull Throwable throwable) {
        final SelectLanguageViewModel viewModel = new SelectLanguageViewModel();
        viewModel.hasError.set(true);
        viewModel.error.set(throwable.getMessage());
        return viewModel;
    }

    private SelectLanguageViewModel() {
        mLanguageListener = item -> {
            // do nothing
        };
    }

    public SelectLanguageViewModel(@NonNull LanguageViewModel.OnItemClickListener languageListener) {
        mLanguageListener = languageListener;
    }

    @Override
    public void onItemClick(@NonNull LanguageViewModel item) {
        mLanguageListener.onItemClick(item);
    }

    @Nullable
    public LanguageViewModel getCheckedLanguage() {
        return mCheckedLanguage;
    }

    public void setCheckedLanguage(@NonNull LanguageViewModel checkedLanguage) {
        mCheckedLanguage = checkedLanguage;
    }
}
