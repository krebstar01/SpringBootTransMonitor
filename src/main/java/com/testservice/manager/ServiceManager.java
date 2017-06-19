package com.testservice.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.Temporal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;

/**
 * Created by recor on 19/06/2017.
 */
@Component
public class ServiceManager {


    @Autowired
    com.testservice.data.repository.StatisticRepository statisticRepository;


    public ZonedDateTime getZonedTimeStampFromEpochMilliSecond(long epochMillis) {
        Instant instant = Instant.ofEpochMilli(epochMillis);
        ZonedDateTime utc = ZonedDateTime.ofInstant(instant, ZoneOffset.UTC);
        return utc;
    }


    public boolean isTimeStampMoreThanOneMineOld(long epochMillis) {
        Instant instant = Instant.ofEpochMilli(epochMillis);
        ZonedDateTime utc = ZonedDateTime.ofInstant(instant, ZoneOffset.UTC);

        ZonedDateTime minusOneMinuteZone = ZonedDateTime.now(ZoneOffset.UTC).minusMinutes(1);
        Long pastEpochMillis = minusOneMinuteZone.toEpochSecond() * 1000;

        if(epochMillis < pastEpochMillis) {
            return true;
        }

        return false;
    }


    public Long getEpochMilliSecondFromZonedTimeStamp(ZonedDateTime utc) {
        if (utc == null) {
            return ZonedDateTime.now(ZoneOffset.UTC).toEpochSecond();
        }

        long epochMillis = utc.toEpochSecond() * 1000;
        return epochMillis;
    }


    public ZonedDateTime getZonedDateTimeFromLocalDateTime(LocalDateTime ldt) {
        if (ldt == null) {
            return ZonedDateTime.now(ZoneOffset.UTC);
        }
        Long epochMillis = ldt.toEpochSecond(ZoneOffset.UTC);
        Instant instant = Instant.ofEpochSecond(epochMillis);
        ZonedDateTime utc = ZonedDateTime.ofInstant(instant, ZoneOffset.UTC);
        return utc;
    }

    public LocalDateTime getLocalDateTimeFromZonedDateTime(ZonedDateTime utc) {
        if (utc == null) {
            return ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime();
        }
        return utc.toLocalDateTime();
    }


    public LocalDateTime getLocalDateTimeFromEpochMilliSecond(long epochMillis) {
        ZonedDateTime zdt = getZonedTimeStampFromEpochMilliSecond(epochMillis);
        LocalDateTime ldt = getLocalDateTimeFromZonedDateTime(zdt);
        return ldt;
    }

    public HashMap<String, String> getStatsLast60Seconds() {
        HashMap<String, String> results = new HashMap<>();

        ZonedDateTime nowZone = ZonedDateTime.now(ZoneOffset.UTC);
        ZonedDateTime minusOneMinuteZone = nowZone.minusMinutes(1);
        LocalDateTime now = getLocalDateTimeFromZonedDateTime(nowZone);
        LocalDateTime minusOneMinute = getLocalDateTimeFromZonedDateTime(minusOneMinuteZone);

        Double sum = statisticRepository.sumAmountByTimestampBetween(minusOneMinute, now);
        Double avg = statisticRepository.avgAmountByTimestampBetween(minusOneMinute, now);
        Double max = statisticRepository.maxAmountByTimestampBetween(minusOneMinute, now);
        Double min = statisticRepository.minAmountByTimestampBetween(minusOneMinute, now);
        Long count = statisticRepository.countByTimestampBetween(minusOneMinute, now);

        results.put("sum", sum.toString());
        results.put("avg", avg.toString());
        results.put("max", max.toString());
        results.put("min", min.toString());
        results.put("count", count.toString());


        return results;

    }


}
