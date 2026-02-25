package apsd.interfaces.containers.collections;

import apsd.classes.utilities.Box;

public interface OrderedSet<Data extends Comparable <? super Data>> extends Set<Data> { // Must extend Set

  // Min
  default Data Min() {
    Box<Data> min = new Box<>(null);
    TraverseForward(dat -> {
      if (min.IsNull() || dat.compareTo(min.Get()) < 0) {
        min.Set(dat);
      }
      return true;
    });
    return min.Get();
  }

  // RemoveMin
  default void RemoveMin() {
    Remove(Min());
  }

  // MinNRemove
  default Data MinNRemove() {
    Data min = Min();
    Remove(min);
    return min;
  }

  // Max
  default Data Max() {
    Box<Data> max = new Box<>(null);
    TraverseForward(dat -> {
      if (max.IsNull() || dat.compareTo(max.Get()) > 0) {
        max.Set(dat);
      }
      return true;
    });
    return max.Get();
  }

  // RemoveMax
  default void RemoveMax() {
    Remove(Max());
  }

  // MaxNRemove
  default Data MaxNRemove() {
    Data max = Max();
    Remove(max);
    return max;
  }

  // Predecessor
  default Data Predecessor(Data dat) {
    Box<Data> predecessor = new Box<>(null);
    TraverseForward(current -> {
      if (current.compareTo(dat) < 0) {
        if (predecessor.IsNull() || current.compareTo(predecessor.Get()) > 0) {
          predecessor.Set(current);
        }
      }
      return true;
    });
    return predecessor.Get();
    
  }
  // RemovePredecessor
  default void RemovePredecessor(Data dat) {
    Data pred = Predecessor(dat);
    if (pred != null) {
      Remove(pred);
    }
  }

  // PredecessorNRemove
  default Data PredecessorNRemove(Data dat) {
    Data pred = Predecessor(dat);
    if (pred != null) {
      Remove(pred);
    }
    return pred;
  }

  // Successor
  default Data Successor(Data dat) {
    Box<Data> successor = new Box<>(null);
    TraverseForward(current -> {
      if (current.compareTo(dat) > 0) {
        if (successor.IsNull() || current.compareTo(successor.Get()) < 0) {
          successor.Set(current);
        }
      }
      return true;
    });
    return successor.Get();
  }

  // RemoveSuccessor
  default void RemoveSuccessor(Data dat) {
    Data succ = Successor(dat);
    if (succ != null) {
      Remove(succ);
    }
  }

  // SuccessorNRemove
  default Data SuccessorNRemove(Data dat) {
    Data succ = Successor(dat);
    if (succ != null) {
      Remove(succ);
    }
    return succ;
  }
}
