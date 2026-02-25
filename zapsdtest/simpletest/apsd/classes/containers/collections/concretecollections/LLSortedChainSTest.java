package zapsdtest.simpletest.apsd.classes.containers.collections.concretecollections;

import apsd.classes.containers.collections.concretecollections.LLSortedChain;
import apsd.interfaces.containers.base.TraversableContainer;
import zapsdtest.simpletest.apsd.classes.containers.collections.concretecollections.generic.XSortedChainITest;
import zapsdtest.simpletest.apsd.classes.containers.collections.concretecollections.generic.XSortedChainSTest;

public class LLSortedChainSTest extends XSortedChainSTest {

  public LLSortedChainSTest() {
    System.out.println("**** LLSortedChainS ********************************");
    name = "08.s - LLSortedChainS";
  }

  @Override
  public void NewEmptyContainer() { container = new LLSortedChain<>(); }
  @Override
  public void NewNonEmptyContainer(TraversableContainer con) { container = new LLSortedChain<>(con); }

  @Override
  public LLSortedChain<String> GetNewEmptyContainer() { return new LLSortedChain<>(); }

  public LLSortedChain<String> GetNewNonEmptyContainer(TraversableContainer con) { return new LLSortedChain<>(con); }

}
