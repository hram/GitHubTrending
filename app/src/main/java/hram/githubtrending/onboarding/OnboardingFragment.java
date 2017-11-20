package hram.githubtrending.onboarding;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hram.githubtrending.R;

/**
 * @author Evgeny Khramov
 */
public class OnboardingFragment extends android.support.v17.leanback.app.OnboardingFragment {

    private static final int[] pageTitles = {R.string.adjust_language, R.string.adjust_time_span};

    private static final int[] pageDescriptions = {R.string.adjust_language_description, R.string.adjust_time_span_description};

    @Override
    protected int getPageCount() {
        return pageTitles.length;
    }

    @Override
    protected CharSequence getPageTitle(int pageIndex) {
        return getString(pageTitles[pageIndex]);
    }

    @Override
    protected CharSequence getPageDescription(int pageIndex) {
        return getString(pageDescriptions[pageIndex]);
    }

    @Nullable
    @Override
    protected View onCreateBackgroundView(LayoutInflater inflater, ViewGroup container) {
        View bgView = inflater.inflate(R.layout.fr_onboarding, container, false);

//        View bgView = new View(getActivity());
//        // TODO
//        bgView.setBackgroundColor(getResources().getColor(R.color.fastlane_background));
        return bgView;
    }

    @Nullable
    @Override
    protected View onCreateContentView(LayoutInflater inflater, ViewGroup container) {
        return null;
    }

    @Nullable
    @Override
    protected View onCreateForegroundView(LayoutInflater inflater, ViewGroup container) {
        return null;
    }

    @Override
    protected void onPageChanged(final int newPage, int previousPage) {
    }
}
