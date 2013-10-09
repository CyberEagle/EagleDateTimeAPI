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
import br.com.cybereagle.eagledatetime.Time;
import br.com.cybereagle.eagledatetime.factory.GregorianDateTime;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Convert date, time and date-time from a string into a  {@link br.com.cybereagle.eagledatetime.Date},
 * {@link br.com.cybereagle.eagledatetime.Time} and {@link br.com.cybereagle.eagledatetime.DateTime}.
 * The primary use case for this class is converting date-times from a database.
 * It can also convert an ISO time, having a 'T' separating the date from the time.
 */
public final class DateTimeParser {

    /**
     * Gross pattern for dates.
     * Detailed validation is done by DateTime.
     * The Group index VARIES for y-m-d according to which option is selected
     * Year: Group 1, 4, 6
     * Month: Group 2, 5
     * Day: Group 3
     */
    private static final Pattern DATE = Pattern.compile("(\\d{1,4})-(\\d\\d)-(\\d\\d)|(\\d{1,4})-(\\d\\d)|(\\d{1,4})");
    /**
     * Gross pattern for times.
     * Detailed validation is done by DateTime.
     * The Group index VARIES for h-m-s-f according to which option is selected
     * Hour: Group 1, 5, 8, 10
     * Minute: Group 2, 6, 9
     * Second: Group 3, 7
     * Microsecond:  Group 4
     */
    private static final String CL = "\\:"; //colon is a special character
    private static final String TT = "(\\d\\d)"; //colon is a special character

    // PRIVATE
    private static final String NUM_DIGITS_FOR_FRACTIONAL_SECONDS = "9";
    private static final Integer NUM_DIGITS = Integer.valueOf(NUM_DIGITS_FOR_FRACTIONAL_SECONDS);
    private static final Pattern TIME = Pattern.compile("" +
            TT + CL + TT + CL + TT + "\\." + "(\\d{1," + NUM_DIGITS_FOR_FRACTIONAL_SECONDS + "})" + "|" +
            TT + CL + TT + CL + TT + "|" +
            TT + CL + TT + "|" +
            TT
    );
    private static final String COLON = ":";
    private static final int THIRD_POSITION = 2;
    private Integer fYear;
    private Integer fMonth;
    private Integer fDay;
    private Integer fHour;
    private Integer fMinute;
    private Integer fSecond;
    private Integer fNanosecond;


    /**
     * Parse String into <tt>Date</tt>
     */
    public Date parseDate(String dateString) {
        if (dateString == null) {
            throw new NullPointerException("Date string is null.");
        }

        dateString = dateString.trim();

        Parts parts = splitIntoDateAndTime(dateString);

        if (!parts.hasTwoParts() && !parts.hasDateOnly()) {
            throw new IllegalArgumentException("String doesn't contain a date.");
        }

        parseDateInternal(parts.datePart);

        return GregorianDateTime.newDate(fYear, fMonth, fDay);
    }

    /**
     * Parse String into <tt>Time</tt>
     */
    public Time parseTime(String timeString){
        if (timeString == null) {
            throw new NullPointerException("Time string is null.");
        }

        timeString = timeString.trim();

        Parts parts = splitIntoDateAndTime(timeString);

        if (!parts.hasTwoParts() && !parts.hasTimeOnly()) {
            throw new IllegalArgumentException("String doesn't contain a time.");
        }

        parseTimeInternal(parts.timePart);

        return GregorianDateTime.newTime(fHour, fMinute, fSecond, fNanosecond);
    }

    /**
     * Parse String into <tt>DateTime</tt>
     */
    public DateTime parseDateTime(String dateTimeString){
        if (dateTimeString == null) {
            throw new NullPointerException("Date-time string is null.");
        }

        dateTimeString = dateTimeString.trim();

        Parts parts = splitIntoDateAndTime(dateTimeString);

        if (!parts.hasTwoParts()) {
            throw new IllegalArgumentException("String doesn't contain a date-time.");
        }

        parseDateInternal(parts.datePart);
        parseTimeInternal(parts.timePart);

        return GregorianDateTime.newDateTime(fYear, fMonth, fDay, fHour, fMinute, fSecond, fNanosecond);
    }

