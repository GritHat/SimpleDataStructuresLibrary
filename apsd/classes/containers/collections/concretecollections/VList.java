package apsd.classes.containers.collections.concretecollections;

import apsd.interfaces.containers.sequences.DynVector;

import apsd.classes.containers.collections.concretecollections.bases.VChainBase;
import apsd.classes.utilities.MutableNatural;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.List;
import apsd.interfaces.containers.iterators.MutableBackwardIterator;
import apsd.interfaces.containers.iterators.MutableForwardIterator;
import apsd.interfaces.containers.sequences.MutableSequence;

/** Object: Concrete list implementation on (dynamic circular) vector. */
public class VList<Data> extends VChainBase<Data> implements List<Data> { // Must extend VChainBase and implement List

  public VList() {}

  public VList(TraversableContainer<Data> con) {
    if (con == null) return;
    MutableNatural idx = new MutableNatural(0L);
    con.TraverseForward(dat -> {
      if (dat != null) vec.InsertAt(dat, idx.GetNIncrement());
      return false;
    });
  }
  
  protected VList(DynVector<Data> vec) {
    if (vec == null) return;
    MutableNatural idx = new MutableNatural(0L);
    vec.TraverseForward(dat -> {
      if (dat != null) this.vec.InsertAt(dat, idx.GetNIncrement());
      return false;
    });
  }

  // NewChain
  @Override
  protected VChainBase<Data> NewChain(DynVector<Data> vec) {
    if (vec == null) return null;
    return new VList<Data>(vec);
  }

  /* ************************************************************************ */
  /* Override specific member functions from MutableIterableContainer         */
  /* ************************************************************************ */

  // ...
  @Override
  public MutableForwardIterator<Data> FIterator() {
    return vec.FIterator();
  }

  @Override
  public MutableBackwardIterator<Data> BIterator() {
    return vec.BIterator();
  }
    

  /* ************************************************************************ */
  /* Override specific member functions from MutableSequence                  */
  /* ************************************************************************ */

  // ...
  @Override
  public void SetAt(Data dat, Natural idx) {
    if (dat == null) return;
    vec.SetAt(dat, idx);
  }

  @Override
  public MutableSequence<Data> SubSequence(Natural startidx, Natural endidx) {
    return (MutableSequence<Data>) super.SubSequence(startidx, endidx);
  }

  /* ************************************************************************ */
  /* Override specific member functions from InsertableAtSequence             */
  /* ************************************************************************ */

  // ...
  @Override
  public void InsertAt(Data dat, Natural idx) {
    if (dat == null) return;
    vec.InsertAt(dat, idx);
  }

  public List<Data> Reverse() {
    List<Data> reversed = new VList<>();
    TraverseBackward(dat -> {
      reversed.InsertLast(dat);
      return false;
    });
    return reversed;
  }

}
