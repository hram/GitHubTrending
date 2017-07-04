package hram.githubtrending.data.prefepences;

import android.support.annotation.NonNull;

import hram.githubtrending.data.model.SearchParams;

/**
 * @author Evgeny Khramov
 */

public interface PreferencesHelper {

    @NonNull
    SearchParams getSearchParams();

    void setSearchParams(@NonNull SearchParams params);

    void resetSearchParams();

    void clearAppData();
}
