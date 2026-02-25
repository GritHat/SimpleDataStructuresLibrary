package apsd.classes.containers.sequences;

import apsd.classes.containers.sequences.abstractbases.DynLinearVectorBase;
import apsd.classes.containers.sequences.abstractbases.VectorBase;
import apsd.classes.utilities.Box;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;

/** Object: Concrete dynamic (linear) vector implementation. */
public class DynVector<Data> extends DynLinearVectorBase<Data> { // Must extend DynLinearVectorBase

  public DynVector() {
    super(Natural.ZERO);
  }

  // public DynVector(Natural inisize)
  public DynVector(Natural inisize) {
    super(inisize);
  }

  // public DynVector(TraversableContainer<Data> con)
  public DynVector(TraversableContainer<Data> con) {
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

  // protected DynVector(Data[] arr)
  protected DynVector(Data[] arr) {
    super(Natural.ZERO);
    if (arr == null) return;
    ArrayAlloc(Natural.Of(arr.length));
    for (int i = 0; i < arr.length; i++) {
      this.arr[i] = arr[i];
    }
    this.size = arr.length;
  }

  // NewVector
  @Override
  protected VectorBase<Data> NewVector(Data[] arr) {
    return new DynVector<Data>(arr);
  }


}
