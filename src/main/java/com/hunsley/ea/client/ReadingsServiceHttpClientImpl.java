package com.hunsley.ea.client;

import static com.hunsley.ea.client.model.Util.DATE_FORMATTER;
import static com.hunsley.ea.client.model.Util.DATE_TIME_FORMATTER;

import com.hunsley.ea.client.model.EaApiClientException;
import com.hunsley.ea.client.model.response.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.client.utils.URIBuilder;

/**
 * Creates an {@link HttpClient} instance which executes requests to the Readings API Service
 *
 * @author johnhunsley
 */
@Slf4j
public class ReadingsServiceHttpClientImpl extends AbstractEaService implements ReadingsService {
    public static final String READINGS_PATH = "readings";
    public static final String TODAY_PARAM = "today";
    public static final String LATEST_PARAM = "latest";
    public static final String FROM_PARAM = "startdate";
    public static final String END_PARAM = "enddate";
    public static final String SINCE_PARAM = "since";
    public static final String SORTED_PARAM = "_sorted";

    public ReadingsServiceHttpClientImpl(final String scheme, final String host, final String path) {
        super(scheme, host, path);
    }

    @Override
    public Response getLatestLevelReadings(final int stationId) throws EaApiClientException {
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
    public Response getTodaysLevelReadings(final int stationId) throws EaApiClientException {
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
    public Response getLevelReadingsFromDate(final int stationId, LocalDateTime from) throws EaApiClientException {
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
    public Response getLevelReadingsBetweenDates(final int stationId, LocalDate from, LocalDate to) throws EaApiClientException {
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

    /**
     * Builds a URI to the readings API with the given station Id as a path variable
     */
    private URIBuilder builder(final int stationId) {
        return builder().setPath(path + "/" + stationId + "/" + READINGS_PATH);
    }


}
