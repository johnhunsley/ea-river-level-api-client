package com.hunsley.ea.client.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.hunsley.ea.client.model.Util.DATE_FORMATTER;

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
    return LocalDateTime.parse(dateTime, DATE_FORMATTER);
  }

  @Override
  public int compareTo(Item that) {
    return that.getLocalDateTime().compareTo(this.getLocalDateTime());
  }
}
