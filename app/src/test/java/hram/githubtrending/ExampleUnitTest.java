package hram.githubtrending;

import java.util.List;

import hram.githubtrending.data.model.Repository;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private List<Repository> mRepositories;

//    @Test
//    public void testRequest() throws InterruptedException {
//
//        final OkHttpClient okHttpClient = new OkHttpClient();
//
//        final Trending trending = new RetroJsoup.Builder()
//                .url("https://github.com/trending/java")
//                //.client(okHttpClient)
//                .build()
//                .create(Trending.class);
//
//        trending.getRepositories()
//                .toList()
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(list -> mRepositoriesViewModel = list);
//
//        Thread.sleep(1000);
//
//
//        assertThat(mRepositoriesViewModel.size(), is(25));
//
//        Repository first = mRepositoriesViewModel.get(0);
//
//        assertThat(first.getUser(), is("alibaba /"));
//        assertThat(first.getTitle(), is("alibaba / vlayout"));
//        assertThat(first.getHref(), is("/alibaba/vlayout"));
//        assertThat(first.getDescription(), is("Project vlayout... "));
//        assertThat(first.getAllStars(), is("737"));
//        assertThat(first.getForks(), is("82"));
//        assertThat(first.getStarsToday(), is("190 stars today"));
//    }
}