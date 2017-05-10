package hram.githubtrending;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.florent37.retrojsoup.RetroJsoup;

import java.util.ArrayList;
import java.util.List;

import hram.githubtrending.databinding.ActivityMainBinding;
import hram.githubtrending.model.RepositoryModel;
import hram.githubtrending.model.Trending;
import hram.githubtrending.viewmodel.RepositoriesViewModel;
import hram.githubtrending.viewmodel.RepositoryViewModel;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TrendsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    RepositoriesViewModel mRepositoriesViewModel;

    ActivityMainBinding mBinding;

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
    }

    private SingleSource<List<RepositoryViewModel>> mapToViewModel(List<RepositoryModel> list) {
        final List<RepositoryViewModel> items = new ArrayList<>(list.size());
        for (RepositoryModel model : list) {
            items.add(RepositoryViewModel.create(model));
        }
        return Single.just(items);
    }

    @Override
    public void onRefresh() {
        final Trending trending = new RetroJsoup.Builder()
                .url("https://github.com/trending/java")
                //.client(okHttpClient)
                .build()
                .create(Trending.class);

        trending.getJava()
                .toList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(this::mapToViewModel)
                .subscribe(this::handleResult, this::handleError);
    }

    private void handleResult(List<RepositoryViewModel> list) {
        mBinding.refreshLayout.setRefreshing(false);
        mRepositoriesViewModel.setItems(list);
    }

    private void handleError(Throwable throwable) {
        Log.e("TrendsActivity", throwable.getMessage());
    }
}
