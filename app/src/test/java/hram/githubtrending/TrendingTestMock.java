package hram.githubtrending;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import hram.githubtrending.data.model.Language;
import hram.githubtrending.data.model.Repository;
import hram.githubtrending.data.model.TimeSpan;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


/**
 * @author evgeny.khramov
 */
public class TrendingTestMock extends BaseMockTest {

    @Test
    public void testRepositories() throws InterruptedException, IOException {

        mServer.enqueue(getResponse("with_href_in_description_and_empty_description.html"));

        final List<Repository> repositories = getRepositories(mServer.url("/").toString());

        assertThat(repositories.get(0).getTitle(), is("didi / VirtualAPK"));
        assertThat(repositories.get(0).getDescription(), is("A powerful and lightweight plugin framework for Android"));
        assertThat(repositories.get(0).getUser(), is("didi /"));
        assertThat(repositories.get(0).getHref(), is("/didi/VirtualAPK"));
        assertThat(repositories.get(0).getAllStars(), is("2,042"));
        assertThat(repositories.get(0).getForks(), is("273"));
        assertThat(repositories.get(0).getStarsToday(), is("364 stars today"));

        assertThat(repositories.get(1).getTitle(), is("Qihoo360 / RePlugin"));
        assertThat(repositories.get(1).getDescription(), is("RePlugin - A flexible, stable, easy-to-use Android Plug-in Framework"));
        assertThat(repositories.get(1).getUser(), is("Qihoo360 /"));
        assertThat(repositories.get(1).getHref(), is("/Qihoo360/RePlugin"));
        assertThat(repositories.get(1).getAllStars(), is("1,687"));
        assertThat(repositories.get(1).getForks(), is("218"));
        assertThat(repositories.get(1).getStarsToday(), is("139 stars today"));

        assertThat(repositories.get(2).getTitle(), is("lihengming / spring-boot-api-project-seed"));
//        assertThat(repositories.get().getDescription(), is(""));
//        assertThat(repositories.get().getUser(), is(""));
//        assertThat(repositories.get().getHref(), is(""));
//        assertThat(repositories.get().getAllStars(), is(""));
//        assertThat(repositories.get().getForks(), is(""));
//        assertThat(repositories.get().getStarsToday(), is(""));

        assertThat(repositories.get(3).getTitle(), is("apache / incubator-rocketmq"));
        assertThat(repositories.get(4).getTitle(), is("Krupen / FabulousFilter"));
        assertThat(repositories.get(5).getTitle(), is("JetBrains / kotlin"));
        assertThat(repositories.get(6).getTitle(), is("airbnb / lottie-android"));
        assertThat(repositories.get(7).getTitle(), is("sebig3000 / MachineLearning"));
        assertThat(repositories.get(8).getTitle(), is("Ramotion / cardslider-android"));
        assertThat(repositories.get(9).getTitle(), is("shuzheng / zheng"));
        assertThat(repositories.get(10).getTitle(), is("spring-projects / spring-boot"));
        assertThat(repositories.get(11).getTitle(), is("harjot-oberai / VectorMaster"));
        assertThat(repositories.get(12).getTitle(), is("google / flexbox-layout"));
        assertThat(repositories.get(13).getTitle(), is("Tencent / angel"));
        assertThat(repositories.get(14).getTitle(), is("Justson / AgentWeb"));
        assertThat(repositories.get(15).getTitle(), is("square / okhttp"));
        assertThat(repositories.get(16).getTitle(), is("JakeWharton / butterknife"));
        assertThat(repositories.get(17).getTitle(), is("iluwatar / java-design-patterns"));
        assertThat(repositories.get(18).getTitle(), is("google / guava"));
        assertThat(repositories.get(19).getTitle(), is("ReactiveX / RxJava"));
        assertThat(repositories.get(20).getTitle(), is("PhilJay / MPAndroidChart"));

        assertThat(repositories.get(21).getTitle(), is("CymChad / BaseRecyclerViewAdapterHelper"));
        assertThat(repositories.get(21).getDescription(), is("Powerful and flexible RecyclerAdapter, www.recyclerview.org"));
        assertThat(repositories.get(21).getUser(), is("CymChad /"));
        assertThat(repositories.get(21).getHref(), is("/CymChad/BaseRecyclerViewAdapterHelper"));
        assertThat(repositories.get(21).getAllStars(), is("8,093"));
        assertThat(repositories.get(21).getForks(), is("1,818"));
        assertThat(repositories.get(21).getStarsToday(), is("11 stars today"));

        assertThat(repositories.get(22).getTitle(), is("bumptech / glide"));
        assertThat(repositories.get(22).getDescription(), is("An image loading and caching library for Android focused on smooth scrolling"));
        assertThat(repositories.get(22).getUser(), is("bumptech /"));
        assertThat(repositories.get(22).getHref(), is("/bumptech/glide"));
        assertThat(repositories.get(22).getAllStars(), is("16,260"));
        assertThat(repositories.get(22).getForks(), is("3,338"));
        assertThat(repositories.get(22).getStarsToday(), is("10 stars today"));

        assertThat(repositories.get(23).getTitle(), is("alibaba / druid"));
        assertThat(repositories.get(23).getDescription(), is("♨️ 为监控而生的数据库连接池！"));
        assertThat(repositories.get(23).getUser(), is("alibaba /"));
        assertThat(repositories.get(23).getHref(), is("/alibaba/druid"));
        assertThat(repositories.get(23).getAllStars(), is("6,788"));
        assertThat(repositories.get(23).getForks(), is("3,240"));
        assertThat(repositories.get(23).getStarsToday(), is("10 stars today"));

        assertThat(repositories.get(24).getTitle(), is("huseyinozer / TooltipIndicator"));
        assertThat(repositories.get(24).getDescription(), is(""));
        assertThat(repositories.get(24).getUser(), is("huseyinozer /"));
        assertThat(repositories.get(24).getHref(), is("/huseyinozer/TooltipIndicator"));
        assertThat(repositories.get(24).getAllStars(), is("42"));
        assertThat(repositories.get(24).getForks(), is("3"));
        assertThat(repositories.get(24).getStarsToday(), is("11 stars today"));
    }

