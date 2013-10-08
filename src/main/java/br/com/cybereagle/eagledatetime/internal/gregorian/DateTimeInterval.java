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

import br.com.cybereagle.eagledatetime.Date;
import br.com.cybereagle.eagledatetime.DateTime;
import br.com.cybereagle.eagledatetime.DayOverflow;
import br.com.cybereagle.eagledatetime.Time;
import br.com.cybereagle.eagledatetime.internal.util.DateTimeUtil;

/**
 * Helper class for adding intervals of time.
 * The mental model of this class is similar to that of a car's odometer.
 */
public final class DateTimeInterval {

    private static final int MIN = 0;
    private static final int MAX = 9999;
    private static final int MIN_NANOS = 0;
    // PRIVATE
    private static final int MAX_NANOS = 999999999;
    private static final boolean PLUS = true;
    private static final boolean MINUS = false;
    //the base date to which the interval is calculated
    private boolean isPlus;
    private DayOverflow dayOverflow;
    //the various increments
    private int yearIncrement;
    private int monthIncrement;
    private int dayIncrement;
    private int hourIncrement;
    private int minuteIncrement;
    private int secondIncrement;
    private int nanosecondIncrement;
    //work area for the final result - starts off with values from base date fFrom
    private Integer resultYear;
    private Integer resultMonth;
    private Integer resultDay;
    private Integer resultHour;
    private Integer resultMinute;
    private Integer resultSecond;
    private Integer resultNanoseconds;

    public DateTimeInterval(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer second, Integer nanoseconds, DayOverflow dayOverflow) {
        this.dayOverflow = dayOverflow;

        resultYear = year;
        resultMonth = month;
        resultDay = day;
        resultHour = hour;
        resultMinute = minute;
        resultSecond = second;
        resultNanoseconds = nanoseconds;
    }

    public DateTimeInterval(DateTime dateTime, DayOverflow dayOverflow) {
        this(dateTime.getYear(), dateTime.getMonth(), dateTime.getDay(), dateTime.getHour(), dateTime.getMinute(), dateTime.getSecond(), dateTime.getNanoseconds(), dayOverflow);
    }

    public DateTimeInterval(Integer year, Integer month, Integer day, DayOverflow dayOverflow) {
        this(year, month, day, null, null, null, null, dayOverflow);
    }

    public DateTimeInterval(Date date, DayOverflow dayOverflow) {
        this(date.getYear(), date.getMonth(), date.getDay(), dayOverflow);
    }

    public DateTimeInterval(Integer hour, Integer minute, Integer second, Integer nanoseconds) {
        this(null, null, null, hour, minute, second, nanoseconds, null);
    }

    public DateTimeInterval(Time time) {
        this(time.getHour(), time.getMinute(), time.getSecond(), time.getNanoseconds());
    }

    public void plus(int year, int month, int day, int hour, int minute, int second, int nanosecond) {
        plusOrMinus(PLUS, year, month, day, hour, minute, second, nanosecond);
    }

    public void plus(int year, int month, int day){
        plus(year, month, day, 0, 0, 0, 0);
    }

    public void plus(int hour, int minute, int second, int nanosecond) {
        plus(0, 0, 0, hour, minute, second, nanosecond);
    }

    public void minus(int year, int month, int day, int hour, int minute, int second, int nanosecond) {
        plusOrMinus(MINUS, year, month, day, hour, minute, second, nanosecond);
    }

    public void minus(int year, int month, int day){
        minus(year, month, day, 0, 0, 0, 0);
    }

    public void minus(int hour, int minute, int second, int nanosecond) {
        minus(0, 0, 0, hour, minute, second, nanosecond);
    }

    private void plusOrMinus(boolean isPlus, Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer second, Integer nanosecond) {
        this.isPlus = isPlus;
        this.yearIncrement = year;
        this.monthIncrement = month;
        this.dayIncrement = day;
        this.hourIncrement = hour;
        this.minuteIncrement = minute;
        this.secondIncrement = second;
        this.nanosecondIncrement = nanosecond;

        checkRange(yearIncrement, "Year");
        checkRange(monthIncrement, "Month");
        checkRange(dayIncrement, "Day");
        checkRange(hourIncrement, "Hour");
        checkRange(minuteIncrement, "Minute");
        checkRange(secondIncrement, "Second");
        checkRangeNanos(nanosecondIncrement);

        changeYear();
        changeMonth();
        handleMonthOverflow();
        changeDay();
        changeHour();
        changeMinute();
        changeSecond();
        changeNanosecond();
    }

    protected void checkRange(Integer aValue, String aName) {
        if (aValue < MIN || aValue > MAX) {
            throw new IllegalArgumentException(aName + " is not in the range " + MIN + ".." + MAX);
        }
    }

    protected void checkRangeNanos(Integer aValue) {
        if (aValue < MIN_NANOS || aValue > MAX_NANOS) {
            throw new IllegalArgumentException("Nanosecond interval is not in the range " + MIN_NANOS + ".." + MAX_NANOS);
        }
    }

    protected void changeYear() {
        if (resultYear != null) {
            if (isPlus) {
                resultYear = resultYear + yearIncrement;
            } else {
                resultYear = resultYear - yearIncrement;
            }
            //the DateTime ctor will check the range of the year
        }
    }

    protected void changeMonth() {
        if (resultMonth != null) {
            int count = 0;
            while (count < monthIncrement) {
                stepMonth();
                count++;
            }
        }
    }

