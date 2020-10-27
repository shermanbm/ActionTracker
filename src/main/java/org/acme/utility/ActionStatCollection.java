package org.acme.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;

public class ActionStatCollection {

  private final List<ActionStat> actionStats = new ArrayList<>();
  private final ObjectMapper objectMapper = new ObjectMapper();

  public List<ActionStat> getActionStats() {
    return actionStats;
  }

  public void addActionStat(ActionStat actionStat) {
    actionStats.add(actionStat);
  }

  public String toJson() throws JsonProcessingException {
    return objectMapper.writeValueAsString(actionStats);
  }
}
