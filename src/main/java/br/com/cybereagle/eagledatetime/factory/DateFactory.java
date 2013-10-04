package br.com.cybereagle.eagledatetime.factory;

import br.com.cybereagle.eagledatetime.Date;

import java.util.TimeZone;

public class DateFactory {

    public static Date create(Integer year, Integer month, Integer day){
        return null;
    }

    public static Date parse(String rawString){
        return null;
    }

    public static boolean isParseable(String candidate){
        return false;
    }

    public static Date forInstant(long miliseconds, TimeZone timeZone){
        return null;
    }

    public static Date forInstantNanos(long nanoseconds, TimeZone timeZone){
        return null;
    }

    public static Date today(TimeZone timeZone){
        return null;
    }

}
