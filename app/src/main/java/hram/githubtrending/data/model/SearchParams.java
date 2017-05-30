package hram.githubtrending.data.model;

import android.support.annotation.NonNull;
import android.text.TextUtils;

/**
 * @author Evgeny Khramov
 */

public class SearchParams {

    private String mLanguage;

    private String mTimeSpan;

    @NonNull
    public static SearchParams createEmpty() {
        return new SearchParams("", "");
    }

    public SearchParams(@NonNull String language, @NonNull String timeSpan) {
        mLanguage = language;
        mTimeSpan = timeSpan;
    }

    @NonNull
    public String getLanguage() {
        return mLanguage;
    }

    public void setLanguage(@NonNull String language) {
        mLanguage = language;
    }

    @NonNull
    public String getTimeSpan() {
        return mTimeSpan;
    }

    public void setTimeSpan(@NonNull String timeSpan) {
        mTimeSpan = timeSpan;
    }

    public boolean isEmpty() {
        return TextUtils.isEmpty(mLanguage) || TextUtils.isEmpty(mTimeSpan);
    }
}
