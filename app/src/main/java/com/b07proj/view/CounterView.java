package com.b07proj.view;

import com.b07proj.viewmodel.CounterViewModel;

import java.util.Scanner;

public class CounterView {
  private final CounterViewModel vm;

  public CounterView(CounterViewModel vm) {
    this.vm = vm;

    // Observe ViewModel state
    vm.count.observe(value -> {
      System.out.println("Count updated: " + value);
    });
  }

  public void start() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Commands: i = increment, r = reset, q = quit");

    while (true) {
      System.out.print("> ");
      String cmd = scanner.nextLine();

      switch (cmd) {
        case "i":
          vm.onIncrementClicked();
          break;
        case "r":
          vm.onResetClicked();
          break;
        case "q":
          System.out.println("Bye!");
          return;
        default:
          System.out.println("Unknown command.");
      }
    }
  }
}
