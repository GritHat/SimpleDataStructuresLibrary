package apsd.classes.containers.collections.concretecollections.bases;

import apsd.classes.containers.deqs.WStack;
import apsd.classes.utilities.Box;
import apsd.classes.utilities.MutableNatural;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.Chain;
import apsd.interfaces.containers.collections.Set;
import apsd.interfaces.containers.deqs.Stack;
import apsd.interfaces.containers.iterators.BackwardIterator;
import apsd.interfaces.containers.iterators.ForwardIterator;
import apsd.interfaces.containers.iterators.MutableBackwardIterator;
import apsd.interfaces.containers.iterators.MutableForwardIterator;
import apsd.interfaces.containers.sequences.Sequence;
import apsd.interfaces.traits.Predicate;

/** Object: Abstract chain base implementation on linked-list. */
abstract public class LLChainBase<Data> implements Chain<Data> {

  protected final MutableNatural size = new MutableNatural();
  protected final Box<LLNode<Data>> headref = new Box<>();
  protected final Box<LLNode<Data>> tailref = new Box<>();

  protected LLChainBase() {
    size.Assign(0L);
    headref.Set(null);
    tailref.Set(null);
  }

  protected LLChainBase(long size, LLNode<Data> head, LLNode<Data> tail) {
    this.size.Assign(size);
    this.headref.Set(head);
    this.tailref.Set(tail);
  }

  public LLChainBase(TraversableContainer<Data> con) {
    size.Assign(con.Size());
    final Box<Boolean> first = new Box<>(true);
    con.TraverseForward(dat -> {
      LLNode<Data> node = new LLNode<>(dat);
      if (first.Get()) {
        headref.Set(node);
        first.Set(false);
      } else {
        tailref.Get().SetNext(node);
      }
      tailref.Set(node);
      return false;
    });
  }

  protected abstract LLChainBase<Data> NewChain(long size, LLNode<Data> head, LLNode<Data> tail);

  protected MutableForwardIterator<Box<LLNode<Data>>> FRefIterator() {
    return new ChainRefFIterator();
  }

  protected MutableBackwardIterator<Box<LLNode<Data>>> BRefIterator() {
    return new ChainRefBIterator();
  }

  @Override
  public Natural Size() {
    return Natural.Of(size.ToLong());
  }

  @Override
  public void Clear() {
    headref.Set(null);
    tailref.Set(null);
    size.Assign(0L);
  }

  @Override
  public boolean Remove(Data dat) {
    if (dat == null) return false;
    final Box<LLNode<Data>> prd = new Box<>();
    return FRefIterator().ForEachForward(cur -> {
      LLNode<Data> node = cur.Get();
      if (node.Get().equals(dat)) {
        cur.Set(node.GetNext().Get());
        if (tailref.Get() == node) { tailref.Set(prd.Get()); }
        size.Decrement();
        return true;
      }
      prd.Set(node);
      return false;
    });
  }

  @Override
  public ForwardIterator<Data> FIterator() {
    return new ChainFIterator();
  }

  @Override
  public BackwardIterator<Data> BIterator() {
    return new ChainBIterator();
  }

  @Override
  public Data GetFirst() {
    if (headref.Get() == null) {
      throw new IndexOutOfBoundsException("LLChainBase GetFirst: chain is empty");
    }
    return headref.Get().Get();
  }

  @Override
  public Data GetLast() {
    if (tailref.Get() == null) {
      throw new IndexOutOfBoundsException("LLChainBase GetLast: chain is empty");
    }
    return tailref.Get().Get();
  }

  @Override
  public Sequence<Data> SubSequence(Natural start, Natural end) {
    LLChainBase<Data> subchain = NewChain(0L, null, null);
    if (start == null && end == null) return subchain;
    if (!IsInBound(start) || !IsInBound(end) || start.ToLong() > end.ToLong()) return null;
    Box<Long> idx = new Box<>(Size().ToLong() - 1L);
    BIterator().ForEachBackward(dat -> {
      if (idx.Get() >= start.ToLong() && idx.Get() <= end.ToLong()) {
        subchain.Insert(dat);
      }
      idx.Set(idx.Get() - 1L);
      return idx.Get() < start.ToLong();
    });
    return subchain;
  }

