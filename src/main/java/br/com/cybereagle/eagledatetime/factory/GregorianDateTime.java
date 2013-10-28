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

package br.com.cybereagle.eagledatetime.factory;

import br.com.cybereagle.eagledatetime.*;
import br.com.cybereagle.eagledatetime.internal.DefaultCurrentTimeService;
import br.com.cybereagle.eagledatetime.internal.gregorian.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import static br.com.cybereagle.eagledatetime.internal.util.DateTimeUtil.*;

public class GregorianDateTime {

    private static CurrentTimeService currentTimeService;

    static {
        resetDefaultCurrentTimeService();
    }

    public static void setCurrentTimeService(CurrentTimeService currentTimeService) {
        GregorianDateTime.currentTimeService = currentTimeService;
    }

    public static void resetDefaultCurrentTimeService(){
        GregorianDateTime.currentTimeService = new DefaultCurrentTimeService();
    }

    public static DateTime newDateTime(Date date, Time time) {
        return new DateTimeImpl(date, time);
    }

    public static DateTime newDateTime(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer second, Integer nanoseconds) {
        return new DateTimeImpl(newDate(year, month, day), newTime(hour, minute, second, nanoseconds));
    }

    public static DateTime newDateTime(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer second) {
        return newDateTime(year, month, day, hour, minute, second, 0);
    }

    public static AmountOfTime newAmountOfTime(Integer years, Integer months, Integer days, Integer hours, Integer minutes, Integer seconds, Integer nanoseconds){
        return new AmountOfTimeImpl(years, months, days, hours, minutes, seconds, nanoseconds);
    }

    public static DateTime parseDateTime(String rawString) {
        DateTimeParser dateTimeParser = new DateTimeParser();
        return dateTimeParser.parseDateTime(rawString);
    }

    public static Date newDate(Integer year, Integer month, Integer day) {
        return new DateImpl(year, month, day);
    }

    public static Date parseDate(String rawString) {
        DateTimeParser dateTimeParser = new DateTimeParser();
        return dateTimeParser.parseDate(rawString);
    }

    public static Time newTime(Integer hour, Integer minute, Integer second, Integer nanoseconds) {
        return new TimeImpl(hour, minute, second, nanoseconds);
    }

    public static Time newTime(Integer hour, Integer minute, Integer second) {
        return new TimeImpl(hour, minute, second, 0);
    }

    public static Time parseTime(String rawString) {
        DateTimeParser dateTimeParser = new DateTimeParser();
        return dateTimeParser.parseTime(rawString);
    }

    public static boolean isParseableForDateTime(String candidate) {
        try {
            DateTimeParser dateTimeParser = new DateTimeParser();
            dateTimeParser.parseDateTime(candidate);
            return true;
        }
        catch (RuntimeException e){
            return false;
        }
    }

    public static boolean isParseableForDate(String candidate) {
        try {
            DateTimeParser dateTimeParser = new DateTimeParser();
            dateTimeParser.parseDate(candidate);
            return true;
        }
        catch (RuntimeException e){
            return false;
        }
    }

    public static boolean isParseableForTime(String candidate) {
        try {
            DateTimeParser dateTimeParser = new DateTimeParser();
            dateTimeParser.parseTime(candidate);
            return true;
        }
        catch (RuntimeException e){
            return false;
        }
    }

