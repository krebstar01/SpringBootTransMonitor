package testservice.manager;

import com.testservice.Application;
import com.testservice.manager.ServiceManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by recor on 19/06/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ServiceManagerTest {

    @Autowired
    ServiceManager serviceManager;

    @Test
    public void testTheTest() {
        assertTrue(true);
    }


    @Test
    public void testEpochToDb() {

        Long epochTimeStamp = 1478192204000L;

        //epoch to zonedtimestamp
        ZonedDateTime zonedDateTime = serviceManager.getZonedTimeStampFromEpochMilliSecond(epochTimeStamp);

        long expected1 = zonedDateTime.getDayOfMonth();
        long expected2 = zonedDateTime.getHour();
        long expected3 = zonedDateTime.getMinute();

        //zonedtimestamp to LocalDateTime -- kosher for H2!
        LocalDateTime ldt = serviceManager.getLocalDateTimeFromZonedDateTime(zonedDateTime);
        long actual1 = ldt.getDayOfMonth();
        long actual2 = ldt.getHour();
        long actual3 = ldt.getMinute();


        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
        assertEquals(expected3, actual3);
    }


    @Test
    public void testDbToZonedDateTime() {
        LocalDateTime ldt = LocalDateTime.now();
        long expected1 = ldt.getDayOfMonth();
        long expected2 = ldt.getHour();
        long expected3 = ldt.getMinute();

        ZonedDateTime zonedDateTime = serviceManager.getZonedDateTimeFromLocalDateTime(ldt);

        long actual1 = zonedDateTime.getDayOfMonth();
        long actual2 = zonedDateTime.getHour();
        long actual3 = zonedDateTime.getMinute();

        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
        assertEquals(expected3, actual3);
    }


    @Test
    public void testDbToZonedEpochMilliRoundTrip() {
        Long epochTimeStamp = 1478192204000L;
        //epoch to zonedtimestamp
        ZonedDateTime zdtBefore = serviceManager.getZonedTimeStampFromEpochMilliSecond(epochTimeStamp);
        //zonedtimestamp to LocalDateTime -- kosher for H2!
        LocalDateTime ldt = serviceManager.getLocalDateTimeFromZonedDateTime(zdtBefore);

        ZonedDateTime zdtAfter = serviceManager.getZonedDateTimeFromLocalDateTime(ldt);

        long actual = serviceManager.getEpochMilliSecondFromZonedTimeStamp(zdtAfter);
        long expected = epochTimeStamp;
        assertEquals(expected, actual);
    }


    @Test
    public void testGetLocalDateTimeFromEpochMilliSecond(){
        Long epochTimeStamp = 1478192204000L;
        LocalDateTime ldt = serviceManager.getLocalDateTimeFromEpochMilliSecond(epochTimeStamp);
        ZonedDateTime zdtAfter = serviceManager.getZonedDateTimeFromLocalDateTime(ldt);
        long actual = serviceManager.getEpochMilliSecondFromZonedTimeStamp(zdtAfter);
        long expected = epochTimeStamp;
        assertEquals(expected, actual);
    }


}