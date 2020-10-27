package org.acme.utility;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ActionEntry {

  @JsonProperty private String action;
  @JsonProperty private double time;

  public ActionEntry() {

  }

  public ActionEntry(String action, double time) {
    this.action = action;
    this.time = time;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public double getTime() {
    return time;
  }

  public void setTime(double time) {
    this.time = time;
  }
}
