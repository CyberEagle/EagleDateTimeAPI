package br.com.cybereagle.eagledatetime;

import java.util.Locale;

public interface AmountOfTime {

    Integer getDays();
    Integer getMonths();
    Integer getYears();
    Integer getHours();
    Integer getMinutes();
    Integer getSeconds();
    Integer getNanoseconds();
    TimeUnit getUpperTimeUnit();
    AmountOfTime convert(TimeUnit upperTimeUnit);
    String format(String format);
    String format(String format, Locale locale);

}
