package hram.githubtrending.selecttimespan;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import hram.githubtrending.R;
import hram.githubtrending.databinding.AcSelectTimeSpanBinding;
import hram.githubtrending.trends.TrendsActivity;
import hram.githubtrending.viewmodel.SelectTimeSpanViewModel;

/**
 * @author Evgeny Khramov
 */

public class SelectTimeSpanActivity extends MvpAppCompatActivity implements SelectTimeSpanView, View.OnClickListener {

    private AcSelectTimeSpanBinding mBinding;

    @InjectPresenter
    SelectTimeSpanPresenter mPresenter;

    public static void start(@NonNull Context context) {
        context.startActivity(new Intent(context, SelectTimeSpanActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.ac_select_time_span);
    }

    @Override
    public void setViewModel(@NonNull SelectTimeSpanViewModel viewModel) {
        mBinding.setViewModel(viewModel);
        mBinding.setListener(this);
        mBinding.executePendingBindings();
    }

    @Override
    public void onClick(View v) {
        TrendsActivity.start(this);
        finish();
    }
}
