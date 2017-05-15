package hram.githubtrending.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import java.util.List;

import hram.githubtrending.BR;
import hram.githubtrending.R;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * @author Evgeny Khramov
 */

public class RepositoriesViewModel {

    public final ObservableList<RepositoryViewModel> items = new ObservableArrayList<>();

    public final ItemBinding<RepositoryViewModel> itemBinding = ItemBinding.of(BR.item, R.layout.item);

    public void setItems(@NonNull List<RepositoryViewModel> list) {
        items.clear();
        items.addAll(list);
    }

    public void removeItem(int position) {
        items.remove(position);
    }
}
