package hram.githubtrending.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.view.View;

import org.jetbrains.annotations.Contract;

import hram.githubtrending.BR;
import hram.githubtrending.model.RepositoryModel;
import hugo.weaving.DebugLog;

/**
 * @author Evgeny Khramov
 */

public class RepositoryViewModel extends BaseObservable {
    public boolean checkable;

    @Bindable
    private int mIndex;

    @Bindable
    private boolean mChecked;

    @Bindable
    private String mTitle;

    @Bindable
    private String mDescription;

    @Bindable
    private String mStarsToday;

    @Bindable
    private String mAllStars;

    @Bindable
    private String mForks;

    @DebugLog
    @Contract("_ -> !null")
    public static RepositoryViewModel create(@NonNull RepositoryModel model) {
        return new RepositoryViewModel(model);
    }

    private RepositoryViewModel(@NonNull RepositoryModel model) {
        mTitle = model.getTitle();
        mDescription = model.getDescription();
        mStarsToday = model.getStarsToday();
        mAllStars = model.getAllStars();
        mForks = model.getForks();
    }

    public int getIndex() {
        return mIndex;
    }

    public boolean isChecked() {
        return mChecked;
    }

    @NonNull
    public String getTitle() {
        return mTitle;
    }

    @NonNull
    public String getDescription() {
        return mDescription;
    }

    @NonNull
    public String getStarsToday() {
        return mStarsToday;
    }

    @NonNull
    public String getAllStars() {
        return mAllStars;
    }

    @NonNull
    public String getForks() {
        return mForks;
    }

    public boolean onToggleChecked(View v) {
        if (!checkable) {
            return false;
        }
        mChecked = !mChecked;
        notifyPropertyChanged(BR.checked);
        return true;
    }
}
