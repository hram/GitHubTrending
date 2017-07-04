package hram.githubtrending.data.prefepences;

import android.support.annotation.NonNull;

import com.orhanobut.hawk.Hawk;

import hram.githubtrending.data.model.SearchParams;

/**
 * @author Evgeny Khramov
 */

public class HawkPreferencesHelper implements PreferencesHelper {

    @Override
    @NonNull
    public SearchParams getSearchParams() {
        return Hawk.get(SearchParams.class.getName(), SearchParams.createEmpty());
    }

    @Override
    public void setSearchParams(@NonNull SearchParams params) {
        Hawk.put(SearchParams.class.getName(), params);
    }


    @Override
    public void resetSearchParams() {
        Hawk.put(SearchParams.class.getName(), SearchParams.createEmpty());
    }

    @Override
    public void clearAppData() {
        Hawk.clear();
    }
}
