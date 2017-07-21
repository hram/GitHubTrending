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

public class RepositoriesViewModel implements RepositoryViewModel.OnItemClickListener {

    private final RepositoryViewModel.OnItemClickListener mListener;

    public final ObservableList<RepositoryViewModel> items = new ObservableArrayList<>();

    public final ItemBinding<RepositoryViewModel> itemBinding = ItemBinding.<RepositoryViewModel>of(BR.item, R.layout.item_repository)
            .bindExtra(BR.listener, this);

    public RepositoriesViewModel(@NonNull RepositoryViewModel.OnItemClickListener listener) {
        mListener = listener;
    }

    public void setItems(@NonNull List<RepositoryViewModel> list) {
        items.clear();
        items.addAll(list);
    }

    public void removeItem(int position) {
        items.remove(position);
    }

    @Override
    public void onItemClick(@NonNull RepositoryViewModel item) {
        mListener.onItemClick(item);
    }
}
