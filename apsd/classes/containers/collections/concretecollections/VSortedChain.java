package apsd.classes.containers.collections.concretecollections;

import apsd.classes.containers.collections.concretecollections.bases.VChainBase;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.SortedChain;
import apsd.interfaces.containers.sequences.DynVector;

/** Object: Concrete set implementation via (dynamic circular) vector. */
public class VSortedChain<Data extends Comparable<? super Data>> extends VChainBase<Data> implements SortedChain<Data> { // Must extend VChainBase and implements SortedChain

  public VSortedChain() {}

  public VSortedChain(VSortedChain<Data> chn) {
    if (chn == null) return;
    InsertAll(chn);
  }

  public VSortedChain(TraversableContainer<Data> con) {
    if (con == null) return;
    con.TraverseForward(dat -> {
      Insert(dat);
      return false;
    });
  }

  protected VSortedChain(DynVector<Data> vec) {
    if (vec == null) return;
    this.vec = vec.SubVector(null, null);
    vec.TraverseForward(dat -> {
      Insert(dat);
      return false;
    });
  }

  // NewChain
  @Override
  protected VChainBase<Data> NewChain(DynVector<Data> vec) {
    if (vec == null) return null;
    return new VSortedChain<Data>(vec);
  }
  

  /* ************************************************************************ */
  /* Override specific member functions from InsertableContainer              */
  /* ************************************************************************ */

  // ...
  @Override
  public boolean Insert(Data dat) {
    if (dat == null) return false;
    long start = 0L;
    long end = Size().ToLong() - 1L;
    long mid = end / 2L;
    long candidateIdx = Size().ToLong();
    while (start <= end) {
      Data midData = vec.GetAt(Natural.Of(mid));
      int cmp = dat.compareTo(midData);
      if (cmp == 0) {
        candidateIdx = mid;
        break;
      } else if (cmp < 0) {
        candidateIdx = mid;
        end = mid - 1L;
      } else {
        start = mid + 1L;
      }
      mid = start + (end - start) / 2L;
    }
    vec.InsertAt(dat, Natural.Of(candidateIdx));
    return true;
  }

  /* ************************************************************************ */
  /* Override specific member functions from Chain                            */
  /* ************************************************************************ */

  // ...
  @Override
  public boolean InsertIfAbsent(Data dat) {
    if (dat == null) return false;
    long start = 0L;
    long end = Size().ToLong() - 1L;
    long mid = end / 2L;
    long candidateIdx = Size().ToLong();
    while (start <= end) {
      Data midData = vec.GetAt(Natural.Of(mid));
      int cmp = dat.compareTo(midData);
      if (cmp == 0) {
        return false;
      } else if (cmp < 0) {
        candidateIdx = mid;
        end = mid - 1L;
      } else {
        start = mid + 1L;
      }
      mid = start + (end - start) / 2L;
    }
    vec.InsertAt(dat, Natural.Of(candidateIdx));
    return true;
  }

}
