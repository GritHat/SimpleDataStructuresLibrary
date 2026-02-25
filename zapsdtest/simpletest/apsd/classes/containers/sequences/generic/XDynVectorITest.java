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


abstract public class XDynVectorITest extends XDynVectorTest<Long> {

  @Override
  public void NewNonEmptyContainer() {
    AddTest(13);
    NewEmptyContainer();
    TestSetFirst(0L, true);
    TestExpandWithNumber(Natural.Of(1));
    TestSetFirst(0L, false);
    TestExpandWithNumber(Natural.Of(4));
    TestSize(5, false);
    TestSwap(Natural.ZERO, Natural.Of(3), false);
    TestSetFirst(4L, false);
    TestSetAt(3L, Natural.Of(1), false);
    TestSetAt(2L, Natural.Of(2), false);
    TestSetAt(1L, Natural.Of(5), true);
    TestSetLast(1L, false);
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
      TestGetFirst(0L, true);
      TestGetLast(0L, true);
      TestSetFirst(0L, true);
      TestSetLast(0L, true);
      TestGetAt(Natural.Of(1), 0L, true);
      TestSetAt(0L, Natural.Of(2), true);
      TestExists(4L, false);
      TestPrintContent("");
      TestFoldBackward((dat, acc) -> acc + dat, 0L, 0L);
    }

