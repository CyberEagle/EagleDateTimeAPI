package br.com.cybereagle.eagledatetime.internal.impl;

import br.com.cybereagle.eagledatetime.Date;
import br.com.cybereagle.eagledatetime.DateTimeUnit;

import java.util.Locale;
import java.util.TimeZone;

public class DateImpl implements Date {

    public DateImpl(Integer year, Integer month, Integer day){

    }

    @Override
    public Integer getDay() {
        return null;
    }

    @Override
    public Integer getDayOfYear() {
        return null;
    }

    @Override
    public Integer getMonth() {
        return null;
    }

    @Override
    public Integer getWeekDay() {
        return null;
    }

    @Override
    public Integer getYear() {
        return null;
    }

    @Override
    public Integer getWeekIndex() {
        return null;
    }

    @Override
    public Integer getWeekIndex(Date startingFromDate) {
        return null;
    }

    @Override
    public Integer getNumberOfDaysInMonth() {
        return null;
    }

    @Override
    public Date getStartOfMonth() {
        return null;
    }

    @Override
    public Date getEndOfMonth() {
        return null;
    }

    @Override
    public Integer getModifiedJulianDayNumber() {
        return null;
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
    public boolean isLeapYear() {
        return false;
    }

    @Override
    public boolean isSameDayAs(Date date) {
        return false;
    }

    @Override
    public boolean isToday() {
        return false;
    }

    @Override
    public Date minusDays(Integer numberOfDays) {
        return null;
    }

    @Override
    public Date plusDays(Integer numberOfDays) {
        return null;
    }

    @Override
    public Integer numberOfDaysFrom(Date date) {
        return null;
    }

    @Override
    public Date changeTimeZone(TimeZone fromTimeZone, TimeZone toTimeZone) {
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
    public Date minus(Date object) {
        return null;
    }

    @Override
    public Date plus(Date object) {
        return null;
    }

    @Override
    public Date truncate(DateTimeUnit unit) {
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
    public int compareTo(Date date) {
        return 0;
    }
}
