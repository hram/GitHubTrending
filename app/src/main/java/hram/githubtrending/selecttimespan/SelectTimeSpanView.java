package hram.githubtrending.selecttimespan;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import hram.githubtrending.viewmodel.SelectTimeSpanViewModel;

/**
 * @author Evgeny Khramov
 */

interface SelectTimeSpanView extends MvpView {

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void setViewModel(@NonNull SelectTimeSpanViewModel viewModel);
}
