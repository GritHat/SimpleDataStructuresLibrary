package apsd.interfaces.containers.collections;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.IterableContainer;
import apsd.interfaces.containers.iterators.ForwardIterator;
import apsd.interfaces.containers.sequences.RemovableAtSequence;

public interface Chain<Data> extends Set<Data>, RemovableAtSequence<Data> { // Must extend RemovableAtSequence

  // InsertIfAbsent
  default boolean InsertIfAbsent(Data dat) {
    return !Exists(dat) && Insert(dat);
  }

  // RemoveOccurrences
  default void RemoveOccurrences(Data dat) {
    Filter(cur -> !cur.equals(dat));
  }

  // SubChain
  default Chain<Data> SubChain(Natural from, Natural to) {
    return (Chain<Data>) SubSequence(from, to);
  }

  //@Override
  //default boolean IsEqual(IterableContainer<Data> other) {
  //  return Set.super.IsEqual(other);
  //}
  
  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  @Override
  default Natural Search(Data dat) {
    if (dat  == null) return null;
    return RemovableAtSequence.super.Search(dat);
  }
  // ...


  @Override
  default boolean IsEqual(IterableContainer<Data> other) {
    if (other == null) return false;
    if (other == this) return true;
    if (this.Size().compareTo(other.Size()) != 0) return false;
    ForwardIterator<Data> itThis = this.FIterator();
    ForwardIterator<Data> itOther = other.FIterator();
    while (itThis.IsValid() && itOther.IsValid()) {
      Data dataThis = itThis.DataNNext();
      Data dataOther = itOther.DataNNext();
      if ((dataThis == null && dataOther != null) ||
          (dataThis != null && !dataThis.equals(dataOther)))
        return false;
    }
    return !(itThis.IsValid() || itOther.IsValid());
  }
}
