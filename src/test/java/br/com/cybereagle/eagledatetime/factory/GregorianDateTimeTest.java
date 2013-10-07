package br.com.cybereagle.eagledatetime.factory;

import br.com.cybereagle.eagledatetime.Date;
import br.com.cybereagle.eagledatetime.DateTime;
import br.com.cybereagle.eagledatetime.Time;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class GregorianDateTimeTest {

    public static final int YEAR = 2009;
    public static final int MONTH = 3;
    public static final int DAY = 10;
    public static final int HOUR = 22;
    public static final int MINUTE = 46;
    public static final int SECOND = 30;
    public static final int NANOSECONDS = 532643678;

    @Test
    public void shouldCreateNewDateCorrectly(){
        Date date = GregorianDateTime.newDate(YEAR, MONTH, DAY);

        verify(date, YEAR, MONTH, DAY);
    }

    @Test
    public void shouldCreateNewTimeCorrectlyWithAndWithoutNanoseconds(){
        Time time = GregorianDateTime.newTime(HOUR, MINUTE, SECOND, NANOSECONDS);

        verify(time, HOUR, MINUTE, SECOND, NANOSECONDS);

        time = GregorianDateTime.newTime(HOUR, MINUTE, SECOND);

        verify(time, HOUR, MINUTE, SECOND, 0);
    }

    @Test
    public void shouldCreateNewDateTimeCorrectlyWithAndWithoutNanoseconds(){
        DateTime dateTime = GregorianDateTime.newDateTime(YEAR, MONTH, DAY, HOUR, MINUTE, SECOND, NANOSECONDS);
        verify(dateTime, YEAR, MONTH, DAY, HOUR, MINUTE, SECOND, NANOSECONDS);

        dateTime = GregorianDateTime.newDateTime(YEAR, MONTH, DAY, HOUR, MINUTE, SECOND);

        verify(dateTime, YEAR, MONTH, DAY, HOUR, MINUTE, SECOND, 0);
    }

    @Test
    public void shouldCreateNewDateTimeWithDateAndTimeCorrectly(){
        Date date = GregorianDateTime.newDate(YEAR, MONTH, DAY);
        Time time = GregorianDateTime.newTime(HOUR, MINUTE, SECOND, NANOSECONDS);

        DateTime dateTime = GregorianDateTime.newDateTime(date, time);
        verify(dateTime, YEAR, MONTH, DAY, HOUR, MINUTE, SECOND, NANOSECONDS);
    }

    private void verify(Date date, Integer year, Integer month, Integer day) {
        assertThat(date.getYear()).isEqualTo(year);
        assertThat(date.getMonth()).isEqualTo(month);
        assertThat(date.getDay()).isEqualTo(day);
    }

    private void verify(Time time, Integer hour, Integer minute, Integer second, Integer nanoseconds) {
        assertThat(time.getHour()).isEqualTo(hour);
        assertThat(time.getMinute()).isEqualTo(minute);
        assertThat(time.getSecond()).isEqualTo(second);
        assertThat(time.getNanoseconds()).isEqualTo(nanoseconds);
    }

    private void verify(DateTime dateTime, Integer year, Integer month, Integer day,
                        Integer hour, Integer minute, Integer second, Integer nanoseconds) {
        assertThat(dateTime.getYear()).isEqualTo(year);
        assertThat(dateTime.getMonth()).isEqualTo(month);
        assertThat(dateTime.getDay()).isEqualTo(day);
        assertThat(dateTime.getHour()).isEqualTo(hour);
        assertThat(dateTime.getMinute()).isEqualTo(minute);
        assertThat(dateTime.getSecond()).isEqualTo(second);
        assertThat(dateTime.getNanoseconds()).isEqualTo(nanoseconds);
    }

}
