package hram.githubtrending.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.view.View;

import org.jetbrains.annotations.Contract;

import hram.githubtrending.data.model.Repository;

/**
 * @author Evgeny Khramov
 */
public final class RepositoryViewModel extends BaseObservable {

    public boolean checkable;

    private String mId;

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
        mId = model.getKey();
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
    public String getId() {
        return mId;
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

    public int getStarsTodayVisibility() {
        return "".equals(mStarsToday) ? View.GONE : View.VISIBLE;
    }

    @NonNull
    public String getAllStars() {
        return mAllStars;
    }

    @NonNull
    public String getForks() {
        return mForks;
    }

    public interface OnItemClickListener {
        void onItemClick(@NonNull RepositoryViewModel item);
    }
}
