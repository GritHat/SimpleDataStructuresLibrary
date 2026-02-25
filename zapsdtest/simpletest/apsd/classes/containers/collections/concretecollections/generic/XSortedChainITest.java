package zapsdtest.simpletest.apsd.classes.containers.collections.concretecollections.generic;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.*;

import apsd.classes.containers.collections.abstractcollections.WOrderedSet;
import apsd.classes.containers.collections.concretecollections.LLList;
import apsd.classes.containers.collections.concretecollections.LLSortedChain;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.collections.Chain;
import apsd.interfaces.containers.collections.SortedChain;

abstract public class XSortedChainITest extends XSortedChainTest<Long> {

  @Override
  public void NewNonEmptyContainer() {
    AddTest(11);
    NewEmptyContainer();
    TestInsert(4L, true);
    TestInsert(0L, true);
    TestInsert(5L, true);
    TestInsert(9L, true);
    TestInsert(2L, true);
    TestInsert(1L, true);
    TestInsert(0L, true);
    TestInsert(5L, true);
    TestInsert(1L, true);
    TestSize(9, false);
    TestPrintContent("Initial Sorted Chain");
  }

  @Nested
  @DisplayName("SortedChain Basics")
  public class SortedChainBasics {

    @Test
    @DisplayName("Check starting from an Empty SortedChain")
    public void Empty() {
      AddTest(9);
      NewEmptyContainer();
      TestGetFirst(0L, true);
      TestGetLast(0L, true);
      TestGetAt(Natural.Of(1), 0L, true);
      TestExists(0L, false);
      TestPrintContent("");
      TestFoldBackward((dat, acc) -> acc + dat, 0L, 0L);
      TestRemove(0L, false);
      TestRemoveFirst();
      TestRemoveLast();
    }

    @Test
    @DisplayName("Check starting from a NonEmpty SortedChain")
    public void NonEmpty() {
      AddTest(20);
      NewNonEmptyContainer();
      TestGetFirst(0L, false);
      TestGetLast(9L, false);
      TestGetAt(Natural.Of(4), 2L, false);
      TestExists(4L, true);
      TestPrintContent("");
      TestFoldForward((dat, acc) -> acc + dat, 0L, 27L);
      TestFoldBackward((dat, acc) -> acc + dat, 0L, 27L);
      TestRemoveFirst();
      TestFoldForward((dat, acc) -> acc + dat, 0L, 27L);
      TestRemoveLast();
      TestFoldForward((dat, acc) -> acc + dat, 0L, 18L);
      TestAtNRemove(Natural.Of(5), 5L, false);
      TestFoldBackward((dat, acc) -> acc + dat, 0L, 13L);
      TestSize(6, false);
      TestInsert(3L, true);
      TestPrintContent("");
      TestSuccessor(6L, null);
      TestFoldBackward((dat, acc) -> acc + dat, 0L, 16L);
      TestClear();
      TestSize(0, false);
    }

  }


  @Nested
  @DisplayName("SortedChain Edge Cases")
  public class SortedChainEdgeCases {

