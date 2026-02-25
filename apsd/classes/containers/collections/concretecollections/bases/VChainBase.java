package apsd.classes.containers.collections.concretecollections.bases;

import apsd.classes.containers.sequences.DynCircularVector;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.collections.Chain;
import apsd.interfaces.containers.collections.Set;
import apsd.interfaces.containers.iterators.BackwardIterator;
import apsd.interfaces.containers.iterators.ForwardIterator;
import apsd.interfaces.containers.sequences.DynVector;
import apsd.interfaces.containers.sequences.Sequence;
import apsd.interfaces.traits.Predicate;

/** Object: Abstract list base implementation on (dynamic circular) vector. */
abstract public class VChainBase<Data> implements Chain<Data> { // Must implement Chain

  protected DynVector<Data> vec;

  // VChainBase
  protected VChainBase() {
    this.vec = new DynCircularVector<>();
  }

  // NewChain
  protected abstract VChainBase<Data> NewChain(DynVector<Data> vec);

  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */

  // ...
  @Override
  public Natural Size() {
    return vec.Size();
  }

  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */

  // ...
  @Override
  public void Clear() {
    vec.Clear();
  }

  /* ************************************************************************ */
  /* Override specific member functions from RemovableContainer               */
  /* ************************************************************************ */

  // ...
  @Override
  public boolean Remove(Data dat) {
    if (dat == null) return false;
    ForwardIterator<Data> fit = FIterator();
    long index = 0L;
    while (fit.IsValid()) {
      Data cur = fit.GetCurrent();
      if (dat.equals(cur)) {
        vec.RemoveAt(Natural.Of(index));
        return true;
      }
      fit.Next();
      ++index;
    }
    return false;
  }

  /* ************************************************************************ */
  /* Override specific member functions from IterableContainer                */
  /* ************************************************************************ */

  // ...
  @Override
  public ForwardIterator<Data> FIterator() {
    return vec.FIterator();
  }

  @Override
  public BackwardIterator<Data> BIterator() {
    return vec.BIterator();
  }

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  // ...
  @Override
  public Data GetAt(Natural index) {
    return vec.GetAt(index);
  }

  @Override
  public Sequence<Data> SubSequence(Natural start, Natural end) {
    VChainBase<Data> subchn = NewChain(vec.SubVector(start, end));
    return subchn;
  }

  /* ************************************************************************ */
  /* Override specific member functions from RemovableAtSequence              */
  /* ************************************************************************ */

  // ...
  @Override
  public Data AtNRemove(Natural pos) {
    return vec.AtNRemove(pos);
  }

  /* ************************************************************************ */
  /* Override specific member functions from Collection                       */
  /* ************************************************************************ */

  // ...
  @Override
  public boolean Filter(Predicate<Data> pred) {
    if (pred == null) return false;
    boolean removed = false;
    ForwardIterator<Data> fit = FIterator();
    long index = 0L;
    while (fit.IsValid()) {
      Data cur = fit.GetCurrent();
      if (!pred.Apply(cur)) {
        RemoveAt(Natural.Of(index));
        removed = true;
        fit.Reset();
        index = 0L;
      } else {
        fit.Next();
        ++index;
      }
    }
    return removed;
  }

  public Set<Data> SubSet(Predicate<Data> condition) {
    DynVector<Data> subvec = vec.SubVector(null, null);
    TraverseForward(dat -> {
      if (condition.Apply(dat)) subvec.InsertLast(dat);
      return false;
    });
    return (Set<Data>) NewChain(subvec);
  }

}
