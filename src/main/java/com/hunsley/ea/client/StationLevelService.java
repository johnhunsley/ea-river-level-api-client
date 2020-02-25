package com.hunsley.ea.client;

import com.hunsley.ea.client.model.response.ReadingsResponse;
import java.time.LocalDate;

public interface StationLevelService {

  ReadingsResponse getLatestLevelReadings(int stationId);

  ReadingsResponse getTodaysLevelReadings(int stationId);

  ReadingsResponse getLevelReadingsFromDate(int stationId, LocalDate from);

  ReadingsResponse getLevelReadingsBetweenDates(int stationId, LocalDate from, LocalDate to);


}
