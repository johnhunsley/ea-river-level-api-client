package com.hunsley.ea.client.model;

import java.time.format.DateTimeFormatter;

public class Util {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_DATE;
}
