package hram.githubtrending;

import com.github.florent37.retrojsoup.RetroJsoup;

import org.junit.Test;

import java.util.List;

import hram.githubtrending.data.model.Language;
import hram.githubtrending.data.model.Repository;
import hram.githubtrending.data.model.TimeSpan;
import hram.githubtrending.data.network.Trending;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;


/**
 * @author evgeny.khramov
 */
public class TrendingTest {

    private List<Repository> mRepositories;

    private List<Language> mLanguages;

    private List<TimeSpan> mTimeSpans;

    @Test
    public void testRepositories() throws InterruptedException {

        final Trending trending = new RetroJsoup.Builder()
                .url("https://github.com/trending/java")
                .build()
                .create(Trending.class);

        trending.getRepositories()
                .toList()
                .subscribe(list -> mRepositories = list);

        assertThat(mRepositories.size(), is(25));

        //Repository first = mRepositories.get(0);

        //assertThat(first.getUser(), is("harjot-oberai /"));
        //assertThat(first.getTitle(), is("harjot-oberai / VectorMaster"));
        //assertThat(first.getHref(), is("/harjot-oberai/VectorMaster"));
        //assertThat(first.getDescription(), is("Dynamic control over vector drawables!"));
        //assertThat(first.getAllStars(), is("544"));
        //assertThat(first.getForks(), is("39"));
//        assertThat(first.getStarsToday(), is("214 stars today"));

        for (Repository repository : mRepositories) {
            assertThat(repository.getTitle(), repository.getUser(), is(not(equalTo(""))));
            assertThat(repository.getTitle(), repository.getTitle(), is(not(equalTo(""))));
            assertThat(repository.getTitle(), repository.getHref(), is(not(equalTo(""))));
            assertThat(repository.getTitle(), repository.getDescription(), is(not(equalTo(""))));
            assertThat(repository.getTitle(), repository.getAllStars(), is(not(equalTo(""))));
            assertThat(repository.getTitle(), repository.getForks(), is(not(equalTo(""))));
            assertThat(repository.getTitle(), repository.getStarsToday(), is(not(equalTo(""))));
            try {
                assertThat(repository.getTitle(), Integer.parseInt(repository.getStarsToday().split("\\s+")[0].replaceAll(",", "")), greaterThan(0));
            } catch (NumberFormatException e) {
                fail(repository.getTitle() + " " + repository.getStarsToday());
            }
        }
    }

    @Test
    public void testLanguages() throws InterruptedException {

        final Trending trending = new RetroJsoup.Builder()
                .url("https://github.com/trending")
                .build()
                .create(Trending.class);

        trending.getLanguages()
                .toList()
                .subscribe(list -> mLanguages = list);

        assertThat(mLanguages.size(), is(451));

        assertThat(mLanguages.get(0).getHref(), is("https://github.com/trending/1c-enterprise"));
        assertThat(mLanguages.get(0).getName(), is("1C Enterprise"));

        assertThat(mLanguages.get(100).getHref(), is("https://github.com/trending/ec"));
        assertThat(mLanguages.get(100).getName(), is("eC"));

        assertThat(mLanguages.get(200).getHref(), is("https://github.com/trending/kotlin"));
        assertThat(mLanguages.get(200).getName(), is("Kotlin"));
    }

    @Test
    public void testTimeSpans() throws InterruptedException {

        final Trending trending = new RetroJsoup.Builder()
                .url("https://github.com/trending")
                .build()
                .create(Trending.class);

        trending.getTimeSpans()
                .toList()
                .subscribe(list -> mTimeSpans = list);

        assertThat(mTimeSpans.size(), is(3));

        assertThat(mTimeSpans.get(0).getHref(), is("https://github.com/trending?since=daily"));
        assertThat(mTimeSpans.get(0).getName(), is("today"));

        assertThat(mTimeSpans.get(1).getHref(), is("https://github.com/trending?since=weekly"));
        assertThat(mTimeSpans.get(1).getName(), is("this week"));

        assertThat(mTimeSpans.get(2).getHref(), is("https://github.com/trending?since=monthly"));
        assertThat(mTimeSpans.get(2).getName(), is("this month"));
    }
}