package hram.githubtrending.splash;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import hram.githubtrending.R;
import hram.githubtrending.databinding.AcSplashBinding;
import hram.githubtrending.selectlanguage.SelectLanguageActivity;
import hram.githubtrending.trends.TrendsActivity;
import hram.githubtrending.utils.FabricUtil;
import hram.githubtrending.viewmodel.SplashViewModel;

public class SplashActivity extends MvpAppCompatActivity implements SplashView {

    protected AcSplashBinding mBinding;

    @InjectPresenter
    SplashPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.ac_splash);
    }

    @Override
    public void setViewModel(@NonNull SplashViewModel viewModel) {

    }

    @Override
    public void openTrendsScreen() {
        TrendsActivity.start(this);
        finish();
    }

    @Override
    public void openFilterScreen() {
        SelectLanguageActivity.start(this);
        finish();
    }

    @Override
    public void showProgress() {
        mBinding.progress.showLoading();
    }

    @Override
    public void showEmpty() {
        mBinding.progress.showEmpty(R.drawable.ic_emergency, "Ooops! Что то пошло не так (((", "Помощь в пути.");
        FabricUtil.logException(FabricUtil.createEmptyDataException());
    }

    @Override
    public void showError(@NonNull Throwable throwable) {
        mBinding.progress.showError(R.drawable.ic_emergency, "Ooops! Произошла ошибка (((", "Помощь в пути", "Обновить", this::refresh);
        FabricUtil.logException(throwable);
    }

    @Override
    public void showContent() {
        // do nothing
    }

    private void refresh(View view) {
        mPresenter.loadData();
    }
}
