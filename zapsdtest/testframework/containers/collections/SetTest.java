package zapsdtest.testframework.containers.collections;

import apsd.interfaces.containers.collections.Set;

import static org.junit.jupiter.api.Assertions.*;

import apsd.classes.containers.collections.concretecollections.LLList;
import apsd.interfaces.traits.Predicate;

public interface SetTest<Data, Con extends Set<Data>> extends CollectionTest<Data, Con> {

  default void TestUnion(Set<Data> otherSet) {
    BeginTest("Union");
    long initialSize = ThisContainer().Size().ToLong();
    ThisContainer().Union(otherSet);
    assertTrue(ThisContainer().Size().ToLong() >= initialSize,
    "Size should not decrease after Union");
    if (otherSet != null) {
      assertTrue(otherSet.FoldForward((dat, acc) -> ThisContainer().Exists(dat) && acc, true),
      "The new set should contain all elements of the input set after Union");
    }
    EndTest();
  }

  default void TestDifference(Set<Data> otherSet) {
    BeginTest("Difference");
    long initialSize = ThisContainer().Size().ToLong();
    LLList<Data> copy = new LLList<>(ThisContainer());
    LLList<Data> dupl = new LLList<>();
    while (!copy.IsEmpty()) {
      Data dat = copy.FirstNRemove();
      if (copy.Exists(dat)) dupl.InsertIfAbsent(dat);
    }
    ThisContainer().Difference(otherSet);
    assertTrue(ThisContainer().Size().ToLong() <= initialSize,
    "Size should not increase after Difference");
    if (otherSet != null) {
      assertFalse(otherSet.FoldForward((dat, acc) -> ThisContainer().Exists(dat) && !dupl.Exists(dat) || acc, false),
      "The new set should not contain any element of the input set after Differerence");
    }
    EndTest();
  }

  default void TestIntersection(Set<Data> otherSet) {
    BeginTest("Intersection");
    long initialSize = ThisContainer().Size().ToLong();
    ThisContainer().Intersection(otherSet);
    assertTrue(ThisContainer().Size().ToLong() <= initialSize,
    "Size should not increase after Intersection");
    if (otherSet != null) {
      assertTrue(ThisContainer().FoldForward((dat, acc) -> otherSet.Exists(dat) && acc, true),
      "The input set should contain all elements of the new set after Intersection");
    }
    EndTest();
  }

  default void TestSubSet(Predicate<Data> fun, Set<Data> expectedset) {
    BeginTest("SubSet");
    long initialSize = ThisContainer().Size().ToLong();
    Set<Data> set = ThisContainer().SubSet(fun);
    System.out.println("Original set:");
    ThisContainer().TraverseForward(dat -> {
      System.out.print(dat + " ");
      return false;    });
    System.out.println("\nSubset:");
    set.TraverseForward(dat -> {
      System.out.print(dat + " ");      return false;    });
    System.out.println("\nExpected subset:");
    expectedset.TraverseForward(dat -> {
      System.out.print(dat + " ");      return false;    });
    System.out.println();

    assertNotNull(set,
    "Subset should not return null");
    assertTrue(set.Size().ToLong() <= initialSize,
    "The size of the result of SubSet should not be greater than the original set");
    assertTrue(set.IsEqual(expectedset),
    "The two subsets should be equal");
    EndTest();
  }

}
