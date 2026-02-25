package apsd.classes.containers.sequences.abstractbases;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.sequences.DynVector;

/** Object: Abstract dynamic linear vector base implementation. */
abstract public class DynLinearVectorBase<Data> extends LinearVectorBase<Data> implements DynVector<Data> { // Must extend LinearVectorBase and implement DynVector

  protected long size = 0L;

  // DynLinearVectorBase
  protected DynLinearVectorBase(Natural capacity) {
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
    long newSize = this.size - reductionSize.ToLong();
    if (newSize < 0) throw new ArithmeticException("Underflow: cannot reduce size below zero!");
    Shrink();
    this.size = newSize;
  }
}
