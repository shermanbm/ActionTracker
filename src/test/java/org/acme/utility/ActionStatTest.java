package org.acme.utility;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

class ActionStatTest {
  private static final String TEST_JSON = "{\"action\":\"jump\",\"avg\":100.52}";
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void actionStatCanBeSerialized() throws Exception {
    ActionStat stat = objectMapper.readValue(TEST_JSON, ActionStat.class);

    assertThat(stat.getAction()).isEqualTo("jump");
    assertThat(stat.getAvg()).isEqualTo(100.52);
  }

  @Test
  void actionStatCanBeDeserialized() throws Exception {
    ActionStat stat = new ActionStat("jump", 100.52);

    String result = objectMapper.writeValueAsString(stat);
    assertThat(result).isEqualTo(TEST_JSON);
  }

  @Test
  void invalidStatThrowsException() {
    assertThatThrownBy(() ->
        objectMapper.readValue("{\"action\":\"jump\",\"time\":100.52}", ActionStat.class))
        .isInstanceOf(Exception.class)
        .hasMessageContaining("Unrecognized field");

  }

}