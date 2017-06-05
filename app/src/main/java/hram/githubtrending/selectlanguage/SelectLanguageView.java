package hram.githubtrending.selectlanguage;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import hram.githubtrending.viewmodel.SelectLanguageViewModel;

/**
 * @author Evgeny Khramov
 */
interface SelectLanguageView extends MvpView {

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void setViewModel(@NonNull SelectLanguageViewModel viewModel);
}
