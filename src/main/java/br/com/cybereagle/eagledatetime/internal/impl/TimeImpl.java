package br.com.cybereagle.eagledatetime.internal.impl;

import br.com.cybereagle.eagledatetime.DateTimeUnit;
import br.com.cybereagle.eagledatetime.Time;

import java.util.Locale;
import java.util.TimeZone;

public class TimeImpl implements Time {

    public TimeImpl(Integer aHour, Integer aMinute, Integer aSecond, Integer aNanoseconds){

    }

    @Override
    public Integer getHour() {
        return null;
    }

    @Override
    public Integer getMinute() {
        return null;
    }

    @Override
    public Integer getSecond() {
        return null;
    }

    @Override
    public Integer getNanoseconds() {
        return null;
    }

    @Override
    public long numberOfSecondsFrom(Time time) {
        return 0;
    }

    @Override
    public Time changeTimeZone(TimeZone fromTimeZone, TimeZone toTimeZone) {
        return null;
    }

    @Override
    public String format(String format) {
        return null;
    }

    @Override
    public String format(String format, Locale locale) {
        return null;
    }

    @Override
    public long getMillisecondsInstant(TimeZone timeZone) {
        return 0;
    }

    @Override
    public long getNanosecondsInstant(TimeZone timeZone) {
        return 0;
    }

    @Override
    public boolean isParseable(String cadidate) {
        return false;
    }

    @Override
    public Time minus(Time object) {
        return null;
    }

    @Override
    public Time plus(Time object) {
        return null;
    }

    @Override
    public Time truncate(DateTimeUnit unit) {
        return null;
    }

    @Override
    public DateTimeUnit getPrecision() {
        return null;
    }

    @Override
    public boolean unitsAllAbsent(DateTimeUnit... units) {
        return false;
    }

    @Override
    public boolean unitsAllPresent(DateTimeUnit... units) {
        return false;
    }

    @Override
    public boolean isInTheFuture(TimeZone timeZone) {
        return false;
    }

    @Override
    public boolean isInThePast(TimeZone timeZone) {
        return false;
    }

    @Override
    public int compareTo(Time time) {
        return 0;
    }
}
