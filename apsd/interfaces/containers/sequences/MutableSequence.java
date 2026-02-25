package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.MutableIterableContainer;
import apsd.interfaces.containers.iterators.MutableForwardIterator;

/** Interface: Sequence & MutableIterableContainer con supporto alla scrittura tramite posizione. */
public interface MutableSequence<Data> extends Sequence<Data>, MutableIterableContainer<Data> { // Must extend Sequence and MutableIterableContainer

  // SetAt
  default void SetAt(Data data, Natural num) {
    GetNSetAt(data, num);
  };

  // GetNSetAt
  default Data GetNSetAt(Data data, Natural num) {
    long idx = ExcIfOutOfBound(num);
    MutableForwardIterator<Data> it = FIterator();
    it.Next(idx);
    Data oldData = it.GetCurrent();
    it.SetCurrent(data);
    return oldData;
  };

  // SetFirst
  default void SetFirst(Data data) {
    SetAt(data, Natural.ZERO);
  };

  // GetNSetFirst
  default Data GetNSetFirst(Data data) {
    return GetNSetAt(data, Natural.ZERO);
  };

  // SetLast
  default void SetLast(Data data) {
    if (this.Size().ToLong() == 0L) throw new IndexOutOfBoundsException("SetLast: Cannot set last element of an empty sequence!");
    SetAt(data, Natural.Of(this.Size().ToLong() - 1));
  };

  // GetNSetLast
  default Data GetNSetLast(Data data) {
    if (this.Size().ToLong() == 0L) throw new IndexOutOfBoundsException("SetLast: Cannot set last element of an empty sequence!");
    return GetNSetAt(data, Natural.Of(this.Size().ToLong() - 1));
  };

  // Swap
  default void Swap(Natural num1, Natural num2) {
    long idx1 = ExcIfOutOfBound(num1);
    long idx2 = ExcIfOutOfBound(num2);
    if (idx1 == idx2) return;
    Data data1 = GetAt(num1);
    Data data2 = GetAt(num2);
    SetAt(data2, num1);
    SetAt(data1, num2);
  };

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  @Override
  MutableSequence<Data> SubSequence(Natural from, Natural to);
  // ...

}