    public static DateTime forInstant(long millis, TimeZone timeZone) {
        Calendar calendar = new GregorianCalendar(timeZone);
        calendar.setTimeInMillis(millis);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // 0-based
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY); // 0..23
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int nanoseconds = calendar.get(Calendar.MILLISECOND) * 1000 * 1000;
        return newDateTime(year, month, day, hour, minute, second, nanoseconds);
    }

    public static DateTime forInstantNanos(long nanoseconds, TimeZone timeZone) {
        MillisAndNanosRemainingCalculator millisAndNanosRemainingCalculator = new MillisAndNanosRemainingCalculator(nanoseconds).calculate();
        long millis = millisAndNanosRemainingCalculator.getMillis();
        long nanosRemaining = millisAndNanosRemainingCalculator.getNanosRemaining();


        //base calculation in millis
        Calendar calendar = new GregorianCalendar(timeZone);
        calendar.setTimeInMillis(millis);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // 0-based
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY); // 0..23
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int milliseconds = calendar.get(Calendar.MILLISECOND);

        DateTime withoutNanos = newDateTime(year, month, day, hour, minute, second, milliseconds * MILLION);
        //adjust for nanos - this cast is acceptable, because the value's range is 0..999,999:
        DateTime withNanos = withoutNanos.plus(0, 0, 0, (int) nanosRemaining, DayOverflow.SPILLOVER);
        return withNanos;
    }

    public static DateTime now(TimeZone timeZone) {
        return forInstant(currentTimeService.currentTimeMillis(), timeZone);
    }

    public static Date forInstantDateOnly(long millis, TimeZone timeZone) {
        Calendar calendar = new GregorianCalendar(timeZone);
        calendar.setTimeInMillis(millis);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // 0-based
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return newDate(year, month, day);
    }

    public static Date today(TimeZone timeZone) {
        return forInstantDateOnly(currentTimeService.currentTimeMillis(), timeZone);
    }

    public static Time forInstantTimeOnly(long millis, TimeZone timeZone) {
        Calendar calendar = new GregorianCalendar(timeZone);
        calendar.setTimeInMillis(millis);
        int hour = calendar.get(Calendar.HOUR_OF_DAY); // 0..23
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int nanoseconds = calendar.get(Calendar.MILLISECOND) * 1000 * 1000;

        return newTime(hour, minute, second, nanoseconds);
    }

    public static Time forInstantNanosTimeOnly(long nanoseconds, TimeZone timeZone) {
        MillisAndNanosRemainingCalculator millisAndNanosRemainingCalculator = new MillisAndNanosRemainingCalculator(nanoseconds).calculate();
        long millis = millisAndNanosRemainingCalculator.getMillis();
        long nanosRemaining = millisAndNanosRemainingCalculator.getNanosRemaining();


        //base calculation in millis
        Calendar calendar = new GregorianCalendar(timeZone);
        calendar.setTimeInMillis(millis);
        int hour = calendar.get(Calendar.HOUR_OF_DAY); // 0..23
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int milliseconds = calendar.get(Calendar.MILLISECOND);

        Time withoutNanos = newTime(hour, minute, second, milliseconds * MILLION);
        //adjust for nanos - this cast is acceptable, because the value's range is 0..999,999:
        Time withNanos = withoutNanos.plus(0, 0, 0, (int) nanosRemaining);
        return withNanos;
    }

    public static Time nowTimeOnly(TimeZone timeZone) {
        return forInstantTimeOnly(currentTimeService.currentTimeMillis(), timeZone);
    }

    public static Time getEndOfDay() {
        return newTime(23, 59, 59, 999999999);
    }

    public static Time getStartOfDay() {
        return newTime(0, 0, 0, 0);
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

    private static class MillisAndNanosRemainingCalculator {
        private long nanoseconds;
        private long millis;
        private long nanosRemaining;

        public MillisAndNanosRemainingCalculator(long nanoseconds) {
            this.nanoseconds = nanoseconds;
        }

        public long getMillis() {
            return millis;
        }

        public long getNanosRemaining() {
            return nanosRemaining;
        }

        public MillisAndNanosRemainingCalculator calculate() {
            //these items can be of either sign
            millis = nanoseconds / MILLION;
            nanosRemaining = nanoseconds % MILLION;
            //when negative: go to the previous millis, and take the complement of nanosRemaining
            if (nanoseconds < 0) {
                millis = millis - 1;
                nanosRemaining = MILLION + nanosRemaining; //-1 remaining coerced to 999,999
            }
            return this;
        }
    }
}
