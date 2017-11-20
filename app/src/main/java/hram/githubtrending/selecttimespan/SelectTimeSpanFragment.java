package hram.githubtrending.selecttimespan;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import hram.githubtrending.R;
import hram.githubtrending.databinding.FrSelectTimeSpanBinding;
import hram.githubtrending.trends.TrendsActivity;
import hram.githubtrending.viewmodel.SelectTimeSpanViewModel;

/**
 * @author Evgeny Khramov
 */
public class SelectTimeSpanFragment extends MvpAppCompatFragment implements SelectTimeSpanView, View.OnClickListener {

    private FrSelectTimeSpanBinding mBinding;

    @InjectPresenter
    SelectTimeSpanPresenter mPresenter;

    public static void start(@NonNull Context context) {
        context.startActivity(new Intent(context, SelectTimeSpanFragment.class));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fr_select_time_span, container, false);
        mBinding.recyclerview.addItemDecoration(new DividerItemDecoration(inflater.getContext(), DividerItemDecoration.VERTICAL));
        return mBinding.getRoot();
    }

    @Override
    public void setViewModel(@NonNull SelectTimeSpanViewModel viewModel) {
        mBinding.setViewModel(viewModel);
        mBinding.setListener(this);
        mBinding.executePendingBindings();
    }

    @Override
    public void onClick(View v) {
        TrendsActivity.start(v.getContext());
        getActivity().finish();
    }
}
