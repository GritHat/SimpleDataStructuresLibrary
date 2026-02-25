package apsd.classes.containers.sequences.abstractbases;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.iterators.MutableBackwardIterator;
import apsd.interfaces.containers.iterators.MutableForwardIterator;
import apsd.interfaces.containers.sequences.MutableSequence;
import apsd.interfaces.containers.sequences.Vector;

/** Object: Abstract vector base implementation. */
abstract public class VectorBase<Data> implements Vector<Data> { // Must implement Vector

  protected Data[] arr;

  // VectorBase
  protected VectorBase(Natural size) {
    if (size == null) { throw new IllegalArgumentException("Invalid argument: size cannot be null!"); }
    ArrayAlloc(size);
  }

  // NewVector
  protected abstract VectorBase<Data> NewVector(Data[] array);

  @SuppressWarnings("unchecked")
  protected void ArrayAlloc(Natural newsize) {
    long size = newsize.ToLong();
    if (size >= Integer.MAX_VALUE) { throw new ArithmeticException("Overflow: size cannot exceed Integer.MAX_VALUE!"); }
    arr = (Data[]) new Object[(int) size];
  }

  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */

  // ...
  @Override
  public void Clear() {
    ArrayAlloc(Natural.Of(0));
  }

  /* ************************************************************************ */
  /* Override specific member functions from ResizableContainer               */
  /* ************************************************************************ */

  // ...
  @Override
  public Natural Capacity() {
    if (arr == null) return Natural.ZERO;
    return Natural.Of(arr.length);
  }

  /* ************************************************************************ */
  /* Override specific member functions from IterableContainer                */
  /* ************************************************************************ */

  // ...
  @Override
  public MutableForwardIterator<Data> FIterator() { 
    return new VectorFIterator();
  }

  @Override
  public MutableBackwardIterator<Data> BIterator() {
    return new VectorBIterator();
  }


  protected class VectorFIterator implements MutableForwardIterator<Data> {
    private long index = 0L;

    public VectorFIterator() {
      Reset();
    }

    @Override
    public boolean IsValid() {
      return index < Size().ToLong();
    }

    @Override
    public void SetCurrent(Data dat) {
      SetAt(dat, Natural.Of(index));
    }

    @Override
    public Data GetCurrent() {
      return GetAt(Natural.Of(index));
    }

    @Override
    public void Reset() {
      index = 0L;
    }

    @Override
    public Data DataNNext() {
      return GetAt(Natural.Of(index++));
    }
  }

  protected class VectorBIterator implements MutableBackwardIterator<Data> {
    private long index = 0L;

    public VectorBIterator() {
      Reset();
    }

    @Override
    public boolean IsValid() {
      return index >= 0 && index < Size().ToLong();
    }

    @Override
    public void SetCurrent(Data dat) {
      SetAt(dat, Natural.Of(index));
    }

    @Override
    public Data GetCurrent() {
      return GetAt(Natural.Of(index));
    }

    @Override
    public void Reset() {
      index = Size().ToLong() - 1;
    }

    @Override
    public Data DataNPrev() {
      if (!IsValid()) {
        return null;
      }
      return GetAt(Natural.Of(index--));
    }
  }

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  // ...
  public abstract Data GetAt(Natural index);

  /* ************************************************************************ */
  /* Override specific member functions from MutableSequence                  */
  /* ************************************************************************ */

  // ...
  public abstract void SetAt(Data dat, Natural index);

  @SuppressWarnings("unchecked")
  public MutableSequence<Data> SubSequence(Natural from, Natural to) {
    if (from == null && to == null) return NewVector((Data[]) new Object[0]);
    if (!IsInBound(from) || !IsInBound(to) || from.ToLong() > to.ToLong()) return null;
    long startIdx = from.ToLong();
    long endIdx = to.ToLong();
    Data[] subArr = (Data[]) new Object[(int) (endIdx - startIdx + 1)];
    VectorBase<Data> subVec = NewVector(subArr);
    for (long i = startIdx; i <= endIdx; i++) subVec.SetAt(GetAt(Natural.Of(i)), Natural.Of(i - startIdx));
    return subVec;
  }

}
