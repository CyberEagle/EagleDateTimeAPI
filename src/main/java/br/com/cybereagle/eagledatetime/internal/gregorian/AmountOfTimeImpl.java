package br.com.cybereagle.eagledatetime.internal.gregorian;

import br.com.cybereagle.eagledatetime.AmountOfTime;
import br.com.cybereagle.eagledatetime.TimeUnit;
import br.com.cybereagle.eagledatetime.internal.util.DateTimeUtil;

import java.util.Locale;

import static br.com.cybereagle.eagledatetime.internal.util.DateTimeUtil.checkRange;

public class AmountOfTimeImpl implements AmountOfTime {

    private Integer years;
    private Integer months;
    private Integer days;
    private Integer hours;
    private Integer minutes;
    private Integer seconds;
    private Integer nanoseconds;

    private TimeUnit upperTimeUnit;

    public AmountOfTimeImpl(Integer years, Integer months, Integer days, Integer hours, Integer minutes, Integer seconds, Integer nanoseconds) {
        this.years = years;
        this.months = months;
        this.days = days;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        this.nanoseconds = nanoseconds;

        if(years != null){
            upperTimeUnit = TimeUnit.YEAR;
        }
        else if(months != null){
            upperTimeUnit = TimeUnit.MONTH;
        }
        else if(days != null){
            upperTimeUnit = TimeUnit.DAY;
        }
        else if(hours != null){
            upperTimeUnit = TimeUnit.HOUR;
        }
        else if(minutes != null){
            upperTimeUnit = TimeUnit.MINUTE;
        }
        else if(seconds != null){
            upperTimeUnit = TimeUnit.SECOND;
        }
        else if(nanoseconds != null){
            upperTimeUnit = TimeUnit.NANOSECONDS;
        }
        else {
            throw new NullPointerException("All the properties of AmountOfTime are null.");
        }

        validateState();
    }

    private void validateState() {
        switch (upperTimeUnit){
            case YEAR:
                checkRange(years, 1, Integer.MAX_VALUE, "Years");
            case MONTH:
                checkRange(months, 1, 12, "Months");
            case DAY:
                checkRange(days, 1, 31, "Days");
            case HOUR:
                checkRange(hours, 0, 23, "Hours");
            case MINUTE:
                checkRange(minutes, 0, 59, "Minutes");
            case SECOND:
                checkRange(seconds, 0, 59, "Second");
            case NANOSECONDS:
                checkRange(nanoseconds, 0, 999999999, "Nanoseconds");
        }
    }

    @Override
    public Integer getYears() {
        return years;
    }

    @Override
    public Integer getMonths() {
        return months;
    }

    @Override
    public Integer getDays() {
        return days;
    }

    @Override
    public Integer getHours() {
        return hours;
    }

    @Override
    public Integer getMinutes() {
        return minutes;
    }

    @Override
    public Integer getSeconds() {
        return seconds;
    }

    @Override
    public Integer getNanoseconds() {
        return nanoseconds;
    }

    @Override
    public TimeUnit getUpperTimeUnit() {
        return upperTimeUnit;
    }

    /**
     * This method is used to convert an amount of time from one upper time unit to another. <br />
     * OBS: The conversion of years, months and days is not very accurate, since it considers the following
     * conversions:<br />
     * 1 year = 365 days<br />
     * 1 month = 30 days<br />
     * So, be careful when using this method.
     * @param upperTimeUnit
     * @return
     */
    @Override
    public AmountOfTime convert(TimeUnit upperTimeUnit) {
        Integer years = this.years;
        Integer months = this.months;
        Integer days = this.days;
        Integer hours = this.hours;
        Integer minutes = this.minutes;
        Integer seconds = this.seconds;
        Integer nanoseconds = this.nanoseconds;
        TimeUnit currentUpperTimeUnit = this.upperTimeUnit;

        while (currentUpperTimeUnit != upperTimeUnit){
            boolean upConversion = currentUpperTimeUnit.compareTo(upperTimeUnit) < 0;
            switch (currentUpperTimeUnit){
                case YEAR:
                    if(upConversion){
                        // Shouldn't ever get here
                        throw new IllegalStateException();
                    }
                    else {
                        months += years * 12;
                        years = null;
                    }
                    break;
                case MONTH:
                    if(upConversion){
                        years = months / 365;
                        months = months % 365;
                    }
                    else {
                        days += months * 30;
                        months = null;
                    }
                    break;
                case DAY:
                    if(upConversion){
                        months = days / 30;
                        days = days % 30;
                    }
                    else {
                        hours += days * 24;
                        days = null;
                    }
                    break;
                case HOUR:
                    if(upConversion){
                        days = hours / 24;
                        hours = hours % 24;
                    }
                    else {
                        minutes += hours * 60;
                        hours = null;
                    }
                    break;
                case MINUTE:
                    if(upConversion){
                        hours = minutes / 60;
                        minutes = minutes % 60;
                    }
                    else {
                        seconds += minutes * 60;
                        minutes = null;
                    }
                    break;
                case SECOND:
                    if(upConversion){
                        minutes = seconds / 60;
                        seconds = seconds % 60;
                    }
                    else {
                        nanoseconds += seconds * 1000000000;
                        seconds = null;
                    }
                    break;
                case NANOSECONDS:
                    if(upConversion){
                        seconds = nanoseconds / 1000000000;
                        nanoseconds = nanoseconds % 1000000000;
                    }
                    else {
                        // Shouldn't ever get here
                        throw new IllegalStateException();
                    }
                    break;
            }
        }
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
}
