package hram.githubtrending.trends;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import hram.githubtrending.R;
import hram.githubtrending.databinding.ActivityMainBinding;
import hram.githubtrending.viewmodel.RepositoriesViewModel;
import hram.githubtrending.viewmodel.RepositoryViewModel;

public class TrendsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, TrendsView {

    RepositoriesViewModel mRepositoriesViewModel;

    ActivityMainBinding mBinding;

    private TrendsPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mRepositoriesViewModel = new RepositoriesViewModel();
        mBinding.setViewModel(mRepositoriesViewModel);
        mBinding.executePendingBindings();

        mBinding.refreshLayout.setOnRefreshListener(this);
        mBinding.refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        mPresenter = new TrendsPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.attachView(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.detachView();
    }

    @Override
    public void onRefresh() {
        mPresenter.refresh();
    }

    @Override
    public void setRepositories(@NonNull List<RepositoryViewModel> list) {
        mRepositoriesViewModel.setItems(list);
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        mBinding.refreshLayout.setRefreshing(refreshing);
    }
}
