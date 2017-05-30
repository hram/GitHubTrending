package hram.githubtrending.filter;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import hram.githubtrending.viewmodel.FilterViewModel;

/**
 * @author Evgeny Khramov
 */
public interface FilterView extends MvpView {

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void setViewModel(@NonNull FilterViewModel viewModel);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void setFilterWasChanged(boolean value);
}
