package hram.githubtrending.trends;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import hram.githubtrending.R;
import hram.githubtrending.databinding.ActivityMainBinding;
import hram.githubtrending.filter.FilterActivity;
import hram.githubtrending.viewmodel.RepositoriesViewModel;
import hram.githubtrending.viewmodel.RepositoryViewModel;
import hugo.weaving.DebugLog;

public class TrendsActivity extends MvpAppCompatActivity implements TrendsView {

    ActivityMainBinding mBinding;

    @InjectPresenter
    TrendsPresenter mPresenter;

    @NonNull
    public static void start(@NonNull Context context) {
        context.startActivity(new Intent(context, TrendsActivity.class));
    }

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
        setSupportActionBar(mBinding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.trends, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_open_filter:
                FilterActivity.startForResult(this, TrendsPresenter.CHANGE_FILTER);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode, resultCode);
    }

    @Override
    public void setTitle(@NonNull String title) {
        mBinding.toolbarTitle.setText(title);
    }

    @Override
    public void setViewModel(@NonNull RepositoriesViewModel viewModel) {
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

    @Override
    public void openScreen(@NonNull Intent intent) {
        try {
            startActivity(intent);
        } catch (Exception e) {
            // do nothing
        }
    }

    @DebugLog
    @Override
    public void showProgress() {
        mBinding.progress.showLoading();
    }

    @DebugLog
    @Override
    public void showEmpty() {
        mBinding.progress.showEmpty(R.drawable.ic_star, "Хорошая работа!", "Поздравляю все прочитано");
    }

    @DebugLog
    @Override
    public void showError(@NonNull Throwable throwable) {
        mBinding.progress.showError(R.drawable.ic_star, "error", throwable.getMessage(), "обновить", this::refresh);
    }

    @DebugLog
    @Override
    public void showContent() {
        mBinding.progress.showContent();
    }

    private void refresh(View view) {
        mPresenter.refresh();
    }
}
