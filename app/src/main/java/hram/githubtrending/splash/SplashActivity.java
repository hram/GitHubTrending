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
import hram.githubtrending.viewmodel.SplashViewModel;

public class SplashActivity extends MvpAppCompatActivity implements SplashView {

    private AcSplashBinding mBinding;

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
    public void showEmpty() {
        mBinding.animationView.setVisibility(View.GONE);
        mBinding.progressActivity.showEmpty(R.drawable.ic_star, "Что то пошло не так (((", "Не удалось получить список языков. Мы уже в курсе. Помощь уже в пути.");
    }

    @Override
    public void showError(@NonNull Throwable throwable) {
        mBinding.animationView.setVisibility(View.GONE);
        mBinding.progressActivity.showError(R.drawable.ic_star, "error", throwable.getMessage(), "обновить", this::refresh);
    }

    @Override
    public void showContent() {
        mBinding.animationView.setVisibility(View.VISIBLE);
        mBinding.progressActivity.showContent();
    }

    private void refresh(View view) {
        mPresenter.loadData();
    }
}
