package hram.githubtrending.model;

import android.support.annotation.NonNull;

import com.github.florent37.retrojsoup.annotations.JsoupHref;
import com.github.florent37.retrojsoup.annotations.JsoupText;

/**
 * @author Evgeny Khramov
 */

public class LanguageModel {

    @JsoupHref("a")
    String href;

    @JsoupText(".select-menu-item-text")
    String mName;

    @NonNull
    public String getHref() {
        return href;
    }

    @NonNull
    public String getName() {
        return mName;
    }

    @Override
    public String toString() {
        return "LanguageModel{" +
                "mName='" + mName + '\'' +
                '}';
    }
}
