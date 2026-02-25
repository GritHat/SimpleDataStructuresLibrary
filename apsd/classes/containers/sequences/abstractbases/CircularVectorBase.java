package apsd.classes.containers.sequences.abstractbases;

import apsd.classes.utilities.Natural;

/** Object: Abstract (static) circular vector base implementation. */
abstract public class CircularVectorBase<Data> extends VectorBase<Data> { // Must extend VectorBase

  protected long start = 0L;

  // CircularVectorBase
  protected CircularVectorBase(Natural size) {
    super(size);
  }
    
  @Override
  protected void ArrayAlloc(Natural newsize) {
    super.ArrayAlloc(newsize);
    this.start = 0L;
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
    long oldstart = this.start;
    ArrayAlloc(newcapacity);
    for (int i = 0; i < Math.min(oldsize, newcapacity.ToLong()); i++) {
      arr[i] = oldarr[(int) ((oldstart + i) % oldsize)];
    }
    this.start = 0L;
  }

  protected void Realing() {
    if (this.start == 0L) return;
    Realloc(Capacity());
  }
  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  // ...
  @Override
  public Data GetAt(Natural pos) {
    long idx = ExcIfOutOfBound(pos);
    return arr[(int) ((this.start + idx) % Size().ToLong())];
  }

  /* ************************************************************************ */
  /* Override specific member functions from MutableSequence                  */
  /* ************************************************************************ */

  // ...
  @Override
  public void SetAt(Data element, Natural pos) {
    long idx = ExcIfOutOfBound(pos);
    arr[(int) ((this.start + idx) % Size().ToLong())] = element;
  }

  /* ************************************************************************ */
  /* Specific member functions of Vector                                      */
  /* ************************************************************************ */

  // ...
  @Override
  public void ShiftLeft(Natural pos, Natural num) {
    long idx = ExcIfOutOfBound(pos);
    long count = num.ToLong();
    if (count > Size().ToLong() - idx) count = Size().ToLong() - idx;
    if (count == 0L) return;
    if (idx == 0L) ShiftStartRight(count);
    else if (idx > Size().ToLong() - idx - count) ShiftLeftByRightSide(idx, count);
    else ShiftLeftByLeftSide(idx, count);
  }

  protected void ShiftLeftByRightSide(long pos, long num) {
    long wrt = PositionToIndex(pos);
    long rdr = (wrt + num) % Size().ToLong();
    long i = 0;
    for (; i < Size().ToLong() - pos - num; ++i) {
      SetAt(GetAt(Natural.Of(IndexToPosition(rdr))), Natural.Of(IndexToPosition(wrt)));
      SetAt(null, Natural.Of(IndexToPosition(rdr)));
      wrt = (++wrt) % Size().ToLong();
      rdr = (++rdr) % Size().ToLong();
    }
    while (i < num) {
      SetAt(null, Natural.Of(IndexToPosition(wrt)));
      wrt = (++wrt) % Size().ToLong();
      ++i;
    }
  }

  protected void ShiftLeftByLeftSide(long pos, long num){
    long wrt = PositionToIndex(pos + num - 1);
    long rdr = PositionToIndex(pos - 1);
    long i = 0;
    for (; i < pos; ++i) {
      SetAt(GetAt(Natural.Of(IndexToPosition(rdr))), Natural.Of(IndexToPosition(wrt)));
      SetAt(null, Natural.Of(IndexToPosition(rdr)));
      wrt = (wrt - 1 + Size().ToLong()) % Size().ToLong();
      rdr = (rdr - 1 + Size().ToLong()) % Size().ToLong();
    }
    while (i < num) {
      SetAt(null, Natural.Of(IndexToPosition(wrt)));
      wrt = (wrt - 1 + Size().ToLong()) % Size().ToLong();
      ++i;
    }
    this.start = (this.start + num) % Size().ToLong();
  }

  protected void ShiftStartRight(long len) {
    for (long i = 0; i < len; ++i) {
      SetAt(null, Natural.Of((i) % Size().ToLong()));
    }
    this.start = (this.start + len) % Size().ToLong();
  }

  @Override
  public void ShiftRight(Natural pos, Natural num) {
    long idx = ExcIfOutOfBound(pos);
    long count = num.ToLong();
    if (count > Size().ToLong() - idx) count = Size().ToLong() - idx;
    if (count == 0L) return;
    if (idx == 0L) ShiftStartLeft(count);
    else if (idx >= Size().ToLong() - idx) ShiftRightByRightSide(idx, count);
    else ShiftRightByLeftSide(idx, count);
  }

  protected void ShiftRightByRightSide(long pos, long num) {
    long wrt = PositionToIndex(Size().Decrement());
    long rdr = (((wrt - num) % Size().ToLong()) + Size().ToLong()) % Size().ToLong();
    long i = 0;
    for (; i < Size().ToLong() - pos - num; ++i) {
      SetAt(GetAt(Natural.Of(IndexToPosition(rdr))), Natural.Of(IndexToPosition(wrt)));
      SetAt(null, Natural.Of(IndexToPosition(rdr)));
      wrt = (wrt - 1 + Size().ToLong()) % Size().ToLong();
      rdr = (rdr - 1 + Size().ToLong()) % Size().ToLong();
    }
    while (i < num) {
      SetAt(null, Natural.Of(IndexToPosition(wrt)));
      wrt = (wrt - 1 + Size().ToLong()) % Size().ToLong();
      ++i;
    }
  }

  protected void ShiftRightByLeftSide(long pos, long num) {
    long size = Size().ToLong();
    long wrt = (((this.start - num) % size) + size) % size;
    long rdr = PositionToIndex(0L);
    long i = 0;
    for (; i < pos; ++i) {
      SetAt(GetAt(Natural.Of(IndexToPosition(rdr))), Natural.Of(IndexToPosition(wrt)));
      SetAt(null, Natural.Of(IndexToPosition(rdr)));
      wrt = (wrt + 1) % Size().ToLong();
      rdr = (rdr + 1) % Size().ToLong();
    }
    while (i < num) {
      SetAt(null, Natural.Of(IndexToPosition(wrt)));
      wrt = (wrt + 1) % Size().ToLong();
      ++i;
    }
    this.start = (((this.start - num) % Size().ToLong()) + Size().ToLong()) % Size().ToLong();
  }

  protected void ShiftStartLeft(long len) {
    for (long i = 1; i <= len; ++i) {
      long wrt = (Size().ToLong() - i) % Size().ToLong();
      SetAt(null, Natural.Of(wrt));
    }
    this.start = (((this.start - len) % Size().ToLong()) + Size().ToLong()) % Size().ToLong();
  }


  protected long PositionToIndex(Natural pos) {
    long idx = pos.ToLong();
    return PositionToIndex(idx);
  }

  protected long PositionToIndex(long pos) {
    return (this.start + pos) % Size().ToLong();
  }

  protected long IndexToPosition(long idx) {
    if (idx >= this.start) {
      return idx - this.start;
    } else {
      return Size().ToLong() - (this.start - idx);
    }
  }
}
