package zapsdtest.simpletest.apsd.classes.containers.collections.concretecollections.generic;

import org.junit.jupiter.api.*;

import apsd.classes.containers.collections.concretecollections.LLList;
import apsd.classes.containers.collections.concretecollections.LLSortedChain;
import apsd.classes.utilities.Natural;

abstract public class XSortedChainSTest extends XSortedChainTest<String> {

  @Override
  public void NewNonEmptyContainer() {
    AddTest(11);
    NewEmptyContainer();
    TestInsert("date", true);
    TestInsert("apple", true);
    TestInsert("elderberry", true);
    TestInsert("kiwi", true);
    TestInsert("cherry", true);
    TestInsert("banana", true);
    TestInsert("apple", true);
    TestInsert("elderberry", true);
    TestInsert("banana", true);
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
      TestGetFirst("", true);
      TestGetLast("", true);
      TestGetAt(Natural.Of(1), "", true);
      TestExists("apple", false);
      TestPrintContent("");
      TestFoldBackward((dat, acc) -> acc + dat, "", "");
      TestRemove("apple", false);
      TestRemoveFirst();
      TestRemoveLast();
    }

    @Test
    @DisplayName("Check starting from a NonEmpty SortedChain")
    public void NonEmpty() {
      AddTest(20);
      NewNonEmptyContainer();
      // Sorted: [apple, apple, banana, banana, cherry, date, elderberry, elderberry, kiwi]
      TestGetFirst("apple", false);
      TestGetLast("kiwi", false);
      TestGetAt(Natural.Of(4), "cherry", false);
      TestExists("date", true);
      TestPrintContent("");
      TestFoldForward((dat, acc) -> acc + "," + dat, "", ",apple,apple,banana,banana,cherry,date,elderberry,elderberry,kiwi");
      TestFoldBackward((dat, acc) -> acc + "," + dat, "", ",kiwi,elderberry,elderberry,date,cherry,banana,banana,apple,apple");
      TestRemoveFirst();
      // [apple, banana, banana, cherry, date, elderberry, elderberry, kiwi]
      TestFoldForward((dat, acc) -> acc + 1, 0, 8);
      TestRemoveLast();
      // [apple, banana, banana, cherry, date, elderberry, elderberry]
      TestFoldForward((dat, acc) -> acc + 1, 0, 7);
      TestAtNRemove(Natural.Of(5), "elderberry", false);
      // [apple, banana, banana, cherry, date, elderberry]
      TestFoldBackward((dat, acc) -> acc + 1, 0, 6);
      TestSize(6, false);
      TestInsert("coconut", true);
      TestPrintContent("");
      TestSuccessor("kiwi", null);
      TestFoldBackward((dat, acc) -> acc + 1, 0, 7);
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
      TestInsert("elderberry", true);
      TestInsert("cherry", true);
      TestInsert("fig", true);
      TestInsert("apple", true);
      TestInsert("kiwi", true);
      TestGetFirst("apple", false);
      TestGetLast("kiwi", false);
      TestGetAt(Natural.ZERO, "apple", false);
      TestGetAt(Natural.Of(1), "cherry", false);
      TestGetAt(Natural.Of(2), "elderberry", false);
      TestGetAt(Natural.Of(3), "fig", false);
      TestGetAt(Natural.Of(4), "kiwi", false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("All duplicates")
    public void AllDuplicates() {
      AddTest(11);
      NewEmptyContainer();
      TestInsert("hello", true);
      TestInsert("hello", true);
      TestInsert("hello", true);
      TestInsert("hello", true);
      TestSize(4, false);
      TestGetFirst("hello", false);
      TestGetLast("hello", false);
      TestExists("hello", true);
      TestExists("world", false);
      TestFoldForward((dat, acc) -> acc + dat, "", "hellohellohellohello");
      TestPrintContent("");
    }

    @Test
    @DisplayName("Remove all occurrences of duplicate")
    public void RemoveAllDuplicates() {
      AddTest(13);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("cherry", true);
      TestInsert("cherry", true);
      TestInsert("cherry", true);
      TestInsert("fig", true);
      TestSize(5, false);
      TestRemove("cherry", true);
      TestExists("cherry", true);
      TestRemove("cherry", true);
      TestExists("cherry", true);
      TestRemove("cherry", true);
      TestExists("cherry", false);
      TestSize(2, false);
    }

    @Test
    @DisplayName("Single element operations")
    public void SingleElement() {
      AddTest(10);
      NewEmptyContainer();
      TestInsert("mango", true);
      TestSize(1, false);
      TestGetFirst("mango", false);
      TestGetLast("mango", false);
      TestGetAt(Natural.ZERO, "mango", false);
      TestExists("mango", true);
      TestFoldForward((dat, acc) -> acc + dat, "", "mango");
      TestRemoveFirst();
      TestSize(0, false);
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Remove first and last alternating")
    public void RemoveAlternating() {
      AddTest(16);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      TestInsert("date", true);
      TestInsert("elderberry", true);
      TestRemoveFirst();
      TestGetFirst("banana", false);
      TestRemoveLast();
      TestGetLast("date", false);
      TestRemoveFirst();
      TestGetFirst("cherry", false);
      TestRemoveLast();
      TestGetLast("cherry", false);
      TestSize(1, false);
      TestRemoveFirst();
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Strings with various cases sorting")
    public void StringCaseSorting() {
      AddTest(11);
      NewEmptyContainer();
      TestInsert("banana", true);
      TestInsert("Apple", true);
      TestInsert("cherry", true);
      TestInsert("BANANA", true);
      TestInsert("apple", true);
      // Lexicographic: uppercase letters come before lowercase
      TestGetFirst("Apple", false);
      TestGetLast("cherry", false);
      TestGetAt(Natural.Of(2), "apple", false);
      TestFoldForward((dat, acc) -> acc + 1, 0, 5);
      TestPrintContent("");
      TestSize(5, false);
    }

    @Test
    @DisplayName("Insert at boundaries")
    public void InsertAtBoundaries() {
      AddTest(11);
      NewEmptyContainer();
      TestInsert("mango", true);
      TestInsert("watermelon", true);
      TestInsert("apple", true);
      TestGetFirst("apple", false);
      TestGetLast("watermelon", false);
      TestInsert("AAA", true);
      TestGetFirst("AAA", false);
      TestInsert("zzz", true);
      TestGetLast("zzz", false);
      TestSize(5, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Large sequence of inserts")
    public void LargeSequence() {
      AddTest(56);
      NewEmptyContainer();
      for (int i = 50; i >= 1; i--) {
        TestInsert("item" + String.format("%02d", i), true);
      }
      TestSize(50, false);
      TestGetFirst("item01", false);
      TestGetLast("item50", false);
      TestGetAt(Natural.Of(24), "item25", false);
      TestFoldForward((dat, acc) -> acc + 1, 0, 50);
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
      TestInsert("apple", true);
      TestInsert("cherry", true);
      TestInsert("banana", true);
      TestInsert("apple", true);
      TestInsert("cherry", true);
      TestInsert("banana", true);
      TestInsert("apple", true);
      TestSize(7, false);
      TestGetFirst("apple", false);
      TestGetLast("cherry", false);
      TestGetAt(Natural.Of(0), "apple", false);
      TestGetAt(Natural.Of(1), "apple", false);
      TestGetAt(Natural.Of(2), "apple", false);
      TestGetAt(Natural.Of(3), "banana", false);
      TestGetAt(Natural.Of(4), "banana", false);
      TestGetAt(Natural.Of(5), "cherry", false);
      TestGetAt(Natural.Of(6), "cherry", false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Remove from middle")
    public void RemoveFromMiddle() {
      AddTest(13);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      TestInsert("date", true);
      TestInsert("elderberry", true);
      TestAtNRemove(Natural.Of(2), "cherry", false);
      TestSize(4, false);
      TestGetAt(Natural.Of(2), "date", false);
      TestAtNRemove(Natural.Of(1), "banana", false);
      TestSize(3, false);
      TestGetAt(Natural.Of(1), "date", false);
      TestFoldForward((dat, acc) -> acc + "," + dat, "", ",apple,date,elderberry");
      TestPrintContent("");
    }

    @Test
    @DisplayName("Predecessor and successor find")
    public void PredSuccFind() {
      AddTest(16);
      NewEmptyContainer();
      TestInsert("banana", true);
      TestInsert("date", true);
      TestInsert("fig", true);
      TestInsert("kiwi", true);
      TestInsert("mango", true);
      TestPredecessor("elderberry", "date");
      TestPredecessor("fig", "date");
      TestPredecessor("banana", null);
      TestPredecessor("apple", null);
      TestSuccessor("elderberry", "fig");
      TestSuccessor("fig", "kiwi");
      TestSuccessor("mango", null);
      TestSuccessor("zebra", null);
      TestPredecessor("grape", "fig");
      TestSuccessor("grape", "kiwi");
      TestPrintContent("");
    }

    @Test
    @DisplayName("Remove until empty then refill")
    public void RemoveUntilEmptyThenRefill() {
      AddTest(18);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      TestRemoveFirst();
      TestRemoveFirst();
      TestRemoveFirst();
      TestIsEmpty(true, false);
      TestInsert("mango", true);
      TestInsert("watermelon", true);
      TestInsert("orange", true);
      TestSize(3, false);
      TestGetFirst("mango", false);
      TestGetAt(Natural.Of(1), "orange", false);
      TestGetLast("watermelon", false);
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
      TestInsert("zzzzz", true);
      TestInsert("AAAAA", true);
      TestInsert("mmmm", true);
      TestInsert("MMMMM", true);
      TestInsert("aaaaa", true);
      TestGetFirst("AAAAA", false);
      TestGetLast("zzzzz", false);
      TestGetAt(Natural.Of(2), "aaaaa", false);
      TestExists("zzzzz", true);
      TestExists("AAAAA", true);
      TestSize(5, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Consecutive removes")
    public void ConsecutiveRemoves() {
      AddTest(15);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      TestInsert("date", true);
      TestInsert("elderberry", true);
      TestRemove("cherry", true);
      TestRemove("apple", true);
      TestRemove("elderberry", true);
      TestSize(2, false);
      TestGetFirst("banana", false);
      TestGetLast("date", false);
      TestRemove("banana", true);
      TestRemove("date", true);
      TestIsEmpty(true, false);
      TestRemove("apple", false);
    }

    @Test
    @DisplayName("Many duplicates at boundaries")
    public void ManyDuplicatesAtBoundaries() {
      AddTest(17);
      NewEmptyContainer();
      TestInsert("aaa", true);
      TestInsert("aaa", true);
      TestInsert("aaa", true);
      TestInsert("zzz", true);
      TestInsert("zzz", true);
      TestInsert("zzz", true);
      TestInsert("mmm", true);
      TestSize(7, false);
      TestGetFirst("aaa", false);
      TestGetLast("zzz", false);
      TestRemoveFirst();
      TestGetFirst("aaa", false);
      TestRemoveFirst();
      TestGetFirst("aaa", false);
      TestRemoveLast();
      TestGetLast("zzz", false);
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
      LLList<String> list = new LLList<>();
      list.Insert("date");
      list.Insert("banana");
      list.Insert("cherry");
      list.Insert("apple");
      LLSortedChain<String> conFromList = new LLSortedChain<>(list);
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      TestInsert("date", true);
      TestIsEqual(conFromList, true);
      TestSize(4, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Construction from empty container")
    public void ConstructionFromEmptyContainer() {
      AddTest(4);
      LLList<String> emptyList = new LLList<>();
      LLSortedChain<String> conFromEmpty = new LLSortedChain<>(emptyList);
      NewEmptyContainer();
      TestIsEqual(conFromEmpty, true);
      TestSize(0, false);
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Construction from unsorted list produces sorted chain")
    public void ConstructionFromUnsortedList() {
      AddTest(8);
      LLList<String> unsortedList = new LLList<>();
      unsortedList.InsertLast("elderberry");
      unsortedList.InsertLast("apple");
      unsortedList.InsertLast("date");
      unsortedList.InsertLast("banana");
      unsortedList.InsertLast("cherry");
      LLSortedChain<String> conFromList = new LLSortedChain<>(unsortedList);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      TestInsert("date", true);
      TestInsert("elderberry", true);
      TestIsEqual(conFromList, true);
      TestGetFirst("apple", false);
      TestGetLast("elderberry", false);
    }

    @Test
    @DisplayName("Equality with same elements")
    public void EqualitySameElements() {
      AddTest(6);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      
      LLSortedChain<String> other = new LLSortedChain<>();
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
      
      LLSortedChain<String> smaller = new LLSortedChain<>();
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
      
      LLSortedChain<String> different = new LLSortedChain<>();
      different.Insert("apple");
      different.Insert("banana");
      different.Insert("date");
      TestIsEqual(different, false);
      TestSize(3, false);
    }

    @Test
    @DisplayName("Equality of two empty chains")
    public void EqualityEmptyChains() {
      AddTest(3);
      NewEmptyContainer();
      LLSortedChain<String> other = new LLSortedChain<>();
      TestIsEqual(other, true);
      TestSize(0, false);
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Copy construction preserves elements")
    public void CopyConstructionPreservesElements() {
      AddTest(7);
      LLSortedChain<String> original = new LLSortedChain<>();
      original.Insert("cherry");
      original.Insert("apple");
      original.Insert("banana");
      LLSortedChain<String> copy = new LLSortedChain<>(original);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      TestIsEqual(original, true);
      TestIsEqual(copy, true);
      TestSize(3, false);
    }

    @Test
    @DisplayName("Equality with duplicates")
    public void EqualityWithDuplicates() {
      AddTest(6);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("apple", true);
      TestInsert("banana", true);
      
      LLSortedChain<String> other = new LLSortedChain<>();
      other.Insert("banana");
      other.Insert("apple");
      other.Insert("banana");
      other.Insert("apple");
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
      TestInsert("apple", true);
      TestInsert(null, false);
      TestSize(1, false);
      TestExists(null, false);
    }

    @Test
    @DisplayName("Remove null element")
    public void RemoveNull() {
      AddTest(4);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestRemove(null, false);
      TestSize(2, false);
    }

    @Test
    @DisplayName("Exists with null")
    public void ExistsNull() {
      AddTest(4);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestExists(null, false);
      TestSize(2, false);
    }

    @Test
    @DisplayName("GetAt with null Natural")
    public void GetAtNullNatural() {
      AddTest(4);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestGetAt(null, "", true);
      TestSize(2, false);
    }

    @Test
    @DisplayName("AtNRemove with null Natural")
    public void AtNRemoveNullNatural() {
      AddTest(4);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestAtNRemove(null, "", true);
      TestSize(2, false);
    }

    @Test
    @DisplayName("IsEqual with null container")
    public void IsEqualNullContainer() {
      AddTest(3);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestIsEqual(null, false);
    }

    @Test
    @DisplayName("FoldForward with null accumulator")
    public void FoldForwardNullAccumulator() {
      AddTest(4);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      TestFoldForward((dat, acc) -> dat, null, "cherry");
    }

    @Test
    @DisplayName("FoldBackward with null accumulator")
    public void FoldBackwardNullAccumulator() {
      AddTest(4);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      TestFoldBackward((dat, acc) -> dat, null, "apple");
    }

    @Test
    @DisplayName("Filter with null predicate")
    public void FilterWithNullPredicate() {
      AddTest(5);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      TestFilter(null);
      TestSize(3, false);
    }

    @Test
    @DisplayName("Operations with null maintain consistency")
    public void NullOperationsConsistency() {
      AddTest(8);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert(null, false);
      TestInsert("banana", true);
      TestRemove(null, false);
      TestInsert("cherry", true);
      TestExists(null, false);
      TestSize(3, false);
      TestFoldForward((dat, acc) -> acc + dat, "", "applebananacherry");
    }

    @Test
    @DisplayName("Empty chain null operations")
    public void EmptyChainNullOperations() {
      AddTest(6);
      NewEmptyContainer();
      TestInsert(null, false);
      TestRemove(null, false);
      TestExists(null, false);
      TestGetAt(null, "", true);
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
      TestInsert("elderberry", true);
      TestInsert("cherry", true);
      TestInsert("fig", true);
      TestInsert("apple", true);
      TestInsert("kiwi", true);
      TestInsert("banana", true);
      TestInsert("grape", true);
      TestInsert("date", true);
      TestInsert("honeydew", true);
      TestSize(9, false);
      TestGetFirst("apple", false);
      TestGetLast("kiwi", false);
      TestGetAt(Natural.ZERO, "apple", false);
      TestGetAt(Natural.Of(4), "elderberry", false);
      TestGetAt(Natural.Of(8), "kiwi", false);
      TestSearch("apple", Natural.ZERO);
      TestSearch("elderberry", Natural.Of(4));
      TestSearch("kiwi", Natural.Of(8));
      TestFoldForward((dat, acc) -> acc + 1, 0, 9);
      TestFoldBackward((dat, acc) -> acc + 1, 0, 9);
      TestForwardIteration(false, false);
      TestBackwardIteration(false, false);
    }

    @Test
    @DisplayName("GetAt, GetFirst, GetLast comprehensive")
    public void GetAtGetFirstGetLastComprehensive() {
      AddTest(18);
      NewEmptyContainer();
      TestInsert("banana", true);
      TestInsert("date", true);
      TestInsert("fig", true);
      TestInsert("kiwi", true);
      TestInsert("mango", true);
      TestSize(5, false);
      TestGetFirst("banana", false);
      TestGetLast("mango", false);
      TestGetAt(Natural.ZERO, "banana", false);
      TestGetAt(Natural.Of(1), "date", false);
      TestGetAt(Natural.Of(2), "fig", false);
      TestGetAt(Natural.Of(3), "kiwi", false);
      TestGetAt(Natural.Of(4), "mango", false);
      TestExists("fig", true);
      TestExists("elderberry", false);
      TestFoldForward((dat, acc) -> acc + "," + dat, "", ",banana,date,fig,kiwi,mango");
      TestFIterator();
      TestBIterator();
    }

    @Test
    @DisplayName("RemoveFirst, RemoveLast chain")
    public void RemoveFirstRemoveLastChain() {
      AddTest(20);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      TestInsert("date", true);
      TestInsert("elderberry", true);
      TestSize(5, false);
      TestRemoveFirst();
      TestGetFirst("banana", false);
      TestSize(4, false);
      TestRemoveLast();
      TestGetLast("date", false);
      TestSize(3, false);
      TestRemoveFirst();
      TestRemoveLast();
      TestSize(1, false);
      TestGetFirst("cherry", false);
      TestGetLast("cherry", false);
      TestRemoveFirst();
      TestIsEmpty(true, false);
      TestSize(0, false);
    }

    @Test
    @DisplayName("FirstNRemove and LastNRemove chain")
    public void FirstNRemoveLastNRemoveChain() {
      AddTest(20);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      TestInsert("date", true);
      TestInsert("elderberry", true);
      TestSize(5, false);
      TestFirstNRemove("apple");
      TestGetFirst("banana", false);
      TestSize(4, false);
      TestLastNRemove("elderberry");
      TestGetLast("date", false);
      TestSize(3, false);
      TestFirstNRemove("banana");
      TestLastNRemove("date");
      TestSize(1, false);
      TestGetFirst("cherry", false);
      TestGetLast("cherry", false);
      TestFirstNRemove("cherry");
      TestIsEmpty(true, false);
      TestSize(0, false);
    }

    @Test
    @DisplayName("AtNRemove comprehensive test")
    public void AtNRemoveComprehensive() {
      AddTest(18);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      TestInsert("date", true);
      TestInsert("elderberry", true);
      TestSize(5, false);
      TestAtNRemove(Natural.Of(2), "cherry", false);
      TestSize(4, false);
      TestGetAt(Natural.Of(2), "date", false);
      TestAtNRemove(Natural.ZERO, "apple", false);
      TestGetFirst("banana", false);
      TestSize(3, false);
      TestAtNRemove(Natural.Of(2), "elderberry", false);
      TestGetLast("date", false);
      TestSize(2, false);
      TestFoldForward((dat, acc) -> acc + "," + dat, "", ",banana,date");
      TestExists("banana", true);
      TestExists("date", true);
    }

    @Test
    @DisplayName("Remove and RemoveOccurrences combined")
    public void RemoveAndRemoveOccurrences() {
      AddTest(18);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      TestInsert("banana", true);
      TestInsert("date", true);
      TestSize(6, false);
      TestRemove("banana", true);
      TestSize(5, false);
      TestExists("banana", true);
      TestRemoveOccurrences("banana");
      TestExists("banana", false);
      TestSize(3, false);
      TestRemove("banana", false);
      TestExists("apple", true);
      TestExists("cherry", true);
      TestExists("date", true);
      TestFoldForward((dat, acc) -> acc + "," + dat, "", ",apple,cherry,date");
    }

    @Test
    @DisplayName("InsertIfAbsent with Insert and Exists")
    public void InsertIfAbsentWithInsertExists() {
      AddTest(16);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      TestSize(3, false);
      TestInsertIfAbsent("date", true);
      TestSize(4, false);
      TestExists("date", true);
      TestInsertIfAbsent("banana", false);
      TestSize(4, false);
      TestInsertIfAbsent("elderberry", true);
      TestSize(5, false);
      TestInsert("banana", true);
      TestSize(6, false);
      TestInsertIfAbsent("apple", false);
      TestFoldForward((dat, acc) -> acc + 1, 0, 6);
    }

    @Test
    @DisplayName("Search, SearchPredecessor, SearchSuccessor comprehensive")
    public void SearchPredSuccComprehensive() {
      AddTest(20);
      NewEmptyContainer();
      TestInsert("banana", true);
      TestInsert("date", true);
      TestInsert("fig", true);
      TestInsert("kiwi", true);
      TestInsert("mango", true);
      TestSize(5, false);
      TestSearch("banana", Natural.ZERO);
      TestSearch("fig", Natural.Of(2));
      TestSearch("mango", Natural.Of(4));
      TestSearch("elderberry", null);
      TestSearchPredecessor("elderberry", Natural.Of(1));
      TestSearchPredecessor("fig", Natural.Of(1));
      TestSearchSuccessor("elderberry", Natural.Of(2));
      TestSearchSuccessor("fig", Natural.Of(3));
      TestPredecessor("elderberry", "date");
      TestPredecessor("banana", null);
      TestSuccessor("elderberry", "fig");
      TestSuccessor("mango", null);
      TestGetAt(Natural.Of(2), "fig", false);
      TestExists("fig", true);
    }

    @Test
    @DisplayName("Predecessor and Successor verification")
    public void PredecessorSuccessorVerification() {
      AddTest(18);
      NewEmptyContainer();
      TestInsert("banana", true);
      TestInsert("date", true);
      TestInsert("fig", true);
      TestInsert("kiwi", true);
      TestInsert("mango", true);
      TestSize(5, false);
      TestPredecessor("grape", "fig");
      TestPredecessor("mango", "kiwi");
      TestPredecessor("banana", null);
      TestPredecessor("apple", null);
      TestSuccessor("grape", "kiwi");
      TestSuccessor("banana", "date");
      TestSuccessor("mango", null);
      TestSuccessor("zebra", null);
      TestPredecessor("elderberry", "date");
      TestSuccessor("elderberry", "fig");
      TestFoldForward((dat, acc) -> acc + 1, 0, 5);
      TestSize(5, false);
    }

    @Test
    @DisplayName("SubSequence and SubChain verification")
    public void SubSequenceSubChainVerification() {
      AddTest(14);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      TestInsert("date", true);
      TestInsert("elderberry", true);
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
      TestInsert("a", true);
      TestInsert("bb", true);
      TestInsert("c", true);
      TestInsert("dd", true);
      TestInsert("e", true);
      TestInsert("ff", true);
      TestInsert("g", true);
      TestInsert("hh", true);
      TestSize(8, false);
      TestFilter(dat -> dat.length() == 2);
      TestSize(4, false);
      TestExists("bb", true);
      TestExists("dd", true);
      TestExists("ff", true);
      TestExists("hh", true);
      TestExists("a", false);
      TestExists("c", false);
      TestFoldForward((dat, acc) -> acc + dat, "", "bbddffhh");
    }

    @Test
    @DisplayName("Union, Intersection, Difference chain")
    public void SetOperationsChain() {
      AddTest(24);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      TestSize(3, false);
      
      LLSortedChain<String> otherChain = new LLSortedChain<>();
      otherChain.Insert("cherry");
      otherChain.Insert("date");
      otherChain.Insert("elderberry");
      
      TestUnion(otherChain);
      TestSize(5, false);
      TestExists("apple", true);
      TestExists("date", true);
      TestExists("elderberry", true);
      
      LLSortedChain<String> intersectChain = new LLSortedChain<>();
      intersectChain.Insert("banana");
      intersectChain.Insert("cherry");
      intersectChain.Insert("date");
      
      TestIntersection(intersectChain);
      TestExists("banana", true);
      TestExists("cherry", true);
      TestExists("date", true);
      TestExists("apple", false);
      
      LLSortedChain<String> diffChain = new LLSortedChain<>();
      diffChain.Insert("cherry");
      
      TestDifference(diffChain);
      TestExists("cherry", false);
      TestExists("banana", true);
      TestExists("date", true);
    }

    @Test
    @DisplayName("TraverseForward and TraverseBackward verification")
    public void TraverseForwardBackwardVerification() {
      AddTest(14);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      TestInsert("date", true);
      TestInsert("elderberry", true);
      TestSize(5, false);
      TestTraverseForward(dat -> dat.length() > 0, true);
      TestTraverseBackward(dat -> dat.length() > 0, true);
      TestTraverseForward(dat -> dat.length() < 7, true);
      TestTraverseBackward(dat -> dat.length() < 7, true);
      TestTraverseForward(dat -> dat.contains("a"), true);
      TestTraverseBackward(dat -> dat.contains("e"), true);
      TestTraverseForward(dat -> dat.length() <= 10, true);
      TestTraverseBackward(dat -> dat.length() >= 4, true);
      TestTraverseForward(dat -> dat.equals("elderberry"), true);
      TestTraverseBackward(dat -> dat.equals("apple"), true);
      TestTraverseForward(dat -> dat.equals("missing"), false);
      TestTraverseBackward(dat -> dat.equals("missing"), false);
      TestTraverseForward(dat -> dat.length() > 20, false);
      TestTraverseBackward(dat -> dat.length() > 20, false);
      TestTraverseForward(dat -> dat.startsWith("z"), false);
      TestTraverseBackward(dat -> dat.startsWith("z"), false);
      TestTraverseForward(dat -> dat.isEmpty(), false);
      TestTraverseBackward(dat -> dat.isEmpty(), false);
    }

    @Test
    @DisplayName("FoldForward and FoldBackward comprehensive")
    public void FoldForwardBackwardComprehensive() {
      AddTest(14);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      TestInsert("date", true);
      TestInsert("elderberry", true);
      TestFoldForward((dat, acc) -> acc + dat, "", "applebananacherrydateelderberry");
      TestFoldBackward((dat, acc) -> acc + dat, "", "elderberrydatecherrybananaapple");
      TestFoldForward((dat, acc) -> acc + "|" + dat, "", "|apple|banana|cherry|date|elderberry");
      TestFoldBackward((dat, acc) -> acc + "|" + dat, "", "|elderberry|date|cherry|banana|apple");
      TestFoldForward((dat, acc) -> dat.length() > acc.length() ? dat : acc, "", "elderberry");
      TestFoldBackward((dat, acc) -> dat.length() < acc.length() ? dat : acc, "elderberry", "date");
      TestSize(5, false);
      TestIsEmpty(false, false);
    }

    @Test
    @DisplayName("FIterator and BIterator usage")
    public void FIteratorBIteratorUsage() {
      AddTest(10);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      TestInsert("date", true);
      TestInsert("elderberry", true);
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
      TestInsert("elderberry", true);
      TestInsert("cherry", true);
      TestInsert("fig", true);
      TestSize(3, false);
      TestGetFirst("cherry", false);
      TestGetLast("fig", false);
      TestClear();
      TestIsEmpty(true, false);
      TestSize(0, false);
      TestInsert("kiwi", true);
      TestInsert("apple", true);
      TestInsert("elderberry", true);
      TestSize(3, false);
      TestGetFirst("apple", false);
      TestGetLast("kiwi", false);
      TestGetAt(Natural.Of(1), "elderberry", false);
      TestClear();
      TestInsert("banana", true);
      TestInsert("date", true);
      TestInsert("fig", true);
      TestInsert("grape", true);
      TestSize(4, false);
      TestFoldForward((dat, acc) -> acc + "," + dat, "", ",banana,date,fig,grape");
    }

    @Test
    @DisplayName("IsEqual verification after modifications")
    public void IsEqualAfterModifications() {
      AddTest(16);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      
      LLSortedChain<String> otherChain = new LLSortedChain<>();
      otherChain.Insert("cherry");
      otherChain.Insert("apple");
      otherChain.Insert("banana");
      
      TestIsEqual(otherChain, true);
      TestInsert("date", true);
      TestIsEqual(otherChain, false);
      otherChain.Insert("date");
      TestIsEqual(otherChain, true);
      TestRemove("banana", true);
      TestIsEqual(otherChain, false);
      otherChain.Remove("banana");
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
      TestGetFirst("", true);
      TestGetLast("", true);
      TestGetAt(Natural.ZERO, "", true);
      TestExists("apple", false);
      TestRemove("apple", false);
      TestFIterator();
      TestBIterator();
      TestForwardIteration(false, true);
      TestBackwardIteration(false, true);
      TestPredecessor("apple", null);
      TestSuccessor("apple", null);
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
      TestInsert("mango", true);
      TestSize(1, false);
      TestIsEmpty(false, false);
      TestGetFirst("mango", false);
      TestGetLast("mango", false);
      TestGetAt(Natural.ZERO, "mango", false);
      TestExists("mango", true);
      TestSearch("mango", Natural.ZERO);
      TestPredecessor("mango", null);
      TestSuccessor("mango", null);
      TestPredecessor("zebra", "mango");
      TestSuccessor("apple", "mango");
      TestFIterator();
      TestBIterator();
      TestForwardIteration(false, false);
      TestBackwardIteration(false, false);
      TestFoldForward((dat, acc) -> acc + dat, "", "mango");
      TestFoldBackward((dat, acc) -> acc + dat, "", "mango");
      TestRemoveFirst();
      TestIsEmpty(true, false);
      TestSize(0, false);
    }

    @Test
    @DisplayName("Duplicate values comprehensive handling")
    public void DuplicateValuesComprehensive() {
      AddTest(22);
      NewEmptyContainer();
      TestInsert("hello", true);
      TestInsert("hello", true);
      TestInsert("hello", true);
      TestInsert("world", true);
      TestInsert("hello", true);
      TestInsert("world", true);
      TestSize(6, false);
      TestGetFirst("hello", false);
      TestGetLast("world", false);
      TestSearch("hello", Natural.ZERO);
      TestSearch("world", Natural.Of(4));
      TestExists("hello", true);
      TestRemove("hello", true);
      TestSize(5, false);
      TestExists("hello", true);
      TestRemoveOccurrences("hello");
      TestExists("hello", false);
      TestSize(2, false);
      TestGetFirst("world", false);
      TestGetLast("world", false);
      TestFoldForward((dat, acc) -> acc + dat, "", "worldworld");
      TestInsertIfAbsent("world", false);
      TestSize(2, false);
    }

    @Test
    @DisplayName("Extreme values handling")
    public void ExtremeValuesHandling() {
      AddTest(18);
      NewEmptyContainer();
      TestInsert("AAAAA", true);
      TestInsert("mmmmm", true);
      TestInsert("zzzzz", true);
      TestSize(3, false);
      TestGetFirst("AAAAA", false);
      TestGetLast("zzzzz", false);
      TestExists("AAAAA", true);
      TestExists("zzzzz", true);
      TestExists("mmmmm", true);
      TestSearch("AAAAA", Natural.ZERO);
      TestSearch("mmmmm", Natural.Of(1));
      TestPredecessor("mmmmm", "AAAAA");
      TestSuccessor("mmmmm", "zzzzz");
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
      TestInsert("cherry", true);
      TestInsert("fig", true);
      TestInsert("elderberry", true);
      TestSize(3, false);
      TestGetFirst("cherry", false);
      TestGetLast("fig", false);
      TestInsert("banana", true);
      TestInsert("kiwi", true);
      TestSize(5, false);
      TestRemoveFirst();
      TestRemoveLast();
      TestSize(3, false);
      TestGetFirst("cherry", false);
      TestGetLast("fig", false);
      TestFirstNRemove("cherry");
      TestLastNRemove("fig");
      TestSize(1, false);
      TestGetFirst("elderberry", false);
      TestInsertIfAbsent("watermelon", true);
      TestInsertIfAbsent("elderberry", false);
      TestSize(2, false);
      TestPredecessor("mango", "elderberry");
      TestSuccessor("fig", "watermelon");
      TestRemoveOccurrences("elderberry");
      TestSize(1, false);
      TestExists("elderberry", false);
      TestFilter(dat -> dat.compareTo("mango") > 0);
      TestSize(1, false);
      TestGetFirst("watermelon", false);
      TestClear();
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Build, verify, clear, rebuild multiple cycles")
    public void BuildVerifyClearRebuildCycles() {
      AddTest(34);
      NewEmptyContainer();
      
      // Cycle 1
      TestInsert("cherry", true);
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestSize(3, false);
      TestGetFirst("apple", false);
      TestGetLast("cherry", false);
      TestFoldForward((dat, acc) -> acc + "," + dat, "", ",apple,banana,cherry");
      TestClear();
      TestIsEmpty(true, false);
      
      // Cycle 2
      TestInsert("fig", true);
      TestInsert("banana", true);
      TestInsert("date", true);
      TestSize(3, false);
      TestGetFirst("banana", false);
      TestGetLast("fig", false);
      TestSearch("date", Natural.Of(1));
      TestClear();
      
      // Cycle 3
      TestInsert("cherry", true);
      TestInsert("elderberry", true);
      TestInsert("date", true);
      TestSize(3, false);
      TestGetAt(Natural.Of(1), "date", false);
      TestPredecessor("coconut", "cherry");
      TestSuccessor("coconut", "date");
      TestClear();
      
      // Cycle 4
      TestInsert("mango", true);
      TestInsert("orange", true);
      TestInsert("peach", true);
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
      for (int i = 20; i >= 1; i--) {
        TestInsert("item" + String.format("%02d", i), true);
      }
      TestSize(20, false);
      TestGetFirst("item01", false);
      TestGetLast("item20", false);
      
      // Remove first 5
      for (int i = 0; i < 5; i++) {
        TestRemoveFirst();
      }
      TestSize(15, false);
      TestGetFirst("item06", false);
      
      // Remove last 5
      for (int i = 0; i < 5; i++) {
        TestRemoveLast();
      }
      TestSize(10, false);
      TestGetLast("item15", false);
      
      // Filter items with even numbers
      TestFilter(dat -> { int num = Integer.parseInt(dat.replace("item", "")); return num % 2 == 0; });
      TestSize(5, false);
      TestExists("item06", true);
      TestExists("item08", true);
      TestExists("item10", true);
      TestExists("item12", true);
      TestExists("item14", true);
      TestExists("item07", false);
      
      // Verify count
      TestFoldForward((dat, acc) -> acc + 1, 0, 5);
      
      TestClear();
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Filter removes all elements")
    public void FilterRemovesAll() {
      AddTest(12);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      TestInsert("date", true);
      TestInsert("elderberry", true);
      TestSize(5, false);
      TestFilter(dat -> false);
      TestIsEmpty(true, false);
      TestSize(0, false);
      TestGetFirst("", true);
      TestGetLast("", true);
      TestFIterator();
    }

    @Test
    @DisplayName("Filter keeps all elements")
    public void FilterKeepsAll() {
      AddTest(14);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      TestInsert("date", true);
      TestInsert("elderberry", true);
      TestSize(5, false);
      TestFilter(dat -> true);
      TestSize(5, false);
      TestGetFirst("apple", false);
      TestGetLast("elderberry", false);
      TestExists("apple", true);
      TestExists("elderberry", true);
      TestFoldForward((dat, acc) -> acc + dat, "", "applebananacherrydateelderberry");
      TestForwardIteration(false, false);
    }

    @Test
    @DisplayName("Sorted order maintained after all operations")
    public void SortedOrderMaintainedAfterAllOps() {
      AddTest(18);
      NewEmptyContainer();
      TestInsert("mango", true);
      TestInsert("banana", true);
      TestInsert("fig", true);
      TestInsert("date", true);
      TestInsert("kiwi", true);
      TestSize(5, false);
      TestSortedOrder();
      TestRemove("fig", true);
      TestSortedOrder();
      TestInsert("elderberry", true);
      TestSortedOrder();
      
      LLSortedChain<String> other = new LLSortedChain<>();
      other.Insert("cherry");
      other.Insert("grape");
      
      TestUnion(other);
      TestSortedOrder();
      TestSize(7, false);
      TestGetFirst("banana", false);
      TestGetLast("mango", false);
      TestFilter(dat -> dat.compareTo("date") > 0);
      TestSortedOrder();
    }

    @Test
    @DisplayName("Search returns correct positions")
    public void SearchReturnsCorrectPositions() {
      AddTest(18);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      TestInsert("cherry", true);
      TestInsert("cherry", true);
      TestInsert("date", true);
      TestSize(7, false);
      TestSearch("apple", Natural.ZERO);
      TestSearch("date", Natural.Of(6));
      TestSearchPredecessor("bbb", Natural.Of(2));
      TestSearchSuccessor("bbb", Natural.Of(3));
      TestSearchPredecessor("coconut", Natural.Of(5));
      TestSearchSuccessor("coconut", Natural.Of(6));
      TestGetFirst("apple", false);
      TestGetLast("date", false);
      TestFoldForward((dat, acc) -> acc + 1, 0, 7);
    }

  }

}

