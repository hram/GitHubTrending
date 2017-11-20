package hram.githubtrending.selectlanguage;

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
import hram.githubtrending.databinding.FrSelectLanguageBinding;
import hram.githubtrending.selecttimespan.SelectTimeSpanActivity;
import hram.githubtrending.viewmodel.SelectLanguageViewModel;

/**
 * @author Evgeny Khramov
 */
public class SelectLanguageFragment extends MvpAppCompatFragment implements SelectLanguageView, View.OnClickListener {

    private FrSelectLanguageBinding mBinding;

    @InjectPresenter
    SelectLanguagePresenter mPresenter;

    public static void start(@NonNull Context context) {
        context.startActivity(new Intent(context, SelectLanguageFragment.class));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fr_select_language, container, false);
        mBinding.recyclerview.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mBinding.fastScroller.setRecyclerView(mBinding.recyclerview);
        return mBinding.getRoot();
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
        SelectTimeSpanActivity.start(getActivity());
        getActivity().finish();
    }
}
