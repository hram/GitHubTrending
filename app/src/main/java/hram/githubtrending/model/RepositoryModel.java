package hram.githubtrending.model;

import android.support.annotation.NonNull;

import com.github.florent37.retrojsoup.annotations.JsoupHref;
import com.github.florent37.retrojsoup.annotations.JsoupText;

/**
 * @author Evgeny Khramov
 */
public class RepositoryModel {

    @JsoupText(".text-normal")
    String mUser;

    @JsoupText(".d-inline-block a")
    String mTitle;

    @JsoupHref(".d-inline-block a")
    String href;

    @JsoupText(".py-1 p")
    String mDescription;

    @JsoupText(".f6 :eq(2)")
    String mAllStars;

    @JsoupText(".f6 :eq(3)")
    String mForks;

    @JsoupText(".f6 :eq(5)")
    String mStarsToday;

    @NonNull
    public String getUser() {
        return mUser;
    }

    @NonNull
    public String getTitle() {
        return mTitle;
    }

    @NonNull
    public String getHref() {
        return href;
    }

    @NonNull
    public String getDescription() {
        return mDescription;
    }

    @NonNull
    public String getAllStars() {
        return mAllStars;
    }

    @NonNull
    public String getForks() {
        return mForks;
    }

    public String getStarsToday() {
        return mStarsToday;
    }

    @Override
    public String toString() {
        return "RepositoryModel{" +
                "mUser='" + mUser + '\'' +
                ", mTitle='" + mTitle + '\'' +
                ", href='" + href + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mAllStars='" + mAllStars + '\'' +
                ", mForks='" + mForks + '\'' +
                ", mStarsToday='" + mStarsToday + '\'' +
                '}';
    }
}
