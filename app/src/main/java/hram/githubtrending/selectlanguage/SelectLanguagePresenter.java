package hram.githubtrending.selectlanguage;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import hram.githubtrending.data.DataManager;
import hram.githubtrending.viewmodel.LanguageViewModel;
import hram.githubtrending.viewmodel.SelectLanguageViewModel;
import hugo.weaving.DebugLog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Evgeny Khramov
 */
@InjectViewState
public class SelectLanguagePresenter extends MvpPresenter<SelectLanguageView> implements LanguageViewModel.OnItemClickListener {

    private SelectLanguageViewModel mViewModel;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        getViewState().setViewModel(SelectLanguageViewModel.createEmpty());

        DataManager.getInstance()
                .getSelectLanguageViewModel(this)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSelectLanguageViewModel, this::handleError);
    }

    private void handleSelectLanguageViewModel(@NonNull SelectLanguageViewModel viewModel) {
        mViewModel = viewModel;
        getViewState().setViewModel(mViewModel);
    }

    private void handleError(@NonNull Throwable throwable) {
        getViewState().setViewModel(SelectLanguageViewModel.createWithError(throwable));
    }

    @Override
    public void onItemClick(@NonNull LanguageViewModel item) {
        if (item.isChecked()) {
            return;
        }

        if (mViewModel.getCheckedLanguage() != null) {
            mViewModel.getCheckedLanguage().setChecked(false);
        }

        item.setChecked(true);
        mViewModel.setCheckedLanguage(item);
        mViewModel.isButtonNextEnabled.set(mViewModel.getCheckedLanguage() != null);
        DataManager.getInstance().setSearchParamsLanguage(item.getHref());
        DataManager.getInstance().setSearchParamsLanguageName(item.getName());
    }
}
