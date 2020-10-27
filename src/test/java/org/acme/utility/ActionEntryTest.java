package org.acme.utility;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

class ActionEntryTest {

  private static final String TEST_JSON = "{\"action\":\"jump\",\"time\":100.52}";
  private ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void actionEntryCanBeSerialized() throws Exception {
    ActionEntry entry = objectMapper.readValue(TEST_JSON, ActionEntry.class);

    assertThat(entry.getAction()).isEqualTo("jump");
    assertThat(entry.getTime()).isEqualTo(100.52);
  }

  @Test
  void actionEntryCanBeDeserialized() throws Exception {
    ActionEntry entry = new ActionEntry("jump", 100.52);

    String result = objectMapper.writeValueAsString(entry);
    assertThat(result).isEqualTo(TEST_JSON);
  }

  @Test
  void invalidEntryThrowsException() {
    assertThatThrownBy(() ->
        objectMapper.readValue("{\"event\":\"jump\",\"time\":100.52}", ActionEntry.class))
        .isInstanceOf(Exception.class)
        .hasMessageContaining("Unrecognized field");
  }
}