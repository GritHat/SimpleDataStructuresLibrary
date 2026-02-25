package zapsdtest.simpletest.apsd.classes.containers.collections.abstractcollections;

import apsd.classes.containers.collections.abstractcollections.WSet;
import apsd.interfaces.containers.base.TraversableContainer;
import zapsdtest.simpletest.apsd.classes.containers.collections.abstractcollections.generic.WSetSTest;

public class VSetSTest extends WSetSTest {

  public VSetSTest() {
    System.out.println("**** VSetS *****************************************");
    name = "13.s - VSetS";
  }

  @Override
  public void NewEmptyContainer() { container = new WSet<>(); }
  @Override
  public void NewNonEmptyContainer(TraversableContainer con) { container = new WSet<>(con); }

  @Override
  public WSet<String> GetNewEmptyContainer() { return new WSet<>(); }

  public WSet<String> GetNewNonEmptyContainer(TraversableContainer con) { return new WSet<>(con); }

}
