package hram.githubtrending.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;

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
    public static TimeSpanViewModel create(@NonNull TimeSpan timeSpan) {
        return new TimeSpanViewModel(timeSpan.getName(), timeSpan.getHref());
    }

    @DebugLog
    public TimeSpanViewModel(@NonNull String name, @NonNull String href) {
        mName = name;
        mHref = href;
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

    @Override
    public String toString() {
        return "TimeSpanViewModel{" +
                "mName='" + mName + '\'' +
                ", mHref='" + mHref + '\'' +
                ", mChecked=" + mChecked +
                '}';
    }
}
