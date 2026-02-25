package apsd.interfaces.containers.base;

import apsd.classes.utilities.Box;

/** Interface: Container con supporto alla rimozione di un dato. */
public interface RemovableContainer<Data> extends Container { // Must extend Container

  // Remove
  boolean Remove(Data dat);

  // // RemoveAll
  // default boolean RemoveAll(TraversableContainer<Data> container){
  //   final Box<Boolean> allRemoved = new Box<>(true);
  //   if (container == null) return true;
  //   container.TraverseForward( data -> {
  //     if (!this.Remove(data)) allRemoved.Set(false);
  //       return false;
  //   });
  //   return allRemoved.Get();
  // };

  // // RemoveSome
  // default boolean RemoveSome(TraversableContainer<Data> container) {
  //   final Box<Boolean> someRemoved = new Box<>(false);
  //   if (container == null) return false;
  //   container.TraverseForward( data -> {
  //     if (this.Remove(data)) someRemoved.Set(true);
  //       return false;
  //   });
  //   return someRemoved.Get();
  // };


  default boolean RemoveAll(TraversableContainer<Data> container){
    final Box<Boolean> allRemoved = new Box<>(true);
    if (container == null) return true;
    container.TraverseForward( data -> {
      allRemoved.Set(this.Remove(data) && allRemoved.Get());
      return false;
    });
    return allRemoved.Get();
  };

  // RemoveSome
  default boolean RemoveSome(TraversableContainer<Data> container) {
    final Box<Boolean> someRemoved = new Box<>(false);
    if (container == null) return false;
    container.TraverseForward( data -> {
      someRemoved.Set(this.Remove(data) || someRemoved.Get());
      return false;
    });
    return someRemoved.Get();
  };
}
