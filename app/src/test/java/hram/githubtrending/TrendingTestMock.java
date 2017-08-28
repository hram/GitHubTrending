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

        assertThat(repositories.get(0).getTitle(), is("rsms / interface"));
        assertThat(repositories.get(0).getDescription(), is("The Interface font family"));
        assertThat(repositories.get(0).getUser(), is("rsms /"));
        assertThat(repositories.get(0).getHref(), is("/rsms/interface"));
        assertThat(repositories.get(0).getAllStars(), is("1,983"));
        assertThat(repositories.get(0).getForks(), is("26"));
        assertThat(repositories.get(0).getStarsToday(), is("666 stars today"));

        assertThat(repositories.get(1).getTitle(), is("LewisVo / Awesome-Linux-Software"));
        assertThat(repositories.get(2).getTitle(), is("justdoit0823 / pywxclient"));
        assertThat(repositories.get(3).getTitle(), is("0x09AL / raven"));

        assertThat(repositories.get(4).getTitle(), is("eldraco / Salamandra"));
        assertThat(repositories.get(4).getDescription(), is(""));
        assertThat(repositories.get(4).getUser(), is("eldraco /"));
        assertThat(repositories.get(4).getHref(), is("/eldraco/Salamandra"));
        assertThat(repositories.get(4).getAllStars(), is("71"));
        assertThat(repositories.get(4).getForks(), is("11"));
        assertThat(repositories.get(4).getStarsToday(), is("62 stars today"));

        assertThat(repositories.get(5).getTitle(), is("facebook / codemod"));
        assertThat(repositories.get(6).getTitle(), is("Kyubyong / neural_chinese_transliterator"));
        assertThat(repositories.get(7).getTitle(), is("tensorflow / models"));
        assertThat(repositories.get(8).getTitle(), is("vinta / awesome-python"));
        assertThat(repositories.get(9).getTitle(), is("lk-geimfari / mimesis"));
        assertThat(repositories.get(10).getTitle(), is("fchollet / keras"));
        assertThat(repositories.get(11).getTitle(), is("pallets / flask"));
        assertThat(repositories.get(12).getTitle(), is("requests / requests"));
        assertThat(repositories.get(13).getTitle(), is("josephmisiti / awesome-machine-learning"));
        assertThat(repositories.get(14).getTitle(), is("masasin / latexipy"));
        assertThat(repositories.get(15).getTitle(), is("jmhessel / fmpytorch"));
        assertThat(repositories.get(16).getTitle(), is("ansible / ansible"));
        assertThat(repositories.get(17).getTitle(), is("chiphuyen / stanford-tensorflow-tutorials"));
        assertThat(repositories.get(18).getTitle(), is("rg3 / youtube-dl"));
        assertThat(repositories.get(19).getTitle(), is("django / django"));
        assertThat(repositories.get(20).getTitle(), is("deepmind / pysc2"));
        assertThat(repositories.get(21).getTitle(), is("BenWhetton / keras-surgeon"));
        assertThat(repositories.get(22).getTitle(), is("pytorch / pytorch"));
        assertThat(repositories.get(23).getTitle(), is("AndreaOm / xiaomiquan_bak"));

        assertThat(repositories.get(24).getTitle(), is("jakubroztocil / httpie"));
        assertThat(repositories.get(24).getDescription(), is("Modern command line HTTP client � user-friendly curl alternative with intuitive UI, JSON support, syntax highlighting, wget-like downloads, extensions, etc. https://httpie.org"));
        assertThat(repositories.get(24).getUser(), is("jakubroztocil /"));
        assertThat(repositories.get(24).getHref(), is("/jakubroztocil/httpie"));
        assertThat(repositories.get(24).getAllStars(), is("31,152"));
        assertThat(repositories.get(24).getForks(), is("2,095"));
        assertThat(repositories.get(24).getStarsToday(), is(""));
    }

    @Test
    public void testLanguages() throws InterruptedException, IOException {

        mServer.enqueue(getResponse("with_href_in_description_and_empty_description.html"));

        final List<Language> languages = getLanguages(mServer.url("/").toString());

        assertThat(languages.size(), is(457));

        assertThat(languages.get(0).getHref(), is("https://github.com/trending/1c-enterprise?since=daily"));
        assertThat(languages.get(0).getName(), is("1C Enterprise"));

        assertThat(languages.get(100).getHref(), is("https://github.com/trending/easybuild?since=daily"));
        assertThat(languages.get(100).getName(), is("Easybuild"));

        assertThat(languages.get(200).getHref(), is("https://github.com/trending/kicad-layout?since=daily"));
        assertThat(languages.get(200).getName(), is("KiCad Layout"));
    }

    @Test
    public void testTimeSpans() throws InterruptedException, IOException {

        mServer.enqueue(getResponse("with_href_in_description_and_empty_description.html"));

        final List<TimeSpan> timeSpans = getTimeSpans(mServer.url("/").toString());

        assertThat(timeSpans.size(), is(3));

        assertThat(timeSpans.get(0).getHref(), is("https://github.com/trending/python?since=daily"));
        assertThat(timeSpans.get(0).getName(), is("today"));

        assertThat(timeSpans.get(1).getHref(), is("https://github.com/trending/python?since=weekly"));
        assertThat(timeSpans.get(1).getName(), is("this week"));

        assertThat(timeSpans.get(2).getHref(), is("https://github.com/trending/python?since=monthly"));
        assertThat(timeSpans.get(2).getName(), is("this month"));
    }

    @Test
    public void test0719() throws IOException {
        mServer.enqueue(getResponse("java_today_07.19.html"));

        final List<Repository> repositories = getRepositories(mServer.url("/").toString());
        assertThat(repositories.size(), is(25));
    }
}