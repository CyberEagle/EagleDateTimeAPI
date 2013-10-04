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
import br.com.cybereagle.eagledatetime.internal.DateTimeImpl;
import br.com.cybereagle.eagledatetime.internal.DateTimeParser;

import java.util.TimeZone;

public class DateTimeFactory {

    private static final DateTimeParser DATE_TIME_PARSER = new DateTimeParser();

    public static DateTime create(Date date, Time time) {
        return new DateTimeImpl(date, time);
    }

    public static DateTime create(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer second, Integer nanoseconds) {
        return new DateTimeImpl(DateFactory.create(year, month, day), TimeFactory.create(hour, minute, second, nanoseconds));
    }

    public static DateTime parse(String rawString) {
        return DATE_TIME_PARSER.parseDateTime(rawString);
    }

    public static boolean isParseable(String candidate) {
        try {
            DATE_TIME_PARSER.parseDateTime(candidate);
            return true;
        }
        catch (RuntimeException e){
            return false;
        }
    }

    public static DateTime forInstant(long miliseconds, TimeZone timeZone) {
        return null;
    }

    public static DateTime forInstantNanos(long nanoseconds, TimeZone timeZone) {
        return null;
    }

    public static DateTime now(TimeZone timeZone) {
        return null;
    }

}
