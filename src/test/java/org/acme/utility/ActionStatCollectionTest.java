package org.acme.utility;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ActionStatCollectionTest {


  @Test
  void collectionWithSingleEntryCanBeWrittenToJsonString() throws Exception {
    ActionStat stat = new ActionStat("jump", 100.52);
    ActionStatCollection collection = new ActionStatCollection();
    collection.addActionStat(stat);

    String result = collection.toJson();

    assertThat(result).isEqualTo("[{\"action\":\"jump\",\"avg\":100.52}]");
  }

  @Test
  void collectionWithNoEntriesCanBeWrittenToJsonString() throws Exception {
    ActionStatCollection collection = new ActionStatCollection();

    String result = collection.toJson();

    assertThat(result).isEqualTo("[]");
  }

  @Test
  void collectionWithMultipleEntriesCanBeWrittenToJsonString() throws Exception {
    ActionStat statJump = new ActionStat("jump", 100.52);
    ActionStat statKick = new ActionStat("kick", 64.5);
    ActionStat statChop = new ActionStat("chop", 5346.4);
    ActionStatCollection collection = new ActionStatCollection();
    collection.addActionStat(statJump);
    collection.addActionStat(statKick);
    collection.addActionStat(statChop);

    String result = collection.toJson();

    assertThat(result).isEqualTo("[{\"action\":\"jump\",\"avg\":100.52},"
        + "{\"action\":\"kick\",\"avg\":64.5},"
        + "{\"action\":\"chop\",\"avg\":5346.4}]");
  }

}