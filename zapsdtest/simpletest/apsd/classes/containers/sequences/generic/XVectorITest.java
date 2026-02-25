package zapsdtest.simpletest.apsd.classes.containers.sequences.generic;

import apsd.classes.containers.sequences.CircularVector;
import apsd.classes.containers.sequences.Vector;
import apsd.classes.utilities.Natural;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

abstract public class XVectorITest extends XVectorTest<Long> {

  @Override
  public void NewNonEmptyContainer() {
    AddTest(13);
    NewEmptyContainer();
    TestSetFirst(0L, true);
    TestRealloc(Natural.Of(1));
    TestSetFirst(0L, false);
    TestRealloc(Natural.Of(5));
    TestSize(5, false);
    TestSwap(Natural.ZERO, Natural.Of(3), false);
    TestSetFirst(4L, false);
    TestSetAt(3L, Natural.Of(1), false);
    TestSetAt(2L, Natural.Of(2), false);
    TestSetAt(1L, Natural.Of(5), true);
    TestSetLast(1L, false);
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
      AddTest(25);
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
      TestSetFirst(42L, false);
      TestGetFirst(42L, false);
      TestGetLast(42L, false);
      TestGetAt(Natural.ZERO, 42L, false);
      TestExists(42L, true);
      TestExists(0L, false);
      TestSwap(Natural.ZERO, Natural.ZERO, false);
      TestGetFirst(42L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 42L);
      TestFoldBackward((dat, acc) -> acc + dat, 0L, 42L);
      TestClear();
    }

