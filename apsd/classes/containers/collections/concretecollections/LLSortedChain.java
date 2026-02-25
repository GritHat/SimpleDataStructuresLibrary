package apsd.classes.containers.collections.concretecollections;

import apsd.classes.containers.collections.concretecollections.bases.LLChainBase;
import apsd.classes.containers.collections.concretecollections.bases.LLNode;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.SortedChain;

/** Object: Concrete sorted chain implementation on linked-list. */
public class LLSortedChain<Data extends Comparable<? super Data>> extends LLChainBase<Data> implements SortedChain<Data> { // Must extend LLChainBase and implement SortedChain

  public LLSortedChain() {}

  public LLSortedChain(LLSortedChain<Data> chn) {
    if (chn == null) return;
    InsertAll(chn);
  }

  public LLSortedChain(TraversableContainer<Data> con) {
    if (con == null) return;
    con.TraverseForward(dat -> {
      Insert(dat);
      return false;
    });
  }

  protected LLSortedChain(long size, LLNode<Data> head, LLNode<Data> tail) {
    super(size, head, tail);
  }

  // NewChain
  @Override
  protected LLChainBase<Data> NewChain(long size, LLNode<Data> head, LLNode<Data> tail) {
    return new LLSortedChain<Data>(size, head, tail);
  }

  /* ************************************************************************ */
  /* Specific member functions of LLSortedChain                               */
  /* ************************************************************************ */

  // ...
  protected LLNode<Data> PredFind(Data dat) {
    if (dat == null) return null;
    if (Size().ToLong() == 0L) return null;
    long start = 0L;
    long end = Size().ToLong() - 1L;
    long mid = end / 2L;
    LLNode<Data> candidateNode = null;
    ChainRefFIterator fiterStart = (ChainRefFIterator) FRefIterator();
    ChainRefFIterator fiter = new ChainRefFIterator();
    fiter.MoveTo(fiterStart);
    while (start <= end) {
      fiter.Next(mid - start);
      LLNode<Data> midNode = fiter.GetCurrent().Get();
      int cmp = dat.compareTo(midNode.Get());
      if (cmp <= 0) {
        end = mid - 1L;
      } else {
        candidateNode = midNode;
        start = mid + 1L;
        fiterStart.MoveTo(fiter);
        if (fiterStart.IsValid()) fiterStart.Next();
      }
      if (!fiterStart.IsValid()) break;
      fiter.MoveTo(fiterStart);
      mid = start + (end - start) / 2L;
    }
    return candidateNode;
  }

  protected LLNode<Data> SuccFind(Data dat) {
    if (dat == null) return null;
    if (Size().ToLong() == 0L) return null;
    long start = 0L;
    long end = Size().ToLong() - 1L;
    long mid = end / 2L;
    LLNode<Data> candidateNode = null;
    ChainRefFIterator fiterStart = (ChainRefFIterator) FRefIterator();
    ChainRefFIterator fiter = new ChainRefFIterator();
    fiter.MoveTo(fiterStart);
    while (start <= end) {
      fiter.Next(mid - start);
      LLNode<Data> midNode = fiter.GetCurrent().Get();
      int cmp = dat.compareTo(midNode.Get());
      if (cmp < 0) {
        candidateNode = midNode;
        end = mid - 1L;
      } else {
        start = mid + 1L;
        fiterStart.MoveTo(fiter);
        fiterStart.Next();
      }
      if (!fiterStart.IsValid()) break;
      fiter.MoveTo(fiterStart);
      mid = (start + end) / 2L;
    }
    return candidateNode;
  }

  public LLNode<Data> PredPredFind(Data dat) {
    if (dat == null) return null;
    ChainRefFIterator fiter = new ChainRefFIterator(), fiterStart = (ChainRefFIterator) FRefIterator();
    long len = Size().ToLong(), mid, cmp;
    LLNode<Data> candidateNode = null, tmp = null, midNode = null;
    while (len >= 2L) {
      mid = len / 2L;
      fiter.MoveTo(fiterStart);
      fiter.Next(mid - 1L);
      tmp = fiter.GetCurrent().Get();
      fiter.Next();
      midNode = fiter.GetCurrent().Get();
      cmp = dat.compareTo(midNode.Get());
      if (cmp <= 0) {
        len = mid;
      } else {
        candidateNode = tmp;
        fiterStart.MoveTo(fiter);
        len -= mid;
      }
    }
    return candidateNode;
  }

  public LLNode<Data> SuccPredFind(Data dat) {
    if (dat == null) return null;
    if (Size().ToLong() == 0L) return null;
    LLNode<Data> predNode = PredFind(dat);
    if (predNode != null) {
      return predNode.GetNext().Get();
    } else {
      return headref.Get();
    }
  }