    @Test
    @DisplayName("Check starting from a NonEmpty Vector")
    public void NonEmpty() {
      AddTest(28);
      NewNonEmptyContainer();
      TestGetFirst(4L, false);
      TestGetLast(1L, false);
      TestSetFirst(5L, false);
      TestSetLast(4L, false);
      TestExists(4L, true);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 14L);
      TestFoldForward((dat, acc) -> acc * dat, 1L, 0L);
      TestGetAt(Natural.Of(3), 0L, false);
      TestSetAt(1L, Natural.Of(3), false);
      TestFoldForward((dat, acc) -> acc * dat, 1L, 120L);
      TestPrintContent("");
      TestRealloc(Natural.Of(2));
      TestPrintContent("");
      TestFoldForward((dat, acc) -> acc * dat, 1L, 15L);
      TestRealloc(Natural.Of(4));
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
      TestSetAt(10L, Natural.ZERO, false);
      TestSetAt(20L, Natural.Of(4), false);
      TestGetAt(Natural.ZERO, 10L, false);
      TestGetAt(Natural.Of(4), 20L, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Multiple expansions")
    public void MultipleExpansions() {
      AddTest(10);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(2));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestExpandWithNumber(Natural.Of(3));
      TestSize(5, false);
      TestGetAt(Natural.ZERO, 1L, false);
      TestGetAt(Natural.Of(1), 2L, false);
      TestSetAt(5L, Natural.Of(4), false);
      TestGetAt(Natural.Of(4), 5L, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Shrink preserves front elements")
    public void ShrinkPreservesFront() {
      AddTest(8);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestRealloc(Natural.Of(2));
      TestSize(2, false);
      TestGetAt(Natural.ZERO, 1L, false);
      TestGetAt(Natural.Of(1), 2L, false);
    }

    @Test
    @DisplayName("ShiftLeft reduces size")
    public void ShiftLeftReducesSize() {
      AddTest(11);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestSetAt(4L, Natural.Of(3), false);
      TestSetAt(5L, Natural.Of(4), false);
      TestSize(5, false);
      TestShiftLeft(Natural.Of(2), Natural.Of(2));
      TestSize(3, false);
      TestPrintContent("");
      TestFoldForward((dat, acc) -> acc + dat, 0L, 8L);
    }

    @Test
    @DisplayName("ShiftRight increases size")
    public void ShiftRightIncreasesSize() {
      AddTest(11);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(3));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestSize(3, false);
      TestShiftRight(Natural.ONE, Natural.Of(2));
      TestSize(5, false);
      TestGetAt(Natural.ZERO, 1L, false);
      TestGetAt(Natural.Of(3), 2L, false);
      TestGetAt(Natural.Of(4), 3L, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Insert at with expansion")
    public void InsertAtWithExpansion() {
      AddTest(11);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(3));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(3L, Natural.Of(1), false);
      TestSetAt(5L, Natural.Of(2), false);
      TestInsertAtWithAutoExpansion(Natural.ONE, 2L);
      TestSize(4, false);
      TestGetAt(Natural.ZERO, 1L, false);
      TestGetAt(Natural.Of(1), 2L, false);
      TestGetAt(Natural.Of(2), 3L, false);
      TestGetAt(Natural.Of(3), 5L, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Remove at with reduction")
    public void RemoveAtWithReduction() {
      AddTest(12);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestSetAt(4L, Natural.Of(3), false);
      TestSetAt(5L, Natural.Of(4), false);
      TestAtNRemoveWithAutoReduction(Natural.Of(2), 3L);
      TestSize(4, false);
      TestGetAt(Natural.ZERO, 1L, false);
      TestGetAt(Natural.Of(1), 2L, false);
      TestGetAt(Natural.Of(2), 4L, false);
      TestGetAt(Natural.Of(3), 5L, false);
    }

    @Test
    @DisplayName("Reduce by number")
    public void ReduceByNumber() {
      AddTest(8);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(10));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestSize(10, false);
      TestReduceWithNumber(Natural.Of(3));
      TestSize(7, false);
      TestGetAt(Natural.ZERO, 1L, false);
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
      TestSetAt(42L, Natural.Of(2), false);
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
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
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
      TestSetAt(10L, Natural.ZERO, false);
      TestSetAt(20L, Natural.Of(1), false);
      TestSetAt(30L, Natural.Of(2), false);
      TestSetAt(40L, Natural.Of(3), false);
      TestSetAt(50L, Natural.Of(4), false);
      TestReduceWithNumber(Natural.Of(2));
      TestSize(3, false);
      TestGetAt(Natural.ZERO, 10L, false);
      TestGetAt(Natural.Of(2), 30L, false);
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
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(9), false);
      TestSize(10, false);
      TestRealloc(Natural.Of(5));
      TestSize(5, false);
      TestGetAt(Natural.ZERO, 1L, false);
      TestExpandWithNumber(Natural.Of(15));
      TestSize(20, false);
      TestGetAt(Natural.ZERO, 1L, false);
      TestRealloc(Natural.Of(1));
      TestSize(1, false);
      TestGetAt(Natural.ZERO, 1L, false);
      TestExpandWithNumber(Natural.Of(99));
      TestSetAt(100L, Natural.Of(99), false);
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
      TestSetAt(5L, Natural.ZERO, false);
      TestInsertAtWithAutoExpansion(Natural.ZERO, 4L);
      TestInsertAtWithAutoExpansion(Natural.ZERO, 3L);
      TestInsertAtWithAutoExpansion(Natural.ZERO, 2L);
      TestInsertAtWithAutoExpansion(Natural.ZERO, 1L);
      TestSize(5, false);
      TestGetAt(Natural.ZERO, 1L, false);
      TestGetAt(Natural.Of(1), 2L, false);
      TestGetAt(Natural.Of(2), 3L, false);
      TestGetAt(Natural.Of(3), 4L, false);
      TestGetAt(Natural.Of(4), 5L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 15L);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Insert at end repeatedly")
    public void InsertAtEndRepeatedly() {
      AddTest(14);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(1));
      TestSetAt(1L, Natural.ZERO, false);
      TestInsertAtWithAutoExpansion(Natural.ONE, 2L);
      TestInsertAtWithAutoExpansion(Natural.Of(2), 3L);
      TestInsertAtWithAutoExpansion(Natural.Of(3), 4L);
      TestInsertAtWithAutoExpansion(Natural.Of(4), 5L);
      TestSize(5, false);
      TestGetAt(Natural.ZERO, 1L, false);
      TestGetAt(Natural.Of(1), 2L, false);
      TestGetAt(Natural.Of(2), 3L, false);
      TestGetAt(Natural.Of(3), 4L, false);
      TestGetAt(Natural.Of(4), 5L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 15L);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Remove from beginning repeatedly")
    public void RemoveFromBeginningRepeatedly() {
      AddTest(14);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestSetAt(4L, Natural.Of(3), false);
      TestSetAt(5L, Natural.Of(4), false);
      TestAtNRemoveWithAutoReduction(Natural.ZERO, 1L);
      TestAtNRemoveWithAutoReduction(Natural.ZERO, 2L);
      TestAtNRemoveWithAutoReduction(Natural.ZERO, 3L);
      TestSize(2, false);
      TestGetAt(Natural.ZERO, 4L, false);
      TestGetAt(Natural.Of(1), 5L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 9L);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Remove from end repeatedly")
    public void RemoveFromEndRepeatedly() {
      AddTest(14);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestSetAt(4L, Natural.Of(3), false);
      TestSetAt(5L, Natural.Of(4), false);
      TestAtNRemoveWithAutoReduction(Natural.Of(4), 5L);
      TestAtNRemoveWithAutoReduction(Natural.Of(3), 4L);
      TestAtNRemoveWithAutoReduction(Natural.Of(2), 3L);
      TestSize(2, false);
      TestGetAt(Natural.ZERO, 1L, false);
      TestGetAt(Natural.Of(1), 2L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 3L);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Alternating insert and remove")
    public void AlternatingInsertRemove() {
      AddTest(17);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(3));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestInsertAtWithAutoExpansion(Natural.ONE, 100L);
      TestSize(4, false);
      TestAtNRemoveWithAutoReduction(Natural.ONE, 100L);
      TestSize(3, false);
      TestInsertAtWithAutoExpansion(Natural.Of(2), 200L);
      TestSize(4, false);
      TestAtNRemoveWithAutoReduction(Natural.Of(2), 200L);
      TestSize(3, false);
      TestGetAt(Natural.ZERO, 1L, false);
      TestGetAt(Natural.Of(1), 2L, false);
      TestGetAt(Natural.Of(2), 3L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 6L);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Expand with zero")
    public void ExpandWithZero() {
      AddTest(8);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(3));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestExpandWithNumber(Natural.ZERO);
      TestSize(3, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 6L);
      TestPrintContent("");
    }

    @Test
    @DisplayName("ShiftRight at last position")
    public void ShiftRightAtLastPosition() {
      AddTest(11);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(3));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestShiftRight(Natural.Of(2), Natural.Of(2));
      TestSize(5, false);
      TestGetAt(Natural.ZERO, 1L, false);
      TestGetAt(Natural.Of(1), 2L, false);
      TestGetAt(Natural.Of(4), 3L, false);
      TestPrintContent("");
      TestFoldForward((dat, acc) -> { if (dat != null) return acc + dat; else return acc; }, 0L, 6L);
    }

    @Test
    @DisplayName("ShiftLeft at first position")
    public void ShiftLeftAtFirstPosition() {
      AddTest(11);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestSetAt(4L, Natural.Of(3), false);
      TestSetAt(5L, Natural.Of(4), false);
      TestShiftLeft(Natural.ZERO, Natural.Of(2));
      TestSize(3, false);
      TestGetAt(Natural.ZERO, 3L, false);
      TestGetAt(Natural.Of(1), 4L, false);
      TestGetAt(Natural.Of(2), 5L, false);
    }

    @Test
    @DisplayName("Multiple operations preserve data integrity")
    public void DataIntegrityTest() {
      AddTest(22);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestSetAt(10L, Natural.ZERO, false);
      TestSetAt(20L, Natural.Of(1), false);
      TestSetAt(30L, Natural.Of(2), false);
      TestSetAt(40L, Natural.Of(3), false);
      TestSetAt(50L, Natural.Of(4), false);
      TestInsertAtWithAutoExpansion(Natural.Of(2), 25L);
      TestSize(6, false);
      TestGetAt(Natural.Of(2), 25L, false);
      TestGetAt(Natural.Of(3), 30L, false);
      TestAtNRemoveWithAutoReduction(Natural.Of(2), 25L);
      TestSize(5, false);
      TestGetAt(Natural.Of(2), 30L, false);
      TestShiftRight(Natural.Of(1), Natural.Of(2));
      TestSize(7, false);
      TestGetAt(Natural.ZERO, 10L, false);
      TestGetAt(Natural.Of(3), 20L, false);
      TestShiftLeft(Natural.Of(1), Natural.Of(2));
      TestSize(5, false);
      TestGetAt(Natural.Of(1), 20L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 150L);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Extreme values in dynamic vector")
    public void ExtremeValues() {
      AddTest(12);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestSetAt(Long.MAX_VALUE, Natural.ZERO, false);
      TestSetAt(Long.MIN_VALUE, Natural.Of(1), false);
      TestSetAt(0L, Natural.Of(2), false);
      TestSetAt(-1L, Natural.Of(3), false);
      TestSetAt(1L, Natural.Of(4), false);
      TestGetAt(Natural.ZERO, Long.MAX_VALUE, false);
      TestGetAt(Natural.Of(1), Long.MIN_VALUE, false);
      TestExists(Long.MAX_VALUE, true);
      TestExists(Long.MIN_VALUE, true);
      TestAtNRemoveWithAutoReduction(Natural.ZERO, Long.MAX_VALUE);
      TestSize(4, false);
    }

    @Test
    @DisplayName("Clear and rebuild")
    public void ClearAndRebuild() {
      AddTest(14);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestClear();
      TestIsEmpty(true, false);
      TestSize(0, false);
      TestExpandWithNumber(Natural.Of(3));
      TestSetAt(10L, Natural.ZERO, false);
      TestSetAt(20L, Natural.Of(1), false);
      TestSetAt(30L, Natural.Of(2), false);
      TestSize(3, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 60L);
      TestPrintContent("");
    }

  }

  @Nested
  @DisplayName("DynVector Construction and Equality Tests")
  public class DynVectorConstructionAndEqualityTests {

    @Test
    @DisplayName("Construction from another container")
    public void ConstructionFromAnotherContainer() {
      AddTest(9);
      LLList<Long> source = new LLList<>();
      source.InsertLast(1L);
      source.InsertLast(2L);
      source.InsertLast(3L);
      source.InsertLast(4L);
      source.InsertLast(5L);
      NewNonEmptyContainer((TraversableContainer<Long>) source);
      TestSize(5, false);
      TestGetAt(Natural.ZERO, 1L, false);
      TestGetAt(Natural.Of(4), 5L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 15L);
    }

    @Test
    @DisplayName("Construction from empty container")
    public void ConstructionFromEmptyContainer() {
      AddTest(3);
      LLList<Long> source = new LLList<>();
      NewNonEmptyContainer(source);
      TestIsEmpty(true, false);
      TestSize(0, false);
    }

    @Test
    @DisplayName("Construction with initial size")
    public void ConstructionWithInitialSize() {
      AddTest(5);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(10));
      TestSize(10, false);
      TestSetAt(42L, Natural.Of(5), false);
      TestGetAt(Natural.Of(5), 42L, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Equality with same elements same order")
    public void EqualitySameOrder() {
      AddTest(7);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(3));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      apsd.interfaces.containers.sequences.DynVector<Long> other = GetNewEmptyContainer();
      other.Expand(Natural.Of(3));
      other.SetAt(1L, Natural.ZERO);
      other.SetAt(2L, Natural.Of(1));
      other.SetAt(3L, Natural.Of(2));
      TestIsEqual(other, true);
    }

    @Test
    @DisplayName("Inequality with same elements different order")
    public void InequalityDifferentOrder() {
      AddTest(7);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(3));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      apsd.interfaces.containers.sequences.DynVector<Long> other = GetNewEmptyContainer();
      other.Expand(Natural.Of(3));
      other.SetAt(3L, Natural.ZERO);
      other.SetAt(2L, Natural.Of(1));
      other.SetAt(1L, Natural.Of(2));
      TestIsEqual(other, false);
    }

    @Test
    @DisplayName("Inequality with different sizes")
    public void InequalityDifferentSizes() {
      AddTest(6);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(3));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      apsd.interfaces.containers.sequences.DynVector<Long> other = GetNewEmptyContainer();
      other.Expand(Natural.Of(2));
      other.SetAt(1L, Natural.ZERO);
      other.SetAt(2L, Natural.Of(1));
      TestIsEqual(other, false);
    }

    @Test
    @DisplayName("Equality of two empty dynamic vectors")
    public void EqualityEmptyDynVectors() {
      AddTest(2);
      NewEmptyContainer();
      apsd.interfaces.containers.sequences.DynVector<Long> other = GetNewEmptyContainer();
      TestIsEqual(other, true);
    }

    @Test
    @DisplayName("Copy construction preserves elements")
    public void CopyConstructionPreservesElements() {
      AddTest(9);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(4));
      TestSetAt(10L, Natural.ZERO, false);
      TestSetAt(20L, Natural.Of(1), false);
      TestSetAt(30L, Natural.Of(2), false);
      TestSetAt(40L, Natural.Of(3), false);
      apsd.interfaces.containers.sequences.DynVector<Long> copy = GetNewNonEmptyContainer(ThisContainer());
      TestIsEqual(copy, true);
      TestSize(4, false);
      copy.SetAt(999L, Natural.ZERO);
      TestGetAt(Natural.ZERO, 10L, false);
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
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(null, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestGetAt(Natural.Of(1), null, false);
    }

    @Test
    @DisplayName("SetFirst with null")
    public void SetFirstNull() {
      AddTest(3);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(1));
      TestSetFirst(null, false);
      TestGetFirst(null, false);
    }

    @Test
    @DisplayName("SetLast with null")
    public void SetLastNull() {
      AddTest(3);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(1));
      TestSetLast(null, false);
      TestGetLast(null, false);
    }

