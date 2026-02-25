package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.ReallocableContainer;

public interface Vector<Data> extends ReallocableContainer, MutableSequence<Data> { // Must extend ReallocableContainer and MutableSequence

  // ShiftLeft
  default void ShiftLeft(Natural pos) {
    ShiftLeft(pos, Natural.Of(1));
  }

  default void ShiftLeft(Natural pos, Natural num) {
    long idx = ExcIfOutOfBound(pos);
    long size = Size().ToLong();
    long len = num.ToLong();
    len = (len <= size - idx) ? len : size - idx;
    if (len > 0) {
      long iniwrt = idx;
      long wrt = iniwrt;
      for (long rdr = wrt + len; rdr < size; rdr++, wrt++) {
        Natural natrdr = Natural.Of(rdr);
        SetAt(GetAt(natrdr), Natural.Of(wrt));
        SetAt(null, natrdr);
      }
      for (; wrt - iniwrt < len; wrt++) {
        SetAt(null, Natural.Of(wrt));
      }
    }
  }

  // ShiftFirstLeft
  default void ShiftFirstLeft() {
    ShiftLeft(Natural.Of(0), Natural.Of(1));
  }

  // ShiftLastLeft
  default void ShiftLastLeft() {
    long size = Size().ToLong();
    if (size > 0) {
      ShiftLeft(Natural.Of(size - 1), Natural.Of(1));
    } else throw new IndexOutOfBoundsException("Cannot shift last element left in an empty vector!");
  }

  // ShiftRight
  default void ShiftRight(Natural pos) {
    ShiftRight(pos, Natural.Of(1));
  }

  default void ShiftRight(Natural pos, Natural num) {
    long idx = ExcIfOutOfBound(pos);
    long size = Size().ToLong();
    long len = num.ToLong();
    if (len > size - idx) len = size - idx;
    if (len > 0) {
      long inirdr = size - 1 - len;
      long rdr = inirdr;
      long wrt = size - 1;
      long i = 0;
      for (; i < size - idx - len; i++) {
        Natural natrdr = Natural.Of(rdr);
        SetAt(GetAt(natrdr), Natural.Of(wrt));
        SetAt(null, natrdr);
        rdr--;
        wrt--;
      }
      for (; i < len; i++) {
        SetAt(null, Natural.Of(wrt));
        wrt--;
      }
      
    }
  }


  // ShiftFirstRight
  default void ShiftFirstRight() {
    ShiftRight(Natural.Of(0), Natural.Of(1));
  }

  // ShiftLastRight
  default void ShiftLastRight() {
    long size = Size().ToLong();
    if (size > 0) {
      ShiftRight(Natural.Of(size - 1), Natural.Of(1));
    } else throw new IndexOutOfBoundsException("Cannot shift last element right in an empty vector!");
  }

  // SubVector
  default Vector<Data> SubVector(Natural from, Natural to) {
    return (Vector<Data>) SubSequence(from, to);
  }

  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */

  @Override
  default Natural Size() {
    return this.Capacity();
  };
  // ...

}
