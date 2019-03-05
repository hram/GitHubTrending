package hram.githubtrending;

import org.junit.Test;

import java.util.List;

import hram.githubtrending.data.model.Language;
import hram.githubtrending.data.model.Repository;
import hram.githubtrending.data.model.TimeSpan;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;


/**
 * @author evgeny.khramov
 */
public class TrendingTest extends BaseTest {

    @Test
    public void testRepositories() throws InterruptedException {

        final List<Repository> repositories = getRepositories("https://github.com/trending/java");

        assertThat(repositories.size(), is(25));

        //Repository first = mRepositories.get(0);

        //assertThat(first.getUser(), is("harjot-oberai /"));
        //assertThat(first.getTitle(), is("harjot-oberai / VectorMaster"));
        //assertThat(first.getHref(), is("/harjot-oberai/VectorMaster"));
        //assertThat(first.getDescription(), is("Dynamic control over vector drawables!"));
        //assertThat(first.getAllStars(), is("544"));
        //assertThat(first.getForks(), is("39"));
//        assertThat(first.getStarsToday(), is("214 stars today"));

        for (Repository repository : repositories) {
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

        final List<Language> languages = getLanguages("https://github.com/trending");

        assertThat(languages.size(), is(493));

        assertThat(languages.get(0).getHref(), is("https://github.com/trending/1c-enterprise"));
        assertThat(languages.get(0).getName(), is("1C Enterprise"));

        assertThat(languages.get(100).getHref(), is("https://github.com/trending/dogescript"));
        assertThat(languages.get(100).getName(), is("Dogescript"));

        assertThat(languages.get(200).getHref(), is("https://github.com/trending/jasmin"));
        assertThat(languages.get(200).getName(), is("Jasmin"));
    }

    @Test
    public void testTimeSpans() throws InterruptedException {

        final List<TimeSpan> timeSpans = getTimeSpans("https://github.com/trending");

        assertThat(timeSpans.size(), is(3));

        assertThat(timeSpans.get(0).getHref(), is("https://github.com/trending?since=daily"));
        assertThat(timeSpans.get(0).getName(), is("today"));

        assertThat(timeSpans.get(1).getHref(), is("https://github.com/trending?since=weekly"));
        assertThat(timeSpans.get(1).getName(), is("this week"));

        assertThat(timeSpans.get(2).getHref(), is("https://github.com/trending?since=monthly"));
        assertThat(timeSpans.get(2).getName(), is("this month"));
    }
}