package com.hunsley.ea.client.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.SortedSet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReadingsResponse {
  private Meta meta;
  private SortedSet<Item> items;
}
