package apsd.interfaces.containers.collections;

import apsd.interfaces.containers.iterators.ForwardIterator;

import apsd.interfaces.traits.Predicate;

import apsd.interfaces.containers.base.IterableContainer;

public interface Set<Data> extends Collection<Data> { // Must extend Collection

  // Union
  // default void Union(Set<Data> other) {
  //   if (other == null || other == this) return;
  //   ForwardIterator<Data> itr = other.FIterator();
  //   while (itr.IsValid()) {
  //     Data dat = itr.GetCurrent();
  //     if (!this.Exists(dat)) Insert(dat);
  //     itr.Next();
  //   }
  // }
  default void Union(Set<Data> other) {
    if (other == null || other == this) return;
    other.TraverseForward(dat -> {
      if (!this.Exists(dat)) Insert(dat);
      return false;
    });
  }

  // Difference
  default void Difference(Set<Data> other) {
    if (other == null) return;
    if (other == this) { Clear(); return; }
    Filter(dat -> !other.Exists(dat));
  }

  // Intersection
  default void Intersection(Set<Data> other) {
    if (other == null || other == this) return;
    Filter(dat -> other.Exists(dat));
  }

  /* ************************************************************************ */
  /* Override specific member functions from IterableContainer                */
  /* ************************************************************************ */

  @Override
  default boolean IsEqual(IterableContainer<Data> other) {
    if (other == null) return false;
    if (other == this) return true;
    if (!(other instanceof Set<?>)) return false;
    Set<Data> otherSet = (Set<Data>) other;
    if (!this.Size().equals(otherSet.Size())) return false;
    ForwardIterator<Data> itr = this.FIterator();
    while (itr.IsValid()) {
      Data dat = itr.GetCurrent();
      if (!otherSet.Exists(dat)) return false;
      itr.Next();
    }
    return true;
  }
  // ...

  Set<Data> SubSet(Predicate<Data> condition);

}
