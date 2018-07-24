package hram.githubtrending.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import hram.githubtrending.data.model.Language;
import io.reactivex.Flowable;

/**
 * @author Dmitry Konurov
 */

@Dao
public abstract class LanguageDao {

    @NonNull
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract List<Long> saveDb(@NotNull List<Language> languages);

    @Nullable
    @VisibleForTesting
    @Query("SELECT * FROM " + Language.TABLE_NAME +
            " WHERE mHref = :href")
    protected abstract Language findById(String href);

    @NonNull
    @Query("SELECT * FROM " + Language.TABLE_NAME)
    public abstract Flowable<List<Language>> observableAll();

    @Query("DELETE FROM " + Language.TABLE_NAME)
    public abstract int clear();

    @Transaction
    public void updateLanguage(@NotNull List<Language> list) {
        clear();
        saveDb(list);
    }
}