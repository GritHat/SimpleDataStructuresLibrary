package zapsdtest.simpletest.apsd.classes.containers.collections.abstractcollections;

import apsd.classes.containers.collections.abstractcollections.WOrderedSet;
import apsd.interfaces.containers.base.TraversableContainer;
import zapsdtest.simpletest.apsd.classes.containers.collections.abstractcollections.generic.WOrderedSetSTest;

public class VOrderedSetSTest extends WOrderedSetSTest {

  public VOrderedSetSTest() {
    System.out.println("**** VOrderedSetS **********************************");
    name = "15.s - VOrderedSetS";
  }

  @Override
  public void NewEmptyContainer() { container = new WOrderedSet<String>(); }

  @Override
  public void NewNonEmptyContainer(TraversableContainer con) { container = new WOrderedSet<String>(con); }

  @Override
  public WOrderedSet<String> GetNewEmptyContainer() { return new WOrderedSet<String>(); }

  public WOrderedSet<String> GetNewNonEmptyContainer(TraversableContainer con) { return new WOrderedSet<String>(con); }

}
