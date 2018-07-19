package hram.githubtrending.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import hram.githubtrending.data.model.Language;
import hram.githubtrending.data.model.Repository;
import hram.githubtrending.data.model.TimeSpan;

/**
 * @author Dmitry Konurov
 */

@Database(entities = {Repository.class, Language.class, TimeSpan.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "apdatabase";

    public abstract RepositoryDao repositoryDao();

    public abstract LanguageDao languageDao();

    public abstract TimeSpanDao timeSpanDao();
}