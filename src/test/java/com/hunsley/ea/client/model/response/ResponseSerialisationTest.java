package com.hunsley.ea.client.model.response;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import org.junit.Test;


public class ResponseSerialisationTest {

  @Test
  public void testSerialise() throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    ReadingsResponse readingsResponse = mapper.readValue(new File("src/test/resources/readings.json"), ReadingsResponse.class);
    assertNotNull(readingsResponse);
    assertEquals(50, readingsResponse.getItems().size());
  }
}
