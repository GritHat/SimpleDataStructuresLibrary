package zapsdtest.simpletest.apsd.classes.containers.collections.abstractcollections.generic;

import org.junit.jupiter.api.*;

import apsd.classes.containers.collections.abstractcollections.WOrderedSet;
import apsd.classes.containers.collections.concretecollections.LLList;
import apsd.classes.containers.collections.concretecollections.LLSortedChain;
import apsd.interfaces.containers.collections.List;
import apsd.interfaces.containers.collections.SortedChain;

abstract public class WOrderedSetITest extends WOrderedSetTest<Long> {

  @Override
  public void NewNonEmptyContainer() {
    AddTest(10);
    NewEmptyContainer();
    TestInsert(4L, true);
    TestInsert(0L, true);
    TestInsert(4L, false);
    TestInsert(3L, true);
    TestInsert(1L, true);
    TestInsert(3L, false);
    TestInsert(2L, true);
    TestInsert(0L, false);
    TestSize(5, false);
    TestPrintContent("");
  }

  @Nested
  @DisplayName("WOrderedSet Basics")
  public class WOrderedSetBasics {

    @Test
    @DisplayName("Check starting from an Empty WOrderedSet")
    public void Empty() {
      AddTest(8);
      NewEmptyContainer();
      TestExists(0L, false);
      TestFilter(dat -> false);
      TestUnion(ThisContainer());
      TestIntersection(ThisContainer());
      TestDifference(ThisContainer());
      TestSize(0, false);
      TestMin(null);
      TestMax(null);
    }

    @Test
    @DisplayName("Check starting from a NonEmpty WOrderedSet")
    public void NonEmpty() {
      AddTest(48);
      NewNonEmptyContainer();
      TestMin(0L);
      TestMax(4L);
      TestRemoveMin();
      TestMinNRemove(1L);
      TestInsert(-1L, true);
      TestInsert(1L, true);
      TestMin(-1L);
      TestMaxNRemove(4L);
      TestSize(4, false);
      TestInsert(6L, true);
      TestSize(5, false);
      TestMax(6L);
      TestInsert(7L, true);
      TestSize(6, false);
      TestExists(6L, true);
      TestExists(8L, false);
      TestExists(0L, false);
      TestExists(-1L, true);
      TestExists(2L, true);
      TestRemove(5L, false);
      TestRemove(2L, true);
      TestExists(5L, false);
      TestExists(2L, false);
      TestRemoveMax();
      TestPrintContent("");
      TestPredecessor(4L, 3L);
      TestPredecessor(5L, 3L);
      TestSuccessor(2L, 3L);
      TestSuccessor(4L, 6L);
      TestPredecessorNRemove(7L, 6L);
      TestSuccessorNRemove(0L, 1L);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 2L);
      TestFoldBackward((dat, acc) -> acc + dat, 0L, 2L);
      TestClear();
      TestSize(0, false);
      
