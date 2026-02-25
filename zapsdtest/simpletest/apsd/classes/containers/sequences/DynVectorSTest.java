package zapsdtest.simpletest.apsd.classes.containers.sequences;

import apsd.classes.containers.sequences.DynVector;
import apsd.interfaces.containers.base.TraversableContainer;
import zapsdtest.simpletest.apsd.classes.containers.sequences.generic.XDynVectorSTest;

public class DynVectorSTest extends XDynVectorSTest {

  public DynVectorSTest() {
    System.out.println("**** DynVectorS ************************************");
    name = "03.s - DynVectorS";
  }

  @Override
  public void NewEmptyContainer() { container = new DynVector<>(); }
  @Override
  public void NewNonEmptyContainer(TraversableContainer con) { container = new DynVector<>(con); }

  @Override
  public DynVector<String> GetNewEmptyContainer() { return new DynVector<>(); }

  public DynVector<String> GetNewNonEmptyContainer(TraversableContainer con) { return new DynVector<>(con); }

}
