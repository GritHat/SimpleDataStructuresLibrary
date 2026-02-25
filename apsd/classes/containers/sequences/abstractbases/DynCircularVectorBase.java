package apsd.classes.containers.sequences.abstractbases;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.sequences.DynVector;

/** Object: Abstract dynamic circular vector base implementation. */
abstract public class DynCircularVectorBase<Data> extends CircularVectorBase<Data> implements DynVector<Data> { // Must extend CircularVectorBase and implement DynVector

  protected long size = 0L;

  // DynCircularVectorBase
  protected DynCircularVectorBase(Natural capacity) {
    super(capacity);
  }

  @Override
  protected void ArrayAlloc(Natural newsize) {
    super.ArrayAlloc(newsize);
    this.size = 0L;
  }

  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */

  // ...
  @Override
  public Natural Size() {
    return Natural.Of(this.size);
  }

  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */

  // ...
  @Override
  public void Clear() {
    super.Clear();
    this.size = 0L;
  }

  /* ************************************************************************ */
  /* Override specific member functions from ReallocableContainer             */
  /* ************************************************************************ */

  // ...
  @Override
  public void Realloc(Natural newcapacity) {
    if (newcapacity == null) return;
    long oldsize = Size().ToLong();
    super.Realloc(newcapacity);
    this.size = Math.min(oldsize, newcapacity.ToLong());
  }

  /* ************************************************************************ */
  /* Override specific member functions from ResizableContainer               */
  /* ************************************************************************ */

  // ...
  @Override
  public void Expand(Natural additionalSize) {
    if (additionalSize == null) return;
    Grow(additionalSize);
    this.size += additionalSize.ToLong();
  }

  @Override
  public void Reduce(Natural reductionSize) {
    if (reductionSize == null) return;
    if (reductionSize.ToLong() > this.size) throw new ArithmeticException("Underflow: reduction size exceeds current size!");
    Realing();
    Shrink();
    this.size -= reductionSize.ToLong();
  }

  /* ************************************************************************ */
  /* Specific member functions of Vector                                      */
  /* ************************************************************************ */

  // ...
  @Override
  public void ShiftLeft(Natural pos, Natural num) {
    long idx = ExcIfOutOfBound(pos);
    if (num.ToLong() > Size().ToLong() - idx) num = Natural.Of(Size().ToLong() - idx);
    super.ShiftLeft(pos, num);
    Reduce(num);
  }

  @Override
  public void ShiftRight(Natural pos, Natural num) {
    if (pos == null || num == null) throw new NullPointerException("ShiftRight: Position or number is null Size: " + Size().ToLong());
    if (pos.ToLong() > Size().ToLong()) throw new IndexOutOfBoundsException("ShiftRight: Position " + pos.ToLong() + " out of bounds for size " + Size().ToLong());
    Expand(num);
    long idx = PositionToIndex(ExcIfOutOfBound(pos)); // serve in caso num = 0 e dunque pos = 0 ancora out of bounds
    long count = num.ToLong();
    long rdr = Size().ToLong() - 1 - count;
    if (count == 0L) return;
    for (long wrt = Size().ToLong() - 1; rdr >= idx; rdr--, wrt--) {
      SetAt(GetAt(Natural.Of(rdr)), Natural.Of(wrt));
      SetAt(null, Natural.Of(rdr));
    }
    if (this.start > idx) {
      this.start = (this.start + count) % Size().ToLong();
    }
  }
}
