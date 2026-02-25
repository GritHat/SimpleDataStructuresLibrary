package zapsdtest.simpletest.apsd.classes.containers.deqs;

import apsd.classes.containers.deqs.WStack;
import apsd.interfaces.containers.base.TraversableContainer;
import zapsdtest.simpletest.apsd.classes.containers.deqs.generic.WStackITest;

public class VStackITest extends WStackITest {

  public VStackITest() {
    System.out.println("**** VStackI ***************************************");
    name = "09 - VStackI";
  }

  @Override
  public void NewEmptyContainer() { container = new WStack<>(); }
  @Override
  public void NewNonEmptyContainer(TraversableContainer con) { container = new WStack<>(con); }

  @Override
  public WStack<Long> GetNewEmptyContainer() { return new WStack<>(); }

  public WStack<Long> GetNewNonEmptyContainer(TraversableContainer con) { return new WStack<>(con); }

}
