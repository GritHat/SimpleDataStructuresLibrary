package apsd.classes.containers.sequences;

import apsd.classes.containers.sequences.abstractbases.DynCircularVectorBase;
import apsd.classes.containers.sequences.abstractbases.VectorBase;
import apsd.classes.utilities.Box;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;

/** Object: Concrete dynamic circular vector implementation. */
public class DynCircularVector<Data> extends DynCircularVectorBase<Data> { // Must extend DynCircularVectorBase

  public DynCircularVector() {
    super(Natural.ZERO);
  }

  public DynCircularVector(Natural inisize) {
    super(inisize);
  }

  public DynCircularVector(TraversableContainer<Data> con) {
    super(Natural.ZERO);
    if (con == null) return;
    ArrayAlloc(Natural.Of(con.Size().ToLong()));
    Box<Natural> idx = new Box<>(Natural.ZERO);
    this.size = con.Size().ToLong();
    con.TraverseForward(dat -> {
      SetAt(dat, idx.Get());
      idx.Set(idx.Get().Increment());
      return false;
    });
  }

  protected DynCircularVector(Data[] arr) {
    super(Natural.ZERO);
    if (arr == null) return;
    ArrayAlloc(Natural.Of(arr.length));
    for (int i = 0; i < arr.length; i++) {
      this.arr[i] = arr[i];
    }
    this.size = arr.length;
  }

  @Override
  protected VectorBase<Data> NewVector(Data[] arr) {
    return new DynCircularVector<Data>(arr);
  }

}
