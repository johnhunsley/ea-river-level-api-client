package com.hunsley.ea.client;

import com.hunsley.ea.client.model.response.ReadingsResponse;
import java.time.LocalDate;

/**
 * Finds the Level Readings for a given station id.
 *
 * @author jhunsley
 */
public interface StationLevelService {

  /**
   * https://environment.data.gov.uk/flood-monitoring/id/stations/{id}/readings?latest
   */
  ReadingsResponse getLatestLevelReadings(int stationId);

  /**
   * https://environment.data.gov.uk/flood-monitoring/id/stations/{id}/readings?today&_sorted
   */
  ReadingsResponse getTodaysLevelReadings(int stationId);

  ReadingsResponse getLevelReadingsFromDate(int stationId, LocalDate from);

  ReadingsResponse getLevelReadingsBetweenDates(int stationId, LocalDate from, LocalDate to);


}