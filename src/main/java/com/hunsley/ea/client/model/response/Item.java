package com.hunsley.ea.client.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Item implements Comparable<Item> {
  @JsonProperty("@id")
  private String id;

  private String dateTime;

  private BigDecimal value;

  public LocalDateTime getLocalDateTime() {
    return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
  }

  @Override
  public int compareTo(Item that) {
    return that.getLocalDateTime().compareTo(this.getLocalDateTime());
  }
}
