package hram.githubtrending.filter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import hram.githubtrending.R;
import hram.githubtrending.databinding.AcFilterBinding;
import hram.githubtrending.trends.TrendsActivity;
import hram.githubtrending.viewmodel.FilterViewModel;
import hugo.weaving.DebugLog;

public class FilterActivity extends MvpAppCompatActivity implements FilterView, View.OnClickListener {

    private static final String START_FOR_FIRST_SETUP = "start_for_first_setup";

    AcFilterBinding mBinding;

    @InjectPresenter
    FilterPresenter mPresenter;

    private boolean mIsForFirstSetup;

    public static void startForFirstSetup(@NonNull Context context) {
        final Intent intent = new Intent(context, FilterActivity.class);
        intent.putExtra(START_FOR_FIRST_SETUP, true);
        context.startActivity(intent);
    }

    public static void startForResult(@NonNull Activity activity, int requestCode) {
        activity.startActivityForResult(new Intent(activity, FilterActivity.class), requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.ac_filter);

        if (getIntent().hasExtra(START_FOR_FIRST_SETUP)) {
            mIsForFirstSetup = true;
        }
    }

    @Override
    public void setViewModel(@NonNull FilterViewModel viewModel) {
        viewModel.isForFirstSetup.set(mIsForFirstSetup);
        mBinding.setViewModel(viewModel);
        mBinding.setListener(this);
        mBinding.executePendingBindings();
    }

    @DebugLog
    @Override
    public void setFilterWasChanged(boolean value) {
        setResult(value ? Activity.RESULT_OK : Activity.RESULT_CANCELED);
    }

    @DebugLog
    @Override
    public void onClick(View v) {
        TrendsActivity.start(this);
        finish();
    }
}
