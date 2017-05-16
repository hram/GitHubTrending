package hram.githubtrending.viewmodel;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;

import org.jetbrains.annotations.Contract;

import hram.githubtrending.BR;
import hram.githubtrending.BuildConfig;
import hram.githubtrending.data.model.Repository;
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

    Repository mModel;

    @Contract("_ -> !null")
    public static RepositoryViewModel create(@NonNull Repository model) {
        return new RepositoryViewModel(model);
    }

    private RepositoryViewModel(@NonNull Repository model) {
        mModel = model;
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

    @DebugLog
    public void onClick(View view) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.URL_BASE + mModel.getHref()));
            view.getContext().startActivity(intent);
        } catch (Exception e) {
            // do nothing
        }
    }
}
