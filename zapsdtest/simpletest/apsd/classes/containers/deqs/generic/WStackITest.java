package zapsdtest.simpletest.apsd.classes.containers.deqs.generic;

import apsd.classes.containers.collections.concretecollections.LLList;
import apsd.classes.containers.deqs.WStack;
import apsd.interfaces.containers.deqs.Stack;

import org.junit.jupiter.api.*;

abstract public class WStackITest extends WStackTest<Long> {

  @Override
  public void NewNonEmptyContainer() {
    AddTest(6);
    NewEmptyContainer();
    TestInsert(4L, true);
    TestPush(0L);
    TestInsert(3L, true);
    TestPush(1L);
    TestInsert(2L, true);
    TestSize(5, false);
  }

  @Nested
  @DisplayName("Stack Basics")
  public class StackBasics {

    @Test
    @DisplayName("Check starting from an Empty Stack")
    public void Empty() {
      AddTest(3);
      NewEmptyContainer();
      TestTop(null);
      TestPop(true);
      TestTopNPop(null, true);
    }

    @Test
    @DisplayName("Check starting from a NonEmpty Stack")
    public void NonEmpty() {
      AddTest(11);
      NewNonEmptyContainer();
      TestSize(5, false);
      TestTopNPop(2L, false);
      TestTop(1L);
      TestPush(5L);
      TestPush(6L);
      TestTop(6L);
      TestTopNPop(6L, false);
      TestPop(false);
      TestTop(1L);
      TestClear();
      TestTopNPop(null, true);
    }

  }

  @Nested
  @DisplayName("Stack Edge Cases")
  public class StackEdgeCases {

    @Test
    @DisplayName("LIFO order verification")
    public void LIFOOrder() {
      AddTest(12);
      NewEmptyContainer();
      TestPush(10L);
      TestPush(20L);
      TestPush(30L);
      TestPush(40L);
      TestPush(50L);
      TestTopNPop(50L, false);
      TestTopNPop(40L, false);
      TestTopNPop(30L, false);
      TestTopNPop(20L, false);
      TestTopNPop(10L, false);
      TestIsEmpty(true, false);
      TestTop(null);
    }

    @Test
    @DisplayName("Single element stack")
    public void SingleElement() {
      AddTest(7);
      NewEmptyContainer();
      TestPush(42L);
      TestSize(1, false);
      TestTop(42L);
      TestPop(false);
      TestIsEmpty(true, false);
      TestTop(null);
      TestPop(true);
    }

    @Test
    @DisplayName("Push after complete pop")
    public void PushAfterEmpty() {
      AddTest(11);
      NewEmptyContainer();
      TestPush(1L);
      TestPush(2L);
      TestPop(false);
      TestPop(false);
      TestIsEmpty(true, false);
      TestPush(3L);
      TestPush(4L);
      TestSize(2, false);
      TestTop(4L);
      TestTopNPop(4L, false);
      TestTop(3L);
    }

    @Test
    @DisplayName("Large number of operations")
    public void LargeOperations() {
      AddTest(154);
      NewEmptyContainer();
      for (long i = 1L; i <= 100L; i++) {
        TestPush(i);
      }
      TestSize(100, false);
      TestTop(100L);
      for (int i = 0; i < 50; i++) {
        TestPop(false);
      }
      TestSize(50, false);
      TestTop(50L);
    }

    @Test
    @DisplayName("Alternating push and pop")
    public void AlternatingOperations() {
      AddTest(15);
      NewEmptyContainer();
      TestPush(1L);
      TestTop(1L);
      TestPush(2L);
      TestPop(false);
      TestTop(1L);
      TestPush(3L);
      TestPush(4L);
      TestPop(false);
      TestTop(3L);
      TestPop(false);
      TestTop(1L);
      TestPop(false);
      TestIsEmpty(true, false);
      TestPush(5L);
      TestTop(5L);
    }

