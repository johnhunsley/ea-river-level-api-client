package com.hunsley.ea.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunsley.ea.client.model.EaApiClientException;
import com.hunsley.ea.client.model.response.ReadingsResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.hunsley.ea.client.model.Util.DATE_FORMATTER;
import static com.hunsley.ea.client.model.Util.DATE_TIME_FORMATTER;
import static java.lang.String.format;

/**
 * Creates an {@link HttpClient} instance which executes requests to the Readings API Service
 *
 * @author johnhunsley
 */
@Slf4j
public class ReadingsServiceHttpClientImpl implements ReadingsService {
    private static final String READINGS_PATH = "/readings";
    private static final String TODAY_PARAM = "today";
    private static final String LATEST_PARAM = "latest";
    private static final String FROM_PARAM = "startdate";
    private static final String END_PARAM = "enddate";
    private static final String SINCE_PARAM = "since";
    private static final String SORTED_PARAM = "_sorted";
    private final String scheme;
    private final String host;
    private final String path;
    private final HttpClient client;
    private final ObjectMapper objectMapper;

    public ReadingsServiceHttpClientImpl(final String scheme, final String host, final String path) {
        client = HttpClientBuilder.create().build();
        objectMapper = new ObjectMapper();
        this.scheme = scheme;
        this.host = host;
        this.path = path;
    }

    @Override
    public ReadingsResponse getLatestLevelReadings(final int stationId) throws EaApiClientException {
        try {
            final URI uri = builder(stationId)
                    .addParameter(LATEST_PARAM, null).build();
            return get(uri);

        } catch (URISyntaxException e) {
            log.warn(e.getMessage());
            throw new EaApiClientException(e);
        }
    }

    @Override
    public ReadingsResponse getTodaysLevelReadings(final int stationId) throws EaApiClientException {
        try {
            final URI uri = builder(stationId).addParameter(TODAY_PARAM,null)
                    .addParameter(SORTED_PARAM, null).build();
            return get(uri);

        } catch (URISyntaxException e) {
            log.warn(e.getMessage());
            throw new EaApiClientException(e);
        }
    }

    @Override
    public ReadingsResponse getLevelReadingsFromDate(final int stationId, LocalDateTime from) throws EaApiClientException {
        try {
            final URI uri = builder(stationId).addParameter(SINCE_PARAM, from.format(DATE_TIME_FORMATTER))
                    .addParameter(SORTED_PARAM, null).build();
            return get(uri);

        } catch (URISyntaxException e) {
            log.warn(e.getMessage());
            throw new EaApiClientException(e);
        }
    }

    @Override
    public ReadingsResponse getLevelReadingsBetweenDates(int stationId, LocalDate from, LocalDate to) throws EaApiClientException {
        try {
            final URI uri = builder(stationId).addParameter(FROM_PARAM, from.format(DATE_FORMATTER))
                    .addParameter(END_PARAM, to.format(DATE_FORMATTER))
                    .addParameter(SORTED_PARAM, null).build();
            return get(uri);

        } catch (URISyntaxException e) {
            log.warn(e.getMessage());
            throw new EaApiClientException(e);
        }
    }

    private URIBuilder builder(final int stationId) {
        return new URIBuilder().setScheme(scheme).setHost(host).setPath(path + "/" + stationId + "/" + READINGS_PATH);
    }

    private ReadingsResponse get(URI uri) throws EaApiClientException {
        log.info("Getting reading from " + uri.toString());

        try {
            HttpResponse response = client.execute(new HttpGet(uri));

            if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                final String errorMsg =
                        format("Request to %s failed with status code %s", uri.toString(),
                                response.getStatusLine().getStatusCode());
                log.warn(errorMsg);
                throw new EaApiClientException(errorMsg);
            }

            return objectMapper.readValue(response.getEntity().getContent(), ReadingsResponse.class);

        } catch (IOException e) {
            throw new EaApiClientException(e);
        }
    }
}