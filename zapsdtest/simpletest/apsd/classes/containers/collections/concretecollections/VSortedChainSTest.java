package zapsdtest.simpletest.apsd.classes.containers.collections.concretecollections;

import apsd.classes.containers.collections.concretecollections.VSortedChain;
import apsd.interfaces.containers.base.TraversableContainer;
import zapsdtest.simpletest.apsd.classes.containers.collections.concretecollections.generic.XSortedChainITest;
import zapsdtest.simpletest.apsd.classes.containers.collections.concretecollections.generic.XSortedChainSTest;

public class VSortedChainSTest extends XSortedChainSTest {

  public VSortedChainSTest() {
    System.out.println("**** VSortedChainS *********************************");
    name = "07.s - VSortedChainS";
  }

  @Override
  public void NewEmptyContainer() { container = new VSortedChain<>(); }
  @Override
  public void NewNonEmptyContainer(TraversableContainer con) { container = new VSortedChain<>(con); }

  @Override
  public VSortedChain<String> GetNewEmptyContainer() { return new VSortedChain<>(); }

  public VSortedChain<String> GetNewNonEmptyContainer(TraversableContainer con) { return new VSortedChain<>(con); }

}
