package hram.githubtrending.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.github.florent37.retrojsoup.annotations.JsoupHref;
import com.github.florent37.retrojsoup.annotations.JsoupText;

import org.jetbrains.annotations.NotNull;

/**
 * @author Dmitry Konurov
 */

@Entity(tableName = Language.TABLE_NAME, indices = @Index(value = "mName"))
public class Language {

    public static final String TABLE_NAME = "Language";

    @JsoupHref("a")
    @PrimaryKey
    @NotNull
    protected String mHref;

    @JsoupText(".select-menu-item-text")
    protected String mName;

    public Language() {

    }

    @NonNull
    public String getHref() {
        return mHref;
    }

    public void setHref(String href) {
        mHref = href;
    }

    @NonNull
    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    @Override
    public String toString() {
        return "Language{" +
                "mHref='" + mHref + '\'' +
                ", mName='" + mName + '\'' +
                '}';
    }
}
