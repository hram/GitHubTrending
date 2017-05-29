package hram.githubtrending.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import hram.githubtrending.BR;
import hram.githubtrending.R;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * @author Evgeny Khramov
 */

public class FilterViewModel {

    public final ObservableList<LanguageViewModel> languageItems = new ObservableArrayList<>();

    public final ItemBinding<LanguageViewModel> languageItemBinding = ItemBinding.of(BR.item, R.layout.item_language);

    public final ObservableList<TimeSpanViewModel> timeSpanItems = new ObservableArrayList<>();

    public final ItemBinding<TimeSpanViewModel> timeSpanItemBinding = ItemBinding.of(BR.item, R.layout.item_time_span);
}
