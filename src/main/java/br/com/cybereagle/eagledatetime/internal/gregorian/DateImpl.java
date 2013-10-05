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
import br.com.cybereagle.eagledatetime.exception.ItemOutOfRange;
import br.com.cybereagle.eagledatetime.factory.GregorianDateTime;
import br.com.cybereagle.eagledatetime.internal.format.DateTimeAdapter;
import br.com.cybereagle.eagledatetime.internal.format.DateTimeFormatter;
import br.com.cybereagle.eagledatetime.internal.util.DateTimeUtil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

import static br.com.cybereagle.eagledatetime.internal.util.DateTimeUtil.*;

public class DateImpl implements Date {

    private static final int EQUAL = 0;

    private Integer year;
    private Integer month;
    private Integer day;

    public DateImpl(Integer year, Integer month, Integer day) {
        this.year = year;
        this.month = month;
        this.day = day;
        validateState();
    }

    private void validateState(){
        if(year == null){
            throw new NullPointerException("Year can't be null");
        }
        if(month == null){
            throw new NullPointerException("Month can't be null");
        }
        if(day == null){
            throw new NullPointerException("Day can't be null");
        }

        checkRange(year, 1, 9999, "Year");
        checkRange(month, 1, 12, "Month");
        checkRange(day, 1, 31, "Day");
        checkNumDaysInMonth(year, month, day);
    }

    private void checkNumDaysInMonth(Integer year, Integer month, Integer day) {
        Integer numberOfDaysInMonth = DateTimeUtil.getNumberOfDaysInMonth(year, month);
        if (day > numberOfDaysInMonth) {
            throw new ItemOutOfRange("The day-of-the-month value '" + day + "' exceeds the number of days in the month: " + numberOfDaysInMonth);
        }
    }

    @Override
    public Integer getDay() {
        return day;
    }

    @Override
    public Integer getDayOfYear() {
        int k = isLeapYear() ? 1 : 2;
        Integer result = ((275 * month) / 9) - k * ((month + 9) / 12) + day - 30; // integer division
        return result;
    }

    @Override
    public Integer getMonth() {
        return month;
    }

    @Override
    public Integer getWeekDay() {
        int dayNumber = calculateJulianDayNumberAtNoon() + 1;
        int index = dayNumber % 7;
        return index + 1;
    }

    @Override
    public Integer getYear() {
        return year;
    }

    @Override
    public Integer getWeekIndex() {
        Date start = GregorianDateTime.newDate(2000, 1, 2);
        return getWeekIndex(start);
    }

    @Override
    public Integer getWeekIndex(Date startingFromDate) {
        int diff = getModifiedJulianDayNumber() - startingFromDate.getModifiedJulianDayNumber();
        return (diff / 7) + 1; // integer division
    }

    @Override
    public Integer getNumberOfDaysInMonth() {
        return DateTimeUtil.getNumberOfDaysInMonth(year, month);
    }

    @Override
    public Date getStartOfMonth() {
        return GregorianDateTime.newDate(year, month, 1);
    }

    @Override
    public Date getEndOfMonth() {
        return GregorianDateTime.newDate(year, month, getNumberOfDaysInMonth());
    }

    @Override
    public Integer getModifiedJulianDayNumber() {
        return calculateJulianDayNumberAtNoon() - 1 - EPOCH_MODIFIED_JD;
    }

    /**
     * Return a the whole number, with no fraction.
     * The JD at noon is 1 more than the JD at midnight.
     */
    private int calculateJulianDayNumberAtNoon() {
        //http://www.hermetic.ch/cal_stud/jdn.htm
        return (1461 * (year + 4800 + (month - 14) / 12)) / 4 + (367 * (month - 2 - 12 * ((month - 14) / 12))) / 12 - (3 * ((year + 4900 + (month - 14) / 12) / 100)) / 4 + day - 32075;
    }

    @Override
    public boolean isInTheFuture(TimeZone timeZone) {
        return GregorianDateTime.today(timeZone).compareTo(this) < 0;
    }

    @Override
    public boolean isInThePast(TimeZone timeZone) {
        return GregorianDateTime.today(timeZone).compareTo(this) > 0;
    }

    @Override
    public boolean isLeapYear() {
        return DateTimeUtil.isLeapYear(year);
    }

    @Override
    public boolean isSameDayAs(Date that) {
        return equals(that);
    }

    @Override
    public boolean isToday() {
        return false;
    }

    @Override
    public Date minusDays(Integer numberOfDays) {
        return plusDays(-1 * numberOfDays);
    }

    @Override
    public Date plusDays(Integer numberOfDays) {
        int thisJDAtNoon = getModifiedJulianDayNumber() + 1 + EPOCH_MODIFIED_JD;
        int resultJD = thisJDAtNoon + numberOfDays;
        Date datePortion = GregorianDateTime.fromJulianDayNumberAtNoon(resultJD);
        return GregorianDateTime.newDate(datePortion.getYear(), datePortion.getMonth(), datePortion.getDay());
    }

    @Override
    public Integer numberOfDaysFrom(Date that) {
        return that.getModifiedJulianDayNumber() - this.getModifiedJulianDayNumber();
    }

    @Override
    public Date plus(Integer year, Integer month, Integer day) {
        return null;
    }

    @Override
    public Date minus(Integer year, Integer month, Integer day) {
        return null;
    }

    @Override
    public String format(String format, List<String> months, List<String> weekdays) {
        DateTimeFormatter dateTimeFormatter = new DateTimeFormatter(format, months, weekdays, null);
        return dateTimeFormatter.format(new DateTimeAdapter(this));
    }

    @Override
    public String format(String format) {
        DateTimeFormatter dateTimeFormatter = new DateTimeFormatter(format);
        return dateTimeFormatter.format(new DateTimeAdapter(this));
    }

    @Override
    public String format(String format, Locale locale) {
        DateTimeFormatter dateTimeFormatter = new DateTimeFormatter(format, locale);
        return dateTimeFormatter.format(new DateTimeAdapter(this));
    }

    @Override
    public long getMillisecondsInstant(TimeZone timeZone) {
        Calendar calendar = new GregorianCalendar(timeZone);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1); // 0-based
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, 0); // 0..23
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTimeInMillis();
    }

    @Override
    public long getNanosecondsInstant(TimeZone timeZone) {
        // these are always positive:
        Integer year = getYear();
        Integer month = getMonth();
        Integer day = getDay();

        //base calculation in millis
        Calendar calendar = new GregorianCalendar(timeZone);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1); // 0-based
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, 0); // 0..23
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTimeInMillis() * MILLION;
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
    public int compareTo(Date that) {
        if (this == that) return EQUAL;

        int comparison = year.compareTo(that.getYear());
        if(comparison != EQUAL) return comparison;

        comparison = month.compareTo(that.getMonth());
        if(comparison != EQUAL) return comparison;

        comparison = day.compareTo(that.getDay());
        if(comparison != EQUAL) return comparison;

        return EQUAL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Date)) return false;

        Date date = (Date) o;

        if (!day.equals(date.getDay())) return false;
        if (!month.equals(date.getMonth())) return false;
        if (!year.equals(date.getYear())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = year.hashCode();
        result = 31 * result + month.hashCode();
        result = 31 * result + day.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DateImpl{");
        sb.append("year=").append(year);
        sb.append(", month=").append(month);
        sb.append(", day=").append(day);
        sb.append('}');
        return sb.toString();
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
