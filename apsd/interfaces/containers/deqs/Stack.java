package apsd.interfaces.containers.deqs;

import apsd.interfaces.containers.base.ClearableContainer;
import apsd.interfaces.containers.base.InsertableContainer;

public interface Stack<Data> extends ClearableContainer, InsertableContainer<Data> { // Must extend ClearableContainer and InsertableContainer

  // Top
  Data Top();
  // Pop
  void Pop();
  // TopNPop
  default Data TopNPop() {
    if (IsEmpty()) return null;
    Data value = Top();
    Pop();
    return value;
  };

  // SwapTop
  default void SwapTop(Data value) {
    if (IsEmpty()) return;
    Pop();
    Push(value);
  };
  // TopNSwap
  default Data TopNSwap(Data value) {
    if (IsEmpty()) return null;
    Data oldTop = Top();
    Pop();
    Push(value);
    return oldTop;
  };

  // Push
  void Push(Data value);

  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */
  @Override
  default void Clear() {
    while (!IsEmpty()) {
      Pop();
    }
  };
  // ...

  /* ************************************************************************ */
  /* Override specific member functions from InsertableContainer              */
  /* ************************************************************************ */

  @Override
  default boolean Insert(Data value) {
    if (value == null) return false;
    Push(value);
    return true;
  };
  // ...

}
