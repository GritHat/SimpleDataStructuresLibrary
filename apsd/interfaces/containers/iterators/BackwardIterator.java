package apsd.interfaces.containers.iterators;

import apsd.classes.utilities.Natural;
import apsd.classes.utilities.MutableNatural;

/** Interface: Iteratore all'indietro. */
public interface BackwardIterator<Data> extends Iterator<Data> { // Must extend Iterator

  // Prev
  default void Prev() {
    if (IsValid()) {
      DataNPrev();
    }
  };

  default void Prev(Natural n) {
    for (MutableNatural i = new MutableNatural(0); i.ToNatural().LessThan(n); i.Increment()) {
      Prev();
    }
  };

  default void Prev(long n) {
    for (long i = 0; i < n; i++) {
      Prev();
    }
  };

  // DataNPrev
  Data DataNPrev();

  // ForEachBackward
  default boolean ForEachBackward(apsd.interfaces.traits.Predicate<Data> fun) {
    if (fun != null) {
      while (IsValid()) {
        if (fun.Apply(DataNPrev())) { return true; }
      }
    }
    return false;
  }

}
