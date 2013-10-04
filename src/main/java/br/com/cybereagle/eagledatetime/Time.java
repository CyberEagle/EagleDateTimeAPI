package br.com.cybereagle.eagledatetime;

import br.com.cybereagle.eagledatetime.internal.interfaces.CommonDateTimeOperations;

public interface Time extends CommonDateTimeOperations<Time> {

    Integer getHour();
    Integer getMinute();
    Integer getSecond();
    Integer getNanoseconds();
    long numberOfSecondsFrom(Time time);

}
