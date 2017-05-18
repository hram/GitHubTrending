package hram.githubtrending.trends;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import hram.githubtrending.R;
import hram.githubtrending.databinding.ActivityMainBinding;
import hram.githubtrending.viewmodel.RepositoriesViewModel;
import hram.githubtrending.viewmodel.RepositoryViewModel;

public class TrendsActivity extends MvpAppCompatActivity implements TrendsView {

    ActivityMainBinding mBinding;

    @InjectPresenter
    TrendsPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.refreshLayout.setOnRefreshListener(() -> mPresenter.refresh());
        mBinding.refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mBinding.buttonRefresh.setOnClickListener(v -> mPresenter.refresh());
    }

    @Override
    public void setViewModel(RepositoriesViewModel viewModel) {
        mBinding.setViewModel(viewModel);
        mBinding.executePendingBindings();
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        mBinding.refreshLayout.setRefreshing(refreshing);
    }

    @Override
    public void attachToRecyclerView(@NonNull ItemTouchHelper itemTouchHelper) {
        itemTouchHelper.attachToRecyclerView(mBinding.recyclerview);
    }

    @Override
    public void sowRemoveUndo(@NonNull RepositoryViewModel viewModel, int position, @NonNull String text) {
        Snackbar.make(mBinding.recyclerview, text, Snackbar.LENGTH_LONG)
                .setAction("отменить", view -> mPresenter.onUndoRemove(viewModel, position))
                .show();
    }
}
