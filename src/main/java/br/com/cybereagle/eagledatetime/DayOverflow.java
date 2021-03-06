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

package br.com.cybereagle.eagledatetime;

/**
 * Policy for treating 'day-of-the-month overflow' conditions encountered during some date calculations.
 * <p/>
 * <P>Months are different from other units of time, since the length of a month is not fixed, but rather varies with
 * both month and year. This leads to problems. Take the following simple calculation, for example :
 * <p/>
 * <PRE>May 31 + 1 month = ?</PRE>
 * <p/>
 * <P>What's the answer? Since there is no such thing as June 31, the result of this operation is inherently ambiguous.
 * This  <tt>DayOverflow</tt> enumeration lists the various policies for treating such situations, as supported by
 * <tt>DateTime</tt>.
 * <p/>
 * <P>This table illustrates how the policies behave :
 * <P><table BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <tr>
 * <th>Date</th>
 * <th>DayOverflow</th>
 * <th>Result</th>
 * </tr>
 * <tr>
 * <td>May 31 + 1 Month</td>
 * <td>LAST_DAY</td>
 * <td>June 30</td>
 * </tr>
 * <tr>
 * <td>May 31 + 1 Month</td>
 * <td>FIRST_DAY</td>
 * <td>July 1</td>
 * </tr>
 * <tr>
 * <td>December 31, 2001 + 2 Months</td>
 * <td>SPILLOVER</td>
 * <td>March 3</td>
 * </tr>
 * <tr>
 * <td>May 31 + 1 Month</td>
 * <td>ABORT</td>
 * <td>RuntimeException</td>
 * </tr>
 * </table>
 */
public enum DayOverflow {
    /**
     * Coerce the day to the last day of the month.
     */
    LAST_DAY,
    /**
     * Coerce the day to the first day of the next month.
     */
    FIRST_DAY,
    /**
     * SPILLOVER the day into the next month.
     */
    SPILLOVER,
    /**
     * Throw a RuntimeException.
     */
    ABORT;
}
