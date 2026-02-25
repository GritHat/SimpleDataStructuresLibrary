package zapsdtest.simpletest.apsd.classes.containers.collections.concretecollections.generic;

import org.junit.jupiter.api.*;

import apsd.classes.containers.collections.concretecollections.LLList;
import apsd.classes.containers.collections.concretecollections.LLSortedChain;
import apsd.classes.utilities.Natural;

abstract public class XListITest extends XListTest<Long> {

  @Override
  public void NewNonEmptyContainer() {
    AddTest(11);
    NewEmptyContainer();
    TestInsert(0L, true);
    TestInsertLast(4L);
    TestInsertFirst(5L);
    TestInsertLast(9L);
    TestInsertFirst(2L);
    TestInsertFirst(1L);
    TestInsert(0L, true);
    TestInsert(5L, true);
    TestInsertLast(1L);
    TestSize(9, false);
    TestPrintContent("Initial List");
  }

  @Nested
  @DisplayName("List Basics")
  public class ListBasics {

    @Test
    @DisplayName("Check starting from an Empty List")
    public void Empty() {
      AddTest(12);
      NewEmptyContainer();
      TestGetFirst(0L, true);
      TestGetLast(0L, true);
      TestSetFirst(0L, true);
      TestSetLast(0L, true);
      TestGetAt(Natural.Of(1), 0L, true);
      TestSetAt(0L, Natural.Of(2), true);
      TestExists(0L, false);
      TestPrintContent("");
      TestFoldBackward((dat, acc) -> acc + dat, 0L, 0L);
      TestRemove(0L, false);
      TestRemoveFirst();
      TestRemoveLast();
    }

    @Test
    @DisplayName("Check starting from a NonEmpty List")
    public void NonEmpty() {
      AddTest(25);
      NewNonEmptyContainer();
      TestGetFirst(5L, false);
      TestGetLast(1L, false);
      TestSetFirst(2L, false);
      TestSetLast(6L, false);
      TestGetAt(Natural.Of(4), 5L, false);
      TestSetAt(3L, Natural.Of(4), false);
      TestExists(4L, true);
      TestPrintContent("");
      TestFoldForward((dat, acc) -> acc + dat, 0L, 27L);
      TestFoldBackward((dat, acc) -> acc + dat, 0L, 27L);
      TestRemoveFirst();
      TestFoldForward((dat, acc) -> acc + dat, 0L, 25L);
      TestRemoveLast();
      TestFoldForward((dat, acc) -> acc + dat, 0L, 19L);
      TestAtNRemove(Natural.Of(5), 4L, false);
      TestFoldBackward((dat, acc) -> acc + dat, 0L, 15L);
      TestSize(6, false);
      TestInsertAt(5L, Natural.Of(6), false);
      TestPrintContent("");
      TestFoldBackward((dat, acc) -> acc + dat, 0L, 20L);
      TestInsertAt(5L, Natural.Of(4), false);
      LLList<Long> set = new LLList<>();
      set.Insert(5L);
      set.Insert(9L);
      set.Insert(5L);
      set.Insert(3L);
      set.Insert(1L);
      TestPrintContent("");
      System.out.println("Testing SubSet with condition dat % 2 == 1@@@@@@@@@@");
      TestSubSet(dat -> dat % 2 == 1, set);
      TestPrintContent("");
      TestReverse();
      TestClear();
      TestSize(0, false);
    }

  }

  @Nested
  @DisplayName("List Edge Cases")
  public class ListEdgeCases {

    @Test
    @DisplayName("Single element list operations")
    public void SingleElement() {
      AddTest(16);
      NewEmptyContainer();
      TestInsertFirst(42L);
      TestSize(1, false);
      TestGetFirst(42L, false);
      TestGetLast(42L, false);
      TestGetAt(Natural.ZERO, 42L, false);
      TestExists(42L, true);
      TestExists(0L, false);
      TestSetFirst(100L, false);
      TestGetFirst(100L, false);
      TestGetLast(100L, false);
      TestSetLast(200L, false);
      TestGetFirst(200L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 200L);
      TestRemoveFirst();
      TestIsEmpty(true, false);
      TestSize(0, false);
    }

    @Test
    @DisplayName("Insert at various positions")
    public void InsertAtPositions() {
      AddTest(17);
      NewEmptyContainer();
      TestInsertFirst(3L);
      TestInsertFirst(1L);
      TestInsertLast(5L);
      TestInsertAt(2L, Natural.Of(1), false);
      TestInsertAt(4L, Natural.Of(3), false);
      TestPrintContent("");
      TestGetAt(Natural.ZERO, 1L, false);
      TestGetAt(Natural.Of(1), 2L, false);
      TestGetAt(Natural.Of(2), 3L, false);
      TestGetAt(Natural.Of(3), 4L, false);
      TestGetAt(Natural.Of(4), 5L, false);
      TestSize(5, false);
      TestInsertAt(0L, Natural.ZERO, false);
      TestGetFirst(0L, false);
      TestInsertAt(6L, Natural.Of(6), false);
      TestGetLast(6L, false);
      TestSize(7, false);
    }

