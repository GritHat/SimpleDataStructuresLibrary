package apsd.classes.containers.collections.abstractcollections.bases;

import apsd.classes.containers.collections.abstractcollections.WSet;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.collections.Chain;
import apsd.interfaces.containers.collections.Set;
import apsd.interfaces.containers.iterators.BackwardIterator;
import apsd.interfaces.containers.iterators.ForwardIterator;
import apsd.interfaces.traits.Predicate;

/** Object: Abstract wrapper set base implementation via chain. */
abstract public class WSetBase<Data, Chn extends Chain<Data>> implements Set<Data> { // Must implement Set; Chn must extend Chain

  protected Chn chn;

  protected WSetBase() {
    ChainAlloc();
  }

  protected abstract void ChainAlloc();

  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */

  // ...
  @Override
  public Natural Size() {
    return chn.Size();
  }

  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */

  // ...
  @Override
  public void Clear() {
    chn.Clear();
  }

  /* ************************************************************************ */
  /* Override specific member functions from InsertableContainer              */
  /* ************************************************************************ */

  // ...
  @Override
  public boolean Insert(Data dat) {
    if (dat == null) return false;
    return chn.InsertIfAbsent(dat);
  }

  /* ************************************************************************ */
  /* Override specific member functions from RemovableContainer               */
  /* ************************************************************************ */

  // ...
  @Override
  public boolean Remove(Data dat) {
    if (dat == null) return false;
    return chn.Remove(dat);
  }

  /* ************************************************************************ */
  /* Override specific member functions from IterableContainer                */
  /* ************************************************************************ */

  // ...
  @Override
  public ForwardIterator<Data> FIterator() {
    return chn.FIterator();
  }

  @Override
  public BackwardIterator<Data> BIterator() {
    return chn.BIterator();
  }

  /* ************************************************************************ */
  /* Override specific member functions from Collection                       */
  /* ************************************************************************ */

  // ...
  @Override
  public boolean Filter(Predicate<Data> pred) {
    return chn.Filter(pred);
  }

  /* ************************************************************************ */
  /* Override specific member functions from Set                              */
  /* ************************************************************************ */

  // ...
  @Override
  public void Intersection(Set<Data> set) {
    if (set == this) return;
    if (set == null || set.Size().ToLong() == 0L) {
      Clear();
      return;
    }
    ForwardIterator<Data> itr = this.FIterator();
    while (itr.IsValid()) {
      Data dat = itr.GetCurrent();
      if (set.Exists(dat)) {
        itr.Next();
      } else {
        Remove(dat);
        itr.Reset();
      }
    }
    
  }

  public Set<Data> SubSet(Predicate<Data> condition) {
    Chain<Data> subChn = (Chain<Data>) chn.SubSequence(null, null);
    TraverseForward(dat -> {
      if (condition.Apply(dat)) subChn.Insert(dat);
      return false;
    });
    return new WSet<>(subChn);
  }

}
