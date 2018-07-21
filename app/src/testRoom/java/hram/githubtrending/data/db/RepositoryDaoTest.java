package hram.githubtrending.data.db;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hram.githubtrending.data.model.Repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;


public class RepositoryDaoTest extends DaoTest {

    private RepositoryDao mRepositoryDao;

    public void setup() {
        super.setup();
        mRepositoryDao = getTestDataModule().provideRepositoryDao();
    }

    @Test
    public void deleteNotHiddenTest() {
        //Arrange
        String language = "java";
        String timeSapn = "daily";
        int notHiddenCount = 11;
        Repository[] notHidden = prepareRepository(language, timeSapn, notHiddenCount, false);

        mRepositoryDao.save(notHidden);

        Repository hidden = prepareRepository(language, timeSapn, 1, true)[0];
        Repository notHiddenLang = prepareRepository(language + "test", timeSapn, 1, false)[0];
        Repository notHiddenTimeSpan = prepareRepository(language, timeSapn + "test", 1, false)[0];
        mRepositoryDao.save(notHiddenLang);
        mRepositoryDao.save(notHiddenTimeSpan);
        mRepositoryDao.save(hidden);

        //Act
        int count = mRepositoryDao.deleteDbByRelation(language, timeSapn);

        //Asserts
        assertEquals(count, notHiddenCount);
        List<Repository> repositoryList = mRepositoryDao.all();
        Collections.sort(repositoryList, (o1, o2) -> Integer.compare(o1.getOrder(), o2.getOrder()));
        assertEquals(repositoryList.size(), 3);
        Repository hide = repositoryList.get(0);
        Repository lang = repositoryList.get(1);
        Repository time = repositoryList.get(2);
        assertEquals(hide.toString(), hidden.toString());
        assertEquals(lang.toString(), notHiddenLang.toString());
        assertEquals(time.toString(), notHiddenTimeSpan.toString());
    }

    @Test
    public void clearTest() {
        //Arrange
        String language = "language";
        String timeSapn = "timeSpan";
        int all = 20;
        Repository[] notHidden = prepareRepository(language, timeSapn, all, false);
        mRepositoryDao.save(notHidden);
        notHidden = prepareRepository(language + "1", timeSapn, all, false);
        mRepositoryDao.save(notHidden);
        notHidden = prepareRepository(language, timeSapn + "1", all, false);
        mRepositoryDao.save(notHidden);
        Repository[] hidden = prepareRepository(language, timeSapn, all, true);
        mRepositoryDao.save(hidden);
        hidden = prepareRepository(language + "1", timeSapn, all, true);
        mRepositoryDao.save(hidden);
        hidden = prepareRepository(language, timeSapn + "1", all, true);
        mRepositoryDao.save(hidden);

        //Act
        int count = mRepositoryDao.clear();

        //Asserts
        assertEquals(count, 6 * all);
        List<Repository> repositoryList = mRepositoryDao.all();
        assertEquals(repositoryList.size(), 0);
    }

    @Test
    public void allTest() {
        //Arrange
        String language = "language";
        String timeSapn = "timeSpan";
        int all = 20;
        Repository[] notHidden = prepareRepository(language, timeSapn, all, false);
        mRepositoryDao.save(notHidden);
        notHidden = prepareRepository(language + "1", timeSapn, all, false);
        mRepositoryDao.save(notHidden);
        notHidden = prepareRepository(language, timeSapn + "1", all, false);
        mRepositoryDao.save(notHidden);
        Repository[] hidden = prepareRepository(language, timeSapn, all, true);
        mRepositoryDao.save(hidden);
        hidden = prepareRepository(language + "1", timeSapn, all, true);
        mRepositoryDao.save(hidden);
        hidden = prepareRepository(language, timeSapn + "1", all, true);
        mRepositoryDao.save(hidden);

        //Act
        List<Repository> repositories = mRepositoryDao.all();

        //Asserts
        assertEquals(repositories.size(), 6 * all);
    }

    @Test
    public void observableByRelationTest() {
        //Arrange
        String language = "language";
        String timeSapn = "timeSpan";
        int all = 20;
        Repository[] notHidden = prepareRepository(language, timeSapn, all, false);
        mRepositoryDao.save(notHidden);
        Repository[] hidden = prepareRepository(language, timeSapn, all, true);
        mRepositoryDao.save(hidden);

        //Act
        List<Repository> repositories = mRepositoryDao.observableByRelation(language, timeSapn)
                .blockingFirst();

        //Asserts
        assertEquals(repositories.size(), 20);
        for (Repository repository : repositories) {
            assertFalse(repository.isHided());
            assertEquals(repository.getLanguage(), language);
            assertEquals(repository.getTimeSpan(), timeSapn);
        }
    }

