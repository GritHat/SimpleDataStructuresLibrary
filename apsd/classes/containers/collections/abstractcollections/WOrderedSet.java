package apsd.classes.containers.collections.abstractcollections;

import apsd.classes.containers.collections.abstractcollections.bases.WOrderedSetBase;
import apsd.classes.containers.collections.concretecollections.VSortedChain;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.Chain;
import apsd.interfaces.containers.collections.SortedChain;
import apsd.interfaces.containers.iterators.ForwardIterator;

/** Object: Wrapper ordered set implementation via ordered chain. */
public class WOrderedSet<Data extends Comparable<? super Data>> extends WOrderedSetBase<Data, SortedChain<Data>> { // Must extend WOrderedSetBase

  public WOrderedSet() {
    super();
  }

  public WOrderedSet(SortedChain<Data> chn) {
    if (chn == null) return;
    this.chn = (SortedChain<Data>) chn.SubChain(null, null);
    ForwardIterator<Data> it = chn.FIterator();
    while (it.IsValid()) {
      Data dat = it.DataNNext();
      this.chn.InsertIfAbsent(dat);
    }
  }

  public WOrderedSet(SortedChain<Data> chn, TraversableContainer<Data> con) {
    if (chn != null) {
      this.chn = (SortedChain<Data>) chn.SubChain(null, null);
      ForwardIterator<Data> it = chn.FIterator();
      while (it.IsValid()) {
        Data dat = it.DataNNext();
        this.chn.InsertIfAbsent(dat);
      }
    }
    if (con == null) return;
    con.TraverseForward(dat -> {
      this.chn.InsertIfAbsent(dat);
      return false;
    });
  
  }

  public WOrderedSet(Chain<Data> chn, TraversableContainer<Data> con) {
    if (chn != null) {
    chn.TraverseForward(dat -> {
      this.chn.InsertIfAbsent(dat);
      return false;
    });
    }
    if (con == null) return;
    con.TraverseForward(dat -> {
      this.chn.InsertIfAbsent(dat);
      return false;
    });
  }

  public WOrderedSet(TraversableContainer<Data> con) {
    if (con == null) return;
    con.TraverseForward(dat -> {
      this.chn.InsertIfAbsent(dat);
      return false;
    });
  }


  // ...

  @Override
  protected void ChainAlloc() { chn = new VSortedChain<>(); }


}
