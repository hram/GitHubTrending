package hram.githubtrending;

import android.support.annotation.NonNull;

import hram.githubtrending.data.model.SearchParams;
import hram.githubtrending.data.prefepences.PreferencesHelper;

/**
 * @author Evgeny Khramov
 */

public class SharedPreferencesHelper implements PreferencesHelper {

    @NonNull
    @Override
    public SearchParams getSearchParams() {
        return new SearchParams("java", "daily");
    }

    @Override
    public void setSearchParams(@NonNull SearchParams params) {
        // do nothing
    }

    @Override
    public void resetSearchParams() {
        // do nothing
    }

    @Override
    public void clearAppData() {
        // do nothing
    }
}
