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

public interface Time extends CommonDateTimeOperations<Time> {

    Time changeTimeZone(TimeZone fromTimeZone, TimeZone toTimeZone);

    Integer getHour();

    Integer getMinute();

    Integer getSecond();

    Integer getNanoseconds();

    int numberOfSecondsFrom(Time time);

    int numberOfSeconds();

    Time plus(Integer hour, Integer minute, Integer second, Integer nanoseconds);

    Time plus(Integer hour, Integer minute, Integer second);

    Time minus(Integer hour, Integer minute, Integer second, Integer nanoseconds);

    Time minus(Integer hour, Integer minute, Integer second);

    String format(String format, List<String> amPmIndicators);

}
