package apsd.interfaces.containers.base;

import apsd.classes.utilities.Natural;

/** Interface: ReallocableContainer che è espandibile e riducibile. */
public interface ResizableContainer extends ReallocableContainer { // Must extend ReallocableContainer

  double THRESHOLD_FACTOR = 2.0; // Must be strictly greater than 1.

  // Expand
  default void Expand() {
    Expand(Natural.ONE);
  }

  void Expand(Natural size);

  // Reduce

  default void Reduce() {
    Reduce(Natural.ONE);
  }

  void Reduce(Natural size);


  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */

  // ...

  /* ************************************************************************ */
  /* Override specific member functions from ReallocableContainer             */
  /* ************************************************************************ */

  @Override
  Natural Size();

  @Override
  default void Grow(Natural additionalCapacity) {
    if (additionalCapacity == null) return;
    if (Size().ToLong() + additionalCapacity.ToLong() < Capacity().ToLong()) return; // No need to grow if the new size is within capacity
    long newCap = (long)((additionalCapacity.ToLong() + Size().ToLong()) * GROW_FACTOR);
    if (newCap > Integer.MAX_VALUE)
      throw new OutOfMemoryError("Cannot grow container beyond Integer.MAX_VALUE capacity.");
    ReallocableContainer.super.Grow(additionalCapacity);
  }

  @Override
  default void Shrink() {
    if ((long)(THRESHOLD_FACTOR * SHRINK_FACTOR * Size().ToLong()) <= Capacity().ToLong())
      ReallocableContainer.super.Shrink();
  }
}
