package hram.githubtrending;

import com.github.florent37.retrojsoup.RetroJsoup;

import org.junit.Test;

import java.util.List;

import hram.githubtrending.model.Repository;
import hram.githubtrending.model.Trending;
import okhttp3.OkHttpClient;
import rx.observers.TestSubscriber;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testRequest() {

        final OkHttpClient okHttpClient = new OkHttpClient();

        final Trending trending = new RetroJsoup.Builder()
                .url("https://github.com/trending/java")
                //.client(okHttpClient)
                .build()
                .create(Trending.class);

        TestSubscriber<List<Repository>> testSubscriber = new TestSubscriber<>();

        trending.getJava()
                .toList()
                .subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);
        List<List<Repository>> repositories = testSubscriber.getOnNextEvents();
        assertThat(repositories.get(0).size(), is(25));

        Repository first = repositories.get(0).get(0);

        assertThat(first.getUser(), is("alibaba /"));
        assertThat(first.getTitle(), is("alibaba / vlayout"));
        assertThat(first.getHref(), is("/alibaba/vlayout"));
        assertThat(first.getDescription(), is("Project vlayout is a powerfull LayoutManager extension for RecyclerView, it provides a group of layouts for RecyclerView. Make it able to handle a complicate situation when grid, list and other layouts in the same recyclerview."));
        assertThat(first.getAllStars(), is("737"));
        assertThat(first.getForks(), is("82"));
        assertThat(first.getStarsToday(), is("190 stars today"));

        //testSubscriber.assertReceivedOnNext(Arrays.asList(user1, user2))
    }
}