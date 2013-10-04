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

package br.com.cybereagle.eagledatetime.internal.interfaces;

import br.com.cybereagle.eagledatetime.DateTimeUnit;

import java.util.Locale;
import java.util.TimeZone;

public interface CommonDateTimeOperations<T> extends Comparable<T> {

    T changeTimeZone(TimeZone fromTimeZone, TimeZone toTimeZone);

    String format(String format);

    String format(String format, Locale locale);

    long getMillisecondsInstant(TimeZone timeZone);

    long getNanosecondsInstant(TimeZone timeZone);

    boolean isParseable(String cadidate);

    T minus(T object);

    T plus(T object);

    T truncate(DateTimeUnit unit);

    DateTimeUnit getPrecision();

    boolean unitsAllAbsent(DateTimeUnit... units);

    boolean unitsAllPresent(DateTimeUnit... units);

    boolean isInTheFuture(TimeZone timeZone);

    boolean isInThePast(TimeZone timeZone);

}
