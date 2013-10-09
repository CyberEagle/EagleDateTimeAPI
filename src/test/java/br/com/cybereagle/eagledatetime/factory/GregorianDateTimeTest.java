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

import br.com.cybereagle.eagledatetime.Date;
import br.com.cybereagle.eagledatetime.DateTime;
import br.com.cybereagle.eagledatetime.Time;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import static org.fest.assertions.api.Assertions.assertThat;

public class GregorianDateTimeTest {

    private static final TimeZone TIME_ZONE = TimeZone.getTimeZone("UTC");

    private static final int YEAR = 2009;
    private static final int MONTH = 3;
    private static final int DAY = 10;
    private static final int HOUR = 22;
    private static final int MINUTE = 46;
    private static final int SECOND = 30;
    private static final int NANOSECONDS = 532643678;

    @Test
    public void shouldCreateNewDate(){
        Date date = GregorianDateTime.newDate(YEAR, MONTH, DAY);

        verify(date, YEAR, MONTH, DAY);
    }

    @Test
    public void shouldCreateNewTimeWithAndWithoutNanoseconds(){
        Time time = GregorianDateTime.newTime(HOUR, MINUTE, SECOND, NANOSECONDS);

        verify(time, HOUR, MINUTE, SECOND, NANOSECONDS);

        time = GregorianDateTime.newTime(HOUR, MINUTE, SECOND);

        verify(time, HOUR, MINUTE, SECOND, 0);
    }

    @Test
    public void shouldCreateNewDateTimeWithAndWithoutNanoseconds(){
        DateTime dateTime = GregorianDateTime.newDateTime(YEAR, MONTH, DAY, HOUR, MINUTE, SECOND, NANOSECONDS);
        verify(dateTime, YEAR, MONTH, DAY, HOUR, MINUTE, SECOND, NANOSECONDS);

        dateTime = GregorianDateTime.newDateTime(YEAR, MONTH, DAY, HOUR, MINUTE, SECOND);

        verify(dateTime, YEAR, MONTH, DAY, HOUR, MINUTE, SECOND, 0);
    }

    @Test
    public void shouldCreateNewDateTimeWithDateAndTime(){
        Date date = GregorianDateTime.newDate(YEAR, MONTH, DAY);
        Time time = GregorianDateTime.newTime(HOUR, MINUTE, SECOND, NANOSECONDS);

        DateTime dateTime = GregorianDateTime.newDateTime(date, time);
        verify(dateTime, YEAR, MONTH, DAY, HOUR, MINUTE, SECOND, NANOSECONDS);
    }

    @Test
    public void shouldCreateDateTimeForInstant(){
        Calendar calendar = new GregorianCalendar(TIME_ZONE);
        long instant = System.currentTimeMillis();
        calendar.setTimeInMillis(instant);

        DateTime dateTime = GregorianDateTime.forInstant(instant, TIME_ZONE);

        verify(dateTime, calendar);
    }

    @Test
    public void shouldCreateDateTimeForInstantNanos() throws ParseException {
        java.util.Date javaDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss z").parse("2009-03-10T22:46:30 " + TIME_ZONE.getID());
        long nanos = javaDate.getTime() * (long)Math.pow(10, 6) + NANOSECONDS;

        DateTime dateTime = GregorianDateTime.forInstantNanos(nanos, TIME_ZONE);
        verify(dateTime, YEAR, MONTH, DAY, HOUR, MINUTE, SECOND, NANOSECONDS);
    }

    @Test
    public void shouldCreateDateTimeForNow(){
        long millis = System.currentTimeMillis();
        DateTime dateTime = GregorianDateTime.now(TIME_ZONE);

        Calendar calendar = new GregorianCalendar(TIME_ZONE);
        calendar.setTimeInMillis(millis);

        verify(dateTime, calendar);
    }

    @Test
    public void shouldCreateDateForInstant(){
        long millis = System.currentTimeMillis();

        Date date = GregorianDateTime.forInstantDateOnly(millis, TIME_ZONE);
        Calendar calendar = new GregorianCalendar(TIME_ZONE);
        calendar.setTimeInMillis(millis);

        verify(date, calendar);
    }

    @Test
    public void shouldCreateDateForToday(){
        long millis = System.currentTimeMillis();

        Date date = GregorianDateTime.today(TIME_ZONE);
        Calendar calendar = new GregorianCalendar(TIME_ZONE);
        calendar.setTimeInMillis(millis);

        verify(date, calendar);
    }

    @Test
    public void shouldCreateTimeForInstant(){
        long millis = System.currentTimeMillis();

        Time time = GregorianDateTime.forInstantTimeOnly(millis, TIME_ZONE);
        Calendar calendar = new GregorianCalendar(TIME_ZONE);
        calendar.setTimeInMillis(millis);

        verify(time, calendar);
    }

