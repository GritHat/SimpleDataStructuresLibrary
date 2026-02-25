package zapsdtest.simpletest.apsd.classes.containers.sequences.generic;

import apsd.classes.containers.collections.concretecollections.LLList;
import apsd.classes.containers.sequences.CircularVector;
import apsd.classes.containers.sequences.DynCircularVector;
import apsd.classes.containers.sequences.DynVector;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Random;

import org.junit.jupiter.api.*;


abstract public class XDynVectorSTest extends XDynVectorTest<String> {

  @Override
  public void NewNonEmptyContainer() {
    AddTest(13);
    NewEmptyContainer();
    TestSetFirst("zero", true);
    TestExpandWithNumber(Natural.Of(1));
    TestSetFirst("zero", false);
    TestExpandWithNumber(Natural.Of(4));
    TestSize(5, false);
    TestSwap(Natural.ZERO, Natural.Of(3), false);
    TestSetFirst("four", false);
    TestSetAt("three", Natural.Of(1), false);
    TestSetAt("two", Natural.Of(2), false);
    TestSetAt("one", Natural.Of(5), true);
    TestSetLast("one", false);
    TestSize(5, false);
    TestPrintContent("Initial Dynamic Vector");
  }

  @Nested
  @DisplayName("Vector Basics")
  public class VectorBasics {

    @Test
    @DisplayName("Check starting from an Empty Vector")
    public void Empty() {
      AddTest(9);
      NewEmptyContainer();
      TestGetFirst("", true);
      TestGetLast("", true);
      TestSetFirst("", true);
      TestSetLast("", true);
      TestGetAt(Natural.Of(1), "", true);
      TestSetAt("", Natural.Of(2), true);
      TestExists("four", false);
      TestPrintContent("");
      TestFoldBackward((dat, acc) -> acc + dat, "", "");
    }

    @Test
    @DisplayName("Check starting from a NonEmpty Vector")
    public void NonEmpty() {
      AddTest(28);
      NewNonEmptyContainer();
      // State: [four, three, two, zero, one]
      TestGetFirst("four", false);
      TestGetLast("one", false);
      TestSetFirst("five", false);
      TestSetLast("four", false);
      TestExists("four", true);
      TestPrintContent("");
      TestFoldForward((dat, acc) -> { if (dat != null) return acc + "," + dat; else return acc; }, "", ",five,three,two,zero,four");
      TestFoldForward((dat, acc) -> { if (dat != null) return acc + 1; else return acc; }, 0, 5);
      TestGetAt(Natural.Of(3), "zero", false);
      TestSetAt("one", Natural.Of(3), false);
      TestFoldForward((dat, acc) -> dat != null ? acc + "," + dat : acc, "", ",five,three,two,one,four");
      TestPrintContent("");
      TestRealloc(Natural.Of(2));
      TestPrintContent("");
      TestFoldForward((dat, acc) -> acc + "," + dat, "", ",five,three");
      TestRealloc(Natural.Of(4));
      TestPrintContent("");
      TestShiftRight(Natural.ONE, Natural.Of(2));
      TestPrintContent("");
      TestSize(4L, false);
      TestShiftLeft(Natural.ZERO, Natural.ONE);
      TestSize(3L, false);
      TestShiftRight(Natural.ZERO, Natural.Of(2));
      TestPrintContent("");
      TestShiftFirstRight();
      TestShiftLastRight();
      TestSize(7L, false);
      TestPrintContent("");
      TestClear();
      TestSize(0L, false);
    }

  }


  @Nested
  @DisplayName("DynVector Edge Cases")
  public class DynVectorEdgeCases {

