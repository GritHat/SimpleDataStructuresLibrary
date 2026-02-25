package zapsdtest.simpletest.apsd.classes.containers.collections.concretecollections.generic;

import org.junit.jupiter.api.*;

import apsd.classes.containers.collections.concretecollections.LLList;
import apsd.classes.containers.collections.concretecollections.LLSortedChain;
import apsd.classes.utilities.Natural;

abstract public class XListSTest extends XListTest<String> {  
  @Override
  public void NewNonEmptyContainer() {
    AddTest(11);
    NewEmptyContainer();
    TestInsert("apple", true);
    TestInsertLast("date");
    TestInsertFirst("elderberry");
    TestInsertLast("fig");
    TestInsertFirst("banana");
    TestInsertFirst("avocado");
    TestInsert("apple", true);
    TestInsert("elderberry", true);
    TestInsertLast("avocado");
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
      TestGetFirst("", true);
      TestGetLast("", true);
      TestSetFirst("", true);
      TestSetLast("", true);
      TestGetAt(Natural.Of(1), "", true);
      TestSetAt("", Natural.Of(2), true);
      TestExists("apple", false);
      TestPrintContent("");
      TestFoldBackward((dat, acc) -> acc + dat, "", "");
      TestRemove("apple", false);
      TestRemoveFirst();
      TestRemoveLast();
    }

    @Test
    @DisplayName("Check starting from a NonEmpty List")
    public void NonEmpty() {
      AddTest(25);
      NewNonEmptyContainer();
      // List state: [elderberry, apple, avocado, banana, elderberry, apple, date, fig, avocado]
      TestGetFirst("elderberry", false);
      TestGetLast("avocado", false);
      TestSetFirst("cherry", false);
      TestSetLast("grape", false);
      TestGetAt(Natural.Of(4), "elderberry", false);
      TestSetAt("kiwi", Natural.Of(4), false);
      TestExists("date", true);
      // List state: [cherry, apple, avocado, banana, kiwi, apple, date, fig, grape]
      TestPrintContent("");
      TestFoldForward((dat, acc) -> acc + "," + dat, "", ",cherry,apple,avocado,banana,kiwi,apple,date,fig,grape");
      TestFoldBackward((dat, acc) -> acc + "," + dat, "", ",grape,fig,date,apple,kiwi,banana,avocado,apple,cherry");
      TestRemoveFirst();
      TestFoldForward((dat, acc) -> acc + "," + dat, "", ",apple,avocado,banana,kiwi,apple,date,fig,grape");
      TestRemoveLast();
      TestFoldForward((dat, acc) -> acc + "," + dat, "", ",apple,avocado,banana,kiwi,apple,date,fig");
      TestAtNRemove(Natural.Of(5), "date", false);
      TestFoldBackward((dat, acc) -> acc + "," + dat, "", ",fig,apple,kiwi,banana,avocado,apple");
      TestSize(6, false);
      TestInsertAt("mango", Natural.Of(6), false);
      TestPrintContent("");
      TestFoldBackward((dat, acc) -> acc + "," + dat, "", ",mango,fig,apple,kiwi,banana,avocado,apple");
      TestInsertAt("mango", Natural.Of(4), false);
      //mango,fig,apple,kiwi,mango,banana,avocado,apple
      TestPrintContent("@@@@@@@@@@@@@@@@@@@@@@@@@@@");
      LLList<String> set = new LLList<>();
      set.Insert("mango");
      set.Insert("apple");
      set.Insert("mango");
      set.Insert("banana");
      set.Insert("avocado");
      set.Insert("apple");
      TestPrintContent("");
      TestSubSet(dat -> dat.length() > 4, set);
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
      TestInsertFirst("hello");
      TestSize(1, false);
      TestGetFirst("hello", false);
      TestGetLast("hello", false);
      TestGetAt(Natural.ZERO, "hello", false);
      TestExists("hello", true);
      TestExists("world", false);
      TestSetFirst("foo", false);
      TestGetFirst("foo", false);
      TestGetLast("foo", false);
      TestSetLast("bar", false);
      TestGetFirst("bar", false);
      TestFoldForward((dat, acc) -> acc + dat, "", "bar");
      TestRemoveFirst();
      TestIsEmpty(true, false);
      TestSize(0, false);
    }

    @Test
    @DisplayName("Insert at various positions")
    public void InsertAtPositions() {
      AddTest(17);
      NewEmptyContainer();
      TestInsertFirst("cherry");
      TestInsertFirst("apple");
      TestInsertLast("elderberry");
      TestInsertAt("banana", Natural.Of(1), false);
      TestInsertAt("date", Natural.Of(3), false);
      TestPrintContent("");
      TestGetAt(Natural.ZERO, "apple", false);
      TestGetAt(Natural.Of(1), "banana", false);
      TestGetAt(Natural.Of(2), "cherry", false);
      TestGetAt(Natural.Of(3), "date", false);
      TestGetAt(Natural.Of(4), "elderberry", false);
      TestSize(5, false);
      TestInsertAt("aaa", Natural.ZERO, false);
      TestGetFirst("aaa", false);
      TestInsertAt("zzz", Natural.Of(6), false);
      TestGetLast("zzz", false);
      TestSize(7, false);
    }

