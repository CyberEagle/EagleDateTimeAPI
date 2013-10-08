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

package br.com.cybereagle.eagledatetime.internal.util;

import br.com.cybereagle.eagledatetime.exception.ItemOutOfRange;

public class DateTimeUtil {

    public static final int MILLION = 1000000;
    public static int EPOCH_MODIFIED_JD = 2400000;

    public static void checkRange(Integer aValue, int aMin, int aMax, String aName) {
        if (aValue != null) {
            if (aValue < aMin || aValue > aMax) {
                throw new ItemOutOfRange(aName + " is not in the range " + aMin + ".." + aMax + ". Value is:" + aValue);
            }
        }
    }

    public static Integer getNumberOfDaysInMonth(Integer year, Integer month){
        Integer result = null;
        if (year != null && month != null) {
            if (month == 1) {
                result = 31;
            } else if (month == 2) {
                result = isLeapYear(year) ? 29 : 28;
            } else if (month == 3) {
                result = 31;
            } else if (month == 4) {
                result = 30;
            } else if (month == 5) {
                result = 31;
            } else if (month == 6) {
                result = 30;
            } else if (month == 7) {
                result = 31;
            } else if (month == 8) {
                result = 31;
            } else if (month == 9) {
                result = 30;
            } else if (month == 10) {
                result = 31;
            } else if (month == 11) {
                result = 30;
            } else if (month == 12) {
                result = 31;
            } else {
                throw new AssertionError("Month is out of range 1..12:" + month);
            }
        }
        return result;
    }

    public static boolean isLeapYear(Integer year){
        boolean result = false;
        if (year % 100 == 0) {
            // this is a century year
            if (year % 400 == 0) {
                result = true;
            }
        } else if (year % 4 == 0) {
            result = true;
        }
        return result;
    }

}
