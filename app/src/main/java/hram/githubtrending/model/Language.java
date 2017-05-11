package hram.githubtrending.model;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Evgeny Khramov
 */
@Retention(RetentionPolicy.SOURCE)
@StringDef({Language.JAVA, Language.C})
public @interface Language {
    String JAVA = "java";
    String C = "c";
}
