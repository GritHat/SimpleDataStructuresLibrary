package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.MutableNatural;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.IterableContainer;
import apsd.interfaces.containers.iterators.ForwardIterator;

/** Interface: IterableContainer con supporto alla lettura e ricerca tramite posizione. */
public interface Sequence<Data> extends IterableContainer<Data> { // Must extend IterableContainer

  // GetAt
  default Data GetAt(Natural num) {
    long idx = ExcIfOutOfBound(num);
    ForwardIterator<Data> it = FIterator();
    it.Next(idx);
    if (!it.IsValid()) throw new IndexOutOfBoundsException("Index out of bounds: " + idx + "; Size: " + Size() + "!");
    return it.GetCurrent();
  };

  // GetFirst
  default Data GetFirst() {
    return GetAt(Natural.ZERO);
  };
  // GetLast
  default Data GetLast() {
    if (IsEmpty()) throw new IndexOutOfBoundsException("GetLast: Cannot get last element of an empty sequence!");
    return GetAt(Size().Decrement());
  };

  // Search
  default Natural Search(Data value) {
    /*ForwardIterator<Data> it = FIterator();
    Natural idx = Natural.ZERO;
    while (it.IsValid()) {
      Data currentValue = it.DataNNext();
      if ((currentValue == null && value == null) ||
          (currentValue != null && currentValue.equals(value))) {
        return idx;
      }
      idx = idx.Increment();
    }
    return null;*/
    MutableNatural pos = new MutableNatural(0);
    if (TraverseForward(dat -> {
      boolean found = (dat == null && value == null) || (dat != null && dat.equals(value));
      if (!found) pos.Increment();
      return found;
    })) return pos.ToNatural();
    return null;
  };

  // IsInBound
  default boolean IsInBound(Natural num) {
    if (num == null) throw new NullPointerException("Natural number cannot be null!");
    long idx = num.ToLong();
    return idx < Size().ToLong();
  };

  default long ExcIfOutOfBound(Natural num) {
    if (num == null) throw new NullPointerException("Natural number cannot be null!");
    long idx = num.ToLong();
    if (idx >= Size().ToLong()) throw new IndexOutOfBoundsException("Index out of bounds: " + idx + "; Size: " + Size() + "!");
    return idx;
  }

  // SubSequence
  Sequence<Data> SubSequence(Natural from, Natural to);
}
