package hram.githubtrending.splash;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;

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
}
