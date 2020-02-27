package com.hunsley.ea.client;

import com.hunsley.ea.client.model.EaApiClientException;
import com.hunsley.ea.client.model.response.Item;
import com.hunsley.ea.client.model.response.Response;

/**
 *
 * @author jhunsley
 */
public interface RiverStationsService {

  /**
   * Finds the river stations, as {@link Item}s in a {@link Response} from the stations service
   * for a given river name. e.g. ?riverName=River%20Severn
   */
  Response getRiverStations(String riverName) throws EaApiClientException;
}
