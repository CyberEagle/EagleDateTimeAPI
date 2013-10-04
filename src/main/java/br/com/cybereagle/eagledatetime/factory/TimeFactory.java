package br.com.cybereagle.eagledatetime.factory;

import br.com.cybereagle.eagledatetime.Time;

import java.util.TimeZone;

public class TimeFactory {

    public static Time create(Integer aHour, Integer aMinute, Integer aSecond, Integer aNanoseconds){
        return null;
    }

    public static Time parse(String rawString){
        return null;
    }

    public static boolean isParseable(String candidate){
        return false;
    }

    public static Time forInstant(long miliseconds, TimeZone timeZone){
        return null;
    }

    public static Time forInstantNanos(long nanoseconds, TimeZone timeZone){
        return null;
    }

    public static Time now(TimeZone timeZone){
        return null;
    }

    public static Time getEndOfDay(){
        return null;
    }

    public static Time getStartOfDay(){
        return null;
    }

}
