package com.b07proj.viewmodel;

import com.b07proj.model.CounterModel;
import com.b07proj.util.ObservableValue;

public class CounterViewModel {
  private final CounterModel model = new CounterModel();

  // Expose the count as observable state
  public final ObservableValue<Integer> count = new ObservableValue<>(0);

  public void onIncrementClicked() {
    model.increment();
    count.set(model.get());
  }

  public void onResetClicked() {
    model.reset();
    count.set(model.get());
  }
}
