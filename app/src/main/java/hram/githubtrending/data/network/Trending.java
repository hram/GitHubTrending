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

    @Select(".repo-list")
    Observable<Repository> getRepositories();

    @Select("[data-filterable-for=text-filter-field]")
    Observable<Language> getLanguages();

    @Select(".select-menu.select-menu-modal-right")
    Observable<TimeSpan> getTimeSpans();
}
