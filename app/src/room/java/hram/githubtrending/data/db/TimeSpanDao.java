package hram.githubtrending.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.support.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import hram.githubtrending.data.model.TimeSpan;
import io.reactivex.Flowable;

/**
 * @author Dmitry Konurov
 */

@Dao
public abstract class TimeSpanDao {

    @NonNull
    @Query("DELETE FROM " + TimeSpan.TABLE_NAME)
    public abstract int clear();

    @NonNull
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract List<Long> saveDb(List<TimeSpan> timeSpans);

    @Transaction
    @NotNull
    public Flowable<List<TimeSpan>> updateTimeSpan(List<TimeSpan> timeSpans) {
        clear();
        saveDb(timeSpans);
        return Flowable.just(timeSpans);
    }

    @NonNull
    @Query("SELECT * FROM " + TimeSpan.TABLE_NAME)
    public abstract Flowable<List<TimeSpan>> observableAll();

}
