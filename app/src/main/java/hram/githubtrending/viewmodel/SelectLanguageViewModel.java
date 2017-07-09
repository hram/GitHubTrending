package hram.githubtrending.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.viethoa.RecyclerViewFastScroller;
import com.viethoa.models.AlphabetItem;

import java.util.ArrayList;
import java.util.List;

import hram.githubtrending.BR;
import hram.githubtrending.R;
import me.tatarka.bindingcollectionadapter2.BindingListViewAdapter;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
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

    public List<AlphabetItem> alphabetItems = new ArrayList<>();

    public FastScrollerAdapter<LanguageViewModel> adapter = new FastScrollerAdapter<>();

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

    public class FastScrollerAdapter<T> extends BindingRecyclerViewAdapter<LanguageViewModel> implements RecyclerViewFastScroller.BubbleTextGetter {

        @Override
        public String getTextToShowInBubble(int pos) {
            if (pos < 0 || pos >= languageItems.size())
                return null;

            final String name = languageItems.get(pos).getName();
            if (TextUtils.isEmpty(name) || name.length() < 1)
                return null;

            return name.substring(0, 1).toUpperCase();
        }
    }
}
