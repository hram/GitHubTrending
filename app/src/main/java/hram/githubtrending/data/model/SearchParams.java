package hram.githubtrending.data.model;

import android.support.annotation.NonNull;

/**
 * @author Evgeny Khramov
 */

public class SearchParams {

    private String mLanguage;

    private String mTimeSpan;

    public SearchParams(@NonNull String language, @NonNull String timeSpan) {
        mLanguage = language;
        mTimeSpan = timeSpan;
    }

    @NonNull
    public String getLanguage() {
        return mLanguage;
    }

    @NonNull
    public String getTimeSpan() {
        return mTimeSpan;
    }
}
