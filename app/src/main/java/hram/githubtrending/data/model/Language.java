package hram.githubtrending.data.model;

import android.support.annotation.NonNull;

import com.github.florent37.retrojsoup.annotations.JsoupHref;
import com.github.florent37.retrojsoup.annotations.JsoupText;

import fr.xebia.android.freezer.annotations.Model;
import io.realm.RealmObject;

/**
 * @author Evgeny Khramov
 */
@Model
public class Language extends RealmObject {

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
        return "Language{" +
                "href='" + href + '\'' +
                ", mName='" + mName + '\'' +
                '}';
    }
}
