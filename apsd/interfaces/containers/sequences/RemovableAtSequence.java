package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Natural;

/** Interface: Sequence con supporto alla rimozione di un dato tramite posizione. */
public interface RemovableAtSequence<Data> extends Sequence<Data> { // Must extend Sequence

  // RemoveAt
  default void RemoveAt(Natural num) {
    AtNRemove(num);
  };
  // AtNRemove
  Data AtNRemove(Natural num);

  // RemoveFirst
  default void RemoveFirst() {
    if (IsEmpty()) throw new IndexOutOfBoundsException("Sequence is empty.");
    AtNRemove(Natural.ZERO);
  };
  // FirstNRemove
  default Data FirstNRemove() {
    if (IsEmpty()) throw new IndexOutOfBoundsException("Sequence is empty.");
    return AtNRemove(Natural.ZERO);
  };

  // RemoveLast
  default void RemoveLast() {
    if (IsEmpty()) throw new IndexOutOfBoundsException("Sequence is empty.");
    AtNRemove(Size().Decrement());
  };
  // LastNRemove
  default Data LastNRemove() {
    if (IsEmpty()) throw new IndexOutOfBoundsException("Sequence is empty.");
    return AtNRemove(Size().Decrement());
  };

}
