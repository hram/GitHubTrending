package hram.githubtrending.utils;

import android.support.annotation.NonNull;

import com.crashlytics.android.Crashlytics;

import hram.githubtrending.BuildConfig;

/**
 * @author Evgeny Khramov
 */
public class FabricUtil {

    public static void logException(@NonNull Throwable throwable) {
        if (!BuildConfig.DEBUG) {
            Crashlytics.logException(throwable);
        }
    }

    @NonNull
    public static Throwable createEmptyDataException() {
        return new EmptyDataException();
    }

    private static class EmptyDataException extends Exception {

    }
}
