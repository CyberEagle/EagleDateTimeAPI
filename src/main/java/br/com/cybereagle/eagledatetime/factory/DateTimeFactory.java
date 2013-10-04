package br.com.cybereagle.eagledatetime.factory;

import br.com.cybereagle.eagledatetime.Date;
import br.com.cybereagle.eagledatetime.DateTime;
import br.com.cybereagle.eagledatetime.Time;

import java.util.TimeZone;

public class DateTimeFactory {

    public static DateTime create(Date date, Time time){
        return null;
    }

    public static DateTime create(Integer aYear, Integer aMonth, Integer aDay, Integer aHour, Integer aMinute, Integer aSecond, Integer aNanoseconds){
        return null;
    }

    public static DateTime parse(String rawString){
        return null;
    }

    public static boolean isParseable(String candidate){
        return false;
    }

    public static DateTime forInstant(long miliseconds, TimeZone timeZone){
        return null;
    }

    public static DateTime forInstantNanos(long nanoseconds, TimeZone timeZone){
        return null;
    }

    public static DateTime now(TimeZone timeZone){
        return null;
    }

}