    /**
     * Date and time can be separated with a single space, or with a 'T' character (case-sensitive).
     */
    private Parts splitIntoDateAndTime(String aDateTime) {
        Parts result = new Parts();
        int dateTimeSeparator = getDateTimeSeparator(aDateTime);
        boolean hasDateTimeSeparator = 0 < dateTimeSeparator && dateTimeSeparator < aDateTime.length();
        if (hasDateTimeSeparator) {
            result.datePart = aDateTime.substring(0, dateTimeSeparator);
            result.timePart = aDateTime.substring(dateTimeSeparator + 1);
        } else if (hasColonInThirdPlace(aDateTime)) {
            result.timePart = aDateTime;
        } else {
            result.datePart = aDateTime;
        }
        return result;
    }

    /**
     * Return the index of a space character, or of a 'T' character. If not found, return -1.
     */
    int getDateTimeSeparator(String aDateTime) {
        String SPACE = " ";
        int NOT_FOUND = -1;
        int result = NOT_FOUND;
        result = aDateTime.indexOf(SPACE);
        if (result == NOT_FOUND) {
            result = aDateTime.indexOf("T");
        }
        return result;
    }

    private boolean hasColonInThirdPlace(String aDateTime) {
        boolean result = false;
        if (aDateTime.length() >= THIRD_POSITION) {
            result = COLON.equals(aDateTime.substring(THIRD_POSITION, THIRD_POSITION + 1));
        }
        return result;
    }

    private void parseDateInternal(String aDate) {
        Matcher matcher = DATE.matcher(aDate);
        if (matcher.matches()) {
            String year = getGroup(matcher, 1, 4, 6);
            if (year != null) {
                fYear = Integer.valueOf(year);
            }
            String month = getGroup(matcher, 2, 5);
            if (month != null) {
                fMonth = Integer.valueOf(month);
            }
            String day = getGroup(matcher, 3);
            if (day != null) {
                fDay = Integer.valueOf(day);
            }
        } else {
            throw new UnknownDateTimeFormat("Unexpected format for date:" + aDate);
        }
    }

    private String getGroup(Matcher aMatcher, int... aGroupIds) {
        String result = null;
        for (int id : aGroupIds) {
            result = aMatcher.group(id);
            if (result != null) break;
        }
        return result;
    }

    private void parseTimeInternal(String aTime) {
        Matcher matcher = TIME.matcher(aTime);
        if (matcher.matches()) {
            String hour = getGroup(matcher, 1, 5, 8, 10);
            fHour = hour != null ? Integer.valueOf(hour) : 0;

            String minute = getGroup(matcher, 2, 6, 9);
            fMinute = minute != null ? Integer.valueOf(minute) : 0;

            String second = getGroup(matcher, 3, 7);
            fSecond = second != null ? Integer.valueOf(second) : 0;

            String decimalSeconds = getGroup(matcher, 4);
            fNanosecond = decimalSeconds != null ? Integer.valueOf(convertToNanoseconds(decimalSeconds)) : 0;
        } else {
            throw new UnknownDateTimeFormat("Unexpected format for time:" + aTime);
        }
    }

    /**
     * Convert any number of decimals (1..9) into the form it would have taken if nanos had been used,
     * by adding any 0's to the right side.
     */
    private String convertToNanoseconds(String aDecimalSeconds) {
        StringBuilder result = new StringBuilder(aDecimalSeconds);
        while (result.length() < NUM_DIGITS) {
            result.append("0");
        }
        return result.toString();
    }

    /**
     * Thrown when the given string cannot be converted into a <tt>DateTime</tt>, since it doesn't
     * have a format allowed by this class.
     * An unchecked exception.
     */
    static final class UnknownDateTimeFormat extends RuntimeException {
        private static final long serialVersionUID = -7179421566055773208L;

        UnknownDateTimeFormat(String aMessage) {
            super(aMessage);
        }

        UnknownDateTimeFormat(String aMessage, Throwable aEx) {
            super(aMessage, aEx);
        }
    }

    private class Parts {
        String datePart;
        String timePart;

        boolean hasTwoParts() {
            return datePart != null && timePart != null;
        }

        boolean hasDateOnly() {
            return timePart == null;
        }

        boolean hasTimeOnly() {
            return datePart == null;
        }
    }
}
