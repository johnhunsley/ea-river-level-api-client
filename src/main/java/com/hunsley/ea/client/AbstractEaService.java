package com.hunsley.ea.client;

import static java.lang.String.format;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunsley.ea.client.model.EaApiClientException;
import com.hunsley.ea.client.model.response.Response;
import java.io.IOException;
import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;

@Slf4j
public abstract class AbstractEaService {
  protected final String scheme;
  protected final String host;
  protected final String path;
  protected final HttpClient client;
  protected final ObjectMapper objectMapper;

  protected AbstractEaService(final String scheme, final String host, final String path) {
    client = HttpClientBuilder.create().build();
    objectMapper = new ObjectMapper();
    this.scheme = scheme;
    this.host = host;
    this.path = path;
  }

  /**
   * GET the response from the given URI. Will throw a {@link EaApiClientException} if the response status
   * is anything other than 200 OK
   */
  protected final Response get(URI uri) throws EaApiClientException {
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

      return objectMapper.readValue(response.getEntity().getContent(), Response.class);

    } catch (IOException e) {
      throw new EaApiClientException(e);
    }
  }

  protected URIBuilder builder() {
    return new URIBuilder().setScheme(scheme).setHost(host);
  }
}
