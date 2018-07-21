package hram.githubtrending.data.db;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hram.githubtrending.data.model.Language;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class LangaugeDaoTest extends DaoTest {

    private LanguageDao mLanguageDao;

    public void setup() {
        super.setup();
        mLanguageDao = getTestDataModule().provideLanguageDao();
    }

    @Test
    public void observableAllTest() {
        //Arrange
        int count = 10;
        List<String> ids = new ArrayList<>();
        List<Language> languages = prepareLanguage(count);
        for (Language language : languages) {
            ids.add(language.getHref());
        }
        mLanguageDao.saveDb(languages);

        //Act
        List<Language> db = mLanguageDao.observableAll().blockingFirst();

        //Asserts
        assertEquals(db.size(), count);
        for (Language language : db) {
            assertTrue(ids.contains(language.getHref()));
        }
    }

    @Test
    public void insertRepalceTest() {
        //Arrange
        int count = 10;
        List<Language> languages = prepareLanguage(count);
        mLanguageDao.saveDb(languages);
        for (Language language : languages) {
            language.setName(language.getName() + language.getHref());
        }

        //Act
        List<Long> rowsId = mLanguageDao.saveDb(languages);


        //Asserts
        assertEquals(rowsId.size(), count);
        List<Language> db = mLanguageDao.observableAll().blockingFirst();
        assertEquals(db.size(), count);
        for (Language language : languages) {
            singleEquals(language);
        }
    }

    @Test
    public void findByIdTest() {
        //Arrange
        Language language = prepareLanguage(1).get(0);
        mLanguageDao.saveDb(Collections.singletonList(language));

        //Act
        Language db = mLanguageDao.findById(language.getHref());


        //Asserts
        assertEquals(db.toString(), language.toString());
    }

    @Test
    public void clearTest() {
        //Arrange
        int count = 10;
        List<Language> languages = prepareLanguage(count);
        mLanguageDao.saveDb(languages);

        //Act
        int deleted = mLanguageDao.clear();


        //Asserts
        assertEquals(deleted, count);
        List<Language> db = mLanguageDao.observableAll().blockingFirst();
        assertTrue(db.isEmpty());
    }

    @Test
    public void updateLangaugeTestRollBack() {
        //Arrange
        LanguageDao dao = spy(mLanguageDao);
        when(dao.saveDb(anyList())).thenThrow(new RuntimeException());
        int count = 10;
        List<String> ids = new ArrayList<>();
        List<Language> languages = prepareLanguage(count);
        for (Language language : languages) {
            ids.add(language.getHref());
        }
        List<Language> update = prepareLanguage(20);
        mLanguageDao.saveDb(languages);

        //Act
        try {
            dao.updateLanguage(update);
        } catch (RuntimeException ignore) {

        }

        //Asserts
        List<Language> db = mLanguageDao.observableAll().blockingFirst();
        assertEquals(db.size(), count);
        for (Language language : db) {
            assertTrue(ids.contains(language.getHref()));
        }
    }

    @Test
    public void updateLangaugeTest() {
        //Arrange
        int count = 10;
        List<String> ids = new ArrayList<>();
        List<Language> languages = prepareLanguage(count);
        List<Language> update = prepareLanguage(20);
        for (Language language : update) {
            ids.add(language.getHref());
        }
        mLanguageDao.saveDb(languages);

        //Act
        mLanguageDao.updateLanguage(update);

        //Asserts
        List<Language> db = mLanguageDao.observableAll().blockingFirst();
        assertEquals(db.size(), 20);
        for (Language language : db) {
            assertTrue(ids.contains(language.getHref()));
        }
    }

    private void singleEquals(Language language) {
        Language db = mLanguageDao.findById(language.getHref());
        assertEquals(language.toString(), db.toString());
    }

    private void singleNotEquals(Language language) {
        Language db = mLanguageDao.findById(language.getHref());
        assertNotEquals(language.toString(), db.toString());
    }


    private List<Language> prepareLanguage(int count) {
        List<Language> languages = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int id = nextId();
            Language language = new Language();
            language.setHref(String.valueOf(id));
            language.setName(String.valueOf(id));
            languages.add(language);
        }
        return languages;
    }
}
