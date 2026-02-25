package apsd.classes.containers.collections.concretecollections;

import apsd.classes.containers.collections.concretecollections.bases.LLChainBase;
import apsd.classes.containers.collections.concretecollections.bases.LLNode;
import apsd.classes.utilities.Box;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.List;
import apsd.interfaces.containers.iterators.MutableBackwardIterator;
import apsd.interfaces.containers.iterators.MutableForwardIterator;
import apsd.interfaces.containers.sequences.MutableSequence;

/** Object: Concrete list implementation on linked-list. */
public class LLList<Data> extends LLChainBase<Data> implements List<Data> { // Must extend LLChainBase and implement List

  public LLList() {}

  public LLList(TraversableContainer<Data> con) {
    if (con == null) return;
    con.TraverseBackward(dat -> {
      if (dat != null) Insert(dat);
      return false;
    });
  }

  protected LLList(long size, LLNode<Data> head, LLNode<Data> tail) {
    super(size, head, tail);
  }

  // NewChain
  @Override
  protected LLChainBase<Data> NewChain(long size, LLNode<Data> head, LLNode<Data> tail) {
    return new LLList<Data>(size, head, tail);
  }

  /* ************************************************************************ */
  /* Override specific member functions from MutableIterableContainer         */
  /* ************************************************************************ */

  // ...
  @Override
  public MutableForwardIterator<Data> FIterator() {
    return (MutableForwardIterator<Data>) super.FIterator();
  }

  @Override
  public MutableBackwardIterator<Data> BIterator() {
    return (MutableBackwardIterator<Data>) super.BIterator();
  }

  /* ************************************************************************ */
  /* Override specific member functions from MutableSequence                  */
  /* ************************************************************************ */

  // ...
  @Override
  public void SetAt(Data dat, Natural pos) {
    if (pos == null) throw new NullPointerException("Natural is null: Index is null; Size: " + Size() + "!");
    if (dat == null) return;
    if (pos.ToLong() >= size.ToLong()) throw new IndexOutOfBoundsException("Index out of bounds: " + pos + "; Size: " + Size() + "!");
    if (pos.ToLong() == 0L) {
      headref.Get().Set(dat);
      return;
    }
    if (pos.ToLong() == size.ToLong() - 1L) {
      tailref.Get().Set(dat);
      return;
    }
    MutableForwardIterator<Data> it = FIterator();
    it.Next(pos);
    if (it.IsValid()) it.SetCurrent(dat);
  }

  @Override
  public MutableSequence<Data> SubSequence(Natural start, Natural end) {
    return (MutableSequence<Data>) super.SubSequence(start, end);
  }

  /* ************************************************************************ */
  /* Override specific member functions from InsertableAtSequence             */
  /* ************************************************************************ */

  // ...
  @Override
  public void InsertAt(Data dat, Natural pos) {
    if (pos == null) throw new NullPointerException("InsertAt: Index is null Size: " + Size().ToLong());
    if (dat == null) return;
    if (pos.ToLong() == 0L) InsertHead(dat);
    else if (pos.ToLong() == size.ToLong()) InsertTail(dat);
    else InsertInternal(dat, pos);
  }

  private void InsertHead(Data dat) {
    LLNode<Data> newNode = new LLNode<Data>(dat, headref.Get());
    headref.Set(newNode);
    if (size.ToLong() == 0L) {
      tailref.Set(newNode);
    }
    size.Increment();
  }

  private void InsertTail(Data dat) {
    LLNode<Data> newNode = new LLNode<Data>(dat, null);
    if (size.ToLong() == 0L) {
      headref.Set(newNode);
      tailref.Set(newNode);
    } else {
      tailref.Get().SetNext(newNode);
      tailref.Set(newNode);
    }
    size.Increment();
  }

  private void InsertInternal(Data dat, Natural pos) {
  long idx = ExcIfOutOfBound(pos);
    MutableForwardIterator<Box<LLNode<Data>>> it = FRefIterator();
    it.Next(Natural.Of(idx - 1L));
    if (it.IsValid()) {
      LLNode<Data> newNode = new LLNode<Data>(dat, it.GetCurrent().Get().GetNext().Get());
      it.GetCurrent().Get().SetNext(newNode);
      size.Increment();
    }
  }

  public List<Data> Reverse() {
    List<Data> reversed = new LLList<>();
    TraverseForward(dat -> {
      if (dat != null) reversed.Insert(dat);
      return false;
    });
    return reversed;
  }
}
