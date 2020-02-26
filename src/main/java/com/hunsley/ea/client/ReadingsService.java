package com.hunsley.ea.client;

import com.hunsley.ea.client.model.EaApiClientException;
import com.hunsley.ea.client.model.response.ReadingsResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Finds the Level Readings for a given station id.
 *
 * @author jhunsley
 */
public interface ReadingsService {

  /**
   * Return only the most recently available reading for each included measure.
   *
   * ?latest
   */
  ReadingsResponse getLatestLevelReadings(int stationId) throws EaApiClientException;

  /**
   * Return all the readings taken today for each included measure.
   *
   * ?today&_sorted
   */
  ReadingsResponse getTodaysLevelReadings(int stationId) throws EaApiClientException;

  /**
   *  Return the readings taken since the given date time (not inclusive), up to the specified _limit.
   *  If no _limit is given then a default limit of 500 will be used. Typically when tracking a particular measurement
   *  then use the dateTime of the last retrieved value as the since parameter to find any new readings.
   *  Will accept a simple date value such as 2016-09-07 which will be interpreted as 2016-09-07T:00:00:00Z.
   *
   * ?since=2016-09-07T10:30:00Z
   */
  ReadingsResponse getLevelReadingsFromDate(int stationId, LocalDateTime from) throws EaApiClientException;

  /**
   * Return the readings taken on the specified range of days for each included measure, up to the specified _limit.
   * If no _limit is given then a default limit of 500 will be used.
   *
   * ?startdate=2015-02-05&enddate=2015-02-07
   */
  ReadingsResponse getLevelReadingsBetweenDates(int stationId, LocalDate from, LocalDate to) throws EaApiClientException;


}
