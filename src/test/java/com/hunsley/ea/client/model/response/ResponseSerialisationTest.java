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
    Response response = mapper.readValue(new File("src/test/resources/readingsToday.json"), Response.class);
    assertNotNull(response);
    assertEquals(50, response.getItems().size());
  }
}
