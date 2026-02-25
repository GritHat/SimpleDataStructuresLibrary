package apsd.classes.containers.deqs;

import apsd.classes.containers.collections.concretecollections.VList;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.List;
import apsd.interfaces.containers.deqs.Stack;

/** Object: Wrapper stack implementation. */
public class WStack<Data> implements Stack<Data> {

  protected final List<Data> lst;

  public WStack() {
    this.lst = new VList<Data>();
  }

  public WStack(List<Data> lst) {
    if (lst != null) this.lst = lst;
    else this.lst = new VList<Data>();
  }

  public WStack(TraversableContainer<Data> con) {
    this.lst = new VList<Data>(con);
  }

  public WStack(List<Data> lst, TraversableContainer<Data> con) {
    if (lst != null) this.lst = (List<Data>) lst.SubSequence(null, null);
    else this.lst = new VList<Data>();
    if (con == null) return;
    con.TraverseForward(dat -> {
      this.lst.InsertLast(dat);
      return false;
    });
  }


  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */

  @Override
  public Natural Size() {
    return lst.Size();
  }

  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */

  @Override
  public void Clear() {
    lst.Clear();
  }

  /* ************************************************************************ */
  /* Override specific member functions from Stack                            */
  /* ************************************************************************ */

  @Override
  public void Push(Data dat) {
    lst.InsertFirst(dat);
  }

  @Override
  public void Pop() {
    if (lst.Size().ToLong() == 0L) return;
    lst.RemoveFirst();
  }

  @Override
  public Data Top() {
    if (lst.Size().ToLong() == 0L) return null;
    return lst.GetAt(Natural.ZERO);
  }
}
