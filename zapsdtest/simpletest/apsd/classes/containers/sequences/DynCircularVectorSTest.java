package zapsdtest.simpletest.apsd.classes.containers.sequences;

import apsd.classes.containers.sequences.DynCircularVector;
import apsd.interfaces.containers.base.TraversableContainer;
import zapsdtest.simpletest.apsd.classes.containers.sequences.generic.XDynVectorSTest;

public class DynCircularVectorSTest extends XDynVectorSTest {

  public DynCircularVectorSTest() {
    System.out.println("**** DynCircularVectorS ****************************");
    name = "04.s - DynCircularVectorS";
  }

  @Override
  public void NewEmptyContainer() { container = new DynCircularVector<>(); }
  @Override
  public void NewNonEmptyContainer(TraversableContainer con) { container = new DynCircularVector<>(con); }

  @Override
  public DynCircularVector<String> GetNewEmptyContainer() { return new DynCircularVector<>(); }

  public DynCircularVector<String> GetNewNonEmptyContainer(TraversableContainer con) { return new DynCircularVector<>(con); }

}
