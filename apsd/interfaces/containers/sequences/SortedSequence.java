package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.SortedIterableContainer;

/** Interface: Sequence & SortedIterableContainer. */
public interface SortedSequence<Data extends Comparable<? super Data>> extends Sequence<Data>, SortedIterableContainer<Data> { // Must extend Sequence and SortedIterableContainer

  /* ************************************************************************ */
  /* Override specific member functions from MembershipContainer              */
  /* ************************************************************************ */
  @Override
  default boolean Exists(Data dat) {
    return Search(dat) != null;
  }

  @Override
  default Natural Search(Data dat) {
    if (dat == null) return null;
    long start = 0;
    long end = Size().ToLong() - 1;
    long mid = (start + end) / 2;
    Natural candidateIdx = null;
    while (start <= end) {
      Data current = this.GetAt(Natural.Of(mid));
      int cmp = dat.compareTo(current);
      if (cmp == 0) {
        candidateIdx = Natural.Of(mid);
        end = mid - 1;
      } else if (cmp < 0) {
        end = mid - 1;
      } else {
        start = mid + 1;
      }
      mid = (start + end) / 2;
    }
    return candidateIdx;
  }
  // ...

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  // ...

}
