package hram.githubtrending.viewmodel;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * @author Evgeny Khramov
 */
public class LanguagesAndTimeSpan {

    private List<LanguageViewModel> mLanguages;

    private List<TimeSpanViewModel> mTimeSpans;

    public LanguagesAndTimeSpan(@NonNull List<LanguageViewModel> languages, @NonNull List<TimeSpanViewModel> timeSpans) {
        mLanguages = languages;
        mTimeSpans = timeSpans;
    }

    @NonNull
    public List<LanguageViewModel> getLanguages() {
        return mLanguages;
    }

    @NonNull
    public List<TimeSpanViewModel> getTimeSpans() {
        return mTimeSpans;
    }
}
