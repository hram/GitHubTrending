package hram.githubtrending.data.model;

import android.support.annotation.NonNull;

import com.github.florent37.retrojsoup.annotations.JsoupHref;
import com.github.florent37.retrojsoup.annotations.JsoupText;

import fr.xebia.android.freezer.annotations.Model;
import hram.githubtrending.BuildConfig;

/**
 * @author Evgeny Khramov
 */
@Model
public class Language {

    @JsoupHref("a")
    String href;

    @JsoupText("span")
    String mName;

    @NonNull
    public String getHref() {
        return BuildConfig.URL_BASE + href.substring(0, href.indexOf("?"));
    }

    @NonNull
    public String getName() {
        return mName;
    }

    @Override
    public String toString() {
        return "Language{" +
                "href='" + href + '\'' +
                ", mName='" + mName + '\'' +
                '}';
    }
}
