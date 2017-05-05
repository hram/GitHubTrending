package hram.githubtrending.model;

import com.github.florent37.retrojsoup.annotations.Select;

import io.reactivex.Observable;

/**
 * @author hram on 05.03.2017.
 */
public interface Trending {

    @Select(".repo-list .col-12")
    Observable<RepositoryModel> getJava();
}
