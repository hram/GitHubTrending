package hram.githubtrending.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import hram.githubtrending.BR;

/**
 * @author Evgeny Khramov
 */

public class RepositoryViewModel extends BaseObservable {
    public boolean checkable;
    @Bindable
    private int index;
    @Bindable
    private boolean checked;
    @Bindable
    private String text;

    public RepositoryViewModel(String text) {
        this.text = text;
    }

    public int getIndex() {
        return index;
    }

    public boolean isChecked() {
        return checked;
    }

    public String getText() {
        return text;
    }

    public boolean onToggleChecked(View v) {
        if (!checkable) {
            return false;
        }
        checked = !checked;
        notifyPropertyChanged(BR.checked);
        return true;
    }
}