    @Test
    @DisplayName("Exists with null")
    public void ExistsNull() {
      AddTest(6);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(3));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(null, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestExists(null, true);
    }

    @Test
    @DisplayName("GetAt with null Natural")
    public void GetAtNullNatural() {
      AddTest(3);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(3));
      TestGetAt(null, 0L, true);
    }

    @Test
    @DisplayName("SetAt with null Natural")
    public void SetAtNullNatural() {
      AddTest(3);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(3));
      TestSetAt(1L, null, true);
    }

    @Test
    @DisplayName("ShiftLeft with null Natural")
    public void ShiftLeftNullNatural() {
      AddTest(5);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestShiftLeft(null, Natural.ONE);
      TestShiftLeft(Natural.ZERO, null);
    }

    @Test
    @DisplayName("Realloc with null Natural")
    public void ReallocNullNatural() {
      AddTest(3);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(3));
      TestRealloc(null);
    }

    @Test
    @DisplayName("InsertAt with null Natural")
    public void InsertAtNullNatural() {
      AddTest(3);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(3));
      TestInsertAtWithAutoExpansion(null, 1L);
    }

    @Test
    @DisplayName("AtNRemove with null Natural")
    public void AtNRemoveNullNatural() {
      AddTest(3);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(3));
      TestAtNRemoveWithAutoReduction(null, 0L);
    }

    @Test
    @DisplayName("IsEqual with null container")
    public void IsEqualNullContainer() {
      AddTest(3);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(3));
      TestIsEqual(null, false);
    }

    @Test
    @DisplayName("FoldForward with null accumulator")
    public void FoldForwardNullAccumulator() {
      AddTest(4);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(3));
      TestSetAt(1L, Natural.ZERO, false);
      TestFoldForward((dat, acc) -> acc != null ? dat != null ? acc + dat : acc : dat, null, 1L);
    }

    @Test
    @DisplayName("FoldBackward with null accumulator")
    public void FoldBackwardNullAccumulator() {
      AddTest(4);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(3));
      TestSetAt(1L, Natural.Of(1), false);
      TestSetAt(1L, Natural.Of(2), false);
      TestFoldBackward((dat, acc) -> acc != null ? dat != null ? acc + dat : acc : dat, null, 2L);
    }

    @Test
    @DisplayName("Operations with null elements maintain consistency")
    public void NullOperationsConsistency() {
      AddTest(8);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(3));
      TestSetAt(null, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(null, Natural.Of(2), false);
      TestSize(3, false);
      TestExists(null, true);
      TestExists(2L, true);
      TestGetAt(Natural.ZERO, null, false);
      TestGetAt(Natural.Of(2), null, false);
    }

    @Test
    @DisplayName("Empty dynamic vector null operations")
    public void EmptyDynVectorNullOperations() {
      AddTest(4);
      NewEmptyContainer();
      TestExists(null, false);
      TestGetAt(null, 0L, true);
      TestSetAt(null, null, true);
    }

  }

  @Nested
  @DisplayName("DynVector Comprehensive Method Combinations")
  public class DynVectorComprehensiveMethodCombinations {

    @Test
    @DisplayName("Expand, SetAt, GetAt, GetFirst, GetLast comprehensive")
    public void ExpandSetAtGetAtComprehensive() {
      AddTest(20);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestSize(5, false);
      TestSetAt(10L, Natural.ZERO, false);
      TestSetAt(20L, Natural.Of(1), false);
      TestSetAt(30L, Natural.Of(2), false);
      TestSetAt(40L, Natural.Of(3), false);
      TestSetAt(50L, Natural.Of(4), false);
      TestGetAt(Natural.ZERO, 10L, false);
      TestGetAt(Natural.Of(2), 30L, false);
      TestGetAt(Natural.Of(4), 50L, false);
      TestGetFirst(10L, false);
      TestGetLast(50L, false);
      TestExists(30L, true);
      TestExists(25L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 150L);
      TestFIterator();
      TestBIterator();
      TestForwardIteration(false, false);
      TestBackwardIteration(false, false);
    }

    @Test
    @DisplayName("SetFirst, SetLast with GetFirst, GetLast verification")
    public void SetFirstLastVerification() {
      AddTest(16);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestSetFirst(1L, false);
      TestSetLast(5L, false);
      TestGetFirst(1L, false);
      TestGetLast(5L, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestSetAt(4L, Natural.Of(3), false);
      TestSetFirst(10L, false);
      TestGetFirst(10L, false);
      TestSetLast(50L, false);
      TestGetLast(50L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 69L);
      TestSize(5, false);
      TestIsEmpty(false, false);
    }

    @Test
    @DisplayName("GetNSetFirst, GetNSetLast, GetNSetAt chain")
    public void GetNSetChain() {
      AddTest(18);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestSetAt(4L, Natural.Of(3), false);
      TestSetAt(5L, Natural.Of(4), false);
      TestGetNSetFirst(10L, 1L, false);
      TestGetFirst(10L, false);
      TestGetNSetLast(50L, 5L, false);
      TestGetLast(50L, false);
      TestGetNSetAt(30L, 3L, Natural.Of(2), false);
      TestGetAt(Natural.Of(2), 30L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 96L);
      TestSize(5, false);
      TestExists(10L, true);
      TestExists(50L, true);
      TestExists(30L, true);
    }

    @Test
    @DisplayName("Grow and Reduce cycles with verification")
    public void GrowReduceCycles() {
      AddTest(22);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(3));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestSize(3, false);
      TestGrowWithNumber(Natural.Of(2), true);
      TestSize(3, false);
      TestCapacity(6);
      TestExpandWithNumber(Natural.Of(2));
      TestSize(5, false);
      TestSetAt(4L, Natural.Of(3), false);
      TestSetAt(5L, Natural.Of(4), false);
      TestGetFirst(1L, false);
      TestGetLast(5L, false);
      TestReduceWithNumber(Natural.Of(2));
      TestSize(3, false);
      TestGetLast(3L, false);
      TestExpandWithNumber(Natural.Of(3));
      TestSize(6, false);
      TestReduceWithNumber(Natural.Of(4));
      TestSize(2, false);
      TestGetFirst(1L, false);
      TestGetLast(2L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 3L);
    }

    @Test
    @DisplayName("Expand and Shrink with data verification")
    public void ExpandShrinkWithDataVerification() {
      AddTest(18);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestSetAt(10L, Natural.ZERO, false);
      TestSetAt(20L, Natural.Of(1), false);
      TestSetAt(30L, Natural.Of(2), false);
      TestSetAt(40L, Natural.Of(3), false);
      TestSetAt(50L, Natural.Of(4), false);
      TestSize(5, false);
      TestRealloc(Natural.Of(3));
      TestSize(3, false);
      TestGetFirst(10L, false);
      TestGetLast(30L, false);
      TestExpandWithNumber(Natural.Of(2));
      TestSize(5, false);
      TestSetAt(60L, Natural.Of(3), false);
      TestSetAt(70L, Natural.Of(4), false);
      TestGetLast(70L, false);
      TestFoldForward((dat, acc) -> acc + (dat != null ? dat : 0), 0L, 190L);
    }

    @Test
    @DisplayName("Realloc preserving front elements")
    public void ReallocPreservingFrontElements() {
      AddTest(16);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestSetAt(4L, Natural.Of(3), false);
      TestSetAt(5L, Natural.Of(4), false);
      TestSize(5, false);
      TestRealloc(Natural.Of(3));
      TestSize(3, false);
      TestGetFirst(1L, false);
      TestGetAt(Natural.Of(1), 2L, false);
      TestGetLast(3L, false);
      TestRealloc(Natural.Of(6));
      TestCapacity(6);
      TestSize(3, false);
      TestGetAt(Natural.Of(2), 3L, false);
    }

    @Test
    @DisplayName("ShiftLeft and ShiftRight comprehensive")
    public void ShiftLeftRightComprehensive() {
      AddTest(20);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestSetAt(4L, Natural.Of(3), false);
      TestSetAt(5L, Natural.Of(4), false);
      TestSize(5, false);
      TestShiftLeft(Natural.ZERO, Natural.ONE);
      TestSize(4, false);
      TestGetFirst(2L, false);
      TestShiftRight(Natural.ZERO, Natural.Of(2));
      TestSize(6, false);
      TestGetAt(Natural.Of(2), 2L, false);
      TestShiftLeft(Natural.Of(3), Natural.Of(2));
      TestSize(4, false);
      TestShiftRight(Natural.Of(2), Natural.ONE);
      TestSize(5, false);
      TestGetFirst(null, false);
      TestFIterator();
      TestBIterator();
    }

    @Test
    @DisplayName("InsertAt with GetAt verification")
    public void InsertAtWithGetAtVerification() {
      AddTest(18);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(3));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestSize(3, false);
      TestInsertAtWithAutoExpansion(Natural.Of(1), 10L);
      TestSize(4, false);
      TestGetAt(Natural.Of(1), 10L, false);
      TestGetAt(Natural.Of(2), 2L, false);
      TestInsertAtWithAutoExpansion(Natural.ZERO, 0L);
      TestSize(5, false);
      TestGetFirst(0L, false);
      TestInsertAtWithAutoExpansion(Natural.Of(5), 100L);
      TestSize(6, false);
      TestGetLast(100L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 116L);
    }

    @Test
    @DisplayName("RemoveAt, RemoveFirst, RemoveLast chain")
    public void RemoveAtFirstLastChain() {
      AddTest(22);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(7));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestSetAt(4L, Natural.Of(3), false);
      TestSetAt(5L, Natural.Of(4), false);
      TestSetAt(6L, Natural.Of(5), false);
      TestSetAt(7L, Natural.Of(6), false);
      TestSize(7, false);
      TestAtNRemoveWithAutoReduction(Natural.Of(3), 4L);
      TestSize(6, false);
      TestGetAt(Natural.Of(3), 5L, false);
      TestRemoveFirstWithAutoReduction();
      TestSize(5, false);
      TestGetFirst(2L, false);
      TestRemoveLastWithAutoReduction();
      TestSize(4, false);
      TestGetLast(6L, false);
      TestRemoveFirstWithAutoReduction();
      TestRemoveLastWithAutoReduction();
      TestSize(2, false);
      TestGetFirst(3L, false);
      TestGetLast(5L, false);
    }

    @Test
    @DisplayName("FirstNRemove, LastNRemove, AtNRemove chain")
    public void FirstLastAtNRemoveChain() {
      AddTest(20);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestSetAt(4L, Natural.Of(3), false);
      TestSetAt(5L, Natural.Of(4), false);
      TestSize(5, false);
      TestFirstNRemoveWithAutoReduction(1L);
      TestSize(4, false);
      TestGetFirst(2L, false);
      TestLastNRemoveWithAutoReduction(5L);
      TestSize(3, false);
      TestGetLast(4L, false);
      TestAtNRemoveWithAutoReduction(Natural.Of(1), 3L);
      TestSize(2, false);
      TestGetAt(Natural.Of(1), 4L, false);
      TestFirstNRemoveWithAutoReduction(2L);
      TestLastNRemoveWithAutoReduction(4L);
      TestSize(0, false);
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("InsertFirst, InsertLast with verification")
    public void InsertFirstLastVerification() {
      AddTest(18);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(3));
      TestSetAt(2L, Natural.ZERO, false);
      TestSetAt(3L, Natural.Of(1), false);
      TestSetAt(4L, Natural.Of(2), false);
      TestSize(3, false);
      TestInsertFirstWithAutoExpansion(1L);
      TestSize(4, false);
      TestGetFirst(1L, false);
      TestGetAt(Natural.Of(1), 2L, false);
      TestInsertLastWithAutoExpansion(5L);
      TestSize(5, false);
      TestGetLast(5L, false);
      TestInsertFirstWithAutoExpansion(0L);
      TestInsertLastWithAutoExpansion(6L);
      TestSize(7, false);
      TestGetFirst(0L, false);
      TestGetLast(6L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 21L);
    }

    @Test
    @DisplayName("Search with various positions")
    public void SearchWithVariousPositions() {
      AddTest(16);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestSetAt(10L, Natural.ZERO, false);
      TestSetAt(20L, Natural.Of(1), false);
      TestSetAt(30L, Natural.Of(2), false);
      TestSetAt(40L, Natural.Of(3), false);
      TestSetAt(50L, Natural.Of(4), false);
      TestSize(5, false);
      TestSearch(10L, Natural.ZERO);
      TestSearch(30L, Natural.Of(2));
      TestSearch(50L, Natural.Of(4));
      TestSearch(25L, null);
      TestSearch(100L, null);
      TestExists(30L, true);
      TestExists(35L, false);
      TestGetAt(Natural.Of(2), 30L, false);
    }

    @Test
    @DisplayName("Swap operations comprehensive")
    public void SwapOperationsComprehensive() {
      AddTest(18);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestSetAt(4L, Natural.Of(3), false);
      TestSetAt(5L, Natural.Of(4), false);
      TestSize(5, false);
      TestSwap(Natural.ZERO, Natural.Of(4), false);
      TestGetFirst(5L, false);
      TestGetLast(1L, false);
      TestSwap(Natural.Of(1), Natural.Of(3), false);
      TestGetAt(Natural.Of(1), 4L, false);
      TestGetAt(Natural.Of(3), 2L, false);
      TestSwap(Natural.Of(2), Natural.Of(2), false);
      TestGetAt(Natural.Of(2), 3L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 15L);
    }

    @Test
    @DisplayName("Shift then SubVector")
    public void ShiftThenSubVector() {
      AddTest(4);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(6));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestSetAt(4L, Natural.Of(3), false);
      TestSetAt(5L, Natural.Of(4), false);
      TestSetAt(6L, Natural.Of(5), false);
      TestShiftRight(Natural.ZERO, Natural.Of(3));
      TestPrintContent("");
      TestSize(9L, false);
      TestSubVector(Natural.ZERO, Natural.Of(2), false);
    }

    @Test
    @DisplayName("Shift Branches Match LinearVector")
    public void ShiftBranchesMatchLinearVector() {
      var circ = new DynCircularVector<Long>(Natural.Of(8));
      var lin = new DynVector<Long>(Natural.Of(8));
      circ.Expand(Natural.Of(8));
      lin.Expand(Natural.Of(8));
      for (long i = 0; i < 8; i++) {
        circ.SetAt(i, Natural.Of(i));
        lin.SetAt(i, Natural.Of(i));
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
      var circ = new DynCircularVector<Long>(Natural.Of(800));
      var lin = new DynVector<Long>(Natural.Of(800));

      circ.Expand(Natural.Of(800));
      lin.Expand(Natural.Of(800));
      for (long i = 0; i < 800; i++) {
        circ.SetAt(i, Natural.Of(i));
        lin.SetAt(i, Natural.Of(i));
      }

      // force non-zero start early
      circ.ShiftLeft(Natural.ZERO, Natural.Of(37));
      lin.ShiftLeft(Natural.ZERO, Natural.Of(37));

      Random rnd = new Random(12388789L);
      for (int q = 0; q < 10; q++) {
        for (long i = 0; i < lin.Size().ToLong(); i++) {
          circ.SetAt(i, Natural.Of(i));
          lin.SetAt(i, Natural.Of(i));
        }
        for (int t = 0; t < 500; t++) {
          long pos = rnd.nextInt((int)lin.Size().ToLong());
          long num = rnd.nextInt(3); // includes 0 and values > remaining (clamp path)
          //System.out.println("Lin after shift: " + pos + ", num=" + num + ", t=" + t);
            /*if (t == 8) {
            System.out.println("Lin before shift: ");
            for (long i = 0; i < lin.Size().ToLong(); i++) {
              System.out.print(lin.GetAt(Natural.Of(i)) + " ");
              if (i % 10 == 9)
                System.out.println();
            }
            System.out.println("Circ before shift: ");
            for (long i = 0; i < lin.Size().ToLong(); i++) {
              System.out.print(circ.GetAt(Natural.Of(i)) + " ");
              if (i % 10 == 9)
                System.out.println();
            }
            System.out.println();
          }*/
          boolean left = false;
          if (rnd.nextBoolean()) {
            left = true;
            circ.ShiftLeft(Natural.Of(pos), Natural.Of(num));
            lin.ShiftLeft(Natural.Of(pos), Natural.Of(num));
          } else {
            circ.ShiftRight(Natural.Of(pos), Natural.Of(num));
            lin.ShiftRight(Natural.Of(pos), Natural.Of(num));
          }
          /*if (t == 499) {
            for (long i = 0; i < lin.Size().ToLong(); i++) {
              System.out.print(lin.GetAt(Natural.Of(i)) + " ");
              if (i % 10 == 9)
                System.out.println();
            }
            System.out.println();
            System.out.println("Circ after shift: ");
            for (long i = 0; i < lin.Size().ToLong(); i++) {
              System.out.print(circ.GetAt(Natural.Of(i)) + " ");
              if (i % 10 == 9)
                System.out.println();
            }
            System.out.println();
          }*/
          for (long i = 0; i < lin.Size().ToLong(); i++) {
            assertEquals(lin.GetAt(Natural.Of(i)), circ.GetAt(Natural.Of(i)),
                "Mismatch at step " + t + ", index " + i + ", pos=" + pos + ", num=" + num + ", left=" + left);
          }
        }
        for (int t = 0; t < 500; t++) {
          //System.out.println("lin size " + lin.Size().ToLong() + " sizeint: " + (int) lin.Size().ToLong());
          long pos = rnd.nextInt(Math.max((int)lin.Size().ToLong(),1));
          long num = rnd.nextInt(1200); // includes 0 and values > remaining (clamp path)
          //System.out.println("Lin State: " + pos + ", num=" + num + ", t=" + t);
          boolean left = false;
          if (!rnd.nextBoolean() || lin.Size().ToLong() == 0) {
            circ.ShiftRight(Natural.Of(pos), Natural.Of(num));
            lin.ShiftRight(Natural.Of(pos), Natural.Of(num));
          } else {
            //System.out.println("left");
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
    }

    @Test
    @DisplayName("SubSequence and SubVector verification")
    public void SubSequenceSubVectorVerification() {
      AddTest(14);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestSetAt(4L, Natural.Of(3), false);
      TestSetAt(5L, Natural.Of(4), false);
      TestSize(5, false);
      TestSubSequence(Natural.Of(1), Natural.Of(3), false);
      TestSubVector(Natural.ZERO, Natural.Of(2), false);
      TestSubSequence(Natural.ZERO, Natural.Of(4), false);
      TestSubVector(Natural.Of(2), Natural.Of(4), false);
      TestSubSequence(Natural.Of(5), Natural.Of(6), true);
      TestSubVector(Natural.Of(3), Natural.Of(1), true);
      TestSize(5, false);
    }

    @Test
    @DisplayName("TraverseForward and TraverseBackward verification")
    public void TraverseForwardBackwardVerification() {
      AddTest(16);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestSetAt(4L, Natural.Of(3), false);
      TestSetAt(5L, Natural.Of(4), false);
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
      TestExpandWithNumber(Natural.Of(5));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestSetAt(4L, Natural.Of(3), false);
      TestSetAt(5L, Natural.Of(4), false);
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
      AddTest(12);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestSetAt(4L, Natural.Of(3), false);
      TestSetAt(5L, Natural.Of(4), false);
      TestFIterator();
      TestBIterator();
      TestForwardIteration(false, false);
      TestBackwardIteration(false, false);
      TestSize(5, false);
      TestIsEmpty(false, false);
    }

    @Test
    @DisplayName("Clear then rebuild with verification")
    public void ClearThenRebuildWithVerification() {
      AddTest(24);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(3));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestSize(3, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 6L);
      TestClear();
      TestIsEmpty(true, false);
      TestSize(0, false);
      TestExpandWithNumber(Natural.Of(4));
      TestSetAt(10L, Natural.ZERO, false);
      TestSetAt(20L, Natural.Of(1), false);
      TestSetAt(30L, Natural.Of(2), false);
      TestSetAt(40L, Natural.Of(3), false);
      TestSize(4, false);
      TestGetFirst(10L, false);
      TestGetLast(40L, false);
      TestClear();
      TestExpandWithNumber(Natural.Of(2));
      TestSetAt(100L, Natural.ZERO, false);
      TestSetAt(200L, Natural.Of(1), false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 300L);
    }

    @Test
    @DisplayName("IsEqual verification after modifications")
    public void IsEqualAfterModifications() {
      AddTest(18);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(3));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      
      DynCircularVector<Long> otherVector = new DynCircularVector<>(Natural.Of(3));
      otherVector.Expand(Natural.Of(3));
      otherVector.SetAt(1L, Natural.ZERO);
      otherVector.SetAt(2L, Natural.Of(1));
      otherVector.SetAt(3L, Natural.Of(2));
      
      TestIsEqual(otherVector, true);
      TestSetAt(4L, Natural.Of(2), false);
      TestIsEqual(otherVector, false);
      otherVector.SetAt(4L, Natural.Of(2));
      TestIsEqual(otherVector, true);
      TestGrowWithNumber(Natural.ONE, true);
      TestIsEqual(otherVector, true);
      TestExpandWithNumber(Natural.ONE);
      TestIsEqual(otherVector, false);
      otherVector.Grow(Natural.ONE);
      TestIsEqual(otherVector, false);
      otherVector.Expand(Natural.ONE);
      TestIsEqual(otherVector, true);
      TestSize(4, false);
    }

    @Test
    @DisplayName("Empty vector all operations")
    public void EmptyVectorAllOperations() {
      AddTest(18);
      NewEmptyContainer();
      TestIsEmpty(true, false);
      TestSize(0, false);
      TestGetFirst(0L, true);
      TestGetLast(0L, true);
      TestGetAt(Natural.ZERO, 0L, true);
      TestExists(1L, false);
      TestSearch(1L, null);
      TestFIterator();
      TestBIterator();
      TestForwardIteration(false, true);
      TestBackwardIteration(false, true);
      TestSubSequence(Natural.ZERO, Natural.ZERO, true);
      TestFoldForward((dat, acc) -> acc, 0L, 0L);
      TestFoldBackward((dat, acc) -> acc, 0L, 0L);
      TestClear();
      TestIsEmpty(true, false);
      TestSize(0, false);
    }

    @Test
    @DisplayName("Single element all operations")
    public void SingleElementAllOperations() {
      AddTest(24);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.ONE);
      TestSetAt(42L, Natural.ZERO, false);
      TestSize(1, false);
      TestIsEmpty(false, false);
      TestGetFirst(42L, false);
      TestGetLast(42L, false);
      TestGetAt(Natural.ZERO, 42L, false);
      TestExists(42L, true);
      TestSearch(42L, Natural.ZERO);
      TestSearch(100L, null);
      TestFIterator();
      TestBIterator();
      TestForwardIteration(false, false);
      TestBackwardIteration(false, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 42L);
      TestFoldBackward((dat, acc) -> acc + dat, 0L, 42L);
      TestSubSequence(Natural.ZERO, Natural.ZERO, false);
      TestSwap(Natural.ZERO, Natural.ZERO, false);
      TestGetAt(Natural.ZERO, 42L, false);
      TestRemoveFirstWithAutoReduction();
      TestIsEmpty(true, false);
      TestSize(0, false);
    }

    @Test
    @DisplayName("Extreme values handling")
    public void ExtremeValuesHandling() {
      AddTest(18);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(3));
      TestSetAt(Long.MIN_VALUE, Natural.ZERO, false);
      TestSetAt(0L, Natural.Of(1), false);
      TestSetAt(Long.MAX_VALUE, Natural.Of(2), false);
      TestSize(3, false);
      TestGetFirst(Long.MIN_VALUE, false);
      TestGetLast(Long.MAX_VALUE, false);
      TestExists(Long.MIN_VALUE, true);
      TestExists(Long.MAX_VALUE, true);
      TestExists(0L, true);
      TestSearch(Long.MIN_VALUE, Natural.ZERO);
      TestSearch(0L, Natural.Of(1));
      TestSearch(Long.MAX_VALUE, Natural.Of(2));
      TestFIterator();
      TestBIterator();
      TestForwardIteration(false, false);
      TestBackwardIteration(false, false);
    }

    @Test
    @DisplayName("Complex interleaved operations")
    public void ComplexInterleavedOperations() {
      AddTest(32);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(3));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestSize(3, false);
      TestExpandWithNumber(Natural.Of(2));
      TestSetAt(4L, Natural.Of(3), false);
      TestSetAt(5L, Natural.Of(4), false);
      TestSize(5, false);
      TestSwap(Natural.ZERO, Natural.Of(4), false);
      TestGetFirst(5L, false);
      TestGetLast(1L, false);
      TestRemoveFirstWithAutoReduction();
      TestRemoveLastWithAutoReduction();
      TestSize(3, false);
      TestGetFirst(2L, false);
      TestGetLast(4L, false);
      TestInsertAtWithAutoExpansion(Natural.Of(1), 10L);
      TestSize(4, false);
      TestGetAt(Natural.Of(1), 10L, false);
      TestRealloc(Natural.Of(2));
      TestSize(2, false);
      TestGetFirst(2L, false);
      TestGetLast(10L, false);
      TestClear();
      TestIsEmpty(true, false);
      TestExpandWithNumber(Natural.Of(2));
      TestSetAt(100L, Natural.ZERO, false);
      TestSetAt(200L, Natural.Of(1), false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 300L);
    }

    @Test
    @DisplayName("Build, verify, clear, rebuild multiple cycles")
    public void BuildVerifyClearRebuildCycles() {
      AddTest(34);
      NewEmptyContainer();
      
      // Cycle 1
      TestExpandWithNumber(Natural.Of(3));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestSize(3, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 6L);
      TestClear();
      TestIsEmpty(true, false);
      
      // Cycle 2
      TestExpandWithNumber(Natural.Of(4));
      TestSetAt(10L, Natural.ZERO, false);
      TestSetAt(20L, Natural.Of(1), false);
      TestSetAt(30L, Natural.Of(2), false);
      TestSetAt(40L, Natural.Of(3), false);
      TestSize(4, false);
      TestGetFirst(10L, false);
      TestGetLast(40L, false);
      TestClear();
      
      // Cycle 3
      TestExpandWithNumber(Natural.Of(5));
      TestSetAt(5L, Natural.ZERO, false);
      TestSetAt(10L, Natural.Of(1), false);
      TestSetAt(15L, Natural.Of(2), false);
      TestSetAt(20L, Natural.Of(3), false);
      TestSetAt(25L, Natural.Of(4), false);
      TestSize(5, false);
      TestSearch(15L, Natural.Of(2));
      TestSwap(Natural.Of(1), Natural.Of(3), false);
      TestClear();
      
      // Cycle 4
      TestExpandWithNumber(Natural.Of(2));
      TestSetAt(100L, Natural.ZERO, false);
      TestSetAt(200L, Natural.Of(1), false);
      TestForwardIteration(false, false);
      TestBackwardIteration(false, false);
      TestClear();
      TestIsEmpty(true, false);
      TestSize(0, false);
    }

    @Test
    @DisplayName("Large vector mixed operations")
    public void LargeVectorMixedOperations() {
      AddTest(40);
      NewEmptyContainer();
      
      // Build a vector with 20 elements
      TestExpandWithNumber(Natural.Of(20));
      for (int i = 0; i < 20; i++) {
        TestSetAt((long)(i + 1), Natural.Of(i), false);
      }
      TestSize(20, false);
      TestGetFirst(1L, false);
      TestGetLast(20L, false);
      
      // Remove first 5
      for (int i = 0; i < 5; i++) {
        TestRemoveFirstWithAutoReduction();
      }
      TestSize(15, false);
      TestGetFirst(6L, false);
      
      // Remove last 5
      for (int i = 0; i < 5; i++) {
        TestRemoveLastWithAutoReduction();
      }
      TestSize(10, false);
      TestGetLast(15L, false);
      
      // Verify sum
      TestFoldForward((dat, acc) -> acc + dat, 0L, 105L);
      
      TestClear();
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Shift operations comprehensive")
    public void ShiftOperationsComprehensive() {
      AddTest(26);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestSetAt(4L, Natural.Of(3), false);
      TestSetAt(5L, Natural.Of(4), false);
      TestSize(5, false);
      
      // ShiftLeft removes from beginning
      TestShiftLeft(Natural.ZERO, Natural.Of(2));
      TestSize(3, false);
      TestGetFirst(3L, false);
      
      // ShiftRight adds at beginning
      TestShiftRight(Natural.ZERO, Natural.Of(2));
      TestSize(5, false);
      TestGetAt(Natural.Of(2), 3L, false);
      
      // ShiftLeft at middle
      TestShiftLeft(Natural.Of(1), Natural.Of(2));
      TestSize(3, false);
      
      // ShiftRight at middle
      TestShiftRight(Natural.Of(1), Natural.Of(2));
      TestSize(5, false);
      
      TestGetFirst(null, false);

      TestGetNSetAt(1L, null, Natural.ZERO, false);
      TestGetNSetAt(2L, null, Natural.Of(1), false);
      TestGetNSetAt(3L, null, Natural.Of(2), false);
      TestFIterator();
      TestBIterator();
      TestForwardIteration(false, false);
      TestBackwardIteration(false, false);
    }

    @Test
    @DisplayName("Capacity and Size relationship")
    public void CapacitySizeRelationship() {
      AddTest(18);
      NewEmptyContainer();
      TestSize(0, false);
      TestIsEmpty(true, false);
      TestExpandWithNumber(Natural.Of(5));
      TestSize(5, false);
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestSetAt(4L, Natural.Of(3), false);
      TestSetAt(5L, Natural.Of(4), false);
      TestGrowWithNumber(Natural.Of(3), true);
      TestCapacity(10);
      TestReduceWithNumber(Natural.Of(3));
      TestSize(2, false);
      TestRealloc(Natural.Of(3));
      TestCapacity(3);
      TestSize(2, false);
      TestRealloc(Natural.Of(10));
      TestCapacity(10);
      TestSize(2, false);
      TestGetFirst(1L, false);
    }

    @Test
    @DisplayName("Duplicate values handling")
    public void DuplicateValuesHandling() {
      AddTest(18);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestSetAt(10L, Natural.ZERO, false);
      TestSetAt(10L, Natural.Of(1), false);
      TestSetAt(10L, Natural.Of(2), false);
      TestSetAt(10L, Natural.Of(3), false);
      TestSetAt(10L, Natural.Of(4), false);
      TestSize(5, false);
      TestGetFirst(10L, false);
      TestGetLast(10L, false);
      TestExists(10L, true);
      TestSearch(10L, Natural.ZERO);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 50L);
      TestSwap(Natural.ZERO, Natural.Of(4), false);
      TestGetFirst(10L, false);
      TestGetLast(10L, false);
      TestRemoveFirstWithAutoReduction();
      TestSize(4, false);
      TestExists(10L, true);
    }

    @Test
    @DisplayName("Mixed null and non-null elements")
    public void MixedNullNonNullElements() {
      AddTest(18);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(null, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestSetAt(null, Natural.Of(3), false);
      TestSetAt(5L, Natural.Of(4), false);
      TestSize(5, false);
      TestGetFirst(1L, false);
      TestGetLast(5L, false);
      TestGetAt(Natural.Of(1), null, false);
      TestGetAt(Natural.Of(3), null, false);
      TestExists(null, true);
      TestExists(3L, true);
      TestSwap(Natural.Of(4), Natural.Of(3), false);
      TestGetAt(Natural.Of(4), null, false);
      TestFIterator();
      TestBIterator();
      TestSetAt(20L, Natural.Of(1), false);
      TestSetAt(40L, Natural.Of(4), false);
      TestForwardIteration(false, false);
    }

    @Test
    @DisplayName("Sequential access verification")
    public void SequentialAccessVerification() {
      AddTest(18);
      NewEmptyContainer();
      TestExpandWithNumber(Natural.Of(5));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestSetAt(4L, Natural.Of(3), false);
      TestSetAt(5L, Natural.Of(4), false);
      TestSize(5, false);
      // Forward access
      TestGetAt(Natural.ZERO, 1L, false);
      TestGetAt(Natural.Of(1), 2L, false);
      TestGetAt(Natural.Of(2), 3L, false);
      TestGetAt(Natural.Of(3), 4L, false);
      TestGetAt(Natural.Of(4), 5L, false);
      // Backward access
      TestGetAt(Natural.Of(4), 5L, false);
      TestGetAt(Natural.Of(3), 4L, false);
      TestGetAt(Natural.Of(2), 3L, false);
      TestGetAt(Natural.Of(1), 2L, false);
      TestGetAt(Natural.ZERO, 1L, false);
    }

  }

}
