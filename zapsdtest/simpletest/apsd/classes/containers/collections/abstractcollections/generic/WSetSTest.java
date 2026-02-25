package zapsdtest.simpletest.apsd.classes.containers.collections.abstractcollections.generic;

import apsd.classes.containers.collections.abstractcollections.WSet;
import apsd.classes.containers.collections.concretecollections.LLSortedChain;
import apsd.classes.containers.collections.concretecollections.LLList;
import apsd.interfaces.containers.collections.List;
import apsd.interfaces.containers.collections.Set;

import org.junit.jupiter.api.*;

abstract public class WSetSTest extends WSetTest<String> {

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
    TestPrintContent("Initial Set");
  }

  @Nested
  @DisplayName("WSet Basics")
  public class WSetBasics {

    @Test
    @DisplayName("Check starting from an Empty WSet")
    public void Empty() {
      AddTest(6);
      NewEmptyContainer();
      TestExists("apple", false);
      TestFilter(dat -> false);
      TestUnion(ThisContainer());
      TestIntersection(ThisContainer());
      TestDifference(ThisContainer());
      TestSize(0, false);
    }

    @Test
    @DisplayName("Check starting from a NonEmpty WSet")
    public void NonEmpty() {
      AddTest(42);
      NewNonEmptyContainer();
      // Set: {apple, banana, cherry, date, elderberry}
      Set<String> newcon = GetNewEmptyContainer();
      newcon.Insert("cherry");
      newcon.Insert("banana");
      newcon.Insert("fig");
      TestInsert("apricot", true);
      TestExists("banana", true);
      TestExists("elderberry", true);
      TestExists("cherry", true);
      TestExists("fig", false);
      TestUnion(newcon);
      TestExists("banana", true);
      TestExists("cherry", true);
      TestExists("fig", true);
      TestSize(7, false);
      TestDifference(newcon);
      TestExists("banana", false);
      TestExists("cherry", false);
      TestExists("fig", false);
      TestSize(4, false);
      TestUnion(newcon);
      TestExists("banana", true);
      TestExists("cherry", true);
      TestExists("fig", true);
      TestSize(7, false);
      TestIntersection(newcon);
      TestExists("apricot", false);
      TestExists("elderberry", false);
      TestSize(3, false);
      TestInsert("date", true);
      TestInsert("apple", true);
      TestInsert("date", false);
      TestInsert("cherry", false);
      TestInsert("banana", false);
      TestInsert("cherry", false);
      TestInsert("elderberry", true);
      TestInsert("apple", false);
      TestInsert("fig", false);
      TestPrintContent("");
      TestSize(6, false);
      LLList<String> set = new LLList<>();
      set.Insert("banana");
      set.Insert("cherry");
      set.Insert("date");
      TestSubSet(dat -> dat.compareTo("b") >= 0 && dat.compareTo("e") < 0, set);
      TestFilter(dat -> dat.compareTo("c") >= 0);
      TestSize(4, false);
      TestExists("banana", false);
      TestExists("date", true);
      TestClear();
      TestSize(0, false);
    }

  }

  
  @Nested
  @DisplayName("WSet Edge Cases")
  public class WSetEdgeCases {

    @Test
    @DisplayName("Single element set")
    public void SingleElement() {
      AddTest(11);
      NewEmptyContainer();
      TestInsert("mango", true);
      TestSize(1, false);
      TestExists("mango", true);
      TestExists("apple", false);
      TestInsert("mango", false);
      TestSize(1, false);
      TestRemove("mango", true);
      TestIsEmpty(true, false);
      TestExists("mango", false);
      TestRemove("mango", false);
      TestSize(0, false);
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

    @Test
    @DisplayName("Union with empty set")
    public void UnionWithEmpty() {
      AddTest(9);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      Set<String> emptySet = GetNewEmptyContainer();
      TestUnion(emptySet);
      TestSize(3, false);
      TestExists("apple", true);
      TestExists("banana", true);
      TestExists("cherry", true);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Union with disjoint set")
    public void UnionWithDisjoint() {
      AddTest(12);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      Set<String> other = GetNewEmptyContainer();
      other.Insert("cherry");
      other.Insert("date");
      TestUnion(other);
      TestSize(4, false);
      TestExists("apple", true);
      TestExists("banana", true);
      TestExists("cherry", true);
      TestExists("date", true);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Union with overlapping set")
    public void UnionWithOverlapping() {
      AddTest(11);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      Set<String> other = GetNewEmptyContainer();
      other.Insert("banana");
      other.Insert("date");
      TestUnion(other);
      TestSize(4, false);
      TestExists("apple", true);
      TestExists("banana", true);
      TestExists("cherry", true);
      TestExists("date", true);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Intersection with empty set")
    public void IntersectionWithEmpty() {
      AddTest(6);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      Set<String> emptySet = GetNewEmptyContainer();
      TestIntersection(emptySet);
      TestIsEmpty(true, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Intersection with disjoint set")
    public void IntersectionWithDisjoint() {
      AddTest(6);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      Set<String> other = GetNewEmptyContainer();
      other.Insert("cherry");
      other.Insert("date");
      TestIntersection(other);
      TestIsEmpty(true, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Intersection with overlapping set")
    public void IntersectionWithOverlapping() {
      AddTest(9);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      Set<String> other = GetNewEmptyContainer();
      other.Insert("banana");
      other.Insert("cherry");
      other.Insert("date");
      TestIntersection(other);
      TestSize(2, false);
      TestExists("banana", true);
      TestExists("cherry", true);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Difference with empty set")
    public void DifferenceWithEmpty() {
      AddTest(9);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      Set<String> emptySet = GetNewEmptyContainer();
      TestDifference(emptySet);
      TestSize(3, false);
      TestExists("apple", true);
      TestExists("banana", true);
      TestExists("cherry", true);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Difference with disjoint set")
    public void DifferenceWithDisjoint() {
      AddTest(9);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      Set<String> other = GetNewEmptyContainer();
      other.Insert("date");
      other.Insert("fig");
      TestDifference(other);
      TestSize(3, false);
      TestExists("apple", true);
      TestExists("banana", true);
      TestExists("cherry", true);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Difference with overlapping set")
    public void DifferenceWithOverlapping() {
      AddTest(9);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      Set<String> other = GetNewEmptyContainer();
      other.Insert("banana");
      other.Insert("date");
      TestDifference(other);
      TestSize(2, false);
      TestExists("apple", true);
      TestExists("cherry", true);
      TestExists("banana", false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Difference with superset")
    public void DifferenceWithSuperset() {
      AddTest(5);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      Set<String> superset = GetNewEmptyContainer();
      superset.Insert("apple");
      superset.Insert("banana");
      superset.Insert("cherry");
      TestDifference(superset);
      TestIsEmpty(true, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Filter keeps matching elements")
    public void FilterKeepsMatching() {
      AddTest(9);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      TestInsert("date", true);
      TestFilter(dat -> dat.startsWith("a") || dat.startsWith("c"));
      TestSize(2, false);
      TestExists("apple", true);
      TestExists("cherry", true);
      TestExists("banana", false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Filter removes all")
    public void FilterRemovesAll() {
      AddTest(4);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestFilter(dat -> false);
      TestIsEmpty(true, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Filter keeps all")
    public void FilterKeepsAll() {
      AddTest(6);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestFilter(dat -> true);
      TestSize(2, false);
      TestExists("apple", true);
      TestExists("banana", true);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Empty string in set")
    public void EmptyStringInSet() {
      AddTest(7);
      NewEmptyContainer();
      TestInsert("", true);
      TestInsert("apple", true);
      TestSize(2, false);
      TestExists("", true);
      TestInsert("", false);
      TestRemove("", true);
      TestSize(1, false);
    }

    @Test
    @DisplayName("Clear and reuse")
    public void ClearAndReuse() {
      AddTest(8);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestClear();
      TestIsEmpty(true, false);
      TestInsert("cherry", true);
      TestInsert("date", true);
      TestSize(2, false);
      TestExists("apple", false);
      TestExists("cherry", true);
    }

    @Test
    @DisplayName("Union with self")
    public void UnionWithSelf() {
      AddTest(6);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      TestUnion(ThisContainer());
      TestSize(3, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Intersection with self")
    public void IntersectionWithSelf() {
      AddTest(8);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      TestIntersection(ThisContainer());
      TestSize(3, false);
      TestExists("apple", true);
      TestExists("banana", true);
      TestExists("cherry", true);
    }

    @Test
    @DisplayName("Difference with self")
    public void DifferenceWithSelf() {
      AddTest(5);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      TestDifference(ThisContainer());
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Large set operations")
    public void LargeSetOperations() {
      AddTest(24);
      NewEmptyContainer();
      for (int i = 0; i < 20; i++) {
        TestInsert("item" + i, true);
      }
      TestSize(20, false);
      TestExists("item0", true);
      TestExists("item19", true);
      TestExists("item20", false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Symmetric difference behavior")
    public void SymmetricDifferenceBehavior() {
      AddTest(13);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      Set<String> other = GetNewEmptyContainer();
      other.Insert("banana");
      other.Insert("cherry");
      other.Insert("date");
      TestDifference(other);
      TestSize(1, false);
      TestExists("apple", true);
      TestExists("banana", false);
      TestExists("cherry", false);
      Set<String> bMinusA = GetNewEmptyContainer();
      bMinusA.Insert("date");
      TestUnion(bMinusA);
      TestSize(2, false);
      TestExists("apple", true);
      TestExists("date", true);
      TestPrintContent("");
    }

  }

  @Nested
  @DisplayName("WSet Stress Tests")
  public class WSetStressTests {

    @Test
    @DisplayName("Chained set operations")
    public void ChainedSetOperations() {
      AddTest(19);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      TestInsert("date", true);
      TestInsert("elderberry", true);
      
      Set<String> set1 = GetNewEmptyContainer();
      set1.Insert("cherry");
      set1.Insert("date");
      set1.Insert("elderberry");
      set1.Insert("fig");
      
      Set<String> set2 = GetNewEmptyContainer();
      set2.Insert("apple");
      set2.Insert("banana");
      
      TestUnion(set1);
      TestSize(6, false);
      TestExists("fig", true);
      
      TestDifference(set2);
      TestSize(4, false);
      TestExists("apple", false);
      TestExists("banana", false);
      
      TestIntersection(set1);
      TestSize(4, false);
      TestExists("cherry", true);
      TestExists("date", true);
      TestExists("elderberry", true);
      TestExists("fig", true);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Filter with complex predicate")
    public void FilterComplexPredicate() {
      AddTest(12);
      NewEmptyContainer();
      // Insert words of varying lengths
      TestInsert("a", true);
      TestInsert("bb", true);
      TestInsert("ccc", true);
      TestInsert("dddd", true);
      TestInsert("eeeee", true);
      TestInsert("ff", true);
      TestInsert("ggg", true);
      TestInsert("hh", true);
      // Filter: keep only strings with length <= 3
      TestFilter(dat -> dat.length() <= 3);
      TestSize(6, false);
      TestExists("a", true);
      TestExists("bb", true);
      TestExists("ccc", true);
      TestExists("dddd", false);
      TestExists("eeeee", false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Extreme values in set")
    public void ExtremeValues() {
      AddTest(8);
      NewEmptyContainer();
      TestInsert("", true);
      TestInsert("a", true);
      String longStr = "x".repeat(10000);
      TestInsert(longStr, true);
      TestSize(3, false);
      TestExists("", true);
      TestExists("a", true);
      TestExists(longStr, true);
      TestExists("nonexistent", false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Union with subset")
    public void UnionWithSubset() {
      AddTest(12);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      TestInsert("date", true);
      TestInsert("elderberry", true);
      Set<String> subset = GetNewEmptyContainer();
      subset.Insert("banana");
      subset.Insert("date");
      TestUnion(subset);
      TestSize(5, false);
      TestExists("apple", true);
      TestExists("banana", true);
      TestExists("cherry", true);
      TestExists("date", true);
      TestExists("elderberry", true);
    }

    @Test
    @DisplayName("Intersection with subset")
    public void IntersectionWithSubset() {
      AddTest(12);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      TestInsert("date", true);
      TestInsert("elderberry", true);
      Set<String> subset = GetNewEmptyContainer();
      subset.Insert("banana");
      subset.Insert("date");
      TestIntersection(subset);
      TestSize(2, false);
      TestExists("banana", true);
      TestExists("date", true);
      TestExists("apple", false);
      TestExists("cherry", false);
      TestExists("elderberry", false);
    }

    @Test
    @DisplayName("Multiple filters")
    public void MultipleFilters() {
      AddTest(10);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      TestInsert("date", true);
      TestInsert("elderberry", true);
      TestFilter(dat -> dat.length() > 4);
      TestSize(4, false);
      TestFilter(dat -> dat.startsWith("e") || dat.startsWith("c"));
      TestSize(2, false);
      TestExists("cherry", true);
      TestExists("elderberry", true);
      TestExists("apple", false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Empty set operations")
    public void EmptySetOperations() {
      AddTest(12);
      NewEmptyContainer();
      Set<String> other = GetNewEmptyContainer();
      TestUnion(other);
      TestIsEmpty(true, false);
      TestIntersection(other);
      TestIsEmpty(true, false);
      TestDifference(other);
      TestIsEmpty(true, false);
      other.Insert("apple");
      other.Insert("banana");
      TestUnion(other);
      TestSize(2, false);
      TestExists("apple", true);
      TestExists("banana", true);
      TestDifference(other);
      TestIsEmpty(true, false);
    }

  }

  @Nested
  @DisplayName("WSet Construction and Equality Tests")
  public class WSetConstructionAndEqualityTests {

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
      WSet<String> conFromList = new WSet<>(list);
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
      AddTest(3);
      NewEmptyContainer();
      List<String> emptyList = new LLList<>();
      WSet<String> conFromEmpty = new WSet<>(emptyList);
      TestIsEqual(conFromEmpty, true);
      TestIsEmpty(true, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Construction from chain")
    public void ConstructionFromChain() {
      AddTest(5);
      NewEmptyContainer();
      LLSortedChain<String> chain = new LLSortedChain<>();
      chain.Insert("cherry");
      chain.Insert("apple");
      chain.Insert("banana");
      WSet<String> conFromChain = new WSet<>(chain);
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      TestIsEqual(conFromChain, true);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Equality with same elements different order insertion")
    public void EqualityDifferentInsertionOrder() {
      AddTest(5);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      
      WSet<String> other = (WSet<String>) GetNewEmptyContainer();
      other.Insert("cherry");
      other.Insert("apple");
      other.Insert("banana");
      TestIsEqual(other, true);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Inequality with different sizes")
    public void InequalityDifferentSizes() {
      AddTest(4);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      
      WSet<String> other = (WSet<String>) GetNewEmptyContainer();
      other.Insert("apple");
      other.Insert("banana");
      other.Insert("cherry");
      TestIsEqual(other, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Inequality with different elements")
    public void InequalityDifferentElements() {
      AddTest(5);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      
      WSet<String> different = (WSet<String>) GetNewEmptyContainer();
      different.Insert("apple");
      different.Insert("banana");
      different.Insert("date");
      TestIsEqual(different, false);
      TestSize(3, false);
    }

    @Test
    @DisplayName("Equality of two empty sets")
    public void EqualityEmptySets() {
      AddTest(2);
      NewEmptyContainer();
      WSet<String> other = (WSet<String>) GetNewEmptyContainer();
      TestIsEqual(other, true);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Copy construction preserves elements")
    public void CopyConstructionPreservesElements() {
      AddTest(7);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      WSet<String> copy = new WSet<>(ThisContainer());
      TestIsEqual(copy, true);
      TestSize(3, false);
      TestExists("apple", true);
      TestPrintContent("");
    }

  }

  @Nested
  @DisplayName("WSet Null Testing")
  public class WSetNullTesting {

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
    @DisplayName("Union with null set")
    public void UnionWithNull() {
      AddTest(4);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestUnion(null);
      TestSize(2, false);
    }

    @Test
    @DisplayName("Intersection with null set")
    public void IntersectionWithNull() {
      AddTest(5);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      TestIntersection(null);
      TestSize(0, false);
    }

    @Test
    @DisplayName("Difference with null set")
    public void DifferenceWithNull() {
      AddTest(5);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      TestDifference(null);
      TestSize(3, false);
    }

    @Test
    @DisplayName("Filter with null predicate")
    public void FilterWithNullPredicate() {
      AddTest(4);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestFilter(null);
      TestSize(2, false);
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
      AddTest(8);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert(null, false);
      TestRemove(null, false);
      TestSize(1, false);
      TestExists("apple", true);
      TestExists(null, false);
      TestRemove("apple", true);
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Empty set null operations")
    public void EmptySetNullOperations() {
      AddTest(7);
      NewEmptyContainer();
      TestInsert(null, false);
      TestRemove(null, false);
      TestExists(null, false);
      TestUnion(null);
      TestIntersection(null);
      TestDifference(null);
      TestIsEmpty(true, false);
    }

  }

  @Nested
  @DisplayName("WSet Comprehensive Method Combinations")
  public class WSetComprehensiveMethodCombinations {

    @Test
    @DisplayName("Insert, Exists, Size cycle through all elements")
    public void InsertExistsSizeCycle() {
      AddTest(16);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestExists("apple", true);
      TestSize(1, false);
      TestInsert("banana", true);
      TestExists("banana", true);
      TestSize(2, false);
      TestInsert("cherry", true);
      TestExists("cherry", true);
      TestSize(3, false);
      TestInsert("date", true);
      TestExists("date", true);
      TestSize(4, false);
      TestInsert("elderberry", true);
      TestExists("elderberry", true);
      TestSize(5, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Remove then verify with Exists and Size")
    public void RemoveWithExistsAndSize() {
      AddTest(15);
      NewNonEmptyContainer();
      TestRemove("cherry", true);
      TestExists("cherry", false);
      TestSize(4, false);
      TestRemove("apple", true);
      TestExists("apple", false);
      TestSize(3, false);
      TestRemove("elderberry", true);
      TestExists("elderberry", false);
      TestSize(2, false);
      TestExists("banana", true);
      TestExists("date", true);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Filter then verify with Exists and Size")
    public void FilterWithExistsAndSize() {
      AddTest(10);
      NewNonEmptyContainer();
      // Set: {apple, banana, cherry, date, elderberry}
      TestFilter(dat -> dat.length() >= 5);
      TestSize(4, false);
      TestExists("apple", true);
      TestExists("cherry", true);
      TestExists("elderberry", true);
      TestExists("banana", true);
      TestExists("date", false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Union with verification using Exists, Size")
    public void UnionWithFullVerification() {
      AddTest(12);
      NewNonEmptyContainer();
      Set<String> other = GetNewEmptyContainer();
      other.Insert("fig");
      other.Insert("grape");
      other.Insert("cherry");
      TestUnion(other);
      TestSize(7, false);
      TestExists("apple", true);
      TestExists("banana", true);
      TestExists("cherry", true);
      TestExists("date", true);
      TestExists("elderberry", true);
      TestExists("fig", true);
      TestExists("grape", true);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Intersection with verification using Exists, Size")
    public void IntersectionWithFullVerification() {
      AddTest(10);
      NewNonEmptyContainer();
      Set<String> other = GetNewEmptyContainer();
      other.Insert("banana");
      other.Insert("date");
      other.Insert("fig");
      TestIntersection(other);
      TestSize(2, false);
      TestExists("banana", true);
      TestExists("date", true);
      TestExists("apple", false);
      TestExists("cherry", false);
      TestExists("elderberry", false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Difference with verification using Exists, Size")
    public void DifferenceWithFullVerification() {
      AddTest(10);
      NewNonEmptyContainer();
      Set<String> other = GetNewEmptyContainer();
      other.Insert("banana");
      other.Insert("date");
      other.Insert("fig");
      TestDifference(other);
      TestSize(3, false);
      TestExists("apple", true);
      TestExists("cherry", true);
      TestExists("elderberry", true);
      TestExists("banana", false);
      TestExists("date", false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("InsertAll then RemoveAll with verification")
    public void InsertAllThenRemoveAll() {
      AddTest(12);
      NewEmptyContainer();
      Set<String> items = GetNewEmptyContainer();
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
      Set<String> toInsert = GetNewEmptyContainer();
      toInsert.Insert("banana");
      toInsert.Insert("cherry");
      toInsert.Insert("date");
      TestInsertSome(toInsert, true);
      TestSize(4, false);
      Set<String> toRemove = GetNewEmptyContainer();
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
      TestInsert("alpha", true);
      TestInsert("bravo", true);
      TestInsert("charlie", true);
      TestInsert("delta", true);
      TestInsert("echo", true);
      TestFIterator();
      TestBIterator();
      TestSize(5, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Clear then rebuild with all operations")
    public void ClearThenRebuildWithAllOps() {
      AddTest(12);
      NewNonEmptyContainer();
      TestClear();
      TestIsEmpty(true, false);
      TestInsert("fig", true);
      TestInsert("grape", true);
      TestInsert("honeydew", true);
      TestSize(3, false);
      TestExists("fig", true);
      TestExists("grape", true);
      TestExists("honeydew", true);
      TestExists("apple", false);
      TestRemove("grape", true);
      TestSize(2, false);
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
      Set<String> s1 = GetNewEmptyContainer();
      s1.Insert("cherry");
      s1.Insert("date");
      Set<String> s2 = GetNewEmptyContainer();
      s2.Insert("apple");
      s2.Insert("date");
      TestUnion(s1);
      TestSize(4, false);
      TestIntersection(s2);
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
      TestFoldForward((dat, acc) -> acc + 1, 0, 5);
      TestFoldBackward((dat, acc) -> acc + 1, 0, 5);
      TestFoldForward((dat, acc) -> dat.length() > acc ? dat.length() : acc, 0, 10);
      TestFoldBackward((dat, acc) -> dat.length() < acc ? dat.length() : acc, Integer.MAX_VALUE, 4);
      TestPrintContent("");
    }

    @Test
    @DisplayName("IsEqual after various modifications")
    public void IsEqualAfterModifications() {
      AddTest(10);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      WSet<String> copy = (WSet<String>) GetNewEmptyContainer();
      copy.Insert("apple");
      copy.Insert("banana");
      copy.Insert("cherry");
      TestIsEqual(copy, true);
      TestRemove("cherry", true);
      TestIsEqual(copy, false);
      copy.Remove("cherry");
      TestIsEqual(copy, true);
      TestInsert("date", true);
      TestIsEqual(copy, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Empty set all operations")
    public void EmptySetAllOperations() {
      AddTest(10);
      NewEmptyContainer();
      TestIsEmpty(true, false);
      TestSize(0, false);
      TestExists("apple", false);
      TestRemove("apple", false);
      TestFilter(dat -> true);
      Set<String> other = GetNewEmptyContainer();
      other.Insert("apple");
      TestIntersection(other);
      TestDifference(other);
      TestIsEmpty(true, false);
      TestFIterator();
      TestBIterator();
    }

    @Test
    @DisplayName("Single element all operations")
    public void SingleElementAllOperations() {
      AddTest(12);
      NewEmptyContainer();
      TestInsert("mango", true);
      TestSize(1, false);
      TestExists("mango", true);
      TestFoldForward((dat, acc) -> acc + dat, "", "mango");
      TestFoldBackward((dat, acc) -> acc + dat, "", "mango");
      
      WSet<String> other = (WSet<String>) GetNewEmptyContainer();
      other.Insert("mango");
      TestIsEqual(other, true);
      
      TestRemove("mango", true);
      TestIsEmpty(true, false);
      TestIsEqual(other, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("InsertAll with duplicates then Filter")
    public void InsertAllWithDuplicatesThenFilter() {
      AddTest(8);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      Set<String> dupes = GetNewEmptyContainer();
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
      Set<String> nonExistent = GetNewEmptyContainer();
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
      Set<String> extras = GetNewEmptyContainer();
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
      TestExists("delta", true);
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

    @Test
    @DisplayName("Large set with multiple operations")
    public void LargeSetWithMultipleOperations() {
      AddTest(10);
      NewEmptyContainer();
      for (int i = 0; i < 50; i++) {
        TestInsert("element" + i, true);
      }
      TestSize(50, false);
      Set<String> toRemove = GetNewEmptyContainer();
      for (int i = 0; i < 25; i++) {
        toRemove.Insert("element" + (i * 2));
      }
      TestDifference(toRemove);
      TestSize(25, false);
      TestExists("element1", true);
      TestExists("element0", false);
      TestFilter(dat -> dat.compareTo("element2") >= 0);
      TestExists("element1", false);
      TestExists("element19", false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Set equality with different insertion orders")
    public void SetEqualityDifferentOrders() {
      AddTest(6);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      
      WSet<String> other = (WSet<String>) GetNewEmptyContainer();
      other.Insert("cherry");
      other.Insert("apple");
      other.Insert("banana");
      TestIsEqual(other, true);
      TestSize(3, false);
      TestPrintContent("");
    }

  }

}
