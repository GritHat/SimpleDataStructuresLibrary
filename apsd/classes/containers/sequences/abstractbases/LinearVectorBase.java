package apsd.classes.containers.sequences.abstractbases;

import apsd.classes.utilities.Natural;

/** Object: Abstract (static) linear vector base implementation. */
abstract public class LinearVectorBase<Data> extends VectorBase<Data> { // Must extend VectorBase

  // LinearVectorBase
  protected LinearVectorBase(Natural size) {
    super(size);
  }


  /* ************************************************************************ */
  /* Override specific member functions from ReallocableContainer             */
  /* ************************************************************************ */

  // ...
  @Override
  public void Realloc(Natural newcapacity) {
    if (newcapacity == null) return;
    Data[] oldarr = arr;
    long oldsize = Size().ToLong();
    ArrayAlloc(newcapacity);
    for (int i = 0; i < Math.min(oldsize, newcapacity.ToLong()); i++) {
      arr[i] = oldarr[i];
    }
  }

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  // ...
  @Override
  public Data GetAt(Natural pos) {
    long idx = ExcIfOutOfBound(pos);
    return arr[(int) idx];
  }

  /* ************************************************************************ */
  /* Override specific member functions from MutableSequence                  */
  /* ************************************************************************ */

  // ...
  @Override
  public void SetAt(Data element, Natural pos) {
    long idx = ExcIfOutOfBound(pos);
    arr[(int) idx] = element;
  }


}
