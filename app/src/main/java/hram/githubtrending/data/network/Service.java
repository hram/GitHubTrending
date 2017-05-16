package hram.githubtrending.data.network;

import com.github.florent37.retrojsoup.annotations.Select;

import hram.githubtrending.data.model.Language;
import hram.githubtrending.data.model.Repository;
import io.reactivex.Observable;

/**
 * @author Evgeny Khramov
 */
public interface Service {

    @Select(".repo-list .col-12")
    Observable<Repository> getRepositories();

    @Select(".column.one-fourth .select-menu-item")
    Observable<Language> getLanguages();
}