  @Override
  public Data AtNRemove(Natural pos) {
    if (pos == null) throw new NullPointerException("AtNRemove: Index is null Size: " + Size().ToLong());
    if (!IsInBound(pos)) throw new IndexOutOfBoundsException("AtNRemove: Index out of bounds: " + pos + "; Size: " + Size().ToLong() + "!");
    final Box<Data> removedData = new Box<>();
    final Box<LLNode<Data>> prevNode = new Box<>();
    final MutableNatural idx = new MutableNatural(0L);
    FRefIterator().ForEachForward(cur -> {
      if (tailref.Get() == cur.Get().GetNext().Get()) { prevNode.Set(cur.Get()); }
      if (idx.ToLong() == pos.ToLong()) {
        LLNode<Data> node = cur.Get();
        removedData.Set(node.Get());
        cur.Set(node.GetNext().Get());
        if (tailref.Get() == node) { tailref.Set(prevNode.Get()); }
        size.Decrement();
        return true;
      }
      idx.Increment();
      return false;
    });
    if (removedData.Get() == null) {
      throw new IndexOutOfBoundsException("LLChainBase AtNRemove: position out of bounds");
    }
    return removedData.Get();
  }

  @Override
  public void RemoveFirst() {
    FirstNRemove();
  }

  @Override
  public void RemoveLast() {
    LastNRemove();
  }

  @Override
  public Data LastNRemove() {
    if (tailref.Get() == null) throw new IndexOutOfBoundsException("LLChainBase LastNRemove: chain is empty");
    Data lastData = tailref.Get().Get();
    if (headref.Get() == tailref.Get()) {
      headref.Set(null);
      tailref.Set(null);
    } else {
      final Box<LLNode<Data>> prevNode = new Box<>();
      FRefIterator().ForEachForward(cur -> {
        if (tailref.Get() == cur.Get().GetNext().Get()) {
          prevNode.Set(cur.Get());
          return true;
        }
        return false;
      });
      prevNode.Get().SetNext(null);
      tailref.Set(prevNode.Get());
    }
    size.Decrement();
    return lastData;
  }

  @Override
  public Data FirstNRemove() {
    if (headref.Get() == null) throw new IndexOutOfBoundsException("LLChainBase FirstNRemove: chain is empty");
    Data firstData = headref.Get().Get();
    headref.Set(headref.Get().GetNext().Get());
    size.Decrement();
    if (headref.Get() == null) {
      tailref.Set(null);
    }
    return firstData;
  }

  /*@Override
  public boolean Filter(Predicate<Data> pred) {
    if (pred == null) return false;
    final Box<Boolean> removed = new Box<>(false);
    final Box<LLNode<Data>> prevNode = new Box<>();
    ForwardIterator<Box<LLNode<Data>>> fit = FRefIterator();
    while (fit.IsValid()) {
      LLNode<Data> node = fit.GetCurrent().Get();
      if (!pred.Apply(node.Get())) {
        // Remove node
        if (headref.Get() == node) headref.Set(node.GetNext().Get());
        if (tailref.Get() == node) {
          tailref.Set(prevNode.Get());
          if (tailref.Get() != null) tailref.Get().SetNext(null);
        } else fit.GetCurrent().Set(node.GetNext().Get());
        size.Decrement();
        removed.Set(true);
        fit.Reset();
      } else {
        prevNode.Set(node);
        fit.Next();
      }
    }
    return removed.Get();
  }*/

  @Override
  public boolean Filter(Predicate<Data> pred) {
    if (pred == null) return false;
    boolean removed = false;

    // Remove non-matching nodes from the head
    while (headref.Get() != null && !pred.Apply(headref.Get().Get())) {
      headref.Set(headref.Get().GetNext().Get());
      size.Decrement();
      removed = true;
    }

    if (headref.Get() == null) {
      tailref.Set(null);
      return removed;
    }

    // Walk from head, removing non-matching successors
    LLNode<Data> prev = headref.Get();
    LLNode<Data> curr = prev.GetNext().Get();
    while (curr != null) {
      if (!pred.Apply(curr.Get())) {
        prev.SetNext(curr.GetNext().Get());
        if (curr == tailref.Get()) {
          tailref.Set(prev);
        }
        size.Decrement();
        removed = true;
        curr = prev.GetNext().Get();
      } else {
        prev = curr;
        curr = curr.GetNext().Get();
      }
    }
    return removed;
  }


