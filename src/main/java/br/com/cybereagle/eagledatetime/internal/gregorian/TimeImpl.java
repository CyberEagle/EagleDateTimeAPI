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

import java.util.Locale;
import java.util.TimeZone;

public class TimeImpl implements Time {

    private Integer hour;
    private Integer minute;
    private Integer second;
    private Integer nanoseconds;

    public TimeImpl(Integer hour, Integer minute, Integer second, Integer nanoseconds) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.nanoseconds = nanoseconds;
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
    public long numberOfSecondsFrom(Time time) {
        return 0;
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
