package br.com.cybereagle.eagledatetime;

import br.com.cybereagle.eagledatetime.internal.interfaces.CommonDateTimeOperations;

import java.util.TimeZone;

public interface DateTime extends CommonDateTimeOperations<DateTime> {

    Date getDate();
    Time getTime();

    Integer getDay();
    Integer getDayOfYear();
    Integer getMonth();
    Integer getWeekDay();
    Integer getYear();
    Integer getWeekIndex();
    Integer getWeekIndex(DateTime startingFromDate);
    Integer getNumberOfDaysInMonth();
    DateTime getStartOfMonth();
    DateTime getEndOfMonth();
    Integer getModifiedJulianDayNumber();

    boolean isLeapYear();
    boolean isSameDayAs(DateTime dateTime);

    DateTime minusDays(Integer numberOfDays);
    DateTime plusDays(Integer numberOfDays);
    Integer numberOfDaysFrom(DateTime dateTime);

    Integer getHour();
    Integer getMinute();
    Integer getSecond();
    Integer getNanoseconds();
    long numberOfSecondsFrom(DateTime time);

}
