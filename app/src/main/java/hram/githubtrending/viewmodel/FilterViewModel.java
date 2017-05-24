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

    public final ObservableList<LanguageViewModel> languageViewModels = new ObservableArrayList<>();

    public final ItemBinding<LanguageViewModel> languageViewModelItemBinding = ItemBinding.of(BR.item, R.layout.item);
}