    @Test
    public void testLanguages() throws InterruptedException, IOException {

        mServer.enqueue(getResponse("with_href_in_description_and_empty_description.html"));

        final List<Language> languages = getLanguages(mServer.url("/").toString());

        assertThat(languages.size(), is(451));

        assertThat(languages.get(0).getHref(), is("https://github.com/trending/1c-enterprise"));
        assertThat(languages.get(0).getName(), is("1C Enterprise"));

        assertThat(languages.get(100).getHref(), is("https://github.com/trending/ec"));
        assertThat(languages.get(100).getName(), is("eC"));

        assertThat(languages.get(200).getHref(), is("https://github.com/trending/kotlin"));
        assertThat(languages.get(200).getName(), is("Kotlin"));
    }

    @Test
    public void testTimeSpans() throws InterruptedException, IOException {

        mServer.enqueue(getResponse("with_href_in_description_and_empty_description.html"));

        final List<TimeSpan> timeSpans = getTimeSpans(mServer.url("/").toString());

        assertThat(timeSpans.size(), is(3));

        assertThat(timeSpans.get(0).getHref(), is("https://github.com/trending/java?since=daily"));
        assertThat(timeSpans.get(0).getName(), is("today"));

        assertThat(timeSpans.get(1).getHref(), is("https://github.com/trending/java?since=weekly"));
        assertThat(timeSpans.get(1).getName(), is("this week"));

        assertThat(timeSpans.get(2).getHref(), is("https://github.com/trending/java?since=monthly"));
        assertThat(timeSpans.get(2).getName(), is("this month"));
    }

    @Test
    public void test0719() throws IOException {
        mServer.enqueue(getResponse("java_today_07.19.html"));

        final List<Repository> repositories = getRepositories(mServer.url("/").toString());
        assertThat(repositories.size(), is(25));
    }
}