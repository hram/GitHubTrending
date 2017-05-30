package hram.githubtrending.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;

import hram.githubtrending.BR;
import hram.githubtrending.data.model.TimeSpan;
import hugo.weaving.DebugLog;

/**
 * @author Evgeny Khramov
 */
public class TimeSpanViewModel extends BaseObservable {

    @Bindable
    private String mName;

    @Bindable
    private String mHref;

    @Bindable
    private boolean mChecked;

    @NonNull
    public static TimeSpanViewModel create(@NonNull TimeSpan timeSpan, boolean checked) {
        return new TimeSpanViewModel(timeSpan, checked);
    }

    private TimeSpanViewModel(@NonNull TimeSpan model, boolean checked) {
        mName = model.getName();
        mHref = model.getHref();
        mChecked = checked;
    }

    @NonNull
    public String getName() {
        return mName;
    }

    @NonNull
    public String getHref() {
        return mHref;
    }

    public boolean isChecked() {
        return mChecked;
    }

    public void setChecked(boolean checked) {
        mChecked = checked;
        notifyPropertyChanged(BR.checked);
    }

    @Override
    public String toString() {
        return "TimeSpanViewModel{" +
                "mName='" + mName + '\'' +
                ", mHref='" + mHref + '\'' +
                ", mChecked=" + mChecked +
                '}';
    }

    public interface OnItemClickListener {
        void onItemClick(@NonNull TimeSpanViewModel item);
    }
}
