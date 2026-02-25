package zapsdtest.testframework.containers.sequences;

import apsd.interfaces.containers.sequences.DynVector;
import apsd.classes.utilities.Natural;

import zapsdtest.testframework.containers.base.ResizableContainerTest;

import static org.junit.jupiter.api.Assertions.*;

public interface DynVectorTest<Data, Con extends DynVector<Data>> extends VectorTest<Data, Con>, ResizableContainerTest<Con> {

  default void TestInsertFirstWithAutoExpansion(Data element) {
    BeginTest("InsertFirstWithAutoExpansion");
    long initialSize = ThisContainer().Size().ToLong();
    ThisContainer().InsertFirst(element);
    assertEquals(initialSize + 1, ThisContainer().Size().ToLong(),
    "InsertFirst should automatically expand size");
    EndTest();
  }

  default void TestInsertLastWithAutoExpansion(Data element) {
    BeginTest("InsertLastWithAutoExpansion");
    long initialSize = ThisContainer().Size().ToLong();
    ThisContainer().InsertLast(element);
    assertEquals(initialSize + 1, ThisContainer().Size().ToLong(),
    "InsertLast should automatically expand size");
    EndTest();
  }

  default void TestInsertAtWithAutoExpansion(Natural position, Data element) {
    BeginTest("InsertAtWithAutoExpansion");
    if (position == null) {
      assertThrows(NullPointerException.class,
      () -> ThisContainer().InsertAt(element, position),
      "InsertAt should throw exception for null position");
      EndTest();
      return;
    }
    long initialSize = ThisContainer().Size().ToLong();
    ThisContainer().InsertAt(element, position);
    assertEquals(initialSize + 1, ThisContainer().Size().ToLong(),
    "InsertAt should automatically expand size");
    EndTest();
  }

  default void TestAtNRemoveWithAutoReduction(Natural position, Data expectedElement) {
    BeginTest("AtNRemoveWithAutoReduction");
    if (position == null) {
      assertThrows(NullPointerException.class,
      () -> ThisContainer().AtNRemove(position),
      "AtNRemove should throw exception for null position");
      EndTest();
      return;
    }
    long initialSize = ThisContainer().Size().ToLong();
    assertEquals(expectedElement, ThisContainer().AtNRemove(position),
    "AtNRemove should return the removed element");
    assertEquals(initialSize - 1, ThisContainer().Size().ToLong(),
    "AtNRemove should automatically reduce size");
    EndTest();
  }

  default void TestRemoveFirstWithAutoReduction() {
    TestRemoveFirstWithAutoReduction(false);
  }

  default void TestRemoveFirstWithAutoReduction(boolean edgeCase) {
    BeginTest("RemoveFirstWithAutoReduction");
    if (edgeCase && ThisContainer().Size().ToLong() == 0L) {
      assertThrows(IndexOutOfBoundsException.class,
      () -> ThisContainer().RemoveFirst(),
      "RemoveFirst should throw exception when removing from empty container");
      EndTest();
      return;
    }
    long initialSize = ThisContainer().Size().ToLong();
    Data removed = ThisContainer().GetFirst();
    ThisContainer().RemoveFirst();
    assertNotNull(removed,
    "RemoveFirst should return the removed element");
    assertEquals(initialSize - 1, ThisContainer().Size().ToLong(),
    "RemoveFirst should automatically reduce size");
    EndTest();
  }

  default void TestRemoveLastWithAutoReduction() {
    TestRemoveLastWithAutoReduction(false);
  }

  default void TestRemoveLastWithAutoReduction(boolean edgeCase) {
    BeginTest("RemoveLastWithAutoReduction");
    if (edgeCase && ThisContainer().Size().ToLong() == 0L) {
      assertThrows(IndexOutOfBoundsException.class,
      () -> ThisContainer().RemoveLast(),
      "RemoveLast should throw exception when removing from empty container");
      EndTest();
      return;
    }
    long initialSize = ThisContainer().Size().ToLong();
    Data removed = ThisContainer().GetLast();
    ThisContainer().RemoveLast();
    assertNotNull(removed,
    "RemoveLast should return the removed element");
    assertEquals(initialSize - 1, ThisContainer().Size().ToLong(),
    "RemoveLast should automatically reduce size");
    EndTest();
  }

  default void TestFirstNRemoveWithAutoReduction(Data expectedFirst) {
    BeginTest("FirstNRemoveWithAutoReduction");
    if (ThisContainer().Size().ToLong() == 0L) {
      assertThrows(IndexOutOfBoundsException.class,
      () -> ThisContainer().FirstNRemove(),
      "FirstNRemove should throw exception when removing from empty container");
      EndTest();
      return;
    }
    long initialSize = ThisContainer().Size().ToLong();
    Data removed = ThisContainer().FirstNRemove();
    assertEquals(expectedFirst, removed,
    "FirstNRemove should return the removed first element");
    assertEquals(initialSize - 1, ThisContainer().Size().ToLong(),
    "FirstNRemove should automatically reduce size");
    EndTest();
  }

