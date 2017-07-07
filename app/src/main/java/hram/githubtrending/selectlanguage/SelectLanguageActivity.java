package hram.githubtrending.selectlanguage;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.view.View;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import hram.githubtrending.R;
import hram.githubtrending.databinding.AcSelectLanguageBinding;
import hram.githubtrending.selecttimespan.SelectTimeSpanActivity;
import hram.githubtrending.viewmodel.SelectLanguageViewModel;

/**
 * @author Evgeny Khramov
 */
public class SelectLanguageActivity extends MvpAppCompatActivity implements SelectLanguageView, View.OnClickListener {

    private AcSelectLanguageBinding mBinding;

    @InjectPresenter
    SelectLanguagePresenter mPresenter;

    public static void start(@NonNull Context context) {
        context.startActivity(new Intent(context, SelectLanguageActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.ac_select_language);
        mBinding.recyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mBinding.fastScroller.setRecyclerView(mBinding.recyclerview);
    }

    @Override
    public void setViewModel(@NonNull SelectLanguageViewModel viewModel) {
        mBinding.fastScroller.setUpAlphabet(viewModel.alphabetItems);
        mBinding.setViewModel(viewModel);
        mBinding.setListener(this);
        mBinding.executePendingBindings();
    }

    @Override
    public void onClick(View v) {
        SelectTimeSpanActivity.start(this);
        finish();
    }
}