  public LLNode<Data> PredSuccFind(Data dat) {
    if (dat == null) return null;
    if (Size().ToLong() == 0L) return null;
    ChainRefFIterator fiter = new ChainRefFIterator(), fiterStart = (ChainRefFIterator) FRefIterator();
    long len = Size().ToLong(), mid, cmp;
    LLNode<Data> candidateNode = null, tmp = null, midNode = null;
    while (len >= 2L) {
      mid = len / 2L;
      fiter.MoveTo(fiterStart);
      fiter.Next(mid - 1L);
      tmp = fiter.GetCurrent().Get();
      fiter.Next();
      midNode = fiter.GetCurrent().Get();
      cmp = dat.compareTo(midNode.Get());
      if (cmp < 0) {
        if (tmp.Get().compareTo(dat) <= 0) {
          candidateNode = tmp;
        }
        len = mid;
      } else {
        fiterStart.MoveTo(fiter);
        len -= mid;
      }
    }
    return candidateNode;
  }

  /* ************************************************************************ */
  /* Override specific member functions from InsertableContainer              */
  /* ************************************************************************ */

  // ...
  @Override
  public boolean Insert(Data dat) {
    if (dat == null) return false;
    long oldSize = Size().ToLong();
    LLNode<Data> predNode = PredFind(dat);
    if (predNode == null) {
      LLNode<Data> newNode = new LLNode<Data>(dat, headref.Get());
      headref.Set(newNode);
      if (tailref.Get() == null) {
        tailref.Set(newNode);
      }
    } else {
      LLNode<Data> newNode = new LLNode<Data>(dat, predNode.GetNext().Get());
      predNode.SetNext(newNode);
      if (predNode == tailref.Get()) {
        tailref.Set(newNode);;
      }
    }
    size.Increment();
    return Size().ToLong() > oldSize;
  }

  /* ************************************************************************ */
  /* Override specific member functions from RemovableContainer               */
  /* ************************************************************************ */

  // ...
  @Override
  public boolean Remove(Data dat) {
    if (dat == null) return false;
    LLNode<Data> predNode = PredFind(dat);
    if (predNode == null) {
      // Check if head is the target
      LLNode<Data> headNode = headref.Get();
      if (headNode != null && headNode.Get().equals(dat)) {
        headref.Set(headNode.GetNext().Get());
        if (headref.Get() == null) tailref.Set(null);
        size.Decrement();
        return true;
      } else return false;
    } else {
      LLNode<Data> targetNode = predNode.GetNext().Get();
      if (targetNode != null && targetNode.Get().equals(dat)) {
        predNode.SetNext(targetNode.GetNext().Get());
        if (targetNode == tailref.Get()) tailref.Set(predNode);
        size.Decrement();
        return true;
      } else return false;
    }
  }

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  // ...
  @Override
  public Natural Search(Data dat) {
    if (dat == null) return null;
    if (Size().ToLong() == 0L) return null;
    long start = 0L;
    long end = Size().ToLong() - 1L;
    long mid = (start + end) / 2L;
    ChainRefFIterator fiterStart = (ChainRefFIterator) FRefIterator();
    ChainRefFIterator fiter = new ChainRefFIterator();
    fiter.MoveTo(fiterStart);
    Natural candidateIdx = null;
    while (start <= end) {
      fiter.Next(mid - start);
      LLNode<Data> midNode = fiter.GetCurrent().Get();
      int cmp = dat.compareTo(midNode.Get());
      if (cmp < 0) {
        end = mid - 1;
      } else if (cmp > 0) {
        start = mid + 1;
        fiterStart.MoveTo(fiter);
        fiterStart.Next();
      } else {
        candidateIdx = Natural.Of(mid);
        end = mid - 1;
      }
      if (fiterStart.IsValid()) fiter.MoveTo(fiterStart);
      mid = (start + end) / 2L;
    }
    return candidateIdx;
  }

  /* ************************************************************************ */
  /* Override specific member functions from SortedSequence                   */
  /* ************************************************************************ */

  // ...
  @Override
  public Natural SearchPredecessor(Data dat) {
    if (dat == null) return null;
    long start = 0L;
    long end = Size().ToLong() - 1L;
    long mid = (start + end) / 2L;
    Natural result = null;
    ChainRefFIterator fiterStart = (ChainRefFIterator) FRefIterator();
    ChainRefFIterator fiter = new ChainRefFIterator();
    fiter.MoveTo(fiterStart);
    while (start <= end) {
      fiter.Next(mid - start);
      LLNode<Data> midNode = fiter.GetCurrent().Get();
      int cmp = dat.compareTo(midNode.Get());
      if (cmp <= 0) {
        end = mid - 1;
      } else {
        result = Natural.Of(mid);
        start = mid + 1;
        fiterStart.MoveTo(fiter);
        fiterStart.Next();
      }
      if (!fiterStart.IsValid()) break;
      fiter.MoveTo(fiterStart);
      mid = (start + end) / 2L;
    }
    return result;
  }

