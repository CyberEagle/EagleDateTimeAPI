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

import br.com.cybereagle.eagledatetime.Time;
import br.com.cybereagle.eagledatetime.internal.DateTimeParser;
import br.com.cybereagle.eagledatetime.internal.TimeImpl;

import java.util.TimeZone;

public class TimeFactory {

    private static final DateTimeParser DATE_TIME_PARSER = new DateTimeParser();

    public static Time create(Integer hour, Integer minute, Integer second, Integer nanoseconds) {
        return new TimeImpl(hour, minute, second, nanoseconds);
    }

    public static Time parse(String rawString) {
        return DATE_TIME_PARSER.parseTime(rawString);
    }

    public static boolean isParseable(String candidate) {
        try {
            DATE_TIME_PARSER.parseTime(candidate);
            return true;
        }
        catch (RuntimeException e){
            return false;
        }
    }

    public static Time forInstant(long miliseconds, TimeZone timeZone) {
        return null;
    }

    public static Time forInstantNanos(long nanoseconds, TimeZone timeZone) {
        return null;
    }

    public static Time now(TimeZone timeZone) {
        return null;
    }

    public static Time getEndOfDay() {
        return null;
    }

    public static Time getStartOfDay() {
        return null;
    }

}
