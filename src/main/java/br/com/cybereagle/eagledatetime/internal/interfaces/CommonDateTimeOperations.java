package br.com.cybereagle.eagledatetime.internal.interfaces;

import br.com.cybereagle.eagledatetime.DateTimeUnit;

import java.util.Locale;
import java.util.TimeZone;

public interface CommonDateTimeOperations<T> extends Comparable<T> {

    T changeTimeZone(TimeZone fromTimeZone, TimeZone toTimeZone);
    String format(String format);
    String format(String format, Locale locale);
    long getMillisecondsInstant(TimeZone timeZone);
    long getNanosecondsInstant(TimeZone timeZone);
    boolean isParseable(String cadidate);
    T minus(T object);
    T plus(T object);
    T truncate(DateTimeUnit unit);
    DateTimeUnit getPrecision();

    boolean unitsAllAbsent(DateTimeUnit... units);
    boolean unitsAllPresent(DateTimeUnit... units);

    boolean isInTheFuture(TimeZone timeZone);
    boolean isInThePast(TimeZone timeZone);

}
