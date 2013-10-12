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
import br.com.cybereagle.eagledatetime.factory.GregorianDateTime;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public class DateTimeIntervalTest {

    private static final boolean SUCCESS = true;
    private static final boolean FAIL = false;
    private static final String FORMAT = "YYYY-MM-DD hh:mm:ss.fffffffff";

    @Test
    public void testRanges(){
        testRange(SUCCESS, 0,0,0,0,0,0,0);
        testRange(SUCCESS, 9998,0,0,0,0,0,0);
        testRange(SUCCESS, 0,9999,0,0,0,0,0);
        testRange(SUCCESS, 0,0,9999,0,0,0,0);
        testRange(SUCCESS, 0,0,0,9999,0,0,0);
        testRange(SUCCESS, 0,0,0,0,9999,0,0);
        testRange(SUCCESS, 0,0,0,0,0,9999,0);
        testRange(SUCCESS, 0,0,0,0,0,0,999999999);

        testRange(FAIL, -1,0,0,0,0,0,0);
        testRange(FAIL, 0,-1,0,0,0,0,0);
        testRange(FAIL, 0,0,-1,0,0,0,0);
        testRange(FAIL, 0,0,0,-1,0,0,0);
        testRange(FAIL, 0,0,0,0,-1,0,0);
        testRange(FAIL, 0,0,0,0,0,-1,0);
        testRange(FAIL, 0,0,0,0,0,0,-1);

        testRange(FAIL, 10000,0,0,0,0,0,0);
        testRange(FAIL, 0,10000,0,0,0,0,0);
        testRange(FAIL, 0,0,10000,0,0,0,0);
        testRange(FAIL, 0,0,0,10000,0,0,0);
        testRange(FAIL, 0,0,0,0,10000,10000,0);
        testRange(FAIL, 0,0,0,0,0,0,1000000000);
    }

    @Test
    public void testSingleField(){
        testDateTime("2001-01-01 23:45:19.0", "2001-01-01 23:45:19.0", 0, 0, 0, 0, 0, 0, 0);

        testDateTime("2001-01-01 23:45:19.0", "2002-01-01 23:45:19.0", 1, 0, 0, 0, 0, 0, 0);
        testDateTime("2001-01-01 23:45:19.0", "2001-02-01 23:45:19.0", 0, 1, 0, 0, 0, 0, 0);
        testDateTime("2001-01-01 23:45:19.0", "2001-01-02 23:45:19.0", 0, 0, 1, 0, 0, 0, 0);

        testDateTime("2001-01-01 13:45:19.0", "2001-01-01 14:45:19.0", 0, 0, 0, 1, 0, 0, 0);
        testDateTime("2001-01-01 13:45:19.0", "2001-01-01 13:46:19.0", 0, 0, 0, 0, 1, 0, 0);
        testDateTime("2001-01-01 13:45:19.0", "2001-01-01 13:45:20.0", 0, 0, 0, 0, 0, 1, 0);
        testDateTime("2001-01-01 13:45:19.0", "2001-01-01 13:45:19.000000001", 0, 0, 0, 0, 0, 0, 1);

        testDateTime("2001-01-01 13:45:56.0", "2001-01-01 13:46:00.0", 0, 0, 0, 0, 0, 4, 0);
        testDateTime("2001-01-01 13:45:56.0", "2001-01-01 13:46:01.0", 0, 0, 0, 0, 0, 5, 0);
        testDateTime("2001-01-01 13:45:56.0", "2001-01-01 13:47:01.0", 0, 0, 0, 0, 0, 65, 0);

        testDateTime("2001-01-01 13:45:56.0", "2001-01-01 13:45:56.999999999", 0, 0, 0, 0, 0, 0, 999999999);
        testDateTime("2001-01-01 13:45:56.000000001", "2001-01-01 13:45:57.0", 0, 0, 0, 0, 0, 0, 999999999);
        testDateTime("2001-01-01 13:45:56.000000002", "2001-01-01 13:45:57.000000001", 0, 0, 0, 0, 0, 0, 999999999);

        testDateTime("2001-01-01 13:45:19.0", "2001-01-01 14:00:19.0", 0, 0, 0, 0, 15, 0, 0);
        testDateTime("2001-01-01 13:45:19.0", "2001-01-01 15:01:19.0", 0, 0, 0, 0, 76, 0, 0);

        testDateTime("2001-01-01 10:45:19.0", "2001-01-01 11:45:19.0", 0, 0, 0, 1, 0, 0, 0);
        testDateTime("2001-01-01 10:45:19.0", "2001-01-01 13:45:19.0", 0, 0, 0, 3, 0, 0, 0);
        testDateTime("2001-01-01 23:45:19.0", "2001-01-02 01:45:19.0", 0, 0, 0, 2, 0, 0, 0);

        testDateTime("2001-01-01 13:45:19.0", "2001-01-21 13:45:19.0", 0, 0, 20, 0, 0, 0, 0);
        testDateTime("2001-01-01 13:45:19.0", "2001-01-31 13:45:19.0", 0, 0, 30, 0, 0, 0, 0);
        testDateTime("2001-01-01 13:45:19.0", "2001-02-01 13:45:19.0", 0, 0, 31, 0, 0, 0, 0);
        testDateTime("2001-01-01 13:45:19.0", "2001-02-28 13:45:19.0", 0, 0, 58, 0, 0, 0, 0);
        testDateTime("2001-01-01 13:45:19.0", "2001-03-01 13:45:19.0", 0, 0, 59, 0, 0, 0, 0);
        testDateTime("2001-01-01 13:45:19.0", "2001-03-02 13:45:19.0", 0, 0, 60, 0, 0, 0, 0);

        testDateTime("2001-01-01 13:45:19.0", "2001-04-01 13:45:19.0", 0, 3, 0, 0, 0, 0, 0);
        testDateTime("2001-01-01 13:45:19.0", "2002-01-01 13:45:19.0", 0, 12, 0, 0, 0, 0, 0);
        testDateTime("2001-01-01 13:45:19.0", "2002-04-01 13:45:19.0", 0, 15, 0, 0, 0, 0, 0);

        testDateTime("2001-01-01 13:45:19.0", "2004-01-01 13:45:19.0", 3, 0, 0, 0, 0, 0, 0);
        testDateTime("2001-01-01 13:45:19.0", "2011-01-01 13:45:19.0", 10, 0, 0, 0, 0, 0, 0);
        testDateTime("2001-01-01 13:45:19.0", "3501-01-01 13:45:19.0", 1500, 0, 0, 0, 0, 0, 0);

        testDateTime("2001-01-01", "2002-01-01 00:00:00.0", 1, 0, 0, 0, 0, 0, 0);
        testDateTime("2001-01-01", "2001-02-01 00:00:00.0", 0, 1, 0, 0, 0, 0, 0);
        testDateTime("2001-01-01", "2001-01-02 00:00:00.0", 0, 0, 1, 0, 0, 0, 0);
        testDateTime("2001-01-01", "2001-01-01 01:00:00.0", 0, 0, 0, 1, 0, 0, 0);
        testDateTime("2001-01-01", "2001-01-01 00:01:00.0", 0, 0, 0, 0, 1, 0, 0);
        testDateTime("2001-01-01", "2001-01-01 00:00:01.0", 0, 0, 0, 0, 0, 1, 0);
        testDateTime("2001-01-01", "2001-01-01 00:00:00.0", 0, 0, 0, 0, 0, 0, 0);
        testDateTime("2001-01-01 15:14:10.123456789", "2001-01-01 15:14:20.123456789", 0, 0, 0, 0, 0, 10, 0);

        testDateTime("10:12:14", "0002-01-01 10:12:14.0", 1, 0, 0, 0, 0, 0, 0);
        testDateTime("10:12:14", "0001-02-01 10:12:14.0", 0, 1, 0, 0, 0, 0, 0);
        testDateTime("10:12:14", "0001-01-02 10:12:14.0", 0, 0, 1, 0, 0, 0, 0);
        testDateTime("10:12:14", "0001-01-01 11:12:14.0", 0, 0, 0, 1, 0, 0, 0);
        testDateTime("10:12:14", "0001-01-01 10:13:14.0", 0, 0, 0, 0, 1, 0, 0);
        testDateTime("10:12:14", "0001-01-01 10:12:15.0", 0, 0, 0, 0, 0, 1, 0);
    }

    @Test
    public void testMultipleFields(){
        testDateTime("2001-01-01 23:45:19.0", "2002-02-01 23:45:19.0", 1, 1, 0, 0, 0, 0, 0);
        testDateTime("2001-01-01 23:45:19.0", "2002-02-02 23:45:19.0", 1, 1, 1, 0, 0, 0, 0);
        testDateTime("2001-01-01 23:45:19.0", "2002-02-03 00:45:19.0", 1, 1, 1, 1, 0, 0, 0);
        testDateTime("2001-01-01 23:45:19.0", "2002-02-03 00:46:19.0", 1, 1, 1, 1, 1, 0, 0);
        testDateTime("2001-01-01 23:45:19.0", "2002-02-03 00:46:20.0", 1, 1, 1, 1, 1, 1, 0);
        testDateTime("2001-01-01 23:45:19.0", "2002-02-03 00:46:20.000000001", 1, 1, 1, 1, 1, 1, 1);

        testDateTime("2001-01-01", "2002-02-01 00:00:00.0", 1, 1, 0, 0, 0, 0, 0);
        testDateTime("2001-01-01", "2002-02-02 00:00:00.0", 1, 1, 1, 0, 0, 0, 0);
        testDateTime("2001-01-01", "2002-02-02 01:00:00.0", 1, 1, 1, 1, 0, 0, 0);
        testDateTime("2001-01-01", "2002-02-02 01:01:00.0", 1, 1, 1, 1, 1, 0, 0);
        testDateTime("2001-01-01", "2002-02-02 01:01:01.0", 1, 1, 1, 1, 1, 1, 0);
        testDateTime("2001-01-01", "2002-02-02 01:01:01.000000001", 1, 1, 1, 1, 1, 1, 1);

        testDateTime("10:12:14", "0002-02-01 10:12:14.0", 1, 1, 0, 0, 0, 0, 0);
        testDateTime("10:12:14", "0002-02-02 10:12:14.0", 1, 1, 1, 0, 0, 0, 0);
        testDateTime("10:12:14", "0002-02-02 11:12:14.0", 1, 1, 1, 1, 0, 0, 0);
        testDateTime("10:12:14", "0002-02-02 11:13:14.0", 1, 1, 1, 1, 1, 0, 0);
        testDateTime("10:12:14", "0002-02-02 11:13:15.0", 1, 1, 1, 1, 1, 1, 0);
        testDateTime("10:12:14", "0002-02-02 11:13:15.000000001", 1, 1, 1, 1, 1, 1, 1);
    }

    @Test
    public void testMultipleFieldsWithRollovers(){
        testDateTime("2001-01-01 23:45:19.0", "2012-02-01 23:45:19.0", 10, 13, 0, 0, 0, 0, 0);
        testDateTime("2001-01-01 23:45:19.0", "2012-02-29 23:45:19.0", 10, 13, 28, 0, 0, 0, 0);
        testDateTime("2001-01-01 23:45:19.0", "2012-03-01 23:45:19.0", 10, 13, 29, 0, 0, 0, 0);
        testDateTime("2001-01-01 23:45:19.0", "2012-03-02 00:45:19.0", 10, 13, 29, 1, 0, 0, 0);
        testDateTime("2001-01-01 23:45:19.0", "2012-03-03 00:45:19.0", 10, 13, 29, 25, 0, 0, 0);
        testDateTime("2001-01-01 23:45:19.0", "2012-03-03 03:45:19.0", 10, 13, 29, 28, 0, 0, 0);
        testDateTime("2001-01-01 23:45:19.0", "2012-03-03 04:00:19.0", 10, 13, 29, 28, 15, 0, 0);
        testDateTime("2001-01-01 23:45:19.0", "2012-03-03 04:15:19.0", 10, 13, 29, 28, 30, 0, 0);
        testDateTime("2001-01-01 23:45:19.0", "2012-03-03 04:15:29.0", 10, 13, 29, 28, 30, 10, 0);
        testDateTime("2001-01-01 23:45:19.0", "2012-03-03 04:15:59.0", 10, 13, 29, 28, 30, 40, 0);
        testDateTime("2001-01-01 23:45:19.0", "2012-03-03 04:16:00.0", 10, 13, 29, 28, 30, 41, 0);
        testDateTime("2001-01-01 23:45:19.0", "2012-03-03 04:16:00.000000001", 10, 13, 29, 28, 30, 41, 1);
        testDateTime("2001-01-01 23:45:19.0", "2012-03-03 04:16:00.999999999", 10, 13, 29, 28, 30, 41, 999999999);
        testDateTime("2001-01-01 23:45:19.000000001", "2012-03-03 04:16:01.000000000", 10, 13, 29, 28, 30, 41, 999999999);
        testDateTime("2001-01-01 23:45:19.000000002", "2012-03-03 04:16:01.000000001", 10, 13, 29, 28, 30, 41, 999999999);

        //date only
        testDateTime("2001-01-01", "2012-02-01 00:00:00.0", 10, 13, 0, 0, 0, 0, 0);
        testDateTime("2001-01-01", "2012-02-28 00:00:00.0", 10, 13, 27, 0, 0, 0, 0);
        testDateTime("2001-01-01", "2012-02-29 00:00:00.0", 10, 13, 28, 0, 0, 0, 0);
        testDateTime("2001-01-01", "2012-03-01 00:00:00.0", 10, 13, 29, 0, 0, 0, 0);
        testDateTime("2001-01-01", "2012-03-01 23:00:00.0", 10, 13, 29, 23, 0, 0, 0);
        testDateTime("2001-01-01", "2012-03-02 00:00:00.0", 10, 13, 29, 24, 0, 0, 0);
        testDateTime("2001-01-01", "2012-03-02 01:00:00.0", 10, 13, 29, 25, 0, 0, 0);
        testDateTime("2001-01-01", "2012-03-02 01:59:00.0", 10, 13, 29, 25, 59, 0, 0);
        testDateTime("2001-01-01", "2012-03-02 02:01:00.0", 10, 13, 29, 25, 61, 0, 0);
        testDateTime("2001-01-01", "2012-03-02 02:01:59.0", 10, 13, 29, 25, 61, 59, 0);
        testDateTime("2001-01-01", "2012-03-02 02:02:01.0", 10, 13, 29, 25, 61, 61, 0);
        testDateTime("2001-01-01", "2012-03-02 02:02:01.999999999", 10, 13, 29, 25, 61, 61, 999999999);

        //time only
        testDateTime("00:00:00", "0001-01-01 00:00:00.0", 0, 0, 0, 0, 0, 0, 0);
        testDateTime("00:00:00", "0001-01-01 23:00:00.0", 0, 0, 0, 23, 0, 0, 0);
        testDateTime("00:00:00", "0001-01-02 00:00:00.0", 0, 0, 0, 24, 0, 0, 0);
        testDateTime("00:00:00", "0001-01-02 01:00:00.0", 0, 0, 0, 25, 0, 0, 0);
        testDateTime("00:00:00", "0001-01-02 01:00:00.0", 0, 0, 0, 25, 0, 0, 0);
        testDateTime("00:00:00", "0001-01-02 01:59:00.0", 0, 0, 0, 25, 59, 0, 0);
        testDateTime("00:00:00", "0001-01-02 02:00:00.0", 0, 0, 0, 25, 60, 0, 0);
        testDateTime("00:00:00", "0001-01-02 02:01:00.0", 0, 0, 0, 25, 61, 0, 0);
        testDateTime("00:00:00", "0001-01-02 02:01:59.0", 0, 0, 0, 25, 61, 59, 0);
        testDateTime("00:00:00", "0001-01-02 02:02:01.0", 0, 0, 0, 25, 61, 61, 0);
        testDateTime("00:00:00", "0001-01-02 02:02:01.999999999", 0, 0, 0, 25, 61, 61, 999999999);
        testDateTime("00:00:00.000000001", "0001-01-02 02:02:02.0", 0, 0, 0, 25, 61, 61, 999999999);
        testDateTime("00:00:00.000000002", "0001-01-02 02:02:02.000000001", 0, 0, 0, 25, 61, 61, 999999999);
    }

    @Test
    public void testDayOverflow(){
        testDayOverflow("2001-01-31 10:20:30.0", DayOverflow.LAST_DAY, "2001-02-28 10:20:30.0", 0,1,0,0,0,0,0);
        testDayOverflow("2001-01-31 10:20:30.0", DayOverflow.FIRST_DAY, "2001-03-01 10:20:30.0", 0,1,0,0,0,0,0);
        testDayOverflow("2001-12-31 10:20:30.0", DayOverflow.SPILLOVER, "2002-03-03 10:20:30.0", 0,2,0,0,0,0,0);

        testDayOverflowAbort(SUCCESS, "2001-01-31 10:20:30.0",  0,1,0,0,0,0,0);
        testDayOverflowAbort(SUCCESS, "2001-03-31 10:20:30.0",  0,1,0,0,0,0,0);
        testDayOverflowAbort(SUCCESS, "2001-03-31 10:20:30.0",  0,1,0,0,0,0,0);
        testDayOverflowAbort(SUCCESS, "2001-05-31 10:20:30.0",  0,1,0,0,0,0,0);
        testDayOverflowAbort(SUCCESS, "2001-10-31 10:20:30.0",  0,1,0,0,0,0,0);
        testDayOverflowAbort(FAIL, "2001-02-28 10:20:30.0",  0,1,0,0,0,0,0);
        testDayOverflowAbort(FAIL, "2001-04-30 10:20:30.0",  0,1,0,0,0,0,0);
        testDayOverflowAbort(FAIL, "2001-05-01 10:20:30.0",  0,1,0,0,0,0,0);
        testDayOverflowAbort(FAIL, "2001-07-31 10:20:30.0",  0,1,0,0,0,0,0);
        testDayOverflowAbort(FAIL, "2001-12-31 10:20:30.0",  0,1,0,0,0,0,0);
    }

    @Test
    public void testSingleFieldMinus(){
        testDateMinus("2001-04-15 23:45:19.0", "2001-04-15 23:45:19.0", 0,0,0,0,0,0,0);
        testDateMinus("2001-04-15 23:45:19.0", "2000-04-15 23:45:19.0", 1,0,0,0,0,0,0);
        testDateMinus("2001-04-15 23:45:19.0", "2001-03-15 23:45:19.0", 0,1,0,0,0,0,0);
        testDateMinus("2001-04-15 23:45:19.0", "2001-04-14 23:45:19.0", 0,0,1,0,0,0,0);
        testDateMinus("2001-04-15 23:45:19.0", "2001-04-15 22:45:19.0", 0,0,0,1,0,0,0);
        testDateMinus("2001-04-15 23:45:19.0", "2001-04-15 23:44:19.0", 0,0,0,0,1,0,0);
        testDateMinus("2001-04-15 23:45:19.0", "2001-04-15 23:45:18.0", 0,0,0,0,0,1,0);

        testDateMinus("2001-04-15 23:45:19.0", "2001-04-15 23:45:18.999999999", 0,0,0,0,0,0,1);
        testDateMinus("2001-04-15 23:45:19.0", "2001-04-15 23:45:18.000000001", 0,0,0,0,0,0,999999999);
        testDateMinus("2001-04-15 23:45:19.999999999", "2001-04-15 23:45:19.0", 0,0,0,0,0,0,999999999);

        testDateMinus("2001-04-15 23:45:19.0", "2001-04-15 23:45:09.0", 0,0,0,0,0,10,0);
        testDateMinus("2001-04-15 23:45:19.0", "2001-04-15 23:45:00.0", 0,0,0,0,0,19,0);
        testDateMinus("2001-04-15 23:45:19.0", "2001-04-15 23:44:59.0", 0,0,0,0,0,20,0);
        testDateMinus("2001-04-15 23:45:19.0", "2001-04-15 23:44:49.0", 0,0,0,0,0,30,0);
        testDateMinus("2001-04-15 23:45:19.0", "2001-04-15 23:44:19.0", 0,0,0,0,0,60,0);

        testDateMinus("2001-04-15 23:45:19.0", "2001-04-15 23:35:19.0", 0,0,0,0,10,0,0);
        testDateMinus("2001-04-15 23:45:19.0", "2001-04-15 23:00:19.0", 0,0,0,0,45,0,0);
        testDateMinus("2001-04-15 23:45:19.0", "2001-04-15 22:59:19.0", 0,0,0,0,46,0,0);
        testDateMinus("2001-04-15 23:45:19.0", "2001-04-15 22:49:19.0", 0,0,0,0,56,0,0);

        testDateMinus("2001-04-15 23:45:19.0", "2001-04-15 13:45:19.0", 0,0,0,10,0,0,0);
        testDateMinus("2001-04-15 23:45:19.0", "2001-04-15 00:45:19.0", 0,0,0,23,0,0,0);
        testDateMinus("2001-04-15 23:45:19.0", "2001-04-14 23:45:19.0", 0,0,0,24,0,0,0);
        testDateMinus("2001-04-15 23:45:19.0", "2001-04-14 22:45:19.0", 0,0,0,25,0,0,0);

        testDateMinus("2001-04-15 23:45:19.0", "2001-04-05 23:45:19.0", 0,0,10,0,0,0,0);
        testDateMinus("2001-04-15 23:45:19.0", "2001-04-01 23:45:19.0", 0,0,14,0,0,0,0);
        testDateMinus("2001-04-15 23:45:19.0", "2001-03-31 23:45:19.0", 0,0,15,0,0,0,0);
        testDateMinus("2001-04-15 23:45:19.0", "2001-03-30 23:45:19.0", 0,0,16,0,0,0,0);
        testDateMinus("2001-04-15 23:45:19.0", "2001-03-01 23:45:19.0", 0,0,45,0,0,0,0);
        testDateMinus("2001-04-15 23:45:19.0", "2001-02-28 23:45:19.0", 0,0,46,0,0,0,0);

        testDateMinus("2001-04-15 23:45:19.0", "2001-01-15 23:45:19.0", 0,3,0,0,0,0,0);
        testDateMinus("2001-04-15 23:45:19.0", "2000-12-15 23:45:19.0", 0,4,0,0,0,0,0);
        testDateMinus("2001-04-15 23:45:19.0", "2000-01-15 23:45:19.0", 0,15,0,0,0,0,0);
        testDateMinus("2001-04-15 23:45:19.0", "1999-12-15 23:45:19.0", 0,16,0,0,0,0,0);

        testDateMinus("2001-04-15 23:45:19.0", "2000-04-15 23:45:19.0", 1,0,0,0,0,0,0);
        testDateMinus("2001-04-15 23:45:19.0", "1999-04-15 23:45:19.0", 2,0,0,0,0,0,0);
        testDateMinus("2001-04-15 23:45:19.0", "1989-04-15 23:45:19.0", 12,0,0,0,0,0,0);

        testDateMinus("1900-04-15 23:45:19.0", "900-04-15 23:45:19.0", 1000,0,0,0,0,0,0);
    }

    @Test
    public void testMultipleFieldsMinus(){
        testDateMinus("2001-04-15 23:45:19.0", "2000-04-15 23:45:19.0", 1,0,0,0,0,0,0);
        testDateMinus("2001-04-15 23:45:19.0", "2000-03-15 23:45:19.0", 1,1,0,0,0,0,0);
        testDateMinus("2001-04-15 23:45:19.0", "2000-03-14 23:45:19.0", 1,1,1,0,0,0,0);
        testDateMinus("2001-04-15 23:45:19.0", "2000-03-14 22:45:19.0", 1,1,1,1,0,0,0);
        testDateMinus("2001-04-15 23:45:19.0", "2000-03-14 22:44:19.0", 1,1,1,1,1,0,0);
        testDateMinus("2001-04-15 23:45:19.0", "2000-03-14 22:44:18.0", 1,1,1,1,1,1,0);
        testDateMinus("2001-04-15 23:45:19.0", "2000-03-14 22:44:17.999999999", 1,1,1,1,1,1,1);
    }

    @Test
    public void testMultipleFieldsWithRolloversMinus(){
        testDateMinus("2001-04-15 23:45:19.0", "1999-12-15 23:45:19.0", 1,4,0,0,0,0,0);
        testDateMinus("2001-04-15 23:45:19.0", "1999-11-30 23:45:19.0", 1,4,15,0,0,0,0);
        testDateMinus("2001-04-15 23:45:19.0", "1999-11-30 00:45:19.0", 1,4,15,23,0,0,0);
        testDateMinus("2001-04-15 23:45:19.0", "1999-11-29 23:45:19.0", 1,4,15,24,0,0,0);
        testDateMinus("2001-04-15 23:45:19.0", "1999-11-29 23:00:19.0", 1,4,15,24,45,0,0);
        testDateMinus("2001-04-15 23:45:19.0", "1999-11-29 22:59:19.0", 1,4,15,24,46,0,0);
        testDateMinus("2001-04-15 23:45:19.0", "1999-11-29 22:59:00.0", 1,4,15,24,46,19,0);
        testDateMinus("2001-04-15 23:45:19.0", "1999-11-29 22:58:59.0", 1,4,15,24,46,20,0);
        testDateMinus("2001-04-15 23:45:19.0", "1999-11-29 22:58:58.999999999", 1,4,15,24,46,20,1);
    }

    @Test
    public void testDayOverflowMinus(){
        testDayOverflowMinus("2001-03-31 10:20:30.0", DayOverflow.LAST_DAY, "2001-02-28 10:20:30.0", 0,1,0,0,0,0,0);
        testDayOverflowMinus("2001-12-31 10:20:30.0", DayOverflow.LAST_DAY, "2001-11-30 10:20:30.0", 0,1,0,0,0,0,0);
        testDayOverflowMinus("2001-10-31 10:20:30.0", DayOverflow.LAST_DAY, "2001-09-30 10:20:30.0", 0,1,0,0,0,0,0);
        testDayOverflowMinus("2001-07-31 10:20:30.0", DayOverflow.LAST_DAY, "2001-06-30 10:20:30.0", 0,1,0,0,0,0,0);
        testDayOverflowMinus("2001-05-31 10:20:30.0", DayOverflow.LAST_DAY, "2001-04-30 10:20:30.0", 0,1,0,0,0,0,0);
        testDayOverflowMinus("2001-05-31 10:20:30.0", DayOverflow.LAST_DAY, "2001-02-28 10:20:30.0", 0,3,0,0,0,0,0);
    }

    @Test
    public void testWeekIssue(){
        testWeekIndex("2009-02-01", "2009-02-01", 1);
        testWeekIndex("2009-02-01", "2009-02-02", 1);
        testWeekIndex("2009-02-01", "2009-02-03", 1);
        testWeekIndex("2009-02-01", "2009-02-04", 1);
        testWeekIndex("2009-02-01", "2009-02-05", 1);
        testWeekIndex("2009-02-01", "2009-02-06", 1);
        testWeekIndex("2009-02-01", "2009-02-07", 1);
        testWeekIndex("2009-02-01", "2009-02-08", 2);
        testWeekIndex("2009-02-01", "2009-02-09", 2);
        testWeekIndex("2009-02-01", "2009-02-10", 2);
        testWeekIndex("2009-02-01", "2009-02-11", 2);
        testWeekIndex("2009-02-01", "2009-02-12", 2);
        testWeekIndex("2009-02-01", "2009-02-13", 2);
        testWeekIndex("2009-02-01", "2009-02-14", 2);
        testWeekIndex("2009-02-01", "2009-02-15", 3);
        testWeekIndex("2009-02-01", "2009-02-16", 3);

        testWeekIndex("2009-04-26", "2009-04-26", 1);
        testWeekIndex("2009-04-26", "2009-04-27", 1);
        testWeekIndex("2009-04-26", "2009-04-28", 1);
        testWeekIndex("2009-04-26", "2009-04-29", 1);
        testWeekIndex("2009-04-26", "2009-04-30", 1);
        testWeekIndex("2009-04-26", "2009-05-01", 1);
        testWeekIndex("2009-04-26", "2009-05-02", 1);
        testWeekIndex("2009-04-26", "2009-05-03", 2);
    }

    private void testDateTime(String aInput, String aExpected, int aYearIncr, int aMonthIncr, int aDayIncr, int aHourIncr, int aMinIncr, int aSecIncr, int aNanosIncr){
        DateTime from = GregorianDateTime.parseDateTime(aInput);
        DateTime result = from.plus(aYearIncr, aMonthIncr, aDayIncr, aHourIncr, aMinIncr, aSecIncr, aNanosIncr, DayOverflow.LAST_DAY);
        DateTime expectedResult = GregorianDateTime.parseDateTime(aExpected);
        if (!result.equals(expectedResult)){
            fail("Expected " + aExpected + ", but actual:" + result.format(FORMAT));
        }
    }

    private void testDate(String aInput, String aExpected, int aYearIncr, int aMonthIncr, int aDayIncr){
        Date from = GregorianDateTime.parseDate(aInput);
        Date result = from.plus(aYearIncr, aMonthIncr, aDayIncr, DayOverflow.LAST_DAY);
        Date expectedResult = GregorianDateTime.parseDate(aExpected);
        if (!result.equals(expectedResult)){
            fail("Expected " + aExpected + ", but actual:" + result.format(FORMAT));
        }
    }

    private void testRange(boolean aSuccess, int aYearIncr, int aMonthIncr, int aDayIncr, int aHourIncr, int aMinIncr, int aSecIncr, int aNanosIncr){
        DateTime from = GregorianDateTime.parseDateTime("0001-02-28 11:23:56.0");
        try {
            DateTime result = from.plus(aYearIncr, aMonthIncr, aDayIncr, aHourIncr, aMinIncr, aSecIncr, aNanosIncr, DayOverflow.LAST_DAY);
            if(!aSuccess){
                fail();
            }
        }
        catch(IllegalArgumentException ex){
            if(aSuccess){
                fail();
            }
        }
    }

    private void testDayOverflow(String aInput, DayOverflow aOverflow, String aExpected, int aYearIncr, int aMonthIncr, int aDayIncr, int aHourIncr, int aMinIncr, int aSecIncr, int aNanosIncr){
        DateTime from = GregorianDateTime.parseDateTime(aInput);
        DateTime expectedResult = GregorianDateTime.parseDateTime(aExpected);
        DateTime result = from.plus(aYearIncr, aMonthIncr, aDayIncr, aHourIncr, aMinIncr, aSecIncr, aNanosIncr, aOverflow);
        if (!result.equals(expectedResult)){
            fail("Expected " + aExpected + ", but actual:" + result.format(FORMAT));
        }
    }

    private void testDayOverflowMinus(String aInput, DayOverflow aOverflow, String aExpected, int aYearIncr, int aMonthIncr, int aDayIncr, int aHourIncr, int aMinIncr, int aSecIncr, int aNanosIncr){
        DateTime from = GregorianDateTime.parseDateTime(aInput);
        DateTime expectedResult = GregorianDateTime.parseDateTime(aExpected);
        DateTime result = from.minus(aYearIncr, aMonthIncr, aDayIncr, aHourIncr, aMinIncr, aSecIncr, aNanosIncr, aOverflow);
        if (!result.equals(expectedResult)){
            fail("Expected " + aExpected + ", but actual:" + result.format(FORMAT));
        }
    }

    private void testDayOverflowAbort(boolean aShouldAbort, String aInput,  int aYearIncr, int aMonthIncr, int aDayIncr, int aHourIncr, int aMinIncr, int aSecIncr, int aNanosIncr){
        DateTime from = GregorianDateTime.parseDateTime(aInput);
        try {
            DateTime result = from.plus(aYearIncr, aMonthIncr, aDayIncr, aHourIncr, aMinIncr, aSecIncr, aNanosIncr, DayOverflow.ABORT);
            if(aShouldAbort){
                fail();
            }
        }
        catch(RuntimeException ex){
            if (! aShouldAbort ){
                fail();
            }
        }
    }

    private void testDateMinus(String aInput, String aExpected, int aYearIncr, int aMonthIncr, int aDayIncr, int aHourIncr, int aMinIncr, int aSecIncr, int aNanosIncr){
        DateTime from = GregorianDateTime.parseDateTime(aInput);
        DateTime result = from.minus(aYearIncr, aMonthIncr, aDayIncr, aHourIncr, aMinIncr, aSecIncr, aNanosIncr, DayOverflow.LAST_DAY);
        DateTime expectedResult = GregorianDateTime.parseDateTime(aExpected);
        if (!result.equals(expectedResult)){
            fail("Expected " + aExpected + ", but actual:" + result.format(FORMAT));
        }
    }

    private void testWeekIndex(String aStartDate, String aEndDate, int aExpected){
        DateTime start = GregorianDateTime.parseDateTime(aStartDate);
        DateTime end = GregorianDateTime.parseDateTime(aEndDate);
        if( end.getWeekIndex(start) != aExpected) {
            fail("Expected:" + aExpected + " Actual:" + end.getWeekIndex(start) );
        }

    }

}
