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
public class SelectTimeSpanViewModel implements TimeSpanViewModel.OnItemClickListener {

    private final TimeSpanViewModel.OnItemClickListener mTimeSpanListener;

    public final ObservableList<TimeSpanViewModel> timeSpanItems = new ObservableArrayList<>();

    public final ItemBinding<TimeSpanViewModel> timeSpanItemBinding = ItemBinding.<TimeSpanViewModel>of(BR.item, R.layout.item_time_span)
            .bindExtra(BR.listener, this);

    public ObservableBoolean isButtonNextEnabled = new ObservableBoolean(false);

    public final ObservableField<String> error = new ObservableField<>();

    public ObservableBoolean hasError = new ObservableBoolean(false);

    private TimeSpanViewModel mCheckedTimeSpan;

    @NonNull
    public static SelectTimeSpanViewModel createEmpty() {
        return new SelectTimeSpanViewModel();
    }

    public static SelectTimeSpanViewModel createWithError(@NonNull Throwable throwable) {
        final SelectTimeSpanViewModel viewModel = new SelectTimeSpanViewModel();
        viewModel.hasError.set(true);
        viewModel.error.set(throwable.getMessage());
        return viewModel;
    }

    private SelectTimeSpanViewModel() {
        mTimeSpanListener = item -> {
            // do nithing
        };
    }

    public SelectTimeSpanViewModel(@NonNull TimeSpanViewModel.OnItemClickListener timeSpanListener) {
        mTimeSpanListener = timeSpanListener;
    }

    @Override
    public void onItemClick(@NonNull TimeSpanViewModel item) {
        mTimeSpanListener.onItemClick(item);
    }

    @Nullable
    public TimeSpanViewModel getCheckedTimeSpan() {
        return mCheckedTimeSpan;
    }

    public void setCheckedTimeSpan(@NonNull TimeSpanViewModel checkedTimeSpan) {
        mCheckedTimeSpan = checkedTimeSpan;
    }
}
