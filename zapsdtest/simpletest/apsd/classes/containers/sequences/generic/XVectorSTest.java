package zapsdtest.simpletest.apsd.classes.containers.sequences.generic;

import apsd.classes.utilities.Natural;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

abstract public class XVectorSTest extends XVectorTest<String> {

  @Override
  public void NewNonEmptyContainer() {
    AddTest(13);
    NewEmptyContainer();
    TestSetFirst("", true);
    TestRealloc(Natural.Of(1));
    TestSetFirst("", false);
    TestRealloc(Natural.Of(5));
    TestSize(5, false);
    TestSwap(Natural.ZERO, Natural.Of(3), false);
    TestSetFirst("date", false);
    TestSetAt("cherry", Natural.Of(1), false);
    TestSetAt("banana", Natural.Of(2), false);
    TestSetAt("apple", Natural.Of(5), true);
    TestSetLast("apple", false);
    TestSize(5, false);
    TestPrintContent("Initial Static Vector");
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
      TestExists("date", false);
      TestPrintContent("");
      TestFoldBackward((dat, acc) -> acc + dat, "", "");
    }

    @Test
    @DisplayName("Check starting from a NonEmpty Vector")
    public void NonEmpty() {
      AddTest(25);
      NewNonEmptyContainer();
      // Vector state: [date, cherry, banana, null, apple] (size 5)
      TestGetFirst("date", false);
      TestGetLast("apple", false);
      TestSetFirst("elderberry", false);
      TestSetLast("fig", false);
      TestExists("fig", true);
      TestPrintContent("");
      TestFoldForward((dat, acc) -> { if (dat != null) return acc + "," + dat; else return acc; }, "", ",elderberry,cherry,banana,,fig");
      TestFoldForward((dat, acc) -> { if (dat != null) return acc + 1; else return acc; }, 0, 5);
      TestGetAt(Natural.Of(3), "", false);
      TestSetAt("grape", Natural.Of(3), false);
      TestFoldForward((dat, acc) -> dat != null ? acc + "," + dat : acc, "", ",elderberry,cherry,banana,grape,fig");
      TestPrintContent("");
      TestRealloc(Natural.Of(2));
      TestPrintContent("");
      TestFoldForward((dat, acc) -> acc + "," + dat, "", ",elderberry,cherry");
      TestRealloc(Natural.Of(4));
      TestShiftRight(Natural.ONE, Natural.Of(2));
      TestPrintContent("");
      TestShiftLeft(Natural.ZERO, Natural.ONE);
      TestPrintContent("");
      TestSize(4L, false);
      TestShiftRight(Natural.ZERO, Natural.Of(2));
      TestSize(4L, false);
      TestPrintContent("");
      TestClear();
      TestSize(0L, false);
    }

  }

  @Nested
  @DisplayName("Vector Edge Cases")
  public class VectorEdgeCases {

    @Test
    @DisplayName("Single element vector operations")
    public void SingleElement() {
      AddTest(12);
      NewEmptyContainer();
      TestRealloc(Natural.Of(1));
      TestSetFirst("hello", false);
      TestGetFirst("hello", false);
      TestGetLast("hello", false);
      TestGetAt(Natural.ZERO, "hello", false);
      TestExists("hello", true);
      TestExists("world", false);
      TestSwap(Natural.ZERO, Natural.ZERO, false);
      TestGetFirst("hello", false);
      TestFoldForward((dat, acc) -> acc + dat, "", "hello");
      TestFoldBackward((dat, acc) -> acc + dat, "", "hello");
      TestClear();
    }

    @Test
    @DisplayName("Boundary index access")
    public void BoundaryAccess() {
      AddTest(11);
      NewEmptyContainer();
      TestRealloc(Natural.Of(5));
      TestSetAt("alpha", Natural.ZERO, false);
      TestSetAt("omega", Natural.Of(4), false);
      TestGetAt(Natural.ZERO, "alpha", false);
      TestGetAt(Natural.Of(4), "omega", false);
      TestGetAt(Natural.Of(5), "", true);
      TestSetAt("", Natural.Of(5), true);
      TestSwap(Natural.ZERO, Natural.Of(4), false);
      TestGetAt(Natural.ZERO, "omega", false);
      TestGetAt(Natural.Of(4), "alpha", false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Realloc to same size")
    public void ReallocSameSize() {
      AddTest(7);
      NewEmptyContainer();
      TestRealloc(Natural.Of(3));
      TestSetAt("apple", Natural.ZERO, false);
      TestSetAt("banana", Natural.Of(1), false);
      TestSetAt("cherry", Natural.Of(2), false);
      TestRealloc(Natural.Of(3));
      TestFoldForward((dat, acc) -> acc + "," + dat, "", ",apple,banana,cherry");
      TestSize(3, false);
    }

    @Test
    @DisplayName("Realloc to zero")
    public void ReallocToZero() {
      AddTest(4);
      NewNonEmptyContainer();
      TestRealloc(Natural.ZERO);
      TestSize(0, false);
      TestIsEmpty(true, false);
      TestGetFirst("", true);
    }

    @Test
    @DisplayName("Multiple consecutive shifts")
    public void ConsecutiveShifts() {
      AddTest(19);
      NewEmptyContainer();
      TestRealloc(Natural.Of(6));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestSetAt("e", Natural.Of(4), false);
      TestSetAt("f", Natural.Of(5), false);
      TestPrintContent("");
      TestShiftLeft(Natural.ZERO, Natural.Of(2));
      TestPrintContent("");
      TestShiftRight(Natural.Of(2), Natural.Of(2));
      TestPrintContent("");
      TestShiftLeft(Natural.Of(1), Natural.ONE);
      TestPrintContent("");
      TestSize(6, false);
      TestFoldForward((dat, acc) -> { if (dat != null) return acc + 1; else return acc; }, 0, 3);
      TestShiftRight(Natural.Of(3), Natural.Of(2));
      TestFoldForward((dat, acc) -> { if (dat != null) return acc + 1; else return acc; }, 0, 2);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Shift at boundaries")
    public void ShiftBoundaries() {
      AddTest(10);
      NewEmptyContainer();
      TestRealloc(Natural.Of(4));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestShiftLeft(Natural.ZERO, Natural.ONE);
      TestPrintContent("");
      TestShiftRight(Natural.Of(3), Natural.ONE);
      TestPrintContent("");
      TestSize(4, false);
    }

    @Test
    @DisplayName("Swap same position")
    public void SwapSamePosition() {
      AddTest(6);
      NewEmptyContainer();
      TestRealloc(Natural.Of(3));
      TestSetAt("hello", Natural.Of(1), false);
      TestSwap(Natural.ONE, Natural.ONE, false);
      TestGetAt(Natural.Of(1), "hello", false);
      TestPrintContent("");
      TestSize(3, false);
    }

    @Test
    @DisplayName("Fold with identity operations")
    public void FoldIdentity() {
      AddTest(9);
      NewEmptyContainer();
      TestRealloc(Natural.Of(4));
      TestSetAt("apple", Natural.ZERO, false);
      TestSetAt("banana", Natural.Of(1), false);
      TestSetAt("cherry", Natural.Of(2), false);
      TestSetAt("date", Natural.Of(3), false);
      TestFoldForward((dat, acc) -> acc, "identity", "identity");
      TestFoldBackward((dat, acc) -> acc, "sentinel", "sentinel");
      TestFoldForward((dat, acc) -> dat, "", "date");
      TestFoldBackward((dat, acc) -> dat, "", "apple");
    }

    @Test
    @DisplayName("ShiftRight clamps num > remaining (tail becomes nulls)")
    public void ShiftRightClampsLargeNum() {
      AddTest(8);
      NewEmptyContainer();
      TestRealloc(Natural.Of(5));
      ThisContainer().SetAt("a", Natural.ZERO);
      ThisContainer().SetAt("b", Natural.Of(1));
      ThisContainer().SetAt("c", Natural.Of(2));
      ThisContainer().SetAt("d", Natural.Of(3));
      ThisContainer().SetAt("e", Natural.Of(4));

      assertDoesNotThrow(() -> ThisContainer().ShiftRight(Natural.ONE, Natural.Of(5)));
      assertEquals("a", ThisContainer().GetAt(Natural.ZERO));
      assertNull(ThisContainer().GetAt(Natural.Of(1)));
      assertNull(ThisContainer().GetAt(Natural.Of(2)));
      assertNull(ThisContainer().GetAt(Natural.Of(3)));
      assertNull(ThisContainer().GetAt(Natural.Of(4)));

      ThisContainer().SetAt("a", Natural.ZERO);
      ThisContainer().SetAt("b", Natural.Of(1));
      ThisContainer().SetAt("c", Natural.Of(2));
      ThisContainer().SetAt("d", Natural.Of(3));
      ThisContainer().SetAt("e", Natural.Of(4));
      assertDoesNotThrow(() -> ThisContainer().ShiftRight(Natural.ZERO, Natural.Of(5)));
      assertNull(ThisContainer().GetAt(Natural.ZERO));
      assertNull(ThisContainer().GetAt(Natural.Of(1)));
      assertNull(ThisContainer().GetAt(Natural.Of(2)));
      assertNull(ThisContainer().GetAt(Natural.Of(3)));
      assertNull(ThisContainer().GetAt(Natural.Of(4)));
      TestSize(5, false);
    }

    @Test
    @DisplayName("ShiftLeft clamps num > size (everything becomes null)")
    public void ShiftLeftClampsLargeNum() {
      AddTest(7);
      NewEmptyContainer();
      TestRealloc(Natural.Of(5));
      ThisContainer().SetAt("a", Natural.ZERO);
      ThisContainer().SetAt("b", Natural.Of(1));
      ThisContainer().SetAt("c", Natural.Of(2));
      ThisContainer().SetAt("d", Natural.Of(3));
      ThisContainer().SetAt("e", Natural.Of(4));

      assertDoesNotThrow(() -> ThisContainer().ShiftLeft(Natural.ZERO, Natural.Of(5)));
      for (long i = 0; i < 5; i++) assertNull(ThisContainer().GetAt(Natural.Of(i)));


      ThisContainer().SetAt("a", Natural.ZERO);
      ThisContainer().SetAt("b", Natural.Of(1));
      ThisContainer().SetAt("c", Natural.Of(2));
      ThisContainer().SetAt("d", Natural.Of(3));
      ThisContainer().SetAt("e", Natural.Of(4));

      assertDoesNotThrow(() -> ThisContainer().ShiftLeft(Natural.ONE, Natural.Of(5)));
      assertEquals("a", ThisContainer().GetAt(Natural.ZERO));
      for (long i = 1; i < 5; i++) assertNull(ThisContainer().GetAt(Natural.Of(i)));
      
      TestSize(5, false);
    }

    @Test
    @DisplayName("Shift with num=0 is a no-op")
    public void ShiftZeroIsNoOp() {
      AddTest(6);
      NewEmptyContainer();
      TestRealloc(Natural.Of(3));
      ThisContainer().SetAt("x", Natural.ZERO);
      ThisContainer().SetAt("y", Natural.Of(1));
      ThisContainer().SetAt("z", Natural.Of(2));

      assertDoesNotThrow(() -> ThisContainer().ShiftRight(Natural.ONE, Natural.ZERO));
      assertEquals("x", ThisContainer().GetAt(Natural.ZERO));
      assertEquals("y", ThisContainer().GetAt(Natural.Of(1)));
      assertEquals("z", ThisContainer().GetAt(Natural.Of(2)));

      assertDoesNotThrow(() -> ThisContainer().ShiftLeft(Natural.ONE, Natural.ZERO));
      assertEquals("x", ThisContainer().GetAt(Natural.ZERO));
      assertEquals("y", ThisContainer().GetAt(Natural.Of(1)));
      assertEquals("z", ThisContainer().GetAt(Natural.Of(2)));
    }

    @Test
    @DisplayName("Shift out of bounds throws")
    public void ShiftOutOfBoundsThrows() {
      AddTest(2);
      NewEmptyContainer();
      TestRealloc(Natural.Of(2));
      assertThrows(RuntimeException.class, () -> ThisContainer().ShiftLeft(Natural.Of(2), Natural.ONE));
      assertThrows(RuntimeException.class, () -> ThisContainer().ShiftRight(Natural.Of(2), Natural.ONE));
    }
  }

  @Nested
  @DisplayName("Vector ShiftFirst and ShiftLast Tests")
  public class VectorShiftFirstLastTests {

    @Test
    @DisplayName("ShiftFirstLeft on single element")
    public void ShiftFirstLeftSingleElement() {
      AddTest(4);
      NewEmptyContainer();
      TestRealloc(Natural.Of(1));
      TestSetFirst("only", false);
      TestShiftFirstLeft();
      TestGetFirst(null, false);
    }

    @Test
    @DisplayName("ShiftFirstLeft on multiple elements")
    public void ShiftFirstLeftMultipleElements() {
      AddTest(8);
      NewEmptyContainer();
      TestRealloc(Natural.Of(4));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestShiftFirstLeft();
      // After ShiftFirstLeft: [b, c, d, null]
      TestGetFirst("b", false);
      TestGetAt(Natural.Of(2), "d", false);
      TestSize(4, false);
    }

    @Test
    @DisplayName("ShiftFirstRight on single element")
    public void ShiftFirstRightSingleElement() {
      AddTest(4);
      NewEmptyContainer();
      TestRealloc(Natural.Of(1));
      TestSetFirst("only", false);
      TestShiftFirstRight();
      TestGetFirst(null, false);
    }

    @Test
    @DisplayName("ShiftFirstRight on multiple elements")
    public void ShiftFirstRightMultipleElements() {
      AddTest(9);
      NewEmptyContainer();
      TestRealloc(Natural.Of(4));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestShiftFirstRight();
      // After ShiftFirstRight: [null, a, b, c]
      TestGetFirst(null, false);
      TestGetAt(Natural.Of(1), "a", false);
      TestGetAt(Natural.Of(3), "c", false);
      TestSize(4, false);
    }

    @Test
    @DisplayName("ShiftLastLeft on single element")
    public void ShiftLastLeftSingleElement() {
      AddTest(4);
      NewEmptyContainer();
      TestRealloc(Natural.Of(1));
      TestSetFirst("only", false);
      TestShiftLastLeft();
      TestGetFirst(null, false);
    }

    @Test
    @DisplayName("ShiftLastLeft on multiple elements")
    public void ShiftLastLeftMultipleElements() {
      AddTest(7);
      NewEmptyContainer();
      TestRealloc(Natural.Of(4));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestShiftLastLeft();
      // After: [a, b, c, null]
      TestGetAt(Natural.Of(2), "c", false);
      TestSize(4, false);
    }

    @Test
    @DisplayName("ShiftLastRight on single element")
    public void ShiftLastRightSingleElement() {
      AddTest(4);
      NewEmptyContainer();
      TestRealloc(Natural.Of(1));
      TestSetFirst("only", false);
      TestShiftLastRight();
      TestGetFirst(null, false);
    }

    @Test
    @DisplayName("ShiftLastRight on multiple elements")
    public void ShiftLastRightMultipleElements() {
      AddTest(7);
      NewEmptyContainer();
      TestRealloc(Natural.Of(4));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestShiftLastRight();
      TestGetAt(Natural.Of(2), "c", false);
      TestSize(4, false);
    }

    @Test
    @DisplayName("ShiftFirstLeft and ShiftFirstRight on empty vector throws")
    public void ShiftFirstOnEmptyThrows() {
      AddTest(2);
      NewEmptyContainer();
      assertThrows(RuntimeException.class, () -> ThisContainer().ShiftFirstLeft());
      assertThrows(RuntimeException.class, () -> ThisContainer().ShiftFirstRight());
    }

    @Test
    @DisplayName("ShiftLastLeft and ShiftLastRight on empty vector throws")
    public void ShiftLastOnEmptyThrows() {
      AddTest(2);
      NewEmptyContainer();
      assertThrows(RuntimeException.class, () -> ThisContainer().ShiftLastLeft());
      assertThrows(RuntimeException.class, () -> ThisContainer().ShiftLastRight());
    }

    @Test
    @DisplayName("Consecutive ShiftFirstLeft clears all data")
    public void ConsecutiveShiftFirstLeftClearsData() {
      AddTest(9);
      NewEmptyContainer();
      TestRealloc(Natural.Of(3));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestShiftFirstLeft();
      TestShiftFirstLeft();
      TestShiftFirstLeft();
      TestFoldForward((dat, acc) -> { if (dat != null) return acc + 1; else return acc; }, 0, 0);
      TestSize(3, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Consecutive ShiftFirstRight clears all data")
    public void ConsecutiveShiftFirstRightClearsData() {
      AddTest(9);
      NewEmptyContainer();
      TestRealloc(Natural.Of(3));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestShiftFirstRight();
      TestShiftFirstRight();
      TestShiftFirstRight();
      TestFoldForward((dat, acc) -> { if (dat != null) return acc + 1; else return acc; }, 0, 0);
      TestSize(3, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Alternating ShiftFirstLeft and ShiftLastRight")
    public void AlternatingShiftFirstLeftShiftLastRight() {
      AddTest(10);
      NewEmptyContainer();
      TestRealloc(Natural.Of(5));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestSetAt("e", Natural.Of(4), false);
      TestShiftFirstLeft();
      // [b, c, d, e, null]
      TestShiftLastRight();
      // [b, c, d, e, null] -> last is already null, ShiftLastRight nullifies last
      TestShiftFirstLeft();
      // [c, d, e, null, null]
      TestGetFirst("c", false);
      TestFoldForward((dat, acc) -> { if (dat != null) return acc + "," + dat; else return acc; }, "", ",c,d,e");
      TestSize(5, false);
    }

    @Test
    @DisplayName("Alternating ShiftFirstRight and ShiftLastLeft")
    public void AlternatingShiftFirstRightShiftLastLeft() {
      AddTest(10);
      NewEmptyContainer();
      TestRealloc(Natural.Of(5));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestSetAt("e", Natural.Of(4), false);
      TestShiftFirstRight();
      // [null, a, b, c, d]
      TestShiftLastLeft();
      // [null, a, b, c, null]
      TestShiftFirstRight();
      // [null, null, a, b, c]
      TestGetAt(Natural.Of(2), "a", false);
      TestGetAt(Natural.Of(4), "c", false);
      TestSize(5, false);
    }

    @Test
    @DisplayName("ShiftLeft with position 1 on two-element vector")
    public void ShiftLeftPos1TwoElements() {
      AddTest(5);
      NewEmptyContainer();
      TestRealloc(Natural.Of(2));
      TestSetAt("x", Natural.ZERO, false);
      TestSetAt("y", Natural.Of(1), false);
      TestShiftLeft(Natural.ONE);
      // ShiftLeft at pos 1 by 1: [x, null]
      TestGetFirst("x", false);
      TestGetAt(Natural.Of(1), null, false);
    }

    @Test
    @DisplayName("ShiftRight with position 0 on two-element vector")
    public void ShiftRightPos0TwoElements() {
      AddTest(5);
      NewEmptyContainer();
      TestRealloc(Natural.Of(2));
      TestSetAt("x", Natural.ZERO, false);
      TestSetAt("y", Natural.Of(1), false);
      TestShiftRight(Natural.ZERO);
      // ShiftRight at pos 0 by 1: [null, x]
      TestGetFirst(null, false);
      TestGetAt(Natural.Of(1), "x", false);
    }
  }

  @Nested
  @DisplayName("Vector GetNSet Tests")
  public class VectorGetNSetTests {

    @Test
    @DisplayName("GetNSetFirst basic operation")
    public void GetNSetFirstBasic() {
      AddTest(6);
      NewEmptyContainer();
      TestRealloc(Natural.Of(3));
      TestSetAt("apple", Natural.ZERO, false);
      TestSetAt("banana", Natural.Of(1), false);
      TestSetAt("cherry", Natural.Of(2), false);
      TestGetNSetFirst("date", "apple", false);
      TestGetFirst("date", false);
      TestFoldForward((dat, acc) -> acc + "," + dat, "", ",date,banana,cherry");
    }

    @Test
    @DisplayName("GetNSetLast basic operation")
    public void GetNSetLastBasic() {
      AddTest(6);
      NewEmptyContainer();
      TestRealloc(Natural.Of(3));
      TestSetAt("apple", Natural.ZERO, false);
      TestSetAt("banana", Natural.Of(1), false);
      TestSetAt("cherry", Natural.Of(2), false);
      TestGetNSetLast("date", "cherry", false);
      TestGetLast("date", false);
      TestFoldForward((dat, acc) -> acc + "," + dat, "", ",apple,banana,date");
    }

    @Test
    @DisplayName("GetNSetAt basic operation")
    public void GetNSetAtBasic() {
      AddTest(7);
      NewEmptyContainer();
      TestRealloc(Natural.Of(4));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestGetNSetAt("X", "b", Natural.Of(1), false);
      TestGetAt(Natural.Of(1), "X", false);
      TestFoldForward((dat, acc) -> acc + "," + dat, "", ",a,X,c,d");
    }

    @Test
    @DisplayName("GetNSetFirst on empty vector throws")
    public void GetNSetFirstEmptyThrows() {
      AddTest(1);
      NewEmptyContainer();
      TestGetNSetFirst("x", "", true);
    }

    @Test
    @DisplayName("GetNSetLast on empty vector throws")
    public void GetNSetLastEmptyThrows() {
      AddTest(1);
      NewEmptyContainer();
      TestGetNSetLast("x", "", true);
    }

    @Test
    @DisplayName("GetNSetAt out of bounds throws")
    public void GetNSetAtOutOfBoundsThrows() {
      AddTest(2);
      NewEmptyContainer();
      TestRealloc(Natural.Of(2));
      TestGetNSetAt("x", "", Natural.Of(5), true);
      TestGetNSetAt("x", "", Natural.Of(2), true);
    }

    @Test
    @DisplayName("GetNSetFirst chain replaces values correctly")
    public void GetNSetFirstChain() {
      AddTest(8);
      NewEmptyContainer();
      TestRealloc(Natural.Of(3));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestGetNSetFirst("X", "a", false);
      TestGetNSetFirst("Y", "X", false);
      TestGetNSetFirst("Z", "Y", false);
      TestGetFirst("Z", false);
      TestFoldForward((dat, acc) -> acc + "," + dat, "", ",Z,b,c");
    }

    @Test
    @DisplayName("GetNSetLast chain replaces values correctly")
    public void GetNSetLastChain() {
      AddTest(8);
      NewEmptyContainer();
      TestRealloc(Natural.Of(3));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestGetNSetLast("X", "c", false);
      TestGetNSetLast("Y", "X", false);
      TestGetNSetLast("Z", "Y", false);
      TestGetLast("Z", false);
      TestFoldForward((dat, acc) -> acc + "," + dat, "", ",a,b,Z");
    }

    @Test
    @DisplayName("GetNSetAt on single element vector")
    public void GetNSetAtSingleElement() {
      AddTest(5);
      NewEmptyContainer();
      TestRealloc(Natural.Of(1));
      TestSetFirst("hello", false);
      TestGetNSetAt("world", "hello", Natural.ZERO, false);
      TestGetFirst("world", false);
      TestGetNSetFirst("foo", "world", false);
      TestGetNSetLast("bar", "foo", false);
    }

    @Test
    @DisplayName("GetNSetAt replaces null with value")
    public void GetNSetAtReplacesNull() {
      AddTest(5);
      NewEmptyContainer();
      TestRealloc(Natural.Of(3));
      // All positions are null initially
      TestGetNSetAt("hello", null, Natural.Of(1), false);
      TestGetAt(Natural.Of(1), "hello", false);
      TestGetNSetAt("world", "hello", Natural.Of(1), false);
      TestGetAt(Natural.Of(1), "world", false);
      TestSize(3, false);
    }

    @Test
    @DisplayName("GetNSetFirst and GetNSetLast on same single-element vector")
    public void GetNSetFirstLastSameElement() {
      AddTest(6);
      NewEmptyContainer();
      TestRealloc(Natural.Of(1));
      TestSetFirst("orig", false);
      TestGetNSetFirst("new1", "orig", false);
      TestGetNSetLast("new2", "new1", false);
      TestGetFirst("new2", false);
      TestGetLast("new2", false);
      TestSize(1, false);
    }

    @Test
    @DisplayName("GetNSetAt on every position of a full vector")
    public void GetNSetAtEveryPosition() {
      AddTest(10);
      NewEmptyContainer();
      TestRealloc(Natural.Of(4));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestGetNSetAt("W", "a", Natural.ZERO, false);
      TestGetNSetAt("X", "b", Natural.Of(1), false);
      TestGetNSetAt("Y", "c", Natural.Of(2), false);
      TestGetNSetAt("Z", "d", Natural.Of(3), false);
      TestFoldForward((dat, acc) -> acc + "," + dat, "", ",W,X,Y,Z");
      TestFoldBackward((dat, acc) -> acc + "," + dat, "", ",Z,Y,X,W");
    }
  }

  @Nested
  @DisplayName("Vector Swap Advanced Tests")
  public class VectorSwapAdvancedTests {

    @Test
    @DisplayName("Swap first and last")
    public void SwapFirstAndLast() {
      AddTest(8);
      NewEmptyContainer();
      TestRealloc(Natural.Of(5));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestSetAt("e", Natural.Of(4), false);
      TestSwap(Natural.ZERO, Natural.Of(4), false);
      TestGetFirst("e", false);
      TestGetLast("a", false);
    }

    @Test
    @DisplayName("Swap adjacent elements")
    public void SwapAdjacent() {
      AddTest(8);
      NewEmptyContainer();
      TestRealloc(Natural.Of(4));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestSwap(Natural.ZERO, Natural.ONE, false);
      TestGetAt(Natural.ZERO, "b", false);
      TestGetAt(Natural.Of(1), "a", false);
      TestFoldForward((dat, acc) -> acc + "," + dat, "", ",b,a,c,d");
    }

    @Test
    @DisplayName("Swap with null elements")
    public void SwapWithNulls() {
      AddTest(7);
      NewEmptyContainer();
      TestRealloc(Natural.Of(3));
      TestSetAt("hello", Natural.ZERO, false);
      // positions 1, 2 are null
      TestSwap(Natural.ZERO, Natural.Of(2), false);
      TestGetAt(Natural.ZERO, null, false);
      TestGetAt(Natural.Of(2), "hello", false);
      TestSwap(Natural.ZERO, Natural.Of(2), false);
      TestGetAt(Natural.ZERO, "hello", false);
    }

    @Test
    @DisplayName("Multiple swaps restore original")
    public void MultipleSwapsRestore() {
      AddTest(10);
      NewEmptyContainer();
      TestRealloc(Natural.Of(3));
      TestSetAt("x", Natural.ZERO, false);
      TestSetAt("y", Natural.Of(1), false);
      TestSetAt("z", Natural.Of(2), false);
      TestSwap(Natural.ZERO, Natural.Of(2), false);
      TestGetFirst("z", false);
      TestGetLast("x", false);
      TestSwap(Natural.ZERO, Natural.Of(2), false);
      TestGetFirst("x", false);
      TestGetLast("z", false);
      TestFoldForward((dat, acc) -> acc + "," + dat, "", ",x,y,z");
      TestFoldBackward((dat, acc) -> acc + "," + dat, "", ",z,y,x");
    }

    @Test
    @DisplayName("Swap out of bounds throws")
    public void SwapOutOfBoundsThrows() {
      AddTest(3);
      NewEmptyContainer();
      TestRealloc(Natural.Of(3));
      TestSwap(Natural.ZERO, Natural.Of(5), true);
      TestSwap(Natural.Of(5), Natural.ZERO, true);
      TestSwap(Natural.Of(10), Natural.Of(20), true);
    }

    @Test
    @DisplayName("Swap on two-element vector - reverse order")
    public void SwapTwoElements() {
      AddTest(6);
      NewEmptyContainer();
      TestRealloc(Natural.Of(2));
      TestSetAt("first", Natural.ZERO, false);
      TestSetAt("second", Natural.Of(1), false);
      TestSwap(Natural.ZERO, Natural.Of(1), false);
      TestGetFirst("second", false);
      TestGetLast("first", false);
      TestFoldForward((dat, acc) -> acc + "," + dat, "", ",second,first");
    }

    @Test
    @DisplayName("Bubble sort via swaps then verify order")
    public void BubbleSortViaSwaps() {
      AddTest(13);
      NewEmptyContainer();
      TestRealloc(Natural.Of(4));
      TestSetAt("d", Natural.ZERO, false);
      TestSetAt("c", Natural.Of(1), false);
      TestSetAt("b", Natural.Of(2), false);
      TestSetAt("a", Natural.Of(3), false);
      // Bubble sort: pass 1
      TestSwap(Natural.ZERO, Natural.ONE, false);  // c,d,b,a
      TestSwap(Natural.ONE, Natural.Of(2), false);  // c,b,d,a
      TestSwap(Natural.Of(2), Natural.Of(3), false); // c,b,a,d
      // pass 2
      TestSwap(Natural.ZERO, Natural.ONE, false);  // b,c,a,d
      TestSwap(Natural.ONE, Natural.Of(2), false);  // b,a,c,d
      // pass 3
      TestSwap(Natural.ZERO, Natural.ONE, false);  // a,b,c,d
      TestGetFirst("a", false);
      TestGetLast("d", false);
      TestFoldForward((dat, acc) -> acc + "," + dat, "", ",a,b,c,d");
    }

    @Test
    @DisplayName("Swap both null positions is no-op")
    public void SwapBothNulls() {
      AddTest(5);
      NewEmptyContainer();
      TestRealloc(Natural.Of(4));
      // All null
      TestSwap(Natural.ZERO, Natural.Of(3), false);
      TestGetAt(Natural.ZERO, null, false);
      TestGetAt(Natural.Of(3), null, false);
      TestFoldForward((dat, acc) -> { if (dat != null) return acc + 1; else return acc; }, 0, 0);
      TestSize(4, false);
    }
  }

  @Nested
  @DisplayName("Vector Traverse and Fold Advanced Tests")
  public class VectorTraverseFoldAdvancedTests {

    @Test
    @DisplayName("TraverseForward and TraverseBackward basic")
    public void TraverseForwardBackwardBasic() {
      AddTest(6);
      NewEmptyContainer();
      TestRealloc(Natural.Of(4));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestTraverseForward(dat -> dat.equals("z"), false);
      TestTraverseBackward(dat -> dat.equals("a"), true);
    }

    @Test
    @DisplayName("FoldForward concatenation with nulls")
    public void FoldForwardConcatWithNulls() {
      AddTest(7);
      NewEmptyContainer();
      TestRealloc(Natural.Of(5));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("e", Natural.Of(4), false);
      // Positions 1, 3 are null
      TestFoldForward((dat, acc) -> { if (dat != null) return acc + dat; else return acc; }, "", "ace");
      TestFoldBackward((dat, acc) -> { if (dat != null) return acc + dat; else return acc; }, "", "eca");
      TestFoldForward((dat, acc) -> { if (dat != null) return acc + 1; else return acc; }, 0, 3);
      TestSize(5, false);
    }

    @Test
    @DisplayName("FoldBackward builds reversed string")
    public void FoldBackwardReversed() {
      AddTest(5);
      NewEmptyContainer();
      TestRealloc(Natural.Of(4));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestFoldBackward((dat, acc) -> acc + dat, "", "dcba");
    }

    @Test
    @DisplayName("Fold on empty vector returns initial value")
    public void FoldEmptyReturnsInitial() {
      AddTest(2);
      NewEmptyContainer();
      TestFoldForward((dat, acc) -> acc + dat, "init", "init");
      TestFoldBackward((dat, acc) -> acc + dat, "init", "init");
    }

    @Test
    @DisplayName("Fold on single null element vector")
    public void FoldOnSingleNull() {
      AddTest(3);
      NewEmptyContainer();
      TestRealloc(Natural.Of(1));
      TestFoldForward((dat, acc) -> { if (dat != null) return acc + dat; else return acc; }, "", "");
      TestFoldBackward((dat, acc) -> { if (dat != null) return acc + dat; else return acc; }, "", "");
      TestSize(1, false);
    }

    @Test
    @DisplayName("FoldForward find longest string")
    public void FoldForwardLongest() {
      AddTest(6);
      NewEmptyContainer();
      TestRealloc(Natural.Of(5));
      TestSetAt("hi", Natural.ZERO, false);
      TestSetAt("hello", Natural.Of(1), false);
      TestSetAt("hey", Natural.Of(2), false);
      TestSetAt("greetings", Natural.Of(3), false);
      TestSetAt("yo", Natural.Of(4), false);
      TestFoldForward((dat, acc) -> dat.length() > acc.length() ? dat : acc, "", "greetings");
    }

    @Test
    @DisplayName("FoldBackward find shortest string")
    public void FoldBackwardShortest() {
      AddTest(6);
      NewEmptyContainer();
      TestRealloc(Natural.Of(5));
      TestSetAt("hello", Natural.ZERO, false);
      TestSetAt("hi", Natural.Of(1), false);
      TestSetAt("hey", Natural.Of(2), false);
      TestSetAt("greetings", Natural.Of(3), false);
      TestSetAt("a", Natural.Of(4), false);
      TestFoldBackward((dat, acc) -> dat.length() < acc.length() ? dat : acc, "placeholder", "a");
    }

    @Test
    @DisplayName("FoldForward counts non-null elements")
    public void FoldForwardCountNonNull() {
      AddTest(7);
      NewEmptyContainer();
      TestRealloc(Natural.Of(6));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(2), false);
      TestSetAt("c", Natural.Of(4), false);
      // Positions 1, 3, 5 are null
      TestFoldForward((dat, acc) -> { if (dat != null) return acc + 1; else return acc; }, 0, 3);
      TestFoldBackward((dat, acc) -> { if (dat != null) return acc + 1; else return acc; }, 0, 3);
      TestFoldForward((dat, acc) -> { if (dat != null) return acc + dat + "|"; else return acc; }, "", "a|b|c|");
      TestSize(6, false);
    }

    @Test
    @DisplayName("TraverseForward stops early on match")
    public void TraverseForwardStopsEarly() {
      AddTest(5);
      NewEmptyContainer();
      TestRealloc(Natural.Of(5));
      TestSetAt("alpha", Natural.ZERO, false);
      TestSetAt("beta", Natural.Of(1), false);
      TestSetAt("gamma", Natural.Of(2), false);
      TestSetAt("delta", Natural.Of(3), false);
      TestSetAt("epsilon", Natural.Of(4), false);
      TestTraverseForward(dat -> dat.equals("gamma"), true);
    }

    @Test
    @DisplayName("TraverseBackward stops early on match")
    public void TraverseBackwardStopsEarly() {
      AddTest(5);
      NewEmptyContainer();
      TestRealloc(Natural.Of(5));
      TestSetAt("alpha", Natural.ZERO, false);
      TestSetAt("beta", Natural.Of(1), false);
      TestSetAt("gamma", Natural.Of(2), false);
      TestSetAt("delta", Natural.Of(3), false);
      TestSetAt("epsilon", Natural.Of(4), false);
      TestTraverseBackward(dat -> dat.equals("gamma"), true);
    }

    @Test
    @DisplayName("FoldForward with empty string accumulator")
    public void FoldForwardEmptyAccumulator() {
      AddTest(5);
      NewEmptyContainer();
      TestRealloc(Natural.Of(3));
      TestSetAt("hello", Natural.ZERO, false);
      TestSetAt(" ", Natural.Of(1), false);
      TestSetAt("world", Natural.Of(2), false);
      TestFoldForward((dat, acc) -> acc + dat, "", "hello world");
      TestFoldBackward((dat, acc) -> acc + dat, "", "world hello");
    }
  }

  @Nested
  @DisplayName("Vector Search and Exists Tests")
  public class VectorSearchExistsTests {

    @Test
    @DisplayName("Search element present at various positions")
    public void SearchAtVariousPositions() {
      AddTest(8);
      NewEmptyContainer();
      TestRealloc(Natural.Of(5));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestSetAt("e", Natural.Of(4), false);
      TestExists("a", true);
      TestExists("c", true);
      TestExists("e", true);
    }

    @Test
    @DisplayName("Search element not present")
    public void SearchNotPresent() {
      AddTest(5);
      NewEmptyContainer();
      TestRealloc(Natural.Of(3));
      TestSetAt("apple", Natural.ZERO, false);
      TestSetAt("banana", Natural.Of(1), false);
      TestSetAt("cherry", Natural.Of(2), false);
      TestExists("date", false);
      TestExists("fig", false);
    }

    @Test
    @DisplayName("Search on empty vector")
    public void SearchOnEmpty() {
      AddTest(2);
      NewEmptyContainer();
      TestExists("anything", false);
      TestExists(null, false);
    }

    @Test
    @DisplayName("Exists with duplicate values")
    public void ExistsWithDuplicates() {
      AddTest(5);
      NewEmptyContainer();
      TestRealloc(Natural.Of(4));
      TestSetAt("same", Natural.ZERO, false);
      TestSetAt("same", Natural.Of(1), false);
      TestSetAt("same", Natural.Of(2), false);
      TestSetAt("same", Natural.Of(3), false);
      TestExists("same", true);
    }

    @Test
    @DisplayName("Exists with empty string")
    public void ExistsEmptyString() {
      AddTest(4);
      NewEmptyContainer();
      TestRealloc(Natural.Of(3));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("", Natural.Of(1), false);
      TestSetAt("b", Natural.Of(2), false);
      TestExists("", true);
    }

    @Test
    @DisplayName("Exists after shift makes element disappear")
    public void ExistsAfterShiftDisappears() {
      AddTest(7);
      NewEmptyContainer();
      TestRealloc(Natural.Of(3));
      TestSetAt("unique", Natural.ZERO, false);
      TestSetAt("target", Natural.Of(1), false);
      TestSetAt("other", Natural.Of(2), false);
      TestExists("target", true);
      TestShiftLeft(Natural.Of(1), Natural.ONE);
      // After: [unique, other, null]
      TestExists("target", false);
      TestExists("other", true);
    }
  }

  @Nested
  @DisplayName("Vector SubVector Tests")
  public class VectorSubVectorTests {

    @Test
    @DisplayName("SubVector full range")
    public void SubVectorFullRange() {
      AddTest(3);
      NewEmptyContainer();
      TestRealloc(Natural.Of(4));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSubVector(Natural.ZERO, Natural.Of(3), false);
    }

    @Test
    @DisplayName("SubVector single element")
    public void SubVectorSingleElement() {
      AddTest(3);
      NewEmptyContainer();
      TestRealloc(Natural.Of(4));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSubVector(Natural.ONE, Natural.ONE, false);
    }

    @Test
    @DisplayName("SubVector out of bounds returns null")
    public void SubVectorOutOfBounds() {
      AddTest(3);
      NewEmptyContainer();
      TestRealloc(Natural.Of(3));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSubVector(Natural.ZERO, Natural.Of(5), true);
    }

    @Test
    @DisplayName("SubVector reversed range returns null")
    public void SubVectorReversedRange() {
      AddTest(3);
      NewEmptyContainer();
      TestRealloc(Natural.Of(4));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSubVector(Natural.Of(3), Natural.ZERO, true);
    }
  }

  @Nested
  @DisplayName("Vector Combined Operations Tests")
  public class VectorCombinedOperationsTests {

    @Test
    @DisplayName("SetFirst, SetLast then ShiftLeft and verify fold")
    public void SetFirstLastThenShiftLeft() {
      AddTest(11);
      NewEmptyContainer();
      TestRealloc(Natural.Of(5));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestSetAt("e", Natural.Of(4), false);
      TestSetFirst("X", false);
      TestSetLast("Z", false);
      TestShiftLeft(Natural.ZERO, Natural.Of(2));
      // After: [c, d, Z, null, null]
      TestGetFirst("c", false);
      TestFoldForward((dat, acc) -> { if (dat != null) return acc + "," + dat; else return acc; }, "", ",c,d,Z");
      TestSize(5, false);
    }

    @Test
    @DisplayName("GetNSetFirst then ShiftRight preserves replaced value")
    public void GetNSetFirstThenShiftRight() {
      AddTest(9);
      NewEmptyContainer();
      TestRealloc(Natural.Of(4));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestGetNSetFirst("Z", "a", false);
      // now: [Z, b, c, d]
      TestShiftRight(Natural.ZERO, Natural.ONE);
      // After: [null, Z, b, c]
      TestGetFirst(null, false);
      TestGetAt(Natural.Of(1), "Z", false);
      TestSize(4, false);
    }

    @Test
    @DisplayName("Swap then ShiftLeft then fold")
    public void SwapThenShiftLeftThenFold() {
      AddTest(10);
      NewEmptyContainer();
      TestRealloc(Natural.Of(5));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestSetAt("e", Natural.Of(4), false);
      TestSwap(Natural.ZERO, Natural.Of(4), false);
      // now: [e, b, c, d, a]
      TestShiftLeft(Natural.Of(1), Natural.Of(2));
      // After: [e, d, a, null, null]
      TestGetFirst("e", false);
      TestGetAt(Natural.Of(1), "d", false);
      TestFoldForward((dat, acc) -> { if (dat != null) return acc + "," + dat; else return acc; }, "", ",e,d,a");
    }

    @Test
    @DisplayName("Realloc shrink then expand then verify")
    public void ReallocShrinkExpandVerify() {
      AddTest(11);
      NewEmptyContainer();
      TestRealloc(Natural.Of(5));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestSetAt("e", Natural.Of(4), false);
      TestRealloc(Natural.Of(2));
      TestSize(2, false);
      TestGetFirst("a", false);
      TestGetLast("b", false);
      TestRealloc(Natural.Of(4));
      TestSize(4, false);
      TestGetAt(Natural.Of(2), null, false);
    }

    @Test
    @DisplayName("ShiftLeft then ShiftRight partial restore")
    public void ShiftLeftThenShiftRightPartialRestore() {
      AddTest(12);
      NewEmptyContainer();
      TestRealloc(Natural.Of(5));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestSetAt("e", Natural.Of(4), false);
      TestShiftLeft(Natural.ZERO, Natural.Of(2));
      // After: [c, d, e, null, null]
      TestGetFirst("c", false);
      TestShiftRight(Natural.ZERO, Natural.Of(2));
      // After: [null, null, c, d, e]
      TestGetAt(Natural.Of(2), "c", false);
      TestFoldForward((dat, acc) -> { if (dat != null) return acc + "," + dat; else return acc; }, "", ",c,d,e");
      TestSize(5, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("ShiftFirstLeft repeated then set and verify")
    public void ShiftFirstLeftRepeatedThenSet() {
      AddTest(11);
      NewEmptyContainer();
      TestRealloc(Natural.Of(4));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestShiftFirstLeft();
      // [b, c, d, null]
      TestShiftFirstLeft();
      // [c, d, null, null]
      TestGetFirst("c", false);
      TestGetAt(Natural.Of(1), "d", false);
      TestSetAt("Z", Natural.Of(2), false);
      TestFoldForward((dat, acc) -> { if (dat != null) return acc + "," + dat; else return acc; }, "", ",c,d,Z");
      TestSize(4, false);
    }

    @Test
    @DisplayName("GetNSetAt on all positions then fold")
    public void GetNSetAtAllPositions() {
      AddTest(10);
      NewEmptyContainer();
      TestRealloc(Natural.Of(4));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestGetNSetAt("W", "a", Natural.ZERO, false);
      TestGetNSetAt("X", "b", Natural.Of(1), false);
      TestGetNSetAt("Y", "c", Natural.Of(2), false);
      TestGetNSetAt("Z", "d", Natural.Of(3), false);
      TestFoldForward((dat, acc) -> acc + "," + dat, "", ",W,X,Y,Z");
      TestFoldBackward((dat, acc) -> acc + "," + dat, "", ",Z,Y,X,W");
    }

    @Test
    @DisplayName("Interleaved shift and swap operations")
    public void InterleavedShiftSwap() {
      AddTest(14);
      NewEmptyContainer();
      TestRealloc(Natural.Of(6));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestSetAt("e", Natural.Of(4), false);
      TestSetAt("f", Natural.Of(5), false);
      TestSwap(Natural.ZERO, Natural.Of(5), false);
      // [f, b, c, d, e, a]
      TestShiftLeft(Natural.Of(2), Natural.Of(2));
      // [f, b, e, a, null, null]
      TestGetAt(Natural.Of(2), "e", false);
      TestShiftRight(Natural.ZERO, Natural.ONE);
      // [null, f, b, e, a, null]
      TestGetFirst(null, false);
      TestGetAt(Natural.Of(1), "f", false);
      TestSwap(Natural.Of(1), Natural.Of(4), false);
      // [null, a, b, e, f, null]
      TestGetAt(Natural.Of(1), "a", false);
      TestGetAt(Natural.Of(4), "f", false);
    }

    @Test
    @DisplayName("Clear then realloc then populate and verify")
    public void ClearReallocPopulateVerify() {
      AddTest(10);
      NewNonEmptyContainer();
      TestClear();
      TestSize(0, false);
      TestIsEmpty(true, false);
      TestRealloc(Natural.Of(3));
      TestSetAt("X", Natural.ZERO, false);
      TestSetAt("Y", Natural.Of(1), false);
      TestSetAt("Z", Natural.Of(2), false);
      TestFoldForward((dat, acc) -> acc + "," + dat, "", ",X,Y,Z");
      TestGetFirst("X", false);
      TestGetLast("Z", false);
    }

    @Test
    @DisplayName("ShiftLeft at middle position with large num")
    public void ShiftLeftMiddleLargeNum() {
      AddTest(9);
      NewEmptyContainer();
      TestRealloc(Natural.Of(6));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestSetAt("e", Natural.Of(4), false);
      TestSetAt("f", Natural.Of(5), false);
      TestShiftLeft(Natural.Of(2), Natural.Of(10));
      TestGetFirst("a", false);
      TestGetAt(Natural.Of(1), "b", false);
      TestSize(6, false);
    }

    @Test
    @DisplayName("ShiftRight at middle position with large num")
    public void ShiftRightMiddleLargeNum() {
      AddTest(9);
      NewEmptyContainer();
      TestRealloc(Natural.Of(6));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestSetAt("e", Natural.Of(4), false);
      TestSetAt("f", Natural.Of(5), false);
      TestShiftRight(Natural.Of(2), Natural.Of(10));
      TestGetFirst("a", false);
      TestGetAt(Natural.Of(1), "b", false);
      TestSize(6, false);
    }

    @Test
    @DisplayName("ShiftLeft position 0 by full size then repopulate")
    public void ShiftLeftFullSizeThenRepopulate() {
      AddTest(10);
      NewEmptyContainer();
      TestRealloc(Natural.Of(4));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestShiftLeft(Natural.ZERO, Natural.Of(4));
      // All null now
      TestFoldForward((dat, acc) -> { if (dat != null) return acc + 1; else return acc; }, 0, 0);
      TestSetAt("W", Natural.ZERO, false);
      TestSetAt("X", Natural.Of(1), false);
      TestSetAt("Y", Natural.Of(2), false);
      TestSetAt("Z", Natural.Of(3), false);
      TestFoldForward((dat, acc) -> acc + "," + dat, "", ",W,X,Y,Z");
    }

    @Test
    @DisplayName("GetNSetFirst after ShiftFirstRight returns null")
    public void GetNSetFirstAfterShiftFirstRight() {
      AddTest(8);
      NewEmptyContainer();
      TestRealloc(Natural.Of(3));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestShiftFirstRight();
      // [null, a, b]
      TestGetNSetFirst("ZZZ", null, false);
      // now: [ZZZ, a, b]
      TestGetFirst("ZZZ", false);
      TestGetAt(Natural.Of(1), "a", false);
      TestGetAt(Natural.Of(2), "b", false);
      TestSize(3, false);
    }

    @Test
    @DisplayName("GetNSetLast after ShiftLastLeft returns null")
    public void GetNSetLastAfterShiftLastLeft() {
      AddTest(8);
      NewEmptyContainer();
      TestRealloc(Natural.Of(3));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestShiftLastLeft();
      // [a, b, null]
      TestGetNSetLast("ZZZ", null, false);
      // now: [a, b, ZZZ]
      TestGetLast("ZZZ", false);
      TestGetFirst("a", false);
      TestGetAt(Natural.Of(1), "b", false);
      TestSize(3, false);
    }

    @Test
    @DisplayName("Swap after ShiftLeft moves correct elements")
    public void SwapAfterShiftLeft() {
      AddTest(10);
      NewEmptyContainer();
      TestRealloc(Natural.Of(5));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestSetAt("e", Natural.Of(4), false);
      TestShiftLeft(Natural.ZERO, Natural.ONE);
      // [b, c, d, e, null]
      TestSwap(Natural.ZERO, Natural.Of(3), false);
      // [e, c, d, b, null]
      TestGetFirst("e", false);
      TestGetAt(Natural.Of(3), "b", false);
      TestFoldForward((dat, acc) -> { if (dat != null) return acc + "," + dat; else return acc; }, "", ",e,c,d,b");
      TestSize(5, false);
    }

    @Test
    @DisplayName("ShiftRight then GetNSetAt at shifted null position")
    public void ShiftRightThenGetNSetAtNull() {
      AddTest(8);
      NewEmptyContainer();
      TestRealloc(Natural.Of(4));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestShiftRight(Natural.Of(1), Natural.Of(2));
      // [a, null, null, b]
      TestGetNSetAt("X", null, Natural.Of(1), false);
      TestGetNSetAt("Y", null, Natural.Of(2), false);
      TestFoldForward((dat, acc) -> { if (dat != null) return acc + "," + dat; else return acc; }, "", ",a,X,Y,b");
      TestSize(4, false);
    }

    @Test
    @DisplayName("Double realloc shrink preserves only first elements")
    public void DoubleReallocShrink() {
      AddTest(9);
      NewEmptyContainer();
      TestRealloc(Natural.Of(6));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestSetAt("e", Natural.Of(4), false);
      TestSetAt("f", Natural.Of(5), false);
      TestRealloc(Natural.Of(4));
      TestRealloc(Natural.Of(2));
      TestSize(2, false);
      TestGetFirst("a", false);
      TestGetLast("b", false);
    }

    @Test
    @DisplayName("ShiftLeft by 1 at every position sequentially")
    public void ShiftLeftByOneAtEveryPosition() {
      AddTest(14);
      NewEmptyContainer();
      TestRealloc(Natural.Of(5));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestSetAt("e", Natural.Of(4), false);
      TestShiftLeft(Natural.Of(4));
      // [a, b, c, d, null]
      TestShiftLeft(Natural.Of(3));
      // [a, b, c, null, null]
      TestShiftLeft(Natural.Of(2));
      // [a, b, null, null, null]
      TestShiftLeft(Natural.Of(1));
      // [a, null, null, null, null]
      TestShiftLeft(Natural.ZERO);
      // [null, null, null, null, null]
      TestFoldForward((dat, acc) -> { if (dat != null) return acc + 1; else return acc; }, 0, 0);
      TestSize(5, false);
      TestSetAt("Z", Natural.Of(2), false);
      TestExists("Z", true);
      TestGetAt(Natural.Of(2), "Z", false);
    }

    @Test
    @DisplayName("ShiftRight by 1 at every position sequentially")
    public void ShiftRightByOneAtEveryPosition() {
      AddTest(14);
      NewEmptyContainer();
      TestRealloc(Natural.Of(5));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestSetAt("e", Natural.Of(4), false);
      TestShiftRight(Natural.ZERO);
      // [null, a, b, c, d]
      TestShiftRight(Natural.ONE);
      // [null, null, a, b, c]
      TestShiftRight(Natural.Of(2));
      // [null, null, null, a, b]
      TestShiftRight(Natural.Of(3));
      // [null, null, null, null, a]
      TestShiftRight(Natural.Of(4));
      // [null, null, null, null, null]
      TestFoldForward((dat, acc) -> { if (dat != null) return acc + 1; else return acc; }, 0, 0);
      TestSize(5, false);
      TestSetFirst("NEW", false);
      TestGetFirst("NEW", false);
      TestExists("NEW", true);
    }

    @Test
    @DisplayName("Swap then GetNSetFirst then ShiftLeft combined")
    public void SwapGetNSetFirstShiftLeftCombined() {
      AddTest(12);
      NewEmptyContainer();
      TestRealloc(Natural.Of(4));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestSwap(Natural.ZERO, Natural.Of(3), false);
      // [d, b, c, a]
      TestGetNSetFirst("X", "d", false);
      // [X, b, c, a]
      TestShiftLeft(Natural.ZERO, Natural.Of(2));
      // [c, a, null, null]
      TestGetFirst("c", false);
      TestGetAt(Natural.Of(1), "a", false);
      TestFoldForward((dat, acc) -> { if (dat != null) return acc + "," + dat; else return acc; }, "", ",c,a");
      TestSize(4, false);
    }

    @Test
    @DisplayName("GetNSetLast then ShiftRight then swap")
    public void GetNSetLastShiftRightSwap() {
      AddTest(11);
      NewEmptyContainer();
      TestRealloc(Natural.Of(4));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestGetNSetLast("Z", "d", false);
      // [a, b, c, Z]
      TestShiftRight(Natural.Of(2), Natural.ONE);
      // [a, b, null, c]
      TestSwap(Natural.ZERO, Natural.Of(3), false);
      // [c, b, null, a]
      TestGetFirst("c", false);
      TestGetLast("a", false);
      TestGetAt(Natural.Of(2), null, false);
      TestSize(4, false);
    }

    @Test
    @DisplayName("Realloc expand then ShiftFirstRight fills with null at front")
    public void ReallocExpandThenShiftFirstRight() {
      AddTest(10);
      NewEmptyContainer();
      TestRealloc(Natural.Of(3));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestRealloc(Natural.Of(5));
      // [a, b, c, null, null]
      TestShiftFirstRight();
      // [null, a, b, c, null]
      TestShiftFirstRight();
      // [null, null, a, b, c]
      TestGetAt(Natural.Of(2), "a", false);
      TestGetAt(Natural.Of(4), "c", false);
      TestGetFirst(null, false);
      TestFoldForward((dat, acc) -> { if (dat != null) return acc + "," + dat; else return acc; }, "", ",a,b,c");
    }

    @Test
    @DisplayName("ShiftLastLeft repeated collapses from end")
    public void ShiftLastLeftRepeated() {
      AddTest(10);
      NewEmptyContainer();
      TestRealloc(Natural.Of(4));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestShiftLastLeft();
      // [a, b, c, null]
      TestShiftLastLeft();
      // [a, b, null, null] -- depends on ShiftLastLeft semantics
      TestShiftLastLeft();
      // [a, null, null, null]
      TestShiftLastLeft();
      // [null, null, null, null]
      TestPrintContent("@@@@@@@@@@@@@@@@@@@");
      TestFoldForward((dat, acc) -> { if (dat != null) return acc + 1; else return acc; }, 0, 3);
      TestSize(4, false);
    }

    @Test
    @DisplayName("ShiftLastRight repeated has no cumulative effect past last")
    public void ShiftLastRightRepeated() {
      AddTest(10);
      NewEmptyContainer();
      TestRealloc(Natural.Of(4));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestShiftLastRight();
      // [a, b, c, null]
      TestGetAt(Natural.Of(3), null, false);
      TestGetAt(Natural.Of(2), "c", false);
      TestShiftLastRight();
      // last is null, ShiftLastRight nullifies it again
      TestGetAt(Natural.Of(3), null, false);
      TestGetAt(Natural.Of(2), "c", false);
      TestFoldForward((dat, acc) -> { if (dat != null) return acc + "," + dat; else return acc; }, "", ",a,b,c");
      TestSize(4, false);
    }

    @Test
    @DisplayName("Mixed: GetNSetAt + Swap + ShiftLeft + FoldBackward")
    public void MixedGetNSetSwapShiftFold() {
      AddTest(13);
      NewEmptyContainer();
      TestRealloc(Natural.Of(5));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestSetAt("e", Natural.Of(4), false);
      TestGetNSetAt("X", "c", Natural.Of(2), false);
      // [a, b, X, d, e]
      TestSwap(Natural.Of(1), Natural.Of(3), false);
      // [a, d, X, b, e]
      TestShiftLeft(Natural.Of(2), Natural.Of(2));
      // [a, d, e, null, null]
      TestFoldBackward((dat, acc) -> { if (dat != null) return acc + "," + dat; else return acc; }, "", ",e,d,a");
      TestFoldForward((dat, acc) -> { if (dat != null) return acc + "," + dat; else return acc; }, "", ",a,d,e");
      TestGetFirst("a", false);
      TestGetAt(Natural.Of(1), "d", false);
      TestSize(5, false);
    }

    @Test
    @DisplayName("SetAt null explicitly then verify exists and fold")
    public void SetAtNullExplicitly() {
      AddTest(8);
      NewEmptyContainer();
      TestRealloc(Natural.Of(4));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestSetAt(null, Natural.Of(1), false);
      TestSetAt(null, Natural.Of(2), false);
      // [a, null, null, d]
      TestExists("b", false);
      TestExists("c", false);
      TestFoldForward((dat, acc) -> { if (dat != null) return acc + "," + dat; else return acc; }, "", ",a,d");
    }

    @Test
    @DisplayName("Full cycle: populate, shift all out, repopulate, verify")
    public void FullCyclePopulateShiftRepopulate() {
      AddTest(16);
      NewEmptyContainer();
      TestRealloc(Natural.Of(3));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestShiftLeft(Natural.ZERO, Natural.Of(3));
      // [null, null, null]
      TestFoldForward((dat, acc) -> { if (dat != null) return acc + 1; else return acc; }, 0, 0);
      TestSetAt("X", Natural.ZERO, false);
      TestSetAt("Y", Natural.Of(1), false);
      TestSetAt("Z", Natural.Of(2), false);
      TestFoldForward((dat, acc) -> acc + "," + dat, "", ",X,Y,Z");
      TestShiftRight(Natural.ZERO, Natural.Of(3));
      // [null, null, null]
      TestFoldForward((dat, acc) -> { if (dat != null) return acc + 1; else return acc; }, 0, 0);
      TestSetAt("1", Natural.ZERO, false);
      TestSetAt("2", Natural.Of(1), false);
      TestSetAt("3", Natural.Of(2), false);
      TestFoldBackward((dat, acc) -> acc + "," + dat, "", ",3,2,1");
      TestSize(3, false);
    }
  }

  @Nested
  @DisplayName("Vector Iterator Tests")
  public class VectorIteratorTests {

    @Test
    @DisplayName("Forward iterator traverses all elements")
    public void ForwardIteratorTraversal() {
      AddTest(6);
      NewEmptyContainer();
      TestRealloc(Natural.Of(4));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestFIterator();
      TestForwardIteration(false, false);
    }

    @Test
    @DisplayName("Backward iterator traverses all elements")
    public void BackwardIteratorTraversal() {
      AddTest(6);
      NewEmptyContainer();
      TestRealloc(Natural.Of(4));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestBIterator();
      TestBackwardIteration(false, false);
    }

    @Test
    @DisplayName("Iterator on empty vector")
    public void IteratorOnEmpty() {
      AddTest(2);
      NewEmptyContainer();
      TestFIterator();
      TestBIterator();
    }

    @Test
    @DisplayName("Iterator after shift operations")
    public void IteratorAfterShift() {
      AddTest(9);
      NewEmptyContainer();
      TestRealloc(Natural.Of(4));
      TestSetAt("a", Natural.ZERO, false);
      TestSetAt("b", Natural.Of(1), false);
      TestSetAt("c", Natural.Of(2), false);
      TestSetAt("d", Natural.Of(3), false);
      TestShiftLeft(Natural.ZERO, Natural.Of(2));
      // [c, d, null, null]
      TestFIterator();
      TestBIterator();
      TestForwardIteration(true, false);
      TestBackwardIteration(true, false);
      TestSize(4, false);
    }
  }

  @Nested
  @DisplayName("Vector Null Parameter Tests")
  public class VectorNullParameterTests {

    @Test
    @DisplayName("GetAt with null position throws")
    public void GetAtNullThrows() {
      AddTest(2);
      NewNonEmptyContainer();
      assertThrows(NullPointerException.class, () -> ThisContainer().GetAt(null));
      NewEmptyContainer();
      assertThrows(NullPointerException.class, () -> ThisContainer().GetAt(null));
    }

    @Test
    @DisplayName("SetAt with null position throws")
    public void SetAtNullThrows() {
      AddTest(2);
      NewNonEmptyContainer();
      assertThrows(NullPointerException.class, () -> ThisContainer().SetAt("apple", null));
      NewEmptyContainer();
      assertThrows(NullPointerException.class, () -> ThisContainer().SetAt("apple", null));
    }

    @Test
    @DisplayName("Swap with null positions throws")
    public void SwapNullThrows() {
      AddTest(3);
      NewNonEmptyContainer();
      assertThrows(NullPointerException.class, () -> ThisContainer().Swap(null, Natural.ZERO));
      assertThrows(NullPointerException.class, () -> ThisContainer().Swap(Natural.ZERO, null));
      assertThrows(NullPointerException.class, () -> ThisContainer().Swap(null, null));
    }

    @Test
    @DisplayName("Realloc with null is no-op")
    public void ReallocNullNoOp() {
      AddTest(2);
      NewNonEmptyContainer();
      long beforeSize = ThisContainer().Size().ToLong();
      assertDoesNotThrow(() -> ThisContainer().Realloc(null));
      assertEquals(beforeSize, ThisContainer().Size().ToLong());
    }

    @Test
    @DisplayName("ShiftLeft with null num throws")
    public void ShiftLeftNullNumThrows() {
      AddTest(1);
      NewEmptyContainer();
      TestRealloc(Natural.Of(3));
      assertThrows(NullPointerException.class, () -> ThisContainer().ShiftLeft(Natural.ZERO, null));
    }

    @Test
    @DisplayName("ShiftRight with null num throws")
    public void ShiftRightNullNumThrows() {
      AddTest(1);
      NewEmptyContainer();
      TestRealloc(Natural.Of(3));
      assertThrows(NullPointerException.class, () -> ThisContainer().ShiftRight(Natural.ZERO, null));
    }

    @Test
    @DisplayName("ShiftLeft with null position throws")
    public void ShiftLeftNullPosThrows() {
      AddTest(1);
      NewEmptyContainer();
      TestRealloc(Natural.Of(3));
      assertThrows(NullPointerException.class, () -> ThisContainer().ShiftLeft(null, Natural.ONE));
    }

    @Test
    @DisplayName("ShiftRight with null position throws")
    public void ShiftRightNullPosThrows() {
      AddTest(1);
      NewEmptyContainer();
      TestRealloc(Natural.Of(3));
      assertThrows(NullPointerException.class, () -> ThisContainer().ShiftRight(null, Natural.ONE));
    }

    @Test
    @DisplayName("GetNSetAt with null position throws")
    public void GetNSetAtNullPosThrows() {
      AddTest(1);
      NewNonEmptyContainer();
      assertThrows(NullPointerException.class, () -> ThisContainer().GetNSetAt("test", null));
    }

    @Test
    @DisplayName("Swap with null positions on empty vector throws")
    public void SwapNullOnEmptyThrows() {
      AddTest(1);
      NewEmptyContainer();
      assertThrows(NullPointerException.class, () -> ThisContainer().Swap(null, null));
    }
  }

  @Nested
  @DisplayName("Vector String-Specific Edge Cases")
  public class VectorStringSpecificTests {

    @Test
    @DisplayName("Empty string is a valid data value")
    public void EmptyStringAsData() {
      AddTest(6);
      NewEmptyContainer();
      TestRealloc(Natural.Of(3));
      TestSetAt("", Natural.ZERO, false);
      TestSetAt("", Natural.Of(1), false);
      TestSetAt("", Natural.Of(2), false);
      TestExists("", true);
      TestGetFirst("", false);
      TestFoldForward((dat, acc) -> acc + dat.length(), 0, 0);
    }

    @Test
    @DisplayName("Very long strings preserved through operations")
    public void LongStringsPreserved() {
      AddTest(8);
      NewEmptyContainer();
      String longStr = "a".repeat(10000);
      TestRealloc(Natural.Of(3));
      TestSetAt(longStr, Natural.ZERO, false);
      TestSetAt("short", Natural.Of(1), false);
      TestSetAt(longStr, Natural.Of(2), false);
      TestSwap(Natural.ZERO, Natural.Of(1), false);
      TestGetFirst("short", false);
      TestGetAt(Natural.Of(1), longStr, false);
      TestExists(longStr, true);
      TestFoldForward((dat, acc) -> acc + dat.length(), 0, 20005);
    }

    @Test
    @DisplayName("Unicode strings handled correctly")
    public void UnicodeStrings() {
      AddTest(7);
      NewEmptyContainer();
      TestRealloc(Natural.Of(4));
      TestSetAt("日本語", Natural.ZERO, false);
      TestSetAt("中文", Natural.Of(1), false);
      TestSetAt("한국어", Natural.Of(2), false);
      TestSetAt("العربية", Natural.Of(3), false);
      TestExists("中文", true);
      TestSwap(Natural.ZERO, Natural.Of(3), false);
      TestGetFirst("العربية", false);
    }

    @Test
    @DisplayName("Strings with special characters in fold")
    public void SpecialCharactersInFold() {
      AddTest(5);
      NewEmptyContainer();
      TestRealloc(Natural.Of(3));
      TestSetAt("hello\nworld", Natural.ZERO, false);
      TestSetAt("tab\there", Natural.Of(1), false);
      TestSetAt("null\0char", Natural.Of(2), false);
      TestFoldForward((dat, acc) -> acc + 1, 0, 3);
      TestExists("hello\nworld", true);
    }

    @Test
    @DisplayName("Case sensitivity in exists")
    public void CaseSensitivityExists() {
      AddTest(5);
      NewEmptyContainer();
      TestRealloc(Natural.Of(2));
      TestSetAt("Hello", Natural.ZERO, false);
      TestSetAt("HELLO", Natural.Of(1), false);
      TestExists("Hello", true);
      TestExists("hello", false);
      TestExists("HELLO", true);
    }
  }

}
