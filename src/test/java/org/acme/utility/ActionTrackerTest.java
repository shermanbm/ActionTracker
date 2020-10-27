package org.acme.utility;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ActionTrackerTest {
  private final String[] jumpActions = {"{\"action\":\"jump\", \"time\":100}",
      "{\"action\":\"jump\", \"time\":200}",
      "{\"action\":\"jump\", \"time\":300}",
      "{\"action\":\"jump\", \"time\":400}"};
  private final String jumpResult = "{\"action\":\"jump\",\"avg\":250.0}";

  private final String[] chopActions = {"{\"action\":\"chop\", \"time\":67}",
      "{\"action\":\"chop\", \"time\":238.25}",
      "{\"action\":\"chop\", \"time\":521.75}",
      "{\"action\":\"chop\", \"time\":398}"};
  private final String chopResult = "{\"action\":\"chop\",\"avg\":306.25}";

  private final String[] kickActions = {"{\"action\":\"kick\", \"time\":642.68}",
      "{\"action\":\"kick\", \"time\":12368.45}",
      "{\"action\":\"kick\", \"time\":324635.57}",
      "{\"action\":\"kick\", \"time\":8476788.4}"};
  private final String kickResult = "{\"action\":\"kick\",\"avg\":2203608.775}";

  private ActionTracker tracker;
  private Random random = new Random();

  @BeforeEach
  void setup() {
    tracker = new ActionTracker();
  }

  @Test
  void singleActionIsReturned() throws Exception {
    String action = "{\"action\":\"jump\", \"time\":100}";
    tracker.addTime(action);

    String result = tracker.getStats();

    assertThat(result).isEqualTo("[{\"action\":\"jump\",\"avg\":100.0}]");
  }

  @Test
  void singleActionMultipleEntriesCorrectAverageIsReturned() throws Exception {
    for (String action : jumpActions) {
      tracker.addTime(action);
    }

    String result = tracker.getStats();

    assertThat(result).isEqualTo("[" + jumpResult + "]");
  }

  @Test
  void multipleActionsMultipleEntriesCorrectAverageIsReturned() throws Exception {
    for (String action : jumpActions) {
      tracker.addTime(action);
    }
    for (String action : chopActions) {
      tracker.addTime(action);
    }
    for (String action : kickActions) {
      tracker.addTime(action);
    }

    String result = tracker.getStats();

    assertThat(result).contains(jumpResult);
    assertThat(result).contains(chopResult);
    assertThat(result).contains(kickResult);
  }

  @Test
  public void testCounterWithConcurrency() throws Exception {
    int numberOfThreads = 3;
    int entries = 1000;
    AtomicInteger atomicInteger = new AtomicInteger(0);
    String[] actions = {"jump", "kick", "chop"};
    double[] totals = {0, 0, 0};
    int[] counts = {0, 0, 0};
    String[][] data = new String[numberOfThreads][entries];
    for (int i=0; i<numberOfThreads; i++) {
      for (int j=0; j<entries; j++) {
        int index = random.nextInt(3);
        double time = random.nextInt(100000000) / 100;
        counts[index]++;
        totals[index] += time;
        data[i][j] = String.format("{\"action\":\"%s\", \"time\":%f}", actions[index], time);
      }
    }
    ExecutorService service = Executors.newFixedThreadPool(numberOfThreads);
    CountDownLatch latch = new CountDownLatch(numberOfThreads);
    for (int i = 0; i < numberOfThreads; i++) {
      int finalI = i;
      service.execute(() -> {
        try {
          for (int j = 0; j < entries; j++) {
            tracker.addTime(data[finalI][j]);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
        latch.countDown();
      });
    }
    latch.await();

    String result = tracker.getStats();
    assertThat(result).contains("{\"action\":\"jump\",\"avg\":" + totals[0]/counts[0] + "}");
    assertThat(result).contains("{\"action\":\"kick\",\"avg\":" + totals[1]/counts[1] + "}");
    assertThat(result).contains("{\"action\":\"chop\",\"avg\":" + totals[2]/counts[2] + "}");
  }

}