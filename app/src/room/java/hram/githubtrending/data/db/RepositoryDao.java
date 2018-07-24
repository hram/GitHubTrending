package hram.githubtrending.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import hram.githubtrending.data.model.Repository;
import io.reactivex.Flowable;

/**
 * @author Dmitry Konurov
 */

@Dao
public abstract class RepositoryDao {

    @NonNull
    @Query("DELETE FROM " + Repository.TABLE_NAME +
            " WHERE mLanguage = :language AND mTimeSpan = :timeSpan AND isHided = 0")
    protected abstract int deleteDbByRelation(@NotNull String language, @NotNull String timeSpan);

    @NonNull
    @Query("SELECT * FROM " + Repository.TABLE_NAME +
            " WHERE mLanguage = :language AND mTimeSpan = :timeSpan AND isHided = 0 " +
            " ORDER BY mOrder ASC")
    public abstract Flowable<List<Repository>> observableByRelation(@NotNull String language, @NotNull String timeSpan);

    @NonNull
    @Query("SELECT * FROM " + Repository.TABLE_NAME +
            " WHERE mHref IN (:href) AND isHided = 0")
    public abstract Flowable<List<Repository>> observableByIds(@NotNull List<String> href);

    @NonNull
    @Query("SELECT * FROM " + Repository.TABLE_NAME +
            " WHERE mHref = :href")
    public abstract Flowable<Repository> observableById(@NotNull String href);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void save(@NotNull Repository... repository);

    @NonNull
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract List<Long> insertDb(@NotNull List<Repository> repositories);

    @Query("DELETE FROM " + Repository.TABLE_NAME)
    public abstract int clear();

    @VisibleForTesting
    @NotNull
    @Query("SELECT * FROM " + Repository.TABLE_NAME)
    protected abstract List<Repository> all();

    @Transaction
    @NotNull
    public List<String> updateRepositories(@NonNull List<Repository> list,
                                           @NonNull String language,
                                           @NonNull String timeSpan) {
        deleteDbByRelation(language, timeSpan);
        List<String> hrefList = new ArrayList<>();
        for (Repository repository : list) {
            repository.setLanguage(language);
            repository.setTimeSpan(timeSpan);
            hrefList.add(repository.getHref());
        }
        insertDb(list);
        return hrefList;
    }
}
