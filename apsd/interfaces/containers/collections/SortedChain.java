package apsd.interfaces.containers.collections;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.IterableContainer;
import apsd.interfaces.containers.iterators.ForwardIterator;
import apsd.interfaces.containers.sequences.SortedSequence;

public interface SortedChain<Data extends Comparable<? super Data>> extends OrderedChain<Data>, SortedSequence<Data> { // Must extend OrderedChain and SortedSequence

  // SearchPredecessor
  default Natural SearchPredecessor(Data dat) {
    if (dat == null) return null;
    long start = 0;
    long end = Size().ToLong() - 1;
    long mid = (start + end) / 2;
    Natural result = null;
    while (start <= end) {
      Data current = this.GetAt(Natural.Of(mid));
      int cmp = dat.compareTo(current);
      if (cmp <= 0) {
        end = mid - 1;
      } else {
        result = Natural.Of(mid);
        start = mid + 1;
      }
      mid = (start + end) / 2;
    }
    return result;
  }

  // SearchSuccessor
  default Natural SearchSuccessor(Data dat) {
    if (dat == null) return null;
    long start = 0;
    long end = Size().ToLong() - 1;
    long mid = (start + end) / 2;
    Natural result = null;
    while (start <= end) {
      Data current = this.GetAt(Natural.Of(mid));
      int cmp = dat.compareTo(current);
      if (cmp < 0) {
        result = Natural.Of(mid);
        end = mid - 1;
      } else {
        start = mid + 1;
      }
      mid = (start + end) / 2;
    }
    return result;
  }

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  // ...
  @Override
  default Natural Search(Data dat) {
    return SortedSequence.super.Search(dat);
  }

  /* ************************************************************************ */
  /* Override specific member functions from Set                              */
  /* ************************************************************************ */

  default void Intersection(SortedChain<Data> chn) {
    if (chn == null || chn == this) return;
    Natural i = Natural.ZERO, j = Natural.ZERO;
    while (i.compareTo(Size()) < 0 && j.compareTo(chn.Size()) < 0) {
      int cmp = GetAt(i).compareTo(chn.GetAt(j));
      if (cmp < 0) {
        RemoveAt(i);
      } else {
        j = j.Increment();
        if (cmp == 0) { i = i.Increment(); }
      }
    }
    while (i.compareTo(Size()) < 0) {
      RemoveAt(i);
    }
  }

  /* ************************************************************************ */
  /* Override specific member functions from OrderedSet                       */
  /* ************************************************************************ */

  // ...
  @Override
  default Data Min() {
    if (Size().compareTo(Natural.ZERO) == 0) return null;
    return GetFirst();
  }

  @Override
  default Data Max() {
    if (Size().compareTo(Natural.ZERO) == 0) return null;
    return GetAt(Size().Decrement());
  }

  @Override
  default void RemoveMin() {
    if (Size().compareTo(Natural.ZERO) == 0) return;
    RemoveAt(Natural.ZERO);
  }

  @Override
  default void RemoveMax() {
    if (Size().compareTo(Natural.ZERO) == 0) return;
    RemoveAt(Size().Decrement()); 
  }

  @Override
  default Data MinNRemove() {
    Data min = Min();
    RemoveMin();
    return min;
  }

  @Override
  default Data MaxNRemove() {
    Data max = Max();
    RemoveMax();
    return max;
  }

  @Override
  default Data Predecessor(Data dat) {
    Natural pos = SearchPredecessor(dat);
    if (pos == null) {
      return null;
    } else {
      return GetAt(pos);
    }
  }

  @Override
  default Data Successor(Data dat) {
    Natural pos = SearchSuccessor(dat);
    if (pos == null) {
      return null;
    } else {
      return GetAt(pos);
    }
  }

  @Override
  default void RemovePredecessor(Data dat) {
    PredecessorNRemove(dat);
  }

  @Override 
  default void RemoveSuccessor(Data dat) {
    SuccessorNRemove(dat);
  }

  @Override
  default Data PredecessorNRemove(Data dat) {
    Natural pos = SearchPredecessor(dat);
    if (pos == null) {
      return null;
    } else {
      Data predecessor = GetAt(pos);
      RemoveAt(pos);
      return predecessor;
    }
  }

  @Override
  default Data SuccessorNRemove(Data dat) {
    Natural pos = SearchSuccessor(dat);
    if (pos == null) {
      return null;
    } else {
      Data successor = GetAt(pos);
      RemoveAt(pos);
      return successor;
    }
  }
}
