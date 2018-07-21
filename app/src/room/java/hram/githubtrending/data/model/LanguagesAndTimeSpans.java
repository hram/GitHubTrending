package hram.githubtrending.data.model;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * @author Dmitry Konurov
 */

public class LanguagesAndTimeSpans {

    private final List<Language> mLanguages;

    private final List<TimeSpan> mTimeSpans;

    public LanguagesAndTimeSpans(@NonNull List<Language> languages, @NonNull List<TimeSpan> timeSpans) {
        mLanguages = languages;
        mTimeSpans = timeSpans;
    }

    @NonNull
    public List<Language> getLanguages() {
        return mLanguages;
    }

    @NonNull
    public List<TimeSpan> getTimeSpans() {
        return mTimeSpans;
    }
}
