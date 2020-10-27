package org.acme.utility;

public class ActionData {

  private double total;
  private int count;
  private double average;

  public ActionData(double startingValue) {
    total = startingValue;
    count = 1;
    average = startingValue;
  }

  public ActionData addTime(double newTime) {
    synchronized (this) {
      count++;
      total += newTime;
      average = total / count;
    }
    return this;
  }

  public double getAverage() {
    return average;
  }

}
