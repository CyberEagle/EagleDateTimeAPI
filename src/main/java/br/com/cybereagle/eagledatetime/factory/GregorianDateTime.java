package br.com.cybereagle.eagledatetime.factory;

import br.com.cybereagle.eagledatetime.Date;
import br.com.cybereagle.eagledatetime.DateTime;
import br.com.cybereagle.eagledatetime.Time;
import br.com.cybereagle.eagledatetime.internal.gregorian.DateImpl;
import br.com.cybereagle.eagledatetime.internal.gregorian.DateTimeImpl;
import br.com.cybereagle.eagledatetime.internal.gregorian.DateTimeParser;
import br.com.cybereagle.eagledatetime.internal.gregorian.TimeImpl;

import java.util.TimeZone;

public class GregorianDateTime {

    private static final DateTimeParser DATE_TIME_PARSER = new DateTimeParser();

    public static DateTime newDateTime(Date date, Time time) {
        return new DateTimeImpl(date, time);
    }

    public static DateTime newDateTime(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer second, Integer nanoseconds) {
        return new DateTimeImpl(newDate(year, month, day), newTime(hour, minute, second, nanoseconds));
    }

    public static DateTime newDateTime(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer second) {
        return newDateTime(year, month, day, hour, minute, second, 0);
    }

    public static DateTime parseDateTime(String rawString) {
        return DATE_TIME_PARSER.parseDateTime(rawString);
    }

    public static Date newDate(Integer year, Integer month, Integer day) {
        return new DateImpl(year, month, day);
    }

    public static Date parseDate(String rawString) {
        return DATE_TIME_PARSER.parseDate(rawString);
    }

    public static Time newTime(Integer hour, Integer minute, Integer second, Integer nanoseconds) {
        return new TimeImpl(hour, minute, second, nanoseconds);
    }

    public static Time newTime(Integer hour, Integer minute, Integer second) {
        return new TimeImpl(hour, minute, second, 0);
    }

    public static Time parseTime(String rawString) {
        return DATE_TIME_PARSER.parseTime(rawString);
    }

    public static boolean isParseableForDateTime(String candidate) {
        try {
            DATE_TIME_PARSER.parseDateTime(candidate);
            return true;
        }
        catch (RuntimeException e){
            return false;
        }
    }

    public static boolean isParseableForDate(String candidate) {
        try {
            DATE_TIME_PARSER.parseDate(candidate);
            return true;
        }
        catch (RuntimeException e){
            return false;
        }
    }

    public static boolean isParseableForTime(String candidate) {
        try {
            DATE_TIME_PARSER.parseTime(candidate);
            return true;
        }
        catch (RuntimeException e){
            return false;
        }
    }

    public static DateTime forInstant(long miliseconds, TimeZone timeZone) {
        return null;
    }

    public static DateTime forInstantNanos(long nanoseconds, TimeZone timeZone) {
        return null;
    }

    public static DateTime now(TimeZone timeZone) {
        return null;
    }

    public static Date forInstantDateOnly(long miliseconds, TimeZone timeZone) {
        return null;
    }

    public static Date forInstantNanosDateOnly(long nanoseconds, TimeZone timeZone) {
        return null;
    }

    public static Date today(TimeZone timeZone) {
        return null;
    }

    public static Time forInstantTimeOnly(long miliseconds, TimeZone timeZone) {
        return null;
    }

    public static Time forInstantNanosTimeOnly(long nanoseconds, TimeZone timeZone) {
        return null;
    }

    public static Time nowTimeOnly(TimeZone timeZone) {
        return null;
    }

    public static Time getEndOfDay() {
        return null;
    }

    public static Time getStartOfDay() {
        return null;
    }

    public static Date fromJulianDayNumberAtNoon(int aJDAtNoon){
        //http://www.hermetic.ch/cal_stud/jdn.htm
        int l = aJDAtNoon + 68569;
        int n = (4 * l) / 146097;
        l = l - (146097 * n + 3) / 4;
        int i = (4000 * (l + 1)) / 1461001;
        l = l - (1461 * i) / 4 + 31;
        int j = (80 * l) / 2447;
        int d = l - (2447 * j) / 80;
        l = j / 11;
        int m = j + 2 - (12 * l);
        int y = 100 * (n - 49) + i + l;
        return newDate(y, m, d);
    }

}