    @Test
    @DisplayName("Remove at various positions")
    public void RemoveAtPositions() {
      AddTest(15);
      NewEmptyContainer();
      TestInsertLast("apple");
      TestInsertLast("banana");
      TestInsertLast("cherry");
      TestInsertLast("date");
      TestInsertLast("elderberry");
      TestPrintContent("");
      TestAtNRemove(Natural.Of(2), "cherry", false);
      TestPrintContent("");
      TestSize(4, false);
      TestAtNRemove(Natural.ZERO, "apple", false);
      TestGetFirst("banana", false);
      TestAtNRemove(Natural.Of(2), "elderberry", false);
      TestGetLast("date", false);
      TestSize(2, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Remove by value")
    public void RemoveByValue() {
      AddTest(13);
      NewEmptyContainer();
      TestInsertLast("apple");
      TestInsertLast("banana");
      TestInsertLast("cherry");
      TestInsertLast("banana");
      TestInsertLast("date");
      TestRemove("banana", true);
      TestSize(4, false);
      TestExists("banana", true);
      TestRemove("banana", true);
      TestExists("banana", false);
      TestRemove("banana", false);
      TestSize(3, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Duplicates handling")
    public void DuplicatesHandling() {
      AddTest(14);
      NewEmptyContainer();
      TestInsert("hello", true);
      TestInsert("hello", true);
      TestInsert("hello", true);
      TestSize(3, false);
      TestGetFirst("hello", false);
      TestGetLast("hello", false);
      TestExists("hello", true);
      TestFoldForward((dat, acc) -> acc + dat, "", "hellohellohello");
      TestRemove("hello", true);
      TestSize(2, false);
      TestRemove("hello", true);
      TestRemove("hello", true);
      TestRemove("hello", false);
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Alternating insert first and last")
    public void AlternatingInserts() {
      AddTest(12);
      NewEmptyContainer();
      TestInsertFirst("cherry");
      TestInsertLast("date");
      TestInsertFirst("banana");
      TestInsertLast("elderberry");
      TestInsertFirst("apple");
      TestInsertLast("fig");
      TestPrintContent("");
      TestGetFirst("apple", false);
      TestGetLast("fig", false);
      TestSize(6, false);
      TestFoldForward((dat, acc) -> acc + "," + dat, "", ",apple,banana,cherry,date,elderberry,fig");
      TestGetAt(Natural.Of(3), "date", false);
    }

    @Test
    @DisplayName("Remove first and last alternating")
    public void AlternatingRemoves() {
      AddTest(16);
      NewEmptyContainer();
      TestInsertLast("a");
      TestInsertLast("b");
      TestInsertLast("c");
      TestInsertLast("d");
      TestInsertLast("e");
      TestInsertLast("f");
      TestRemoveFirst();
      TestGetFirst("b", false);
      TestRemoveLast();
      TestGetLast("e", false);
      TestRemoveFirst();
      TestGetFirst("c", false);
      TestRemoveLast();
      TestGetLast("d", false);
      TestSize(2, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Set at boundary positions")
    public void SetAtBoundaries() {
      AddTest(12);
      NewEmptyContainer();
      TestInsertLast("a");
      TestInsertLast("b");
      TestInsertLast("c");
      TestInsertLast("d");
      TestInsertLast("e");
      TestSetAt("first", Natural.ZERO, false);
      TestSetAt("last", Natural.Of(4), false);
      TestGetFirst("first", false);
      TestGetLast("last", false);
      TestSetAt("oob", Natural.Of(5), true);
      TestFoldForward((dat, acc) -> acc + "," + dat, "", ",first,b,c,d,last");
      TestPrintContent("");
    }

    @Test
    @DisplayName("Insert at out of bounds")
    public void InsertAtOutOfBounds() {
      AddTest(9);
      NewEmptyContainer();
      TestInsertLast("a");
      TestInsertLast("b");
      TestInsertLast("c");
      TestInsertAt("x", Natural.Of(10), true);
      TestSize(3, false);
      TestInsertAt("y", Natural.Of(4), true);
      TestSize(3, false);
      TestGetLast("c", false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Remove at out of bounds")
    public void RemoveAtOutOfBounds() {
      AddTest(9);
      NewEmptyContainer();
      TestInsertLast("a");
      TestInsertLast("b");
      TestInsertLast("c");
      TestAtNRemove(Natural.Of(10), "", true);
      TestSize(3, false);
      TestAtNRemove(Natural.Of(3), "", true);
      TestSize(3, false);
      TestPrintContent("");
      TestFoldForward((dat, acc) -> acc + dat, "", "abc");
    }

    @Test
    @DisplayName("Large list operations")
    public void LargeListOperations() {
      AddTest(82);
      NewEmptyContainer();
      for (int i = 1; i <= 50; i++) {
        TestInsertLast("item" + i);
      }
      TestSize(50, false);
      TestGetFirst("item1", false);
      TestGetLast("item50", false);
      TestGetAt(Natural.Of(24), "item25", false);
      TestFoldForward((dat, acc) -> acc + 1, 0, 50);
      for (int i = 0; i < 25; i++) {
        TestRemoveFirst();
      }
      TestSize(25, false);
      TestGetFirst("item26", false);
    }

    @Test
    @DisplayName("Empty strings in list")
    public void EmptyStrings() {
      AddTest(13);
      NewEmptyContainer();
      TestInsertLast("hello");
      TestInsertLast("world");
      TestInsertLast("");
      TestInsertLast("foo");
      TestInsertLast("bar");
      TestGetFirst("hello", false);
      TestGetLast("bar", false);
      TestExists("world", true);
      TestExists("missing", false);
      TestFoldForward((dat, acc) -> acc + dat, "", "helloworld" + "" + "foobar");
      TestRemove("world", true);
      TestFoldForward((dat, acc) -> acc + dat, "", "hello" + "" + "foobar");
      TestPrintContent("");
    }

    @Test
    @DisplayName("Clear and reuse")
    public void ClearAndReuse() {
      AddTest(14);
      NewEmptyContainer();
      TestInsertLast("a");
      TestInsertLast("b");
      TestInsertLast("c");
      TestSize(3, false);
      TestClear();
      TestIsEmpty(true, false);
      TestSize(0, false);
      TestInsertFirst("x");
      TestInsertLast("y");
      TestSize(2, false);
      TestGetFirst("x", false);
      TestGetLast("y", false);
      TestFoldForward((dat, acc) -> acc + dat, "", "xy");
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
      TestInsertLast("alpha");
      TestInsertLast("beta");
      TestInsertLast("gamma");
      TestGetNSetFirst("ALPHA", "alpha", false);
      TestGetFirst("ALPHA", false);
      TestGetNSetLast("GAMMA", "gamma", false);
      TestGetLast("GAMMA", false);
      TestGetNSetAt("BETA", "beta", Natural.Of(1), false);
      TestGetAt(Natural.Of(1), "BETA", false);
      TestFoldForward((dat, acc) -> acc + "," + dat, "", ",ALPHA,BETA,GAMMA");
      TestPrintContent("");
      TestSize(3, false);
    }

    @Test
    @DisplayName("Insert at middle repeatedly")
    public void InsertAtMiddleRepeatedly() {
      AddTest(16);
      NewEmptyContainer();
      TestInsertLast("a");
      TestInsertLast("e");
      TestInsertAt("c", Natural.Of(1), false);
      TestInsertAt("b", Natural.Of(1), false);
      TestInsertAt("d", Natural.Of(3), false);
      TestPrintContent("");
      TestGetAt(Natural.ZERO, "a", false);
      TestGetAt(Natural.Of(1), "b", false);
      TestGetAt(Natural.Of(2), "c", false);
      TestGetAt(Natural.Of(3), "d", false);
      TestGetAt(Natural.Of(4), "e", false);
      TestSize(5, false);
      TestFoldForward((dat, acc) -> acc + dat, "", "abcde");
      TestFoldBackward((dat, acc) -> acc + dat, "", "edcba");
    }

    @Test
    @DisplayName("Remove from middle repeatedly")
    public void RemoveFromMiddleRepeatedly() {
      AddTest(18);
      NewEmptyContainer();
      TestInsertLast("a");
      TestInsertLast("b");
      TestInsertLast("c");
      TestInsertLast("d");
      TestInsertLast("e");
      TestInsertLast("f");
      TestInsertLast("g");
      TestPrintContent("");
      TestAtNRemove(Natural.Of(3), "d", false);
      TestPrintContent("");
      TestAtNRemove(Natural.Of(3), "e", false);
      TestPrintContent("");
      TestAtNRemove(Natural.Of(3), "f", false);
      TestPrintContent("");
      TestSize(4, false);
      TestFoldForward((dat, acc) -> acc + dat, "", "abcg");
      TestGetAt(Natural.Of(3), "g", false);
    }

    @Test
    @DisplayName("Extreme string values in list")
    public void ExtremeValues() {
      AddTest(12);
      NewEmptyContainer();
      TestInsertLast("zzzzz");
      TestInsertLast("aaaaa");
      TestInsertLast("");
      TestInsertLast("HELLO");
      TestInsertLast("hello");
      TestGetFirst("zzzzz", false);
      TestGetAt(Natural.Of(1), "aaaaa", false);
      TestExists("zzzzz", true);
      TestExists("aaaaa", true);
      TestSize(5, false);
      TestRemove("zzzzz", true);
      TestGetFirst("aaaaa", false);
    }

    @Test
    @DisplayName("Remove all by value")
    public void RemoveAllByValue() {
      AddTest(16);
      NewEmptyContainer();
      TestInsertLast("x");
      TestInsertLast("y");
      TestInsertLast("x");
      TestInsertLast("z");
      TestInsertLast("x");
      TestInsertLast("w");
      TestInsertLast("x");
      TestSize(7, false);
      TestRemove("x", true);
      TestRemove("x", true);
      TestRemove("x", true);
      TestRemove("x", true);
      TestRemove("x", false);
      TestSize(3, false);
      TestExists("x", false);
      TestFoldForward((dat, acc) -> acc + dat, "", "yzw");
    }

    @Test
    @DisplayName("Rebuild list after clear")
    public void RebuildAfterClear() {
      AddTest(18);
      NewEmptyContainer();
      TestInsertLast("a");
      TestInsertLast("b");
      TestInsertLast("c");
      TestClear();
      TestIsEmpty(true, false);
      TestInsertFirst("x");
      TestInsertLast("z");
      TestInsertAt("y", Natural.Of(1), false);
      TestSize(3, false);
      TestGetAt(Natural.ZERO, "x", false);
      TestGetAt(Natural.Of(1), "y", false);
      TestGetAt(Natural.Of(2), "z", false);
      TestClear();
      TestInsertLast("final");
      TestSize(1, false);
      TestGetFirst("final", false);
      TestGetLast("final", false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Sequential access verification")
    public void SequentialAccessVerification() {
      AddTest(16);
      NewEmptyContainer();
      TestInsertLast("one");
      TestInsertLast("two");
      TestInsertLast("three");
      TestInsertLast("four");
      TestInsertLast("five");
      TestGetAt(Natural.ZERO, "one", false);
      TestGetAt(Natural.Of(1), "two", false);
      TestGetAt(Natural.Of(2), "three", false);
      TestGetAt(Natural.Of(3), "four", false);
      TestGetAt(Natural.Of(4), "five", false);
      TestSetAt("THREE", Natural.Of(2), false);
      TestGetAt(Natural.Of(2), "THREE", false);
      TestFoldForward((dat, acc) -> acc + "," + dat, "", ",one,two,THREE,four,five");
      TestFoldBackward((dat, acc) -> acc + "," + dat, "", ",five,four,THREE,two,one");
      TestPrintContent("");
    }

    @Test
    @DisplayName("Insert and remove at same position")
    public void InsertRemoveSamePosition() {
      AddTest(14);
      NewEmptyContainer();
      TestInsertLast("a");
      TestInsertLast("b");
      TestInsertLast("c");
      TestInsertAt("X", Natural.Of(1), false);
      TestGetAt(Natural.Of(1), "X", false);
      TestAtNRemove(Natural.Of(1), "X", false);
      TestGetAt(Natural.Of(1), "b", false);
      TestInsertAt("Y", Natural.Of(1), false);
      TestAtNRemove(Natural.Of(1), "Y", false);
      TestSize(3, false);
      TestFoldForward((dat, acc) -> acc + dat, "", "abc");
      TestPrintContent("");
    }

    @Test
    @DisplayName("Mixed operations stress test")
    public void MixedOperationsStress() {
      AddTest(24);
      NewEmptyContainer();
      TestInsertFirst("beta");
      TestInsertLast("delta");
      TestInsertFirst("alpha");
      TestInsertLast("epsilon");
      TestInsertAt("gamma", Natural.Of(2), false);
      TestPrintContent("");
      TestRemoveFirst();
      TestRemoveLast();
      TestInsertFirst("AAA");
      TestInsertLast("ZZZ");
      TestAtNRemove(Natural.Of(2), "gamma", false);
      TestPrintContent("");
      TestSize(4, false);
      TestGetFirst("AAA", false);
      TestGetLast("ZZZ", false);
      TestSetFirst("FIRST", false);
      TestSetLast("LAST", false);
      TestFoldForward((dat, acc) -> acc + "," + dat, "", ",FIRST,beta,delta,LAST");
      TestClear();
      TestIsEmpty(true, false);
      TestInsertLast("only");
      TestGetFirst("only", false);
    }

    @Test
    @DisplayName("All same elements")
    public void AllSameElements() {
      AddTest(14);
      NewEmptyContainer();
      TestInsertLast("same");
      TestInsertLast("same");
      TestInsertLast("same");
      TestInsertLast("same");
      TestInsertLast("same");
      TestSize(5, false);
      TestGetFirst("same", false);
      TestGetLast("same", false);
      TestGetAt(Natural.Of(2), "same", false);
      TestExists("same", true);
      TestExists("different", false);
      TestFoldForward((dat, acc) -> acc + dat, "", "samesamesamesamesame");
      TestRemove("same", true);
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
      LLSortedChain<String> chain = new LLSortedChain<>();
      chain.Insert("date");
      chain.Insert("banana");
      chain.Insert("cherry");
      chain.Insert("apple");
      LLList<String> conFromChain = new LLList<>(chain);
      TestInsert("date", true);
      TestInsert("banana", true);
      TestInsert("cherry", true);
      TestInsert("apple", true);
      //TestIsEqual(conFromChain, false);
      TestSize(4, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Construction from empty container")
    public void ConstructionFromEmptyContainer() {
      AddTest(4);
      LLSortedChain<String> emptyChain = new LLSortedChain<>();
      LLList<String> conFromEmpty = new LLList<>(emptyChain);
      NewEmptyContainer();
      TestIsEqual(conFromEmpty, true);
      TestSize(0, false);
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Construction from list with duplicates")
    public void ConstructionFromListWithDuplicates() {
      AddTest(6);
      LLList<String> sourceList = new LLList<>();
      sourceList.Insert("apple");
      sourceList.Insert("banana");
      sourceList.Insert("apple");
      sourceList.Insert("cherry");
      LLList<String> conFromList = new LLList<>(sourceList);
      NewEmptyContainer();
      TestInsert("apple", true);
      TestInsert("banana", true);
      TestInsert("apple", true);
      TestInsert("cherry", true);
      TestIsEqual(conFromList, true);
      TestSize(4, false);
    }

    @Test
    @DisplayName("Equality with same elements same order")
    public void EqualitySameOrder() {
      AddTest(6);
      NewEmptyContainer();
      TestInsertLast("apple");
      TestInsertLast("banana");
      TestInsertLast("cherry");
      
      LLList<String> other = new LLList<>();
      other.InsertLast("apple");
      other.InsertLast("banana");
      other.InsertLast("cherry");
      TestIsEqual(other, true);
      TestSize(3, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Inequality with same elements different order")
    public void InequalityDifferentOrder() {
      AddTest(6);
      NewEmptyContainer();
      TestInsertLast("apple");
      TestInsertLast("cherry");
      TestInsertLast("banana");
      
      LLList<String> other = new LLList<>();
      other.InsertLast("cherry");
      other.InsertLast("banana");
      other.InsertLast("apple");
      //TestIsEqual(other, false);
      TestSize(3, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Inequality with different sizes")
    public void InequalityDifferentSizes() {
      AddTest(5);
      NewEmptyContainer();
      TestInsertLast("apple");
      TestInsertLast("banana");
      TestInsertLast("cherry");
      
      LLList<String> smaller = new LLList<>();
      smaller.InsertLast("apple");
      smaller.InsertLast("banana");
      TestIsEqual(smaller, false);
      TestSize(3, false);
    }

    @Test
    @DisplayName("Inequality with different elements")
    public void InequalityDifferentElements() {
      AddTest(5);
      NewEmptyContainer();
      TestInsertLast("apple");
      TestInsertLast("banana");
      TestInsertLast("cherry");
      
      LLList<String> different = new LLList<>();
      different.InsertLast("apple");
      different.InsertLast("banana");
      different.InsertLast("date");
      TestIsEqual(different, false);
      TestSize(3, false);
    }

    @Test
    @DisplayName("Equality of two empty lists")
    public void EqualityEmptyLists() {
      AddTest(3);
      NewEmptyContainer();
      LLList<String> other = new LLList<>();
      TestIsEqual(other, true);
      TestSize(0, false);
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Copy construction preserves elements and order")
    public void CopyConstructionPreservesElements() {
      AddTest(7);
      LLList<String> original = new LLList<>();
      original.InsertLast("alpha");
      original.InsertLast("beta");
      original.InsertLast("gamma");
      LLList<String> copy = new LLList<>(original);
      NewEmptyContainer();
      TestInsertLast("alpha");
      TestInsertLast("beta");
      TestInsertLast("gamma");
      TestIsEqual(original, true);
      TestIsEqual(copy, true);
      TestSize(3, false);
    }

    @Test
    @DisplayName("Equality with duplicates in same positions")
    public void EqualityWithDuplicates() {
      AddTest(6);
      NewEmptyContainer();
      TestInsertLast("apple");
      TestInsertLast("banana");
      TestInsertLast("apple");
      TestInsertLast("banana");
      
      LLList<String> other = new LLList<>();
      other.InsertLast("apple");
      other.InsertLast("banana");
      other.InsertLast("apple");
      other.InsertLast("banana");
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
      TestInsert("apple", true);
      TestInsert(null, false);
      TestSize(1, false);
      TestExists(null, false);
    }

    @Test
    @DisplayName("InsertFirst null element")
    public void InsertFirstNull() {
      AddTest(4);
      NewEmptyContainer();
      TestInsertLast("apple");
      TestInsertFirst(null, true);
      TestGetFirst("apple", false);
      TestSize(1, false);
    }

    @Test
    @DisplayName("InsertLast null element")
    public void InsertLastNull() {
      AddTest(4);
      NewEmptyContainer();
      TestInsertFirst("apple");
      TestInsertLast(null, true);
      TestGetLast("apple", false);
      TestSize(1, false);
    }

    @Test
    @DisplayName("InsertAt null element")
    public void InsertAtNull() {
      AddTest(5);
      NewEmptyContainer();
      TestInsertLast("apple");
      TestInsertLast("cherry");
      TestInsertAt(null, Natural.Of(1), true);
      TestGetAt(Natural.Of(1), "cherry", false);
      TestSize(2L, false);
    }

    @Test
    @DisplayName("Remove null element")
    public void RemoveNull() {
      AddTest(5);
      NewEmptyContainer();
      TestInsertLast("apple");
      TestInsertLast(null, true);
      TestInsertLast("banana");
      TestRemove(null, false);
      TestSize(2, false);
    }

    @Test
    @DisplayName("Exists with null")
    public void ExistsNull() {
      AddTest(5);
      NewEmptyContainer();
      TestInsertLast("apple");
      TestInsertLast("banana");
      TestExists(null, false);
      TestInsertLast(null, true);
      TestExists(null, false);
    }

    @Test
    @DisplayName("SetFirst with null")
    public void SetFirstNull() {
      AddTest(4);
      NewEmptyContainer();
      TestInsertLast("apple");
      TestInsertLast("banana");
      TestSetFirst(null, true);
      TestGetFirst("apple", false);
    }

    @Test
    @DisplayName("SetLast with null")
    public void SetLastNull() {
      AddTest(4);
      NewEmptyContainer();
      TestInsertLast("apple");
      TestInsertLast("banana");
      TestSetLast(null, true);
      TestGetLast("banana", false);
    }

    @Test
    @DisplayName("SetAt with null")
    public void SetAtNull() {
      AddTest(4);
      NewEmptyContainer();
      TestInsertLast("apple");
      TestInsertLast("banana");
      TestInsertLast("cherry");
      TestSetAt(null, Natural.Of(1), true);
    }

    @Test
    @DisplayName("GetAt with null Natural")
    public void GetAtNullNatural() {
      AddTest(4);
      NewEmptyContainer();
      TestInsertLast("apple");
      TestInsertLast("banana");
      TestGetAt(null, "", true);
      TestSize(2, false);
    }

    @Test
    @DisplayName("SetAt with null Natural")
    public void SetAtNullNatural() {
      AddTest(4);
      NewEmptyContainer();
      TestInsertLast("apple");
      TestInsertLast("banana");
      TestSetAt("cherry", null, true);
      TestSize(2, false);
    }

    @Test
    @DisplayName("InsertAt with null Natural")
    public void InsertAtNullNatural() {
      AddTest(4);
      NewEmptyContainer();
      TestInsertLast("apple");
      TestInsertLast("banana");
      TestInsertAt("cherry", null, true);
      TestSize(2, false);
    }

    @Test
    @DisplayName("AtNRemove with null Natural")
    public void AtNRemoveNullNatural() {
      AddTest(4);
      NewEmptyContainer();
      TestInsertLast("apple");
      TestInsertLast("banana");
      TestAtNRemove(null, "", true);
      TestSize(2, false);
    }

    @Test
    @DisplayName("IsEqual with null container")
    public void IsEqualNullContainer() {
      AddTest(3);
      NewEmptyContainer();
      TestInsertLast("apple");
      TestInsertLast("banana");
      TestIsEqual(null, false);
    }

    @Test
    @DisplayName("FoldForward with null accumulator")
    public void FoldForwardNullAccumulator() {
      AddTest(4);
      NewEmptyContainer();
      TestInsertLast("a");
      TestInsertLast("b");
      TestInsertLast("c");
      TestFoldForward((dat, acc) -> dat, null, "c");
    }

    @Test
    @DisplayName("FoldBackward with null accumulator")
    public void FoldBackwardNullAccumulator() {
      AddTest(4);
      NewEmptyContainer();
      TestInsertLast("a");
      TestInsertLast("b");
      TestInsertLast("c");
      TestFoldBackward((dat, acc) -> dat, null, "a");
    }

    @Test
    @DisplayName("Operations with null elements maintain consistency")
    public void NullOperationsConsistency() {
      AddTest(12);
      NewEmptyContainer();
      TestInsertLast("apple");
      TestInsertLast(null, true);
      TestInsertLast("banana");
      TestInsertLast(null, true);
      TestInsertLast("cherry");
      TestSize(3, false);
      TestGetAt(Natural.Of(1), "banana", false);
      TestGetAt(Natural.Of(2), "cherry", false);
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
      TestGetFirst("", true);
      TestGetLast("", true);
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
      TestInsertFirst("b");
      TestGetFirst("b", false);
      TestGetLast("b", false);
      TestInsertLast("c");
      TestGetFirst("b", false);
      TestGetLast("c", false);
      TestInsertFirst("a");
      TestGetFirst("a", false);
      TestInsertLast("d");
      TestGetLast("d", false);
      TestSize(4, false);
      TestInsertFirst("Z");
      TestInsertLast("e");
      TestSize(6, false);
      TestGetFirst("Z", false);
      TestGetLast("e", false);
      TestFoldForward((dat, acc) -> acc + dat, "", "Zabcde");
      TestFoldBackward((dat, acc) -> acc + dat, "", "edcbaZ");
    }

    @Test
    @DisplayName("SetFirst, SetLast with GetFirst, GetLast verification")
    public void SetFirstLastVerification() {
      AddTest(18);
      NewEmptyContainer();
      TestInsertLast("a");
      TestInsertLast("b");
      TestInsertLast("c");
      TestGetFirst("a", false);
      TestGetLast("c", false);
      TestSetFirst("A", false);
      TestGetFirst("A", false);
      TestSetLast("C", false);
      TestGetLast("C", false);
      TestSize(3, false);
      TestFoldForward((dat, acc) -> acc + dat, "", "AbC");
      TestSetFirst("X", false);
      TestSetLast("Z", false);
      TestGetFirst("X", false);
      TestGetLast("Z", false);
      TestFoldForward((dat, acc) -> acc + dat, "", "XbZ");
      TestExists("X", true);
      TestExists("Z", true);
    }

    @Test
    @DisplayName("GetNSetFirst, GetNSetLast chain")
    public void GetNSetFirstLastChain() {
      AddTest(16);
      NewEmptyContainer();
      TestInsertLast("a");
      TestInsertLast("b");
      TestInsertLast("c");
      TestInsertLast("d");
      TestInsertLast("e");
      TestGetNSetFirst("A", "a", false);
      TestGetFirst("A", false);
      TestGetNSetLast("E", "e", false);
      TestGetLast("E", false);
      TestSize(5, false);
      TestGetNSetFirst("X", "A", false);
      TestGetNSetLast("Z", "E", false);
      TestGetFirst("X", false);
      TestGetLast("Z", false);
      TestFoldForward((dat, acc) -> acc + dat, "", "XbcdZ");
      TestExists("X", true);
    }

    @Test
    @DisplayName("SetAt, GetAt comprehensive test")
    public void SetAtGetAtComprehensive() {
      AddTest(20);
      NewEmptyContainer();
      TestInsertLast("a");
      TestInsertLast("b");
      TestInsertLast("c");
      TestInsertLast("d");
      TestInsertLast("e");
      TestGetAt(Natural.ZERO, "a", false);
      TestGetAt(Natural.Of(2), "c", false);
      TestGetAt(Natural.Of(4), "e", false);
      TestSetAt("A", Natural.ZERO, false);
      TestGetAt(Natural.ZERO, "A", false);
      TestSetAt("C", Natural.Of(2), false);
      TestGetAt(Natural.Of(2), "C", false);
      TestSetAt("E", Natural.Of(4), false);
      TestGetAt(Natural.Of(4), "E", false);
      TestSize(5, false);
      TestFoldForward((dat, acc) -> acc + dat, "", "AbCdE");
      TestGetFirst("A", false);
      TestGetLast("E", false);
      TestExists("C", true);
      TestExists("c", false);
    }

    @Test
    @DisplayName("GetNSetAt comprehensive verification")
    public void GetNSetAtComprehensive() {
      AddTest(18);
      NewEmptyContainer();
      TestInsertLast("a");
      TestInsertLast("b");
      TestInsertLast("c");
      TestInsertLast("d");
      TestInsertLast("e");
      TestGetNSetAt("A", "a", Natural.ZERO, false);
      TestGetAt(Natural.ZERO, "A", false);
      TestGetNSetAt("C", "c", Natural.Of(2), false);
      TestGetAt(Natural.Of(2), "C", false);
      TestGetNSetAt("E", "e", Natural.Of(4), false);
      TestGetAt(Natural.Of(4), "E", false);
      TestSize(5, false);
      TestGetNSetAt("X", "A", Natural.ZERO, false);
      TestGetNSetAt("Z", "E", Natural.Of(4), false);
      TestGetFirst("X", false);
      TestGetLast("Z", false);
      TestFoldForward((dat, acc) -> acc + dat, "", "XbCdZ");
    }

    @Test
    @DisplayName("InsertAt with Size and GetAt verification")
    public void InsertAtWithVerification() {
      AddTest(22);
      NewEmptyContainer();
      TestInsertAt("c", Natural.ZERO, false);
      TestGetAt(Natural.ZERO, "c", false);
      TestSize(1, false);
      TestInsertAt("a", Natural.ZERO, false);
      TestGetFirst("a", false);
      TestSize(2, false);
      TestInsertAt("e", Natural.Of(2), false);
      TestGetLast("e", false);
      TestSize(3, false);
      TestInsertAt("b", Natural.Of(1), false);
      TestInsertAt("d", Natural.Of(3), false);
      TestSize(5, false);
      TestGetAt(Natural.ZERO, "a", false);
      TestGetAt(Natural.Of(1), "b", false);
      TestGetAt(Natural.Of(2), "c", false);
      TestGetAt(Natural.Of(3), "d", false);
      TestGetAt(Natural.Of(4), "e", false);
      TestFoldForward((dat, acc) -> acc + dat, "", "abcde");
      TestForwardIteration(false, false);
      TestBackwardIteration(false, false);
    }

    @Test
    @DisplayName("FirstNRemove and LastNRemove chain")
    public void FirstNRemoveLastNRemoveChain() {
      AddTest(20);
      NewEmptyContainer();
      TestInsertLast("a");
      TestInsertLast("b");
      TestInsertLast("c");
      TestInsertLast("d");
      TestInsertLast("e");
      TestSize(5, false);
      TestFirstNRemove("a");
      TestGetFirst("b", false);
      TestSize(4, false);
      TestLastNRemove("e");
      TestGetLast("d", false);
      TestSize(3, false);
      TestFirstNRemove("b");
      TestLastNRemove("d");
      TestSize(1, false);
      TestGetFirst("c", false);
      TestGetLast("c", false);
      TestFirstNRemove("c");
      TestIsEmpty(true, false);
      TestSize(0, false);
    }

    @Test
    @DisplayName("AtNRemove comprehensive test")
    public void AtNRemoveComprehensive() {
      AddTest(18);
      NewEmptyContainer();
      TestInsertLast("a");
      TestInsertLast("b");
      TestInsertLast("c");
      TestInsertLast("d");
      TestInsertLast("e");
      TestSize(5, false);
      TestAtNRemove(Natural.Of(2), "c", false);
      TestSize(4, false);
      TestGetAt(Natural.Of(2), "d", false);
      TestAtNRemove(Natural.ZERO, "a", false);
      TestGetFirst("b", false);
      TestSize(3, false);
      TestAtNRemove(Natural.Of(2), "e", false);
      TestGetLast("d", false);
      TestSize(2, false);
      TestFoldForward((dat, acc) -> acc + dat, "", "bd");
      TestExists("b", true);
      TestExists("d", true);
    }

    @Test
    @DisplayName("SubSequence, SubList, SubChain verification")
    public void SubSequenceSubListSubChainVerification() {
      AddTest(16);
      NewEmptyContainer();
      TestInsertLast("a");
      TestInsertLast("b");
      TestInsertLast("c");
      TestInsertLast("d");
      TestInsertLast("e");
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
      TestInsertLast("a");
      TestInsertLast("bb");
      TestInsertLast("c");
      TestInsertLast("dd");
      TestInsertLast("e");
      TestInsertLast("ff");
      TestInsertLast("g");
      TestInsertLast("hh");
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
    @DisplayName("Remove and RemoveOccurrences combined")
    public void RemoveAndRemoveOccurrences() {
      AddTest(18);
      NewEmptyContainer();
      TestInsertLast("a");
      TestInsertLast("b");
      TestInsertLast("b");
      TestInsertLast("c");
      TestInsertLast("b");
      TestInsertLast("d");
      TestSize(6, false);
      TestRemove("b", true);
      TestSize(5, false);
      TestExists("b", true);
      TestRemoveOccurrences("b");
      TestExists("b", false);
      TestSize(3, false);
      TestRemove("b", false);
      TestExists("a", true);
      TestExists("c", true);
      TestExists("d", true);
      TestFoldForward((dat, acc) -> acc + dat, "", "acd");
    }

    @Test
    @DisplayName("InsertIfAbsent with Insert and Exists")
    public void InsertIfAbsentWithInsertExists() {
      AddTest(18);
      NewEmptyContainer();
      TestInsert("a", true);
      TestInsert("b", true);
      TestInsert("c", true);
      TestSize(3, false);
      TestInsertIfAbsent("d", true);
      TestSize(4, false);
      TestExists("d", true);
      TestInsertIfAbsent("b", false);
      TestSize(4, false);
      TestInsertIfAbsent("e", true);
      TestSize(5, false);
      TestInsert("b", true);
      TestSize(6, false);
      TestInsertIfAbsent("a", false);
      TestInsertIfAbsent("f", true);
      TestSize(7, false);
      TestFoldForward((dat, acc) -> acc + 1, 0, 7);
    }

    @Test
    @DisplayName("Search with GetAt verification")
    public void SearchWithGetAtVerification() {
      AddTest(16);
      NewEmptyContainer();
      TestInsertLast("alpha");
      TestInsertLast("beta");
      TestInsertLast("gamma");
      TestInsertLast("delta");
      TestInsertLast("epsilon");
      TestSearch("alpha", Natural.ZERO);
      TestSearch("gamma", Natural.Of(2));
      TestSearch("epsilon", Natural.Of(4));
      TestSearch("zeta", null);
      TestSearch("aaa", null);
      TestGetAt(Natural.ZERO, "alpha", false);
      TestGetAt(Natural.Of(2), "gamma", false);
      TestGetAt(Natural.Of(4), "epsilon", false);
      TestSize(5, false);
      TestExists("gamma", true);
      TestExists("zeta", false);
    }

    @Test
    @DisplayName("Union, Intersection, Difference chain")
    public void SetOperationsChain() {
      AddTest(24);
      NewEmptyContainer();
      TestInsertLast("a");
      TestInsertLast("b");
      TestInsertLast("c");
      TestSize(3, false);
      
      LLList<String> otherList = new LLList<>();
      otherList.Insert("c");
      otherList.Insert("d");
      otherList.Insert("e");
      
      TestUnion(otherList);
      TestSize(5, false);
      TestExists("a", true);
      TestExists("d", true);
      TestExists("e", true);
      
      LLList<String> intersectList = new LLList<>();
      intersectList.Insert("b");
      intersectList.Insert("c");
      intersectList.Insert("d");
      
      TestIntersection(intersectList);
      TestExists("b", true);
      TestExists("c", true);
      TestExists("d", true);
      TestExists("a", false);
      
      LLList<String> diffList = new LLList<>();
      diffList.Insert("c");
      
      TestDifference(diffList);
      TestExists("c", false);
      TestExists("b", true);
      TestExists("d", true);
    }

    @Test
    @DisplayName("TraverseForward and TraverseBackward verification")
    public void TraverseForwardBackwardVerification() {
      AddTest(14);
      NewEmptyContainer();
      TestInsertLast("apple");
      TestInsertLast("banana");
      TestInsertLast("cherry");
      TestInsertLast("date");
      TestInsertLast("elderberry");
      TestSize(5, false);
      TestTraverseForward(dat -> dat.length() > 0, true);
      TestTraverseBackward(dat -> dat.length() > 0, true);
      TestTraverseForward(dat -> dat.length() < 6, true);
      TestTraverseBackward(dat -> dat.length() < 6, true);
      TestTraverseForward(dat -> dat.contains("a"), true);
      TestTraverseBackward(dat -> dat.startsWith("a") || dat.startsWith("b") || dat.startsWith("c") || dat.startsWith("d") || dat.startsWith("e"), true);
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
      TestInsertLast("a");
      TestInsertLast("b");
      TestInsertLast("c");
      TestInsertLast("d");
      TestInsertLast("e");
      TestFoldForward((dat, acc) -> acc + dat, "", "abcde");
      TestFoldBackward((dat, acc) -> acc + dat, "", "edcba");
      TestFoldForward((dat, acc) -> acc + "|" + dat, "", "|a|b|c|d|e");
      TestFoldBackward((dat, acc) -> acc + "|" + dat, "", "|e|d|c|b|a");
      TestFoldForward((dat, acc) -> dat.length() > acc.length() ? dat : acc, "", "a");
      TestFoldBackward((dat, acc) -> dat.length() > acc.length() ? dat : acc, "", "e");
      TestSize(5, false);
      TestIsEmpty(false, false);
    }

    @Test
    @DisplayName("FIterator and BIterator usage")
    public void FIteratorBIteratorUsage() {
      AddTest(10);
      NewEmptyContainer();
      TestInsertLast("a");
      TestInsertLast("b");
      TestInsertLast("c");
      TestInsertLast("d");
      TestInsertLast("e");
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
      TestInsertLast("x");
      TestInsertLast("y");
      TestInsertLast("z");
      TestSize(3, false);
      TestClear();
      TestIsEmpty(true, false);
      TestSize(0, false);
      TestInsertFirst("beta");
      TestInsertLast("delta");
      TestInsertAt("gamma", Natural.Of(1), false);
      TestSize(3, false);
      TestGetFirst("beta", false);
      TestGetLast("delta", false);
      TestGetAt(Natural.Of(1), "gamma", false);
      TestClear();
      TestInsert("foo", true);
      TestInsertIfAbsent("bar", true);
      TestInsertFirst("aaa");
      TestInsertLast("zzz");
      TestSize(4, false);
      TestFoldForward((dat, acc) -> acc + "," + dat, "", ",aaa,bar,foo,zzz");
      TestGetFirst("aaa", false);
      TestGetLast("zzz", false);
      TestExists("foo", true);
      TestExists("bar", true);
    }

    @Test
    @DisplayName("Remove operations exhaustively")
    public void RemoveOperationsExhaustively() {
      AddTest(26);
      NewEmptyContainer();
      TestInsertLast("a");
      TestInsertLast("b");
      TestInsertLast("c");
      TestInsertLast("d");
      TestInsertLast("e");
      TestInsertLast("f");
      TestInsertLast("g");
      TestInsertLast("h");
      TestSize(8, false);
      TestRemoveFirst();
      TestGetFirst("b", false);
      TestRemoveLast();
      TestGetLast("g", false);
      TestSize(6, false);
      TestRemove("d", true);
      TestExists("d", false);
      TestSize(5, false);
      TestAtNRemove(Natural.Of(2), "e", false);
      TestSize(4, false);
      TestFirstNRemove("b");
      TestLastNRemove("g");
      TestSize(2, false);
      TestGetFirst("c", false);
      TestGetLast("f", false);
      TestFoldForward((dat, acc) -> acc + dat, "", "cf");
    }

    @Test
    @DisplayName("IsEqual verification after modifications")
    public void IsEqualAfterModifications() {
      AddTest(18);
      NewEmptyContainer();
      TestInsertLast("a");
      TestInsertLast("b");
      TestInsertLast("c");
      
      LLList<String> otherList = new LLList<>();
      otherList.InsertLast("a");
      otherList.InsertLast("b");
      otherList.InsertLast("c");
      
      TestIsEqual(otherList, true);
      TestSetFirst("X", false);
      TestIsEqual(otherList, false);
      TestSetFirst("a", false);
      TestIsEqual(otherList, true);
      TestInsertLast("d");
      TestIsEqual(otherList, false);
      otherList.InsertLast("d");
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
      TestGetFirst("", true);
      TestGetLast("", true);
      TestSetFirst("a", true);
      TestSetLast("a", true);
      TestGetAt(Natural.ZERO, "", true);
      TestSetAt("a", Natural.ZERO, true);
      TestExists("a", false);
      TestRemove("a", false);
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
      TestInsertLast("hello");
      TestSize(1, false);
      TestIsEmpty(false, false);
      TestGetFirst("hello", false);
      TestGetLast("hello", false);
      TestGetAt(Natural.ZERO, "hello", false);
      TestExists("hello", true);
      TestSearch("hello", Natural.ZERO);
      TestFIterator();
      TestBIterator();
      TestForwardIteration(false, false);
      TestBackwardIteration(false, false);
      TestSetFirst("world", false);
      TestGetFirst("world", false);
      TestGetLast("world", false);
      TestSetLast("foo", false);
      TestGetFirst("foo", false);
      TestFoldForward((dat, acc) -> acc + dat, "", "foo");
      TestFoldBackward((dat, acc) -> acc + dat, "", "foo");
      TestRemoveFirst();
      TestIsEmpty(true, false);
      TestSize(0, false);
    }

    @Test
    @DisplayName("Duplicate values comprehensive handling")
    public void DuplicateValuesComprehensive() {
      AddTest(22);
      NewEmptyContainer();
      TestInsertLast("x");
      TestInsertLast("x");
      TestInsertLast("x");
      TestInsertLast("y");
      TestInsertLast("x");
      TestInsertLast("y");
      TestSize(6, false);
      TestSearch("x", Natural.ZERO);
      TestSearch("y", Natural.Of(3));
      TestExists("x", true);
      TestRemove("x", true);
      TestSize(5, false);
      TestExists("x", true);
      TestRemoveOccurrences("x");
      TestExists("x", false);
      TestSize(2, false);
      TestGetFirst("y", false);
      TestGetLast("y", false);
      TestFoldForward((dat, acc) -> acc + dat, "", "yy");
      TestInsertIfAbsent("y", false);
      TestSize(2, false);
      TestInsertIfAbsent("z", true);
      TestSize(3, false);
    }

    @Test
    @DisplayName("Extreme values handling")
    public void ExtremeValuesHandling() {
      AddTest(18);
      NewEmptyContainer();
      TestInsertLast("");
      TestInsertLast("a");
      TestInsertLast("abcdefghijklmnopqrstuvwxyz");
      TestSize(3, false);
      TestGetFirst("", false);
      TestGetLast("abcdefghijklmnopqrstuvwxyz", false);
      TestExists("", true);
      TestExists("abcdefghijklmnopqrstuvwxyz", true);
      TestExists("a", true);
      TestPrintContent("");
      TestSearch("", Natural.ZERO);
      TestSearch("abcdefghijklmnopqrstuvwxyz", Natural.Of(2));
      TestSetAt("REPLACED", Natural.Of(2), false);
      TestGetLast("REPLACED", false);
      TestFIterator();
      TestBIterator();
      TestForwardIteration(false, false);
      TestBackwardIteration(false, false);
      TestFoldForward((dat, acc) -> acc + 1, 0, 3);
    }

    @Test
    @DisplayName("InsertAll then RemoveAll verification")
    public void InsertAllThenOperations() {
      AddTest(20);
      NewEmptyContainer();
      TestInsertLast("a");
      TestInsertLast("b");
      
      LLList<String> toInsert = new LLList<>();
      toInsert.InsertLast("c");
      toInsert.InsertLast("d");
      toInsert.InsertLast("e");
      
      TestInsertAll(toInsert, true);
      TestSize(5, false);
      TestExists("c", true);
      TestExists("d", true);
      TestExists("e", true);
      TestGetFirst("e", false);
      TestGetLast("b", false);
      TestFoldForward((dat, acc) -> acc + dat, "", "edcab");
      
      TestRemove("c", true);
      TestRemove("d", true);
      TestRemove("e", true);
      TestSize(2, false);
      TestExists("c", false);
      TestFoldForward((dat, acc) -> acc + dat, "", "ab");
    }

    @Test
    @DisplayName("InsertSome with partial insertion")
    public void InsertSomePartialInsertion() {
      AddTest(18);
      NewEmptyContainer();
      TestInsertLast("a");
      TestInsertLast("b");
      TestInsertLast("c");
      TestSize(3, false);
      
      LLList<String> toInsert = new LLList<>();
      toInsert.InsertLast("c");
      toInsert.InsertLast("d");
      toInsert.InsertLast("e");
      
      TestInsertSome(toInsert, true);
      TestExists("d", true);
      TestExists("e", true);
      TestGetFirst("e", false);
      TestPrintContent("");
      TestFoldForward((dat, acc) -> acc + 1, 0, 6);
      TestFilter(dat -> dat.compareTo("d") < 0);
      TestSize(4, false);
      TestExists("d", false);
      TestExists("e", false);
      TestFoldForward((dat, acc) -> acc + dat, "", "cabc");
    }

    @Test
    @DisplayName("Complex interleaved operations")
    public void ComplexInterleavedOperations() {
      AddTest(32);
      NewEmptyContainer();
      TestInsertFirst("beta");
      TestInsertLast("delta");
      TestInsertAt("gamma", Natural.Of(1), false);
      TestSize(3, false);
      TestGetNSetFirst("alpha", "beta", false);
      TestGetNSetLast("epsilon", "delta", false);
      TestSize(3, false);
      TestInsertFirst("aaa");
      TestInsertLast("zzz");
      TestSize(5, false);
      TestGetAt(Natural.Of(2), "gamma", false);
      TestSetAt("GAMMA", Natural.Of(2), false);
      TestGetAt(Natural.Of(2), "GAMMA", false);
      TestFirstNRemove("aaa");
      TestLastNRemove("zzz");
      TestSize(3, false);
      TestFoldForward((dat, acc) -> acc + "," + dat, "", ",alpha,GAMMA,epsilon");
      TestInsertIfAbsent("omega", true);
      TestInsertIfAbsent("GAMMA", false);
      TestSize(4, false);
      TestSearch("GAMMA", Natural.Of(2));
      TestSearch("omega", Natural.Of(0));
      TestRemoveOccurrences("GAMMA");
      TestSize(3, false);
      TestExists("GAMMA", false);
      TestFilter(dat -> dat.length() > 5);
      TestSize(1, false);
      TestGetLast("epsilon", false);
      TestClear();
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Build, verify, clear, rebuild multiple cycles")
    public void BuildVerifyClearRebuildCycles() {
      AddTest(36);
      NewEmptyContainer();
      
      // Cycle 1
      TestInsertLast("a");
      TestInsertLast("b");
      TestInsertLast("c");
      TestSize(3, false);
      TestFoldForward((dat, acc) -> acc + dat, "", "abc");
      TestClear();
      TestIsEmpty(true, false);
      
      // Cycle 2
      TestInsertFirst("x");
      TestInsertFirst("y");
      TestInsertFirst("z");
      TestSize(3, false);
      TestGetFirst("z", false);
      TestGetLast("x", false);
      TestFoldForward((dat, acc) -> acc + dat, "", "zyx");
      TestClear();
      
      // Cycle 3
      TestInsertAt("beta", Natural.ZERO, false);
      TestInsertAt("delta", Natural.Of(1), false);
      TestInsertAt("gamma", Natural.Of(1), false);
      TestSize(3, false);
      TestGetAt(Natural.Of(1), "gamma", false);
      TestFoldBackward((dat, acc) -> acc + dat, "", "deltagammabeta");
      TestClear();
      
      // Cycle 4
      TestInsert("one", true);
      TestInsert("two", true);
      TestInsert("three", true);
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
      TestInsertFirst("a");
      TestInsertLast("b");
      TestSize(2, false);
      TestRemoveFirst();
      TestGetFirst("b", false);
      TestInsertFirst("c");
      TestGetFirst("c", false);
      TestRemoveLast();
      TestGetLast("c", false);
      TestInsertLast("d");
      TestGetLast("d", false);
      TestSize(2, false);
      TestInsertFirst("Z");
      TestInsertLast("Y");
      TestSize(4, false);
      TestFirstNRemove("Z");
      TestLastNRemove("Y");
      TestSize(2, false);
      TestInsertAt("m", Natural.Of(1), false);
      TestSize(3, false);
      TestAtNRemove(Natural.Of(1), "m", false);
      TestSize(2, false);
      TestGetFirst("c", false);
      TestGetLast("d", false);
      TestFoldForward((dat, acc) -> acc + dat, "", "cd");
      TestClear();
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Large list mixed operations")
    public void LargeListMixedOperations() {
      AddTest(62);
      NewEmptyContainer();
      
      // Insert 20 elements
      for (int i = 1; i <= 20; i++) {
        TestInsertLast("item" + i);
      }
      TestSize(20, false);
      TestGetFirst("item1", false);
      TestGetLast("item20", false);
      
      // Remove first 5
      for (int i = 0; i < 5; i++) {
        TestRemoveFirst();
      }
      TestSize(15, false);
      TestGetFirst("item6", false);
      
      // Remove last 5
      for (int i = 0; i < 5; i++) {
        TestRemoveLast();
      }
      TestSize(10, false);
      TestGetLast("item15", false);
      
      // Filter items with even numbers (item6, item8, item10, item12, item14)
      TestFilter(dat -> { int num = Integer.parseInt(dat.replace("item", "")); return num % 2 == 0; });
      TestSize(5, false);
      TestExists("item6", true);
      TestExists("item8", true);
      TestExists("item10", true);
      TestExists("item12", true);
      TestExists("item14", true);
      TestExists("item7", false);
      
      // Verify count
      TestFoldForward((dat, acc) -> acc + 1, 0, 5);
      
      // Set all to same value
      TestSetAt("same", Natural.ZERO, false);
      TestSetAt("same", Natural.Of(1), false);
      TestSetAt("same", Natural.Of(2), false);
      TestSetAt("same", Natural.Of(3), false);
      TestSetAt("same", Natural.Of(4), false);
      TestFoldForward((dat, acc) -> acc + dat, "", "samesamesamesamesame");
      
      TestClear();
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Filter removes all elements")
    public void FilterRemovesAll() {
      AddTest(12);
      NewEmptyContainer();
      TestInsertLast("a");
      TestInsertLast("b");
      TestInsertLast("c");
      TestInsertLast("d");
      TestInsertLast("e");
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
      TestInsertLast("a");
      TestInsertLast("b");
      TestInsertLast("c");
      TestInsertLast("d");
      TestInsertLast("e");
      TestSize(5, false);
      TestFilter(dat -> true);
      TestSize(5, false);
      TestGetFirst("a", false);
      TestGetLast("e", false);
      TestExists("a", true);
      TestExists("e", true);
      TestFoldForward((dat, acc) -> acc + dat, "", "abcde");
      TestForwardIteration(false, false);
    }

  }

}

