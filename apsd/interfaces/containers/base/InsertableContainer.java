package apsd.interfaces.containers.base;

import apsd.classes.utilities.Box;

/** Interface: Container con supporto all'inserimento di un dato. */
public interface InsertableContainer<Data> extends Container { // Must extend Container

  // Insert
  boolean Insert(Data data);

  // InsertAll
  default boolean InsertAll(TraversableContainer<Data> container){
    final Box<Boolean> allInserted = new Box<>(true);
    if (container != null) container.TraverseForward( data -> {
      allInserted.Set(Insert(data) && allInserted.Get()); return false;
    });
    return allInserted.Get();
  };

  // InsertSome
  default boolean InsertSome(TraversableContainer<Data> container) {
    final Box<Boolean> someInserted = new Box<>(false);
    if (container != null) container.TraverseForward( data -> {
      someInserted.Set(Insert(data) || someInserted.Get()); return false;
    });
    return someInserted.Get();
  };

}