      TestClear();
      TestInsert(4L, true);
      TestInsert(5L, true);
      TestInsert(6L, true);
      TestInsert(7L, true);
      TestInsert(8L, true);
      TestInsert(9L, true);
      TestInsert(10L, true);
      TestInsert(11L, true);
      TestSuccessorNRemove(3L, 4L);
      TestPredecessorNRemove(8L, 7L);
      TestPredecessorNRemove(6L, 5L);
      TestPredecessorNRemove(12L, 11L);
    }
  }

  @Nested
  @DisplayName("WOrderedSet Edge Cases")
  public class WOrderedSetEdgeCases {

    @Test
    @DisplayName("Single element set")
    public void SingleElement() {
      AddTest(13);
      NewEmptyContainer();
      TestInsert(42L, true);
      TestSize(1, false);
      TestMin(42L);
      TestMax(42L);
      TestExists(42L, true);
      TestPredecessor(42L, null);
      TestSuccessor(42L, null);
      TestPredecessor(50L, 42L);
      TestSuccessor(0L, 42L);
      TestRemove(42L, true);
      TestSize(0, false);
      TestIsEmpty(true, false);
      TestMin(null);
    }

    @Test
    @DisplayName("Predecessor and Successor with gaps")
    public void PredSuccWithGaps() {
      AddTest(14);
      NewEmptyContainer();
      TestInsert(10L, true);
      TestInsert(20L, true);
      TestInsert(30L, true);
      TestInsert(40L, true);
      TestInsert(50L, true);
      TestPredecessor(15L, 10L);
      TestPredecessor(25L, 20L);
      TestPredecessor(35L, 30L);
      TestSuccessor(15L, 20L);
      TestSuccessor(25L, 30L);
      TestSuccessor(35L, 40L);
      TestPredecessor(5L, null);
      TestSuccessor(55L, null);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Remove min until empty")
    public void RemoveMinUntilEmpty() {
      AddTest(12);
      NewEmptyContainer();
      TestInsert(3L, true);
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestMinNRemove(1L);
      TestSize(2, false);
      TestMinNRemove(2L);
      TestSize(1, false);
      TestMinNRemove(3L);
      TestSize(0, false);
      TestIsEmpty(true, false);
      TestMin(null);
      TestMax(null);
    }

    @Test
    @DisplayName("Remove max until empty")
    public void RemoveMaxUntilEmpty() {
      AddTest(12);
      NewEmptyContainer();
      TestInsert(3L, true);
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestMaxNRemove(3L);
      TestSize(2, false);
      TestMaxNRemove(2L);
      TestSize(1, false);
      TestMaxNRemove(1L);
      TestSize(0, false);
      TestIsEmpty(true, false);
      TestMin(null);
      TestMax(null);
    }

    @Test
    @DisplayName("Consecutive numbers")
    public void ConsecutiveNumbers() {
      AddTest(13);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      TestInsert(4L, true);
      TestInsert(5L, true);
      TestPredecessor(3L, 2L);
      TestSuccessor(3L, 4L);
      TestPredecessor(1L, null);
      TestSuccessor(5L, null);
      TestRemove(3L, true);
      TestPredecessor(4L, 2L);
      TestSuccessor(2L, 4L);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Negative numbers in set")
    public void NegativeNumbers() {
      AddTest(13);
      NewEmptyContainer();
      TestInsert(-10L, true);
      TestInsert(-5L, true);
      TestInsert(0L, true);
      TestInsert(5L, true);
      TestInsert(10L, true);
      TestMin(-10L);
      TestMax(10L);
      TestPredecessor(0L, -5L);
      TestSuccessor(0L, 5L);
      TestPredecessor(-7L, -10L);
      TestSuccessor(-7L, -5L);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 0L);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Duplicate insert rejection")
    public void DuplicateRejection() {
      AddTest(11);
      NewEmptyContainer();
      TestInsert(5L, true);
      TestInsert(5L, false);
      TestInsert(5L, false);
      TestSize(1, false);
      TestInsert(10L, true);
      TestInsert(10L, false);
      TestSize(2, false);
      TestInsert(5L, false);
      TestInsert(10L, false);
      TestSize(2, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Remove non-existent element")
    public void RemoveNonExistent() {
      AddTest(8);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(3L, true);
      TestInsert(5L, true);
      TestRemove(2L, false);
      TestRemove(4L, false);
      TestRemove(0L, false);
      TestSize(3, false);
      TestPrintContent("");
    }

  }

  @Nested
  @DisplayName("WOrderedSet Stress Tests")
  public class WOrderedSetStressTests {

    @Test
    @DisplayName("Alternating min and max removal")
    public void AlternatingMinMaxRemoval() {
      AddTest(18);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      TestInsert(4L, true);
      TestInsert(5L, true);
      TestInsert(6L, true);
      TestMinNRemove(1L);
      TestMaxNRemove(6L);
      TestMinNRemove(2L);
      TestMaxNRemove(5L);
      TestSize(2, false);
      TestMin(3L);
      TestMax(4L);
      TestMinNRemove(3L);
      TestMaxNRemove(4L);
      TestIsEmpty(true, false);
      TestMin(null);
      TestMax(null);
    }

    @Test
    @DisplayName("Predecessor chain navigation")
    public void PredecessorChainNavigation() {
      AddTest(16);
      NewEmptyContainer();
      TestInsert(10L, true);
      TestInsert(20L, true);
      TestInsert(30L, true);
      TestInsert(40L, true);
      TestInsert(50L, true);
      TestPredecessor(50L, 40L);
      TestPredecessor(40L, 30L);
      TestPredecessor(30L, 20L);
      TestPredecessor(20L, 10L);
      TestPredecessor(10L, null);
      TestPredecessor(45L, 40L);
      TestPredecessor(35L, 30L);
      TestPredecessor(25L, 20L);
      TestPredecessor(15L, 10L);
      TestPredecessor(5L, null);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Successor chain navigation")
    public void SuccessorChainNavigation() {
      AddTest(16);
      NewEmptyContainer();
      TestInsert(10L, true);
      TestInsert(20L, true);
      TestInsert(30L, true);
      TestInsert(40L, true);
      TestInsert(50L, true);
      TestSuccessor(10L, 20L);
      TestSuccessor(20L, 30L);
      TestSuccessor(30L, 40L);
      TestSuccessor(40L, 50L);
      TestSuccessor(50L, null);
      TestSuccessor(5L, 10L);
      TestSuccessor(15L, 20L);
      TestSuccessor(25L, 30L);
      TestSuccessor(35L, 40L);
      TestSuccessor(55L, null);
      TestPrintContent("");
    }

    @Test
    @DisplayName("PredecessorNRemove chain")
    public void PredecessorNRemoveChain() {
      AddTest(15);
      NewEmptyContainer();
      TestInsert(10L, true);
      TestInsert(20L, true);
      TestInsert(30L, true);
      TestInsert(40L, true);
      TestInsert(50L, true);
      TestPredecessorNRemove(55L, 50L);
      TestSize(4, false);
      TestPredecessorNRemove(45L, 40L);
      TestSize(3, false);
      TestPredecessorNRemove(35L, 30L);
      TestSize(2, false);
      TestPredecessorNRemove(25L, 20L);
      TestSize(1, false);
      TestPredecessorNRemove(15L, 10L);
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("SuccessorNRemove chain")
    public void SuccessorNRemoveChain() {
      AddTest(15);
      NewEmptyContainer();
      TestInsert(10L, true);
      TestInsert(20L, true);
      TestInsert(30L, true);
      TestInsert(40L, true);
      TestInsert(50L, true);
      TestSuccessorNRemove(5L, 10L);
      TestSize(4, false);
      TestSuccessorNRemove(15L, 20L);
      TestSize(3, false);
      TestSuccessorNRemove(25L, 30L);
      TestSize(2, false);
      TestSuccessorNRemove(35L, 40L);
      TestSize(1, false);
      TestSuccessorNRemove(45L, 50L);
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Extreme values in ordered set")
    public void ExtremeValues() {
      AddTest(14);
      NewEmptyContainer();
      TestInsert(Long.MAX_VALUE, true);
      TestInsert(Long.MIN_VALUE, true);
      TestInsert(0L, true);
      TestMin(Long.MIN_VALUE);
      TestMax(Long.MAX_VALUE);
      TestPredecessor(0L, Long.MIN_VALUE);
      TestSuccessor(0L, Long.MAX_VALUE);
      TestExists(Long.MAX_VALUE, true);
      TestExists(Long.MIN_VALUE, true);
      TestMinNRemove(Long.MIN_VALUE);
      TestMin(0L);
      TestMaxNRemove(Long.MAX_VALUE);
      TestMax(0L);
      TestSize(1, false);
    }

    @Test
    @DisplayName("Large ordered set operations")
    public void LargeOrderedSetOperations() {
      AddTest(80);
      NewEmptyContainer();
      for (long i = 1L; i <= 50L; i++) {
        TestInsert(i, true);
      }
      TestSize(50, false);
      TestMin(1L);
      TestMax(50L);
      TestPredecessor(25L, 24L);
      TestSuccessor(25L, 26L);
      for (int i = 0; i < 10; i++) {
        TestRemoveMin();
      }
      TestSize(40, false);
      TestMin(11L);
      for (int i = 0; i < 10; i++) {
        TestRemoveMax();
      }
      TestSize(30, false);
      TestMax(40L);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 765L);
    }

    @Test
    @DisplayName("Clear and rebuild ordered set")
    public void ClearAndRebuild() {
      AddTest(19);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      TestClear();
      TestIsEmpty(true, false);
      TestMin(null);
      TestMax(null);
      TestInsert(100L, true);
      TestInsert(50L, true);
      TestInsert(150L, true);
      TestMin(50L);
      TestMax(150L);
      TestSize(3, false);
      TestPredecessor(100L, 50L);
      TestSuccessor(100L, 150L);
      TestPredecessor(50L, null);
      TestSuccessor(50L, 100L);
      TestPredecessor(150L, 100L);
      TestSuccessor(150L, null);
    }

  }

  @Nested
  @DisplayName("WOrderedSet Construction and Equality tests")
  public class WOrderedSetConstructionAndEqualityTests {
  
    @Test
    @DisplayName("Construction from another container")
    public void ConstructionFromAnotherContainer() {
      // Constructing from a list
      AddTest(7);
      NewEmptyContainer();
      List<Long> list = new LLList<>();
      list.Insert(4L);
      list.Insert(2L);
      list.Insert(3L);
      list.Insert(2L);
      list.Insert(1L);
      WOrderedSet<Long> conFromList = new WOrderedSet<>(list);
      TestInsert(4L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      TestInsert(1L, true);
      TestIsEqual(conFromList, true);
      TestPrintContent("");
      SortedChain<Long> sortedChain = new LLSortedChain<>(list);
      conFromList = new WOrderedSet<>(new LLList<>(), sortedChain);
      TestIsEqual(conFromList, true);
      conFromList = new WOrderedSet<>(sortedChain, new LLList<>());
      TestIsEqual(conFromList, true);
    }

    @Test
    @DisplayName("Construction from empty container")
    public void ConstructionFromEmptyContainer() {
      AddTest(4);
      List<Long> emptyList = new LLList<>();
      WOrderedSet<Long> conFromEmpty = new WOrderedSet<>(emptyList);
      NewEmptyContainer();
      TestIsEqual(conFromEmpty, true);
      TestSize(0, false);
      TestIsEmpty(true, false);
      TestMin(null);
    }

    @Test
    @DisplayName("Construction from sorted chain")
    public void ConstructionFromSortedChain() {
      AddTest(8);
      SortedChain<Long> sortedChain = new LLSortedChain<>();
      sortedChain.Insert(5L);
      sortedChain.Insert(3L);
      sortedChain.Insert(1L);
      sortedChain.Insert(4L);
      sortedChain.Insert(2L);
      WOrderedSet<Long> conFromChain = new WOrderedSet<>(sortedChain);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      TestInsert(4L, true);
      TestInsert(5L, true);
      TestIsEqual(conFromChain, true);
      TestSize(5, false);
      TestMin(1L);
    }

    @Test
    @DisplayName("Equality with same elements different order insertion")
    public void EqualityDifferentInsertionOrder() {
      AddTest(6);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      
      WOrderedSet<Long> other = (WOrderedSet<Long>) GetNewEmptyContainer();
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
      
      WOrderedSet<Long> smaller = (WOrderedSet<Long>) GetNewEmptyContainer();
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
      
      WOrderedSet<Long> different = (WOrderedSet<Long>) GetNewEmptyContainer();
      different.Insert(1L);
      different.Insert(2L);
      different.Insert(4L);
      TestIsEqual(different, false);
      TestSize(3, false);
    }

    @Test
    @DisplayName("Equality of two empty sets")
    public void EqualityEmptySets() {
      AddTest(3);
      NewEmptyContainer();
      WOrderedSet<Long> other = (WOrderedSet<Long>) GetNewEmptyContainer();
      TestIsEqual(other, true);
      TestSize(0, false);
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Copy construction preserves elements")
    public void CopyConstructionPreservesElements() {
      AddTest(7);
      List<Long> list = new LLList<>();
      list.Insert(10L);
      list.Insert(20L);
      list.Insert(30L);
      WOrderedSet<Long> original = new WOrderedSet<>(list);
      WOrderedSet<Long> copy = new WOrderedSet<>(original);
      NewEmptyContainer();
      TestInsert(10L, true);
      TestInsert(20L, true);
      TestInsert(30L, true);
      TestIsEqual(original, true);
      TestIsEqual(copy, true);
      TestSize(3, false);
      TestPrintContent("");
    }

  }

  @Nested
  @DisplayName("WOrderedSet Null Testing")
  public class WOrderedSetNullTesting {

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
    @DisplayName("Predecessor with null")
    public void PredecessorNull() {
      AddTest(4);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      TestPredecessor(null, null);
    }

    @Test
    @DisplayName("Successor with null")
    public void SuccessorNull() {
      AddTest(4);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      TestSuccessor(null, null);
    }

    @Test
    @DisplayName("PredecessorNRemove with null")
    public void PredecessorNRemoveNull() {
      AddTest(5);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      TestPredecessorNRemove(null, null);
      TestSize(3, false);
    }

    @Test
    @DisplayName("SuccessorNRemove with null")
    public void SuccessorNRemoveNull() {
      AddTest(5);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      TestSuccessorNRemove(null, null);
      TestSize(3, false);
    }

    @Test
    @DisplayName("Min and Max on empty set return null")
    public void MinMaxEmptyReturnsNull() {
      AddTest(4);
      NewEmptyContainer();
      TestMin(null);
      TestMax(null);
      TestIsEmpty(true, false);
      TestSize(0, false);
    }

    @Test
    @DisplayName("MinNRemove and MaxNRemove on empty set")
    public void MinMaxNRemoveEmpty() {
      AddTest(4);
      NewEmptyContainer();
      TestMinNRemove(null);
      TestMaxNRemove(null);
      TestIsEmpty(true, false);
      TestSize(0, false);
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
    @DisplayName("IsEqual with null container")
    public void IsEqualNullContainer() {
      AddTest(3);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestIsEqual(null, false);
    }

    @Test
    @DisplayName("Operations after inserting and removing maintain consistency")
    public void NullOperationsConsistency() {
      AddTest(10);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(null, false);
      TestInsert(2L, true);
      TestRemove(null, false);
      TestInsert(3L, true);
      TestExists(null, false);
      TestPredecessor(null, null);
      TestSuccessor(null, null);
      TestSize(3, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 6L);
    }

  }

  @Nested
  @DisplayName("WOrderedSet Comprehensive Method Combinations")
  public class WOrderedSetComprehensiveMethodCombinations {

    @Test
    @DisplayName("Insert, Exists, Min, Max cycle through all elements")
    public void InsertExistsMinMaxCycle() {
      AddTest(25);
      NewEmptyContainer();
      // Insert elements and verify with Exists, Min, Max after each
      TestInsert(50L, true);
      TestExists(50L, true);
      TestMin(50L);
      TestMax(50L);
      TestInsert(25L, true);
      TestExists(25L, true);
      TestMin(25L);
      TestMax(50L);
      TestInsert(75L, true);
      TestExists(75L, true);
      TestMin(25L);
      TestMax(75L);
      TestInsert(10L, true);
      TestExists(10L, true);
      TestMin(10L);
      TestMax(75L);
      TestInsert(90L, true);
      TestExists(90L, true);
      TestMin(10L);
      TestMax(90L);
      TestSize(5, false);
      TestIsEmpty(false, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("RemoveMin then verify with Predecessor and Successor")
    public void RemoveMinWithPredSucc() {
      AddTest(20);
      NewEmptyContainer();
      TestInsert(10L, true);
      TestInsert(20L, true);
      TestInsert(30L, true);
      TestInsert(40L, true);
      TestInsert(50L, true);
      TestRemoveMin();
      TestMin(20L);
      TestPredecessor(20L, null);
      TestSuccessor(20L, 30L);
      TestRemoveMin();
      TestMin(30L);
      TestPredecessor(30L, null);
      TestSuccessor(30L, 40L);
      TestRemoveMin();
      TestMin(40L);
      TestPredecessor(40L, null);
      TestSuccessor(40L, 50L);
      TestSize(2, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("RemoveMax then verify with Predecessor and Successor")
    public void RemoveMaxWithPredSucc() {
      AddTest(20);
      NewEmptyContainer();
      TestInsert(10L, true);
      TestInsert(20L, true);
      TestInsert(30L, true);
      TestInsert(40L, true);
      TestInsert(50L, true);
      TestRemoveMax();
      TestMax(40L);
      TestPredecessor(40L, 30L);
      TestSuccessor(40L, null);
      TestRemoveMax();
      TestMax(30L);
      TestPredecessor(30L, 20L);
      TestSuccessor(30L, null);
      TestRemoveMax();
      TestMax(20L);
      TestPredecessor(20L, 10L);
      TestSuccessor(20L, null);
      TestSize(2, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("MinNRemove and MaxNRemove alternating with IsEqual")
    public void MinMaxNRemoveAlternatingWithIsEqual() {
      AddTest(18);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      TestInsert(4L, true);
      TestInsert(5L, true);
      TestInsert(6L, true);
      
      WOrderedSet<Long> snapshot = (WOrderedSet<Long>) GetNewEmptyContainer();
      snapshot.Insert(2L);
      snapshot.Insert(3L);
      snapshot.Insert(4L);
      snapshot.Insert(5L);
      
      TestMinNRemove(1L);
      TestMaxNRemove(6L);
      TestIsEqual(snapshot, true);
      TestMinNRemove(2L);
      TestMaxNRemove(5L);
      TestSize(2, false);
      TestMin(3L);
      TestMax(4L);
      TestMinNRemove(3L);
      TestMaxNRemove(4L);
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("PredecessorNRemove chain with FoldForward verification")
    public void PredecessorNRemoveChainWithFold() {
      AddTest(15);
      NewEmptyContainer();
      TestInsert(10L, true);
      TestInsert(20L, true);
      TestInsert(30L, true);
      TestInsert(40L, true);
      TestInsert(50L, true);
      // Sum = 150
      TestFoldForward((dat, acc) -> acc + dat, 0L, 150L);
      TestPredecessorNRemove(55L, 50L);
      // Sum = 100
      TestFoldForward((dat, acc) -> acc + dat, 0L, 100L);
      TestPredecessorNRemove(45L, 40L);
      // Sum = 60
      TestFoldForward((dat, acc) -> acc + dat, 0L, 60L);
      TestPredecessorNRemove(35L, 30L);
      // Sum = 30
      TestFoldForward((dat, acc) -> acc + dat, 0L, 30L);
      TestSize(2, false);
    }

    @Test
    @DisplayName("SuccessorNRemove chain with FoldBackward verification")
    public void SuccessorNRemoveChainWithFold() {
      AddTest(15);
      NewEmptyContainer();
      TestInsert(10L, true);
      TestInsert(20L, true);
      TestInsert(30L, true);
      TestInsert(40L, true);
      TestInsert(50L, true);
      // Sum = 150
      TestFoldBackward((dat, acc) -> acc + dat, 0L, 150L);
      TestSuccessorNRemove(5L, 10L);
      // Sum = 140
      TestFoldBackward((dat, acc) -> acc + dat, 0L, 140L);
      TestSuccessorNRemove(15L, 20L);
      // Sum = 120
      TestFoldBackward((dat, acc) -> acc + dat, 0L, 120L);
      TestSuccessorNRemove(25L, 30L);
      // Sum = 90
      TestFoldBackward((dat, acc) -> acc + dat, 0L, 90L);
      TestSize(2, false);
    }

    @Test
    @DisplayName("Filter then verify with Exists and Size")
    public void FilterWithExistsAndSize() {
      AddTest(18);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      TestInsert(4L, true);
      TestInsert(5L, true);
      TestInsert(6L, true);
      TestSize(6, false);
      // Filter out odd numbers
      TestFilter(dat -> dat % 2 == 0);
      TestSize(3, false);
      TestExists(1L, false);
      TestExists(2L, true);
      TestExists(3L, false);
      TestExists(4L, true);
      TestExists(5L, false);
      TestExists(6L, true);
      TestMin(2L);
      TestMax(6L);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Union with verification using Exists, Size, Min, Max")
    public void UnionWithFullVerification() {
      AddTest(20);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(3L, true);
      TestInsert(5L, true);
      
      WOrderedSet<Long> other = (WOrderedSet<Long>) GetNewEmptyContainer();
      other.Insert(2L);
      other.Insert(4L);
      other.Insert(6L);
      
      TestUnion(other);
      TestSize(6, false);
      TestExists(1L, true);
      TestExists(2L, true);
      TestExists(3L, true);
      TestExists(4L, true);
      TestExists(5L, true);
      TestExists(6L, true);
      TestMin(1L);
      TestMax(6L);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 21L);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Intersection with verification using Exists, Size, Min, Max")
    public void IntersectionWithFullVerification() {
      AddTest(16);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      TestInsert(4L, true);
      TestInsert(5L, true);
      
      WOrderedSet<Long> other = (WOrderedSet<Long>) GetNewEmptyContainer();
      other.Insert(2L);
      other.Insert(4L);
      other.Insert(6L);
      
      TestIntersection(other);
      TestSize(2, false);
      TestExists(1L, false);
      TestExists(2L, true);
      TestExists(3L, false);
      TestExists(4L, true);
      TestExists(5L, false);
      TestMin(2L);
      TestMax(4L);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Difference with verification using Exists, Size, Min, Max")
    public void DifferenceWithFullVerification() {
      AddTest(16);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      TestInsert(4L, true);
      TestInsert(5L, true);
      
      WOrderedSet<Long> other = (WOrderedSet<Long>) GetNewEmptyContainer();
      other.Insert(2L);
      other.Insert(4L);
      
      TestDifference(other);
      TestSize(3, false);
      TestExists(1L, true);
      TestExists(2L, false);
      TestExists(3L, true);
      TestExists(4L, false);
      TestExists(5L, true);
      TestMin(1L);
      TestMax(5L);
      TestPrintContent("");
    }

    @Test
    @DisplayName("InsertAll then RemoveAll with verification")
    public void InsertAllThenRemoveAll() {
      AddTest(18);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      
      List<Long> toAdd = new LLList<>();
      toAdd.Insert(3L);
      toAdd.Insert(4L);
      toAdd.Insert(5L);
      
      TestInsertAll(toAdd, true);
      TestSize(5, false);
      TestExists(3L, true);
      TestExists(4L, true);
      TestExists(5L, true);
      TestMin(1L);
      TestMax(5L);
      
      List<Long> toRemove = new LLList<>();
      toRemove.Insert(2L);
      toRemove.Insert(4L);
      
      TestRemoveAll(toRemove, true);
      TestSize(3, false);
      TestExists(2L, false);
      TestExists(4L, false);
      TestMin(1L);
      TestMax(5L);
    }

    @Test
    @DisplayName("InsertSome then RemoveSome with verification")
    public void InsertSomeThenRemoveSome() {
      AddTest(18);
      NewEmptyContainer();
      TestInsert(10L, true);
      TestInsert(20L, true);
      
      List<Long> toAdd = new LLList<>();
      toAdd.Insert(30L);
      toAdd.Insert(40L);
      toAdd.Insert(10L); // duplicate
      
      TestInsertSome(toAdd, true);
      TestSize(4, false);
      TestExists(10L, true);
      TestExists(30L, true);
      TestExists(40L, true);
      
      List<Long> toRemove = new LLList<>();
      toRemove.Insert(20L);
      toRemove.Insert(50L); // not present
      
      TestRemoveSome(toRemove, true);
      TestSize(3, false);
      TestExists(20L, false);
      TestMin(10L);
      TestMax(40L);
      TestPrintContent("");
    }

    @Test
    @DisplayName("TraverseForward and TraverseBackward with predicates")
    public void TraverseForwardAndBackward() {
      AddTest(12);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      TestInsert(4L, true);
      TestInsert(5L, true);
      // All positive
      TestTraverseForward(dat -> dat > 0L, true);
      TestTraverseBackward(dat -> dat > 0L, true);
      // All less than 10
      TestTraverseForward(dat -> dat < 10L, true);
      TestTraverseBackward(dat -> dat < 10L, true);
      // None negative
      TestTraverseForward(dat -> dat < 0L, false);
      TestTraverseBackward(dat -> dat < 0L, false);
      TestSize(5, false);
    }

    @Test
    @DisplayName("FIterator and BIterator usage")
    public void FIteratorAndBIteratorUsage() {
      AddTest(8);
      NewEmptyContainer();
      TestInsert(10L, true);
      TestInsert(20L, true);
      TestInsert(30L, true);
      TestInsert(40L, true);
      TestInsert(50L, true);
      TestFIterator();
      TestBIterator();
      TestSize(5, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Clear then rebuild with all operations")
    public void ClearThenRebuildWithAllOps() {
      AddTest(30);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      TestSize(3, false);
      TestClear();
      TestIsEmpty(true, false);
      TestMin(null);
      TestMax(null);
      
      // Rebuild
      TestInsert(100L, true);
      TestInsert(50L, true);
      TestInsert(150L, true);
      TestInsert(25L, true);
      TestInsert(75L, true);
      TestSize(5, false);
      TestMin(25L);
      TestMax(150L);
      TestPredecessor(100L, 75L);
      TestSuccessor(100L, 150L);
      TestRemove(100L, true);
      TestPredecessor(150L, 75L);
      TestSuccessor(75L, 150L);
      TestRemoveMin();
      TestRemoveMax();
      TestSize(2, false);
      TestMin(50L);
      TestMax(75L);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 125L);
      TestIsEqual((WOrderedSet<Long>) GetNewEmptyContainer(), false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("RemovePredecessor and RemoveSuccessor combinations")
    public void RemovePredecessorAndSuccessorCombos() {
      AddTest(20);
      NewEmptyContainer();
      TestInsert(10L, true);
      TestInsert(20L, true);
      TestInsert(30L, true);
      TestInsert(40L, true);
      TestInsert(50L, true);
      TestInsert(60L, true);
      TestInsert(70L, true);
      // Remove predecessor of 35 (which is 30)
      TestRemovePredecessor(35L, 30L);
      TestExists(30L, false);
      TestSize(6, false);
      // Remove successor of 45 (which is 50)
      TestRemoveSuccessor(45L, 50L);
      TestExists(50L, false);
      TestSize(5, false);
      // Verify structure
      TestPredecessor(40L, 20L);
      TestSuccessor(40L, 60L);
      TestMin(10L);
      TestMax(70L);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Union, Intersection, Difference chain")
    public void SetOperationsChain() {
      AddTest(25);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      TestInsert(4L, true);
      TestInsert(5L, true);
      
      WOrderedSet<Long> set2 = (WOrderedSet<Long>) GetNewEmptyContainer();
      set2.Insert(4L);
      set2.Insert(5L);
      set2.Insert(6L);
      set2.Insert(7L);
      
      WOrderedSet<Long> set3 = (WOrderedSet<Long>) GetNewEmptyContainer();
      set3.Insert(1L);
      set3.Insert(7L);
      
      // Union with set2: {1,2,3,4,5,6,7}
      TestUnion(set2);
      TestSize(7, false);
      TestMin(1L);
      TestMax(7L);
      
      // Intersection with set3: {1,7}
      TestIntersection(set3);
      TestSize(2, false);
      TestExists(1L, true);
      TestExists(7L, true);
      TestExists(4L, false);
      
      // Difference with set3: {} (empty)
      TestDifference(set3);
      TestIsEmpty(true, false);
      TestMin(null);
      TestMax(null);
    }

    @Test
    @DisplayName("All Remove operations exhaustively")
    public void AllRemoveOperationsExhaustively() {
      AddTest(30);
      NewEmptyContainer();
      for (long i = 1L; i <= 10L; i++) {
        TestInsert(i, true);
      }
      TestSize(10, false);
      
      // Remove via different methods
      TestRemove(5L, true);
      TestExists(5L, false);
      TestSize(9, false);
      
      TestRemoveMin();
      TestMin(2L);
      TestSize(8, false);
      
      TestRemoveMax();
      TestMax(9L);
      TestSize(7, false);
      
      TestMinNRemove(2L);
      TestMin(3L);
      TestSize(6, false);
      
      TestMaxNRemove(9L);
      TestMax(8L);
      TestSize(5, false);
      
      TestPredecessorNRemove(5L, 4L);
      TestExists(4L, false);
      TestSize(4, false);
      
      TestSuccessorNRemove(5L, 6L);
      TestExists(6L, false);
      TestSize(3, false);
      
      // Remaining: 3, 7, 8
      TestMin(3L);
      TestMax(8L);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 18L);
    }

    @Test
    @DisplayName("Filter removes all elements")
    public void FilterRemovesAll() {
      AddTest(10);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      TestInsert(4L, true);
      TestInsert(5L, true);
      // Filter: keep nothing
      TestFilter(dat -> false);
      TestIsEmpty(true, false);
      TestSize(0, false);
      TestMin(null);
      TestMax(null);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Filter keeps all elements")
    public void FilterKeepsAll() {
      AddTest(12);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      TestInsert(4L, true);
      TestInsert(5L, true);
      // Filter: keep all
      TestFilter(dat -> true);
      TestIsEmpty(false, false);
      TestSize(5, false);
      TestMin(1L);
      TestMax(5L);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 15L);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Multiple FoldForward and FoldBackward operations")
    public void MultipleFoldOperations() {
      AddTest(12);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      TestInsert(4L, true);
      TestInsert(5L, true);
      // Sum
      TestFoldForward((dat, acc) -> acc + dat, 0L, 15L);
      TestFoldBackward((dat, acc) -> acc + dat, 0L, 15L);
      // Product
      TestFoldForward((dat, acc) -> acc * dat, 1L, 120L);
      TestFoldBackward((dat, acc) -> acc * dat, 1L, 120L);
      // Count
      TestFoldForward((dat, acc) -> acc + 1L, 0L, 5L);
      TestFoldBackward((dat, acc) -> acc + 1L, 0L, 5L);
      TestSize(5, false);
    }

    @Test
    @DisplayName("IsEqual after various modifications")
    public void IsEqualAfterModifications() {
      AddTest(20);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      
      WOrderedSet<Long> other = (WOrderedSet<Long>) GetNewEmptyContainer();
      other.Insert(1L);
      other.Insert(2L);
      other.Insert(3L);
      TestIsEqual(other, true);
      
      // Modify this container
      TestInsert(4L, true);
      TestIsEqual(other, false);
      
      // Make other match
      other.Insert(4L);
      TestIsEqual(other, true);
      
      // Remove from this
      TestRemove(2L, true);
      TestIsEqual(other, false);
      
      // Match other
      other.Remove(2L);
      TestIsEqual(other, true);
      
      // Clear both
      TestClear();
      other.Clear();
      TestIsEqual(other, true);
      TestIsEmpty(true, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Empty set all operations")
    public void EmptySetAllOperations() {
      AddTest(20);
      NewEmptyContainer();
      TestIsEmpty(true, false);
      TestSize(0, false);
      TestMin(null);
      TestMax(null);
      TestMinNRemove(null);
      TestMaxNRemove(null);
      TestExists(1L, false);
      TestRemove(1L, false);
      TestPredecessor(1L, null);
      TestSuccessor(1L, null);
      TestPredecessorNRemove(1L, null);
      TestSuccessorNRemove(1L, null);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 0L);
      TestFoldBackward((dat, acc) -> acc + dat, 0L, 0L);
      TestFilter(dat -> true);
      TestIsEmpty(true, false);
      
      WOrderedSet<Long> other = (WOrderedSet<Long>) GetNewEmptyContainer();
      TestIsEqual(other, true);
      TestUnion(other);
      TestIntersection(other);
      TestDifference(other);
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Single element all operations")
    public void SingleElementAllOperations() {
      AddTest(25);
      NewEmptyContainer();
      TestInsert(42L, true);
      TestIsEmpty(false, false);
      TestSize(1, false);
      TestMin(42L);
      TestMax(42L);
      TestExists(42L, true);
      TestExists(0L, false);
      TestPredecessor(42L, null);
      TestSuccessor(42L, null);
      TestPredecessor(50L, 42L);
      TestSuccessor(30L, 42L);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 42L);
      TestFoldBackward((dat, acc) -> acc + dat, 0L, 42L);
      TestTraverseForward(dat -> dat.equals(42L), true);
      TestTraverseBackward(dat -> dat.equals(42L), true);
      
      WOrderedSet<Long> other = (WOrderedSet<Long>) GetNewEmptyContainer();
      other.Insert(42L);
      TestIsEqual(other, true);
      
      // Remove the single element
      TestRemove(42L, true);
      TestIsEmpty(true, false);
      TestMin(null);
      TestMax(null);
      TestIsEqual(other, false);
    }

    @Test
    @DisplayName("Predecessor and Successor boundary cases")
    public void PredSuccBoundaryCases() {
      AddTest(22);
      NewEmptyContainer();
      TestInsert(Long.MIN_VALUE, true);
      TestInsert(0L, true);
      TestInsert(Long.MAX_VALUE, true);
      
      TestMin(Long.MIN_VALUE);
      TestMax(Long.MAX_VALUE);
      
      TestPredecessor(Long.MIN_VALUE, null);
      TestSuccessor(Long.MIN_VALUE, 0L);
      TestPredecessor(0L, Long.MIN_VALUE);
      TestSuccessor(0L, Long.MAX_VALUE);
      TestPredecessor(Long.MAX_VALUE, 0L);
      TestSuccessor(Long.MAX_VALUE, null);
      
      // Boundary searches
      TestPredecessor(Long.MIN_VALUE + 1, Long.MIN_VALUE);
      TestSuccessor(Long.MAX_VALUE - 1, Long.MAX_VALUE);
      
      TestRemove(0L, true);
      TestPredecessor(Long.MAX_VALUE, Long.MIN_VALUE);
      TestSuccessor(Long.MIN_VALUE, Long.MAX_VALUE);
      TestSize(2, false);
      
      TestFoldForward((dat, acc) -> acc + 1L, 0L, 2L);
      TestPrintContent("");
    }

    @Test
    @DisplayName("InsertAll with duplicates then Filter")
    public void InsertAllWithDuplicatesThenFilter() {
      AddTest(18);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      
      List<Long> toAdd = new LLList<>();
      toAdd.Insert(2L); // duplicate
      toAdd.Insert(3L); // duplicate
      toAdd.Insert(4L);
      toAdd.Insert(5L);
      toAdd.Insert(6L);
      
      TestInsertAll(toAdd, false);
      TestSize(6, false);
      TestClear();
      TestInsertAll(toAdd, true);
      TestMin(2L);
      TestMax(6L);
      
      // Filter to keep only even
      TestFilter(dat -> dat % 2 == 0);
      TestSize(3, false);
      TestExists(1L, false);
      TestExists(2L, true);
      TestExists(3L, false);
      TestExists(4L, true);
      TestExists(6L, true);
      TestMin(2L);
      TestMax(6L);
    }

    @Test
    @DisplayName("RemoveAll non-existent elements")
    public void RemoveAllNonExistent() {
      AddTest(12);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      
      List<Long> toRemove = new LLList<>();
      toRemove.Insert(10L);
      toRemove.Insert(20L);
      toRemove.Insert(30L);
      
      TestRemoveAll(toRemove, false);
      TestSize(3, false);
      TestExists(1L, true);
      TestExists(2L, true);
      TestExists(3L, true);
      TestMin(1L);
      TestMax(3L);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Complex chain: Insert, Union, Filter, Remove, Fold")
    public void ComplexChainOperations() {
      AddTest(30);
      NewEmptyContainer();
      // Insert initial elements
      TestInsert(5L, true);
      TestInsert(10L, true);
      TestInsert(15L, true);
      
      // Create another set and union
      WOrderedSet<Long> other = (WOrderedSet<Long>) GetNewEmptyContainer();
      other.Insert(20L);
      other.Insert(25L);
      other.Insert(30L);
      
      TestUnion(other);
      TestSize(6, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 105L);
      
      // Filter: keep multiples of 10
      TestFilter(dat -> dat % 10 == 0);
      TestSize(3, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 60L);
      TestMin(10L);
      TestMax(30L);
      
      // Remove some
      TestRemove(20L, true);
      TestSize(2, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 40L);
      
      // Verify final state
      TestExists(10L, true);
      TestExists(20L, false);
      TestExists(30L, true);
      TestPredecessor(30L, 10L);
      TestSuccessor(10L, 30L);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Interleaved Insert and Remove with Exists checks")
    public void InterleavedInsertRemoveWithExists() {
      AddTest(30);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestExists(1L, true);
      TestInsert(2L, true);
      TestExists(2L, true);
      TestRemove(1L, true);
      TestExists(1L, false);
      TestInsert(3L, true);
      TestExists(3L, true);
      TestInsert(1L, true);
      TestExists(1L, true);
      TestRemove(2L, true);
      TestExists(2L, false);
      TestInsert(4L, true);
      TestRemove(3L, true);
      TestInsert(5L, true);
      TestRemove(1L, true);
      TestInsert(2L, true);
      TestRemove(4L, true);
      // Remaining: 2, 5
      TestSize(2, false);
      TestExists(2L, true);
      TestExists(5L, true);
      TestExists(1L, false);
      TestExists(3L, false);
      TestExists(4L, false);
      TestMin(2L);
      TestMax(5L);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 7L);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Union with self then Intersection with self")
    public void UnionAndIntersectionWithSelf() {
      AddTest(14);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      TestSize(3, false);
      
      // Union with self
      TestUnion(ThisContainer());
      TestSize(3, false);
      TestMin(1L);
      TestMax(3L);
      
      // Intersection with self
      TestIntersection(ThisContainer());
      TestSize(3, false);
      TestMin(1L);
      TestMax(3L);
      
      // Difference with self
      TestDifference(ThisContainer());
      TestIsEmpty(true, false);
      TestSize(0, false);
    }

    @Test
    @DisplayName("Build, verify, clear, rebuild multiple times")
    public void BuildVerifyClearRebuildCycle() {
      AddTest(35);
      // First cycle
      NewEmptyContainer();
      TestInsert(1L, true);
      TestInsert(2L, true);
      TestInsert(3L, true);
      TestSize(3, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 6L);
      TestClear();
      TestIsEmpty(true, false);
      
      // Second cycle
      TestInsert(10L, true);
      TestInsert(20L, true);
      TestInsert(30L, true);
      TestInsert(40L, true);
      TestSize(4, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 100L);
      TestClear();
      TestIsEmpty(true, false);
      
      // Third cycle
      TestInsert(100L, true);
      TestInsert(200L, true);
      TestSize(2, false);
      TestMin(100L);
      TestMax(200L);
      TestPredecessor(200L, 100L);
      TestSuccessor(100L, 200L);
      TestRemoveMin();
      TestRemoveMax();
      TestIsEmpty(true, false);
      
      // Fourth cycle: use InsertAll
      List<Long> batch = new LLList<>();
      batch.Insert(5L);
      batch.Insert(10L);
      batch.Insert(15L);
      TestInsertAll(batch, true);
      TestSize(3, false);
      TestMin(5L);
      TestMax(15L);
      TestFoldBackward((dat, acc) -> acc + dat, 0L, 30L);
    }

  }


}