package zapsdtest.simpletest.apsd.classes.containers.collections.abstractcollections.generic;

import org.junit.jupiter.api.*;

import apsd.classes.containers.collections.abstractcollections.WOrderedSet;
import apsd.classes.containers.collections.concretecollections.LLList;
import apsd.classes.containers.collections.concretecollections.LLSortedChain;
import apsd.interfaces.containers.collections.List;
import apsd.interfaces.containers.collections.SortedChain;

abstract public class WOrderedSetSTest extends WOrderedSetTest<String> {

  // Strings sorted: "apple" < "banana" < "cherry" < "date" < "elderberry"
  // We use alphabetical ordering for Comparable<String>

  @Override
  public void NewNonEmptyContainer() {
    AddTest(10);
    NewEmptyContainer();
    TestInsert("date", true);
    TestInsert("apple", true);
    TestInsert("date", false);
    TestInsert("cherry", true);
    TestInsert("banana", true);
    TestInsert("cherry", false);
    TestInsert("elderberry", true);
    TestInsert("apple", false);
    TestSize(5, false);
    TestPrintContent("");
    // Set: {apple, banana, cherry, date, elderberry}
  }

  @Nested
  @DisplayName("WOrderedSet Basics")
  public class WOrderedSetBasics {

    @Test
    @DisplayName("Check starting from an Empty WOrderedSet")
    public void Empty() {
      AddTest(8);
      NewEmptyContainer();
      TestExists("apple", false);
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
      // Set: {apple, banana, cherry, date, elderberry}
      TestMin("apple");
      TestMax("elderberry");
      TestRemoveMin();
      // Set: {banana, cherry, date, elderberry}
      TestMinNRemove("banana");
      // Set: {cherry, date, elderberry}
      TestInsert("aa", true);
      TestInsert("banana", true);
      // Set: {aa, banana, cherry, date, elderberry}
      TestMin("aa");
      TestMaxNRemove("elderberry");
      // Set: {aa, banana, cherry, date}
      TestSize(4, false);
      TestInsert("fig", true);
      // Set: {aa, banana, cherry, date, fig}
      TestSize(5, false);
      TestMax("fig");
      TestInsert("grape", true);
      // Set: {aa, banana, cherry, date, fig, grape}
      TestSize(6, false);
      TestExists("fig", true);
      TestExists("honey", false);
      TestExists("apple", false);
      TestExists("aa", true);
      TestExists("cherry", true);
      TestRemove("elderberry", false);
      TestRemove("cherry", true);
      // Set: {aa, banana, date, fig, grape}
      TestExists("elderberry", false);
      TestExists("cherry", false);
      TestRemoveMax();
      // Set: {aa, banana, date, fig}
      TestPrintContent("");
      TestPredecessor("date", "banana");
      TestPredecessor("elderberry", "date");
      TestSuccessor("cherry", "date");
      TestSuccessor("date", "fig");
      TestPredecessorNRemove("grape", "fig");
      // Set: {aa, banana, date}
      TestSuccessorNRemove("a", "aa");
      // Set: {banana, date}
      TestFoldForward((dat, acc) -> acc + "," + dat, "", ",banana,date");
      TestFoldBackward((dat, acc) -> acc + "," + dat, "", ",date,banana");
      TestClear();
      TestSize(0, false);

      TestClear();
      TestInsert("date", true);
      TestInsert("elderberry", true);
      TestInsert("fig", true);
      TestInsert("grape", true);
      TestInsert("honey", true);
      TestInsert("ice", true);
      TestInsert("jackfruit", true);
      TestInsert("kiwi", true);
      // Set: {date, elderberry, fig, grape, honey, ice, jackfruit, kiwi}
      TestSuccessorNRemove("cherry", "date");
      // Set: {elderberry, fig, grape, honey, ice, jackfruit, kiwi}
      TestPredecessorNRemove("honey", "grape");
      // Set: {elderberry, fig, honey, ice, jackfruit, kiwi}
      TestPredecessorNRemove("fig", "elderberry");
      // Set: {fig, honey, ice, jackfruit, kiwi}
      TestPredecessorNRemove("mango", "kiwi");
      // Set: {fig, honey, ice, jackfruit}
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
      TestInsert("mango", true);
      TestSize(1, false);
      TestMin("mango");
      TestMax("mango");
      TestExists("mango", true);
      TestPredecessor("mango", null);
      TestSuccessor("mango", null);
      TestPredecessor("zebra", "mango");
      TestSuccessor("aaa", "mango");
      TestRemove("mango", true);
      TestSize(0, false);
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Predecessor and Successor with gaps")
    public void PredSuccWithGaps() {
      AddTest(15);
      NewEmptyContainer();
      TestInsert("banana", true);
      TestInsert("elderberry", true);
      TestInsert("honey", true);
      TestInsert("kiwi", true);
      // Set: {banana, elderberry, honey, kiwi}
      TestPredecessor("cherry", "banana");
      TestSuccessor("cherry", "elderberry");
      TestPredecessor("fig", "elderberry");
      TestSuccessor("fig", "honey");
      TestPredecessor("ice", "honey");
      TestSuccessor("ice", "kiwi");
      TestPredecessor("apple", null);
      TestSuccessor("mango", null);
      TestPredecessor("banana", null);
      TestSuccessor("kiwi", null);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Remove min until empty")
    public void RemoveMinUntilEmpty() {
      AddTest(13);
      NewEmptyContainer();
      TestInsert("cherry", true);
      TestInsert("apple", true);
      TestInsert("banana", true);
      // Set: {apple, banana, cherry}
      TestMinNRemove("apple");
      TestSize(2, false);
      TestMinNRemove("banana");
      TestSize(1, false);
      TestMinNRemove("cherry");
      TestSize(0, false);
      TestIsEmpty(true, false);
      TestMin(null);
      TestMax(null);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Remove max until empty")
    public void RemoveMaxUntilEmpty() {
      AddTest(13);
      NewEmptyContainer();
      TestInsert("cherry", true);
      TestInsert("apple", true);
      TestInsert("banana", true);
      // Set: {apple, banana, cherry}
      TestMaxNRemove("cherry");
      TestSize(2, false);
      TestMaxNRemove("banana");
      TestSize(1, false);
      TestMaxNRemove("apple");
      TestSize(0, false);
      TestIsEmpty(true, false);
      TestMin(null);
      TestMax(null);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Consecutive strings")
    public void ConsecutiveStrings() {
      AddTest(13);
      NewEmptyContainer();
      TestInsert("a", true);
      TestInsert("b", true);
      TestInsert("c", true);
      TestInsert("d", true);
      TestInsert("e", true);
      TestPredecessor("c", "b");
      TestSuccessor("c", "d");
      TestPredecessor("a", null);
      TestSuccessor("e", null);
      TestRemove("c", true);
      TestPredecessor("d", "b");
      TestSuccessor("b", "d");
      TestPrintContent("");
    }

    @Test
    @DisplayName("Short strings in set")
    public void ShortStrings() {
      AddTest(13);
      NewEmptyContainer();
      TestInsert("A", true);
      TestInsert("B", true);
      TestInsert("M", true);
      TestInsert("Y", true);
      TestInsert("Z", true);
      TestMin("A");
      TestMax("Z");
      TestPredecessor("M", "B");
      TestSuccessor("M", "Y");
      TestPredecessor("C", "B");
      TestSuccessor("C", "M");
      TestSize(5, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Duplicate insert rejection")
    public void DuplicateRejection() {
      AddTest(12);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      TestInsert("apple", false);
      TestInsert("banana", false);
      TestInsert("cherry", false);
      TestSize(3, false);
      TestInsert("apple", false);
      TestInsert("apple", false);
      TestInsert("apple", false);
      TestSize(3, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Remove non-existent element")
    public void RemoveNonExistent() {
      AddTest(10);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("cherry", true);
      TestRemove("banana", false);
      TestSize(2, false);
      TestExists("apple", true);
      TestExists("cherry", true);
      TestRemove("date", false);
      TestRemove("fig", false);
      TestSize(2, false);
      TestPrintContent("");
    }

  }

  @Nested
  @DisplayName("WOrderedSet Stress Tests")
  public class WOrderedSetStressTests {

    @Test
    @DisplayName("Alternating min and max removal")
    public void AlternatingMinMaxRemoval() {
      AddTest(17);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      TestInsert("date", true);
      TestInsert("elderberry", true);
      TestInsert("fig", true);
      TestMinNRemove("apple");
      TestMaxNRemove("fig");
      TestMinNRemove("banana");
      TestMaxNRemove("elderberry");
      TestSize(2, false);
      TestMin("cherry");
      TestMax("date");
      TestMinNRemove("cherry");
      TestMaxNRemove("date");
      TestIsEmpty(true, false);
      TestMin(null);
      TestMax(null);
    }

    @Test
    @DisplayName("Predecessor chain navigation")
    public void PredecessorChainNavigation() {
      AddTest(12);
      NewEmptyContainer();
      TestInsert("b", true);
      TestInsert("d", true);
      TestInsert("f", true);
      TestInsert("h", true);
      TestInsert("j", true);
      // Set: {b, d, f, h, j}
      TestPredecessor("k", "j");
      TestPredecessor("i", "h");
      TestPredecessor("g", "f");
      TestPredecessor("e", "d");
      TestPredecessor("c", "b");
      TestPredecessor("a", null);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Successor chain navigation")
    public void SuccessorChainNavigation() {
      AddTest(12);
      NewEmptyContainer();
      TestInsert("b", true);
      TestInsert("d", true);
      TestInsert("f", true);
      TestInsert("h", true);
      TestInsert("j", true);
      // Set: {b, d, f, h, j}
      TestSuccessor("a", "b");
      TestSuccessor("c", "d");
      TestSuccessor("e", "f");
      TestSuccessor("g", "h");
      TestSuccessor("i", "j");
      TestSuccessor("k", null);
      TestPrintContent("");
    }

    @Test
    @DisplayName("PredecessorNRemove chain")
    public void PredecessorNRemoveChain() {
      AddTest(13);
      NewEmptyContainer();
      TestInsert("b", true);
      TestInsert("d", true);
      TestInsert("f", true);
      TestInsert("h", true);
      TestInsert("j", true);
      // Set: {b, d, f, h, j}
      TestPredecessorNRemove("k", "j");
      // Set: {b, d, f, h}
      TestPredecessorNRemove("i", "h");
      // Set: {b, d, f}
      TestPredecessorNRemove("g", "f");
      // Set: {b, d}
      TestPredecessorNRemove("e", "d");
      // Set: {b}
      TestPredecessorNRemove("c", "b");
      // Set: {}
      TestIsEmpty(true, false);
      TestPredecessorNRemove("a", null);
      TestPrintContent("");
    }

    @Test
    @DisplayName("SuccessorNRemove chain")
    public void SuccessorNRemoveChain() {
      AddTest(13);
      NewEmptyContainer();
      TestInsert("b", true);
      TestInsert("d", true);
      TestInsert("f", true);
      TestInsert("h", true);
      TestInsert("j", true);
      // Set: {b, d, f, h, j}
      TestSuccessorNRemove("a", "b");
      // Set: {d, f, h, j}
      TestSuccessorNRemove("c", "d");
      // Set: {f, h, j}
      TestSuccessorNRemove("e", "f");
      // Set: {h, j}
      TestSuccessorNRemove("g", "h");
      // Set: {j}
      TestSuccessorNRemove("i", "j");
      // Set: {}
      TestIsEmpty(true, false);
      TestSuccessorNRemove("z", null);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Extreme values in ordered set")
    public void ExtremeValues() {
      AddTest(11);
      NewEmptyContainer();
      TestInsert("", true);
      TestInsert("a", true);
      String longStr = "z".repeat(10000);
      TestInsert(longStr, true);
      TestMin("");
      TestMax(longStr);
      TestPredecessor("a", "");
      TestSuccessor("a", longStr);
      TestSize(3, false);
      TestExists("", true);
      TestExists(longStr, true);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Large ordered set operations")
    public void LargeOrderedSetOperations() {
      AddTest(80);
      NewEmptyContainer();
      // Insert 50 strings: "item00" to "item49" (sorted lexicographically)
      for (int i = 0; i < 50; i++) {
        TestInsert(String.format("item%02d", i), true);
      }
      TestSize(50, false);
      TestMin("item00");
      TestMax("item49");
      TestPredecessor("item25", "item24");
      TestSuccessor("item25", "item26");
      for (int i = 0; i < 10; i++) {
        TestRemoveMin();
      }
      TestSize(40, false);
      TestMin("item10");
      for (int i = 0; i < 10; i++) {
        TestRemoveMax();
      }
      TestSize(30, false);
      TestMax("item39");
      TestMin("item10");
      for (int i = 10; i < 40; i++) {
        TestExists(String.format("item%02d", i), true);
      }
      TestPrintContent("");
    }

    @Test
    @DisplayName("Clear and rebuild ordered set")
    public void ClearAndRebuild() {
      AddTest(19);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      TestClear();
      TestIsEmpty(true, false);
      TestMin(null);
      TestMax(null);
      TestInsert("kiwi", true);
      TestInsert("fig", true);
      TestInsert("mango", true);
      TestMin("fig");
      TestMax("mango");
      TestSize(3, false);
      TestPredecessor("kiwi", "fig");
      TestSuccessor("kiwi", "mango");
      TestPredecessor("fig", null);
      TestSuccessor("fig", "kiwi");
      TestPredecessor("mango", "kiwi");
      TestSuccessor("mango", null);
    }

  }

  @Nested
  @DisplayName("WOrderedSet Construction and Equality tests")
  public class WOrderedSetConstructionAndEqualityTests {

    @Test
    @DisplayName("Construction from another container")
    public void ConstructionFromAnotherContainer() {
      AddTest(7);
      NewEmptyContainer();
      List<String> list = new LLList<>();
      list.Insert("date");
      list.Insert("banana");
      list.Insert("cherry");
      list.Insert("banana");
      list.Insert("apple");
      WOrderedSet<String> conFromList = new WOrderedSet<>(list);
      TestInsert("date", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      TestInsert("apple", true);
      TestIsEqual(conFromList, true);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Construction from empty container")
    public void ConstructionFromEmptyContainer() {
      AddTest(4);
      List<String> emptyList = new LLList<>();
      WOrderedSet<String> conFromEmpty = new WOrderedSet<>(emptyList);
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
      SortedChain<String> sortedChain = new LLSortedChain<>();
      sortedChain.Insert("elderberry");
      sortedChain.Insert("cherry");
      sortedChain.Insert("apple");
      sortedChain.Insert("date");
      sortedChain.Insert("banana");
      WOrderedSet<String> conFromChain = new WOrderedSet<>(sortedChain);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      TestInsert("date", true);
      TestInsert("elderberry", true);
      TestIsEqual(conFromChain, true);
      TestSize(5, false);
      TestMin("apple");
    }

    @Test
    @DisplayName("Equality with same elements different order insertion")
    public void EqualityDifferentInsertionOrder() {
      AddTest(6);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);

      WOrderedSet<String> other = (WOrderedSet<String>) GetNewEmptyContainer();
      other.Insert("cherry");
      other.Insert("apple");
      other.Insert("banana");
      TestIsEqual(other, true);
      TestSize(3, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Inequality with different sizes")
    public void InequalityDifferentSizes() {
      AddTest(5);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);

      WOrderedSet<String> smaller = (WOrderedSet<String>) GetNewEmptyContainer();
      smaller.Insert("apple");
      smaller.Insert("banana");
      TestIsEqual(smaller, false);
      TestSize(3, false);
    }

    @Test
    @DisplayName("Inequality with different elements")
    public void InequalityDifferentElements() {
      AddTest(5);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);

      WOrderedSet<String> different = (WOrderedSet<String>) GetNewEmptyContainer();
      different.Insert("apple");
      different.Insert("banana");
      different.Insert("date");
      TestIsEqual(different, false);
      TestSize(3, false);
    }

    @Test
    @DisplayName("Equality of two empty sets")
    public void EqualityEmptySets() {
      AddTest(3);
      NewEmptyContainer();
      WOrderedSet<String> other = (WOrderedSet<String>) GetNewEmptyContainer();
      TestIsEqual(other, true);
      TestIsEmpty(true, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Copy construction preserves elements")
    public void CopyConstructionPreservesElements() {
      AddTest(8);
      NewNonEmptyContainer();
      WOrderedSet<String> copy = new WOrderedSet<>(ThisContainer());
      TestIsEqual(copy, true);
      TestSize(5, false);
      TestMin("apple");
      TestMax("elderberry");
      TestExists("cherry", true);
      TestPrintContent("");
    }

  }

  @Nested
  @DisplayName("WOrderedSet Null Testing")
  public class WOrderedSetNullTesting {

    @Test
    @DisplayName("Insert null element")
    public void InsertNull() {
      AddTest(3);
      NewEmptyContainer();
      TestInsert(null, false);
      TestIsEmpty(true, false);
      TestSize(0, false);
    }

    @Test
    @DisplayName("Remove null element")
    public void RemoveNull() {
      AddTest(4);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestRemove(null, false);
      TestSize(1, false);
      TestExists("apple", true);
    }

    @Test
    @DisplayName("Exists with null")
    public void ExistsNull() {
      AddTest(3);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestExists(null, false);
      TestSize(1, false);
    }

    @Test
    @DisplayName("Predecessor with null")
    public void PredecessorNull() {
      AddTest(3);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestPredecessor(null, null);
      TestSize(1, false);
    }

    @Test
    @DisplayName("Successor with null")
    public void SuccessorNull() {
      AddTest(3);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestSuccessor(null, null);
      TestSize(1, false);
    }

    @Test
    @DisplayName("PredecessorNRemove with null")
    public void PredecessorNRemoveNull() {
      AddTest(4);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestPredecessorNRemove(null, null);
      TestSize(1, false);
      TestExists("apple", true);
    }

    @Test
    @DisplayName("SuccessorNRemove with null")
    public void SuccessorNRemoveNull() {
      AddTest(4);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestSuccessorNRemove(null, null);
      TestSize(1, false);
      TestExists("apple", true);
    }

    @Test
    @DisplayName("Min and Max on empty set return null")
    public void MinMaxEmptyReturnsNull() {
      AddTest(3);
      NewEmptyContainer();
      TestMin(null);
      TestMax(null);
      TestIsEmpty(true, false);
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
      AddTest(3);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestFoldForward((dat, acc) -> acc + dat, null, "nullapple");
      TestSize(1, false);
    }

    @Test
    @DisplayName("FoldBackward with null accumulator")
    public void FoldBackwardNullAccumulator() {
      AddTest(3);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestFoldBackward((dat, acc) -> acc + dat, null, "nullapple");
      TestSize(1, false);
    }

    @Test
    @DisplayName("IsEqual with null container")
    public void IsEqualNullContainer() {
      AddTest(3);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestIsEqual(null, false);
      TestSize(1, false);
    }

    @Test
    @DisplayName("Operations after inserting and removing maintain consistency")
    public void NullOperationsConsistency() {
      AddTest(10);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert(null, false);
      TestRemove(null, false);
      TestSize(1, false);
      TestExists("apple", true);
      TestExists(null, false);
      TestPredecessor(null, null);
      TestSuccessor(null, null);
      TestRemove("apple", true);
      TestIsEmpty(true, false);
    }

  }

  @Nested
  @DisplayName("WOrderedSet Comprehensive Method Combinations")
  public class WOrderedSetComprehensiveMethodCombinations {

    @Test
    @DisplayName("Insert, Exists, Min, Max cycle through all elements")
    public void InsertExistsMinMaxCycle() {
      AddTest(21);
      NewEmptyContainer();
      TestInsert("cherry", true);
      TestExists("cherry", true);
      TestMin("cherry");
      TestMax("cherry");
      TestInsert("apple", true);
      TestExists("apple", true);
      TestMin("apple");
      TestMax("cherry");
      TestInsert("elderberry", true);
      TestExists("elderberry", true);
      TestMin("apple");
      TestMax("elderberry");
      TestInsert("banana", true);
      TestExists("banana", true);
      TestMin("apple");
      TestMax("elderberry");
      TestInsert("date", true);
      TestExists("date", true);
      TestMin("apple");
      TestMax("elderberry");
      TestSize(5, false);
      TestIsEmpty(false, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("RemoveMin then verify with Predecessor and Successor")
    public void RemoveMinWithPredSucc() {
      AddTest(12);
      NewNonEmptyContainer();
      // Set: {apple, banana, cherry, date, elderberry}
      TestRemoveMin();
      // Set: {banana, cherry, date, elderberry}
      TestMin("banana");
      TestPredecessor("banana", null);
      TestSuccessor("banana", "cherry");
      TestRemoveMin();
      // Set: {cherry, date, elderberry}
      TestMin("cherry");
      TestPredecessor("cherry", null);
      TestSuccessor("cherry", "date");
      TestRemoveMin();
      // Set: {date, elderberry}
      TestMin("date");
      TestPredecessor("date", null);
      TestSuccessor("date", "elderberry");
      TestPrintContent("");
    }

    @Test
    @DisplayName("RemoveMax then verify with Predecessor and Successor")
    public void RemoveMaxWithPredSucc() {
      AddTest(12);
      NewNonEmptyContainer();
      // Set: {apple, banana, cherry, date, elderberry}
      TestRemoveMax();
      // Set: {apple, banana, cherry, date}
      TestMax("date");
      TestPredecessor("date", "cherry");
      TestSuccessor("date", null);
      TestRemoveMax();
      // Set: {apple, banana, cherry}
      TestMax("cherry");
      TestPredecessor("cherry", "banana");
      TestSuccessor("cherry", null);
      TestRemoveMax();
      // Set: {apple, banana}
      TestMax("banana");
      TestPredecessor("banana", "apple");
      TestSuccessor("banana", null);
      TestPrintContent("");
    }

    @Test
    @DisplayName("MinNRemove and MaxNRemove alternating with IsEqual")
    public void MinMaxNRemoveAlternatingWithIsEqual() {
      AddTest(15);
      NewNonEmptyContainer();
      // Set: {apple, banana, cherry, date, elderberry}
      WOrderedSet<String> expected = (WOrderedSet<String>) GetNewEmptyContainer();
      expected.Insert("apple");
      expected.Insert("banana");
      expected.Insert("cherry");
      expected.Insert("date");
      expected.Insert("elderberry");
      TestIsEqual(expected, true);
      TestMinNRemove("apple");
      expected.Remove("apple");
      TestIsEqual(expected, true);
      TestMaxNRemove("elderberry");
      expected.Remove("elderberry");
      TestIsEqual(expected, true);
      TestMinNRemove("banana");
      expected.Remove("banana");
      TestIsEqual(expected, true);
      TestMaxNRemove("date");
      expected.Remove("date");
      TestIsEqual(expected, true);
      TestSize(1, false);
      TestMinNRemove("cherry");
      TestIsEmpty(true, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("PredecessorNRemove chain with FoldForward verification")
    public void PredecessorNRemoveChainWithFold() {
      AddTest(10);
      NewNonEmptyContainer();
      // Set: {apple, banana, cherry, date, elderberry}
      TestPredecessorNRemove("elderberry", "date");
      // Set: {apple, banana, cherry, elderberry}
      TestFoldForward((dat, acc) -> acc + 1, 0, 4);
      TestPredecessorNRemove("cherry", "banana");
      // Set: {apple, cherry, elderberry}
      TestFoldForward((dat, acc) -> acc + 1, 0, 3);
      TestPredecessorNRemove("apple", null);
      // Set: {apple, cherry, elderberry}
      TestFoldForward((dat, acc) -> acc + 1, 0, 3);
      TestSize(3, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("SuccessorNRemove chain with FoldBackward verification")
    public void SuccessorNRemoveChainWithFold() {
      AddTest(10);
      NewNonEmptyContainer();
      // Set: {apple, banana, cherry, date, elderberry}
      TestSuccessorNRemove("apple", "banana");
      // Set: {apple, cherry, date, elderberry}
      TestFoldBackward((dat, acc) -> acc + 1, 0, 4);
      TestSuccessorNRemove("cherry", "date");
      // Set: {apple, cherry, elderberry}
      TestFoldBackward((dat, acc) -> acc + 1, 0, 3);
      TestSuccessorNRemove("elderberry", null);
      // Set: {apple, cherry, elderberry}
      TestFoldBackward((dat, acc) -> acc + 1, 0, 3);
      TestSize(3, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Filter then verify with Exists and Size")
    public void FilterWithExistsAndSize() {
      AddTest(10);
      NewNonEmptyContainer();
      // Set: {apple, banana, cherry, date, elderberry}
      // Keep only strings with length >= 6
      TestFilter(dat -> dat.length() >= 6);
      TestSize(3, false);
      TestExists("banana", true);
      TestExists("cherry", true);
      TestExists("elderberry", true);
      TestExists("apple", false);
      TestExists("date", false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Union with verification using Exists, Size, Min, Max")
    public void UnionWithFullVerification() {
      AddTest(14);
      NewNonEmptyContainer();
      // Set: {apple, banana, cherry, date, elderberry}
      WOrderedSet<String> other = (WOrderedSet<String>) GetNewEmptyContainer();
      other.Insert("fig");
      other.Insert("grape");
      other.Insert("cherry");
      TestUnion(other);
      // Set: {apple, banana, cherry, date, elderberry, fig, grape}
      TestSize(7, false);
      TestExists("fig", true);
      TestExists("grape", true);
      TestExists("cherry", true);
      TestMin("apple");
      TestMax("grape");
      TestPredecessor("fig", "elderberry");
      TestSuccessor("elderberry", "fig");
      TestPrintContent("");
    }

    @Test
    @DisplayName("Intersection with verification using Exists, Size, Min, Max")
    public void IntersectionWithFullVerification() {
      AddTest(12);
      NewNonEmptyContainer();
      // Set: {apple, banana, cherry, date, elderberry}
      WOrderedSet<String> other = (WOrderedSet<String>) GetNewEmptyContainer();
      other.Insert("banana");
      other.Insert("date");
      other.Insert("fig");
      TestIntersection(other);
      // Set: {banana, date}
      TestSize(2, false);
      TestExists("banana", true);
      TestExists("date", true);
      TestExists("apple", false);
      TestExists("cherry", false);
      TestExists("elderberry", false);
      TestMin("banana");
      TestMax("date");
      TestPrintContent("");
    }

    @Test
    @DisplayName("Difference with verification using Exists, Size, Min, Max")
    public void DifferenceWithFullVerification() {
      AddTest(12);
      NewNonEmptyContainer();
      // Set: {apple, banana, cherry, date, elderberry}
      WOrderedSet<String> other = (WOrderedSet<String>) GetNewEmptyContainer();
      other.Insert("banana");
      other.Insert("date");
      other.Insert("fig");
      TestDifference(other);
      // Set: {apple, cherry, elderberry}
      TestSize(3, false);
      TestExists("apple", true);
      TestExists("cherry", true);
      TestExists("elderberry", true);
      TestExists("banana", false);
      TestExists("date", false);
      TestMin("apple");
      TestMax("elderberry");
      TestPrintContent("");
    }

    @Test
    @DisplayName("InsertAll then RemoveAll with verification")
    public void InsertAllThenRemoveAll() {
      AddTest(12);
      NewEmptyContainer();
      WOrderedSet<String> items = (WOrderedSet<String>) GetNewEmptyContainer();
      items.Insert("apple");
      items.Insert("banana");
      items.Insert("cherry");
      TestInsertAll(items, true);
      TestSize(3, false);
      TestExists("apple", true);
      TestExists("banana", true);
      TestExists("cherry", true);
      TestRemoveAll(items, true);
      TestIsEmpty(true, false);
      TestExists("apple", false);
      TestExists("banana", false);
      TestExists("cherry", false);
    }

    @Test
    @DisplayName("InsertSome then RemoveSome with verification")
    public void InsertSomeThenRemoveSome() {
      AddTest(10);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      WOrderedSet<String> toInsert = (WOrderedSet<String>) GetNewEmptyContainer();
      toInsert.Insert("banana");
      toInsert.Insert("cherry");
      toInsert.Insert("date");
      TestInsertSome(toInsert, true);
      TestSize(4, false);
      WOrderedSet<String> toRemove = (WOrderedSet<String>) GetNewEmptyContainer();
      toRemove.Insert("banana");
      toRemove.Insert("elderberry");
      TestRemoveSome(toRemove, true);
      TestSize(3, false);
      TestExists("banana", false);
      TestExists("apple", true);
      TestPrintContent("");
    }

    @Test
    @DisplayName("TraverseForward and TraverseBackward with predicates")
    public void TraverseForwardAndBackward() {
      AddTest(4);
      NewNonEmptyContainer();
      TestFoldForward((dat, acc) -> acc + 1, 0, 5);
      TestFoldBackward((dat, acc) -> acc + 1, 0, 5);
      TestFoldForward((dat, acc) -> acc + dat.length(), 0, 31);
      TestPrintContent("");
    }

    @Test
    @DisplayName("FIterator and BIterator usage")
    public void FIteratorAndBIteratorUsage() {
      AddTest(8);
      NewEmptyContainer();
      TestInsert("banana", true);
      TestInsert("date", true);
      TestInsert("fig", true);
      TestInsert("honey", true);
      TestInsert("kiwi", true);
      TestFIterator();
      TestBIterator();
      TestSize(5, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Clear then rebuild with all operations")
    public void ClearThenRebuildWithAllOps() {
      AddTest(14);
      NewNonEmptyContainer();
      TestClear();
      TestIsEmpty(true, false);
      TestInsert("fig", true);
      TestInsert("grape", true);
      TestInsert("honey", true);
      TestSize(3, false);
      TestMin("fig");
      TestMax("honey");
      TestPredecessor("grape", "fig");
      TestSuccessor("grape", "honey");
      TestExists("fig", true);
      TestExists("grape", true);
      TestExists("honey", true);
      TestPrintContent("");
    }

    @Test
    @DisplayName("RemovePredecessor and RemoveSuccessor combinations")
    public void RemovePredecessorAndSuccessorCombos() {
      AddTest(12);
      NewNonEmptyContainer();
      // Set: {apple, banana, cherry, date, elderberry}
      TestPredecessorNRemove("cherry", "banana");
      // Set: {apple, cherry, date, elderberry}
      TestSuccessorNRemove("cherry", "date");
      // Set: {apple, cherry, elderberry}
      TestSize(3, false);
      TestMin("apple");
      TestMax("elderberry");
      TestPredecessor("cherry", "apple");
      TestSuccessor("cherry", "elderberry");
      TestPredecessorNRemove("apple", null);
      TestSuccessorNRemove("elderberry", null);
      TestSize(3, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Union, Intersection, Difference chain")
    public void SetOperationsChain() {
      AddTest(14);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      WOrderedSet<String> s1 = (WOrderedSet<String>) GetNewEmptyContainer();
      s1.Insert("cherry");
      s1.Insert("date");
      WOrderedSet<String> s2 = (WOrderedSet<String>) GetNewEmptyContainer();
      s2.Insert("apple");
      s2.Insert("date");
      TestUnion(s1);
      // Set: {apple, banana, cherry, date}
      TestSize(4, false);
      TestIntersection(s2);
      // Set: {apple, date}
      TestSize(2, false);
      TestExists("apple", true);
      TestExists("date", true);
      TestExists("banana", false);
      TestExists("cherry", false);
      TestDifference(s2);
      TestIsEmpty(true, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("All Remove operations exhaustively")
    public void AllRemoveOperationsExhaustively() {
      AddTest(15);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      TestInsert("date", true);
      TestInsert("elderberry", true);
      TestRemove("cherry", true);
      // Set: {apple, banana, date, elderberry}
      TestRemoveMin();
      // Set: {banana, date, elderberry}
      TestRemoveMax();
      // Set: {banana, date}
      TestMinNRemove("banana");
      // Set: {date}
      TestMaxNRemove("date");
      // Set: {}
      TestIsEmpty(true, false);
      TestInsert("fig", true);
      TestInsert("grape", true);
      TestInsert("honey", true);
      TestPredecessorNRemove("grape", "fig");
      // Set: {grape, honey}
      TestSuccessorNRemove("grape", "honey");
      // Set: {grape}
      TestSize(1, false);
    }

    @Test
    @DisplayName("Filter removes all elements")
    public void FilterRemovesAll() {
      AddTest(4);
      NewNonEmptyContainer();
      TestFilter(dat -> false);
      TestIsEmpty(true, false);
      TestSize(0, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Filter keeps all elements")
    public void FilterKeepsAll() {
      AddTest(4);
      NewNonEmptyContainer();
      TestFilter(dat -> true);
      TestSize(5, false);
      TestExists("apple", true);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Multiple FoldForward and FoldBackward operations")
    public void MultipleFoldOperations() {
      AddTest(5);
      NewNonEmptyContainer();
      // Set: {apple, banana, cherry, date, elderberry}
      TestFoldForward((dat, acc) -> acc + 1, 0, 5);
      TestFoldBackward((dat, acc) -> acc + 1, 0, 5);
      // Longest string length
      TestFoldForward((dat, acc) -> dat.length() > acc ? dat.length() : acc, 0, 10);
      // Shortest string length
      TestFoldBackward((dat, acc) -> dat.length() < acc ? dat.length() : acc, Integer.MAX_VALUE, 4);
      TestPrintContent("");
    }

    @Test
    @DisplayName("IsEqual after various modifications")
    public void IsEqualAfterModifications() {
      AddTest(20);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);

      WOrderedSet<String> other = (WOrderedSet<String>) GetNewEmptyContainer();
      other.Insert("apple");
      other.Insert("banana");
      other.Insert("cherry");
      TestIsEqual(other, true);

      TestInsert("date", true);
      TestIsEqual(other, false);

      other.Insert("date");
      TestIsEqual(other, true);

      TestRemove("banana", true);
      TestIsEqual(other, false);

      other.Remove("banana");
      TestIsEqual(other, true);

      TestClear();
      other.Clear();
      TestIsEqual(other, true);
      TestIsEmpty(true, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Empty set all operations")
    public void EmptySetAllOperations() {
      AddTest(16);
      NewEmptyContainer();
      TestIsEmpty(true, false);
      TestSize(0, false);
      TestMin(null);
      TestMax(null);
      TestExists("apple", false);
      TestRemove("apple", false);
      TestPredecessor("apple", null);
      TestSuccessor("apple", null);
      TestPredecessorNRemove("apple", null);
      TestSuccessorNRemove("apple", null);
      TestFilter(dat -> true);
      TestFIterator();
      TestBIterator();
      TestFoldForward((dat, acc) -> acc + dat, "", "");
      TestFoldBackward((dat, acc) -> acc + dat, "", "");
      TestPrintContent("");
    }

    @Test
    @DisplayName("Single element all operations")
    public void SingleElementAllOperations() {
      AddTest(25);
      NewEmptyContainer();
      TestInsert("mango", true);
      TestIsEmpty(false, false);
      TestSize(1, false);
      TestMin("mango");
      TestMax("mango");
      TestExists("mango", true);
      TestExists("apple", false);
      TestPredecessor("mango", null);
      TestSuccessor("mango", null);
      TestPredecessor("zebra", "mango");
      TestSuccessor("aaa", "mango");
      TestFoldForward((dat, acc) -> acc + dat, "", "mango");
      TestFoldBackward((dat, acc) -> acc + dat, "", "mango");
      TestFIterator();
      TestBIterator();
      TestFilter(dat -> true);
      TestSize(1, false);
      TestRemove("mango", true);
      TestIsEmpty(true, false);
      TestMin(null);
      TestMax(null);

      WOrderedSet<String> other = (WOrderedSet<String>) GetNewEmptyContainer();
      other.Insert("mango");
      TestIsEqual(other, false);
    }

    @Test
    @DisplayName("Predecessor and Successor boundary cases")
    public void PredSuccBoundaryCases() {
      AddTest(14);
      NewNonEmptyContainer();
      // Set: {apple, banana, cherry, date, elderberry}
      TestPredecessor("apple", null);
      TestSuccessor("elderberry", null);
      TestPredecessor("aaa", null);
      TestSuccessor("zzz", null);
      TestPredecessor("banana", "apple");
      TestSuccessor("date", "elderberry");
      TestPredecessor("cherry", "banana");
      TestSuccessor("cherry", "date");
      // Non-existent values
      TestPredecessor("coconut", "cherry");
      TestSuccessor("coconut", "date");
      TestPredecessor("dragonfruit", "date");
      TestSuccessor("dragonfruit", "elderberry");
      TestSize(5, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("InsertAll with duplicates then Filter")
    public void InsertAllWithDuplicatesThenFilter() {
      AddTest(8);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      WOrderedSet<String> dupes = (WOrderedSet<String>) GetNewEmptyContainer();
      dupes.Insert("apple");
      dupes.Insert("cherry");
      TestInsertAll(dupes, false);
      TestSize(3, false);
      TestFilter(dat -> !dat.equals("apple"));
      TestSize(2, false);
      TestExists("apple", false);
      TestExists("banana", true);
      TestExists("cherry", true);
    }

    @Test
    @DisplayName("RemoveAll non-existent elements")
    public void RemoveAllNonExistent() {
      AddTest(6);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      WOrderedSet<String> nonExistent = (WOrderedSet<String>) GetNewEmptyContainer();
      nonExistent.Insert("cherry");
      nonExistent.Insert("date");
      TestRemoveAll(nonExistent, false);
      TestSize(2, false);
      TestExists("apple", true);
      TestExists("banana", true);
    }

    @Test
    @DisplayName("Complex chain: Insert, Union, Filter, Remove, Fold")
    public void ComplexChainOperations() {
      AddTest(14);
      NewEmptyContainer();
      TestInsert("alpha", true);
      TestInsert("bravo", true);
      TestInsert("charlie", true);
      WOrderedSet<String> extras = (WOrderedSet<String>) GetNewEmptyContainer();
      extras.Insert("delta");
      extras.Insert("echo");
      TestUnion(extras);
      TestSize(5, false);
      TestFilter(dat -> dat.length() >= 5);
      TestSize(4, false);
      TestRemove("bravo", true);
      TestSize(3, false);
      TestFoldForward((dat, acc) -> acc + 1, 0, 3);
      TestExists("charlie", true);
      TestExists("bravo", false);
      TestExists("alpha", true);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Interleaved Insert and Remove with Exists checks")
    public void InterleavedInsertRemoveWithExists() {
      AddTest(16);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestExists("apple", true);
      TestInsert("banana", true);
      TestRemove("apple", true);
      TestExists("apple", false);
      TestInsert("cherry", true);
      TestExists("banana", true);
      TestRemove("banana", true);
      TestInsert("date", true);
      TestExists("cherry", true);
      TestExists("date", true);
      TestSize(2, false);
      TestRemove("cherry", true);
      TestRemove("date", true);
      TestIsEmpty(true, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Union with self then Intersection with self")
    public void UnionAndIntersectionWithSelf() {
      AddTest(8);
      NewNonEmptyContainer();
      TestUnion(ThisContainer());
      TestSize(5, false);
      TestIntersection(ThisContainer());
      TestSize(5, false);
      TestExists("apple", true);
      TestExists("banana", true);
      TestExists("cherry", true);
      TestExists("date", true);
      TestExists("elderberry", true);
    }

    @Test
    @DisplayName("Build, verify, clear, rebuild multiple times")
    public void BuildVerifyClearRebuildCycle() {
      AddTest(14);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestSize(2, false);
      TestClear();
      TestIsEmpty(true, false);
      TestInsert("cherry", true);
      TestInsert("date", true);
      TestInsert("elderberry", true);
      TestSize(3, false);
      TestClear();
      TestIsEmpty(true, false);
      TestInsert("fig", true);
      TestSize(1, false);
      TestExists("fig", true);
      TestExists("apple", false);
    }

  }


}