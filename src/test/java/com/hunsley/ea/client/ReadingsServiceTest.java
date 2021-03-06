package com.hunsley.ea.client;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.hunsley.ea.client.ReadingsServiceHttpClientImpl.END_PARAM;
import static com.hunsley.ea.client.ReadingsServiceHttpClientImpl.FROM_PARAM;
import static com.hunsley.ea.client.ReadingsServiceHttpClientImpl.LATEST_PARAM;
import static com.hunsley.ea.client.ReadingsServiceHttpClientImpl.READINGS_PATH;
import static com.hunsley.ea.client.ReadingsServiceHttpClientImpl.SINCE_PARAM;
import static com.hunsley.ea.client.ReadingsServiceHttpClientImpl.SORTED_PARAM;
import static com.hunsley.ea.client.ReadingsServiceHttpClientImpl.TODAY_PARAM;
import static com.hunsley.ea.client.TestUtils.getFileBodyResponse;
import static com.hunsley.ea.client.model.Util.DATE_TIME_FORMATTER;
import static junit.framework.TestCase.assertEquals;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.hunsley.ea.client.model.EaApiClientException;
import com.hunsley.ea.client.model.response.Response;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Wiremock test
 */
public class ReadingsServiceTest {
    private WireMockServer wireMockServer;
    private final String path = "/flood-monitoring/id/stations";
    private final int stationId = 2077;
    private ReadingsService readingsService;

    @Before
    public void initWiremock() {
        readingsService = new ReadingsServiceHttpClientImpl("http", "localhost:8080", path);
        wireMockServer = new WireMockServer(8080);
        wireMockServer.start();
    }

    @Test
    public void testReadToday() throws IOException, EaApiClientException {
        final String url =
            path + "/" + stationId + "/" + READINGS_PATH + "?" + TODAY_PARAM + "&" + SORTED_PARAM;
        stubFor(get(urlEqualTo(url)).willReturn(aResponse().withBody(getFileBodyResponse("src/test/resources/readingsToday.json"))));
        Response response = readingsService.getTodaysLevelReadings(stationId);
        assertEquals(50, response.getItems().size());
    }

    @Test
    public void testReadLatest() throws IOException, EaApiClientException {
        final String url =
            path + "/" + stationId + "/" + READINGS_PATH + "?" + LATEST_PARAM;
        stubFor(get(urlEqualTo(url)).willReturn(aResponse().withBody(getFileBodyResponse(
            "src/test/resources/readingsLatest.json"))));
        Response response = readingsService.getLatestLevelReadings(stationId);
        assertEquals(1, response.getItems().size());
        assertEquals(BigDecimal.valueOf(4.805), response.getItems().get(0).getValue());
    }

    @Test
    public void testReadSince() throws IOException, EaApiClientException {
        final String url =
            path + "/" + stationId + "/" + READINGS_PATH + "?" + SINCE_PARAM + "=2020-02-26T10%3A30%3A00Z&" + SORTED_PARAM;
        stubFor(get(urlEqualTo(url)).willReturn(aResponse().withBody(getFileBodyResponse(
            "src/test/resources/readingsSince.json"))));
        Response response = readingsService.getLevelReadingsFromDate(
            stationId, LocalDateTime.parse("2020-02-26T10:30:00Z", DATE_TIME_FORMATTER));
        assertEquals(14, response.getItems().size());
        assertEquals(BigDecimal.valueOf(4.77), response.getItems().get(0).getValue());
        assertEquals(LocalDateTime.parse("2020-02-26T14:00:00Z", DATE_TIME_FORMATTER),
            response.getItems().get(0).getLocalDateTime());
    }

    @Test
    public void testReadBetweenDates() throws IOException, EaApiClientException {
        final String url =
            path + "/" + stationId + "/" + READINGS_PATH + "?" + FROM_PARAM + "=2020-02-18&" + END_PARAM
                + "=2020-02-19&" + SORTED_PARAM;
        stubFor(get(urlEqualTo(url)).willReturn(aResponse().withBody(getFileBodyResponse(
            "src/test/resources/readingsBetween.json"))));
        Response response = readingsService.getLevelReadingsBetweenDates(
            stationId, LocalDate.parse("2020-02-18", DateTimeFormatter.ISO_DATE),
            LocalDate.parse("2020-02-19", DateTimeFormatter.ISO_DATE));
        assertEquals(191, response.getItems().size());
        assertEquals(BigDecimal.valueOf(3.498), response.getItems().get(0).getValue());
    }


    @After
    public void tearDown() {
        wireMockServer.stop();
    }
}
