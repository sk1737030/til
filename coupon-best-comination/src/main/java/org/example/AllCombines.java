package org.example;

public class AllCombines {
  public static void main(String[] args) {
    int maxNumber = 27;

    for (int i = 0; i < maxNumber; i++) {
      int temp = i;
      int digit1 = temp % 3;
      temp /= 3;
      int digit2 = temp % 3;
      temp /= 3;
      int digit3 = temp % 3;

      System.out.printf("%d: %d%d%d\n", i, digit1, digit2, digit3);
    }
  }
}
