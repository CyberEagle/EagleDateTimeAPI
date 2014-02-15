EagleDateTimeAPI
================

It's an alternative to Date, Calendar and related Java APIs. It was created based on the following project:

Date4J: http://www.date4j.net/

Author: Hirondelle Systems

License: BSD (http://www.date4j.net/LICENSE_BSD.txt)


Motivations
===========

It was created with almost the same motivations that Date4J. So, read the motivations of Date4J first:
http://www.date4j.net/#Problem

Beyond the motivations of Date4J, this API was created to accomplish the following goals:

- To separate the concepts of Date, Time and DateTime.
- To give easy operations for each of these concepts.

It was created because we the Date4J wasn't working well when we wanted to use operations on a time-only DateTime. The operations of 'plus' and 'minus', for example, wasn't expecting you to sum up two time-only DateTimes. So, if you needed just a Time object, you weren't well supported by Date4J.

How was it created?
===================

We separated the concepts of Date, Time and DateTime and used some of the source from Date4J.
