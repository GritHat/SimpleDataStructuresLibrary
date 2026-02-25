package apsd.classes.containers.sequences;

import apsd.classes.containers.sequences.abstractbases.LinearVectorBase;
import apsd.classes.containers.sequences.abstractbases.VectorBase;
import apsd.classes.utilities.Box;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;

/** Object: Concrete (static linear) vector implementation. */
public class Vector<Data> extends LinearVectorBase<Data> { // Must extend LinearVectorBase

  public Vector() {
    super(Natural.ZERO);
  }

  public Vector(Natural inisize) {
    super(inisize);
  }

  public Vector(TraversableContainer<Data> con) {
    super(Natural.ZERO);
    if (con == null) return;
    ArrayAlloc(Natural.Of(con.Size().ToLong()));
    Box<Natural> idx = new Box<>(Natural.ZERO);
    con.TraverseForward(dat -> {
      SetAt(dat, idx.Get());
      idx.Set(idx.Get().Increment());
      return false;
    });
  }

  protected Vector(Data[] arr) {
    super(Natural.ZERO);
    if (arr == null) return;
    ArrayAlloc(Natural.Of(arr.length));
    for (int i = 0; i < arr.length; i++) {
      this.arr[i] = arr[i];
    }
  }

  // NewVector
  @Override
  protected VectorBase<Data> NewVector(Data[] arr) {
    return new Vector<Data>(arr);
  }

}
