package org.acme.utility;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ActionStat {

  @JsonProperty private String action;
  @JsonProperty private double avg;

  public ActionStat() {
  }

  public ActionStat(String action, double avg) {
    this.action = action;
    this.avg = avg;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public double getAvg() {
    return avg;
  }

  public void setAvg(double avg) {
    this.avg = avg;
  }
}
