package com.b07proj;

import com.b07proj.view.CounterView;
import com.b07proj.viewmodel.CounterViewModel;

public class CounterCliApp {
  public static void main(String[] args) {
    CounterViewModel vm = new CounterViewModel();
    CounterView view = new CounterView(vm);
    view.start();
  }
}
