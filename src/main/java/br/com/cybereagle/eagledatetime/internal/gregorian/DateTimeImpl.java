/*
 * Copyright 2013 Cyber Eagle
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package br.com.cybereagle.eagledatetime.internal.gregorian;

import br.com.cybereagle.eagledatetime.Date;
import br.com.cybereagle.eagledatetime.DateTime;
import br.com.cybereagle.eagledatetime.Time;
import br.com.cybereagle.eagledatetime.factory.GregorianDateTime;
import br.com.cybereagle.eagledatetime.internal.format.DateTimeFormatter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

import static br.com.cybereagle.eagledatetime.internal.util.DateTimeUtil.*;

public class DateTimeImpl implements DateTime {

    private static final int EQUAL = 0;

    private final Date date;
    private final Time time;

    public DateTimeImpl(Date date, Time time) {
        this.date = date;
        this.time = time;

        validateState();
    }

    private void validateState(){
        if(date == null){
            throw new NullPointerException("Date can't be null");
        }
        if(time == null){
            throw new NullPointerException("Time can't be null");
        }
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
        Time startOfDay = GregorianDateTime.getStartOfDay();
        return new DateTimeImpl(startOfMonth, startOfDay);
    }

    @Override
    public DateTime getEndOfMonth() {
        Date endOfMonth = date.getEndOfMonth();
        Time endOfDay = GregorianDateTime.getEndOfDay();
        return new DateTimeImpl(endOfMonth, endOfDay);
    }

    @Override
    public Integer getModifiedJulianDayNumber() {
        return date.getModifiedJulianDayNumber();
    }

    @Override
    public boolean isInTheFuture(TimeZone timeZone) {
        if (date.isInTheFuture(timeZone)) {
            return true;
        } else if (date.isToday()) {
            return time.isInTheFuture(timeZone);
        }

        return false;
    }

    @Override
    public boolean isInThePast(TimeZone timeZone) {
        if (date.isInThePast(timeZone)) {
            return true;
        } else if (date.isToday()) {
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
    public long numberOfSecondsFrom(DateTime that) {
        long result = 0;
        result = date.numberOfDaysFrom(that.getDate()) * 86400; //just the day portion
        result = result - time.numberOfSeconds() + that.getTime().numberOfSeconds();
        return result;
    }

    @Override
    public DateTime plus(Time time) {
        return null;
    }

    @Override
    public DateTime plus(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer second, Integer nanoseconds) {
        return null;
    }

    @Override
    public DateTime plus(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer second) {
        return null;
    }

    @Override
    public DateTime plus(Integer hour, Integer minute, Integer second, Integer nanoseconds) {
        return null;
    }

    @Override
    public DateTime plus(Integer hour, Integer minute, Integer second) {
        return null;
    }

    @Override
    public DateTime minus(Time time) {
        return null;
    }

    @Override
    public DateTime minus(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer second, Integer nanoseconds) {
        return null;
    }

    @Override
    public DateTime minus(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer second) {
        return null;
    }

    @Override
    public DateTime minus(Integer hour, Integer minute, Integer second, Integer nanoseconds) {
        return null;
    }

    @Override
    public DateTime minus(Integer hour, Integer minute, Integer second) {
        return null;
    }

    @Override
    public String format(String format, List<String> months, List<String> weekdays, List<String> amPmIndicators) {
        DateTimeFormatter dateTimeFormatter = new DateTimeFormatter(format, months, weekdays, amPmIndicators);
        return dateTimeFormatter.format(this);
    }

    @Override
    public DateTime changeTimeZone(TimeZone fromTimeZone, TimeZone toTimeZone) {
        Calendar fromDate = new GregorianCalendar(fromTimeZone);
        fromDate.set(Calendar.YEAR, getYear());
        fromDate.set(Calendar.MONTH, getMonth() - 1);
        fromDate.set(Calendar.DAY_OF_MONTH, getDay());
        fromDate.set(Calendar.HOUR_OF_DAY, getHour());
        fromDate.set(Calendar.MINUTE, getMinute());
        //other items zeroed out here, since they don't matter for time zone calculations
        fromDate.set(Calendar.SECOND, 0);
        fromDate.set(Calendar.MILLISECOND, 0);

        //millisecond precision is OK here, since the seconds/nanoseconds are not part of the calc
        Calendar toDate = new GregorianCalendar(toTimeZone);
        toDate.setTimeInMillis(fromDate.getTimeInMillis());

        return GregorianDateTime.newDateTime(toDate.get(Calendar.YEAR), toDate.get(Calendar.MONTH) + 1,
                toDate.get(Calendar.DAY_OF_MONTH), toDate.get(Calendar.HOUR_OF_DAY), getMinute(), getSecond(), getNanoseconds());
    }

    @Override
    public String format(String format) {
        DateTimeFormatter dateTimeFormatter = new DateTimeFormatter(format);
        return dateTimeFormatter.format(this);
    }

    @Override
    public String format(String format, Locale locale) {
        DateTimeFormatter dateTimeFormatter = new DateTimeFormatter(format, locale);
        return dateTimeFormatter.format(this);
    }

    @Override
    public long getMillisecondsInstant(TimeZone timeZone) {
        Calendar calendar = new GregorianCalendar(timeZone);
        calendar.set(Calendar.YEAR, getYear());
        calendar.set(Calendar.MONTH, getMonth() - 1); // 0-based
        calendar.set(Calendar.DAY_OF_MONTH, getDay());
        calendar.set(Calendar.HOUR_OF_DAY, getHour()); // 0..23
        calendar.set(Calendar.MINUTE, getMinute());
        calendar.set(Calendar.SECOND, getSecond());
        calendar.set(Calendar.MILLISECOND, getNanoseconds() / 1000000);

        return calendar.getTimeInMillis();
    }

    @Override
    public long getNanosecondsInstant(TimeZone timeZone) {
        Integer nanoseconds = getNanoseconds();
        int millis = nanoseconds / MILLION; //integer division truncates, doesn't round
        int nanosRemaining = nanoseconds % MILLION; //0..999,999 - always positive

        //base calculation in millis
        Calendar calendar = new GregorianCalendar(timeZone);
        calendar.set(Calendar.YEAR, getYear());
        calendar.set(Calendar.MONTH, getMonth() - 1); // 0-based
        calendar.set(Calendar.DAY_OF_MONTH, getDay());
        calendar.set(Calendar.HOUR_OF_DAY, getHour()); // 0..23
        calendar.set(Calendar.MINUTE, getMinute());
        calendar.set(Calendar.SECOND, getSecond());
        calendar.set(Calendar.MILLISECOND, millis);

        long baseResult = calendar.getTimeInMillis() * MILLION; // either sign
        //the adjustment for nanos is always positive, toward the future:
        return baseResult + nanosRemaining;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DateTimeImpl)) return false;

        DateTimeImpl dateTime = (DateTimeImpl) o;

        if (!date.equals(dateTime.date)) return false;
        if (!time.equals(dateTime.time)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = date.hashCode();
        result = 31 * result + time.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DateTimeImpl{");
        sb.append("date=").append(date);
        sb.append(", time=").append(time);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int compareTo(DateTime that) {
        if (this == that) return EQUAL;

        int comparison = getDate().compareTo(that.getDate());
        if(comparison != EQUAL) return comparison;

        comparison = getTime().compareTo(that.getTime());
        if(comparison != EQUAL) return comparison;

        return EQUAL;
    }

    /**
     * Always treat de-serialization as a full-blown constructor, by
     * validating the final state of the de-serialized object.
     */
    private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException {
        //always perform the default de-serialization first
        aInputStream.defaultReadObject();
        //no mutable fields in this case
        validateState();
    }

    /**
     * This is the default implementation of writeObject.
     * Customise if necessary.
     */
    private void writeObject(ObjectOutputStream aOutputStream) throws IOException {
        //perform the default serialization for all non-transient, non-static fields
        aOutputStream.defaultWriteObject();
    }
}
