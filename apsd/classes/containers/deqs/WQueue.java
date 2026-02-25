package apsd.classes.containers.deqs;

import apsd.classes.containers.collections.concretecollections.VList;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.List;
import apsd.interfaces.containers.deqs.Queue;

/** Object: Wrapper queue implementation. */
public class WQueue<Data> implements Queue<Data> {

  protected final List<Data> lst;

  public WQueue() {
    this.lst = new VList<Data>();
  }

  public WQueue(List<Data> lst) {
    if (lst != null) this.lst = lst;
    else this.lst = new VList<Data>();
  }

  public WQueue(TraversableContainer<Data> con) {
    this.lst = new VList<Data>(con);
  }

  public WQueue(List<Data> lst, TraversableContainer<Data> con) {
    this(lst);
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
  /* Override specific member functions from Queue                            */
  /* ************************************************************************ */

  @Override
  public void Enqueue(Data dat) {
    lst.InsertLast(dat);
  }

  @Override
  public Data Head() {
    if (IsEmpty()) return null;
    return lst.GetAt(Natural.ZERO);
  }

  @Override
  public void Dequeue() {
    if (IsEmpty()) return;
    lst.RemoveAt(Natural.ZERO);
  }

}
