package apsd.interfaces.containers.sequences;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.ResizableContainer;

public interface DynVector<Data> extends ResizableContainer, InsertableAtSequence<Data>, RemovableAtSequence<Data>, Vector<Data> { // Must extend ResizableContainer, InsertableAtSequence, RemovableAtSequence, and Vector

  /* ************************************************************************ */
  /* Override specific member functions from InsertableAtSequence             */
  /* ************************************************************************ */
  @Override
  default void InsertAt(Data data, Natural num) {
    if (num == null) throw new NullPointerException("InsertAt: Index is null Size: " + Size().ToLong());
    long idx = num.ToLong();
    if (idx > Size().ToLong()) throw new IndexOutOfBoundsException("InsertAt: Index " + idx + " out of bounds for size " + Size().ToLong());
    ShiftRight(Natural.Of(idx), Natural.Of(1));
    SetAt(data, Natural.Of(idx));
  };
  // ...

  /* ************************************************************************ */
  /* Override specific member functions from RemovableAtSequence              */
  /* ************************************************************************ */
  @Override
  default Data AtNRemove(Natural num) {
    Data data = GetAt(num);
    ShiftLeft(num);
    return data;
  };
  // ...

  /* ************************************************************************ */
  /* Specific member functions of Vector                                       */
  /* ************************************************************************ */
  @Override
  default void ShiftLeft(Natural pos, Natural num) {
    long idx = ExcIfOutOfBound(pos);
    if (num.ToLong() > Size().ToLong() - idx) num = Natural.Of(Size().ToLong() - idx);
    Vector.super.ShiftLeft(pos, num);
    Reduce(num);
  }

  @Override
  default void ShiftRight(Natural pos, Natural num) {
    Expand(num);
    Vector.super.ShiftRight(pos, num);
  }

  @Override
  default DynVector<Data> SubVector(Natural from, Natural to) {
    return (DynVector<Data>) SubSequence(from, to);
  };
  // ...

  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */
  @Override
  Natural Size();

  // ...

}
