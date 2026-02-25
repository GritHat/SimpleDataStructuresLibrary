package apsd.interfaces.containers.collections;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.sequences.InsertableAtSequence;
import apsd.interfaces.containers.sequences.MutableSequence;

public interface List<Data> extends MutableSequence<Data>, InsertableAtSequence<Data>, Chain<Data> { // Must extend MutableSequence, InsertableAtSequence, and Chain

  // SubList
  default List<Data> SubList(Natural from, Natural to) {
    return (List<Data>) this.SubSequence(from, to);
  }

  /* ************************************************************************ */
  /* Override specific member functions from MutableSequence                  */
  /* ************************************************************************ */

  @Override
  default boolean Insert(Data dat) {
    if (dat == null) return false;
    long oldSize = Size().ToLong();
    InsertAt(dat, Natural.ZERO);
    return Size().ToLong() > oldSize;
  }
  // ...

  List<Data> Reverse();
}