    @Test
    @DisplayName("Insert maintains sorted order")
    public void InsertMaintainsOrder() {
      AddTest(13);
      NewEmptyContainer();
      TestInsert(5L, true);
      TestInsert(3L, true);
      TestInsert(7L, true);
      TestInsert(1L, true);
      TestInsert(9L, true);
      TestGetFirst(1L, false);
      TestGetLast(9L, false);
      TestGetAt(Natural.ZERO, 1L, false);
      TestGetAt(Natural.Of(1), 3L, false);
      TestGetAt(Natural.Of(2), 5L, false);
      TestGetAt(Natural.Of(3), 7L, false);
      TestGetAt(Natural.Of(4), 9L, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("All duplicates")
    public void AllDuplicates() {
      AddTest(11);
      NewEmptyContainer();
      TestInsert(5L, true);
      TestInsert(5L, true);
      TestInsert(5L, true);
      TestInsert(5L, true);
      TestSize(4, false);
      TestGetFirst(5L, false);
      TestGetLast(5L, false);
      TestExists(5L, true);
      TestExists(4L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 20L);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Remove all occurrences of duplicate")
    public void RemoveAllDuplicates() {
      AddTest(13);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(5L, true);
      TestInsert(5L, true);
      TestInsert(5L, true);
      TestInsert(9L, true);
      TestSize(5, false);
      TestRemove(5L, true);
      TestExists(5L, true);
      TestRemove(5L, true);
      TestExists(5L, true);
      TestRemove(5L, true);
      TestExists(5L, false);
      TestSize(2, false);
    }

    @Test
    @DisplayName("Single element operations")
    public void SingleElement() {
      AddTest(10);
      NewEmptyContainer();
      TestInsert(42L, true);
      TestSize(1, false);
      TestGetFirst(42L, false);
      TestGetLast(42L, false);
      TestGetAt(Natural.ZERO, 42L, false);
      TestExists(42L, true);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 42L);
      TestRemoveFirst();
      TestSize(0, false);
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Remove first and last alternating")
    public void RemoveAlternating() {
      AddTest(16);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      TestInsert(4L, true);
      TestInsert(5L, true);
      TestRemoveFirst();
      TestGetFirst(2L, false);
      TestRemoveLast();
      TestGetLast(4L, false);
      TestRemoveFirst();
      TestGetFirst(3L, false);
      TestRemoveLast();
      TestGetLast(3L, false);
      TestSize(1, false);
      TestRemoveFirst();
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Negative numbers sorting")
    public void NegativeNumbers() {
      AddTest(11);
      NewEmptyContainer();
      TestInsert(-5L, true);
      TestInsert(0L, true);
      TestInsert(-10L, true);
      TestInsert(5L, true);
      TestInsert(-1L, true);
      TestGetFirst(-10L, false);
      TestGetLast(5L, false);
      TestGetAt(Natural.Of(2), -1L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, -11L);
      TestPrintContent("");
      TestSize(5, false);
    }

    @Test
    @DisplayName("Insert at boundaries")
    public void InsertAtBoundaries() {
      AddTest(11);
      NewEmptyContainer();
      TestInsert(50L, true);
      TestInsert(100L, true);
      TestInsert(0L, true);
      TestGetFirst(0L, false);
      TestGetLast(100L, false);
      TestInsert(-100L, true);
      TestGetFirst(-100L, false);
      TestInsert(200L, true);
      TestGetLast(200L, false);
      TestSize(5, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Large sequence of inserts")
    public void LargeSequence() {
      AddTest(106);
      NewEmptyContainer();
      for (long i = 100L; i >= 1L; i--) {
        TestInsert(i, true);
      }
      TestSize(100, false);
      TestGetFirst(1L, false);
      TestGetLast(100L, false);
      TestGetAt(Natural.Of(49), 50L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 5050L);
      TestPrintContent("");
    }

  }

  @Nested
  @DisplayName("SortedChain Stress Tests")
  public class SortedChainStressTests {

    @Test
    @DisplayName("Interleaved duplicates")
    public void InterleavedDuplicates() {
      AddTest(18);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(3L, true);
      TestInsert(2L, true);
      TestInsert(1L, true);
      TestInsert(3L, true);
      TestInsert(2L, true);
      TestInsert(1L, true);
      TestSize(7, false);
      TestGetFirst(1L, false);
      TestGetLast(3L, false);
      TestGetAt(Natural.Of(0), 1L, false);
      TestGetAt(Natural.Of(1), 1L, false);
      TestGetAt(Natural.Of(2), 1L, false);
      TestGetAt(Natural.Of(3), 2L, false);
      TestGetAt(Natural.Of(4), 2L, false);
      TestGetAt(Natural.Of(5), 3L, false);
      TestGetAt(Natural.Of(6), 3L, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Remove from middle")
    public void RemoveFromMiddle() {
      AddTest(13);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      TestInsert(4L, true);
      TestInsert(5L, true);
      TestAtNRemove(Natural.Of(2), 3L, false);
      TestSize(4, false);
      TestGetAt(Natural.Of(2), 4L, false);
      TestAtNRemove(Natural.Of(1), 2L, false);
      TestSize(3, false);
      TestGetAt(Natural.Of(1), 4L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 10L);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Predecessor and successor find")
    public void PredSuccFind() {
      AddTest(16);
      NewEmptyContainer();
      TestInsert(10L, true);
      TestInsert(20L, true);
      TestInsert(30L, true);
      TestInsert(40L, true);
      TestInsert(50L, true);
      TestPredecessor(25L, 20L);
      TestPredecessor(30L, 20L);
      TestPredecessor(10L, null);
      TestPredecessor(5L, null);
      TestSuccessor(25L, 30L);
      TestSuccessor(30L, 40L);
      TestSuccessor(50L, null);
      TestSuccessor(55L, null);
      TestPredecessor(35L, 30L);
      TestSuccessor(35L, 40L);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Remove until empty then refill")
    public void RemoveUntilEmptyThenRefill() {
      AddTest(18);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      TestRemoveFirst();
      TestRemoveFirst();
      TestRemoveFirst();
      TestIsEmpty(true, false);
      TestInsert(10L, true);
      TestInsert(20L, true);
      TestInsert(15L, true);
      TestSize(3, false);
      TestGetFirst(10L, false);
      TestGetAt(Natural.Of(1), 15L, false);
      TestGetLast(20L, false);
      TestRemoveLast();
      TestRemoveLast();
      TestRemoveLast();
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Extreme values")
    public void ExtremeValues() {
      AddTest(12);
      NewEmptyContainer();
      TestInsert(Long.MAX_VALUE, true);
      TestInsert(Long.MIN_VALUE, true);
      TestInsert(0L, true);
      TestInsert(-1L, true);
      TestInsert(1L, true);
      TestGetFirst(Long.MIN_VALUE, false);
      TestGetLast(Long.MAX_VALUE, false);
      TestGetAt(Natural.Of(2), 0L, false);
      TestExists(Long.MAX_VALUE, true);
      TestExists(Long.MIN_VALUE, true);
      TestSize(5, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Consecutive removes")
    public void ConsecutiveRemoves() {
      AddTest(15);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      TestInsert(4L, true);
      TestInsert(5L, true);
      TestRemove(3L, true);
      TestRemove(1L, true);
      TestRemove(5L, true);
      TestSize(2, false);
      TestGetFirst(2L, false);
      TestGetLast(4L, false);
      TestRemove(2L, true);
      TestRemove(4L, true);
      TestIsEmpty(true, false);
      TestRemove(1L, false);
    }

    @Test
    @DisplayName("Many duplicates at boundaries")
    public void ManyDuplicatesAtBoundaries() {
      AddTest(17);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(1L, true);
      TestInsert(1L, true);
      TestInsert(100L, true);
      TestInsert(100L, true);
      TestInsert(100L, true);
      TestInsert(50L, true);
      TestSize(7, false);
      TestGetFirst(1L, false);
      TestGetLast(100L, false);
      TestRemoveFirst();
      TestGetFirst(1L, false);
      TestRemoveFirst();
      TestGetFirst(1L, false);
      TestRemoveLast();
      TestGetLast(100L, false);
      TestSize(4, false);
    }

  }

  @Nested
  @DisplayName("SortedChain Construction and Equality Tests")
  public class SortedChainConstructionAndEqualityTests {

    @Test
    @DisplayName("Construction from another container")
    public void ConstructionFromAnotherContainer() {
      AddTest(7);
      NewEmptyContainer();
      LLList<Long> list = new LLList<>();
      list.Insert(4L);
      list.Insert(2L);
      list.Insert(3L);
      list.Insert(1L);
      LLSortedChain<Long> conFromList = new LLSortedChain<>(list);
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      TestInsert(4L, true);
      TestIsEqual(conFromList, true);
      TestSize(4, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Construction from empty container")
    public void ConstructionFromEmptyContainer() {
      AddTest(4);
      LLList<Long> emptyList = new LLList<>();
      LLSortedChain<Long> conFromEmpty = new LLSortedChain<>(emptyList);
      NewEmptyContainer();
      TestIsEqual(conFromEmpty, true);
      TestSize(0, false);
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Construction from unsorted list produces sorted chain")
    public void ConstructionFromUnsortedList() {
      AddTest(8);
      LLList<Long> unsortedList = new LLList<>();
      unsortedList.InsertLast(5L);
      unsortedList.InsertLast(1L);
      unsortedList.InsertLast(4L);
      unsortedList.InsertLast(2L);
      unsortedList.InsertLast(3L);
      LLSortedChain<Long> conFromList = new LLSortedChain<>(unsortedList);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      TestInsert(4L, true);
      TestInsert(5L, true);
      TestIsEqual(conFromList, true);
      TestGetFirst(1L, false);
      TestGetLast(5L, false);
    }

    @Test
    @DisplayName("Equality with same elements")
    public void EqualitySameElements() {
      AddTest(6);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      
      LLSortedChain<Long> other = new LLSortedChain<>();
      other.Insert(3L);
      other.Insert(1L);
      other.Insert(2L);
      TestIsEqual(other, true);
      TestSize(3, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Inequality with different sizes")
    public void InequalityDifferentSizes() {
      AddTest(5);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      
      LLSortedChain<Long> smaller = new LLSortedChain<>();
      smaller.Insert(1L);
      smaller.Insert(2L);
      TestIsEqual(smaller, false);
      TestSize(3, false);
    }

    @Test
    @DisplayName("Inequality with different elements")
    public void InequalityDifferentElements() {
      AddTest(5);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      
      LLSortedChain<Long> different = new LLSortedChain<>();
      different.Insert(1L);
      different.Insert(2L);
      different.Insert(4L);
      TestIsEqual(different, false);
      TestSize(3, false);
    }

    @Test
    @DisplayName("Equality of two empty chains")
    public void EqualityEmptyChains() {
      AddTest(3);
      NewEmptyContainer();
      LLSortedChain<Long> other = new LLSortedChain<>();
      TestIsEqual(other, true);
      TestSize(0, false);
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Copy construction preserves elements")
    public void CopyConstructionPreservesElements() {
      AddTest(7);
      LLSortedChain<Long> original = new LLSortedChain<>();
      original.Insert(30L);
      original.Insert(10L);
      original.Insert(20L);
      LLSortedChain<Long> copy = new LLSortedChain<>(original);
      NewEmptyContainer();
      TestInsert(10L, true);
      TestInsert(20L, true);
      TestInsert(30L, true);
      TestIsEqual(original, true);
      TestIsEqual(copy, true);
      TestSize(3, false);
    }

    @Test
    @DisplayName("Equality with duplicates")
    public void EqualityWithDuplicates() {
      AddTest(6);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(1L, true);
      TestInsert(2L, true);
      
      LLSortedChain<Long> other = new LLSortedChain<>();
      other.Insert(2L);
      other.Insert(1L);
      other.Insert(2L);
      other.Insert(1L);
      TestIsEqual(other, true);
      TestSize(4, false);
    }

  }

  @Nested
  @DisplayName("SortedChain Null Testing")
  public class SortedChainNullTesting {

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
    @DisplayName("Remove null element")
    public void RemoveNull() {
      AddTest(4);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestRemove(null, false);
      TestSize(2, false);
    }

    @Test
    @DisplayName("Exists with null")
    public void ExistsNull() {
      AddTest(4);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestExists(null, false);
      TestSize(2, false);
    }

    @Test
    @DisplayName("GetAt with null Natural")
    public void GetAtNullNatural() {
      AddTest(4);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestGetAt(null, 0L, true);
      TestSize(2, false);
    }

    @Test
    @DisplayName("AtNRemove with null Natural")
    public void AtNRemoveNullNatural() {
      AddTest(4);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestAtNRemove(null, 0L, true);
      TestSize(2, false);
    }

    @Test
    @DisplayName("IsEqual with null container")
    public void IsEqualNullContainer() {
      AddTest(3);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestIsEqual(null, false);
    }

    @Test
    @DisplayName("FoldForward with null accumulator")
    public void FoldForwardNullAccumulator() {
      AddTest(4);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      TestFoldForward((dat, acc) -> dat, null, 3L);
    }

    @Test
    @DisplayName("FoldBackward with null accumulator")
    public void FoldBackwardNullAccumulator() {
      AddTest(4);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      TestFoldBackward((dat, acc) -> dat, null, 1L);
    }

    @Test
    @DisplayName("Filter with null predicate")
    public void FilterWithNullPredicate() {
      AddTest(5);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      TestFilter(null);
      TestSize(3, false);
    }

    @Test
    @DisplayName("Operations with null maintain consistency")
    public void NullOperationsConsistency() {
      AddTest(8);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(null, false);
      TestInsert(2L, true);
      TestRemove(null, false);
      TestInsert(3L, true);
      TestExists(null, false);
      TestSize(3, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 6L);
    }

    @Test
    @DisplayName("Empty chain null operations")
    public void EmptyChainNullOperations() {
      AddTest(6);
      NewEmptyContainer();
      TestInsert(null, false);
      TestRemove(null, false);
      TestExists(null, false);
      TestGetAt(null, 0L, true);
      TestIsEmpty(true, false);
      TestSize(0, false);
    }

  }

  @Nested
  @DisplayName("SortedChain Comprehensive Method Combinations")
  public class SortedChainComprehensiveMethodCombinations {

    @Test
    @DisplayName("Insert maintains sorted order with verification")
    public void InsertMaintainsSortedOrderWithVerification() {
      AddTest(22);
      NewEmptyContainer();
      TestInsert(5L, true);
      TestInsert(3L, true);
      TestInsert(7L, true);
      TestInsert(1L, true);
      TestInsert(9L, true);
      TestInsert(2L, true);
      TestInsert(8L, true);
      TestInsert(4L, true);
      TestInsert(6L, true);
      TestSize(9, false);
      TestGetFirst(1L, false);
      TestGetLast(9L, false);
      TestGetAt(Natural.ZERO, 1L, false);
      TestGetAt(Natural.Of(4), 5L, false);
      TestGetAt(Natural.Of(8), 9L, false);
      TestSearch(1L, Natural.ZERO);
      TestSearch(5L, Natural.Of(4));
      TestSearch(9L, Natural.Of(8));
      TestFoldForward((dat, acc) -> acc + dat, 0L, 45L);
      TestFoldBackward((dat, acc) -> acc + dat, 0L, 45L);
      TestForwardIteration(false, false);
      TestBackwardIteration(false, false);
    }

    @Test
    @DisplayName("GetAt, GetFirst, GetLast comprehensive")
    public void GetAtGetFirstGetLastComprehensive() {
      AddTest(18);
      NewEmptyContainer();
      TestInsert(10L, true);
      TestInsert(20L, true);
      TestInsert(30L, true);
      TestInsert(40L, true);
      TestInsert(50L, true);
      TestSize(5, false);
      TestGetFirst(10L, false);
      TestGetLast(50L, false);
      TestGetAt(Natural.ZERO, 10L, false);
      TestGetAt(Natural.Of(1), 20L, false);
      TestGetAt(Natural.Of(2), 30L, false);
      TestGetAt(Natural.Of(3), 40L, false);
      TestGetAt(Natural.Of(4), 50L, false);
      TestExists(30L, true);
      TestExists(25L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 150L);
      TestFIterator();
      TestBIterator();
    }

    @Test
    @DisplayName("RemoveFirst, RemoveLast chain")
    public void RemoveFirstRemoveLastChain() {
      AddTest(20);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      TestInsert(4L, true);
      TestInsert(5L, true);
      TestSize(5, false);
      TestRemoveFirst();
      TestGetFirst(2L, false);
      TestSize(4, false);
      TestRemoveLast();
      TestGetLast(4L, false);
      TestSize(3, false);
      TestRemoveFirst();
      TestRemoveLast();
      TestSize(1, false);
      TestGetFirst(3L, false);
      TestGetLast(3L, false);
      TestRemoveFirst();
      TestIsEmpty(true, false);
      TestSize(0, false);
    }

    @Test
    @DisplayName("FirstNRemove and LastNRemove chain")
    public void FirstNRemoveLastNRemoveChain() {
      AddTest(20);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      TestInsert(4L, true);
      TestInsert(5L, true);
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
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      TestInsert(4L, true);
      TestInsert(5L, true);
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
    @DisplayName("Remove and RemoveOccurrences combined")
    public void RemoveAndRemoveOccurrences() {
      AddTest(18);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      TestInsert(2L, true);
      TestInsert(4L, true);
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
      AddTest(16);
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
      TestFoldForward((dat, acc) -> acc + dat, 0L, 17L);
    }

    @Test
    @DisplayName("Search, SearchPredecessor, SearchSuccessor comprehensive")
    public void SearchPredSuccComprehensive() {
      AddTest(20);
      NewEmptyContainer();
      TestInsert(10L, true);
      TestInsert(20L, true);
      TestInsert(30L, true);
      TestInsert(40L, true);
      TestInsert(50L, true);
      TestSize(5, false);
      TestSearch(10L, Natural.ZERO);
      TestSearch(30L, Natural.Of(2));
      TestSearch(50L, Natural.Of(4));
      TestSearch(25L, null);
      TestSearchPredecessor(25L, Natural.Of(1));
      TestSearchPredecessor(30L, Natural.Of(1));
      TestSearchSuccessor(25L, Natural.Of(2));
      TestSearchSuccessor(30L, Natural.Of(3));
      TestPredecessor(25L, 20L);
      TestPredecessor(10L, null);
      TestSuccessor(25L, 30L);
      TestSuccessor(50L, null);
      TestGetAt(Natural.Of(2), 30L, false);
      TestExists(30L, true);
    }

    @Test
    @DisplayName("Predcessor and Successor iter validation")
    public void SearchPredSuccValidation() {
      AddTest(2);
      NewEmptyContainer();
      TestInsert(10L, true);
      TestSearchSuccessor(10L, null);
    }

    @Test
    @DisplayName("Predecessor and Successor verification")
    public void PredecessorSuccessorVerification() {
      AddTest(18);
      NewEmptyContainer();
      TestInsert(10L, true);
      TestInsert(20L, true);
      TestInsert(30L, true);
      TestInsert(40L, true);
      TestInsert(50L, true);
      TestSize(5, false);
      TestPredecessor(35L, 30L);
      TestPredecessor(50L, 40L);
      TestPredecessor(10L, null);
      TestPredecessor(5L, null);
      TestSuccessor(35L, 40L);
      TestSuccessor(10L, 20L);
      TestSuccessor(50L, null);
      TestSuccessor(55L, null);
      TestPredecessor(25L, 20L);
      TestSuccessor(25L, 30L);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 150L);
      TestSize(5, false);
    }

    @Test
    @DisplayName("Search predecessor and successor edge cases")
    public void SearchPredSuccEdgeCases() {
      AddTest(16);
      NewEmptyContainer();
      TestInsert(10L, true);
      TestInsert(20L, true);
      TestInsert(30L, true);
      TestSuccessorNRemove(10L, 20L);
      TestGetFirst(10L, false);
      TestGetAt(Natural.ONE, 30L, false);
    }

    @Test
    @DisplayName("SubSequence and SubChain verification")
    public void SubSequenceSubChainVerification() {
      AddTest(14);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      TestInsert(4L, true);
      TestInsert(5L, true);
      TestSize(5, false);
      TestSubSequence(Natural.Of(1), Natural.Of(3), false);
      TestSubChain(Natural.Of(0), Natural.Of(2), false);
      TestSubSequence(Natural.ZERO, Natural.Of(4), false);
      TestSubChain(Natural.ZERO, Natural.ZERO, false);
      TestSubSequence(Natural.Of(4), Natural.Of(4), false);
      TestSubSequence(Natural.Of(5), Natural.Of(6), true);
      TestSubChain(Natural.Of(3), Natural.Of(1), true);
      TestSize(5, false);
    }

    @Test
    @DisplayName("Filter with Exists and Size verification")
    public void FilterWithExistsAndSize() {
      AddTest(18);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      TestInsert(4L, true);
      TestInsert(5L, true);
      TestInsert(6L, true);
      TestInsert(7L, true);
      TestInsert(8L, true);
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
    @DisplayName("Union, Intersection, Difference chain")
    public void SetOperationsChain() {
      AddTest(24);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      TestSize(3, false);
      
      LLSortedChain<Long> otherChain = new LLSortedChain<>();
      otherChain.Insert(3L);
      otherChain.Insert(4L);
      otherChain.Insert(5L);
      
      TestUnion(otherChain);
      TestSize(5, false);
      TestExists(1L, true);
      TestExists(4L, true);
      TestExists(5L, true);
      
      LLSortedChain<Long> intersectChain = new LLSortedChain<>();
      intersectChain.Insert(2L);
      intersectChain.Insert(3L);
      intersectChain.Insert(4L);
      
      TestIntersection(intersectChain);
      TestExists(2L, true);
      TestExists(3L, true);
      TestExists(4L, true);
      TestExists(1L, false);
      
      LLSortedChain<Long> diffChain = new LLSortedChain<>();
      diffChain.Insert(3L);
      
      TestDifference(diffChain);
      TestExists(3L, false);
      TestExists(2L, true);
      TestExists(4L, true);
    }

    @Test
    @DisplayName("TraverseForward and TraverseBackward verification")
    public void TraverseForwardBackwardVerification() {
      AddTest(14);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      TestInsert(4L, true);
      TestInsert(5L, true);
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
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      TestInsert(4L, true);
      TestInsert(5L, true);
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
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      TestInsert(4L, true);
      TestInsert(5L, true);
      TestFIterator();
      TestBIterator();
      TestForwardIteration(false, false);
      TestBackwardIteration(false, false);
      TestSize(5, false);
    }

    @Test
    @DisplayName("Clear then rebuild maintaining sorted order")
    public void ClearThenRebuildSortedOrder() {
      AddTest(24);
      NewEmptyContainer();
      TestInsert(5L, true);
      TestInsert(3L, true);
      TestInsert(7L, true);
      TestSize(3, false);
      TestGetFirst(3L, false);
      TestGetLast(7L, false);
      TestClear();
      TestIsEmpty(true, false);
      TestSize(0, false);
      TestInsert(9L, true);
      TestInsert(1L, true);
      TestInsert(5L, true);
      TestSize(3, false);
      TestGetFirst(1L, false);
      TestGetLast(9L, false);
      TestGetAt(Natural.Of(1), 5L, false);
      TestClear();
      TestInsert(2L, true);
      TestInsert(4L, true);
      TestInsert(6L, true);
      TestInsert(8L, true);
      TestSize(4, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 20L);
    }

    @Test
    @DisplayName("IsEqual verification after modifications")
    public void IsEqualAfterModifications() {
      AddTest(16);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      
      LLSortedChain<Long> otherChain = new LLSortedChain<>();
      otherChain.Insert(3L);
      otherChain.Insert(1L);
      otherChain.Insert(2L);
      
      TestIsEqual(otherChain, true);
      TestInsert(4L, true);
      TestIsEqual(otherChain, false);
      otherChain.Insert(4L);
      TestIsEqual(otherChain, true);
      TestRemove(2L, true);
      TestIsEqual(otherChain, false);
      otherChain.Remove(2L);
      TestIsEqual(otherChain, true);
      TestSize(3, false);
      TestClear();
      otherChain.Clear();
      TestIsEqual(otherChain, true);
    }

    @Test
    @DisplayName("Empty chain all operations")
    public void EmptyChainAllOperations() {
      AddTest(18);
      NewEmptyContainer();
      TestIsEmpty(true, false);
      TestSize(0, false);
      TestGetFirst(0L, true);
      TestGetLast(0L, true);
      TestGetAt(Natural.ZERO, 0L, true);
      TestExists(1L, false);
      TestRemove(1L, false);
      TestFIterator();
      TestBIterator();
      TestForwardIteration(false, true);
      TestBackwardIteration(false, true);
      TestPredecessor(1L, null);
      TestSuccessor(1L, null);
      TestSubSequence(Natural.ZERO, Natural.ZERO, true);
      TestFilter(dat -> true);
      TestIsEmpty(true, false);
      TestSize(0, false);
    }

    @Test
    @DisplayName("Single element all operations")
    public void SingleElementAllOperations() {
      AddTest(22);
      NewEmptyContainer();
      TestInsert(42L, true);
      TestSize(1, false);
      TestIsEmpty(false, false);
      TestGetFirst(42L, false);
      TestGetLast(42L, false);
      TestGetAt(Natural.ZERO, 42L, false);
      TestExists(42L, true);
      TestSearch(42L, Natural.ZERO);
      TestPredecessor(42L, null);
      TestSuccessor(42L, null);
      TestPredecessor(50L, 42L);
      TestSuccessor(30L, 42L);
      TestFIterator();
      TestBIterator();
      TestForwardIteration(false, false);
      TestBackwardIteration(false, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 42L);
      TestFoldBackward((dat, acc) -> acc + dat, 0L, 42L);
      TestRemoveFirst();
      TestIsEmpty(true, false);
      TestSize(0, false);
    }

    @Test
    @DisplayName("Duplicate values comprehensive handling")
    public void DuplicateValuesComprehensive() {
      AddTest(22);
      NewEmptyContainer();
      TestInsert(5L, true);
      TestInsert(5L, true);
      TestInsert(5L, true);
      TestInsert(10L, true);
      TestInsert(5L, true);
      TestInsert(10L, true);
      TestSize(6, false);
      TestGetFirst(5L, false);
      TestGetLast(10L, false);
      TestSearch(5L, Natural.ZERO);
      TestSearch(10L, Natural.Of(4));
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
    }

    @Test
    @DisplayName("Extreme values handling")
    public void ExtremeValuesHandling() {
      AddTest(18);
      NewEmptyContainer();
      TestInsert(Long.MIN_VALUE, true);
      TestInsert(0L, true);
      TestInsert(Long.MAX_VALUE, true);
      TestSize(3, false);
      TestGetFirst(Long.MIN_VALUE, false);
      TestGetLast(Long.MAX_VALUE, false);
      TestExists(Long.MIN_VALUE, true);
      TestExists(Long.MAX_VALUE, true);
      TestExists(0L, true);
      TestSearch(Long.MIN_VALUE, Natural.ZERO);
      TestSearch(0L, Natural.Of(1));
      TestPredecessor(0L, Long.MIN_VALUE);
      TestSuccessor(0L, Long.MAX_VALUE);
      TestFIterator();
      TestBIterator();
      TestForwardIteration(false, false);
      TestBackwardIteration(false, false);
    }

    @Test
    @DisplayName("Complex interleaved operations")
    public void ComplexInterleavedOperations() {
      AddTest(30);
      NewEmptyContainer();
      TestInsert(5L, true);
      TestInsert(10L, true);
      TestInsert(7L, true);
      TestSize(3, false);
      TestGetFirst(5L, false);
      TestGetLast(10L, false);
      TestInsert(3L, true);
      TestInsert(12L, true);
      TestSize(5, false);
      TestRemoveFirst();
      TestRemoveLast();
      TestSize(3, false);
      TestGetFirst(5L, false);
      TestGetLast(10L, false);
      TestFirstNRemove(5L);
      TestLastNRemove(10L);
      TestSize(1, false);
      TestGetFirst(7L, false);
      TestInsertIfAbsent(20L, true);
      TestInsertIfAbsent(7L, false);
      TestSize(2, false);
      TestPredecessor(15L, 7L);
      TestSuccessor(10L, 20L);
      TestRemoveOccurrences(7L);
      TestSize(1, false);
      TestExists(7L, false);
      TestFilter(dat -> dat > 10);
      TestSize(1, false);
      TestGetFirst(20L, false);
      TestClear();
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Build, verify, clear, rebuild multiple cycles")
    public void BuildVerifyClearRebuildCycles() {
      AddTest(34);
      NewEmptyContainer();
      
      // Cycle 1
      TestInsert(3L, true);
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestSize(3, false);
      TestGetFirst(1L, false);
      TestGetLast(3L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 6L);
      TestClear();
      TestIsEmpty(true, false);
      
      // Cycle 2
      TestInsert(30L, true);
      TestInsert(10L, true);
      TestInsert(20L, true);
      TestSize(3, false);
      TestGetFirst(10L, false);
      TestGetLast(30L, false);
      TestSearch(20L, Natural.Of(1));
      TestClear();
      
      // Cycle 3
      TestInsert(5L, true);
      TestInsert(15L, true);
      TestInsert(10L, true);
      TestSize(3, false);
      TestGetAt(Natural.Of(1), 10L, false);
      TestPredecessor(12L, 10L);
      TestSuccessor(12L, 15L);
      TestClear();
      
      // Cycle 4
      TestInsert(100L, true);
      TestInsert(200L, true);
      TestInsert(300L, true);
      TestSize(3, false);
      TestForwardIteration(false, false);
      TestBackwardIteration(false, false);
      TestClear();
      TestIsEmpty(true, false);
      TestSize(0, false);
    }

    @Test
    @DisplayName("Large chain mixed operations")
    public void LargeChainMixedOperations() {
      AddTest(46);
      NewEmptyContainer();
      
      // Insert 20 elements in reverse order (should still be sorted)
      for (long i = 20L; i >= 1L; i--) {
        TestInsert(i, true);
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
      
      // Filter odd numbers
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
      
      TestClear();
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Filter removes all elements")
    public void FilterRemovesAll() {
      AddTest(12);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      TestInsert(4L, true);
      TestInsert(5L, true);
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
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      TestInsert(4L, true);
      TestInsert(5L, true);
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

    @Test
    @DisplayName("Sorted order maintained after all operations")
    public void SortedOrderMaintainedAfterAllOps() {
      AddTest(18);
      NewEmptyContainer();
      TestInsert(50L, true);
      TestInsert(10L, true);
      TestInsert(30L, true);
      TestInsert(20L, true);
      TestInsert(40L, true);
      TestSize(5, false);
      TestSortedOrder();
      TestRemove(30L, true);
      TestSortedOrder();
      TestInsert(25L, true);
      TestSortedOrder();
      
      LLSortedChain<Long> other = new LLSortedChain<>();
      other.Insert(15L);
      other.Insert(35L);
      
      TestUnion(other);
      TestSortedOrder();
      TestSize(7, false);
      TestGetFirst(10L, false);
      TestGetLast(50L, false);
      TestFilter(dat -> dat > 20);
      TestSortedOrder();
    }

    @Test
    @DisplayName("Search returns correct positions")
    public void SearchReturnsCorrectPositions() {
      AddTest(18);
      NewEmptyContainer();
      TestInsert(10L, true);
      TestInsert(20L, true);
      TestInsert(20L, true);
      TestInsert(30L, true);
      TestInsert(30L, true);
      TestInsert(30L, true);
      TestInsert(40L, true);
      TestSize(7, false);
      TestSearch(10L, Natural.ZERO);
      TestSearch(40L, Natural.Of(6));
      TestSearchPredecessor(25L, Natural.Of(2));
      TestSearchSuccessor(25L, Natural.Of(3));
      TestSearchPredecessor(35L, Natural.Of(5));
      TestSearchSuccessor(35L, Natural.Of(6));
      TestGetFirst(10L, false);
      TestGetLast(40L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 180L);
    }

  }

  @Nested
  @DisplayName("SortedChain Predecessor/Successor Bug Hunting")
  public class SortedChainPredSuccBugHunting {

    @Test
    @DisplayName("SearchPredecessor of value greater than all elements")
    public void SearchPredecessorGreaterThanAll() {
      AddTest(8);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(3L, true);
      TestInsert(5L, true);
      TestInsert(7L, true);
      TestInsert(9L, true);
      // SearchPredecessor(10): predecessor is 9 (last element)
      // This triggers fiterStart.Next() past end → IllegalStateException
      TestSearchPredecessor(10L, Natural.Of(4));
      TestPredecessor(10L, 9L);
      TestSize(5, false);
    }

    @Test
    @DisplayName("SearchPredecessor of value greater than last in 2-element chain")
    public void SearchPredecessorTwoElements() {
      AddTest(5);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(3L, true);
      // SearchPredecessor(5): pred is 3 (last), fiterStart goes past end
      TestSearchPredecessor(5L, Natural.Of(1));
      TestPredecessor(5L, 3L);
      TestSize(2, false);
    }

    @Test
    @DisplayName("SearchPredecessor of value greater than single element")
    public void SearchPredecessorSingleElement() {
      AddTest(4);
      NewEmptyContainer();
      TestInsert(5L, true);
      // SearchPredecessor(10): pred is 5, fiterStart goes past end of 1-element chain
      TestSearchPredecessor(10L, Natural.ZERO);
      TestPredecessor(10L, 5L);
      TestSize(1, false);
    }

    @Test
    @DisplayName("SearchSuccessor of value less than all elements")
    public void SearchSuccessorLessThanAll() {
      AddTest(8);
      NewEmptyContainer();
      TestInsert(2L, true);
      TestInsert(4L, true);
      TestInsert(6L, true);
      TestInsert(8L, true);
      TestInsert(10L, true);
      TestSearchSuccessor(1L, Natural.ZERO);
      TestSuccessor(1L, 2L);
      TestSize(5, false);
    }

    @Test
    @DisplayName("SearchSuccessor of value greater than all → null")
    public void SearchSuccessorGreaterThanAll() {
      AddTest(6);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(3L, true);
      TestInsert(5L, true);
      // SearchSuccessor(10): no successor, fiterStart goes past end
      TestSearchSuccessor(10L, null);
      TestSuccessor(10L, null);
      TestSize(3, false);
    }

    @Test
    @DisplayName("SearchSuccessor in 2-element chain past end")
    public void SearchSuccessorTwoElementsPastEnd() {
      AddTest(5);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(3L, true);
      TestSearchSuccessor(5L, null);
      TestSuccessor(5L, null);
      TestSize(2, false);
    }

    @Test
    @DisplayName("SearchSuccessor single element greater")
    public void SearchSuccessorSingleElementGreater() {
      AddTest(4);
      NewEmptyContainer();
      TestInsert(5L, true);
      TestSearchSuccessor(10L, null);
      TestSuccessor(10L, null);
      TestSize(1, false);
    }

    @Test
    @DisplayName("SearchPredecessor where value equals last element")
    public void SearchPredecessorEqualsLast() {
      AddTest(6);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(3L, true);
      TestInsert(5L, true);
      // SearchPredecessor(5): pred is 3 (index 1)
      TestSearchPredecessor(5L, Natural.Of(1));
      TestPredecessor(5L, 3L);
      TestSize(3, false);
    }

    @Test
    @DisplayName("SearchSuccessor where value equals first element")
    public void SearchSuccessorEqualsFirst() {
      AddTest(6);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(3L, true);
      TestInsert(5L, true);
      // SearchSuccessor(1): succ is 3 (index 1)
      TestSearchSuccessor(1L, Natural.Of(1));
      TestSuccessor(1L, 3L);
      TestSize(3, false);
    }

    @Test
    @DisplayName("SearchPredecessor with no predecessor")
    public void SearchPredecessorNoPred() {
      AddTest(6);
      NewEmptyContainer();
      TestInsert(5L, true);
      TestInsert(10L, true);
      TestInsert(15L, true);
      TestSearchPredecessor(5L, null);
      TestSearchPredecessor(1L, null);
      TestPredecessor(1L, null);
    }

    @Test
    @DisplayName("Predecessor and Successor with duplicates")
    public void PredSuccWithDuplicates() {
      AddTest(10);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(3L, true);
      TestInsert(3L, true);
      TestInsert(3L, true);
      TestInsert(5L, true);
      TestSearchPredecessor(3L, Natural.ZERO);
      TestPredecessor(3L, 1L);
      TestSearchSuccessor(3L, Natural.Of(4));
      TestSuccessor(3L, 5L);
      TestSize(5, false);
    }
  }

  @Nested
  @DisplayName("SortedChain PredecessorNRemove/SuccessorNRemove Bug Hunting")
  public class SortedChainPredSuccNRemoveBugHunting {

    @Test
    @DisplayName("PredecessorNRemove basic case")
    public void PredecessorNRemoveBasic() {
      AddTest(7);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(3L, true);
      TestInsert(5L, true);
      TestInsert(7L, true);
      TestPredecessorNRemove(5L, 3L);
      TestSize(3, false);
      TestExists(3L, false);
      TestExists(1L, true);
      TestExists(5L, true);
      TestExists(7L, true);
    }

    @Test
    @DisplayName("PredecessorNRemove removes head")
    public void PredecessorNRemoveHead() {
      AddTest(6);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(5L, true);
      TestInsert(10L, true);
      // Predecessor of 5 is 1 (head)
      TestPredecessorNRemove(5L, 1L);
      TestSize(2, false);
      TestGetFirst(5L, false);
      TestExists(1L, false);
    }

    @Test
    @DisplayName("PredecessorNRemove where pred is last element")
    public void PredecessorNRemoveLast() {
      AddTest(6);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(3L, true);
      TestInsert(5L, true);
      // Predecessor of 100 is 5 (last). Removing tail.
      TestPredecessorNRemove(100L, 5L);
      TestSize(2, false);
      TestGetLast(3L, false);
      TestExists(5L, false);
    }

    @Test
    @DisplayName("SuccessorNRemove basic case")
    public void SuccessorNRemoveBasic() {
      AddTest(7);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(3L, true);
      TestInsert(5L, true);
      TestInsert(7L, true);
      // Successor of 3 is 5
      TestSuccessorNRemove(3L, 5L);
      TestSize(3, false);
      TestExists(5L, false);
      TestExists(1L, true);
      TestExists(3L, true);
      TestExists(7L, true);
    }

    @Test
    @DisplayName("SuccessorNRemove removes head (successor of value < min)")
    public void SuccessorNRemoveHead() {
      AddTest(6);
      NewEmptyContainer();
      TestInsert(5L, true);
      TestInsert(10L, true);
      TestInsert(15L, true);
      // Successor of 1 is 5 (head)
      TestSuccessorNRemove(1L, 5L);
      TestSize(2, false);
      TestGetFirst(10L, false);
      TestExists(5L, false);
    }

    @Test
    @DisplayName("SuccessorNRemove where value exists in chain")
    public void SuccessorNRemoveValueExists() {
      AddTest(7);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(3L, true);
      TestInsert(5L, true);
      // Successor of 3 is 5 — this is the PredSuccFind bug case
      TestSuccessorNRemove(3L, 5L);
      TestSize(2, false);
      TestExists(5L, false);
      TestExists(3L, true);
      TestExists(1L, true);
    }

    @Test
    @DisplayName("SuccessorNRemove removes tail")
    public void SuccessorNRemoveTail() {
      AddTest(6);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(3L, true);
      TestInsert(5L, true);
      // Successor of 3 is 5 (tail)
      TestSuccessorNRemove(3L, 5L);
      TestSize(2, false);
      TestGetLast(3L, false);
      TestExists(5L, false);
    }

    @Test
    @DisplayName("SuccessorNRemove no successor → null")
    public void SuccessorNRemoveNoSuccessor() {
      AddTest(4);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(3L, true);
      TestInsert(5L, true);
      TestSuccessorNRemove(5L, null);
    }

    @Test
    @DisplayName("PredecessorNRemove no predecessor → null")
    public void PredecessorNRemoveNoPredecessor() {
      AddTest(4);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(3L, true);
      TestInsert(5L, true);
      TestPredecessorNRemove(1L, null);
    }

    @Test
    @DisplayName("SuccessorNRemove in 2-element chain where value is first")
    public void SuccessorNRemoveTwoElements() {
      AddTest(5);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(3L, true);
      // Successor of 1 is 3
      TestSuccessorNRemove(1L, 3L);
      TestSize(1, false);
      TestGetFirst(1L, false);
    }

    @Test
    @DisplayName("SuccessorNRemove with duplicates")
    public void SuccessorNRemoveWithDuplicates() {
      AddTest(7);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(3L, true);
      TestInsert(3L, true);
      TestInsert(5L, true);
      // Successor of 3 is 5
      TestSuccessorNRemove(3L, 5L);
      TestSize(3, false);
      TestExists(5L, false);
      TestGetLast(3L, false);
    }

    @Test
    @DisplayName("PredecessorNRemove single element")
    public void PredecessorNRemoveSingleElement() {
      AddTest(4);
      NewEmptyContainer();
      TestInsert(5L, true);
      TestPredecessorNRemove(10L, 5L);
      TestSize(0, false);
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("SuccessorNRemove single element")
    public void SuccessorNRemoveSingleElement() {
      AddTest(4);
      NewEmptyContainer();
      TestInsert(5L, true);
      TestSuccessorNRemove(1L, 5L);
      TestSize(0, false);
      TestIsEmpty(true, false);
    }
  }

  @Nested
  @DisplayName("SortedChain RemoveOccurrences Tail Bug Hunting")
  public class SortedChainRemoveOccurrencesBugHunting {

    @Test
    @DisplayName("RemoveOccurrences removes tail elements")
    public void RemoveOccurrencesRemovesTail() {
      AddTest(8);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(3L, true);
      TestInsert(5L, true);
      TestInsert(5L, true);
      TestInsert(5L, true);
      // RemoveOccurrences(5): removes all 5s which are at the tail
      TestRemoveOccurrences(5L);
      TestSize(2, false);
      TestGetFirst(1L, false);
      TestGetLast(3L, false);
      TestExists(5L, false);
    }

    @Test
    @DisplayName("RemoveOccurrences removes middle duplicates then verify tail")
    public void RemoveOccurrencesMiddleThenTail() {
      AddTest(8);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(3L, true);
      TestInsert(3L, true);
      TestInsert(3L, true);
      TestInsert(5L, true);
      TestRemoveOccurrences(3L);
      TestSize(2, false);
      TestGetFirst(1L, false);
      TestGetLast(5L, false);
    }

    @Test
    @DisplayName("RemoveOccurrences removes all head duplicates")
    public void RemoveOccurrencesAllHeads() {
      AddTest(6);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(1L, true);
      TestInsert(1L, true);
      TestInsert(5L, true);
      TestRemoveOccurrences(1L);
      TestSize(1, false);
      TestGetFirst(5L, false);
    }

    @Test
    @DisplayName("RemoveOccurrences removes all elements")
    public void RemoveOccurrencesRemovesAll() {
      AddTest(4);
      NewEmptyContainer();
      TestInsert(3L, true);
      TestInsert(3L, true);
      TestInsert(3L, true);
      TestRemoveOccurrences(3L);
      TestSize(0, false);
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("RemoveOccurrences at tail then insert and verify tail works")
    public void RemoveOccurrencesTailThenInsert() {
      AddTest(10);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(3L, true);
      TestInsert(5L, true);
      TestInsert(5L, true);
      TestRemoveOccurrences(5L);
      TestSize(2, false);
      TestGetLast(3L, false);
      // Now insert something larger — should become new tail
      TestInsert(10L, true);
      TestSize(3, false);
      TestGetLast(10L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 14L);
    }

    @Test
    @DisplayName("RemoveOccurrences single occurrence at tail")
    public void RemoveOccurrencesSingleTail() {
      AddTest(6);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(3L, true);
      TestInsert(5L, true);
      TestRemoveOccurrences(5L);
      TestSize(2, false);
      TestGetLast(3L, false);
    }

    @Test
    @DisplayName("Operations after RemoveOccurrences verify chain integrity")
    public void OperationsAfterRemoveOccurrences() {
      AddTest(14);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      TestInsert(3L, true);
      TestInsert(4L, true);
      TestRemoveOccurrences(3L);
      TestSize(3, false);
      TestGetFirst(1L, false);
      TestGetLast(4L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 7L);
      TestFoldBackward((dat, acc) -> acc + dat, 0L, 7L);
      TestForwardIteration(false, false);
      TestBackwardIteration(false, false);
    }

    private static String dump(LLSortedChain<Long> c) {
      StringBuilder sb = new StringBuilder();
      var it = c.FIterator();
      while (it.IsValid()) {
        sb.append(it.DataNNext()).append(",");
      }
      return sb.toString();
    }

    @Test
    void predecessorNRemove_singleElement_removesOnlyElement_whenDatGreater() {
      var c = new LLSortedChain<Long>();
      c.Insert(5L);

      assertEquals(5L, c.PredecessorNRemove(10L));
      assertEquals(0L, c.Size().ToLong());
      assertTrue(c.IsEmpty());
    }

    @Test
    void successorNRemove_singleElement_removesOnlyElement_whenDatSmaller() {
      var c = new LLSortedChain<Long>();
      c.Insert(5L);

      assertEquals(5L, c.SuccessorNRemove(1L));
      assertEquals(0L, c.Size().ToLong());
      assertTrue(c.IsEmpty());
    }

    @Test
    void predecessorNRemove_noPredecessor_returnsNull_noMutation() {
      var c = new LLSortedChain<Long>();
      c.Insert(1L);
      c.Insert(3L);
      c.Insert(5L);

      assertNull(c.PredecessorNRemove(1L));
      assertEquals(3L, c.Size().ToLong());
      assertEquals("1,3,5,", dump(c));
    }

    @Test
    void successorNRemove_noSuccessor_returnsNull_noMutation() {
      var c = new LLSortedChain<Long>();
      c.Insert(1L);
      c.Insert(3L);
      c.Insert(5L);

      assertNull(c.SuccessorNRemove(5L));
      assertEquals(3L, c.Size().ToLong());
      assertEquals("1,3,5,", dump(c));
    }

    @Test
    void predecessorNRemove_withDuplicates_removesOneRightmostStrictlyLess() {
      var c = new LLSortedChain<Long>();
      c.Insert(1L);
      c.Insert(3L);
      c.Insert(3L);
      c.Insert(5L);

      // predecessor(4) = 3 (index 2 in [1,3,3,5])
      assertEquals(3L, c.PredecessorNRemove(4L));
      assertEquals(3L, c.Size().ToLong());
      // One 3 must remain
      assertEquals("1,3,5,", dump(c));
    }

    @Test
    void successorNRemove_withDuplicates_valueExists_usesStrictSuccessor() {
      var c = new LLSortedChain<Long>();
      c.Insert(1L);
      c.Insert(3L);
      c.Insert(3L);
      c.Insert(5L);

      // successor(3) must be 5 (strictly greater)
      assertEquals(5L, c.SuccessorNRemove(3L));
      assertEquals(3L, c.Size().ToLong());
      assertEquals("1,3,3,", dump(c));
    }

    @Test
    void predecessorNRemove_removesTail_thenInsert_keepsTailValid() {
      var c = new LLSortedChain<Long>();
      c.Insert(1L);
      c.Insert(2L);
      c.Insert(3L);

      // predecessor(10) = tail(3), removes tail
      assertEquals(3L, c.PredecessorNRemove(10L));
      assertEquals("1,2,", dump(c));

      // Tail pointer must still be valid for new insertion
      c.Insert(4L);
      assertEquals("1,2,4,", dump(c));
      assertEquals(4L, c.GetLast());
    }

    @Test
    void successorNRemove_removesHead_thenInsert_keepsHeadValid() {
      var c = new LLSortedChain<Long>();
      c.Insert(2L);
      c.Insert(3L);
      c.Insert(4L);

      // successor(1) = head(2), removes head
      assertEquals(2L, c.SuccessorNRemove(1L));
      assertEquals("3,4,", dump(c));

      c.Insert(1L);
      assertEquals("1,3,4,", dump(c));
      assertEquals(1L, c.GetFirst());
    }

    @Test
    void predPredFind_consistency() {
      var c = new LLSortedChain<Long>();
      c.Insert(1L);
      c.Insert(3L);
      c.Insert(5L);
      c.Insert(7L);

      // predecessor(6)=5, predecessor-of-predecessor = 3
      var n = c.PredPredFind(6L);
      assertNotNull(n);
      assertEquals(3L, n.Get());

      // predecessor(2)=1, predecessor-of-predecessor does not exist
      assertNull(c.PredPredFind(2L));
    }

    @Test
    void predSuccFind_consistency() {
      var c = new LLSortedChain<Long>();
      c.Insert(1L);
      c.Insert(3L);
      c.Insert(5L);
      c.Insert(7L);

      // successor(4)=5, predecessor-of-successor = 3
      var n = c.PredSuccFind(4L);
      assertNotNull(n);
      assertEquals(3L, n.Get());

      // successor(0)=1 (head), predecessor-of-successor does not exist
      assertNull(c.PredSuccFind(0L));

      // no successor
      assertNull(c.PredSuccFind(10L));
    }

    @Test
    void searchAndPredSuccRemainConsistentAfterPredSuccRemovals() {
      var c = new LLSortedChain<Long>();
      c.Insert(1L);
      c.Insert(3L);
      c.Insert(5L);
      c.Insert(7L);
      c.Insert(9L);

      assertEquals(5L, c.PredecessorNRemove(6L)); // removes 5
      assertEquals(7L, c.SuccessorNRemove(6L));   // removes 7
      assertEquals("1,3,9,", dump(c));

      assertEquals(Natural.Of(1), c.SearchPredecessor(9L)); // pred of 9 is 3 at idx 1
      assertNull(c.SearchSuccessor(9L));
      assertEquals(3L, c.Predecessor(4L));
      assertEquals(9L, c.Successor(4L));
    }

    @Test
    void predSuccAndRemovals_matchRawChain() {
      var ch = new LLSortedChain<Long>();
      var ws = new WOrderedSet<Long>(new LLSortedChain<>());

      long[] vals = {4,0,4,3,1,3,2,0,5,6,7};
      for (long v : vals) { ch.InsertIfAbsent(v); ws.Insert(v); }

      assertEquals(ch.Min(), ws.Min());
      assertEquals(ch.Max(), ws.Max());
      assertEquals(ch.Predecessor(4L), ws.Predecessor(4L));
      assertEquals(ch.Successor(4L), ws.Successor(4L));
      assertEquals(ch.PredecessorNRemove(7L), ws.PredecessorNRemove(7L));
      assertEquals(ch.SuccessorNRemove(0L), ws.SuccessorNRemove(0L));

      assertEquals(ch.Size().ToLong(), ws.Size().ToLong());
      assertEquals(ch.Min(), ws.Min());
      assertEquals(ch.Max(), ws.Max());
    }

    @Test
    void constructorPaths_produceSameContent() {
      var base = new LLSortedChain<Long>();
      for (long v : new long[]{5,3,1,4,2,2,3}) base.Insert(v);

      var a = new WOrderedSet<Long>(base);               // SortedChain ctor
      var b = new WOrderedSet<Long>(new LLSortedChain<>(), base); // (SortedChain, con) ctor
      var c = new WOrderedSet<Long>(new LLSortedChain<>(), base); // repeat to catch nondeterminism

      assertTrue(a.IsEqual(b));
      assertTrue(b.IsEqual(c));
      assertEquals(5L, a.Size().ToLong());
      assertEquals(Long.valueOf(1L), a.Min());
      assertEquals(Long.valueOf(5L), a.Max());
    }

    @Test
  void ctor_sortedChain_only() {
    SortedChain<Long> src = new LLSortedChain<>();
    src.Insert(4L); src.Insert(1L); src.Insert(3L); src.Insert(1L);
    WOrderedSet<Long> s = new WOrderedSet<>(src);

    assertEquals(3L, s.Size().ToLong());
    assertEquals(1L, s.Min());
    assertEquals(4L, s.Max());
    assertTrue(s.Exists(3L));
  }

  @Test
  void ctor_sortedChain_plus_container_unionAndDedup() {
    SortedChain<Long> base = new LLSortedChain<>();
    base.Insert(1L); base.Insert(3L);

    LLList<Long> extra = new LLList<>();
    extra.Insert(3L); extra.Insert(4L); extra.Insert(2L); extra.Insert(2L);

    WOrderedSet<Long> s = new WOrderedSet<>(base, extra);

    assertEquals(4L, s.Size().ToLong());
    assertEquals(1L, s.Min());
    assertEquals(4L, s.Max());
    assertTrue(s.Exists(2L));
  }

  @Test
  void ctor_chain_plus_container_withEmptyChain() {
    Chain<Long> chn = new LLList<>();
    LLList<Long> con = new LLList<>();
    con.Insert(4L); con.Insert(2L); con.Insert(4L); con.Insert(1L);

    WOrderedSet<Long> s = new WOrderedSet<>(chn, con);

    assertEquals(3L, s.Size().ToLong());
    assertEquals(1L, s.Min());
    assertEquals(4L, s.Max());
  }

  @Test
  void ctor_chain_plus_container_withNonEmptyChain() {
    Chain<Long> chn = new LLList<>();
    chn.Insert(10L); chn.Insert(8L); chn.Insert(10L);

    LLList<Long> con = new LLList<>();
    con.Insert(9L); con.Insert(7L);

    WOrderedSet<Long> s = new WOrderedSet<>(chn, con);

    assertEquals(4L, s.Size().ToLong());
    assertEquals(7L, s.Min());
    assertEquals(10L, s.Max());
    assertTrue(s.Exists(8L));
    assertTrue(s.Exists(9L));
  }

  @Test
  void ctor_inputs_are_not_aliased() {
    SortedChain<Long> src = new LLSortedChain<>();
    src.Insert(1L); src.Insert(2L); src.Insert(3L);

    WOrderedSet<Long> s = new WOrderedSet<>(src);
    src.Insert(4L);
    src.Remove(1L);

    assertEquals(3L, s.Size().ToLong());
    assertTrue(s.Exists(1L));
    assertFalse(s.Exists(4L));
  }

  @Test
  void null_paths_do_not_break() {
    WOrderedSet<Long> a = new WOrderedSet<>((SortedChain<Long>) null);
    WOrderedSet<Long> b = new WOrderedSet<>((SortedChain<Long>) null, new LLList<Long>());
    WOrderedSet<Long> c = new WOrderedSet<>((Chain<Long>) null, new LLList<Long>());

    assertNotNull(a);
    assertNotNull(b);
    assertNotNull(c);
    assertEquals(0L, a.Size().ToLong());
    assertEquals(0L, b.Size().ToLong());
    assertEquals(0L, c.Size().ToLong());
  }

  @Test
  void strict_pred_succ_semantics() {
    WOrderedSet<Long> s = new WOrderedSet<Long>(new LLSortedChain<>());
    s.Insert(1L); s.Insert(3L); s.Insert(5L);

    assertNull(s.Predecessor(1L));
    assertEquals(1L, s.Predecessor(2L));
    assertEquals(5L, s.PredecessorNRemove(6L)); // remove max predecessor
    assertFalse(s.Exists(5L));

    assertEquals(3L, s.Successor(2L));
    assertNull(s.Successor(3L)); // after removing 5, successor(3)=null
  }

  @Test
  void removePredecessor_removeSuccessor_sideEffects() {
    WOrderedSet<Long> s = new WOrderedSet<Long>(new LLSortedChain<>());
    s.Insert(2L); s.Insert(4L); s.Insert(6L);

    s.RemovePredecessor(5L); // removes 4
    assertFalse(s.Exists(4L));
    assertEquals(2L, s.Size().ToLong());

    s.RemoveSuccessor(2L); // strict successor -> 6
    assertFalse(s.Exists(6L));
    assertEquals(1L, s.Size().ToLong());
    assertEquals(2L, s.Min());
    assertEquals(2L, s.Max());
  }
  }

}

