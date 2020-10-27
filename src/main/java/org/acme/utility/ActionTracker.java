package org.acme.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class ActionTracker {

  private final ConcurrentHashMap<String, ActionData> actionMap = new ConcurrentHashMap<>();
  private final ObjectMapper objectMapper = new ObjectMapper();

  /**
   * Add a new action time.
   *
   * @param actionJson - JSON string of the action
   *
   * @throws IOException - If the JSON string can't be deserialized
   */
  public void addTime(String actionJson) throws IOException {
    ActionEntry entry = objectMapper.readValue(actionJson, ActionEntry.class);
    addTime(entry);
  }

  public void addTime(ActionEntry actionEntry) {
    actionMap.merge(actionEntry.getAction(),
        new ActionData(actionEntry.getTime()),
        (a, b) -> a.addTime(actionEntry.getTime()));
  }

  /**
   * Get a JSON array of all the actions added and their average times.
   *
   * @return String - The JSON array of all actions and their average times.
   *
   * @throws JsonProcessingException - If the JSON string can't be created.
   */
  public String getStats() throws JsonProcessingException {
    ActionStatCollection statCollection = new ActionStatCollection();
    actionMap.forEach((key, value) -> statCollection.addActionStat(new ActionStat(key, value.getAverage())));
    return statCollection.toJson();
  }
}
