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

public interface Date extends CommonDateTimeOperations<Date> {

    Integer getDay();

    Integer getDayOfYear();

    Integer getMonth();

    Integer getWeekDay();

    Integer getYear();

    Integer getWeekIndex();

    Integer getWeekIndex(Date startingFromDate);

    Integer getNumberOfDaysInMonth();

    Date getStartOfMonth();

    Date getEndOfMonth();

    Integer getModifiedJulianDayNumber();

    boolean isLeapYear();

    boolean isSameDayAs(Date date);

    boolean isToday();

    Date minusDays(Integer numberOfDays);

    Date plusDays(Integer numberOfDays);

    Integer numberOfDaysFrom(Date date);

    Date plus(Integer year, Integer month, Integer day);

    Date minus(Integer year, Integer month, Integer day);

}
