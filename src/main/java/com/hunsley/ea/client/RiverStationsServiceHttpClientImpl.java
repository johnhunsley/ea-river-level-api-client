package com.hunsley.ea.client;

import com.hunsley.ea.client.model.EaApiClientException;
import com.hunsley.ea.client.model.response.Response;
import java.net.URI;
import java.net.URISyntaxException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;

@Slf4j
public class RiverStationsServiceHttpClientImpl extends AbstractEaService implements RiverStationsService {
  public final static String RIVER_NAME_PARAM = "riverName";

  public RiverStationsServiceHttpClientImpl(final String scheme, final String host, final String path) {
    super(scheme, host, path);
  }

  @Override
  public Response getRiverStations(final String riverName) throws EaApiClientException {
    try {
      final URI uri = builder().addParameter(RIVER_NAME_PARAM, riverName).build();
      return get(uri);

    } catch (URISyntaxException e) {
      log.warn(e.getMessage());
      throw new EaApiClientException(e);
    }
  }

  /**
   * Builds a URI to the readings API with the given station Id as a path variable
   */
  protected URIBuilder builder() {
    return super.builder().setPath(path);
  }

}
