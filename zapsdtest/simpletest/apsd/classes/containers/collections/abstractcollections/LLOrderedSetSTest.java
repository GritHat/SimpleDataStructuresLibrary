package zapsdtest.simpletest.apsd.classes.containers.collections.abstractcollections;

import apsd.classes.containers.collections.abstractcollections.WOrderedSet;
import apsd.classes.containers.collections.concretecollections.LLSortedChain;
import apsd.interfaces.containers.base.TraversableContainer;
import zapsdtest.simpletest.apsd.classes.containers.collections.abstractcollections.generic.WOrderedSetSTest;

public class LLOrderedSetSTest extends WOrderedSetSTest {

  public LLOrderedSetSTest() {
    System.out.println("**** LLOrderedSetS *********************************");
    name = "16.s - LLOrderedSetS";
  }

  @Override
  public void NewEmptyContainer() { container = new WOrderedSet<String>(new LLSortedChain<>()); }

  @Override
  public void NewNonEmptyContainer(TraversableContainer con) { container = new WOrderedSet<String>(new LLSortedChain<>(), con); }

  @Override
  public WOrderedSet<String> GetNewEmptyContainer() { return new WOrderedSet<String>(new LLSortedChain<>()); }

  public WOrderedSet<String> GetNewNonEmptyContainer(TraversableContainer con) { return new WOrderedSet<String>(new LLSortedChain<>(), con); }

}
