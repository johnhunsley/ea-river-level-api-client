package com.hunsley.ea.client;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestUtils {

  public static byte[] getFileBodyResponse(final String filePath) throws IOException {
    Path fileLocation = Paths.get(filePath);
    return Files.readAllBytes(fileLocation);
  }
}
