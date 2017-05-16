package hram.githubtrending.data.model;

import android.support.annotation.NonNull;

import com.github.florent37.retrojsoup.annotations.JsoupHref;
import com.github.florent37.retrojsoup.annotations.JsoupText;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author Evgeny Khramov
 */

public class Repository extends RealmObject {

    @JsoupText(".text-normal")
    String mUser;

    @JsoupText(".d-inline-block a")
    String mTitle;

    @PrimaryKey
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

    String mLanguage;

    String mTimeSpan;

    public String getUser() {
        return mUser;
    }

    public void setUser(@NonNull String user) {
        mUser = user;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(@NonNull String title) {
        mTitle = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(@NonNull String href) {
        this.href = href;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(@NonNull String description) {
        mDescription = description;
    }

    public String getAllStars() {
        return mAllStars;
    }

    public void setAllStars(@NonNull String allStars) {
        mAllStars = allStars;
    }

    public String getForks() {
        return mForks;
    }

    public void setForks(@NonNull String forks) {
        mForks = forks;
    }

    public String getStarsToday() {
        return mStarsToday;
    }

    public void setStarsToday(@NonNull String starsToday) {
        mStarsToday = starsToday;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public void setLanguage(@NonNull String language) {
        mLanguage = language;
    }

    public String getTimeSpan() {
        return mTimeSpan;
    }

    public void setTimeSpan(@NonNull String timeSpan) {
        mTimeSpan = timeSpan;
    }

    @Override
    public String toString() {
        return "Repository{" +
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
