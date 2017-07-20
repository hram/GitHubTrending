package hram.githubtrending.data.prefepences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import hram.githubtrending.App;
import hram.githubtrending.data.model.SearchParams;

/**
 * @author Evgeny Khramov
 */
public class SharedPreferencesHelper implements PreferencesHelper {

    private static final String FILE_NAME = "preferences";

    @NonNull
    private static final Gson GSON = new Gson();

    @Override
    @NonNull
    public SearchParams getSearchParams() {
        return GSON.fromJson(pref().getString(SearchParams.class.getName(), GSON.toJson(SearchParams.createEmpty())), SearchParams.class);
    }

    @Override
    public void setSearchParams(@NonNull SearchParams params) {
        pref().edit().putString(SearchParams.class.getName(), GSON.toJson(params)).apply();
    }


    @Override
    public void resetSearchParams() {
        setSearchParams(SearchParams.createEmpty());
    }

    @Override
    public void clearAppData() {
        pref().edit().clear().apply();
    }

    private static SharedPreferences pref() {
        return App.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }
}
