package apsd.interfaces.containers.base;

import apsd.interfaces.containers.iterators.BackwardIterator;
import apsd.interfaces.containers.iterators.ForwardIterator;
import apsd.interfaces.traits.Predicate;

/** Interface: TraversableContainer con supporto all'iterazione. */
public interface IterableContainer<Data> extends TraversableContainer<Data> { // Must extend TraversableContainer

  // FIterator
  ForwardIterator<Data> FIterator();
  // BIterator
  BackwardIterator<Data> BIterator();

  // IsEqual
  default boolean IsEqual(IterableContainer<Data> other) {
    if (other == null) return false;
    if (other == this) return true;
    if (this.Size().compareTo(other.Size()) != 0) return false;
    ForwardIterator<Data> itThis = this.FIterator();
    ForwardIterator<Data> itOther = other.FIterator();
    while (itThis.IsValid() && itOther.IsValid()) {
      Data dataThis = itThis.DataNNext();
      Data dataOther = itOther.DataNNext();
      if ((dataThis == null && dataOther != null) || (dataThis != null && dataOther == null) ||
          (dataThis != null && !dataThis.equals(dataOther)))
        return false;
    }
    return !(itThis.IsValid() || itOther.IsValid());
  }

  /* ************************************************************************ */
  /* Override specific member functions from TraversableContainer             */
  /* ************************************************************************ */
  @Override
  default boolean TraverseForward(Predicate<Data> fun) {
    return FIterator().ForEachForward(fun);
  };

  @Override
  default boolean TraverseBackward(Predicate<Data> fun) {
    return BIterator().ForEachBackward(fun);
  };
  // ...

}
