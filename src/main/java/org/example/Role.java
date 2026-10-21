package org.example;

import java.util.Scanner;

public interface Role {
  void showMenu();

  void handleAction(int choice, Scanner scanner);

  boolean authenticate(String password);
}




