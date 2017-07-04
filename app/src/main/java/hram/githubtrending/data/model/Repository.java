package hram.githubtrending.data.model;

import android.support.annotation.NonNull;

import com.github.florent37.retrojsoup.annotations.JsoupHref;
import com.github.florent37.retrojsoup.annotations.JsoupText;

import fr.xebia.android.freezer.annotations.Model;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author Evgeny Khramov
 */
@Model
public class Repository extends RealmObject {

    public static final String COLUMN_ID = "mHref";

    public static final String COLUMN_LANGUAGE = "mLanguage";

    public static final String COLUMN_TIME_SPAN = "mTimeSpan";

    public static final String COLUMN_HIDED = "mIsHided";

    public static final String COLUMN_ORDER = "mOrder";

    @JsoupText(".text-normal")
    String mUser;

    @JsoupText(".d-inline-block.col-9.mb-1 a")
    String mTitle;

    @PrimaryKey
    @JsoupHref(".d-inline-block.col-9.mb-1 a")
    String mHref;

    @JsoupText(".py-1 p")
    String mDescription;

    @JsoupText(".muted-link:eq(1)")
    String mAllStars;

    @JsoupText(".muted-link:eq(2)")
    String mForks;

    @JsoupText(".f6 .float-right")
    String mStarsToday;

    String mLanguage;

    String mTimeSpan;

    boolean mIsHided;

    int mOrder;

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
        return mHref;
    }

    public void setHref(@NonNull String href) {
        this.mHref = href;
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

    public boolean isHided() {
        return mIsHided;
    }

    public void setHided(boolean hided) {
        mIsHided = hided;
    }

    public int getOrder() {
        return mOrder;
    }

    public void setOrder(int order) {
        mOrder = order;
    }

    @Override
    public String toString() {
        return "Repository{" +
                "mUser='" + mUser + '\'' +
                ", mTitle='" + mTitle + '\'' +
                ", mHref='" + mHref + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mAllStars='" + mAllStars + '\'' +
                ", mForks='" + mForks + '\'' +
                ", mStarsToday='" + mStarsToday + '\'' +
                ", mLanguage='" + mLanguage + '\'' +
                ", mTimeSpan='" + mTimeSpan + '\'' +
                ", mIsHided=" + mIsHided +
                ", mOrder=" + mOrder +
                '}';
    }
}
