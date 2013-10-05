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

package br.com.cybereagle.eagledatetime;

import br.com.cybereagle.eagledatetime.internal.interfaces.CommonDateTimeOperations;

import java.util.List;
import java.util.TimeZone;

public interface DateTime extends CommonDateTimeOperations<DateTime> {

    DateTime changeTimeZone(TimeZone fromTimeZone, TimeZone toTimeZone);

    Date getDate();

    Time getTime();

    Integer getDay();

    Integer getDayOfYear();

    Integer getMonth();

    Integer getWeekDay();

    Integer getYear();

    Integer getWeekIndex();

    Integer getWeekIndex(DateTime startingFromDate);

    Integer getNumberOfDaysInMonth();

    DateTime getStartOfMonth();

    DateTime getEndOfMonth();

    Integer getModifiedJulianDayNumber();

    boolean isLeapYear();

    boolean isSameDayAs(DateTime dateTime);

    DateTime minusDays(Integer numberOfDays);

    DateTime plusDays(Integer numberOfDays);

    Integer numberOfDaysFrom(DateTime dateTime);

    Integer getHour();

    Integer getMinute();

    Integer getSecond();

    Integer getNanoseconds();

    long numberOfSecondsFrom(DateTime that);

    DateTime plus(Time time);

    DateTime plus(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer second, Integer nanoseconds);

    DateTime plus(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer second);

    DateTime plus(Integer hour, Integer minute, Integer second, Integer nanoseconds);

    DateTime plus(Integer hour, Integer minute, Integer second);

    DateTime minus(Time time);

    DateTime minus(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer second, Integer nanoseconds);

    DateTime minus(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer second);

    DateTime minus(Integer hour, Integer minute, Integer second, Integer nanoseconds);

    DateTime minus(Integer hour, Integer minute, Integer second);

    String format(String format, List<String> months, List<String> weekdays, List<String> amPmIndicators);

}
