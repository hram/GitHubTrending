package hram.githubtrending.filter;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import hram.githubtrending.R;
import hram.githubtrending.databinding.AcFilterBinding;
import hram.githubtrending.viewmodel.FilterViewModel;

public class FilterActivity extends MvpAppCompatActivity implements FilterView {

    private AcFilterBinding mBinding;

    @InjectPresenter
    FilterPresenter mPresenter;

    public static void startForResult(@NonNull Activity activity, int requestCode) {
        activity.startActivityForResult(new Intent(activity, FilterActivity.class), requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.ac_filter);
    }

    @Override
    public void setViewModel(@NonNull FilterViewModel viewModel) {
        mBinding.setViewModel(viewModel);
        mBinding.executePendingBindings();
    }

    @Override
    public void setFilterWasChanged(boolean value) {
        setResult(value ? Activity.RESULT_OK : Activity.RESULT_CANCELED);
    }
}
