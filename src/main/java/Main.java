package main.java;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        Scanner ss = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nWelcome!");
            System.out.println("PRESS : \n1) To create an account \n2) To withdraw money\n3) To deposit money\n4) To transfer money\n5) To show all info\n6) Exit");
            int n = s.nextInt();
            switch (n) {
                case 1:
                    System.out.println("Enter your full name: ");
                    String username = ss.nextLine();
                    System.out.println("Enter your CIN number: ");
                    String cin = ss.nextLine();
                    System.out.println("Account type: ");
                    System.out.println("PRESS :\n1) Saving account\n2) Current account");
                    int type = s.nextInt();
                    CompteBancaire a=new CompteBancaire(cin, username, type);
                    System.out.println("Account created successfully!"+"\nAccount n° "+a.getNum());
                    break;

                case 2:
                    System.out.println("Enter the account number to withdraw from:");
                    String numToWithdraw = ss.nextLine();
                    CompteBancaire accountToWithdraw = findAccountByNum(numToWithdraw);
                    if (accountToWithdraw != null) {
                        System.out.println("Enter amount to withdraw:");
                        double amount = s.nextDouble();
                        accountToWithdraw.withdraw(amount);
                    } else {
                        System.out.println("Account not found.");
                    }
                    break;

                case 3:
                    System.out.println("Enter the account number to deposit into:");
                    String numToDeposit = ss.nextLine();
                    CompteBancaire accountToDeposit = findAccountByNum(numToDeposit);
                    if (accountToDeposit != null) {
                        System.out.println("Enter amount to deposit:");
                        double amount = s.nextDouble();
                        accountToDeposit.deposit(amount);
                    } else {
                        System.out.println("Account not found.");
                    }
                    break;

                case 4:
                    System.out.println("Enter the account number to transfer from:");
                    String numFrom = ss.nextLine();
                    CompteBancaire fromAccount = findAccountByNum(numFrom);
                    if (fromAccount != null) {
                        System.out.println("Enter the account number to transfer to:");
                        String numTo = ss.nextLine();
                        CompteBancaire toAccount = findAccountByNum(numTo);
                        if (toAccount != null) {
                            System.out.println("Enter amount to transfer:");
                            double amount = s.nextDouble();
                            fromAccount.transfer(toAccount, amount);
                        } else {
                            System.out.println("Recipient account not found.");
                        }
                    } else {
                        System.out.println("Sender account not found.");
                    }
                    break;

                case 5:
                    System.out.println("Enter the account number to view information:");
                    String numToView = ss.nextLine();
                    CompteBancaire accountToView = findAccountByNum(numToView);
                    if (accountToView != null) {
                        accountToView.consulter();
                    } else {
                        System.out.println("Account not found.");
                    }
                    break;

                case 6:
                    running = false;
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        s.close();
    }

    public static CompteBancaire findAccountByNum(String accountNum) {
        try (Connection connection = Database.getConnection()) {
            String query = "SELECT * FROM accounts WHERE account_num = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, accountNum);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new CompteBancaire(
                        rs.getString("cin"),
                        rs.getString("username"),
                        rs.getInt("account_type"),
                        rs.getString("account_num"),
                        rs.getDouble("balance")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
