package apsd.interfaces.containers.deqs;

import apsd.interfaces.containers.base.ClearableContainer;
import apsd.interfaces.containers.base.InsertableContainer;

public interface Queue<Data> extends ClearableContainer, InsertableContainer<Data> { // Must extend ClearableContainer and InsertableContainer

  Data Head();
  void Dequeue();
  void Enqueue(Data value);
  
  default Data HeadNDequeue() {
    if (IsEmpty()) return null;
    Data value = Head();
    Dequeue();
    return value;
  };

  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */
  @Override
  default void Clear() {
    while (!IsEmpty()) {
      Dequeue();
    }
  };

  /* ************************************************************************ */
  /* Override specific member functions from InsertableContainer              */
  /* ************************************************************************ */

  @Override
  default boolean Insert(Data value) {
    if (value == null) return false;
    Enqueue(value);
    return true;
  };

}