    @Test
    public void observableByIdsTest() {
        //Arrange
        String language = "language";
        String timeSapn = "timeSpan";
        List<String> ids = new ArrayList<>();
        int all = 20;
        Repository[] notHidden = prepareRepository(language, timeSapn, all, false);
        for (Repository repository : notHidden) {
            ids.add(repository.getHref());
        }
        mRepositoryDao.save(notHidden);
        Repository[] hidden = prepareRepository(language, timeSapn, all, true);
        for (Repository repository : hidden) {
            ids.add(repository.getHref());
        }
        mRepositoryDao.save(hidden);

        //Act
        List<Repository> repositories = mRepositoryDao.observableByIds(ids)
                .blockingFirst();

        //Asserts
        assertEquals(repositories.size(), 20);
        for (Repository repository : repositories) {
            assertFalse(repository.isHided());
            assertTrue(ids.contains(repository.getHref()));
        }
    }

    @Test
    public void observableByIdTestNotHidden() {
        //Arrange
        String language = "language";
        String timeSapn = "timeSpan";
        Repository notHidden = prepareRepository(language, timeSapn, 1, false)[0];
        mRepositoryDao.save(notHidden);

        //Act
        Repository repository = mRepositoryDao.observableById(notHidden.getHref())
                .blockingFirst();

        //Asserts
        assertEquals(repository.toString(), notHidden.toString());
    }

    @Test
    public void observableByIdTestHidden() {
        //Arrange
        String language = "language";
        String timeSapn = "timeSpan";
        Repository notHidden = prepareRepository(language, timeSapn, 1, true)[0];
        mRepositoryDao.save(notHidden);

        //Act
        Repository repository = mRepositoryDao.observableById(notHidden.getHref())
                .blockingFirst();

        //Asserts
        assertEquals(repository.toString(), notHidden.toString());
    }

    @Test
    public void insertDbIgnoreTest() {
        //Arrange
        String language = "language";
        String timeSapn = "timeSpan";
        Repository[] notHidden = prepareRepository(language, timeSapn, 10, true);
        List<Repository> repositories = Arrays.asList(notHidden[0], notHidden[1]);
        mRepositoryDao.save(notHidden);
        repositories.get(0).setLanguage(language + "1");
        repositories.get(1).setLanguage(timeSapn + "1");

        //Act
        List<Long> rowsId = mRepositoryDao.insertDb(repositories);

        //Asserts
        assertEquals(rowsId.size(), 2);
        notEquals(repositories.get(0));
        notEquals(repositories.get(1));
    }

    @Test
    public void updateRepositoriesTestRollback() {
        //Arrange
        RepositoryDao dao = spy(mRepositoryDao);
        when(dao.insertDb(anyList())).thenThrow(new RuntimeException());
        String language = "language";
        String timeSapn = "timeSpan";
        Repository[] notHidden = prepareRepository(language, timeSapn, 10, false);
        List<String> ids = new ArrayList<>();
        for (Repository repository : notHidden) {
            ids.add(repository.getHref());
        }
        dao.save(notHidden);
        List<Repository> update = Arrays.asList(prepareRepository(language, timeSapn, 10, false));

        //Act
        try {
            dao.updateRepositories(update, language, timeSapn);
        } catch (RuntimeException ignore) {

        }

        //Asserts
        List<Repository> repositories = dao.observableByRelation(language, timeSapn).blockingFirst();
        for (Repository repository : repositories) {
            assertTrue(ids.contains(repository.getHref()));
        }
    }

    @Test
    public void updateRepositoriesTest() {
        //Arrange
        String language = "language";
        String timeSapn = "timeSpan";
        Repository[] notHidden = prepareRepository(language, timeSapn, 10, false);
        List<String> olds = new ArrayList<>();
        for (Repository repository : notHidden) {
            olds.add(repository.getHref());
        }
        mRepositoryDao.save(notHidden);
        List<Repository> update = Arrays.asList(prepareRepository(language, timeSapn, 10, false));

        //Act
        List<String> ids = mRepositoryDao.updateRepositories(update, language, timeSapn);

        //Asserts
        for (String id : ids) {
            assertTrue(!olds.contains(id));
        }
    }

    private void notEquals(Repository repository) {
        Repository saved = mRepositoryDao.observableById(repository.getHref()).blockingFirst();
        assertNotEquals(saved.toString(), repository.toString());
    }

    private Repository[] prepareRepository(String language, String timeSpan, int count, boolean isHidden) {
        Repository[] repositories = new Repository[count];
        for (int i = 0; i < count; i++) {
            int id = nextId();
            Repository repository = new Repository();
            repository.setHref(String.valueOf(id));
            repository.setLanguage(language);
            repository.setTimeSpan(timeSpan);
            repository.setHided(isHidden);
            repository.setOrder(id);
            repositories[i] = repository;
        }
        return repositories;
    }


}
