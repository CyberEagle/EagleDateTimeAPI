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

import br.com.cybereagle.eagledatetime.Time;
import br.com.cybereagle.eagledatetime.factory.GregorianDateTime;
import br.com.cybereagle.eagledatetime.internal.format.DateTimeAdapter;
import br.com.cybereagle.eagledatetime.internal.format.DateTimeFormatter;
import br.com.cybereagle.eagledatetime.internal.util.DateTimeUtil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

import static br.com.cybereagle.eagledatetime.internal.util.DateTimeUtil.*;

public class TimeImpl implements Time {

    private static final int EQUAL = 0;

    private Integer hour;
    private Integer minute;
    private Integer second;
    private Integer nanoseconds;

    public TimeImpl(Integer hour, Integer minute, Integer second, Integer nanoseconds) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.nanoseconds = nanoseconds;

        validateState();
    }

    private void validateState(){
        if(hour == null){
            throw new NullPointerException("Hour can't be null");
        }
        if(minute == null){
            throw new NullPointerException("Minute can't be null");
        }
        if(second == null){
            throw new NullPointerException("Second can't be null");
        }
        if(nanoseconds == null){
            throw new NullPointerException("Nanosecond can't be null");
        }

        checkRange(hour, 0, 23, "Hour");
        checkRange(minute, 0, 59, "Minute");
        checkRange(second, 0, 59, "Second");
        checkRange(nanoseconds, 0, 999999999, "Nanosecond");
    }

    @Override
    public Integer getHour() {
        return hour;
    }

    @Override
    public Integer getMinute() {
        return minute;
    }

    @Override
    public Integer getSecond() {
        return second;
    }

    @Override
    public Integer getNanoseconds() {
        return nanoseconds;
    }

    @Override
    public int numberOfSecondsFrom(Time that) {
        return this.numberOfSeconds() - that.numberOfSeconds();
    }

    @Override
    public int numberOfSeconds(){
        int result = 0;
        if (second != null) {
            result = result + second;
        }
        if (minute != null) {
            result = result + 60 * minute;
        }
        if (hour != null) {
            result = result + 3600 * hour;
        }
        return result;
    }

    @Override
    public Time plus(Integer hour, Integer minute, Integer second, Integer nanoseconds) {
        return null;
    }

    @Override
    public Time plus(Integer hour, Integer minute, Integer second) {
        return null;
    }

    @Override
    public Time minus(Integer hour, Integer minute, Integer second, Integer nanoseconds) {
        return null;
    }

    @Override
    public Time minus(Integer hour, Integer minute, Integer second) {
        return null;
    }

    @Override
    public String format(String format, List<String> amPmIndicators) {
        DateTimeFormatter dateTimeFormatter = new DateTimeFormatter(format, null, null, amPmIndicators);
        return dateTimeFormatter.format(new DateTimeAdapter(this));
    }

    @Override
    public Time changeTimeZone(TimeZone fromTimeZone, TimeZone toTimeZone) {
        return null;
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
        calendar.set(Calendar.HOUR_OF_DAY, hour); // 0..23
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, nanoseconds / 1000000);

        return calendar.getTimeInMillis();
    }

    @Override
    public long getNanosecondsInstant(TimeZone timeZone) {
        int millis = nanoseconds / MILLION; //integer division truncates, doesn't round
        int nanosRemaining = nanoseconds % MILLION; //0..999,999 - always positive

        //base calculation in millis
        Calendar calendar = new GregorianCalendar(timeZone);
        calendar.set(Calendar.HOUR_OF_DAY, hour); // 0..23
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, millis);

        long baseResult = calendar.getTimeInMillis() * MILLION; // either sign
        //the adjustment for nanos is always positive, toward the future:
        return baseResult + nanosRemaining;
    }

    @Override
    public Time minus(Time that) {
        return null;
    }

    @Override
    public Time plus(Time that) {
        return null;
    }

    @Override
    public boolean isInTheFuture(TimeZone timeZone) {
        return GregorianDateTime.nowTimeOnly(timeZone).compareTo(this) < 0;
    }

    @Override
    public boolean isInThePast(TimeZone timeZone) {
        return GregorianDateTime.nowTimeOnly(timeZone).compareTo(this) > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimeImpl)) return false;

        TimeImpl time = (TimeImpl) o;

        if (!hour.equals(time.hour)) return false;
        if (!minute.equals(time.minute)) return false;
        if (!nanoseconds.equals(time.nanoseconds)) return false;
        if (!second.equals(time.second)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = hour.hashCode();
        result = 31 * result + minute.hashCode();
        result = 31 * result + second.hashCode();
        result = 31 * result + nanoseconds.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TimeImpl{");
        sb.append("hour=").append(hour);
        sb.append(", minute=").append(minute);
        sb.append(", second=").append(second);
        sb.append(", nanoseconds=").append(nanoseconds);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int compareTo(Time that) {
        if (this == that) return EQUAL;

        int comparison = hour.compareTo(that.getHour());
        if(comparison != EQUAL) return comparison;

        comparison = minute.compareTo(that.getMinute());
        if(comparison != EQUAL) return comparison;

        comparison = second.compareTo(that.getSecond());
        if(comparison != EQUAL) return comparison;

        comparison = nanoseconds.compareTo(that.getNanoseconds());
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