    @Test
    @DisplayName("Expand from zero")
    public void ExpandFromZero() {
      AddTest(8);
      NewEmptyContainer();
      TestSize(0, false);
      TestExpandWithNumber(Natural.Of(5));
      TestSize(5, false);
      TestSetAt("apple", Natural.ZERO, false);
      TestSetAt("banana", Natural.Of(4), false);
      TestGetAt(Natural.ZERO, "apple", false);
      TestGetAt(Natural.Of(4), "banana", false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Multiple expansions")
    public void MultipleExpansions() {
      AddTest(10);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(2));
      TestSetAt("alpha", Natural.ZERO, false);
      TestSetAt("beta", Natural.Of(1), false);
      TestExpandWithNumber(Natural.Of(3));
      TestSize(5, false);
      TestGetAt(Natural.ZERO, "alpha", false);
      TestGetAt(Natural.Of(1), "beta", false);
      TestSetAt("epsilon", Natural.Of(4), false);
      TestGetAt(Natural.Of(4), "epsilon", false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Shrink preserves front elements")
    public void ShrinkPreservesFront() {
      AddTest(8);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestSetAt("alpha", Natural.ZERO, false);
      TestSetAt("beta", Natural.Of(1), false);
      TestSetAt("gamma", Natural.Of(2), false);
      TestRealloc(Natural.Of(2));
      TestSize(2, false);
      TestGetAt(Natural.ZERO, "alpha", false);
      TestGetAt(Natural.Of(1), "beta", false);
    }

    @Test
    @DisplayName("ShiftLeft reduces size")
    public void ShiftLeftReducesSize() {
      AddTest(11);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestSetAt("e", Natural.Of(4), false);
      TestSize(5, false);
      TestShiftLeft(Natural.Of(2), Natural.Of(2));
      TestSize(3, false);
      TestPrintContent("");
      TestFoldForward((dat, acc) -> { if (dat != null) return acc + "," + dat; else return acc; }, "", ",a,b,e");
    }

    @Test
    @DisplayName("ShiftRight increases size")
    public void ShiftRightIncreasesSize() {
      AddTest(11);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(3));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSize(3, false);
      TestShiftRight(Natural.ONE, Natural.Of(2));
      TestSize(5, false);
      TestGetAt(Natural.ZERO, "a", false);
      TestGetAt(Natural.Of(3), "b", false);
      TestGetAt(Natural.Of(4), "c", false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Insert at with expansion")
    public void InsertAtWithExpansion() {
      AddTest(11);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(3));
      TestSetAt("alpha", Natural.ZERO, false);
      TestSetAt("gamma", Natural.Of(1), false);
      TestSetAt("epsilon", Natural.Of(2), false);
      TestInsertAtWithAutoExpansion(Natural.ONE, "beta");
      TestSize(4, false);
      TestGetAt(Natural.ZERO, "alpha", false);
      TestGetAt(Natural.Of(1), "beta", false);
      TestGetAt(Natural.Of(2), "gamma", false);
      TestGetAt(Natural.Of(3), "epsilon", false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Remove at with reduction")
    public void RemoveAtWithReduction() {
      AddTest(12);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestSetAt("e", Natural.Of(4), false);
      TestAtNRemoveWithAutoReduction(Natural.Of(2), "c");
      TestSize(4, false);
      TestGetAt(Natural.ZERO, "a", false);
      TestGetAt(Natural.Of(1), "b", false);
      TestGetAt(Natural.Of(2), "d", false);
      TestGetAt(Natural.Of(3), "e", false);
    }

    @Test
    @DisplayName("Reduce by number")
    public void ReduceByNumber() {
      AddTest(8);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(10));
      TestSetAt("first", Natural.ZERO, false);
      TestSetAt("second", Natural.Of(1), false);
      TestSetAt("third", Natural.Of(2), false);
      TestSize(10, false);
      TestReduceWithNumber(Natural.Of(3));
      TestSize(7, false);
      TestGetAt(Natural.ZERO, "first", false);
    }

    @Test
    @DisplayName("Reduce to zero")
    public void ReduceToZero() {
      AddTest(5);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestSize(5, false);
      TestReduceWithNumber(Natural.Of(5));
      TestSize(0, false);
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Reduce by zero does nothing")
    public void ReduceByZero() {
      AddTest(6);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestSetAt("hello", Natural.Of(2), false);
      TestSize(5, false);
      TestReduceWithNumber(Natural.ZERO);
      TestSize(5, false);
    }

    @Test
    @DisplayName("Multiple reduce operations")
    public void MultipleReduces() {
      AddTest(10);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(10));
      TestSetAt("first", Natural.ZERO, false);
      TestSetAt("second", Natural.Of(1), false);
      TestSize(10, false);
      TestReduceWithNumber(Natural.Of(2));
      TestSize(8, false);
      TestReduceWithNumber(Natural.Of(3));
      TestSize(5, false);
      TestReduceWithNumber(Natural.Of(5));
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Expand then reduce preserves front elements")
    public void ExpandThenReduce() {
      AddTest(10);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestSetAt("apple", Natural.ZERO, false);
      TestSetAt("banana", Natural.Of(1), false);
      TestSetAt("cherry", Natural.Of(2), false);
      TestSetAt("date", Natural.Of(3), false);
      TestSetAt("elderberry", Natural.Of(4), false);
      TestReduceWithNumber(Natural.Of(2));
      TestSize(3, false);
      TestGetAt(Natural.ZERO, "apple", false);
      TestGetAt(Natural.Of(2), "cherry", false);
    }

  }

  @Nested
  @DisplayName("DynVector Stress Tests")
  public class DynVectorStressTests {

    @Test
    @DisplayName("Rapid expand and shrink cycles")
    public void RapidExpandShrinkCycles() {
      AddTest(21);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(10));
      TestSetAt("first", Natural.ZERO, false);
      TestSetAt("last", Natural.Of(9), false);
      TestSize(10, false);
      TestRealloc(Natural.Of(5));
      TestSize(5, false);
      TestGetAt(Natural.ZERO, "first", false);
      TestExpandWithNumber(Natural.Of(15));
      TestSize(20, false);
      TestGetAt(Natural.ZERO, "first", false);
      TestRealloc(Natural.Of(1));
      TestSize(1, false);
      TestGetAt(Natural.ZERO, "first", false);
      TestExpandWithNumber(Natural.Of(99));
      TestSetAt("century", Natural.Of(99), false);
      TestRealloc(Natural.Of(50));
      TestSize(50, false);
      TestRealloc(Natural.ZERO);
      TestIsEmpty(true, false);
      TestExpandWithNumber(Natural.Of(3));
      TestSize(3, false);
    }

    @Test
    @DisplayName("Insert at beginning repeatedly")
    public void InsertAtBeginningRepeatedly() {
      AddTest(14);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(1));
      TestSetAt("e", Natural.ZERO, false);
      TestInsertAtWithAutoExpansion(Natural.ZERO, "d");
      TestInsertAtWithAutoExpansion(Natural.ZERO, "c");
      TestInsertAtWithAutoExpansion(Natural.ZERO, "b");
      TestInsertAtWithAutoExpansion(Natural.ZERO, "a");
      TestSize(5, false);
      TestGetAt(Natural.ZERO, "a", false);
      TestGetAt(Natural.Of(1), "b", false);
      TestGetAt(Natural.Of(2), "c", false);
      TestGetAt(Natural.Of(3), "d", false);
      TestGetAt(Natural.Of(4), "e", false);
      TestFoldForward((dat, acc) -> acc + dat, "", "abcde");
      TestPrintContent("");
    }

    @Test
    @DisplayName("Insert at end repeatedly")
    public void InsertAtEndRepeatedly() {
      AddTest(14);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(1));
      TestSetAt("a", Natural.ZERO, false);
      TestInsertAtWithAutoExpansion(Natural.ONE, "b");
      TestInsertAtWithAutoExpansion(Natural.Of(2), "c");
      TestInsertAtWithAutoExpansion(Natural.Of(3), "d");
      TestInsertAtWithAutoExpansion(Natural.Of(4), "e");
      TestSize(5, false);
      TestGetAt(Natural.ZERO, "a", false);
      TestGetAt(Natural.Of(1), "b", false);
      TestGetAt(Natural.Of(2), "c", false);
      TestGetAt(Natural.Of(3), "d", false);
      TestGetAt(Natural.Of(4), "e", false);
      TestFoldForward((dat, acc) -> acc + dat, "", "abcde");
      TestPrintContent("");
    }

    @Test
    @DisplayName("Remove from beginning repeatedly")
    public void RemoveFromBeginningRepeatedly() {
      AddTest(14);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestSetAt("e", Natural.Of(4), false);
      TestAtNRemoveWithAutoReduction(Natural.ZERO, "a");
      TestSize(4, false);
      TestAtNRemoveWithAutoReduction(Natural.ZERO, "b");
      TestSize(3, false);
      TestAtNRemoveWithAutoReduction(Natural.ZERO, "c");
      TestSize(2, false);
      TestAtNRemoveWithAutoReduction(Natural.ZERO, "d");
      TestSize(1, false);
      TestAtNRemoveWithAutoReduction(Natural.ZERO, "e");
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Remove from end repeatedly")
    public void RemoveFromEndRepeatedly() {
      AddTest(14);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestSetAt("e", Natural.Of(4), false);
      TestAtNRemoveWithAutoReduction(Natural.Of(4), "e");
      TestSize(4, false);
      TestAtNRemoveWithAutoReduction(Natural.Of(3), "d");
      TestSize(3, false);
      TestAtNRemoveWithAutoReduction(Natural.Of(2), "c");
      TestSize(2, false);
      TestAtNRemoveWithAutoReduction(Natural.Of(1), "b");
      TestSize(1, false);
      TestAtNRemoveWithAutoReduction(Natural.ZERO, "a");
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Alternating insert and remove")
    public void AlternatingInsertRemove() {
      AddTest(17);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(3));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestInsertAtWithAutoExpansion(Natural.ONE, "X");
      TestSize(4, false);
      TestAtNRemoveWithAutoReduction(Natural.ONE, "X");
      TestSize(3, false);
      TestInsertAtWithAutoExpansion(Natural.Of(2), "Y");
      TestSize(4, false);
      TestGetAt(Natural.ZERO, "a", false);
      TestGetAt(Natural.Of(1), "b", false);
      TestGetAt(Natural.Of(2), "Y", false);
      TestGetAt(Natural.Of(3), "c", false);
      TestAtNRemoveWithAutoReduction(Natural.Of(2), "Y");
      TestSize(3, false);
      TestFoldForward((dat, acc) -> acc + dat, "", "abc");
      TestPrintContent("");
    }

    @Test
    @DisplayName("Expand with zero")
    public void ExpandWithZero() {
      AddTest(4);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(3));
      TestSize(3, false);
      TestExpandWithNumber(Natural.ZERO);
      TestSize(3, false);
    }

    @Test
    @DisplayName("ShiftRight at last position")
    public void ShiftRightAtLastPosition() {
      AddTest(11);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(3));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestShiftRight(Natural.Of(2), Natural.Of(2));
      TestSize(5, false);
      TestGetAt(Natural.ZERO, "a", false);
      TestGetAt(Natural.Of(1), "b", false);
      TestGetAt(Natural.Of(4), "c", false);
      TestPrintContent("");
      TestFoldForward((dat, acc) -> { if (dat != null) return acc + dat; else return acc; }, "", "abc");
    }

    @Test
    @DisplayName("ShiftLeft at first position")
    public void ShiftLeftAtFirstPosition() {
      AddTest(11);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestSetAt("e", Natural.Of(4), false);
      TestShiftLeft(Natural.ZERO, Natural.Of(2));
      TestSize(3, false);
      TestGetAt(Natural.ZERO, "c", false);
      TestGetAt(Natural.Of(1), "d", false);
      TestGetAt(Natural.Of(2), "e", false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Multiple operations preserve data integrity")
    public void DataIntegrityTest() {
      AddTest(18);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestSetAt("apple", Natural.ZERO, false);
      TestSetAt("banana", Natural.Of(1), false);
      TestSetAt("cherry", Natural.Of(2), false);
      TestSetAt("date", Natural.Of(3), false);
      TestSetAt("elderberry", Natural.Of(4), false);
      TestInsertAtWithAutoExpansion(Natural.Of(2), "blueberry");
      TestSize(6, false);
      TestGetAt(Natural.Of(2), "blueberry", false);
      TestGetAt(Natural.Of(3), "cherry", false);
      TestAtNRemoveWithAutoReduction(Natural.Of(2), "blueberry");
      TestSize(5, false);
      TestGetAt(Natural.Of(2), "cherry", false);
      TestShiftRight(Natural.Of(1), Natural.Of(2));
      TestSize(7, false);
      TestGetAt(Natural.ZERO, "apple", false);
      TestGetAt(Natural.Of(3), "banana", false);
      TestShiftLeft(Natural.Of(1), Natural.Of(2));
      TestSize(5, false);
    }

    @Test
    @DisplayName("Extreme values in dynamic vector")
    public void ExtremeValues() {
      AddTest(12);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestSetAt("", Natural.ZERO, false);
      TestSetAt("a".repeat(10000), Natural.Of(1), false);
      TestSetAt("hello\nworld", Natural.Of(2), false);
      TestSetAt("tab\there", Natural.Of(3), false);
      TestSetAt("日本語", Natural.Of(4), false);
      TestGetAt(Natural.ZERO, "", false);
      TestGetAt(Natural.Of(1), "a".repeat(10000), false);
      TestExists("", true);
      TestExists("日本語", true);
      TestAtNRemoveWithAutoReduction(Natural.ZERO, "");
      TestSize(4, false);
    }

    @Test
    @DisplayName("Clear and rebuild")
    public void ClearAndRebuild() {
      AddTest(14);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestClear();
      TestIsEmpty(true, false);
      TestSize(0, false);
      TestExpandWithNumber(Natural.Of(3));
      TestSetAt("x", Natural.ZERO, false);
      TestSetAt("y", Natural.Of(1), false);
      TestSetAt("z", Natural.Of(2), false);
      TestGetAt(Natural.ZERO, "x", false);
      TestGetAt(Natural.Of(2), "z", false);
      TestFoldForward((dat, acc) -> acc + dat, "", "xyz");
      TestPrintContent("");
    }

  }

  @Nested
  @DisplayName("DynVector Construction and Equality Tests")
  public class DynVectorConstructionAndEqualityTests {

    @Test
    @DisplayName("Construction from another container")
    public void ConstructionFromAnotherContainer() {
      AddTest(6);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(3));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      NewNonEmptyContainer(ThisContainer());
      TestGetAt(Natural.ZERO, "a", false);
      TestGetAt(Natural.Of(2), "c", false);
      TestSize(3, false);
    }

    @Test
    @DisplayName("Construction from empty container")
    public void ConstructionFromEmptyContainer() {
      AddTest(3);
      NewEmptyContainer();
      NewNonEmptyContainer(ThisContainer());
      TestIsEmpty(true, false);
      TestSize(0, false);
    }

    @Test
    @DisplayName("Construction with initial size")
    public void ConstructionWithInitialSize() {
      AddTest(4);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(10));
      TestSize(10, false);
      TestSetAt("hello", Natural.Of(5), false);
      TestGetAt(Natural.Of(5), "hello", false);
    }

    @Test
    @DisplayName("Equality with same elements same order")
    public void EqualitySameOrder() {
      AddTest(6);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(3));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      apsd.interfaces.containers.sequences.DynVector<String> other = GetNewEmptyContainer();
      other.Expand(Natural.Of(3));
      other.SetAt("a", Natural.ZERO);
      other.SetAt("b", Natural.Of(1));
      other.SetAt("c", Natural.Of(2));
      TestIsEqual(other, true);
    }

