package zapsdtest.simpletest.apsd.classes.containers.sequences;

import apsd.classes.containers.sequences.Vector;
import apsd.interfaces.containers.base.TraversableContainer;
import zapsdtest.simpletest.apsd.classes.containers.sequences.generic.XVectorITest;
import zapsdtest.simpletest.apsd.classes.containers.sequences.generic.XVectorSTest;

public class VectorSTest extends XVectorSTest {

  public VectorSTest() {
    System.out.println("**** VectorS ***************************************");
    name = "01.s - VectorS";
  }

  @Override
  public void NewEmptyContainer() { container = new Vector<>(); }
  @Override
  public void NewNonEmptyContainer(TraversableContainer con) { container = new Vector<>(con); }

  @Override
  public Vector<String> GetNewEmptyContainer() { return new Vector<>(); }

  public Vector<String> GetNewNonEmptyContainer(TraversableContainer con) { return new Vector<>(con); }

}