    @Test
    @DisplayName("Boundary index access")
    public void BoundaryAccess() {
      AddTest(11);
      NewEmptyContainer();
      TestRealloc(Natural.Of(5));
      TestSetAt(10L, Natural.ZERO, false);
      TestSetAt(20L, Natural.Of(4), false);
      TestGetAt(Natural.ZERO, 10L, false);
      TestGetAt(Natural.Of(4), 20L, false);
      TestGetAt(Natural.Of(5), 0L, true);
      TestSetAt(0L, Natural.Of(5), true);
      TestSwap(Natural.ZERO, Natural.Of(4), false);
      TestGetAt(Natural.ZERO, 20L, false);
      TestGetAt(Natural.Of(4), 10L, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Realloc to same size")
    public void ReallocSameSize() {
      AddTest(7);
      NewEmptyContainer();
      TestRealloc(Natural.Of(3));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestRealloc(Natural.Of(3));
      TestFoldForward((dat, acc) -> acc + dat, 0L, 6L);
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
      TestGetFirst(0L, true);
    }

    @Test
    @DisplayName("Shift then SubVector")
    public void ShiftThenSubVector() {
      AddTest(4);
      NewEmptyContainer();
      TestRealloc(Natural.Of(6));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestSetAt(4L, Natural.Of(3), false);
      TestSetAt(5L, Natural.Of(4), false);
      TestSetAt(6L, Natural.Of(5), false);
      TestShiftRight(Natural.ZERO, Natural.Of(3));
      TestPrintContent("");
      TestSize(6L, false);
      TestSubVector(Natural.ZERO, Natural.Of(2), false);
    }

    @Test
    @DisplayName("Shift Branches Match LinearVector")
    public void ShiftBranchesMatchLinearVector() {
      var circ = new CircularVector<Long>(Natural.Of(8));
      var lin = new Vector<Long>(Natural.Of(8));

      for (long i = 0; i < 8; i++) {
        circ.SetAt(i, Natural.Of(i));
        lin.SetAt(i, Natural.Of(i));
      }

      // make circular start non-zero path happen
      circ.ShiftLeft(Natural.ZERO, Natural.Of(2));
      lin.ShiftLeft(Natural.ZERO, Natural.Of(2));

      // force each branch type
      circ.ShiftLeft(Natural.Of(1), Natural.ONE);  lin.ShiftLeft(Natural.Of(1), Natural.ONE);  // left-side
      circ.ShiftLeft(Natural.Of(5), Natural.ONE);  lin.ShiftLeft(Natural.Of(5), Natural.ONE);  // right-side
      circ.ShiftRight(Natural.Of(2), Natural.ONE); lin.ShiftRight(Natural.Of(2), Natural.ONE); // left-side
      circ.ShiftRight(Natural.Of(5), Natural.ONE); lin.ShiftRight(Natural.Of(5), Natural.ONE); // right-side

      for (long i = 0; i < 8; i++) {
        assertEquals(lin.GetAt(Natural.Of(i)), circ.GetAt(Natural.Of(i)), "Mismatch at " + i);
      }
    } 

    @Test
    @DisplayName("Randomized differential: CircularVector matches Vector after each shift")
    public void RandomizedShiftDifferential() {
      var circ = new CircularVector<Long>(Natural.Of(800));
      var lin = new Vector<Long>(Natural.Of(800));

      for (long i = 0; i < 800; i++) {
        circ.SetAt(i, Natural.Of(i));
        lin.SetAt(i, Natural.Of(i));
      }

      // force non-zero start early
      circ.ShiftLeft(Natural.ZERO, Natural.Of(37));
      lin.ShiftLeft(Natural.ZERO, Natural.Of(37));

      Random rnd = new Random(123456789L);
      for (int q = 0; q < 10; q++) {
        for (long i = 0; i < 800; i++) {
          circ.SetAt(i, Natural.Of(i));
          lin.SetAt(i, Natural.Of(i));
        }
        for (int t = 0; t < 500; t++) {
          long pos = rnd.nextInt(800);
          long num = rnd.nextInt(3); // includes 0 and values > remaining (clamp path)

          if (rnd.nextBoolean()) {
            circ.ShiftLeft(Natural.Of(pos), Natural.Of(num));
            lin.ShiftLeft(Natural.Of(pos), Natural.Of(num));
          } else {
            circ.ShiftRight(Natural.Of(pos), Natural.Of(num));
            lin.ShiftRight(Natural.Of(pos), Natural.Of(num));
          }

          for (long i = 0; i < 800; i++) {
            assertEquals(lin.GetAt(Natural.Of(i)), circ.GetAt(Natural.Of(i)),
                "Mismatch at step " + t + ", index " + i + ", pos=" + pos + ", num=" + num);
          }
        }
        for (int t = 0; t < 500; t++) {
          long pos = rnd.nextInt(800);
          long num = rnd.nextInt(1200); // includes 0 and values > remaining (clamp path)

          if (rnd.nextBoolean()) {
            circ.ShiftLeft(Natural.Of(pos), Natural.Of(num));
            lin.ShiftLeft(Natural.Of(pos), Natural.Of(num));
          } else {
            circ.ShiftRight(Natural.Of(pos), Natural.Of(num));
            lin.ShiftRight(Natural.Of(pos), Natural.Of(num));
          }

          for (long i = 0; i < 800; i++) {
            assertEquals(lin.GetAt(Natural.Of(i)), circ.GetAt(Natural.Of(i)),
                "Mismatch at step " + t + ", index " + i + ", pos=" + pos + ", num=" + num);
          }
        }
      }
    }

    @Test
    @DisplayName("Multiple consecutive shifts")
    public void ConsecutiveShifts() {
      AddTest(19);
      NewEmptyContainer();
      TestRealloc(Natural.Of(6));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestSetAt(4L, Natural.Of(3), false);
      TestSetAt(5L, Natural.Of(4), false);
      TestSetAt(6L, Natural.Of(5), false);
      TestPrintContent("");
      TestShiftLeft(Natural.ZERO, Natural.Of(2));
      TestPrintContent("");
      TestShiftRight(Natural.Of(2), Natural.Of(2));
      TestPrintContent("");
      TestShiftLeft(Natural.Of(1), Natural.ONE);
      TestPrintContent("");
      TestSize(6, false);
      TestFoldForward((dat, acc) -> { if (dat != null) return acc + dat; else return acc; }, 0L, 14L);
      TestShiftRight(Natural.Of(3), Natural.Of(2));
      TestFoldForward((dat, acc) -> { if (dat != null) return acc + dat; else return acc; }, 0L, 8L);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Shift at boundaries")
    public void ShiftBoundaries() {
      AddTest(10);
      NewEmptyContainer();
      TestRealloc(Natural.Of(4));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestSetAt(4L, Natural.Of(3), false);
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
      TestSetAt(100L, Natural.Of(1), false);
      TestSwap(Natural.ONE, Natural.ONE, false);
      TestGetAt(Natural.Of(1), 100L, false);
      TestPrintContent("");
      TestSize(3, false);
    }

    @Test
    @DisplayName("Fold with identity operations")
    public void FoldIdentity() {
      AddTest(9);
      NewEmptyContainer();
      TestRealloc(Natural.Of(4));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestSetAt(4L, Natural.Of(3), false);
      TestFoldForward((dat, acc) -> acc, 999L, 999L);
      TestFoldBackward((dat, acc) -> acc, 888L, 888L);
      TestFoldForward((dat, acc) -> dat, 0L, 4L);
      TestFoldBackward((dat, acc) -> dat, 0L, 1L);
    }

    @Test
    @DisplayName("ShiftRight clamps num > remaining (tail becomes nulls)")
    public void ShiftRightClampsLargeNum() {
      AddTest(8);
      NewEmptyContainer();
      TestRealloc(Natural.Of(5));
      ThisContainer().SetAt(1L, Natural.ZERO);
      ThisContainer().SetAt(2L, Natural.Of(1));
      ThisContainer().SetAt(3L, Natural.Of(2));
      ThisContainer().SetAt(4L, Natural.Of(3));
      ThisContainer().SetAt(5L, Natural.Of(4));

      assertDoesNotThrow(() -> ThisContainer().ShiftRight(Natural.ONE, Natural.Of(5)));
      assertEquals(1L, ThisContainer().GetAt(Natural.ZERO));
      assertNull(ThisContainer().GetAt(Natural.Of(1)));
      assertNull(ThisContainer().GetAt(Natural.Of(2)));
      assertNull(ThisContainer().GetAt(Natural.Of(3)));
      assertNull(ThisContainer().GetAt(Natural.Of(4)));

      ThisContainer().SetAt(1L, Natural.ZERO);
      ThisContainer().SetAt(2L, Natural.Of(1));
      ThisContainer().SetAt(3L, Natural.Of(2));
      ThisContainer().SetAt(4L, Natural.Of(3));
      ThisContainer().SetAt(5L, Natural.Of(4));
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
      ThisContainer().SetAt(1L, Natural.ZERO);
      ThisContainer().SetAt(2L, Natural.Of(1));
      ThisContainer().SetAt(3L, Natural.Of(2));
      ThisContainer().SetAt(4L, Natural.Of(3));
      ThisContainer().SetAt(5L, Natural.Of(4));

      assertDoesNotThrow(() -> ThisContainer().ShiftLeft(Natural.ZERO, Natural.Of(5)));
      for (long i = 0; i < 5; i++) assertNull(ThisContainer().GetAt(Natural.Of(i)));


      ThisContainer().SetAt(1L, Natural.ZERO);
      ThisContainer().SetAt(2L, Natural.Of(1));
      ThisContainer().SetAt(3L, Natural.Of(2));
      ThisContainer().SetAt(4L, Natural.Of(3));
      ThisContainer().SetAt(5L, Natural.Of(4));

      assertDoesNotThrow(() -> ThisContainer().ShiftLeft(Natural.ONE, Natural.Of(5)));
      assertEquals(1L, ThisContainer().GetAt(Natural.ZERO));
      for (long i = 1; i < 5; i++) assertNull(ThisContainer().GetAt(Natural.Of(i)));
      
      TestSize(5, false);
    }

    @Test
    @DisplayName("Shift with num=0 is a no-op")
    public void ShiftZeroIsNoOp() {
      AddTest(6);
      NewEmptyContainer();
      TestRealloc(Natural.Of(3));
      ThisContainer().SetAt(7L, Natural.ZERO);
      ThisContainer().SetAt(8L, Natural.Of(1));
      ThisContainer().SetAt(9L, Natural.Of(2));

      assertDoesNotThrow(() -> ThisContainer().ShiftRight(Natural.ONE, Natural.ZERO));
      assertEquals(7L, ThisContainer().GetAt(Natural.ZERO));
      assertEquals(8L, ThisContainer().GetAt(Natural.Of(1)));
      assertEquals(9L, ThisContainer().GetAt(Natural.Of(2)));

      assertDoesNotThrow(() -> ThisContainer().ShiftLeft(Natural.ONE, Natural.ZERO));
      assertEquals(7L, ThisContainer().GetAt(Natural.ZERO));
      assertEquals(8L, ThisContainer().GetAt(Natural.Of(1)));
      assertEquals(9L, ThisContainer().GetAt(Natural.Of(2)));
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
      TestSetFirst(99L, false);
      TestShiftFirstLeft();
      TestGetFirst(null, false);
    }

    @Test
    @DisplayName("ShiftFirstLeft on multiple elements")
    public void ShiftFirstLeftMultipleElements() {
      AddTest(8);
      NewEmptyContainer();
      TestRealloc(Natural.Of(4));
      TestSetAt(10L, Natural.ZERO, false);
      TestSetAt(20L, Natural.Of(1), false);
      TestSetAt(30L, Natural.Of(2), false);
      TestSetAt(40L, Natural.Of(3), false);
      TestShiftFirstLeft();
      // After ShiftFirstLeft: [20, 30, 40, null]
      TestGetFirst(20L, false);
      TestGetAt(Natural.Of(2), 40L, false);
      TestSize(4, false);
    }

    @Test
    @DisplayName("ShiftFirstRight on single element")
    public void ShiftFirstRightSingleElement() {
      AddTest(4);
      NewEmptyContainer();
      TestRealloc(Natural.Of(1));
      TestSetFirst(99L, false);
      TestShiftFirstRight();
      TestGetFirst(null, false);
    }

    @Test
    @DisplayName("ShiftFirstRight on multiple elements")
    public void ShiftFirstRightMultipleElements() {
      AddTest(9);
      NewEmptyContainer();
      TestRealloc(Natural.Of(4));
      TestSetAt(10L, Natural.ZERO, false);
      TestSetAt(20L, Natural.Of(1), false);
      TestSetAt(30L, Natural.Of(2), false);
      TestSetAt(40L, Natural.Of(3), false);
      TestShiftFirstRight();
      // After ShiftFirstRight: [null, 10, 20, 30]
      TestGetFirst(null, false);
      TestGetAt(Natural.Of(1), 10L, false);
      TestGetAt(Natural.Of(3), 30L, false);
      TestSize(4, false);
    }

    @Test
    @DisplayName("ShiftLastLeft on single element")
    public void ShiftLastLeftSingleElement() {
      AddTest(4);
      NewEmptyContainer();
      TestRealloc(Natural.Of(1));
      TestSetFirst(99L, false);
      TestShiftLastLeft();
      TestGetFirst(null, false);
    }

    @Test
    @DisplayName("ShiftLastLeft on multiple elements")
    public void ShiftLastLeftMultipleElements() {
      AddTest(7);
      NewEmptyContainer();
      TestRealloc(Natural.Of(4));
      TestSetAt(10L, Natural.ZERO, false);
      TestSetAt(20L, Natural.Of(1), false);
      TestSetAt(30L, Natural.Of(2), false);
      TestSetAt(40L, Natural.Of(3), false);
      // ShiftLastLeft shifts at last position left by 1
      TestShiftLastLeft();
      // After: [10, 20, 30, null]
      TestGetAt(Natural.Of(2), 30L, false);
      TestSize(4, false);
    }

    @Test
    @DisplayName("ShiftLastRight on single element")
    public void ShiftLastRightSingleElement() {
      AddTest(4);
      NewEmptyContainer();
      TestRealloc(Natural.Of(1));
      TestSetFirst(99L, false);
      TestShiftLastRight();
      TestGetFirst(null, false);
    }

    @Test
    @DisplayName("ShiftLastRight on multiple elements")
    public void ShiftLastRightMultipleElements() {
      AddTest(7);
      NewEmptyContainer();
      TestRealloc(Natural.Of(4));
      TestSetAt(10L, Natural.ZERO, false);
      TestSetAt(20L, Natural.Of(1), false);
      TestSetAt(30L, Natural.Of(2), false);
      TestSetAt(40L, Natural.Of(3), false);
      // ShiftLastRight shifts at last position right by 1 — last pos is clamped so it nullifies the last
      TestShiftLastRight();
      TestGetAt(Natural.Of(2), 30L, false);
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
    @DisplayName("Consecutive ShiftFirstLeft clears vector")
    public void ConsecutiveShiftFirstLeftClearsVector() {
      AddTest(9);
      NewEmptyContainer();
      TestRealloc(Natural.Of(3));
      TestSetAt(10L, Natural.ZERO, false);
      TestSetAt(20L, Natural.Of(1), false);
      TestSetAt(30L, Natural.Of(2), false);
      TestShiftFirstLeft();
      TestShiftFirstLeft();
      TestShiftFirstLeft();
      // After 3 ShiftFirstLeft on size 3: all null
      TestFoldForward((dat, acc) -> { if (dat != null) return acc + dat; else return acc; }, 0L, 0L);
      TestSize(3, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("Consecutive ShiftFirstRight clears vector")
    public void ConsecutiveShiftFirstRightClearsVector() {
      AddTest(9);
      NewEmptyContainer();
      TestRealloc(Natural.Of(3));
      TestSetAt(10L, Natural.ZERO, false);
      TestSetAt(20L, Natural.Of(1), false);
      TestSetAt(30L, Natural.Of(2), false);
      TestShiftFirstRight();
      TestShiftFirstRight();
      TestShiftFirstRight();
      // After 3 ShiftFirstRight on size 3: all null
      TestFoldForward((dat, acc) -> { if (dat != null) return acc + dat; else return acc; }, 0L, 0L);
      TestSize(3, false);
      TestPrintContent("");
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
      TestSetAt(10L, Natural.ZERO, false);
      TestSetAt(20L, Natural.Of(1), false);
      TestSetAt(30L, Natural.Of(2), false);
      TestGetNSetFirst(99L, 10L, false);
      TestGetFirst(99L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 149L);
    }

    @Test
    @DisplayName("GetNSetLast basic operation")
    public void GetNSetLastBasic() {
      AddTest(6);
      NewEmptyContainer();
      TestRealloc(Natural.Of(3));
      TestSetAt(10L, Natural.ZERO, false);
      TestSetAt(20L, Natural.Of(1), false);
      TestSetAt(30L, Natural.Of(2), false);
      TestGetNSetLast(99L, 30L, false);
      TestGetLast(99L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 129L);
    }

    @Test
    @DisplayName("GetNSetAt basic operation")
    public void GetNSetAtBasic() {
      AddTest(7);
      NewEmptyContainer();
      TestRealloc(Natural.Of(4));
      TestSetAt(10L, Natural.ZERO, false);
      TestSetAt(20L, Natural.Of(1), false);
      TestSetAt(30L, Natural.Of(2), false);
      TestSetAt(40L, Natural.Of(3), false);
      TestGetNSetAt(55L, 20L, Natural.Of(1), false);
      TestGetAt(Natural.Of(1), 55L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 135L);
    }

    @Test
    @DisplayName("GetNSetFirst on empty vector throws")
    public void GetNSetFirstEmptyThrows() {
      AddTest(1);
      NewEmptyContainer();
      TestGetNSetFirst(99L, 0L, true);
    }

    @Test
    @DisplayName("GetNSetLast on empty vector throws")
    public void GetNSetLastEmptyThrows() {
      AddTest(1);
      NewEmptyContainer();
      TestGetNSetLast(99L, 0L, true);
    }

    @Test
    @DisplayName("GetNSetAt out of bounds throws")
    public void GetNSetAtOutOfBoundsThrows() {
      AddTest(2);
      NewEmptyContainer();
      TestRealloc(Natural.Of(2));
      TestGetNSetAt(99L, 0L, Natural.Of(5), true);
      TestGetNSetAt(99L, 0L, Natural.Of(2), true);
    }

    @Test
    @DisplayName("GetNSetFirst chain replaces values correctly")
    public void GetNSetFirstChain() {
      AddTest(8);
      NewEmptyContainer();
      TestRealloc(Natural.Of(3));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestGetNSetFirst(10L, 1L, false);
      TestGetNSetFirst(20L, 10L, false);
      TestGetNSetFirst(30L, 20L, false);
      TestGetFirst(30L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 35L);
    }

    @Test
    @DisplayName("GetNSetLast chain replaces values correctly")
    public void GetNSetLastChain() {
      AddTest(8);
      NewEmptyContainer();
      TestRealloc(Natural.Of(3));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestGetNSetLast(10L, 3L, false);
      TestGetNSetLast(20L, 10L, false);
      TestGetNSetLast(30L, 20L, false);
      TestGetLast(30L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 33L);
    }

    @Test
    @DisplayName("GetNSetAt on single element vector")
    public void GetNSetAtSingleElement() {
      AddTest(5);
      NewEmptyContainer();
      TestRealloc(Natural.Of(1));
      TestSetFirst(42L, false);
      TestGetNSetAt(100L, 42L, Natural.ZERO, false);
      TestGetFirst(100L, false);
      TestGetNSetFirst(200L, 100L, false);
      TestGetNSetLast(300L, 200L, false);
    }
  }

  @Nested
  @DisplayName("Vector Swap Advanced Tests")
  public class VectorSwapAdvancedTests {

    @Test
    @DisplayName("Swap first and last")
    public void SwapFirstAndLast() {
      AddTest(6);
      NewEmptyContainer();
      TestRealloc(Natural.Of(5));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestSetAt(4L, Natural.Of(3), false);
      TestSetAt(5L, Natural.Of(4), false);
      TestSwap(Natural.ZERO, Natural.Of(4), false);
    }

    @Test
    @DisplayName("Swap adjacent elements")
    public void SwapAdjacent() {
      AddTest(8);
      NewEmptyContainer();
      TestRealloc(Natural.Of(4));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestSetAt(4L, Natural.Of(3), false);
      TestSwap(Natural.ZERO, Natural.ONE, false);
      TestGetAt(Natural.ZERO, 2L, false);
      TestGetAt(Natural.Of(1), 1L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 10L);
    }

    @Test
    @DisplayName("Swap with null elements")
    public void SwapWithNulls() {
      AddTest(7);
      NewEmptyContainer();
      TestRealloc(Natural.Of(3));
      TestSetAt(10L, Natural.ZERO, false);
      // positions 1, 2 are null
      TestSwap(Natural.ZERO, Natural.Of(2), false);
      TestGetAt(Natural.ZERO, null, false);
      TestGetAt(Natural.Of(2), 10L, false);
      TestSwap(Natural.ZERO, Natural.Of(2), false);
      TestGetAt(Natural.ZERO, 10L, false);
    }

    @Test
    @DisplayName("Multiple swaps restore original")
    public void MultipleSwapsRestore() {
      AddTest(10);
      NewEmptyContainer();
      TestRealloc(Natural.Of(3));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestSwap(Natural.ZERO, Natural.Of(2), false);
      TestGetFirst(3L, false);
      TestGetLast(1L, false);
      TestSwap(Natural.ZERO, Natural.Of(2), false);
      TestGetFirst(1L, false);
      TestGetLast(3L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 6L);
      TestFoldBackward((dat, acc) -> acc + dat, 0L, 6L);
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
      TestSetAt(100L, Natural.ZERO, false);
      TestSetAt(200L, Natural.Of(1), false);
      TestSwap(Natural.ZERO, Natural.Of(1), false);
      TestGetFirst(200L, false);
      TestGetLast(100L, false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 300L);
    }
  }

  @Nested
  @DisplayName("Vector Traverse and Fold Advanced Tests")
  public class VectorTraverseFoldAdvancedTests {

    @Test
    @DisplayName("TraverseForward counts all elements")
    public void TraverseForwardCountAll() {
      AddTest(6);
      NewEmptyContainer();
      TestRealloc(Natural.Of(4));
      TestSetAt(10L, Natural.ZERO, false);
      TestSetAt(20L, Natural.Of(1), false);
      TestSetAt(30L, Natural.Of(2), false);
      TestSetAt(40L, Natural.Of(3), false);
      TestTraverseForward(dat -> dat > 50L, false);
      TestTraverseBackward(dat -> dat == 10L, true);
    }

    @Test
    @DisplayName("FoldForward sum with nulls")
    public void FoldForwardSumWithNulls() {
      AddTest(7);
      NewEmptyContainer();
      TestRealloc(Natural.Of(5));
      TestSetAt(10L, Natural.ZERO, false);
      // positions 1, 2 are null
      TestSetAt(30L, Natural.Of(3), false);
      TestSetAt(40L, Natural.Of(4), false);
      TestFoldForward((dat, acc) -> { if (dat != null) return acc + dat; else return acc; }, 0L, 80L);
      TestFoldBackward((dat, acc) -> { if (dat != null) return acc + dat; else return acc; }, 0L, 80L);
      TestSize(5, false);
    }

    @Test
    @DisplayName("FoldForward product - zero element makes product zero")
    public void FoldForwardProductWithZero() {
      AddTest(6);
      NewEmptyContainer();
      TestRealloc(Natural.Of(3));
      TestSetAt(5L, Natural.ZERO, false);
      TestSetAt(0L, Natural.Of(1), false);
      TestSetAt(7L, Natural.Of(2), false);
      TestFoldForward((dat, acc) -> acc * dat, 1L, 0L);
      TestFoldBackward((dat, acc) -> acc * dat, 1L, 0L);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 12L);
    }

    @Test
    @DisplayName("FoldBackward builds reversed accumulation")
    public void FoldBackwardDirection() {
      AddTest(5);
      NewEmptyContainer();
      TestRealloc(Natural.Of(4));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestSetAt(4L, Natural.Of(3), false);
      // FoldBackward: processes 4, 3, 2, 1
      TestFoldBackward((dat, acc) -> dat, 0L, 1L);
    }

    @Test
    @DisplayName("Fold on empty vector returns initial value")
    public void FoldEmptyReturnsInitial() {
      AddTest(2);
      NewEmptyContainer();
      TestFoldForward((dat, acc) -> acc + dat, 42L, 42L);
      TestFoldBackward((dat, acc) -> acc + dat, 99L, 99L);
    }

    @Test
    @DisplayName("Fold on single null element vector")
    public void FoldOnSingleNull() {
      AddTest(3);
      NewEmptyContainer();
      TestRealloc(Natural.Of(1));
      // Position 0 is null by default
      TestFoldForward((dat, acc) -> { if (dat != null) return acc + dat; else return acc; }, 0L, 0L);
      TestFoldBackward((dat, acc) -> { if (dat != null) return acc + dat; else return acc; }, 0L, 0L);
      TestSize(1, false);
    }

    @Test
    @DisplayName("FoldForward max element")
    public void FoldForwardMax() {
      AddTest(6);
      NewEmptyContainer();
      TestRealloc(Natural.Of(5));
      TestSetAt(3L, Natural.ZERO, false);
      TestSetAt(7L, Natural.Of(1), false);
      TestSetAt(2L, Natural.Of(2), false);
      TestSetAt(9L, Natural.Of(3), false);
      TestSetAt(1L, Natural.Of(4), false);
      TestFoldForward((dat, acc) -> dat > acc ? dat : acc, Long.MIN_VALUE, 9L);
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
      TestSetAt(10L, Natural.ZERO, false);
      TestSetAt(20L, Natural.Of(1), false);
      TestSetAt(30L, Natural.Of(2), false);
      TestSetAt(40L, Natural.Of(3), false);
      TestSetAt(50L, Natural.Of(4), false);
      TestExists(10L, true);
      TestExists(30L, true);
      TestExists(50L, true);
    }

    @Test
    @DisplayName("Search element not present")
    public void SearchNotPresent() {
      AddTest(5);
      NewEmptyContainer();
      TestRealloc(Natural.Of(3));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestExists(4L, false);
      TestExists(0L, false);
    }

    @Test
    @DisplayName("Search on empty vector")
    public void SearchOnEmpty() {
      AddTest(2);
      NewEmptyContainer();
      TestExists(1L, false);
      TestExists(null, false);
    }

    @Test
    @DisplayName("Exists with duplicate values")
    public void ExistsWithDuplicates() {
      AddTest(5);
      NewEmptyContainer();
      TestRealloc(Natural.Of(4));
      TestSetAt(5L, Natural.ZERO, false);
      TestSetAt(5L, Natural.Of(1), false);
      TestSetAt(5L, Natural.Of(2), false);
      TestSetAt(5L, Natural.Of(3), false);
      TestExists(5L, true);
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
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestSubVector(Natural.ZERO, Natural.Of(3), false);
    }

    @Test
    @DisplayName("SubVector single element")
    public void SubVectorSingleElement() {
      AddTest(3);
      NewEmptyContainer();
      TestRealloc(Natural.Of(4));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSubVector(Natural.ONE, Natural.ONE, false);
    }

    @Test
    @DisplayName("SubVector out of bounds returns null")
    public void SubVectorOutOfBounds() {
      AddTest(3);
      NewEmptyContainer();
      TestRealloc(Natural.Of(3));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSubVector(Natural.ZERO, Natural.Of(5), true);
    }

    @Test
    @DisplayName("SubVector reversed range returns null")
    public void SubVectorReversedRange() {
      AddTest(3);
      NewEmptyContainer();
      TestRealloc(Natural.Of(4));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
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
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestSetAt(4L, Natural.Of(3), false);
      TestSetAt(5L, Natural.Of(4), false);
      TestSetFirst(10L, false);
      TestSetLast(50L, false);
      TestShiftLeft(Natural.ZERO, Natural.Of(2));
      // After: [3, 4, 50, null, null]
      TestGetFirst(3L, false);
      TestFoldForward((dat, acc) -> { if (dat != null) return acc + dat; else return acc; }, 0L, 57L);
      TestSize(5, false);
    }

    @Test
    @DisplayName("GetNSetFirst then ShiftRight preserves replaced value")
    public void GetNSetFirstThenShiftRight() {
      AddTest(9);
      NewEmptyContainer();
      TestRealloc(Natural.Of(4));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestSetAt(4L, Natural.Of(3), false);
      TestGetNSetFirst(99L, 1L, false);
      // now: [99, 2, 3, 4]
      TestShiftRight(Natural.ZERO, Natural.ONE);
      // After: [null, 99, 2, 3]
      TestGetFirst(null, false);
      TestGetAt(Natural.Of(1), 99L, false);
      TestSize(4, false);
    }

    @Test
    @DisplayName("Swap then ShiftLeft then fold")
    public void SwapThenShiftLeftThenFold() {
      AddTest(10);
      NewEmptyContainer();
      TestRealloc(Natural.Of(5));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestSetAt(4L, Natural.Of(3), false);
      TestSetAt(5L, Natural.Of(4), false);
      TestSwap(Natural.ZERO, Natural.Of(4), false);
      // now: [5, 2, 3, 4, 1]
      TestShiftLeft(Natural.Of(1), Natural.Of(2));
      // After: [5, 4, 1, null, null]
      TestGetFirst(5L, false);
      TestGetAt(Natural.Of(1), 4L, false);
      TestFoldForward((dat, acc) -> { if (dat != null) return acc + dat; else return acc; }, 0L, 10L);
    }

    @Test
    @DisplayName("Realloc shrink then expand then verify")
    public void ReallocShrinkExpandVerify() {
      AddTest(11);
      NewEmptyContainer();
      TestRealloc(Natural.Of(5));
      TestSetAt(10L, Natural.ZERO, false);
      TestSetAt(20L, Natural.Of(1), false);
      TestSetAt(30L, Natural.Of(2), false);
      TestSetAt(40L, Natural.Of(3), false);
      TestSetAt(50L, Natural.Of(4), false);
      TestRealloc(Natural.Of(2));
      TestSize(2, false);
      TestGetFirst(10L, false);
      TestGetLast(20L, false);
      TestRealloc(Natural.Of(4));
      TestSize(4, false);
      TestGetAt(Natural.Of(2), null, false);
    }

    @Test
    @DisplayName("ShiftLeft then ShiftRight restores part of vector")
    public void ShiftLeftThenShiftRightPartialRestore() {
      AddTest(12);
      NewEmptyContainer();
      TestRealloc(Natural.Of(5));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestSetAt(4L, Natural.Of(3), false);
      TestSetAt(5L, Natural.Of(4), false);
      TestShiftLeft(Natural.ZERO, Natural.Of(2));
      // After: [3, 4, 5, null, null]
      TestGetFirst(3L, false);
      TestShiftRight(Natural.ZERO, Natural.Of(2));
      // After: [null, null, 3, 4, 5] — wait, ShiftRight shifts elements right
      TestGetAt(Natural.Of(2), 3L, false);
      TestFoldForward((dat, acc) -> { if (dat != null) return acc + dat; else return acc; }, 0L, 12L);
      TestSize(5, false);
      TestPrintContent("");
    }

    @Test
    @DisplayName("ShiftFirstLeft repeated then set and verify")
    public void ShiftFirstLeftRepeatedThenSet() {
      AddTest(11);
      NewEmptyContainer();
      TestRealloc(Natural.Of(4));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestSetAt(4L, Natural.Of(3), false);
      TestShiftFirstLeft();
      // [2, 3, 4, null]
      TestShiftFirstLeft();
      // [3, 4, null, null]
      TestGetFirst(3L, false);
      TestGetAt(Natural.Of(1), 4L, false);
      TestSetAt(99L, Natural.Of(2), false);
      TestFoldForward((dat, acc) -> { if (dat != null) return acc + dat; else return acc; }, 0L, 106L);
      TestSize(4, false);
    }

    @Test
    @DisplayName("GetNSetAt on all positions then fold")
    public void GetNSetAtAllPositions() {
      AddTest(10);
      NewEmptyContainer();
      TestRealloc(Natural.Of(4));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestSetAt(4L, Natural.Of(3), false);
      TestGetNSetAt(10L, 1L, Natural.ZERO, false);
      TestGetNSetAt(20L, 2L, Natural.Of(1), false);
      TestGetNSetAt(30L, 3L, Natural.Of(2), false);
      TestGetNSetAt(40L, 4L, Natural.Of(3), false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 100L);
      TestFoldBackward((dat, acc) -> acc + dat, 0L, 100L);
    }

    @Test
    @DisplayName("Interleaved shift and swap operations")
    public void InterleavedShiftSwap() {
      AddTest(14);
      NewEmptyContainer();
      TestRealloc(Natural.Of(6));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestSetAt(4L, Natural.Of(3), false);
      TestSetAt(5L, Natural.Of(4), false);
      TestSetAt(6L, Natural.Of(5), false);
      TestSwap(Natural.ZERO, Natural.Of(5), false);
      // [6, 2, 3, 4, 5, 1]
      TestShiftLeft(Natural.Of(2), Natural.Of(2));
      // [6, 2, 5, 1, null, null]
      TestGetAt(Natural.Of(2), 5L, false);
      TestShiftRight(Natural.ZERO, Natural.ONE);
      // [null, 6, 2, 5, 1, null]
      TestGetFirst(null, false);
      TestGetAt(Natural.Of(1), 6L, false);
      TestSwap(Natural.Of(1), Natural.Of(4), false);
      // [null, 1, 2, 5, 6, null]
      TestGetAt(Natural.Of(1), 1L, false);
      TestGetAt(Natural.Of(4), 6L, false);
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
      TestSetAt(100L, Natural.ZERO, false);
      TestSetAt(200L, Natural.Of(1), false);
      TestSetAt(300L, Natural.Of(2), false);
      TestFoldForward((dat, acc) -> acc + dat, 0L, 600L);
      TestGetFirst(100L, false);
      TestGetLast(300L, false);
    }

    @Test
    @DisplayName("ShiftLeft at middle position with large num")
    public void ShiftLeftMiddleLargeNum() {
      AddTest(9);
      NewEmptyContainer();
      TestRealloc(Natural.Of(6));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestSetAt(4L, Natural.Of(3), false);
      TestSetAt(5L, Natural.Of(4), false);
      TestSetAt(6L, Natural.Of(5), false);
      // ShiftLeft(2, 10) should clamp to available
      TestShiftLeft(Natural.Of(2), Natural.Of(10));
      TestGetFirst(1L, false);
      TestGetAt(Natural.Of(1), 2L, false);
      TestSize(6, false);
    }

    @Test
    @DisplayName("ShiftRight at middle position with large num")
    public void ShiftRightMiddleLargeNum() {
      AddTest(9);
      NewEmptyContainer();
      TestRealloc(Natural.Of(6));
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestSetAt(4L, Natural.Of(3), false);
      TestSetAt(5L, Natural.Of(4), false);
      TestSetAt(6L, Natural.Of(5), false);
      // ShiftRight(2, 10) should clamp
      TestShiftRight(Natural.Of(2), Natural.Of(10));
      TestGetFirst(1L, false);
      TestGetAt(Natural.Of(1), 2L, false);
      TestSize(6, false);
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
      TestSetAt(10L, Natural.ZERO, false);
      TestSetAt(20L, Natural.Of(1), false);
      TestSetAt(30L, Natural.Of(2), false);
      TestSetAt(40L, Natural.Of(3), false);
      TestFIterator();
      TestForwardIteration(false, false);
    }

    @Test
    @DisplayName("Backward iterator traverses all elements")
    public void BackwardIteratorTraversal() {
      AddTest(6);
      NewEmptyContainer();
      TestRealloc(Natural.Of(4));
      TestSetAt(10L, Natural.ZERO, false);
      TestSetAt(20L, Natural.Of(1), false);
      TestSetAt(30L, Natural.Of(2), false);
      TestSetAt(40L, Natural.Of(3), false);
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
      TestSetAt(1L, Natural.ZERO, false);
      TestSetAt(2L, Natural.Of(1), false);
      TestSetAt(3L, Natural.Of(2), false);
      TestSetAt(4L, Natural.Of(3), false);
      TestShiftLeft(Natural.ZERO, Natural.Of(2));
      // [3, 4, null, null]
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
      assertThrows(NullPointerException.class, () -> ThisContainer().SetAt(1L, null));
      NewEmptyContainer();
      assertThrows(NullPointerException.class, () -> ThisContainer().SetAt(1L, null));
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
    @DisplayName("Realloc with null throws (no-op, but verify)")
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
  }

}
