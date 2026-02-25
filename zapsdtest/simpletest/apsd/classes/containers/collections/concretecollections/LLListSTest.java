package zapsdtest.simpletest.apsd.classes.containers.collections.concretecollections;

import apsd.classes.containers.collections.concretecollections.LLList;
import apsd.interfaces.containers.base.TraversableContainer;
import zapsdtest.simpletest.apsd.classes.containers.collections.concretecollections.generic.XListITest;
import zapsdtest.simpletest.apsd.classes.containers.collections.concretecollections.generic.XListSTest;

public class LLListSTest extends XListSTest {

  public LLListSTest() {
    System.out.println("**** LLListS ***************************************");
    name = "06.s - LLListS";
  }

  @Override
  public void NewEmptyContainer() { container = new LLList<>(); }
  @Override
  public void NewNonEmptyContainer(TraversableContainer con) { container = new LLList<>(con); }

  @Override
  public LLList<String> GetNewEmptyContainer() { return new LLList<>(); }

  public LLList<String> GetNewNonEmptyContainer(TraversableContainer con) { return new LLList<>(con); }

}
