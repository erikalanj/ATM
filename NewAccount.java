import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
public class NewAccount {

  private static int lastAccountNumber = 10000000;

  private int accountNumber;

  private static String alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890#_!%^&@";

  private String name;

  private static boolean headerWritten = false;

  public NewAccount() {

    this.accountNumber = lastAccountNumber++;

  }

  public static String generatePassword() {

    StringBuilder password = new StringBuilder();

    Random random = new Random();

    for (int i = 0; i < 8; i++) {

      password.append(alphabets.charAt(random.nextInt(alphabets.length())));

    }

    return password.toString();

  }

  public static void createAccount(String name, String dob) {

    NewAccount newAccount = new NewAccount();

    int accountNumber = newAccount.accountNumber;

    String password = generatePassword();

    AccessAccount accessAccount = new AccessAccount(name, accountNumber, dob);

    try (FileWriter writer = new FileWriter("accountInfo.txt", true)) {

      if (!headerWritten) {

        writer.write("Username Password AccountID DateOfBirth\n");

        headerWritten = true;

      }

      writer.write(name + " " + password + " " + accountNumber + " " + dob + "\n");

    } catch (IOException e) {

      e.printStackTrace();

    }

  }

  public static int getLastAccountNumber() {

    return lastAccountNumber;

  }

  boolean checkAccount(String name) {

    boolean flag = false;

    try {

      FileInputStream fis = new FileInputStream("accountinfo.txt");

      Scanner scan = new Scanner(fis);

      ArrayList < String > list = new ArrayList < String > ();

      while (scan.hasNextLine()) {

        list.add(scan.nextLine());

      }

      scan.close();

      for (String x: list) {

        if (x.contains(name))

          flag = true;

      }

    } catch (Exception e) {

    }

    return flag;

  }
}
