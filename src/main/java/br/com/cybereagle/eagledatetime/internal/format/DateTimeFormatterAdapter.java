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

package br.com.cybereagle.eagledatetime.internal.format;

import br.com.cybereagle.eagledatetime.Date;
import br.com.cybereagle.eagledatetime.DateTime;
import br.com.cybereagle.eagledatetime.DayOverflow;
import br.com.cybereagle.eagledatetime.Time;
import br.com.cybereagle.eagledatetime.exception.InvalidFormatForDateException;
import br.com.cybereagle.eagledatetime.exception.InvalidFormatForTimeException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * It's an adapter to use {@link Date} and {@link Time} with the {@link br.com.cybereagle.eagledatetime.internal.format.DateTimeFormatter}.
 */
public class DateTimeFormatterAdapter implements DateTime {

    private Date date;
    private Time time;

    public DateTimeFormatterAdapter(Date date){
        this.date = date;
    }

    public DateTimeFormatterAdapter(Time time){
        this.time = time;
    }

    @Override
    public Integer getYear() {
        if(date == null){
            throw new InvalidFormatForTimeException("There's no year to be used");
        }
        return date.getYear();
    }

    @Override
    public Integer getMonth() {
        if(date == null){
            throw new InvalidFormatForTimeException("There's no month to be used");
        }
        return date.getMonth();
    }

    @Override
    public Integer getDay() {
        if(date == null){
            throw new InvalidFormatForTimeException("There's no day to be used");
        }
        return date.getDay();
    }

    @Override
    public Integer getWeekDay() {
        if(date == null){
            throw new InvalidFormatForTimeException("There's no week day to be used");
        }
        return date.getWeekDay();
    }

    @Override
    public Integer getHour() {
        if(time == null){
            throw new InvalidFormatForDateException("There's no hour to be used");
        }
        return time.getHour();
    }

    @Override
    public Integer getMinute() {
        if(time == null){
            throw new InvalidFormatForDateException("There's no minute to be used");
        }
        return time.getMinute();
    }

    @Override
    public Integer getSecond() {
        if(time == null){
            throw new InvalidFormatForDateException("There's no second to be used");
        }
        return time.getSecond();
    }

    @Override
    public Integer getNanoseconds() {
        if(time == null){
            throw new InvalidFormatForDateException("There're no nanoseconds to be used");
        }
        return time.getNanoseconds();
    }

    @Override
    public DateTime changeTimeZone(TimeZone fromTimeZone, TimeZone toTimeZone) {
        throw new NotImplementedException();
    }

    @Override
    public Date getDate() {
        throw new NotImplementedException();
    }

    @Override
    public Time getTime() {
        throw new NotImplementedException();
    }

    @Override
    public Integer getDayOfYear() {
        throw new NotImplementedException();
    }

    @Override
    public Integer getWeekIndex() {
        throw new NotImplementedException();
    }

    @Override
    public Integer getWeekIndex(DateTime startingFromDate) {
        throw new NotImplementedException();
    }

    @Override
    public Integer getNumberOfDaysInMonth() {
        throw new NotImplementedException();
    }

    @Override
    public DateTime getStartOfMonth() {
        throw new NotImplementedException();
    }

    @Override
    public DateTime getEndOfMonth() {
        throw new NotImplementedException();
    }

    @Override
    public Integer getModifiedJulianDayNumber() {
        throw new NotImplementedException();
    }

    @Override
    public boolean isLeapYear() {
        throw new NotImplementedException();
    }

    @Override
    public boolean isSameDayAs(DateTime dateTime) {
        throw new NotImplementedException();
    }

    @Override
    public DateTime minusDays(Integer numberOfDays) {
        throw new NotImplementedException();
    }

    @Override
    public DateTime plusDays(Integer numberOfDays) {
        throw new NotImplementedException();
    }

    @Override
    public Integer numberOfDaysFrom(DateTime dateTime) {
        throw new NotImplementedException();
    }

    @Override
    public long numberOfSecondsFrom(DateTime that) {
        throw new NotImplementedException();
    }

    @Override
    public DateTime plus(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer second, Integer nanoseconds, DayOverflow dayOverflow) {
        throw new NotImplementedException();
    }

    @Override
    public DateTime plus(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer second, DayOverflow dayOverflow) {
        throw new NotImplementedException();
    }

    @Override
    public DateTime plus(Integer hour, Integer minute, Integer second, Integer nanoseconds, DayOverflow dayOverflow) {
        throw new NotImplementedException();
    }

    @Override
    public DateTime plus(Integer hour, Integer minute, Integer second, DayOverflow dayOverflow) {
        throw new NotImplementedException();
    }

    @Override
    public DateTime minus(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer second, Integer nanoseconds, DayOverflow dayOverflow) {
        throw new NotImplementedException();
    }

    @Override
    public DateTime minus(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer second, DayOverflow dayOverflow) {
        throw new NotImplementedException();
    }

    @Override
    public DateTime minus(Integer hour, Integer minute, Integer second, Integer nanoseconds, DayOverflow dayOverflow) {
        throw new NotImplementedException();
    }

    @Override
    public DateTime minus(Integer hour, Integer minute, Integer second, DayOverflow dayOverflow) {
        throw new NotImplementedException();
    }

    @Override
    public String format(String format, List<String> months, List<String> weekdays, List<String> amPmIndicators) {
        throw new NotImplementedException();
    }

    @Override
    public String format(String format) {
        throw new NotImplementedException();
    }

    @Override
    public String format(String format, Locale locale) {
        throw new NotImplementedException();
    }

    @Override
    public long getMillisecondsInstant(TimeZone timeZone) {
        throw new NotImplementedException();
    }

    @Override
    public long getNanosecondsInstant(TimeZone timeZone) {
        throw new NotImplementedException();
    }

    @Override
    public boolean isInTheFuture(TimeZone timeZone) {
        throw new NotImplementedException();
    }

    @Override
    public boolean isInThePast(TimeZone timeZone) {
        throw new NotImplementedException();
    }

    @Override
    public int compareTo(DateTime dateTime) {
        throw new NotImplementedException();
    }
}
