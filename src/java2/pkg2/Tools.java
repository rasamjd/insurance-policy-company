package java2.pkg2;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Tools {
  
  public static Scanner sc = new Scanner(System.in);
  
  public static int getInputInt(String msg) {
    int res = 0;
    while (res == 0) {  
      try {
        System.out.println(msg);
        res = sc.nextInt();    
      } catch (InputMismatchException e) {
        
        Tools.printFailure("Enter a number!");
      }
      sc.nextLine();
    } 
    return res;
  }

  public static String getInputString(String msg) {
    System.out.println(msg);
    String res = sc.nextLine();
    return res;  
  }

  public static void printSuccess(String msg) {
    System.out.println(ConsoleColors.GREEN + msg + ConsoleColors.RESET);
  }
  
  public static void printFailure(String msg) {
    System.out.println(ConsoleColors.RED + msg + ConsoleColors.RESET);
  }

  public static void printInfo(String msg) {
    System.out.println("\n" + ConsoleColors.BLUE_BOLD + msg + ConsoleColors.RESET);
  }
  
  public static void printInfoBold(String msg) {
    System.out.println("\n\n" + ConsoleColors.CYAN_BOLD_BRIGHT + "[ " + msg + " ]"  + ConsoleColors.RESET);
  }

  public static void printTestExpectation(String msg) {
    System.out.println("\n" + ConsoleColors.YELLOW_BRIGHT + msg + ConsoleColors.RESET);
  }

  public static void printTitle(String msg) {
    clearConsole();
    System.out.println("\n\n" + ConsoleColors.CYAN_BOLD_BRIGHT + "[ " + msg + " ]"  + ConsoleColors.RESET);
  }

  public static void clearConsole() {
    System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
  }

  public static String subInfoText(String txt) {
    return (ConsoleColors.BLUE + txt + ConsoleColors.RESET);
  }

  public static void backToMenu() {
    System.out.println("\n\nPress enter to continue");
    sc.nextLine();
  }
}
