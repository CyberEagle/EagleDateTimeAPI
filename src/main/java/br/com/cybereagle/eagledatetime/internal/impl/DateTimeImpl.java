package br.com.cybereagle.eagledatetime.internal.impl;

import br.com.cybereagle.eagledatetime.Date;
import br.com.cybereagle.eagledatetime.DateTime;
import br.com.cybereagle.eagledatetime.DateTimeUnit;
import br.com.cybereagle.eagledatetime.Time;
import br.com.cybereagle.eagledatetime.factory.TimeFactory;

import java.util.Locale;
import java.util.TimeZone;

public class DateTimeImpl implements DateTime {

    private final Date date;
    private final Time time;

    public DateTimeImpl(Date date, Time time){
        this.date = date;
        this.time = time;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public Time getTime() {
        return time;
    }

    @Override
    public Integer getDay() {
        return date.getDay();
    }

    @Override
    public Integer getDayOfYear() {
        return date.getDayOfYear();
    }

    @Override
    public Integer getMonth() {
        return date.getMonth();
    }

    @Override
    public Integer getWeekDay() {
        return date.getWeekDay();
    }

    @Override
    public Integer getYear() {
        return date.getYear();
    }

    @Override
    public Integer getWeekIndex() {
        return date.getWeekIndex();
    }

    @Override
    public Integer getWeekIndex(DateTime startingFromDate) {
        return date.getWeekIndex(startingFromDate.getDate());
    }

    @Override
    public Integer getNumberOfDaysInMonth() {
        return date.getNumberOfDaysInMonth();
    }

    @Override
    public DateTime getStartOfMonth() {
        Date startOfMonth = date.getStartOfMonth();
        Time startOfDay = TimeFactory.getStartOfDay();
        return new DateTimeImpl(startOfMonth, startOfDay);
    }

    @Override
    public DateTime getEndOfMonth() {
        Date endOfMonth = date.getEndOfMonth();
        Time endOfDay = TimeFactory.getEndOfDay();
        return new DateTimeImpl(endOfMonth, endOfDay);
    }

    @Override
    public Integer getModifiedJulianDayNumber() {
        return date.getModifiedJulianDayNumber();
    }

    @Override
    public boolean isInTheFuture(TimeZone timeZone) {
        if(date.isInTheFuture(timeZone)){
            return true;
        }
        else if(date.isToday()){
            return time.isInTheFuture(timeZone);
        }

        return false;
    }

    @Override
    public boolean isInThePast(TimeZone timeZone) {
        if(date.isInThePast(timeZone)){
            return true;
        }
        else if(date.isToday()){
            return time.isInThePast(timeZone);
        }

        return false;
    }

    @Override
    public boolean isLeapYear() {
        return date.isLeapYear();
    }

    @Override
    public boolean isSameDayAs(DateTime dateTime) {
        return date.isSameDayAs(dateTime.getDate());
    }

    @Override
    public DateTime minusDays(Integer numberOfDays) {
        return new DateTimeImpl(date.minusDays(numberOfDays), time);
    }

    @Override
    public DateTime plusDays(Integer numberOfDays) {
        return new DateTimeImpl(date.plusDays(numberOfDays), time);
    }

    @Override
    public Integer numberOfDaysFrom(DateTime dateTime) {
        return date.numberOfDaysFrom(dateTime.getDate());
    }

    @Override
    public Integer getHour() {
        return time.getHour();
    }

    @Override
    public Integer getMinute() {
        return time.getMinute();
    }

    @Override
    public Integer getSecond() {
        return time.getSecond();
    }

    @Override
    public Integer getNanoseconds() {
        return time.getNanoseconds();
    }

    @Override
    public long numberOfSecondsFrom(DateTime time) {
        // TODO
        return 0;
    }

    @Override
    public DateTime changeTimeZone(TimeZone fromTimeZone, TimeZone toTimeZone) {
        // TODO
        return null;
    }

    @Override
    public String format(String format) {
        // TODO
        return null;
    }

    @Override
    public String format(String format, Locale locale) {
        // TODO
        return null;
    }

    @Override
    public long getMillisecondsInstant(TimeZone timeZone) {
        // TODO
        return 0;
    }

    @Override
    public long getNanosecondsInstant(TimeZone timeZone) {
        // TODO
        return 0;
    }

    @Override
    public boolean isParseable(String cadidate) {
        // TODO
        return false;
    }

    @Override
    public DateTime minus(DateTime dateTime) {
        // TODO
        return null;
    }

    @Override
    public DateTime plus(DateTime dateTime) {
        // TODO
        return null;
    }

    @Override
    public DateTime truncate(DateTimeUnit unit) {
        // TODO
        return null;
    }

    @Override
    public DateTimeUnit getPrecision() {
        return time.getPrecision();
    }

    @Override
    public boolean unitsAllAbsent(DateTimeUnit... units) {
        // TODO
        return false;
    }

    @Override
    public boolean unitsAllPresent(DateTimeUnit... units) {
        // TODO
        return false;
    }

    @Override
    public int compareTo(DateTime dateTime) {
        // TODO
        return 0;
    }
}
