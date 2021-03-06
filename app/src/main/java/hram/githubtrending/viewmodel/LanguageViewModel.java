package hram.githubtrending.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;

import org.jetbrains.annotations.Contract;

import hram.githubtrending.BR;
import hram.githubtrending.data.model.Language;

/**
 * @author Evgeny Khramov
 */
public final class LanguageViewModel extends BaseObservable {

    @Bindable
    private String mName;

    @Bindable
    private String mHref;

    @Bindable
    private boolean mChecked;

    @Contract("_ -> !null")
    public static LanguageViewModel create(@NonNull Language model, boolean isChecked) {
        return new LanguageViewModel(model, isChecked);
    }

    private LanguageViewModel(@NonNull Language model, boolean isChecked) {
        mName = model.getName();
        mHref = model.getHref();
        mChecked = isChecked;
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
        return "LanguageViewModel{" +
                "mName='" + mName + '\'' +
                ", mHref='" + mHref + '\'' +
                ", mChecked=" + mChecked +
                '}';
    }

    public interface OnItemClickListener {
        void onItemClick(@NonNull LanguageViewModel item);
    }
}