  @Override
  public Natural SearchSuccessor(Data dat) {
    if (dat == null) return null;
    long start = 0L;
    long end = Size().ToLong() - 1L;
    long mid = (start + end) / 2L;
    Natural result = null;
    ChainRefFIterator fiterStart = (ChainRefFIterator) FRefIterator();
    ChainRefFIterator fiter = new ChainRefFIterator();
    fiter.MoveTo(fiterStart);
    while (start <= end) {
      fiter.Next(mid - start);
      LLNode<Data> midNode = fiter.GetCurrent().Get();
      int cmp = dat.compareTo(midNode.Get());
      if (cmp < 0) {
        result = Natural.Of(mid);
        end = mid - 1;
      } else {
        start = mid + 1;
        fiterStart.MoveTo(fiter);
        fiterStart.Next();
      }
      if (!fiterStart.IsValid()) break;
      fiter.MoveTo(fiterStart);
      mid = (start + end) / 2L;
    }
    return result;
  }

  /* ************************************************************************ */
  /* Override specific member functions from OrderedSet                       */
  /* ************************************************************************ */

  // ...
  @Override
  public Data Predecessor(Data dat) {
    if (dat == null) return null;
    LLNode<Data> predNode = PredFind(dat);
    if (predNode != null) {
      return predNode.Get();
    } else {
      return null;
    }
  }

  @Override
  public Data Successor(Data dat) {
    if (dat == null) return null;
    LLNode<Data> succNode = SuccFind(dat);
    if (succNode != null) {
      return succNode.Get();
    } else return null;
  }

  @Override
  public Data PredecessorNRemove(Data dat) {
    if (dat == null) return null;
    LLNode<Data> predPredNode = PredPredFind(dat);
    if (predPredNode != null) {
      Data ret = predPredNode.GetNext().Get().Get();
      if (tailref.Get() == predPredNode.GetNext().Get()) {
        tailref.Set(predPredNode);
      }
      predPredNode.SetNext(predPredNode.GetNext().Get().GetNext().Get());
      size.Decrement();
      return ret;
    } else {
      // Check if head is the target
      LLNode<Data> headNode = headref.Get();
      if (headNode != null && headNode.Get().compareTo(dat) < 0) {
        Data ret = headNode.Get();
        headref.Set(headNode.GetNext().Get());
        if (headref.Get() == null) {
          tailref.Set(null);
        }
        size.Decrement();
        return ret;
      } else return null;
    }
  }

  @Override
  public Data SuccessorNRemove(Data dat) {
    if (dat == null) return null;
    LLNode<Data> predSuccNode = PredSuccFind(dat);
    if (predSuccNode != null) {
      Data ret = predSuccNode.GetNext().Get().Get();
      if (tailref.Get() == predSuccNode.GetNext().Get()) {
        tailref.Set(predSuccNode);
      }
      predSuccNode.SetNext(predSuccNode.GetNext().Get().GetNext().Get());
      size.Decrement();
      return ret;
    } else {
      // Check if head is the target
      LLNode<Data> headNode = headref.Get();
      if (headNode != null && headNode.Get().compareTo(dat) > 0) {
        Data ret = headNode.Get();
        headref.Set(headNode.GetNext().Get());
        if (headref.Get() == null) {
          tailref.Set(null);
        }
        size.Decrement();
        return ret;
      } else {
        return null;
      }
    }
  }

  /* ************************************************************************ */
  /* Override specific member functions from Chain                            */
  /* ************************************************************************ */

  // ...
  @Override
  public boolean InsertIfAbsent(Data dat) {
    if (dat == null) return false;
    LLNode<Data> predNode = PredFind(dat);
    if (predNode == null) {
      LLNode<Data> headNode = headref.Get();
      if (headNode == null || !headNode.Get().equals(dat)) {
        LLNode<Data> newNode = new LLNode<Data>(dat, headref.Get());
        headref.Set(newNode);
        if (tailref.Get() == null) {
          tailref.Set(newNode);
        }
        size.Increment();
        return true;
      } else return false;
    } else {
      LLNode<Data> targetNode = predNode.GetNext().Get();
      if (targetNode == null || !targetNode.Get().equals(dat)) {
        LLNode<Data> newNode = new LLNode<Data>(dat, predNode.GetNext().Get());
        predNode.SetNext(newNode);
        if (predNode == tailref.Get()) {
          tailref.Set(newNode);;
        }
        size.Increment();
        return true;
      } else return false;
    }
  }

  @Override
  public void RemoveOccurrences(Data dat) {
    if (dat == null) return;
    LLNode<Data> predNode = PredFind(dat);
    if (predNode == null) {
      // Check if head is the target
      LLNode<Data> headNode = headref.Get();
      while (headNode != null && headNode.Get().equals(dat)) {
        headref.Set(headNode.GetNext().Get());
        size.Decrement();
        headNode = headref.Get();
      }
      if (headref.Get() == null) {
        tailref.Set(null);
      }
    } else {
      LLNode<Data> currentNode = predNode.GetNext().Get();
      while (currentNode != null && currentNode.Get().equals(dat)) {
        predNode.SetNext(currentNode.GetNext().Get());
        size.Decrement();
        if (currentNode == tailref.Get()) {
          tailref.Set(predNode);
          break;
        }
        currentNode = predNode.GetNext().Get();
      }
    }
  }

}
