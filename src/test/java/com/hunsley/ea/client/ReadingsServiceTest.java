package com.hunsley.ea.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.hunsley.ea.client.model.EaApiClientException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.hunsley.ea.client.ReadingsServiceHttpClientImpl.READINGS_PATH;
import static com.hunsley.ea.client.ReadingsServiceHttpClientImpl.TODAY_PARAM;

/**
 * Wiremock test
 */
public class ReadingsServiceTest {
    private WireMockServer wireMockServer;
    private final String path = "flood-monitoring/id/stations";
    private final int stationId = 2077;
    private ReadingsService readingsService;

    @Before
    public void initWiremock() {
        readingsService = new ReadingsServiceHttpClientImpl("http", "localhost:8080", path);
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        configureFor("localhost", 8080);
    }

    @Test
    public void testReadToday() throws IOException, EaApiClientException {
        final String url = path + "/" + stationId + "/" + READINGS_PATH + "?" + TODAY_PARAM;
        stubFor(get(urlEqualTo(url)).willReturn(aResponse().withBody(getBodyResponse())));
        readingsService.getTodaysLevelReadings(stationId);
    }

    private byte[] getBodyResponse() throws IOException {
        Path fileLocation = Paths.get("src/test/resources/readings.json");
        return Files.readAllBytes(fileLocation);
    }

    @After
    public void tearDown() {
        wireMockServer.stop();
    }
}