    @Test
    @DisplayName("Inequality with same elements different order")
    public void InequalityDifferentOrder() {
      AddTest(6);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(3));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      apsd.interfaces.containers.sequences.DynVector<String> other = GetNewEmptyContainer();
      other.Expand(Natural.Of(3));
      other.SetAt("c", Natural.ZERO);
      other.SetAt("b", Natural.Of(1));
      other.SetAt("a", Natural.Of(2));
      TestIsEqual(other, false);
    }

    @Test
    @DisplayName("Inequality with different sizes")
    public void InequalityDifferentSizes() {
      AddTest(6);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(3));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      apsd.interfaces.containers.sequences.DynVector<String> other = GetNewEmptyContainer();
      other.Expand(Natural.Of(2));
      other.SetAt("a", Natural.ZERO);
      other.SetAt("b", Natural.Of(1));
      TestIsEqual(other, false);
    }

    @Test
    @DisplayName("Equality of two empty dynamic vectors")
    public void EqualityEmptyDynVectors() {
      AddTest(2);
      NewEmptyContainer();
      apsd.interfaces.containers.sequences.DynVector<String> other = GetNewEmptyContainer();
      TestIsEqual(other, true);
    }

    @Test
    @DisplayName("Copy construction preserves elements")
    public void CopyConstructionPreservesElements() {
      AddTest(7);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(4));
      TestSetAt("w", Natural.ZERO, false);
      TestSetAt("x", Natural.Of(1), false);
      TestSetAt("y", Natural.Of(2), false);
      TestSetAt("z", Natural.Of(3), false);
      NewNonEmptyContainer(ThisContainer());
      TestFoldForward((dat, acc) -> acc + dat, "", "wxyz");
      TestSize(4, false);
    }

  }

  @Nested
  @DisplayName("DynVector Null Testing")
  public class DynVectorNullTesting {

    @Test
    @DisplayName("SetAt with null element")
    public void SetAtNullElement() {
      AddTest(5);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(3));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt(null, Natural.Of(1), false);
      TestGetAt(Natural.Of(1), null, false);
      TestExists(null, true);
      TestSize(3, false);
    }

    @Test
    @DisplayName("SetFirst with null")
    public void SetFirstNull() {
      AddTest(4);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(2));
      TestSetFirst("hello", false);
      TestSetFirst(null, false);
      TestGetFirst(null, false);
    }

    @Test
    @DisplayName("SetLast with null")
    public void SetLastNull() {
      AddTest(4);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(2));
      TestSetLast("hello", false);
      TestSetLast(null, false);
      TestGetLast(null, false);
    }

    @Test
    @DisplayName("Exists with null")
    public void ExistsNull() {
      AddTest(3);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(3));
      TestSetAt("a", Natural.ZERO, false);
      TestExists(null, true);
    }

    @Test
    @DisplayName("GetAt with null Natural")
    public void GetAtNullNatural() {
      AddTest(2);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(3));
      TestGetAt(null, "", true);
    }

    @Test
    @DisplayName("SetAt with null Natural")
    public void SetAtNullNatural() {
      AddTest(2);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(3));
      TestSetAt("a", null, true);
    }

    @Test
    @DisplayName("ShiftLeft with null Natural")
    public void ShiftLeftNullNatural() {
      AddTest(2);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(3));
      TestShiftLeft(null, Natural.ONE);
    }

    @Test
    @DisplayName("Realloc with null Natural")
    public void ReallocNullNatural() {
      AddTest(3);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(3));
      TestRealloc(null);
      TestSize(3, false);
    }

    @Test
    @DisplayName("InsertAt with null Natural")
    public void InsertAtNullNatural() {
      AddTest(2);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(3));
      TestInsertAtWithAutoExpansion(null, "a");
    }

    @Test
    @DisplayName("AtNRemove with null Natural")
    public void AtNRemoveNullNatural() {
      AddTest(2);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(3));
      TestAtNRemoveWithAutoReduction(null, "");
    }

    @Test
    @DisplayName("IsEqual with null container")
    public void IsEqualNullContainer() {
      AddTest(2);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(3));
      TestIsEqual(null, false);
    }

    @Test
    @DisplayName("FoldForward with null accumulator")
    public void FoldForwardNullAccumulator() {
      AddTest(2);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(1));
      TestFoldForward((dat, acc) -> acc, null, null);
    }

    @Test
    @DisplayName("FoldBackward with null accumulator")
    public void FoldBackwardNullAccumulator() {
      AddTest(2);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(1));
      TestFoldBackward((dat, acc) -> acc, null, null);
    }

    @Test
    @DisplayName("Operations with null elements maintain consistency")
    public void NullOperationsConsistency() {
      AddTest(8);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(3));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt(null, Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestFoldForward((dat, acc) -> { if (dat != null) return acc + dat; else return acc; }, "", "ac");
      TestSwap(Natural.ZERO, Natural.Of(1), false);
      TestGetAt(Natural.ZERO, null, false);
      TestGetAt(Natural.Of(1), "a", false);
      TestSize(3, false);
    }

    @Test
    @DisplayName("Empty dynamic vector null operations")
    public void EmptyDynVectorNullOperations() {
      AddTest(4);
      NewEmptyContainer();
      TestExists(null, false);
      TestGetAt(null, "", true);
      TestSetAt(null, null, true);
    }

  }

  @Nested
  @DisplayName("DynVector Comprehensive Method Combinations")
  public class DynVectorComprehensiveMethodCombinations {

    @Test
    @DisplayName("Expand, SetAt, GetAt, GetFirst, GetLast comprehensive")
    public void ExpandSetAtGetAtComprehensive() {
      AddTest(14);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestSetAt("alpha", Natural.ZERO, false);
      TestSetAt("beta", Natural.Of(1), false);
      TestSetAt("gamma", Natural.Of(2), false);
      TestSetAt("delta", Natural.Of(3), false);
      TestSetAt("epsilon", Natural.Of(4), false);
      TestGetAt(Natural.ZERO, "alpha", false);
      TestGetAt(Natural.Of(2), "gamma", false);
      TestGetAt(Natural.Of(4), "epsilon", false);
      TestGetFirst("alpha", false);
      TestGetLast("epsilon", false);
      TestSize(5, false);
      TestExists("gamma", true);
      TestExists("zeta", false);
    }

    @Test
    @DisplayName("SetFirst, SetLast with GetFirst, GetLast verification")
    public void SetFirstLastVerification() {
      AddTest(10);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(4));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestSetFirst("X", false);
      TestSetLast("Z", false);
      TestGetFirst("X", false);
      TestGetLast("Z", false);
      TestFoldForward((dat, acc) -> acc + "," + dat, "", ",X,b,c,Z");
      TestSize(4, false);
    }

    @Test
    @DisplayName("GetNSetFirst, GetNSetLast, GetNSetAt chain")
    public void GetNSetChain() {
      AddTest(12);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(4));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestGetNSetFirst("W", "a", false);
      TestGetNSetLast("Z", "d", false);
      TestGetNSetAt("X", "b", Natural.Of(1), false);
      TestGetNSetAt("Y", "c", Natural.Of(2), false);
      TestGetFirst("W", false);
      TestGetLast("Z", false);
      TestFoldForward((dat, acc) -> acc + dat, "", "WXYZ");
      TestFoldBackward((dat, acc) -> acc + dat, "", "ZYXW");
    }

    @Test
    @DisplayName("Grow and Reduce cycles with verification")
    public void GrowReduceCycles() {
      AddTest(12);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(3));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestExpandWithNumber(Natural.Of(2));
      TestSize(5, false);
      TestSetAt("d", Natural.Of(3), false);
      TestSetAt("e", Natural.Of(4), false);
      TestReduceWithNumber(Natural.Of(2));
      TestSize(3, false);
      TestGetAt(Natural.ZERO, "a", false);
      TestGetAt(Natural.Of(2), "c", false);
      TestFoldForward((dat, acc) -> acc + dat, "", "abc");
    }

    @Test
    @DisplayName("Expand and Shrink with data verification")
    public void ExpandShrinkWithDataVerification() {
      AddTest(10);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(6));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestSetAt("e", Natural.Of(4), false);
      TestSetAt("f", Natural.Of(5), false);
      TestRealloc(Natural.Of(3));
      TestSize(3, false);
      TestFoldForward((dat, acc) -> acc + dat, "", "abc");
      TestGetLast("c", false);
    }

    @Test
    @DisplayName("Realloc preserving front elements")
    public void ReallocPreservingFrontElements() {
      AddTest(8);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestSetAt("e", Natural.Of(4), false);
      TestRealloc(Natural.Of(3));
      TestFoldForward((dat, acc) -> acc + dat, "", "abc");
      TestSize(3, false);
    }

    @Test
    @DisplayName("ShiftLeft and ShiftRight comprehensive")
    public void ShiftLeftRightComprehensive() {
      AddTest(14);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestSetAt("e", Natural.Of(4), false);
      TestShiftRight(Natural.Of(2), Natural.Of(2));
      TestSize(7, false);
      TestGetAt(Natural.ZERO, "a", false);
      TestGetAt(Natural.Of(1), "b", false);
      TestGetAt(Natural.Of(4), "c", false);
      TestShiftLeft(Natural.Of(2), Natural.Of(2));
      TestSize(5, false);
      TestGetAt(Natural.Of(2), "c", false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("InsertAt with GetAt verification")
    public void InsertAtWithGetAtVerification() {
      AddTest(10);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(3));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("c", Natural.Of(1), false);
      TestSetAt("e", Natural.Of(2), false);
      TestInsertAtWithAutoExpansion(Natural.Of(1), "b");
      TestInsertAtWithAutoExpansion(Natural.Of(3), "d");
      TestSize(5, false);
      TestGetAt(Natural.ZERO, "a", false);
      TestGetAt(Natural.Of(1), "b", false);
      TestGetAt(Natural.Of(2), "c", false);
      TestGetAt(Natural.Of(3), "d", false);
      TestGetAt(Natural.Of(4), "e", false);
    }

    @Test
    @DisplayName("RemoveAt, RemoveFirst, RemoveLast chain")
    public void RemoveAtFirstLastChain() {
      AddTest(12);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestSetAt("e", Natural.Of(4), false);
      TestRemoveFirstWithAutoReduction();
      TestSize(4, false);
      TestGetFirst("b", false);
      TestRemoveLastWithAutoReduction();
      TestSize(3, false);
      TestGetLast("d", false);
      TestAtNRemoveWithAutoReduction(Natural.Of(1), "c");
      TestSize(2, false);
      TestFoldForward((dat, acc) -> acc + dat, "", "bd");
    }

    @Test
    @DisplayName("FirstNRemove, LastNRemove, AtNRemove chain")
    public void FirstLastAtNRemoveChain() {
      AddTest(12);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestSetAt("e", Natural.Of(4), false);
      TestFirstNRemoveWithAutoReduction("a");
      TestSize(4, false);
      TestLastNRemoveWithAutoReduction("e");
      TestSize(3, false);
      TestAtNRemoveWithAutoReduction(Natural.Of(1), "c");
      TestSize(2, false);
      TestFoldForward((dat, acc) -> acc + dat, "", "bd");
    }

    @Test
    @DisplayName("InsertFirst, InsertLast with verification")
    public void InsertFirstLastVerification() {
      AddTest(10);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(1));
      TestSetAt("c", Natural.ZERO, false);
      TestInsertFirstWithAutoExpansion("b");
      TestInsertFirstWithAutoExpansion("a");
      TestInsertLastWithAutoExpansion("d");
      TestInsertLastWithAutoExpansion("e");
      TestSize(5, false);
      TestGetFirst("a", false);
      TestGetLast("e", false);
      TestFoldForward((dat, acc) -> acc + dat, "", "abcde");
      TestFoldBackward((dat, acc) -> acc + dat, "", "edcba");
    }

    @Test
    @DisplayName("Search with various positions")
    public void SearchWithVariousPositions() {
      AddTest(9);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestSetAt("e", Natural.Of(4), false);
      TestExists("a", true);
      TestExists("c", true);
      TestExists("e", true);
      TestExists("z", false);
    }

    @Test
    @DisplayName("Swap operations comprehensive")
    public void SwapOperationsComprehensive() {
      AddTest(10);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(4));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestSwap(Natural.ZERO, Natural.Of(3), false);
      TestGetFirst("d", false);
      TestGetLast("a", false);
      TestSwap(Natural.Of(1), Natural.Of(2), false);
      TestFoldForward((dat, acc) -> acc + dat, "", "dcba");
      TestSize(4, false);
    }

    @Test
    @DisplayName("Shift then SubVector")
    public void ShiftThenSubVector() {
      AddTest(4);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(6));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestSetAt("e", Natural.Of(4), false);
      TestSetAt("f", Natural.Of(5), false);
      TestShiftRight(Natural.ZERO, Natural.Of(3));
      TestPrintContent("");
      TestSize(9L, false);
      TestSubVector(Natural.ZERO, Natural.Of(2), false);
    }

    @Test
    @DisplayName("Shift Branches Match LinearVector")
    public void ShiftBranchesMatchLinearVector() {
      var circ = new DynCircularVector<String>(Natural.Of(8));
      var lin = new DynVector<String>(Natural.Of(8));
      circ.Expand(Natural.Of(8));
      lin.Expand(Natural.Of(8));
      String[] vals = {"alpha", "beta", "gamma", "delta", "epsilon", "zeta", "eta", "theta"};
      for (int i = 0; i < 8; i++) {
        circ.SetAt(vals[i], Natural.Of(i));
        lin.SetAt(vals[i], Natural.Of(i));
      }

      // make circular start non-zero path happen
      circ.ShiftLeft(Natural.ZERO, Natural.Of(2));
      lin.ShiftLeft(Natural.ZERO, Natural.Of(2));

      // force each branch type
      circ.ShiftLeft(Natural.Of(1), Natural.ONE);  lin.ShiftLeft(Natural.Of(1), Natural.ONE);  // left-side
      circ.ShiftLeft(Natural.Of(4), Natural.ONE);  lin.ShiftLeft(Natural.Of(4), Natural.ONE);  // right-side
      circ.ShiftRight(Natural.Of(2), Natural.ONE); lin.ShiftRight(Natural.Of(2), Natural.ONE); // left-side
      circ.ShiftRight(Natural.Of(4), Natural.ONE); lin.ShiftRight(Natural.Of(4), Natural.ONE); // right-side

      for (long i = 0; i < 6; i++) {
        assertEquals(lin.GetAt(Natural.Of(i)), circ.GetAt(Natural.Of(i)), "Mismatch at " + i);
      }
    }

    //@Test
    //@DisplayName("Randomized differential: CircularVector matches Vector after each shift")
    public void RandomizedShiftDifferential() {
      var circ = new DynCircularVector<String>(Natural.Of(800));
      var lin = new DynVector<String>(Natural.Of(800));

      circ.Expand(Natural.Of(800));
      lin.Expand(Natural.Of(800));
      for (long i = 0; i < 800; i++) {
        circ.SetAt("v" + i, Natural.Of(i));
        lin.SetAt("v" + i, Natural.Of(i));
      }

      // force non-zero start early
      circ.ShiftLeft(Natural.ZERO, Natural.Of(37));
      lin.ShiftLeft(Natural.ZERO, Natural.Of(37));

      Random rnd = new Random(12388789L);
      for (int seedn = 0; seedn < 5; ++seedn) {
        for (int q = 0; q < 10; q++) {
          for (long i = 0; i < lin.Size().ToLong(); i++) {
            circ.SetAt("v" + i, Natural.Of(i));
            lin.SetAt("v" + i, Natural.Of(i));
          }
          for (int t = 0; t < 500; t++) {
            long pos = rnd.nextInt((int) lin.Size().ToLong());
            long num = rnd.nextInt(3);
            boolean left = false;
            if (rnd.nextBoolean()) {
              left = true;
              circ.ShiftLeft(Natural.Of(pos), Natural.Of(num));
              lin.ShiftLeft(Natural.Of(pos), Natural.Of(num));
            } else {
              circ.ShiftRight(Natural.Of(pos), Natural.Of(num));
              lin.ShiftRight(Natural.Of(pos), Natural.Of(num));
            }
            for (long i = 0; i < lin.Size().ToLong(); i++) {
              assertEquals(lin.GetAt(Natural.Of(i)), circ.GetAt(Natural.Of(i)),
                  "Mismatch at step " + t + ", index " + i + ", pos=" + pos + ", num=" + num + ", left=" + left);
            }
          }
          for (int t = 0; t < 500; t++) {
            long pos = rnd.nextInt(Math.max((int) lin.Size().ToLong(), 1));
            long num = rnd.nextInt(1200);
            boolean left = false;
            if (!rnd.nextBoolean() || lin.Size().ToLong() == 0) {
              circ.ShiftRight(Natural.Of(pos), Natural.Of(num));
              lin.ShiftRight(Natural.Of(pos), Natural.Of(num));
            } else {
              left = true;
              circ.ShiftLeft(Natural.Of(pos), Natural.Of(num));
              lin.ShiftLeft(Natural.Of(pos), Natural.Of(num));
            }
            for (long i = 0; i < lin.Size().ToLong(); i++) {
              assertEquals(lin.GetAt(Natural.Of(i)), circ.GetAt(Natural.Of(i)),
                  "Mismatch at step " + t + ", index " + i + ", pos=" + pos + ", num=" + num + ", left=" + left);
            }
          }
        }
        rnd = new Random(rnd.nextLong());
      }
      
    }

    @Test
    @DisplayName("SubSequence and SubVector verification")
    public void SubSequenceSubVectorVerification() {
      AddTest(14);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestSetAt("e", Natural.Of(4), false);
      TestSize(5, false);
      TestSubSequence(Natural.Of(1), Natural.Of(3), false);
      TestSubVector(Natural.Of(0), Natural.Of(4), false);
      TestSubVector(Natural.Of(2), Natural.Of(2), false);
      TestSubVector(Natural.Of(0), Natural.Of(0), false);
      TestSubVector(Natural.Of(4), Natural.Of(4), false);
      TestSubVector(Natural.Of(0), Natural.Of(10), true);
      TestSubVector(Natural.Of(3), Natural.Of(1), true);
      TestPrintContent("");
    }

    @Test
    @DisplayName("TraverseForward and TraverseBackward verification")
    public void TraverseForwardBackwardVerification() {
      AddTest(7);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestSetAt("e", Natural.Of(4), false);
      TestTraverseForward(dat -> dat.equals("c"), true);
      TestTraverseBackward(dat -> dat.equals("c"), true);
    }

    @Test
    @DisplayName("FoldForward and FoldBackward comprehensive")
    public void FoldForwardBackwardComprehensive() {
      AddTest(8);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(4));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestFoldForward((dat, acc) -> acc + dat, "", "abcd");
      TestFoldBackward((dat, acc) -> acc + dat, "", "dcba");
      TestFoldForward((dat, acc) -> acc + 1, 0, 4);
      TestFoldBackward((dat, acc) -> acc + 1, 0, 4);
    }

    @Test
    @DisplayName("FIterator and BIterator usage")
    public void FIteratorBIteratorUsage() {
      AddTest(6);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(4));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestFIterator();
      TestForwardIteration(false, false);
    }

    @Test
    @DisplayName("Clear then rebuild with verification")
    public void ClearThenRebuildWithVerification() {
      AddTest(10);
      NewNonEmptyContainer();
      TestClear();
      TestIsEmpty(true, false);
      TestExpandWithNumber(Natural.Of(3));
      TestSetAt("X", Natural.ZERO, false);
      TestSetAt("Y", Natural.Of(1), false);
      TestSetAt("Z", Natural.Of(2), false);
      TestFoldForward((dat, acc) -> acc + dat, "", "XYZ");
      TestGetFirst("X", false);
      TestGetLast("Z", false);
      TestSize(3, false);
    }

    @Test
    @DisplayName("IsEqual verification after modifications")
    public void IsEqualAfterModifications() {
      AddTest(8);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(3));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      apsd.interfaces.containers.sequences.DynVector<String> other = GetNewEmptyContainer();
      other.Expand(Natural.Of(3));
      other.SetAt("a", Natural.ZERO);
      other.SetAt("b", Natural.Of(1));
      other.SetAt("c", Natural.Of(2));
      TestIsEqual(other, true);
      TestSetAt("X", Natural.Of(1), false);
      TestIsEqual(other, false);
      other.SetAt("X", Natural.Of(1));
      TestIsEqual(other, true);
    }

    @Test
    @DisplayName("Empty vector all operations")
    public void EmptyVectorAllOperations() {
      AddTest(8);
      NewEmptyContainer();
      TestIsEmpty(true, false);
      TestSize(0, false);
      TestGetFirst("", true);
      TestGetLast("", true);
      TestSetFirst("", true);
      TestSetLast("", true);
      TestExists("anything", false);
      TestFoldForward((dat, acc) -> acc + dat, "init", "init");
    }

    @Test
    @DisplayName("Single element all operations")
    public void SingleElementAllOperations() {
      AddTest(12);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(1));
      TestSetFirst("only", false);
      TestGetFirst("only", false);
      TestGetLast("only", false);
      TestExists("only", true);
      TestExists("other", false);
      TestFoldForward((dat, acc) -> acc + dat, "", "only");
      TestFoldBackward((dat, acc) -> acc + dat, "", "only");
      TestSwap(Natural.ZERO, Natural.ZERO, false);
      TestGetFirst("only", false);
      TestAtNRemoveWithAutoReduction(Natural.ZERO, "only");
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Complex interleaved operations")
    public void ComplexInterleavedOperations() {
      AddTest(16);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestSetAt("e", Natural.Of(4), false);
      TestSwap(Natural.ZERO, Natural.Of(4), false);
      // [e, b, c, d, a]
      TestShiftLeft(Natural.Of(1), Natural.Of(2));
      // [e, d, a, ...reduced]
      TestSize(3, false);
      TestGetFirst("e", false);
      TestInsertAtWithAutoExpansion(Natural.Of(1), "X");
      TestSize(4, false);
      TestGetAt(Natural.Of(1), "X", false);
      TestLastNRemoveWithAutoReduction("a");
      TestSize(3, false);
      TestFoldForward((dat, acc) -> acc + "," + dat, "", ",e,X,d");
      TestFirstNRemoveWithAutoReduction("e");
      TestSize(2, false);
    }

    @Test
    @DisplayName("Build, verify, clear, rebuild multiple cycles")
    public void BuildVerifyClearRebuildCycles() {
      AddTest(15);
      NewEmptyContainer();
      // Cycle 1
      TestExpandWithNumber(Natural.Of(3));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestFoldForward((dat, acc) -> acc + dat, "", "abc");
      TestClear();
      TestIsEmpty(true, false);
      // Cycle 2
      TestExpandWithNumber(Natural.Of(2));
      TestSetAt("x", Natural.ZERO, false);
      TestSetAt("y", Natural.Of(1), false);
      TestFoldForward((dat, acc) -> acc + dat, "", "xy");
      TestClear();
      TestIsEmpty(true, false);
      // Cycle 3
      TestExpandWithNumber(Natural.Of(4));
      TestSetAt("W", Natural.ZERO, false);
      TestSetAt("X", Natural.Of(1), false);
      TestSetAt("Y", Natural.Of(2), false);
      TestSetAt("Z", Natural.Of(3), false);
      TestFoldBackward((dat, acc) -> acc + dat, "", "ZYXW");
    }

    @Test
    @DisplayName("Large vector mixed operations")
    public void LargeVectorMixedOperations() {
      AddTest(8);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(100));
      for (int i = 0; i < 100; i++) {
        ThisContainer().SetAt("v" + i, Natural.Of(i));
      }
      TestSize(100, false);
      TestGetFirst("v0", false);
      TestGetLast("v99", false);
      TestExists("v50", true);
      TestExists("v100", false);
      TestRealloc(Natural.Of(10));
      TestSize(10, false);
      TestGetLast("v9", false);
      TestFoldForward((dat, acc) -> acc + 1, 0, 10);
    }

    @Test
    @DisplayName("Shift operations comprehensive")
    public void ShiftOperationsComprehensive() {
      AddTest(16);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestSetAt("e", Natural.Of(4), false);
      TestShiftFirstLeft();
      // [b, c, d, e] size 4
      TestSize(4, false);
      TestGetFirst("b", false);
      TestShiftLastLeft();
      // [b, c, d] size 3
      TestSize(3, false);
      TestShiftFirstRight();
      // [null, b, c, d] size 4
      TestSize(4, false);
      TestShiftLastRight();
      // [null, b, c, null, d] size 5
      TestSize(5, false);
      TestShiftLeft(Natural.ZERO, Natural.ONE);
      // [b, c, null, d] size 4
      TestSize(4, false);
      TestGetNSetAt("X", "b", Natural.ZERO, false);
      TestGetNSetAt("Y", "c", Natural.Of(1), false);
      TestGetNSetAt("Z", null, Natural.Of(2), false);
      TestGetNSetAt("Q", "d", Natural.Of(3), false);
      TestFIterator();
      TestBIterator();
      TestForwardIteration(false, false);
      TestBackwardIteration(false, false);
    }

    @Test
    @DisplayName("Capacity and Size relationship")
    public void CapacitySizeRelationship() {
      AddTest(8);
      NewEmptyContainer();
      TestSize(0, false);
      TestExpandWithNumber(Natural.Of(5));
      TestSize(5, false);
      TestReduceWithNumber(Natural.Of(2));
      TestSize(3, false);
      TestExpandWithNumber(Natural.Of(7));
      TestSize(10, false);
      TestRealloc(Natural.Of(4));
      TestSize(4, false);
      TestRealloc(Natural.ZERO);
      TestSize(0, false);
    }

    @Test
    @DisplayName("Duplicate values handling")
    public void DuplicateValuesHandling() {
      AddTest(10);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestSetAt("same", Natural.ZERO, false);
      TestSetAt("same", Natural.Of(1), false);
      TestSetAt("same", Natural.Of(2), false);
      TestSetAt("same", Natural.Of(3), false);
      TestSetAt("same", Natural.Of(4), false);
      TestExists("same", true);
      TestFoldForward((dat, acc) -> acc + 1, 0, 5);
      TestAtNRemoveWithAutoReduction(Natural.Of(2), "same");
      TestSize(4, false);
      TestExists("same", true);
    }

    @Test
    @DisplayName("Mixed null and non-null elements")
    public void MixedNullNonNullElements() {
      AddTest(10);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt(null, Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt(null, Natural.Of(3), false);
      TestSetAt("e", Natural.Of(4), false);
      TestFoldForward((dat, acc) -> { if (dat != null) return acc + dat; else return acc; }, "", "ace");
      TestFoldForward((dat, acc) -> { if (dat != null) return acc + 1; else return acc; }, 0, 3);
      TestSwap(Natural.ZERO, Natural.Of(1), false);
      TestGetAt(Natural.ZERO, null, false);
      TestGetAt(Natural.Of(1), "a", false);
    }

    @Test
    @DisplayName("Sequential access verification")
    public void SequentialAccessVerification() {
      AddTest(12);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestSetAt("alpha", Natural.ZERO, false);
      TestSetAt("beta", Natural.Of(1), false);
      TestSetAt("gamma", Natural.Of(2), false);
      TestSetAt("delta", Natural.Of(3), false);
      TestSetAt("epsilon", Natural.Of(4), false);
      TestGetAt(Natural.ZERO, "alpha", false);
      TestGetAt(Natural.Of(1), "beta", false);
      TestGetAt(Natural.Of(2), "gamma", false);
      TestGetAt(Natural.Of(3), "delta", false);
      TestGetAt(Natural.Of(4), "epsilon", false);
      TestFoldForward((dat, acc) -> acc + "," + dat, "", ",alpha,beta,gamma,delta,epsilon");
      TestFoldBackward((dat, acc) -> acc + "," + dat, "", ",epsilon,delta,gamma,beta,alpha");
    }

  }

}
