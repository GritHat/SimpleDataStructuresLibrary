package apsd.classes.containers.sequences;

import apsd.classes.containers.sequences.abstractbases.CircularVectorBase;
import apsd.classes.containers.sequences.abstractbases.VectorBase;
import apsd.classes.utilities.Box;
import apsd.classes.utilities.Natural;
// import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.base.TraversableContainer;

/** Object: Concrete (static) circular vector implementation. */
public class CircularVector<Data> extends CircularVectorBase<Data> { // Must extend CircularVectorBase

  public CircularVector() {
    super(Natural.ZERO);
  }

  public CircularVector(Natural inisize) {
    super(inisize);
  }

  public CircularVector(TraversableContainer<Data> con) {
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

  protected CircularVector(Data[] arr) {
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
    return new CircularVector<Data>(arr);
  }

}
