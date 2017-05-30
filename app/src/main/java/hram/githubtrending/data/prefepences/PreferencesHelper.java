package hram.githubtrending.data.prefepences;

import android.support.annotation.NonNull;

import com.orhanobut.hawk.Hawk;

import hram.githubtrending.data.model.SearchParams;

/**
 * @author Evgeny Khramov
 */

public class PreferencesHelper {

    @NonNull
    public SearchParams getSearchParams() {
        return Hawk.get(SearchParams.class.getName(), SearchParams.createEmpty());
    }

    public void setSearchParams(@NonNull SearchParams params) {
        Hawk.put(SearchParams.class.getName(), params);
    }
}
