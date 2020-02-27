package com.hunsley.ea.client.model.response;

import static com.hunsley.ea.client.model.Util.DATE_TIME_FORMATTER;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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

  private String notation;

  private String label;

  private BigDecimal lat;

  private String catchmentName;

  private String town;

  @JsonProperty("long")
  private BigDecimal longitude;

  public LocalDateTime getLocalDateTime() {
    return LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER);
  }

  @Override
  public int compareTo(Item that) {
    return that.getLocalDateTime().compareTo(this.getLocalDateTime());
  }
}
