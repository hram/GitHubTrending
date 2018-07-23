package hram.githubtrending.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import java.util.List;

import hram.githubtrending.data.model.TimeSpan;
import io.reactivex.Flowable;

/**
 * @author Dmitry Konurov
 */

@Dao
public abstract class TimeSpanDao {

    @Query("DELETE FROM " + TimeSpan.TABLE_NAME)
    public abstract int clear();

    @NonNull
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract List<Long> saveDb(List<TimeSpan> timeSpans);

    @Transaction
    public void updateTimeSpan(List<TimeSpan> timeSpans) {
        clear();
        saveDb(timeSpans);
    }

    @NonNull
    @Query("SELECT * FROM " + TimeSpan.TABLE_NAME)
    public abstract Flowable<List<TimeSpan>> observableAll();

    @VisibleForTesting
    @Nullable
    @Query("SELECT * FROM " + TimeSpan.TABLE_NAME
            + " WHERE mHref = :href")
    protected abstract TimeSpan findById(String href);

}