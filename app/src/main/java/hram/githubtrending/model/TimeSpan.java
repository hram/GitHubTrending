package hram.githubtrending.model;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Evgeny Khramov
 */
@Retention(RetentionPolicy.SOURCE)
@StringDef({TimeSpan.DAILY, TimeSpan.WEEKLY, TimeSpan.MONTHLY})
public @interface TimeSpan {
    String DAILY = "daily";
    String WEEKLY = "weekly";
    String MONTHLY = "monthly";
}
