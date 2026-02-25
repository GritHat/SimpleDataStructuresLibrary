package zapsdtest.simpletest.apsd.classes.containers.collections.abstractcollections;

import apsd.classes.containers.collections.abstractcollections.WSet;
import apsd.classes.containers.collections.concretecollections.LLList;
import apsd.classes.containers.collections.concretecollections.LLSortedChain;
import apsd.interfaces.containers.base.TraversableContainer;
import zapsdtest.simpletest.apsd.classes.containers.collections.abstractcollections.generic.WSetSTest;

public class LLSetSTest extends WSetSTest {

  public LLSetSTest() {
    System.out.println("**** LLSetS ****************************************");
    name = "14.s - LLSetS";
  }

  @Override
  public void NewEmptyContainer() { container = new WSet<>(new LLList<>()); }

  @Override
  public void NewNonEmptyContainer(TraversableContainer con) { container = new WSet<String>(new LLSortedChain<>(), con); }

  @Override
  public WSet<String> GetNewEmptyContainer() { return new WSet<String>(new LLSortedChain<>()); }

  public WSet<String> GetNewNonEmptyContainer(TraversableContainer con) { return new WSet<String>(new LLSortedChain<>(), con); }

}
