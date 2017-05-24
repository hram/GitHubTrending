package hram.githubtrending.filter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import hram.githubtrending.R;

public class FilterActivity extends MvpAppCompatActivity implements FilterView {

    @InjectPresenter
    FilterPresenter mPresenter;

    public static void startForResult(Activity activity, int requestCode) {
        activity.startActivityForResult(new Intent(activity, FilterActivity.class), requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_filter);
    }
}
