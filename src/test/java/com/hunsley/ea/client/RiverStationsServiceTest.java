package com.hunsley.ea.client;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.hunsley.ea.client.RiverStationsServiceHttpClientImpl.RIVER_NAME_PARAM;
import static com.hunsley.ea.client.TestUtils.getFileBodyResponse;
import static junit.framework.TestCase.assertEquals;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.hunsley.ea.client.model.EaApiClientException;
import com.hunsley.ea.client.model.response.Response;
import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RiverStationsServiceTest {
  private WireMockServer wireMockServer;
  private final String path = "/flood-monitoring/id/stations";
  private RiverStationsService riverStationsService;

  @Before
  public void initWiremock() {
    riverStationsService = new RiverStationsServiceHttpClientImpl("http", "localhost:8080", path);
    wireMockServer = new WireMockServer(8080);
    wireMockServer.start();
  }

  @After
  public void tearDown() {
    wireMockServer.stop();
  }

  @Test
  public void testReadToday() throws IOException, EaApiClientException {
    final String url =
        path + "?" + RIVER_NAME_PARAM + "=Severn";
    stubFor(get(urlEqualTo(url)).willReturn(aResponse().withBody(
        getFileBodyResponse("src/test/resources/severnStations.json"))));
    Response response = riverStationsService.getRiverStations("Severn");
    assertEquals(21, response.getItems().size());
  }
}