  public Set<Data> SubSet(Predicate<Data> condition) {
    LLChainBase<Data> subchain = NewChain(0L, null, null);
    TraverseBackward(dat -> {
      if (condition.Apply(dat)) subchain.Insert(dat);
      return false;
    });
    return (Set<Data>) subchain;
  }

  protected class ChainRefFIterator implements MutableForwardIterator<Box<LLNode<Data>>> {
    private Box<LLNode<Data>> currentref;

    public ChainRefFIterator() {
      Reset();
    }

    @Override
    public boolean IsValid() {
      return !currentref.IsNull();
    }

    @Override
    public Box<LLNode<Data>> GetCurrent() {
      if (!IsValid()) throw new IllegalStateException("LLChainBase ChainRefFIterator GetCurrent: iterator is invalid");
      return currentref;
    }

    @Override
    public void SetCurrent(Box<LLNode<Data>> dat) {
      if (dat == null) return;
      currentref = dat;
    }

    @Override
    public void Reset() {
      currentref = headref;
    }

    @Override
    public Box<LLNode<Data>> DataNNext() {
      if (!IsValid()) throw new IllegalStateException("LLChainBase ChainRefFIterator DataNNext: iterator is invalid");
      Box<LLNode<Data>> ret = currentref;
      currentref = currentref.Get().GetNext();
      return ret;
    }

    public void MoveTo(ChainRefFIterator iter) {
      if (iter == null) return;
      this.currentref = iter.GetCurrent();
    }
  }

  protected class ChainRefBIterator implements MutableBackwardIterator<Box<LLNode<Data>>> {
    private Stack<Box<LLNode<Data>>> stack = new WStack<>();

     protected Box<LLNode<Data>> currentref;

    public ChainRefBIterator() {
      Reset();
    }

    @Override
    public boolean IsValid() {
      return !currentref.IsNull();
    }

    @Override
    public Box<LLNode<Data>> GetCurrent() {
      if (!IsValid()) throw new IllegalStateException("LLChainBase ChainRefBIterator GetCurrent: iterator is invalid");
      return currentref;
    }

    @Override
    public void SetCurrent(Box<LLNode<Data>> dat) {
      if (dat == null) return;
      currentref = dat;
    }

    @Override
    public void Reset() {
      currentref = tailref;
      ResetStack();
    }

    @Override
    public Box<LLNode<Data>> DataNPrev() {
      if (!IsValid()) throw new IllegalStateException("LLChainBase ChainRefBIterator DataNPrev: iterator is invalid");
      Box<LLNode<Data>> ret = currentref;
      currentref = stack.IsEmpty() ? new Box<>() : stack.TopNPop();
      return ret;
    }

    public void ResetStack() {
      stack.Clear();
      Box<LLNode<Data>> node = headref;
      while (node.Get() != null && node.Get() != tailref.Get()) {
        stack.Push(node);
        node = node.Get().GetNext();
      }
    }
  }

  protected class ChainFIterator implements MutableForwardIterator<Data> {
    private ChainRefFIterator refiter;

    public ChainFIterator() {
      refiter = (ChainRefFIterator) FRefIterator();
    }

    @Override
    public boolean IsValid() {
      return refiter.IsValid();
    }

    @Override
    public Data GetCurrent() {
      return refiter.GetCurrent().Get().Get();
    }

    @Override
    public void Next() {
      refiter.DataNNext();
    }

    @Override
    public void Reset() {
      refiter.Reset();
    }

    @Override
    public Data DataNNext() {
      return refiter.DataNNext().Get().Get();
    }

    @Override
    public void SetCurrent(Data data) {
      if (data == null) return;
      refiter.GetCurrent().Get().Set(data);
    }
  }

  protected class ChainBIterator implements MutableBackwardIterator<Data> {
    private ChainRefBIterator refiter;

    public ChainBIterator() {
      refiter = (ChainRefBIterator) BRefIterator();
    }

    @Override
    public boolean IsValid() {
      return refiter.IsValid();
    }

    @Override
    public Data GetCurrent() {
      return refiter.GetCurrent().Get().Get();
    }

    @Override
    public void Prev() {
      if (this.IsValid()) {
        refiter.DataNPrev();
      }
    }

    @Override
    public void Reset() {
      refiter.Reset();
    }

    @Override
    public Data DataNPrev() {
      return refiter.DataNPrev().Get().Get();
    }

    @Override
    public void SetCurrent(Data data) {
      if (data == null) return;
      refiter.GetCurrent().Get().Set(data);
    }
  }
}