    protected void changeDay() {
        if (resultDay != null) {
            int count = 0;
            while (count < dayIncrement) {
                stepDay();
                count++;
            }
        }
    }

    protected void changeHour() {
        if (resultDay != null) {
            int count = 0;
            while (count < hourIncrement) {
                stepHour();
                count++;
            }
        }
    }

    protected void changeMinute() {
        if (resultMinute != null) {
            int count = 0;
            while (count < minuteIncrement) {
                stepMinute();
                count++;
            }
        }
    }

    protected void changeSecond() {
        if (resultSecond != null) {
            int count = 0;
            while (count < secondIncrement) {
                stepSecond();
                count++;
            }
        }
    }

    /**
     * Nanos are different from other items. They don't cycle one step at a time.
     * They are just added. If they under/over flow, then extra math is performed.
     * They don't over/under by more than 1 second, since the size of the increment is limited.
     */
    protected void changeNanosecond() {
        if (resultNanoseconds != null) {
            if (isPlus) {
                resultNanoseconds = resultNanoseconds + nanosecondIncrement;
            } else {
                resultNanoseconds = resultNanoseconds - nanosecondIncrement;
            }
            if (resultNanoseconds > MAX_NANOS) {
                stepSecond();
                resultNanoseconds = resultNanoseconds - MAX_NANOS - 1;
            } else if (resultNanoseconds < MIN_NANOS) {
                stepSecond();
                resultNanoseconds = MAX_NANOS + resultNanoseconds + 1;
            }
        }
    }

    protected void stepYear() {
        if (resultYear != null) {
            if (isPlus) {
                resultYear = resultYear + 1;
            } else {
                resultYear = resultYear - 1;
            }
        }
    }

    protected void stepMonth() {
        if (resultMonth != null) {
            if (isPlus) {
                resultMonth = resultMonth + 1;
            } else {
                resultMonth = resultMonth - 1;
            }
            if (resultMonth > 12) {
                resultMonth = 1;
                stepYear();
            } else if (resultMonth < 1) {
                resultMonth = 12;
                stepYear();
            }
        }
    }

    protected void stepDay() {
        if (resultDay != null) {
            if (isPlus) {
                resultDay = resultDay + 1;
            } else {
                resultDay = resultDay - 1;
            }
            if (resultDay > numberOfDaysInMonth()) {
                resultDay = 1;
                stepMonth();
            } else if (resultDay < 1) {
                resultDay = numberOfDaysInPreviousMonth();
                stepMonth();
            }
        }
    }

    protected int numberOfDaysInMonth() {
        return DateTimeUtil.getNumberOfDaysInMonth(resultYear, resultMonth);
    }

    protected int numberOfDaysInPreviousMonth() {
        int result = 0;
        if (resultMonth > 1) {
            result = DateTimeUtil.getNumberOfDaysInMonth(resultYear, resultMonth - 1);
        } else {
            result = DateTimeUtil.getNumberOfDaysInMonth(resultYear - 1, 12);
        }
        return result;
    }

    protected void stepHour() {
        if (resultHour != null) {
            if (isPlus) {
                resultHour = resultHour + 1;
            } else {
                resultHour = resultHour - 1;
            }
            if (resultHour > 23) {
                resultHour = 0;
                stepDay();
            } else if (resultHour < 0) {
                resultHour = 23;
                stepDay();
            }
        }
    }

    protected void stepMinute() {
        if (resultMinute != null) {
            if (isPlus) {
                resultMinute = resultMinute + 1;
            } else {
                resultMinute = resultMinute - 1;
            }
            if (resultMinute > 59) {
                resultMinute = 0;
                stepHour();
            } else if (resultMinute < 0) {
                resultMinute = 59;
                stepHour();
            }
        }
    }

    protected void stepSecond() {
        if (resultSecond != null) {
            if (isPlus) {
                resultSecond = resultSecond + 1;
            } else {
                resultSecond = resultSecond - 1;
            }
            if (resultSecond > 59) {
                resultSecond = 0;
                stepMinute();
            } else if (resultSecond < 0) {
                resultSecond = 59;
                stepMinute();
            }
        }
    }

    protected void handleMonthOverflow() {
        if (resultMonth != null) {
            int daysInMonth = numberOfDaysInMonth();
            if (resultDay > daysInMonth) {
                if (DayOverflow.ABORT == dayOverflow) {
                    throw new RuntimeException(
                            "Day Overflow: Year:" + resultYear + " Month:" + resultMonth + " has " + daysInMonth + " days, but day has value:" + resultDay +
                                    " To avoid these exceptions, please specify a different DayOverflow policy."
                    );
                } else if (DayOverflow.FIRST_DAY == dayOverflow) {
                    resultDay = 1;
                    stepMonth();
                } else if (DayOverflow.LAST_DAY == dayOverflow) {
                    resultDay = daysInMonth;
                } else if (DayOverflow.SPILLOVER == dayOverflow) {
                    int overflowAmount = resultDay - daysInMonth;
                    resultDay = overflowAmount;
                    stepMonth();
                }
            }
        }
    }

    public Integer getResultYear() {
        return resultYear;
    }

    public Integer getResultMonth() {
        return resultMonth;
    }

    public Integer getResultDay() {
        return resultDay;
    }

    public Integer getResultHour() {
        return resultHour;
    }

    public Integer getResultMinute() {
        return resultMinute;
    }

    public Integer getResultSecond() {
        return resultSecond;
    }

    public Integer getResultNanoseconds() {
        return resultNanoseconds;
    }
}