    @Test
    public void shouldCreateTimeForInstantNanos() throws ParseException {
        java.util.Date javaDate = new SimpleDateFormat("HH:mm:ss z").parse("22:46:30 " + TIME_ZONE.getID());
        long nanos = javaDate.getTime() * (long)Math.pow(10, 6) + NANOSECONDS;

        Time time = GregorianDateTime.forInstantNanosTimeOnly(nanos, TIME_ZONE);
        verify(time, HOUR, MINUTE, SECOND, NANOSECONDS);
    }

    @Test
    public void shouldCreateTimeForNow(){
        long millis = System.currentTimeMillis();
        Time time = GregorianDateTime.nowTimeOnly(TIME_ZONE);

        Calendar calendar = new GregorianCalendar(TIME_ZONE);
        calendar.setTimeInMillis(millis);

        verify(time, calendar);
    }

    @Test
    public void shouldCreateTimeForEndOfDay(){
        Time time = GregorianDateTime.getEndOfDay();

        verify(time, 23, 59, 59, 999999999);
    }

    @Test
    public void shouldCreateTimeForStartOfDay(){
        Time time = GregorianDateTime.getStartOfDay();

        verify(time, 0, 0, 0, 0);
    }

    @Test
    public void shouldCreateDateFromJulianDayNumberAtNoon(){
        // TODO
    }

    @Test
    public void shouldParseDate(){
        Date date = GregorianDateTime.parseDate("2009-03-10");

        verify(date, YEAR, MONTH, DAY);
    }

    @Test
    public void shouldParseTime(){
        Time timeWithoutDecimalSeconds = GregorianDateTime.parseTime("22:46:30");
        Time timeWithDecimalSeconds = GregorianDateTime.parseTime("22:46:30.532643678");

        verify(timeWithoutDecimalSeconds, HOUR, MINUTE, SECOND, 0);
        verify(timeWithDecimalSeconds, HOUR, MINUTE, SECOND, NANOSECONDS);
    }

    @Test
    public void shouldParseDateTime(){
        DateTime dateTimeWithoutDecimalSeconds = GregorianDateTime.parseDateTime("2009-03-10 22:46:30");
        DateTime dateTimeWithDecimalSeconds = GregorianDateTime.parseDateTime("2009-03-10 22:46:30.532643678");
        DateTime isoDateTimeWithoutDecimalSeconds = GregorianDateTime.parseDateTime("2009-03-10T22:46:30");
        DateTime isoDateTimeWithDecimalSeconds = GregorianDateTime.parseDateTime("2009-03-10T22:46:30.532643678");

        verify(dateTimeWithoutDecimalSeconds, YEAR, MONTH, DAY, HOUR, MINUTE, SECOND, 0);
        verify(dateTimeWithDecimalSeconds, YEAR, MONTH, DAY, HOUR, MINUTE, SECOND, NANOSECONDS);
        verify(isoDateTimeWithoutDecimalSeconds, YEAR, MONTH, DAY, HOUR, MINUTE, SECOND, 0);
        verify(isoDateTimeWithDecimalSeconds, YEAR, MONTH, DAY, HOUR, MINUTE, SECOND, NANOSECONDS);
    }

    private void verify(Date date, Calendar calendar) {
        verify(date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1,
                calendar.get(Calendar.DAY_OF_MONTH));
    }

    private void verify(Time time, Calendar calendar) {
        verify(time, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND), calendar.get(Calendar.MILLISECOND) * (int)Math.pow(10, 6));
    }

    private void verify(DateTime dateTime, Calendar calendar) {
        verify(dateTime, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1,
                calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR),
                calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND),
                calendar.get(Calendar.MILLISECOND) * (int)Math.pow(10, 6));
    }

    private void verify(Date date, Integer year, Integer month, Integer day) {
        assertThat(date.getYear()).isEqualTo(year);
        assertThat(date.getMonth()).isEqualTo(month);
        assertThat(date.getDay()).isEqualTo(day);
    }

    private void verify(Time time, Integer hour, Integer minute, Integer second, Integer nanoseconds) {
        assertThat(time.getHour()).isEqualTo(hour);
        assertThat(time.getMinute()).isEqualTo(minute);
        assertThat(time.getSecond()).isEqualTo(second);
        assertThat(time.getNanoseconds()).isEqualTo(nanoseconds);
    }

    private void verify(DateTime dateTime, Integer year, Integer month, Integer day,
                        Integer hour, Integer minute, Integer second, Integer nanoseconds) {
        assertThat(dateTime.getYear()).isEqualTo(year);
        assertThat(dateTime.getMonth()).isEqualTo(month);
        assertThat(dateTime.getDay()).isEqualTo(day);
        assertThat(dateTime.getHour()).isEqualTo(hour);
        assertThat(dateTime.getMinute()).isEqualTo(minute);
        assertThat(dateTime.getSecond()).isEqualTo(second);
        assertThat(dateTime.getNanoseconds()).isEqualTo(nanoseconds);
    }

}
