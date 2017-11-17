package hram.githubtrending.data.db;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import hram.githubtrending.data.model.Language;
import hram.githubtrending.data.model.Repository;
import hram.githubtrending.data.model.TimeSpan;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * @author Evgeny Khramov
 */
public class RealtimeDatabaseHelper implements DatabaseHelper {

    private final FirebaseDatabase mFirebaseInstance;

    public RealtimeDatabaseHelper(@NonNull FirebaseDatabase database) {
        mFirebaseInstance = database;
        mFirebaseInstance.setPersistenceEnabled(false);
    }

    public RealtimeDatabaseHelper() {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        FirebaseOptions options = mFirebaseInstance.getApp().getOptions();
        Log.d("FirebaseOptions", "options: " + options);
        Log.d("FirebaseOptions", "app: " + mFirebaseInstance.getApp());
        mFirebaseInstance.setPersistenceEnabled(true);
    }

    @Override
    public Observable<List<Repository>> saveRepositories(@NonNull List<Repository> list, @NonNull String language, @NonNull String timeSpan) {
        return Observable.defer(() -> insertOrUpdateRepositories(list));
    }

    private Observable<List<Repository>> insertOrUpdateRepositories(@NonNull List<Repository> list) {
        return Observable.fromIterable(list)
                .filter(this::filterIfNotHided)
                .toList().toObservable()
                .map(this::saveRepositories);
    }

    private boolean filterIfNotHided(@NonNull Repository repository) {
        return getRepositoryObservable(repository.getKey())
                .flatMap(repository1 -> Observable.just(!repository1.isHided()))
                .blockingSingle(true);
    }

    public List<Repository> saveRepositories(@NonNull List<Repository> list) {
        DatabaseReference mDatabase = mFirebaseInstance.getReference("repositories");
        for (Repository repository : list) {
            mDatabase.child(repository.getKey()).setValue(repository);
        }
        return list;
    }

    @Override
    public void saveRepository(@NonNull Repository repository) {
        DatabaseReference mDatabase = mFirebaseInstance.getReference("repositories");
        mDatabase.child(repository.getKey()).setValue(repository);
    }

    @NonNull
    @Override
    public Observable<List<Repository>> getRepositoriesObservable(@NonNull String language, @NonNull String timeSpan) {
        return Observable.create(new ObservableOnSubscribe<List<Repository>>() {
            @Override
            public void subscribe(@io.reactivex.annotations.NonNull ObservableEmitter<List<Repository>> emitter) throws Exception {
                mFirebaseInstance.getReference("repositories").addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() == null) {
                            emitter.onNext(new ArrayList<>());
                        } else {
                            List<Repository> list = new ArrayList<>((int) dataSnapshot.getChildrenCount());
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                final Repository repository = postSnapshot.getValue(Repository.class);
                                if (repository == null || repository.isHided()) {
                                    continue;
                                }

                                list.add(repository);
                            }
                            emitter.onNext(list);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        if (!emitter.isDisposed()) {
                            emitter.onError(new Exception());
                        }
                    }
                });
            }
        });
    }

    @Override
    public Observable<List<Language>> saveLanguages(@NonNull List<Language> list) {
        return Observable.just(saveAndGetLanguages(list));
    }

    @NonNull
    private List<Language> saveAndGetLanguages(@NonNull List<Language> list) {
//        DatabaseReference mDatabase = mFirebaseInstance.getReference("languages");
//        for (Language language : list) {
//            mDatabase.child(mDatabase.push().getKey()).setValue(language);
//        }
        return list;
    }

    @NonNull
    @Override
    public Observable<List<Language>> getLanguagesObservable() {
        return Observable.create(new ObservableOnSubscribe<List<Language>>() {
            @Override
            public void subscribe(@io.reactivex.annotations.NonNull ObservableEmitter<List<Language>> emitter) throws Exception {
                mFirebaseInstance.getReference("languages").addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Map<String, Object> td = (HashMap<String,Object>) dataSnapshot.getValue();
                        //List<Object> values = td.values();

                        if (dataSnapshot.getValue() == null) {
                            emitter.onNext(new ArrayList<>());
                        } else {
                            List<Language> list = new ArrayList<>((int) dataSnapshot.getChildrenCount());
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                Language language = postSnapshot.getValue(Language.class);
                                list.add(language);
                            }
                            emitter.onNext(list);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        if (!emitter.isDisposed()) {
                            emitter.onError(new Exception());
                        }
                    }
                });
            }
        });
    }

    @Override
    public Observable<List<TimeSpan>> saveTimeSpans(@NonNull List<TimeSpan> list) {
        return Observable.just(saveAndGetTimeSpans(list));
    }

    @NonNull
    private List<TimeSpan> saveAndGetTimeSpans(@NonNull List<TimeSpan> list) {
//        DatabaseReference mDatabase = mFirebaseInstance.getReference("time_spans");
//        for (TimeSpan language : list) {
//            mDatabase.child(mDatabase.push().getKey()).setValue(language);
//        }
        return list;
    }

    @NonNull
    @Override
    public Observable<List<TimeSpan>> getTimeSpansObservable() {
        return Observable.create(new ObservableOnSubscribe<List<TimeSpan>>() {
            @Override
            public void subscribe(@io.reactivex.annotations.NonNull ObservableEmitter<List<TimeSpan>> emitter) throws Exception {
                mFirebaseInstance.getReference("time_spans").addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() == null) {
                            emitter.onNext(new ArrayList<>());
                        } else {
                            List<TimeSpan> list = new ArrayList<>((int) dataSnapshot.getChildrenCount());
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                TimeSpan language = postSnapshot.getValue(TimeSpan.class);
                                list.add(language);
                            }
                            emitter.onNext(list);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        if (!emitter.isDisposed()) {
                            emitter.onError(new Exception());
                        }
                    }
                });
            }
        });
    }

    @Override
    public Observable<Repository> getRepositoryObservable(@NonNull String id) {
        return Observable.create(new ObservableOnSubscribe<Repository>() {
            @Override
            public void subscribe(@io.reactivex.annotations.NonNull ObservableEmitter<Repository> emitter) throws Exception {
                mFirebaseInstance.getReference("repositories").child(id).addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            final Repository repository = dataSnapshot.getValue(Repository.class);
                            emitter.onNext(repository);
                        }
                        emitter.onComplete();
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        if (!emitter.isDisposed()) {
                            emitter.onError(new Exception());
                        }
                    }
                });
            }
        });
    }

    @Override
    public void clearAppData() {
        // do nothing
    }
}
