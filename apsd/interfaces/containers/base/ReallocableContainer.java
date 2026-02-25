package apsd.interfaces.containers.base;

import apsd.classes.utilities.Natural;
import apsd.interfaces.traits.Reallocable;

// import apsd.classes.utilities.Natural;
// import apsd.interfaces.traits.Reallocable;

/** Interface: ClearableContainer che è anche Reallocable. */
public interface ReallocableContainer extends ClearableContainer, Reallocable { // Must extend ClearableContainer, Reallocable

  double GROW_FACTOR = 2.0; // Must be strictly greater than 1.
  double SHRINK_FACTOR = 2.0; // Must be strictly greater than 1.

  @Override
  default Natural Size() {
    return Capacity();
  };

  // Capacity
  Natural Capacity();

  @Override
  default void Clear() {
    Realloc(Natural.ZERO);
  };

  // Grow
  default void Grow() {
    Grow(Natural.ZERO);
  };

  default void Grow(Natural additionalCapacity) {
    long newCap = (long)((additionalCapacity.ToLong() + (long)(Capacity().ToLong())) * GROW_FACTOR);
    if (newCap > Integer.MAX_VALUE)
      throw new OutOfMemoryError("Cannot grow container beyond Integer.MAX_VALUE capacity.");
    Realloc(Natural.Of(newCap));
  };

  // Shrink

  default void Shrink() {
    Natural newCapacity = Natural.Of((long)(Capacity().ToLong() / SHRINK_FACTOR));
    Realloc(newCapacity);
  };
  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */

  // ...

  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */

  // ...

}
