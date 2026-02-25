package zapsdtest.simpletest.apsd.classes.containers.collections.concretecollections;

import apsd.classes.containers.collections.concretecollections.VList;
import apsd.interfaces.containers.base.TraversableContainer;
import zapsdtest.simpletest.apsd.classes.containers.collections.concretecollections.generic.XListITest;
import zapsdtest.simpletest.apsd.classes.containers.collections.concretecollections.generic.XListSTest;

public class VListSTest extends XListSTest {

  public VListSTest() {
    System.out.println("**** VListS ****************************************");
    name = "05.s - VListS";
  }

  @Override
  public void NewEmptyContainer() { container = new VList<>(); }
  @Override
  public void NewNonEmptyContainer(TraversableContainer con) { container = new VList<>(con); }

  @Override
  public VList<String> GetNewEmptyContainer() { return new VList<>(); }

  public VList<String> GetNewNonEmptyContainer(TraversableContainer con) { return new VList<>(con); }

}
