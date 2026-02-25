package zapsdtest.simpletest.apsd.classes.containers.sequences;

import apsd.classes.containers.sequences.CircularVector;
import apsd.interfaces.containers.base.TraversableContainer;
import zapsdtest.simpletest.apsd.classes.containers.sequences.generic.XVectorITest;
import zapsdtest.simpletest.apsd.classes.containers.sequences.generic.XVectorSTest;

public class CircularVectorSTest extends XVectorSTest {

  public CircularVectorSTest() {
    System.out.println("**** CircularVectorS *******************************");
    name = "02.s - CircularVectorS";
  }

  @Override
  public void NewEmptyContainer() { container = new CircularVector<>(); }
  @Override
  public void NewNonEmptyContainer(TraversableContainer con) { container = new CircularVector<>(con); }

  @Override
  public CircularVector<String> GetNewEmptyContainer() { return new CircularVector<>(); }

  public CircularVector<String> GetNewNonEmptyContainer(TraversableContainer con) { return new CircularVector<>(con); }

}
