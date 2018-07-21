package hram.githubtrending.data.db;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import hram.githubtrending.data.model.TimeSpan;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class TimeSpanDaoTest extends DaoTest {

    private TimeSpanDao mTimeSpanDao;

    @Override
    public void setup() {
        super.setup();
        mTimeSpanDao = getTestDataModule().provideTimeSpanDao();
    }

    @Test
    public void observableAllTest() {
        //Arrange
        int count = 10;
        List<String> ids = new ArrayList<>();
        List<TimeSpan> timeSpans = prepareTimeSpan(count);
        for (TimeSpan timeSpan : timeSpans) {
            ids.add(timeSpan.getHref());
        }
        mTimeSpanDao.saveDb(timeSpans);

        //Act
        List<TimeSpan> db = mTimeSpanDao.observableAll().blockingFirst();

        //Asserts
        assertEquals(db.size(), count);
        for (TimeSpan timeSpan : db) {
            assertTrue(ids.contains(timeSpan.getHref()));
        }
    }

    @Test
    public void insertRepalceTest() {
        //Arrange
        int count = 10;
        List<TimeSpan> timeSpans = prepareTimeSpan(count);
        mTimeSpanDao.saveDb(timeSpans);
        for (TimeSpan timeSpan : timeSpans) {
            timeSpan.setName(timeSpan.getName() + timeSpan.getHref());
        }

        //Act
        List<Long> rowsId = mTimeSpanDao.saveDb(timeSpans);


        //Asserts
        assertEquals(rowsId.size(), count);
        List<TimeSpan> db = mTimeSpanDao.observableAll().blockingFirst();
        assertEquals(db.size(), count);
        for (TimeSpan timeSpan : timeSpans) {
            singleEquals(timeSpan);
        }
    }

    @Test
    public void findByIdTest() {
        //Arrange
        List<TimeSpan> timeSpans = prepareTimeSpan(1);
        TimeSpan timeSpan = timeSpans.get(0);
        mTimeSpanDao.saveDb(timeSpans);

        //Act
        TimeSpan db = mTimeSpanDao.findById(timeSpan.getHref());


        //Asserts
        assertEquals(db.toString(), timeSpan.toString());
    }

    @Test
    public void clearTest() {
        //Arrange
        int count = 10;
        List<TimeSpan> timeSpans = prepareTimeSpan(count);
        mTimeSpanDao.saveDb(timeSpans);

        //Act
        int deleted = mTimeSpanDao.clear();


        //Asserts
        assertEquals(deleted, count);
        List<TimeSpan> db = mTimeSpanDao.observableAll().blockingFirst();
        assertTrue(db.isEmpty());
    }

    @Test
    public void updateLangaugeTestRollBack() {
        //Arrange
        TimeSpanDao dao = spy(mTimeSpanDao);
        when(dao.saveDb(anyList())).thenThrow(new RuntimeException());
        int count = 10;
        List<String> ids = new ArrayList<>();
        List<TimeSpan> languages = prepareTimeSpan(count);
        for (TimeSpan timeSpan : languages) {
            ids.add(timeSpan.getHref());
        }
        List<TimeSpan> update = prepareTimeSpan(20);
        mTimeSpanDao.saveDb(languages);

        //Act
        try {
            dao.updateTimeSpan(update);
        } catch (RuntimeException ignore) {
            //ignore
        }

        //Asserts
        List<TimeSpan> db = mTimeSpanDao.observableAll().blockingFirst();
        assertEquals(db.size(), count);
        for (TimeSpan timeSpan : db) {
            assertTrue(ids.contains(timeSpan.getHref()));
        }
    }

    @Test
    public void updateLangaugeTest() {
        //Arrange
        int count = 10;
        List<String> ids = new ArrayList<>();
        List<TimeSpan> timeSpans = prepareTimeSpan(count);
        List<TimeSpan> update = prepareTimeSpan(20);
        for (TimeSpan timeSpan : update) {
            ids.add(timeSpan.getHref());
        }
        mTimeSpanDao.saveDb(timeSpans);

        //Act
        mTimeSpanDao.updateTimeSpan(update);

        //Asserts
        List<TimeSpan> db = mTimeSpanDao.observableAll().blockingFirst();
        assertEquals(db.size(), 20);
        for (TimeSpan timeSpan : db) {
            assertTrue(ids.contains(timeSpan.getHref()));
        }
    }

    private void singleEquals(TimeSpan timeSpan) {
        TimeSpan db = mTimeSpanDao.findById(timeSpan.getHref());
        assertEquals(timeSpan.toString(), db.toString());
    }


    private List<TimeSpan> prepareTimeSpan(int count) {
        List<TimeSpan> languages = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int id = nextId();
            TimeSpan timeSpan = new TimeSpan();
            timeSpan.setHref(String.valueOf(id));
            timeSpan.setName(String.valueOf(id));
            languages.add(timeSpan);
        }
        return languages;
    }

}
