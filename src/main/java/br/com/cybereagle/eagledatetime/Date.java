package br.com.cybereagle.eagledatetime;

import br.com.cybereagle.eagledatetime.internal.interfaces.CommonDateTimeOperations;

import java.util.TimeZone;

public interface Date extends CommonDateTimeOperations<Date> {

    Integer getDay();
    Integer getDayOfYear();
    Integer getMonth();
    Integer getWeekDay();
    Integer getYear();
    Integer getWeekIndex();
    Integer getWeekIndex(Date startingFromDate);
    Integer getNumberOfDaysInMonth();
    Date getStartOfMonth();
    Date getEndOfMonth();
    Integer getModifiedJulianDayNumber();

    boolean isLeapYear();
    boolean isSameDayAs(Date date);
    boolean isToday();

    Date minusDays(Integer numberOfDays);
    Date plusDays(Integer numberOfDays);
    Integer numberOfDaysFrom(Date date);

}
