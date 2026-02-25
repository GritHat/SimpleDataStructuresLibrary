package apsd.interfaces.containers.base;

import apsd.classes.utilities.Box;
import apsd.classes.utilities.MutableNatural;
import apsd.classes.utilities.Natural;
import apsd.interfaces.traits.Accumulator;
import apsd.interfaces.traits.Predicate;

// import apsd.classes.utilities.Box;
// import apsd.classes.utilities.MutableNatural;
// import apsd.classes.utilities.Natural;
// import apsd.interfaces.traits.Accumulator;
// import apsd.interfaces.traits.Predicate;

/** Interface: MembershipContainer con supporto all'attraversamento. */
public interface TraversableContainer<Data> extends MembershipContainer<Data> { // Must extend MembershipContainer

  // TraverseForward
  boolean TraverseForward(Predicate<Data> fun);

  // TraverseBackward
  boolean TraverseBackward(Predicate<Data> fun);

  default <Acc> Acc FoldForward(Accumulator<Data, Acc> fun, Acc ini) {
    //if (ini == null) throw new NullPointerException("Initial accumulator value cannot be null!");
    final Box<Acc> acc = new Box<>(ini);
    if (fun != null) TraverseForward(dat -> { acc.Set(fun.Apply(dat, acc.Get())); return false; });
    return acc.Get();
  }

  // FoldBackward

  default <Acc> Acc FoldBackward(Accumulator<Data, Acc> fun, Acc ini) {
    //if (ini == null) throw new NullPointerException("Initial accumulator value cannot be null!");
    final Box<Acc> acc = new Box<>(ini);
    if (fun != null) TraverseBackward(dat -> { acc.Set(fun.Apply(dat, acc.Get())); return false; });
    return acc.Get();
  }

  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */

  @Override
  default Natural Size() {
    /*final Box<Natural> size = new Box<>(Natural.ZERO);
    TraverseForward( data -> {
      size.Set(size.Get().Increment());
      return false;
    });
    return size.Get();*/
    return FoldForward((_, size) -> { size.Increment(); return size; }, 
      new MutableNatural(0)).ToNatural();
  }

  @Override
  default boolean Exists(Data data) {
    return TraverseForward( dat -> {
      if ((data == null && dat == null) ||
          (data != null && data.equals(dat))) {
        return true; // Stop traversal
      }
      return false;
    });
  }
  // ...

  /* ************************************************************************ */
  /* Override specific member functions from MembershipContainer              */
  /* ************************************************************************ */

  // ...

}