  default void TestLastNRemoveWithAutoReduction(Data expectedLast) {
    BeginTest("LastNRemoveWithAutoReduction");
    if (ThisContainer().Size().ToLong() == 0L) {
      assertThrows(IndexOutOfBoundsException.class,
      () -> ThisContainer().LastNRemove(),
      "LastNRemove should throw exception when removing from empty container");
      EndTest();
      return;
    }
    long initialSize = ThisContainer().Size().ToLong();
    Data removed = ThisContainer().LastNRemove();
    assertEquals(expectedLast, removed,
    "LastNRemove should return the removed last element");
    assertEquals(initialSize - 1, ThisContainer().Size().ToLong(),
    "LastNRemove should automatically reduce size");
    EndTest();
  }

  @Override
  default void TestShiftLeft(Natural position, Natural number) {
    BeginTest("ShiftLeft");
    if (position == null || number == null) {
      assertThrows(NullPointerException.class,
      () -> ThisContainer().ShiftLeft(position, number),
      "ShiftLeft should throw exception for null position");
      EndTest();
      return;
    }
    long initialSize = ThisContainer().Size().ToLong();
    if (position.ToLong() >= initialSize) {
      assertThrows(IndexOutOfBoundsException.class, () -> ThisContainer().ShiftLeft(position, number),
      "ShiftLeft should throw exception when sequence is empty");
    } else {
      ThisContainer().ShiftLeft(position, number);
      assertEquals(initialSize - number.ToLong(), ThisContainer().Size().ToLong(),
      "ShiftLeft should automatically call Reduce and Size should decrease by " + number);
    }
    EndTest();
  }

  @Override
  default void TestShiftLeft(Natural position) {
    BeginTest("ShiftLeft");
    if (position == null) {
      assertThrows(NullPointerException.class,
      () -> ThisContainer().ShiftLeft(position),
      "ShiftLeft should throw exception for null position");
      EndTest();
      return;
    }
    long initialSize = ThisContainer().Size().ToLong();
    if (position.ToLong() >= initialSize) {
      assertThrows(IndexOutOfBoundsException.class, () -> ThisContainer().ShiftLeft(position),
      "ShiftLeft should throw exception when sequence is empty");
    } else {
      ThisContainer().ShiftLeft(position);
      assertEquals(initialSize - 1, ThisContainer().Size().ToLong(),
      "ShiftLeft should automatically call Reduce and Size should decrease by 1");
    }
    EndTest();
  }

  @Override
  default void TestShiftFirstLeft() {
    BeginTest("ShiftFirstLeft");
    long initialSize = ThisContainer().Size().ToLong();
    if (initialSize == 0) {
      assertThrows(IndexOutOfBoundsException.class, () -> ThisContainer().ShiftFirstLeft(),
      "ShiftFirstLeft should throw exception when sequence is empty");
    } else {
      ThisContainer().ShiftFirstLeft();
      assertEquals(initialSize - 1, ThisContainer().Size().ToLong(),
      "ShiftFirstLeft should automatically call Reduce and Size should decrease by 1");
    }
    EndTest();
  }

  @Override
  default void TestShiftLastLeft() {
    BeginTest("ShiftLastLeft");
    long initialSize = ThisContainer().Size().ToLong();
    if (initialSize == 0) {
      assertThrows(IndexOutOfBoundsException.class, () -> ThisContainer().ShiftLastLeft(),
      "ShiftLastLeft should throw exception when sequence is empty");
    } else {
      ThisContainer().ShiftLastLeft();
      assertEquals(initialSize - 1, ThisContainer().Size().ToLong(),
      "ShiftLastLeft should automatically call Reduce and Size should decrease by 1");
    }
    EndTest();
  }

  @Override
  default void TestShiftRight(Natural position, Natural number) {
    BeginTest("ShiftRight");
    long initialSize = ThisContainer().Size().ToLong();
    ThisContainer().ShiftRight(position, number);
    assertEquals(initialSize + number.ToLong(), ThisContainer().Size().ToLong(),
    "ShiftRight should automatically call Expand and Size should increase by " + number);
    for(long num = 0; num < number.ToLong(); num++, position = position.Increment()) {
      assertNull(ThisContainer().GetAt(position), "Position " + position + " should be null");
    }
    EndTest();
  }

  @Override
  default void TestShiftRight(Natural position) {
    BeginTest("ShiftRight");
    long initialSize = ThisContainer().Size().ToLong();
    ThisContainer().ShiftRight(position);
    assertEquals(initialSize + 1, ThisContainer().Size().ToLong(),
    "ShiftRight should automatically call Expand and Size should increase by 1");
    assertNull(ThisContainer().GetAt(position), "Position " + position + " should be null");
    EndTest();
  }

  @Override
  default void TestShiftFirstRight() {
    BeginTest("ShiftFirstRight");
    long initialSize = ThisContainer().Size().ToLong();
    ThisContainer().ShiftFirstRight();
    assertEquals(initialSize + 1, ThisContainer().Size().ToLong(),
    "ShiftFirstRight should automatically call Expand and Size should increase by 1");
    assertNull(ThisContainer().GetFirst(), "First position should be null");
    EndTest();
  }

  @Override
  default void TestShiftLastRight() {
    BeginTest("ShiftLastRight");
    Data dat = ThisContainer().GetLast();
    long initialSize = ThisContainer().Size().ToLong();
    ThisContainer().ShiftLastRight();
    assertEquals(initialSize + 1, ThisContainer().Size().ToLong(),
    "ShiftLastRight should automatically call Expand and Size should increase by 1");
    assertEquals(dat, ThisContainer().GetLast(), "Last position should be preserved");
    EndTest();
  }

}
