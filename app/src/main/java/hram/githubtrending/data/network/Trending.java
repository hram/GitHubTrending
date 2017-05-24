package hram.githubtrending.data.network;

import com.github.florent37.retrojsoup.annotations.Select;

import hram.githubtrending.data.model.Language;
import hram.githubtrending.data.model.Repository;
import hram.githubtrending.data.model.TimeSpan;
import io.reactivex.Observable;

/**
 * @author hram on 05.03.2017.
 */
public interface Trending {

    @Select(".repo-list .col-12")
    Observable<Repository> getRepositories();

    @Select(".column.one-fourth .select-menu-item")
    Observable<Language> getLanguages();

    @Select(".column.three-fourths .select-menu-item")
    Observable<TimeSpan> getTimeSpans();
}