    @Test
    @DisplayName("Remove at various positions")
    public void RemoveAtPositions() {
      AddTest(15);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(2L);
      TestInsertLast(3L);
      TestInsertLast(4L);
      TestInsertLast(5L);
      TestPrintContent("");
      TestAtNRemove(Natural.Of(2), 3L, false);
      TestPrintContent("");
      TestSize(4, false);
      TestAtNRemove(Natural.ZERO, 1L, false);
      TestGetFirst(2L, false);
      TestAtNRemove(Natural.Of(2), 5L, false);
      TestGetLast(4L, false);
      TestSize(2, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Remove by value")
    public void RemoveByValue() {
      AddTest(13);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(2L);
      TestInsertLast(3L);
      TestInsertLast(2L);
      TestInsertLast(4L);
      TestRemove(2L, true);
      TestSize(4, false);
      TestExists(2L, true);
      TestRemove(2L, true);
      TestExists(2L, false);
      TestRemove(2L, false);
      TestSize(3, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Duplicates handling")
    public void DuplicatesHandling() {
      AddTest(14);
      NewEmptyContainer();
      TestInsert(5L, true);
      TestInsert(5L, true);
      TestInsert(5L, true);
      TestSize(3, false);
      TestGetFirst(5L, false);
      TestGetLast(5L, false);
      TestExists(5L, true);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 15L);
      TestRemove(5L, true);
      TestSize(2, false);
      TestRemove(5L, true);
      TestRemove(5L, true);
      TestRemove(5L, false);
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Alternating insert first and last")
    public void AlternatingInserts() {
      AddTest(12);
      NewEmptyContainer();
      TestInsertFirst(3L);
      TestInsertLast(4L);
      TestInsertFirst(2L);
      TestInsertLast(5L);
      TestInsertFirst(1L);
      TestInsertLast(6L);
      TestPrintContent("");
      TestGetFirst(1L, false);
      TestGetLast(6L, false);
      TestSize(6, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 21L);
      TestGetAt(Natural.Of(3), 4L, false);
    }

    @Test
    @DisplayName("Remove first and last alternating")
    public void AlternatingRemoves() {
      AddTest(16);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(2L);
      TestInsertLast(3L);
      TestInsertLast(4L);
      TestInsertLast(5L);
      TestInsertLast(6L);
      TestRemoveFirst();
      TestGetFirst(2L, false);
      TestRemoveLast();
      TestGetLast(5L, false);
      TestRemoveFirst();
      TestGetFirst(3L, false);
      TestRemoveLast();
      TestGetLast(4L, false);
      TestSize(2, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Set at boundary positions")
    public void SetAtBoundaries() {
      AddTest(12);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(2L);
      TestInsertLast(3L);
      TestInsertLast(4L);
      TestInsertLast(5L);
      TestSetAt(10L, Natural.ZERO, false);
      TestSetAt(50L, Natural.Of(4), false);
      TestGetFirst(10L, false);
      TestGetLast(50L, false);
      TestSetAt(0L, Natural.Of(5), true);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 69L);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Insert at out of bounds")
    public void InsertAtOutOfBounds() {
      AddTest(9);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(2L);
      TestInsertLast(3L);
      TestInsertAt(100L, Natural.Of(10), true);
      TestSize(3, false);
      TestInsertAt(0L, Natural.Of(4), true);
      TestSize(3, false);
      TestGetLast(3L, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Remove at out of bounds")
    public void RemoveAtOutOfBounds() {
      AddTest(9);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(2L);
      TestInsertLast(3L);
      TestAtNRemove(Natural.Of(10), 0L, true);
      TestSize(3, false);
      TestAtNRemove(Natural.Of(3), 0L, true);
      TestSize(3, false);
      TestPrintContent("");
      TestFoldForward((dat, acc) -> acc + dat, 0L, 6L);
    }

    @Test
    @DisplayName("Large list operations")
    public void LargeListOperations() {
      AddTest(82);
      NewEmptyContainer();
      for (long i = 1L; i <= 50L; i++) {
        TestInsertLast(i);
      }
      TestSize(50, false);
      TestGetFirst(1L, false);
      TestGetLast(50L, false);
      TestGetAt(Natural.Of(24), 25L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 1275L);
      for (int i = 0; i < 25; i++) {
        TestRemoveFirst();
      }
      TestSize(25, false);
      TestGetFirst(26L, false);
    }

    @Test
    @DisplayName("Negative numbers in list")
    public void NegativeNumbers() {
      AddTest(13);
      NewEmptyContainer();
      TestInsertLast(-10L);
      TestInsertLast(-5L);
      TestInsertLast(0L);
      TestInsertLast(5L);
      TestInsertLast(10L);
      TestGetFirst(-10L, false);
      TestGetLast(10L, false);
      TestExists(-5L, true);
      TestExists(-100L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 0L);
      TestRemove(-5L, true);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 5L);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Clear and reuse")
    public void ClearAndReuse() {
      AddTest(14);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(2L);
      TestInsertLast(3L);
      TestSize(3, false);
      TestClear();
      TestIsEmpty(true, false);
      TestSize(0, false);
      TestInsertFirst(10L);
      TestInsertLast(20L);
      TestSize(2, false);
      TestGetFirst(10L, false);
      TestGetLast(20L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 30L);
      TestPrintContent("");
    }

  }

  @Nested
  @DisplayName("List Stress Tests")
  public class ListStressTests {

    @Test
    @DisplayName("GetNSet operations")
    public void GetNSetOperations() {
      AddTest(14);
      NewEmptyContainer();
      TestInsertLast(10L);
      TestInsertLast(20L);
      TestInsertLast(30L);
      TestGetNSetFirst(100L, 10L, false);
      TestGetFirst(100L, false);
      TestGetNSetLast(300L, 30L, false);
      TestGetLast(300L, false);
      TestGetNSetAt(200L, 20L, Natural.Of(1), false);
      TestGetAt(Natural.Of(1), 200L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 600L);
      TestPrintContent("");
      TestSize(3, false);
    }

    @Test
    @DisplayName("Insert at middle repeatedly")
    public void InsertAtMiddleRepeatedly() {
      AddTest(16);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(5L);
      TestInsertAt(3L, Natural.Of(1), false);
      TestInsertAt(2L, Natural.Of(1), false);
      TestInsertAt(4L, Natural.Of(3), false);
      TestPrintContent("");
      TestGetAt(Natural.ZERO, 1L, false);
      TestGetAt(Natural.Of(1), 2L, false);
      TestGetAt(Natural.Of(2), 3L, false);
      TestGetAt(Natural.Of(3), 4L, false);
      TestGetAt(Natural.Of(4), 5L, false);
      TestSize(5, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 15L);
      TestFoldBackward((dat, acc) -> acc + dat, 0L, 15L);
    }

    @Test
    @DisplayName("Remove from middle repeatedly")
    public void RemoveFromMiddleRepeatedly() {
      AddTest(18);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(2L);
      TestInsertLast(3L);
      TestInsertLast(4L);
      TestInsertLast(5L);
      TestInsertLast(6L);
      TestInsertLast(7L);
      TestPrintContent("");
      TestAtNRemove(Natural.Of(3), 4L, false);
      TestPrintContent("");
      TestAtNRemove(Natural.Of(3), 5L, false);
      TestPrintContent("");
      TestAtNRemove(Natural.Of(3), 6L, false);
      TestPrintContent("");
      TestSize(4, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 13L);
      TestGetAt(Natural.Of(3), 7L, false);
    }

    @Test
    @DisplayName("Extreme values in list")
    public void ExtremeValues() {
      AddTest(12);
      NewEmptyContainer();
      TestInsertLast(Long.MAX_VALUE);
      TestInsertLast(Long.MIN_VALUE);
      TestInsertLast(0L);
      TestInsertLast(-1L);
      TestInsertLast(1L);
      TestGetFirst(Long.MAX_VALUE, false);
      TestGetAt(Natural.Of(1), Long.MIN_VALUE, false);
      TestExists(Long.MAX_VALUE, true);
      TestExists(Long.MIN_VALUE, true);
      TestSize(5, false);
      TestRemove(Long.MAX_VALUE, true);
      TestGetFirst(Long.MIN_VALUE, false);
    }

    @Test
    @DisplayName("Remove all by value")
    public void RemoveAllByValue() {
      AddTest(16);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(2L);
      TestInsertLast(1L);
      TestInsertLast(3L);
      TestInsertLast(1L);
      TestInsertLast(4L);
      TestInsertLast(1L);
      TestSize(7, false);
      TestRemove(1L, true);
      TestRemove(1L, true);
      TestRemove(1L, true);
      TestRemove(1L, true);
      TestRemove(1L, false);
      TestSize(3, false);
      TestExists(1L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 9L);
    }

    @Test
    @DisplayName("Rebuild list after clear")
    public void RebuildAfterClear() {
      AddTest(18);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(2L);
      TestInsertLast(3L);
      TestClear();
      TestIsEmpty(true, false);
      TestInsertFirst(10L);
      TestInsertLast(30L);
      TestInsertAt(20L, Natural.Of(1), false);
      TestSize(3, false);
      TestGetAt(Natural.ZERO, 10L, false);
      TestGetAt(Natural.Of(1), 20L, false);
      TestGetAt(Natural.Of(2), 30L, false);
      TestClear();
      TestInsertLast(100L);
      TestSize(1, false);
      TestGetFirst(100L, false);
      TestGetLast(100L, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Sequential access verification")
    public void SequentialAccessVerification() {
      AddTest(16);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(2L);
      TestInsertLast(3L);
      TestInsertLast(4L);
      TestInsertLast(5L);
      TestGetAt(Natural.ZERO, 1L, false);
      TestGetAt(Natural.Of(1), 2L, false);
      TestGetAt(Natural.Of(2), 3L, false);
      TestGetAt(Natural.Of(3), 4L, false);
      TestGetAt(Natural.Of(4), 5L, false);
      TestSetAt(10L, Natural.Of(2), false);
      TestGetAt(Natural.Of(2), 10L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 22L);
      TestFoldBackward((dat, acc) -> acc + dat, 0L, 22L);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Insert and remove at same position")
    public void InsertRemoveSamePosition() {
      AddTest(14);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(2L);
      TestInsertLast(3L);
      TestInsertAt(100L, Natural.Of(1), false);
      TestGetAt(Natural.Of(1), 100L, false);
      TestAtNRemove(Natural.Of(1), 100L, false);
      TestGetAt(Natural.Of(1), 2L, false);
      TestInsertAt(200L, Natural.Of(1), false);
      TestAtNRemove(Natural.Of(1), 200L, false);
      TestSize(3, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 6L);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Mixed operations stress test")
    public void MixedOperationsStress() {
      AddTest(24);
      NewEmptyContainer();
      TestInsertFirst(5L);
      TestInsertLast(10L);
      TestInsertFirst(1L);
      TestInsertLast(15L);
      TestInsertAt(7L, Natural.Of(2), false);
      TestPrintContent("");
      TestRemoveFirst();
      TestRemoveLast();
      TestInsertFirst(0L);
      TestInsertLast(20L);
      TestAtNRemove(Natural.Of(2), 7L, false);
      TestPrintContent("");
      TestSize(4, false);
      TestGetFirst(0L, false);
      TestGetLast(20L, false);
      TestSetFirst(100L, false);
      TestSetLast(200L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 315L);
      TestClear();
      TestIsEmpty(true, false);
      TestInsertLast(999L);
      TestGetFirst(999L, false);
    }

    @Test
    @DisplayName("All same elements")
    public void AllSameElements() {
      AddTest(14);
      NewEmptyContainer();
      TestInsertLast(7L);
      TestInsertLast(7L);
      TestInsertLast(7L);
      TestInsertLast(7L);
      TestInsertLast(7L);
      TestSize(5, false);
      TestGetFirst(7L, false);
      TestGetLast(7L, false);
      TestGetAt(Natural.Of(2), 7L, false);
      TestExists(7L, true);
      TestExists(8L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 35L);
      TestRemove(7L, true);
      TestSize(4, false);
    }

  }

  @Nested
  @DisplayName("List Construction and Equality Tests")
  public class ListConstructionAndEqualityTests {

    @Test
    @DisplayName("Construction from another container")
    public void ConstructionFromAnotherContainer() {
      AddTest(8);
      NewEmptyContainer();
      LLSortedChain<Long> chain = new LLSortedChain<>();
      chain.Insert(4L);
      chain.Insert(2L);
      chain.Insert(3L);
      chain.Insert(1L);
      LLList<Long> conFromChain = new LLList<>(chain);
      TestInsert(4L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      TestInsert(1L, true);
      //TestIsEqual(conFromChain, false);
      TestSize(4, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Construction from empty container")
    public void ConstructionFromEmptyContainer() {
      AddTest(4);
      LLSortedChain<Long> emptyChain = new LLSortedChain<>();
      LLList<Long> conFromEmpty = new LLList<>(emptyChain);
      NewEmptyContainer();
      TestIsEqual(conFromEmpty, true);
      TestSize(0, false);
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Construction from list with duplicates")
    public void ConstructionFromListWithDuplicates() {
      AddTest(6);
      LLList<Long> sourceList = new LLList<>();
      sourceList.Insert(1L);
      sourceList.Insert(2L);
      sourceList.Insert(1L);
      sourceList.Insert(3L);
      LLList<Long> conFromList = new LLList<>(sourceList);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(1L, true);
      TestInsert(3L, true);
      TestIsEqual(conFromList, true);
      TestSize(4, false);
    }

    @Test
    @DisplayName("Equality with same elements same order")
    public void EqualitySameOrder() {
      AddTest(6);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(2L);
      TestInsertLast(3L);
      
      LLList<Long> other = new LLList<>();
      other.InsertLast(1L);
      other.InsertLast(2L);
      other.InsertLast(3L);
      TestIsEqual(other, true);
      TestSize(3, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Inequality with same elements different order")
    public void InequalityDifferentOrder() {
      AddTest(6);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(3L);
      TestInsertLast(2L);
      
      LLList<Long> other = new LLList<>();
      other.InsertLast(3L);
      other.InsertLast(2L);
      other.InsertLast(1L);
      //TestIsEqual(other, false);
      TestSize(3, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Inequality with different sizes")
    public void InequalityDifferentSizes() {
      AddTest(5);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(2L);
      TestInsertLast(3L);
      
      LLList<Long> smaller = new LLList<>();
      smaller.InsertLast(1L);
      smaller.InsertLast(2L);
      TestIsEqual(smaller, false);
      TestSize(3, false);
    }

    @Test
    @DisplayName("Inequality with different elements")
    public void InequalityDifferentElements() {
      AddTest(5);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(2L);
      TestInsertLast(3L);
      
      LLList<Long> different = new LLList<>();
      different.InsertLast(1L);
      different.InsertLast(2L);
      different.InsertLast(4L);
      TestIsEqual(different, false);
      TestSize(3, false);
    }

    @Test
    @DisplayName("Equality of two empty lists")
    public void EqualityEmptyLists() {
      AddTest(3);
      NewEmptyContainer();
      LLList<Long> other = new LLList<>();
      TestIsEqual(other, true);
      TestSize(0, false);
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Copy construction preserves elements and order")
    public void CopyConstructionPreservesElements() {
      AddTest(7);
      LLList<Long> original = new LLList<>();
      original.InsertLast(10L);
      original.InsertLast(20L);
      original.InsertLast(30L);
      LLList<Long> copy = new LLList<>(original);
      NewEmptyContainer();
      TestInsertLast(10L);
      TestInsertLast(20L);
      TestInsertLast(30L);
      TestIsEqual(original, true);
      TestIsEqual(copy, true);
      TestSize(3, false);
    }

    @Test
    @DisplayName("Equality with duplicates in same positions")
    public void EqualityWithDuplicates() {
      AddTest(6);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(2L);
      TestInsertLast(1L);
      TestInsertLast(2L);
      
      LLList<Long> other = new LLList<>();
      other.InsertLast(1L);
      other.InsertLast(2L);
      other.InsertLast(1L);
      other.InsertLast(2L);
      TestIsEqual(other, true);
      TestSize(4, false);
    }

  }

  @Nested
  @DisplayName("List Null Testing")
  public class ListNullTesting {

    @Test
    @DisplayName("Insert null element")
    public void InsertNull() {
      AddTest(4);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(null, false);
      TestSize(1, false);
      TestExists(null, false);
    }

    @Test
    @DisplayName("InsertFirst null element")
    public void InsertFirstNull() {
      AddTest(4);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertFirst(null, true);
      TestGetFirst(1L, false);
      TestSize(1, false);
    }

    @Test
    @DisplayName("InsertLast null element")
    public void InsertLastNull() {
      AddTest(4);
      NewEmptyContainer();
      TestInsertFirst(1L);
      TestInsertLast(null, true);
      TestGetLast(1L, false);
      TestSize(1, false);
    }

    @Test
    @DisplayName("InsertAt null element")
    public void InsertAtNull() {
      AddTest(5);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(3L);
      TestInsertAt(null, Natural.Of(1), true);
      TestGetAt(Natural.Of(1), 3L, false);
      TestSize(2L, false);
    }

    @Test
    @DisplayName("Remove null element")
    public void RemoveNull() {
      AddTest(5);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(null, true);
      TestInsertLast(2L);
      TestRemove(null, false);
      TestSize(2, false);
    }

    @Test
    @DisplayName("Exists with null")
    public void ExistsNull() {
      AddTest(5);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(2L);
      TestExists(null, false);
      TestInsertLast(null, true);
      TestExists(null, false);
    }

    @Test
    @DisplayName("SetFirst with null")
    public void SetFirstNull() {
      AddTest(4);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(2L);
      TestSetFirst(null, true);
      TestGetFirst(1L, false);
    }

    @Test
    @DisplayName("SetLast with null")
    public void SetLastNull() {
      AddTest(4);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(2L);
      TestSetLast(null, true);
      TestGetLast(2L, false);
    }

    @Test
    @DisplayName("SetAt with null")
    public void SetAtNull() {
      AddTest(4);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(2L);
      TestInsertLast(3L);
      TestSetAt(null, Natural.Of(1), true);
    }

    @Test
    @DisplayName("GetAt with null Natural")
    public void GetAtNullNatural() {
      AddTest(4);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(2L);
      TestGetAt(null, 0L, true);
      TestSize(2, false);
    }

    @Test
    @DisplayName("SetAt with null Natural")
    public void SetAtNullNatural() {
      AddTest(4);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(2L);
      TestSetAt(100L, null, true);
      TestSize(2, false);
    }

    @Test
    @DisplayName("InsertAt with null Natural")
    public void InsertAtNullNatural() {
      AddTest(4);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(2L);
      TestInsertAt(100L, null, true);
      TestSize(2, false);
    }

    @Test
    @DisplayName("AtNRemove with null Natural")
    public void AtNRemoveNullNatural() {
      AddTest(4);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(2L);
      TestAtNRemove(null, 0L, true);
      TestSize(2, false);
    }

    @Test
    @DisplayName("IsEqual with null container")
    public void IsEqualNullContainer() {
      AddTest(3);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(2L);
      TestIsEqual(null, false);
    }

    @Test
    @DisplayName("FoldForward with null accumulator")
    public void FoldForwardNullAccumulator() {
      AddTest(4);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(2L);
      TestInsertLast(3L);
      TestFoldForward((dat, acc) -> dat, null, 3L);
    }

    @Test
    @DisplayName("FoldBackward with null accumulator")
    public void FoldBackwardNullAccumulator() {
      AddTest(4);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(2L);
      TestInsertLast(3L);
      TestFoldBackward((dat, acc) -> dat, null, 1L);
    }

    @Test
    @DisplayName("Operations with null elements maintain consistency")
    public void NullOperationsConsistency() {
      AddTest(12);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(null, true);
      TestInsertLast(2L);
      TestInsertLast(null, true);
      TestInsertLast(3L);
      TestSize(3, false);
      TestGetAt(Natural.Of(1), 2L, false);
      TestGetAt(Natural.Of(2), 3L, false);
      TestRemove(null, false);
      TestSize(3, false);
      TestExists(null, false);
      TestRemove(null, false);
      TestExists(null, false);
    }

    @Test
    @DisplayName("Empty list null operations")
    public void EmptyListNullOperations() {
      AddTest(6);
      NewEmptyContainer();
      TestRemove(null, false);
      TestExists(null, false);
      TestGetFirst(0L, true);
      TestGetLast(0L, true);
      TestIsEmpty(true, false);
      TestSize(0, false);
    }

  }

  @Nested
  @DisplayName("List Comprehensive Method Combinations")
  public class ListComprehensiveMethodCombinations {

    @Test
    @DisplayName("InsertFirst, InsertLast, GetFirst, GetLast cycle")
    public void InsertFirstLastGetFirstLastCycle() {
      AddTest(20);
      NewEmptyContainer();
      TestInsertFirst(1L);
      TestGetFirst(1L, false);
      TestGetLast(1L, false);
      TestInsertLast(2L);
      TestGetFirst(1L, false);
      TestGetLast(2L, false);
      TestInsertFirst(0L);
      TestGetFirst(0L, false);
      TestInsertLast(3L);
      TestGetLast(3L, false);
      TestSize(4, false);
      TestInsertFirst(-1L);
      TestInsertLast(4L);
      TestSize(6, false);
      TestGetFirst(-1L, false);
      TestGetLast(4L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 9L);
      TestFoldBackward((dat, acc) -> acc + dat, 0L, 9L);
    }

    @Test
    @DisplayName("SetFirst, SetLast with GetFirst, GetLast verification")
    public void SetFirstLastVerification() {
      AddTest(18);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(2L);
      TestInsertLast(3L);
      TestGetFirst(1L, false);
      TestGetLast(3L, false);
      TestSetFirst(10L, false);
      TestGetFirst(10L, false);
      TestSetLast(30L, false);
      TestGetLast(30L, false);
      TestSize(3, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 42L);
      TestSetFirst(100L, false);
      TestSetLast(300L, false);
      TestGetFirst(100L, false);
      TestGetLast(300L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 402L);
      TestExists(100L, true);
      TestExists(300L, true);
    }

    @Test
    @DisplayName("GetNSetFirst, GetNSetLast chain")
    public void GetNSetFirstLastChain() {
      AddTest(16);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(2L);
      TestInsertLast(3L);
      TestInsertLast(4L);
      TestInsertLast(5L);
      TestGetNSetFirst(10L, 1L, false);
      TestGetFirst(10L, false);
      TestGetNSetLast(50L, 5L, false);
      TestGetLast(50L, false);
      TestSize(5, false);
      TestGetNSetFirst(100L, 10L, false);
      TestGetNSetLast(500L, 50L, false);
      TestGetFirst(100L, false);
      TestGetLast(500L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 609L);
      TestExists(100L, true);
    }

    @Test
    @DisplayName("SetAt, GetAt comprehensive test")
    public void SetAtGetAtComprehensive() {
      AddTest(20);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(2L);
      TestInsertLast(3L);
      TestInsertLast(4L);
      TestInsertLast(5L);
      TestGetAt(Natural.ZERO, 1L, false);
      TestGetAt(Natural.Of(2), 3L, false);
      TestGetAt(Natural.Of(4), 5L, false);
      TestSetAt(10L, Natural.ZERO, false);
      TestGetAt(Natural.ZERO, 10L, false);
      TestSetAt(30L, Natural.Of(2), false);
      TestGetAt(Natural.Of(2), 30L, false);
      TestSetAt(50L, Natural.Of(4), false);
      TestGetAt(Natural.Of(4), 50L, false);
      TestSize(5, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 96L);
      TestGetFirst(10L, false);
      TestGetLast(50L, false);
      TestExists(30L, true);
      TestExists(3L, false);
    }

    @Test
    @DisplayName("GetNSetAt comprehensive verification")
    public void GetNSetAtComprehensive() {
      AddTest(18);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(2L);
      TestInsertLast(3L);
      TestInsertLast(4L);
      TestInsertLast(5L);
      TestGetNSetAt(10L, 1L, Natural.ZERO, false);
      TestGetAt(Natural.ZERO, 10L, false);
      TestGetNSetAt(30L, 3L, Natural.Of(2), false);
      TestGetAt(Natural.Of(2), 30L, false);
      TestGetNSetAt(50L, 5L, Natural.Of(4), false);
      TestGetAt(Natural.Of(4), 50L, false);
      TestSize(5, false);
      TestGetNSetAt(100L, 10L, Natural.ZERO, false);
      TestGetNSetAt(500L, 50L, Natural.Of(4), false);
      TestGetFirst(100L, false);
      TestGetLast(500L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 636L);
    }

    @Test
    @DisplayName("InsertAt with Size and GetAt verification")
    public void InsertAtWithVerification() {
      AddTest(22);
      NewEmptyContainer();
      TestInsertAt(3L, Natural.ZERO, false);
      TestGetAt(Natural.ZERO, 3L, false);
      TestSize(1, false);
      TestInsertAt(1L, Natural.ZERO, false);
      TestGetFirst(1L, false);
      TestSize(2, false);
      TestInsertAt(5L, Natural.Of(2), false);
      TestGetLast(5L, false);
      TestSize(3, false);
      TestInsertAt(2L, Natural.Of(1), false);
      TestInsertAt(4L, Natural.Of(3), false);
      TestSize(5, false);
      TestGetAt(Natural.ZERO, 1L, false);
      TestGetAt(Natural.Of(1), 2L, false);
      TestGetAt(Natural.Of(2), 3L, false);
      TestGetAt(Natural.Of(3), 4L, false);
      TestGetAt(Natural.Of(4), 5L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 15L);
      TestForwardIteration(false, false);
      TestBackwardIteration(false, false);
    }

    @Test
    @DisplayName("FirstNRemove and LastNRemove chain")
    public void FirstNRemoveLastNRemoveChain() {
      AddTest(20);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(2L);
      TestInsertLast(3L);
      TestInsertLast(4L);
      TestInsertLast(5L);
      TestSize(5, false);
      TestFirstNRemove(1L);
      TestGetFirst(2L, false);
      TestSize(4, false);
      TestLastNRemove(5L);
      TestGetLast(4L, false);
      TestSize(3, false);
      TestFirstNRemove(2L);
      TestLastNRemove(4L);
      TestSize(1, false);
      TestGetFirst(3L, false);
      TestGetLast(3L, false);
      TestFirstNRemove(3L);
      TestIsEmpty(true, false);
      TestSize(0, false);
    }

    @Test
    @DisplayName("AtNRemove comprehensive test")
    public void AtNRemoveComprehensive() {
      AddTest(18);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(2L);
      TestInsertLast(3L);
      TestInsertLast(4L);
      TestInsertLast(5L);
      TestSize(5, false);
      TestAtNRemove(Natural.Of(2), 3L, false);
      TestSize(4, false);
      TestGetAt(Natural.Of(2), 4L, false);
      TestAtNRemove(Natural.ZERO, 1L, false);
      TestGetFirst(2L, false);
      TestSize(3, false);
      TestAtNRemove(Natural.Of(2), 5L, false);
      TestGetLast(4L, false);
      TestSize(2, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 6L);
      TestExists(2L, true);
      TestExists(4L, true);
    }

    @Test
    @DisplayName("SubSequence, SubList, SubChain verification")
    public void SubSequenceSubListSubChainVerification() {
      AddTest(16);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(2L);
      TestInsertLast(3L);
      TestInsertLast(4L);
      TestInsertLast(5L);
      TestSize(5, false);
      TestSubSequence(Natural.Of(1), Natural.Of(3), false);
      TestSubList(Natural.Of(0), Natural.Of(2), false);
      TestSubChain(Natural.Of(2), Natural.Of(4), false);
      TestSubSequence(Natural.ZERO, Natural.Of(4), false);
      TestSubList(Natural.ZERO, Natural.ZERO, false);
      TestSubChain(Natural.Of(4), Natural.Of(4), false);
      TestSubSequence(Natural.Of(5), Natural.Of(6), true);
      TestSubList(Natural.Of(3), Natural.Of(1), true);
      TestSubChain(Natural.Of(10), Natural.Of(12), true);
      TestSubSequence(null, null, false);
      TestSubList(null, null, false);
      TestSubChain(null, null, false);
      TestSize(5, false);
    }

    @Test
    @DisplayName("Filter with Exists and Size verification")
    public void FilterWithExistsAndSize() {
      AddTest(18);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(2L);
      TestInsertLast(3L);
      TestInsertLast(4L);
      TestInsertLast(5L);
      TestInsertLast(6L);
      TestInsertLast(7L);
      TestInsertLast(8L);
      TestSize(8, false);
      TestFilter(dat -> dat % 2 == 0);
      TestSize(4, false);
      TestExists(2L, true);
      TestExists(4L, true);
      TestExists(6L, true);
      TestExists(8L, true);
      TestExists(1L, false);
      TestExists(3L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 20L);
    }

    @Test
    @DisplayName("Remove and RemoveOccurrences combined")
    public void RemoveAndRemoveOccurrences() {
      AddTest(18);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(2L);
      TestInsertLast(2L);
      TestInsertLast(3L);
      TestInsertLast(2L);
      TestInsertLast(4L);
      TestSize(6, false);
      TestRemove(2L, true);
      TestSize(5, false);
      TestExists(2L, true);
      TestRemoveOccurrences(2L);
      TestExists(2L, false);
      TestSize(3, false);
      TestRemove(2L, false);
      TestExists(1L, true);
      TestExists(3L, true);
      TestExists(4L, true);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 8L);
    }

    @Test
    @DisplayName("InsertIfAbsent with Insert and Exists")
    public void InsertIfAbsentWithInsertExists() {
      AddTest(18);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      TestSize(3, false);
      TestInsertIfAbsent(4L, true);
      TestSize(4, false);
      TestExists(4L, true);
      TestInsertIfAbsent(2L, false);
      TestSize(4, false);
      TestInsertIfAbsent(5L, true);
      TestSize(5, false);
      TestInsert(2L, true);
      TestSize(6, false);
      TestInsertIfAbsent(1L, false);
      TestInsertIfAbsent(6L, true);
      TestSize(7, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 23L);
    }

    @Test
    @DisplayName("Search with GetAt verification")
    public void SearchWithGetAtVerification() {
      AddTest(16);
      NewEmptyContainer();
      TestInsertLast(10L);
      TestInsertLast(20L);
      TestInsertLast(30L);
      TestInsertLast(40L);
      TestInsertLast(50L);
      TestSearch(10L, Natural.ZERO);
      TestSearch(30L, Natural.Of(2));
      TestSearch(50L, Natural.Of(4));
      TestSearch(25L, null);
      TestSearch(0L, null);
      TestGetAt(Natural.ZERO, 10L, false);
      TestGetAt(Natural.Of(2), 30L, false);
      TestGetAt(Natural.Of(4), 50L, false);
      TestSize(5, false);
      TestExists(30L, true);
      TestExists(25L, false);
    }

    @Test
    @DisplayName("Union, Intersection, Difference chain")
    public void SetOperationsChain() {
      AddTest(24);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(2L);
      TestInsertLast(3L);
      TestSize(3, false);
      
      LLList<Long> otherList = new LLList<>();
      otherList.Insert(3L);
      otherList.Insert(4L);
      otherList.Insert(5L);
      
      TestUnion(otherList);
      TestSize(5, false);
      TestExists(1L, true);
      TestExists(4L, true);
      TestExists(5L, true);
      
      LLList<Long> intersectList = new LLList<>();
      intersectList.Insert(2L);
      intersectList.Insert(3L);
      intersectList.Insert(4L);
      
      TestIntersection(intersectList);
      TestExists(2L, true);
      TestExists(3L, true);
      TestExists(4L, true);
      TestExists(1L, false);
      
      LLList<Long> diffList = new LLList<>();
      diffList.Insert(3L);
      
      TestDifference(diffList);
      TestExists(3L, false);
      TestExists(2L, true);
      TestExists(4L, true);
    }

    @Test
    @DisplayName("TraverseForward and TraverseBackward verification")
    public void TraverseForwardBackwardVerification() {
      AddTest(14);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(2L);
      TestInsertLast(3L);
      TestInsertLast(4L);
      TestInsertLast(5L);
      TestSize(5, false);
      TestTraverseForward(dat -> dat > 0, true);
      TestTraverseBackward(dat -> dat > 0, true);
      TestTraverseForward(dat -> dat < 3, true);
      TestTraverseBackward(dat -> dat < 3, true);
      TestTraverseForward(dat -> dat % 2 == 0, true);
      TestTraverseBackward(dat -> dat % 2 == 1, true);
      TestTraverseForward(dat -> dat <= 5, true);
      TestTraverseBackward(dat -> dat >= 1, true);
      TestTraverseForward(dat -> dat >= 5, true);
      TestTraverseBackward(dat -> dat <= 1, true);
      TestTraverseForward(dat -> dat > 5, false);
      TestTraverseBackward(dat -> dat > 5, false);
      TestTraverseForward(dat -> dat < 1, false);
      TestTraverseBackward(dat -> dat < 1, false);
      TestTraverseForward(dat -> dat % 7 == 0, false);
      TestTraverseBackward(dat -> dat % 7 == 0, false);
      TestTraverseForward(dat -> dat <= 0, false);
      TestTraverseBackward(dat -> dat >= 6, false);
    }

    @Test
    @DisplayName("FoldForward and FoldBackward comprehensive")
    public void FoldForwardBackwardComprehensive() {
      AddTest(14);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(2L);
      TestInsertLast(3L);
      TestInsertLast(4L);
      TestInsertLast(5L);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 15L);
      TestFoldBackward((dat, acc) -> acc + dat, 0L, 15L);
      TestFoldForward((dat, acc) -> acc * dat, 1L, 120L);
      TestFoldBackward((dat, acc) -> acc * dat, 1L, 120L);
      TestFoldForward((dat, acc) -> dat > acc ? dat : acc, 0L, 5L);
      TestFoldBackward((dat, acc) -> dat < acc ? dat : acc, 100L, 1L);
      TestSize(5, false);
      TestIsEmpty(false, false);
    }

    @Test
    @DisplayName("FIterator and BIterator usage")
    public void FIteratorBIteratorUsage() {
      AddTest(10);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(2L);
      TestInsertLast(3L);
      TestInsertLast(4L);
      TestInsertLast(5L);
      TestFIterator();
      TestBIterator();
      TestForwardIteration(false, false);
      TestBackwardIteration(false, false);
      TestSize(5, false);
    }

    @Test
    @DisplayName("Clear then rebuild with all insert methods")
    public void ClearThenRebuildAllInserts() {
      AddTest(26);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(2L);
      TestInsertLast(3L);
      TestSize(3, false);
      TestClear();
      TestIsEmpty(true, false);
      TestSize(0, false);
      TestInsertFirst(5L);
      TestInsertLast(10L);
      TestInsertAt(7L, Natural.Of(1), false);
      TestSize(3, false);
      TestGetFirst(5L, false);
      TestGetLast(10L, false);
      TestGetAt(Natural.Of(1), 7L, false);
      TestClear();
      TestInsert(100L, true);
      TestInsertIfAbsent(200L, true);
      TestInsertFirst(50L);
      TestInsertLast(250L);
      TestSize(4, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 600L);
      TestGetFirst(50L, false);
      TestGetLast(250L, false);
      TestExists(100L, true);
      TestExists(200L, true);
    }

    @Test
    @DisplayName("Remove operations exhaustively")
    public void RemoveOperationsExhaustively() {
      AddTest(26);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(2L);
      TestInsertLast(3L);
      TestInsertLast(4L);
      TestInsertLast(5L);
      TestInsertLast(6L);
      TestInsertLast(7L);
      TestInsertLast(8L);
      TestSize(8, false);
      TestRemoveFirst();
      TestGetFirst(2L, false);
      TestRemoveLast();
      TestGetLast(7L, false);
      TestSize(6, false);
      TestRemove(4L, true);
      TestExists(4L, false);
      TestSize(5, false);
      TestAtNRemove(Natural.Of(2), 5L, false);
      TestSize(4, false);
      TestFirstNRemove(2L);
      TestLastNRemove(7L);
      TestSize(2, false);
      TestGetFirst(3L, false);
      TestGetLast(6L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 9L);
    }

    @Test
    @DisplayName("IsEqual verification after modifications")
    public void IsEqualAfterModifications() {
      AddTest(18);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(2L);
      TestInsertLast(3L);
      
      LLList<Long> otherList = new LLList<>();
      otherList.InsertLast(1L);
      otherList.InsertLast(2L);
      otherList.InsertLast(3L);
      
      TestIsEqual(otherList, true);
      TestSetFirst(10L, false);
      TestIsEqual(otherList, false);
      TestSetFirst(1L, false);
      TestIsEqual(otherList, true);
      TestInsertLast(4L);
      TestIsEqual(otherList, false);
      otherList.InsertLast(4L);
      TestIsEqual(otherList, true);
      TestRemoveLast();
      TestIsEqual(otherList, false);
      TestSize(3, false);
    }

    @Test
    @DisplayName("Empty list all operations")
    public void EmptyListAllOperations() {
      AddTest(18);
      NewEmptyContainer();
      TestIsEmpty(true, false);
      TestSize(0, false);
      TestGetFirst(0L, true);
      TestGetLast(0L, true);
      TestSetFirst(1L, true);
      TestSetLast(1L, true);
      TestGetAt(Natural.ZERO, 0L, true);
      TestSetAt(1L, Natural.ZERO, true);
      TestExists(1L, false);
      TestRemove(1L, false);
      TestFIterator();
      TestBIterator();
      TestForwardIteration(false, true);
      TestBackwardIteration(false, true);
      TestSubSequence(Natural.ZERO, Natural.ZERO, true);
      TestFilter(dat -> true);
      TestIsEmpty(true, false);
      TestSize(0, false);
    }

    @Test
    @DisplayName("Single element all operations")
    public void SingleElementAllOperations() {
      AddTest(24);
      NewEmptyContainer();
      TestInsertLast(42L);
      TestSize(1, false);
      TestIsEmpty(false, false);
      TestGetFirst(42L, false);
      TestGetLast(42L, false);
      TestGetAt(Natural.ZERO, 42L, false);
      TestExists(42L, true);
      TestSearch(42L, Natural.ZERO);
      TestFIterator();
      TestBIterator();
      TestForwardIteration(false, false);
      TestBackwardIteration(false, false);
      TestSetFirst(100L, false);
      TestGetFirst(100L, false);
      TestGetLast(100L, false);
      TestSetLast(200L, false);
      TestGetFirst(200L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 200L);
      TestFoldBackward((dat, acc) -> acc + dat, 0L, 200L);
      TestRemoveFirst();
      TestIsEmpty(true, false);
      TestSize(0, false);
    }

    @Test
    @DisplayName("Duplicate values comprehensive handling")
    public void DuplicateValuesComprehensive() {
      AddTest(22);
      NewEmptyContainer();
      TestInsertLast(5L);
      TestInsertLast(5L);
      TestInsertLast(5L);
      TestInsertLast(10L);
      TestInsertLast(5L);
      TestInsertLast(10L);
      TestSize(6, false);
      TestSearch(5L, Natural.ZERO);
      TestSearch(10L, Natural.Of(3));
      TestExists(5L, true);
      TestRemove(5L, true);
      TestSize(5, false);
      TestExists(5L, true);
      TestRemoveOccurrences(5L);
      TestExists(5L, false);
      TestSize(2, false);
      TestGetFirst(10L, false);
      TestGetLast(10L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 20L);
      TestInsertIfAbsent(10L, false);
      TestSize(2, false);
      TestInsertIfAbsent(15L, true);
      TestSize(3, false);
    }

    @Test
    @DisplayName("Extreme values handling")
    public void ExtremeValuesHandling() {
      AddTest(18);
      NewEmptyContainer();
      TestInsertLast(Long.MIN_VALUE);
      TestInsertLast(0L);
      TestInsertLast(Long.MAX_VALUE);
      TestSize(3, false);
      TestGetFirst(Long.MIN_VALUE, false);
      TestGetLast(Long.MAX_VALUE, false);
      TestExists(Long.MIN_VALUE, true);
      TestExists(Long.MAX_VALUE, true);
      TestExists(0L, true);
      TestPrintContent("");
      TestSearch(Long.MIN_VALUE, Natural.ZERO);
      TestSearch(Long.MAX_VALUE, Natural.Of(2));
      TestSetAt(Long.MAX_VALUE - 1, Natural.Of(2), false);
      TestGetLast(Long.MAX_VALUE - 1, false);
      TestFIterator();
      TestBIterator();
      TestForwardIteration(false, false);
      TestBackwardIteration(false, false);
      TestFoldForward((dat, acc) -> acc + 1, 0L, 3L);
    }

    @Test
    @DisplayName("InsertAll then RemoveAll verification")
    public void InsertAllThenOperations() {
      AddTest(20);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(2L);
      
      LLList<Long> toInsert = new LLList<>();
      toInsert.InsertLast(3L);
      toInsert.InsertLast(4L);
      toInsert.InsertLast(5L);
      
      TestInsertAll(toInsert, true);
      TestSize(5, false);
      TestExists(3L, true);
      TestExists(4L, true);
      TestExists(5L, true);
      TestGetFirst(5L, false);
      TestGetLast(2L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 15L);
      
      TestRemove(3L, true);
      TestRemove(4L, true);
      TestRemove(5L, true);
      TestSize(2, false);
      TestExists(3L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 3L);
    }

    @Test
    @DisplayName("InsertSome with partial insertion")
    public void InsertSomePartialInsertion() {
      AddTest(18);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(2L);
      TestInsertLast(3L);
      TestSize(3, false);
      
      LLList<Long> toInsert = new LLList<>();
      toInsert.InsertLast(3L);
      toInsert.InsertLast(4L);
      toInsert.InsertLast(5L);
      
      TestInsertSome(toInsert, true);
      TestExists(4L, true);
      TestExists(5L, true);
      TestGetFirst(5L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 18L);
      TestFilter(dat -> dat <= 3);
      TestSize(4, false);
      TestExists(4L, false);
      TestExists(5L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 9L);
    }

    @Test
    @DisplayName("Complex interleaved operations")
    public void ComplexInterleavedOperations() {
      AddTest(32);
      NewEmptyContainer();
      TestInsertFirst(5L);
      TestInsertLast(10L);
      TestInsertAt(7L, Natural.Of(1), false);
      TestSize(3, false);
      TestGetNSetFirst(3L, 5L, false);
      TestGetNSetLast(12L, 10L, false);
      TestSize(3, false);
      TestInsertFirst(1L);
      TestInsertLast(15L);
      TestSize(5, false);
      TestGetAt(Natural.Of(2), 7L, false);
      TestSetAt(8L, Natural.Of(2), false);
      TestGetAt(Natural.Of(2), 8L, false);
      TestFirstNRemove(1L);
      TestLastNRemove(15L);
      TestSize(3, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 23L);
      TestInsertIfAbsent(20L, true);
      TestInsertIfAbsent(8L, false);
      TestSize(4, false);
      TestSearch(8L, Natural.Of(2));
      TestSearch(20L, Natural.Of(0));
      TestRemoveOccurrences(8L);
      TestSize(3, false);
      TestExists(8L, false);
      TestFilter(dat -> dat > 5);
      TestSize(2, false);
      TestGetFirst(20L, false);
      TestGetLast(12L, false);
      TestClear();
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Build, verify, clear, rebuild multiple cycles")
    public void BuildVerifyClearRebuildCycles() {
      AddTest(36);
      NewEmptyContainer();
      
      // Cycle 1
      TestInsertLast(1L);
      TestInsertLast(2L);
      TestInsertLast(3L);
      TestSize(3, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 6L);
      TestClear();
      TestIsEmpty(true, false);
      
      // Cycle 2
      TestInsertFirst(10L);
      TestInsertFirst(20L);
      TestInsertFirst(30L);
      TestSize(3, false);
      TestGetFirst(30L, false);
      TestGetLast(10L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 60L);
      TestClear();
      
      // Cycle 3
      TestInsertAt(5L, Natural.ZERO, false);
      TestInsertAt(15L, Natural.Of(1), false);
      TestInsertAt(10L, Natural.Of(1), false);
      TestSize(3, false);
      TestGetAt(Natural.Of(1), 10L, false);
      TestFoldBackward((dat, acc) -> acc + dat, 0L, 30L);
      TestClear();
      
      // Cycle 4
      TestInsert(100L, true);
      TestInsert(200L, true);
      TestInsert(300L, true);
      TestSize(3, false);
      TestForwardIteration(false, false);
      TestBackwardIteration(false, false);
      TestFIterator();
      TestBIterator();
      TestClear();
      TestIsEmpty(true, false);
      TestSize(0, false);
    }

    @Test
    @DisplayName("Alternating insert and remove at boundaries")
    public void AlternatingInsertRemoveBoundaries() {
      AddTest(28);
      NewEmptyContainer();
      TestInsertFirst(1L);
      TestInsertLast(2L);
      TestSize(2, false);
      TestRemoveFirst();
      TestGetFirst(2L, false);
      TestInsertFirst(3L);
      TestGetFirst(3L, false);
      TestRemoveLast();
      TestGetLast(3L, false);
      TestInsertLast(4L);
      TestGetLast(4L, false);
      TestSize(2, false);
      TestInsertFirst(0L);
      TestInsertLast(5L);
      TestSize(4, false);
      TestFirstNRemove(0L);
      TestLastNRemove(5L);
      TestSize(2, false);
      TestInsertAt(2L, Natural.Of(1), false);
      TestSize(3, false);
      TestAtNRemove(Natural.Of(1), 2L, false);
      TestSize(2, false);
      TestGetFirst(3L, false);
      TestGetLast(4L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 7L);
      TestClear();
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Large list mixed operations")
    public void LargeListMixedOperations() {
      AddTest(62);
      NewEmptyContainer();
      
      // Insert 20 elements
      for (long i = 1L; i <= 20L; i++) {
        TestInsertLast(i);
      }
      TestSize(20, false);
      TestGetFirst(1L, false);
      TestGetLast(20L, false);
      
      // Remove first 5
      for (int i = 0; i < 5; i++) {
        TestRemoveFirst();
      }
      TestSize(15, false);
      TestGetFirst(6L, false);
      
      // Remove last 5
      for (int i = 0; i < 5; i++) {
        TestRemoveLast();
      }
      TestSize(10, false);
      TestGetLast(15L, false);
      
      // Filter even numbers
      TestFilter(dat -> dat % 2 == 0);
      TestSize(5, false);
      TestExists(6L, true);
      TestExists(8L, true);
      TestExists(10L, true);
      TestExists(12L, true);
      TestExists(14L, true);
      TestExists(7L, false);
      
      // Verify sum
      TestFoldForward((dat, acc) -> acc + dat, 0L, 50L);
      
      // Set all to same value
      TestSetAt(100L, Natural.ZERO, false);
      TestSetAt(100L, Natural.Of(1), false);
      TestSetAt(100L, Natural.Of(2), false);
      TestSetAt(100L, Natural.Of(3), false);
      TestSetAt(100L, Natural.Of(4), false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 500L);
      
      TestClear();
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Filter removes all elements")
    public void FilterRemovesAll() {
      AddTest(12);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(2L);
      TestInsertLast(3L);
      TestInsertLast(4L);
      TestInsertLast(5L);
      TestSize(5, false);
      TestFilter(dat -> false);
      TestIsEmpty(true, false);
      TestSize(0, false);
      TestGetFirst(0L, true);
      TestGetLast(0L, true);
      TestFIterator();
    }

    @Test
    @DisplayName("Filter keeps all elements")
    public void FilterKeepsAll() {
      AddTest(14);
      NewEmptyContainer();
      TestInsertLast(1L);
      TestInsertLast(2L);
      TestInsertLast(3L);
      TestInsertLast(4L);
      TestInsertLast(5L);
      TestSize(5, false);
      TestFilter(dat -> true);
      TestSize(5, false);
      TestGetFirst(1L, false);
      TestGetLast(5L, false);
      TestExists(1L, true);
      TestExists(5L, true);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 15L);
      TestForwardIteration(false, false);
    }

  }

}

