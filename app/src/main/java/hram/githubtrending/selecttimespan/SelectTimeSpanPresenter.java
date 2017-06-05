package hram.githubtrending.selecttimespan;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import hram.githubtrending.data.DataManager;
import hram.githubtrending.viewmodel.SelectTimeSpanViewModel;
import hram.githubtrending.viewmodel.TimeSpanViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Evgeny Khramov
 */
@InjectViewState
public class SelectTimeSpanPresenter extends MvpPresenter<SelectTimeSpanView> implements TimeSpanViewModel.OnItemClickListener {

    private SelectTimeSpanViewModel mViewModel;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        getViewState().setViewModel(SelectTimeSpanViewModel.createEmpty());

        DataManager.getInstance()
                .getSelectTimeSpanViewModel(this)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleLanguagesAndTimeSpan, this::handleError);
    }

    private void handleLanguagesAndTimeSpan(@NonNull SelectTimeSpanViewModel viewModel) {
        mViewModel = viewModel;
        getViewState().setViewModel(mViewModel);
    }

    private void handleError(@NonNull Throwable throwable) {
        getViewState().setViewModel(SelectTimeSpanViewModel.createWithError(throwable));
    }

    @Override
    public void onItemClick(@NonNull TimeSpanViewModel item) {
        if (item.isChecked()) {
            return;
        }

        if (mViewModel.getCheckedTimeSpan() != null) {
            mViewModel.getCheckedTimeSpan().setChecked(false);
        }
        item.setChecked(true);
        mViewModel.setCheckedTimeSpan(item);
        mViewModel.isButtonNextEnabled.set(mViewModel.getCheckedTimeSpan() != null);
        DataManager.getInstance().setSearchParamsTimeSpan(item.getHref());
    }
}
