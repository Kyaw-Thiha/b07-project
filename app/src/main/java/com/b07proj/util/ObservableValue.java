package com.b07proj.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ObservableValue<T> {
  private T value;
  private final List<Consumer<T>> observers = new ArrayList<>();

  public ObservableValue(T initial) {
    this.value = initial;
  }

  public T get() {
    return value;
  }

  public void set(T newValue) {
    this.value = newValue;
    for (Consumer<T> obs : observers) {
      obs.accept(newValue);
    }
  }

  public void observe(Consumer<T> observer) {
    observers.add(observer);
    observer.accept(value); // send initial value
  }
}
