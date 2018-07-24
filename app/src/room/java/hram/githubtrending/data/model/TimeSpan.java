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

@Entity(tableName = TimeSpan.TABLE_NAME, indices = @Index(value = "mName"))
public class TimeSpan {

    public static final String TABLE_NAME = "TimeSpan";

    @JsoupHref("a")
    @PrimaryKey
    @NotNull
    String mHref;

    @JsoupText(".select-menu-item-text")
    String mName;

    public TimeSpan() {

    }

    @NonNull
    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    @NonNull
    public String getHref() {
        return mHref;
    }

    public void setHref(String href) {
        this.mHref = href;
    }

    @Override
    public String toString() {
        return "TimeSpan{" +
                "mHref='" + mHref + '\'' +
                ", mName='" + mName + '\'' +
                '}';
    }
}
