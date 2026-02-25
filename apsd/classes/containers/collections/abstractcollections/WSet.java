package apsd.classes.containers.collections.abstractcollections;

import apsd.classes.containers.collections.abstractcollections.bases.WSetBase;
import apsd.classes.containers.collections.concretecollections.VList;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.Chain;

/** Object: Wrapper set implementation via chain. */
public class WSet<Data> extends WSetBase<Data, Chain<Data>> { // Must extend WSetBase

  public WSet() {}

  public WSet(Chain<Data> chn) {
    if (chn == null) return;
    this.chn = (Chain<Data>) chn.SubSequence(null, null);
    chn.TraverseForward(dat -> {
      this.chn.InsertIfAbsent(dat);
      return false;
    });
  }

  public WSet(TraversableContainer<Data> con) {
    if (con == null) return;
    con.TraverseForward(dat -> {
      this.chn.InsertIfAbsent(dat);
      return false;
    });
  }

  public WSet(Chain<Data> chn, TraversableContainer<Data> con) {
    if (chn != null) {
      this.chn = (Chain<Data>) chn.SubSequence(null, null);
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

  @Override
  protected void ChainAlloc() { chn = new VList<>(); }

}