    @Test
    @DisplayName("Two element stack operations")
    public void TwoElementStack() {
      AddTest(14);
      NewEmptyContainer();
      TestPush(1L);
      TestPush(2L);
      TestSize(2, false);
      TestTop(2L);
      TestPop(false);
      TestTop(1L);
      TestSize(1, false);
      TestPush(3L);
      TestSize(2, false);
      TestTop(3L);
      TestTopNPop(3L, false);
      TestTop(1L);
      TestTopNPop(1L, false);
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Negative numbers in stack")
    public void NegativeNumbers() {
      AddTest(12);
      NewEmptyContainer();
      TestPush(-10L);
      TestPush(-5L);
      TestPush(0L);
      TestPush(5L);
      TestPush(10L);
      TestTop(10L);
      TestTopNPop(10L, false);
      TestTop(5L);
      TestTopNPop(5L, false);
      TestTop(0L);
      TestSize(3, false);
    }

    @Test
    @DisplayName("Extreme values in stack")
    public void ExtremeValues() {
      AddTest(10);
      NewEmptyContainer();
      TestPush(Long.MIN_VALUE);
      TestPush(Long.MAX_VALUE);
      TestPush(0L);
      TestTop(0L);
      TestTopNPop(0L, false);
      TestTop(Long.MAX_VALUE);
      TestTopNPop(Long.MAX_VALUE, false);
      TestTop(Long.MIN_VALUE);
    }

    @Test
    @DisplayName("Clear and reuse stack")
    public void ClearAndReuse() {
      AddTest(14);
      NewEmptyContainer();
      TestPush(1L);
      TestPush(2L);
      TestPush(3L);
      TestSize(3, false);
      TestClear();
      TestIsEmpty(true, false);
      TestSize(0, false);
      TestTop(null);
      TestPush(10L);
      TestPush(20L);
      TestSize(2, false);
      TestTop(20L);
      TestTopNPop(20L, false);
      TestTop(10L);
    }

    @Test
    @DisplayName("Duplicate values in stack")
    public void DuplicateValues() {
      AddTest(12);
      NewEmptyContainer();
      TestPush(5L);
      TestPush(5L);
      TestPush(5L);
      TestSize(3, false);
      TestTop(5L);
      TestTopNPop(5L, false);
      TestTop(5L);
      TestTopNPop(5L, false);
      TestTop(5L);
      TestTopNPop(5L, false);
      TestIsEmpty(true, false);
      TestTop(null);
    }

  }

  @Nested
  @DisplayName("Stack Stress Tests")
  public class StackStressTests {

    @Test
    @DisplayName("Rapid push pop cycles")
    public void RapidPushPopCycles() {
      AddTest(22);
      NewEmptyContainer();
      TestPush(1L);
      TestPop(false);
      TestPush(2L);
      TestPop(false);
      TestPush(3L);
      TestPop(false);
      TestIsEmpty(true, false);
      TestPush(10L);
      TestPush(20L);
      TestPush(30L);
      TestPop(false);
      TestPop(false);
      TestPop(false);
      TestIsEmpty(true, false);
      TestPush(100L);
      TestTop(100L);
      TestPush(200L);
      TestTop(200L);
      TestPop(false);
      TestTop(100L);
      TestSize(1, false);
      TestPop(false);
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Fill and empty multiple times")
    public void FillAndEmptyMultipleTimes() {
      AddTest(20);
      NewEmptyContainer();
      // First cycle
      TestPush(1L);
      TestPush(2L);
      TestPush(3L);
      TestTopNPop(3L, false);
      TestTopNPop(2L, false);
      TestTopNPop(1L, false);
      TestIsEmpty(true, false);
      // Second cycle
      TestPush(10L);
      TestPush(20L);
      TestTopNPop(20L, false);
      TestTopNPop(10L, false);
      TestIsEmpty(true, false);
      // Third cycle
      TestPush(100L);
      TestTop(100L);
      TestPush(200L);
      TestPush(300L);
      TestSize(3, false);
      TestClear();
      TestIsEmpty(true, false);
      TestTop(null);
    }

    @Test
    @DisplayName("Interleaved operations with size checks")
    public void InterleavedOperationsWithSizeChecks() {
      AddTest(24);
      NewEmptyContainer();
      TestSize(0, false);
      TestPush(1L);
      TestSize(1, false);
      TestPush(2L);
      TestSize(2, false);
      TestPush(3L);
      TestSize(3, false);
      TestPop(false);
      TestSize(2, false);
      TestPush(4L);
      TestSize(3, false);
      TestPop(false);
      TestSize(2, false);
      TestPop(false);
      TestSize(1, false);
      TestPush(5L);
      TestSize(2, false);
      TestPush(6L);
      TestSize(3, false);
      TestTop(6L);
      TestPop(false);
      TestTop(5L);
      TestPop(false);
      TestTop(1L);
    }

    @Test
    @DisplayName("Stack maintains LIFO after many operations")
    public void MaintainsLIFOAfterManyOperations() {
      AddTest(20);
      NewEmptyContainer();
      // Push 1-5
      TestPush(1L);
      TestPush(2L);
      TestPush(3L);
      TestPush(4L);
      TestPush(5L);
      // Pop last two (5,4)
      TestTopNPop(5L, false);
      TestTopNPop(4L, false);
      // Push 6-8
      TestPush(6L);
      TestPush(7L);
      TestPush(8L);
      // Stack should be: 1,2,3,6,7,8 (bottom to top)
      // Pop order: 8,7,6,3,2,1
      TestTop(8L);
      TestTopNPop(8L, false);
      TestTopNPop(7L, false);
      TestTopNPop(6L, false);
      TestTopNPop(3L, false);
      TestTopNPop(2L, false);
      TestTopNPop(1L, false);
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Single element repeated operations")
    public void SingleElementRepeatedOperations() {
      AddTest(18);
      NewEmptyContainer();
      TestPush(42L);
      TestTop(42L);
      TestPop(false);
      TestIsEmpty(true, false);
      TestPush(43L);
      TestTop(43L);
      TestPop(false);
      TestIsEmpty(true, false);
      TestPush(44L);
      TestTop(44L);
      TestSize(1, false);
      TestPush(45L);
      TestSize(2, false);
      TestTopNPop(45L, false);
      TestTopNPop(44L, false);
      TestIsEmpty(true, false);
      TestPop(true);
    }

    @Test
    @DisplayName("Ascending order push")
    public void AscendingOrderPush() {
      AddTest(14);
      NewEmptyContainer();
      TestPush(10L);
      TestPush(20L);
      TestPush(30L);
      TestPush(40L);
      TestPush(50L);
      // LIFO: should come out in reverse order
      TestTopNPop(50L, false);
      TestTopNPop(40L, false);
      TestTopNPop(30L, false);
      TestTopNPop(20L, false);
      TestTopNPop(10L, false);
      TestIsEmpty(true, false);
      TestSize(0, false);
      TestTop(null);
    }

    @Test
    @DisplayName("Mixed positive and negative values")
    public void MixedPositiveNegative() {
      AddTest(14);
      NewEmptyContainer();
      TestPush(-3L);
      TestPush(0L);
      TestPush(3L);
      TestPush(-6L);
      TestPush(6L);
      TestTop(6L);
      TestTopNPop(6L, false);
      TestTopNPop(-6L, false);
      TestTopNPop(3L, false);
      TestTopNPop(0L, false);
      TestTopNPop(-3L, false);
      TestIsEmpty(true, false);
      TestTop(null);
    }

    @Test
    @DisplayName("Partial pop and continue")
    public void PartialPopAndContinue() {
      AddTest(18);
      NewEmptyContainer();
      TestPush(1L);
      TestPush(2L);
      TestPush(3L);
      TestPush(4L);
      TestPush(5L);
      TestPush(6L);
      TestPush(7L);
      TestPush(8L);
      TestPush(9L);
      TestPush(10L);
      TestSize(10, false);
      // Pop top 3
      TestPop(false);
      TestPop(false);
      TestPop(false);
      TestSize(7, false);
      TestTop(7L);
      // Push more
      TestPush(11L);
      TestPush(12L);
      TestSize(9, false);
      // Verify top
      TestTopNPop(12L, false);
    }

    @Test
    @DisplayName("Reverse sequence verification")
    public void ReverseSequenceVerification() {
      AddTest(16);
      NewEmptyContainer();
      // Push in order
      for (long i = 1L; i <= 5L; i++) {
        TestPush(i);
      }
      TestSize(5, false);
      // Pop should give reverse order
      TestTop(5L);
      TestTopNPop(5L, false);
      TestTop(4L);
      TestTopNPop(4L, false);
      TestTop(3L);
      TestTopNPop(3L, false);
      TestTop(2L);
      TestTopNPop(2L, false);
      TestTop(1L);
      TestTopNPop(1L, false);
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Stack as LIFO buffer")
    public void StackAsLIFOBuffer() {
      AddTest(20);
      NewEmptyContainer();
      // Simulate undo operations
      TestPush(100L); // Action 1
      TestPush(200L); // Action 2
      TestPush(300L); // Action 3
      TestTop(300L);
      // Undo action 3
      TestPop(false);
      TestTop(200L);
      // New action 4
      TestPush(400L);
      TestTop(400L);
      // Undo action 4
      TestPop(false);
      TestTop(200L);
      // Undo action 2
      TestPop(false);
      TestTop(100L);
      // Redo: push new actions
      TestPush(500L);
      TestPush(600L);
      TestTop(600L);
      TestSize(3, false);
      // Final check
      TestTopNPop(600L, false);
      TestTopNPop(500L, false);
      TestTopNPop(100L, false);
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Boundary value operations")
    public void BoundaryValueOperations() {
      AddTest(14);
      NewEmptyContainer();
      TestPush(0L);
      TestPush(1L);
      TestPush(-1L);
      TestPush(Long.MAX_VALUE);
      TestPush(Long.MIN_VALUE);
      TestSize(5, false);
      TestTopNPop(Long.MIN_VALUE, false);
      TestTopNPop(Long.MAX_VALUE, false);
      TestTopNPop(-1L, false);
      TestTopNPop(1L, false);
      TestTopNPop(0L, false);
      TestIsEmpty(true, false);
      TestSize(0, false);
      TestTop(null);
    }

  }

  @Nested
  @DisplayName("Stack Construction and Equality Tests")
  public class StackConstructionAndEqualityTests {

    @Test
    @DisplayName("Construction from another container")
    public void ConstructionFromAnotherContainer() {
      AddTest(8);
      LLList<Long> source = new LLList<>();
      source.InsertLast(1L);
      source.InsertLast(2L);
      source.InsertLast(3L);
      source.InsertLast(4L);
      source.InsertLast(5L);
      NewNonEmptyContainer(source);
      TestSize(5, false);
      TestTop(1L);
      TestTopNPop(1L, false);
      TestTop(2L);
      TestTopNPop(2L, false);
      TestTop(3L);
      TestSize(3, false);
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
    @DisplayName("Equality with same elements same order")
    public void EqualitySameOrder() {
      AddTest(5);
      NewEmptyContainer();
      TestPush(3L);
      TestPush(2L);
      TestPush(1L);
      Stack<Long> other = GetNewEmptyContainer();
      other.Push(3L);
      other.Push(2L);
      other.Push(1L);
      TestIsEqual(other, true);
    }

    @Test
    @DisplayName("Inequality with same elements different order")
    public void InequalityDifferentOrder() {
      AddTest(5);
      NewEmptyContainer();
      TestPush(1L);
      TestPush(2L);
      TestPush(3L);
      Stack<Long> other = GetNewEmptyContainer();
      other.Push(3L);
      other.Push(2L);
      other.Push(1L);
      TestIsEqual(other, false);
    }

    @Test
    @DisplayName("Inequality with different sizes")
    public void InequalityDifferentSizes() {
      AddTest(5);
      NewEmptyContainer();
      TestPush(1L);
      TestPush(2L);
      TestPush(3L);
      Stack<Long> other = GetNewEmptyContainer();
      other.Push(1L);
      other.Push(2L);
      TestIsEqual(other, false);
    }

    @Test
    @DisplayName("Inequality with different elements")
    public void InequalityDifferentElements() {
      AddTest(5);
      NewEmptyContainer();
      TestPush(1L);
      TestPush(2L);
      TestPush(3L);
      Stack<Long> other = GetNewEmptyContainer();
      other.Push(1L);
      other.Push(2L);
      other.Push(4L);
      TestIsEqual(other, false);
    }

    @Test
    @DisplayName("Equality of two empty stacks")
    public void EqualityEmptyStacks() {
      AddTest(2);
      NewEmptyContainer();
      Stack<Long> other = GetNewEmptyContainer();
      TestIsEqual(other, true);
    }

    @Test
    @DisplayName("Copy construction preserves elements")
    public void CopyConstructionPreservesElements() {
      AddTest(8);
      NewEmptyContainer();
      TestPush(3L);
      TestPush(2L);
      TestPush(1L);
      Stack<Long> copy = new WStack<>();
      copy.Push(3L);
      copy.Push(2L);
      copy.Push(1L);
      TestSize(3, false);
      TestTop(1L);
      TestSize(3, false);
      TestIsEqual(copy, true);
      TestSize(0, false);
    }

  }

  @Nested
  @DisplayName("Stack Null Testing")
  public class StackNullTesting {

    @Test
    @DisplayName("Push null element")
    public void PushNull() {
      AddTest(4);
      NewEmptyContainer();
      TestPush(null);
      TestSize(0, false);
      TestTop(null);
      TestExists(null, false);
    }

    @Test
    @DisplayName("Insert null element")
    public void InsertNull() {
      AddTest(4);
      NewEmptyContainer();
      TestInsert(null, false);
      TestSize(0, false);
      TestTop(null);
      TestExists(null, false);
    }

    @Test
    @DisplayName("Pop returns null on empty stack")
    public void PopEmptyReturnsNull() {
      AddTest(3);
      NewEmptyContainer();
      TestTop(null);
      TestTopNPop(null, true);
      TestSize(0, false);
    }

    @Test
    @DisplayName("Top returns null on empty stack")
    public void TopEmptyReturnsNull() {
      AddTest(2);
      NewEmptyContainer();
      TestTop(null);
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("IsEqual with null container")
    public void IsEqualNullContainer() {
      AddTest(3);
      NewEmptyContainer();
      TestPush(1L);
      TestIsEqual(null, false);
    }

    @Test
    @DisplayName("Exists with null")
    public void ExistsNull() {
      AddTest(4);
      NewEmptyContainer();
      TestPush(1L);
      TestPush(null);
      TestPush(3L);
      TestExists(null, false);
    }

    @Test
    @DisplayName("Exists null on empty stack")
    public void ExistsNullEmpty() {
      AddTest(2);
      NewEmptyContainer();
      TestExists(null, false);
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Operations with null elements maintain consistency")
    public void NullOperationsConsistency() {
      AddTest(10);
      NewEmptyContainer();
      TestPush(1L);
      TestPush(null);
      TestPush(2L);
      TestSize(2, false);
      TestTop(2L);
      TestTopNPop(2L, false);
      TestTop(1L);
      TestTopNPop(1L, false);
      TestTop(null);
      TestSize(0, false);
    }

    @Test
    @DisplayName("Multiple null elements")
    public void MultipleNullElements() {
      AddTest(9);
      NewEmptyContainer();
      TestPush(null);
      TestPush(null);
      TestPush(null);
      TestSize(0, false);
      TestTop(null);
      TestTopNPop(null, true);
      TestTop(null);
      TestTopNPop(null, true);
      TestTop(null);
    }

    @Test
    @DisplayName("Empty stack null operations")
    public void EmptyStackNullOperations() {
      AddTest(4);
      NewEmptyContainer();
      TestExists(null, false);
      TestTop(null);
      TestTopNPop(null, true);
      TestIsEmpty(true, false);
    }

  }

  @Nested
  @DisplayName("Stack Comprehensive Method Combinations")
  public class StackComprehensiveMethodCombinations {

    @Test
    @DisplayName("Push, Top, Size cycle through all elements")
    public void PushTopSizeCycle() {
      AddTest(20);
      NewEmptyContainer();
      TestPush(10L);
      TestSize(1, false);
      TestTop(10L);
      TestPush(20L);
      TestSize(2, false);
      TestTop(20L);
      TestPush(30L);
      TestSize(3, false);
      TestTop(30L);
      TestPush(40L);
      TestSize(4, false);
      TestTop(40L);
      TestPush(50L);
      TestSize(5, false);
      TestTop(50L);
      TestIsEmpty(false, false);
      TestExists(10L, true);
      TestExists(50L, true);
      TestExists(100L, false);
    }

    @Test
    @DisplayName("TopNPop chain verification")
    public void TopNPopChainVerification() {
      AddTest(18);
      NewEmptyContainer();
      TestPush(1L);
      TestPush(2L);
      TestPush(3L);
      TestPush(4L);
      TestPush(5L);
      TestSize(5, false);
      TestTopNPop(5L, false);
      TestSize(4, false);
      TestTopNPop(4L, false);
      TestSize(3, false);
      TestTopNPop(3L, false);
      TestSize(2, false);
      TestTopNPop(2L, false);
      TestSize(1, false);
      TestTopNPop(1L, false);
      TestSize(0, false);
      TestIsEmpty(true, false);
      TestTopNPop(null, true);
    }

    @Test
    @DisplayName("Pop then verify with Top and Size")
    public void PopWithTopAndSize() {
      AddTest(20);
      NewEmptyContainer();
      TestPush(100L);
      TestPush(200L);
      TestPush(300L);
      TestPush(400L);
      TestPush(500L);
      TestTop(500L);
      TestPop(false);
      TestTop(400L);
      TestSize(4, false);
      TestPop(false);
      TestTop(300L);
      TestSize(3, false);
      TestPop(false);
      TestTop(200L);
      TestSize(2, false);
      TestPop(false);
      TestTop(100L);
      TestSize(1, false);
      TestPop(false);
      TestTop(null);
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Insert and Push combined with Size checks")
    public void InsertAndPushCombined() {
      AddTest(18);
      NewEmptyContainer();
      TestInsert(1L, true);
      TestSize(1, false);
      TestPush(2L);
      TestSize(2, false);
      TestInsert(3L, true);
      TestSize(3, false);
      TestPush(4L);
      TestSize(4, false);
      TestInsert(5L, true);
      TestSize(5, false);
      // Verify LIFO order
      TestTopNPop(5L, false);
      TestTopNPop(4L, false);
      TestTopNPop(3L, false);
      TestTopNPop(2L, false);
      TestTopNPop(1L, false);
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("InsertAll then TopNPop verification")
    public void InsertAllThenTopNPop() {
      AddTest(14);
      NewEmptyContainer();
      LLList<Long> toAdd = new LLList<>();
      toAdd.InsertLast(10L);
      toAdd.InsertLast(20L);
      toAdd.InsertLast(30L);
      toAdd.InsertLast(40L);
      toAdd.InsertLast(50L);
      TestInsertAll(toAdd, true);
      TestSize(5, false);
      TestTop(50L);
      TestTopNPop(50L, false);
      TestTopNPop(40L, false);
      TestTopNPop(30L, false);
      TestTopNPop(20L, false);
      TestTopNPop(10L, false);
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("InsertSome then Pop verification")
    public void InsertSomeThenPop() {
      AddTest(16);
      NewEmptyContainer();
      TestPush(1L);
      TestPush(2L);
      LLList<Long> toAdd = new LLList<>();
      toAdd.InsertLast(3L);
      toAdd.InsertLast(4L);
      toAdd.InsertLast(5L);
      TestInsertSome(toAdd, true);
      TestSize(5, false);
      TestTop(5L);
      TestPop(false);
      TestTop(4L);
      TestPop(false);
      TestTop(3L);
      TestPop(false);
      TestTop(2L);
      TestPop(false);
      TestTop(1L);
      TestSize(1, false);
    }

    @Test
    @DisplayName("Clear then rebuild with Push")
    public void ClearThenRebuildWithPush() {
      AddTest(22);
      NewEmptyContainer();
      TestPush(1L);
      TestPush(2L);
      TestPush(3L);
      TestSize(3, false);
      TestTop(3L);
      TestClear();
      TestIsEmpty(true, false);
      TestSize(0, false);
      TestTop(null);
      TestPush(100L);
      TestPush(200L);
      TestPush(300L);
      TestPush(400L);
      TestSize(4, false);
      TestTop(400L);
      TestTopNPop(400L, false);
      TestTop(300L);
      TestTopNPop(300L, false);
      TestTopNPop(200L, false);
      TestTopNPop(100L, false);
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Alternating Push and TopNPop")
    public void AlternatingPushAndTopNPop() {
      AddTest(24);
      NewEmptyContainer();
      TestPush(1L);
      TestTop(1L);
      TestPush(2L);
      TestTopNPop(2L, false);
      TestTop(1L);
      TestPush(3L);
      TestTopNPop(3L, false);
      TestTop(1L);
      TestPush(4L);
      TestPush(5L);
      TestTopNPop(5L, false);
      TestSize(2, false);
      TestTop(4L);
      TestPush(6L);
      TestSize(3, false);
      TestTopNPop(6L, false);
      TestTopNPop(4L, false);
      TestTopNPop(1L, false);
      TestIsEmpty(true, false);
      TestTop(null);
      TestSize(0, false);
    }

    @Test
    @DisplayName("Multiple Clear and rebuild cycles")
    public void MultipleClearAndRebuildCycles() {
      AddTest(30);
      NewEmptyContainer();
      // First cycle
      TestPush(1L);
      TestPush(2L);
      TestSize(2, false);
      TestClear();
      TestIsEmpty(true, false);
      // Second cycle
      TestPush(10L);
      TestPush(20L);
      TestPush(30L);
      TestSize(3, false);
      TestTop(30L);
      TestClear();
      TestIsEmpty(true, false);
      // Third cycle
      TestPush(100L);
      TestSize(1, false);
      TestTop(100L);
      TestPush(200L);
      TestTopNPop(200L, false);
      TestTopNPop(100L, false);
      TestIsEmpty(true, false);
      // Fourth cycle with InsertAll
      LLList<Long> batch = new LLList<>();
      batch.InsertLast(1000L);
      batch.InsertLast(2000L);
      batch.InsertLast(3000L);
      TestInsertAll(batch, true);
      TestSize(3, false);
      TestTop(3000L);
      TestTopNPop(3000L, false);
      TestSize(2, false);
    }

    @Test
    @DisplayName("Empty stack all operations")
    public void EmptyStackAllOperations() {
      AddTest(12);
      NewEmptyContainer();
      TestIsEmpty(true, false);
      TestSize(0, false);
      TestTop(null);
      TestTopNPop(null, true);
      TestPop(true);
      TestExists(1L, false);
      TestExists(null, false);
      // InsertAll with empty list
      LLList<Long> emptyList = new LLList<>();
      TestInsertAll(emptyList, true);
      TestInsert(1L, true);
      TestIsEmpty(false, false);
      TestClear();
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Single element all operations")
    public void SingleElementAllOperations() {
      AddTest(16);
      NewEmptyContainer();
      TestPush(42L);
      TestIsEmpty(false, false);
      TestSize(1, false);
      TestTop(42L);
      TestExists(42L, true);
      TestExists(0L, false);
      TestTop(42L);
      TestPop(false);
      TestIsEmpty(true, false);
      TestTop(null);
      // Rebuild single element
      TestInsert(99L, true);
      TestSize(1, false);
      TestTop(99L);
      TestTopNPop(99L, false);
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Large stack with mixed operations")
    public void LargeStackMixedOperations() {
      AddTest(35);
      NewEmptyContainer();
      // Push 20 elements
      for (long i = 1L; i <= 20L; i++) {
        TestPush(i);
      }
      TestSize(20, false);
      TestTop(20L);
      // Pop first 10
      for (int i = 0; i < 10; i++) {
        TestPop(false);
      }
      TestSize(10, false);
      TestTop(10L);
      // Add 5 more
      TestPush(21L);
      TestPush(22L);
      TestPush(23L);
      TestPush(24L);
      TestPush(25L);
      TestSize(15, false);
      // Verify order
      TestTopNPop(25L, false);
      TestTopNPop(24L, false);
      TestSize(13, false);
      TestTop(23L);
      // Clear all
      TestClear();
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("LIFO order maintained through all operations")
    public void LIFOOrderMaintained() {
      AddTest(22);
      NewEmptyContainer();
      TestPush(1L);
      TestPush(2L);
      TestPush(3L);
      // Partial pop
      TestTopNPop(3L, false);
      // Add more
      TestPush(4L);
      TestPush(5L);
      // Continue pop
      TestTopNPop(5L, false);
      TestTopNPop(4L, false);
      // Add more
      TestPush(6L);
      // Continue pop
      TestTopNPop(6L, false);
      TestTopNPop(2L, false);
      TestTopNPop(1L, false);
      TestIsEmpty(true, false);
      // Final verification
      TestPush(100L);
      TestPush(200L);
      TestTop(200L);
      TestTopNPop(200L, false);
      TestTop(100L);
      TestSize(1, false);
    }

    @Test
    @DisplayName("Duplicate values in stack operations")
    public void DuplicateValuesOperations() {
      AddTest(18);
      NewEmptyContainer();
      TestPush(5L);
      TestPush(5L);
      TestPush(5L);
      TestPush(10L);
      TestPush(10L);
      TestSize(5, false);
      TestTop(10L);
      TestTopNPop(10L, false);
      TestTop(10L);
      TestTopNPop(10L, false);
      TestTop(5L);
      TestTopNPop(5L, false);
      TestTop(5L);
      TestTopNPop(5L, false);
      TestTop(5L);
      TestTopNPop(5L, false);
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Extreme values through stack operations")
    public void ExtremeValuesOperations() {
      AddTest(16);
      NewEmptyContainer();
      TestPush(Long.MIN_VALUE);
      TestPush(0L);
      TestPush(Long.MAX_VALUE);
      TestSize(3, false);
      TestTop(Long.MAX_VALUE);
      TestExists(Long.MIN_VALUE, true);
      TestExists(Long.MAX_VALUE, true);
      TestTopNPop(Long.MAX_VALUE, false);
      TestTop(0L);
      TestTopNPop(0L, false);
      TestTop(Long.MIN_VALUE);
      TestTopNPop(Long.MIN_VALUE, false);
      TestIsEmpty(true, false);
      TestTop(null);
    }

    @Test
    @DisplayName("InsertAll after partial pop")
    public void InsertAllAfterPartialPop() {
      AddTest(18);
      NewEmptyContainer();
      TestPush(1L);
      TestPush(2L);
      TestPush(3L);
      TestTopNPop(3L, false);
      TestSize(2, false);
      LLList<Long> toAdd = new LLList<>();
      toAdd.InsertLast(4L);
      toAdd.InsertLast(5L);
      toAdd.InsertLast(6L);
      TestInsertAll(toAdd, true);
      TestSize(5, false);
      TestTop(6L);
      TestTopNPop(6L, false);
      TestTopNPop(5L, false);
      TestTopNPop(4L, false);
      TestTopNPop(2L, false);
      TestTopNPop(1L, false);
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Size verification after every operation")
    public void SizeVerificationAfterEveryOperation() {
      AddTest(26);
      NewEmptyContainer();
      TestSize(0, false);
      TestPush(1L);
      TestSize(1, false);
      TestPush(2L);
      TestSize(2, false);
      TestPush(3L);
      TestSize(3, false);
      TestPop(false);
      TestSize(2, false);
      TestPop(false);
      TestSize(1, false);
      TestPush(4L);
      TestSize(2, false);
      TestPush(5L);
      TestSize(3, false);
      TestClear();
      TestSize(0, false);
      TestInsert(10L, true);
      TestSize(1, false);
      TestInsert(20L, true);
      TestSize(2, false);
      TestTopNPop(20L, false);
      TestSize(1, false);
      TestTopNPop(10L, false);
      TestSize(0, false);
    }

    @Test
    @DisplayName("IsEmpty verification through operations")
    public void IsEmptyVerificationThroughOperations() {
      AddTest(18);
      NewEmptyContainer();
      TestIsEmpty(true, false);
      TestPush(1L);
      TestIsEmpty(false, false);
      TestPop(false);
      TestIsEmpty(true, false);
      TestPush(2L);
      TestPush(3L);
      TestIsEmpty(false, false);
      TestClear();
      TestIsEmpty(true, false);
      TestInsert(4L, true);
      TestIsEmpty(false, false);
      TestTopNPop(4L, false);
      TestIsEmpty(true, false);
      LLList<Long> list = new LLList<>();
      list.InsertLast(5L);
      TestInsertAll(list, true);
      TestIsEmpty(false, false);
    }

    @Test
    @DisplayName("Top remains consistent during operations")
    public void TopConsistencyDuringOperations() {
      AddTest(20);
      NewEmptyContainer();
      TestTop(null);
      TestPush(10L);
      TestTop(10L);
      TestPush(20L);
      TestTop(20L);
      TestPush(30L);
      TestTop(30L);
      TestPop(false);
      TestTop(20L);
      TestPush(40L);
      TestTop(40L);
      TestPop(false);
      TestTop(20L);
      TestPop(false);
      TestTop(10L);
      TestPop(false);
      TestTop(null);
      TestPush(50L);
      TestTop(50L);
    }

    @Test
    @DisplayName("Complex interleaved operations")
    public void ComplexInterleavedOperations() {
      AddTest(30);
      NewEmptyContainer();
      // Build up
      TestPush(1L);
      TestPush(2L);
      TestInsert(3L, true);
      TestSize(3, false);
      // Partial drain
      TestTopNPop(3L, false);
      // Add more
      LLList<Long> batch = new LLList<>();
      batch.InsertLast(4L);
      batch.InsertLast(5L);
      TestInsertAll(batch, true);
      TestSize(4, false);
      // Verify order
      TestTop(5L);
      TestTopNPop(5L, false);
      TestTop(4L);
      // Clear and rebuild
      TestClear();
      TestIsEmpty(true, false);
      TestPush(100L);
      TestInsert(200L, true);
      LLList<Long> batch2 = new LLList<>();
      batch2.InsertLast(300L);
      TestInsertSome(batch2, true);
      TestSize(3, false);
      TestTop(300L);
      TestTopNPop(300L, false);
      TestTopNPop(200L, false);
      TestTopNPop(100L, false);
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Rapid push pop with size checks")
    public void RapidPushPopWithSizeChecks() {
      AddTest(24);
      NewEmptyContainer();
      TestPush(1L);
      TestSize(1, false);
      TestPop(false);
      TestSize(0, false);
      TestPush(2L);
      TestSize(1, false);
      TestPush(3L);
      TestSize(2, false);
      TestPop(false);
      TestSize(1, false);
      TestPop(false);
      TestSize(0, false);
      TestIsEmpty(true, false);
      TestPush(4L);
      TestPush(5L);
      TestPush(6L);
      TestSize(3, false);
      TestPop(false);
      TestPop(false);
      TestPop(false);
      TestSize(0, false);
      TestIsEmpty(true, false);
      TestTop(null);
    }

    @Test
    @DisplayName("Stack as LIFO buffer simulation")
    public void StackAsLIFOBufferSimulation() {
      AddTest(22);
      NewEmptyContainer();
      // Simulate undo stack
      TestPush(1L);
      TestPush(2L);
      TestPush(3L);
      TestTop(3L);
      // Undo last action
      TestTopNPop(3L, false);
      TestTop(2L);
      // New action
      TestPush(4L);
      TestTop(4L);
      // Undo
      TestTopNPop(4L, false);
      TestTop(2L);
      // More undos
      TestTopNPop(2L, false);
      TestTop(1L);
      // New actions
      TestPush(5L);
      TestPush(6L);
      TestSize(3, false);
      TestTop(6L);
      TestTopNPop(6L, false);
      TestTopNPop(5L, false);
      TestTopNPop(1L, false);
      TestIsEmpty(true, false);
    }

    @Test
    @DisplayName("Reverse sequence verification")
    public void ReverseSequenceVerification() {
      AddTest(18);
      NewEmptyContainer();
      // Push in order: 1, 2, 3, 4, 5
      TestPush(1L);
      TestPush(2L);
      TestPush(3L);
      TestPush(4L);
      TestPush(5L);
      TestSize(5, false);
      // Pop should give reverse order: 5, 4, 3, 2, 1
      TestTop(5L);
      TestTopNPop(5L, false);
      TestTop(4L);
      TestTopNPop(4L, false);
      TestTop(3L);
      TestTopNPop(3L, false);
      TestTop(2L);
      TestTopNPop(2L, false);
      TestTop(1L);
      TestTopNPop(1L, false);
      TestIsEmpty(true, false);
    }

  }

}
