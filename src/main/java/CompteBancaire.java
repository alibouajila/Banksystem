package main.java;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class CompteBancaire {
    private String cin;
    private String username;
    private double solde;
    private String num_compte;
    private int type_compte;

    public CompteBancaire(String cin, String username, int type) {
        this.cin = cin;
        this.username = username;
        this.type_compte = type;
        this.solde = 0;
        this.num_compte = genererNumeroCompte();
        saveToDatabase();
    }

    public CompteBancaire(String cin, String username, int type, String num_compte, double solde) {
        this.cin = cin;
        this.username = username;
        this.type_compte = type;
        this.num_compte = num_compte;
        this.solde = solde;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
        updateBalanceInDatabase();
    }

    public String getNum() {
        return num_compte;
    }

    private String genererNumeroCompte() {
        Random random = new Random();
        StringBuilder numeroCompte = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            numeroCompte.append(random.nextInt(10));
        }
        return numeroCompte.toString();
    }

    public void deposit(double amount) {
        this.solde += amount;
        updateBalanceInDatabase();
        System.out.println("Deposit successful !");
    }

    public void withdraw(double amount) {
        if (amount <= this.solde) {
            this.solde -= amount;
            updateBalanceInDatabase();
            System.out.println("Withdrawal successful!");
        } else {
            System.out.println("Insufficient funds!");
        }
    }

    public void transfer(CompteBancaire toAccount, double amount) {
        if (this.solde >= amount) {
            this.solde -= amount;
            toAccount.setSolde(toAccount.getSolde()+amount);
            updateBalanceInDatabase();
            System.out.println("Transfer successful!");
        } else {
            System.out.println("Insufficient funds!");
        }
    }

    public void consulter() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "CIN: " + cin + "\nUsername: " + username + "\nAccount Type: " + type_compte +
                "\nAccount Number: " + num_compte + "\nBalance: " + solde +
                "\n----------------------------------------";
    }

    private void saveToDatabase() {
        try (Connection connection = Database.getConnection()) {
            String query = "INSERT INTO accounts (account_num, cin, username, balance, account_type) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, num_compte);
            ps.setString(2, cin);
            ps.setString(3, username);
            ps.setDouble(4, solde);
            ps.setInt(5, type_compte);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateBalanceInDatabase() {
        try (Connection connection = Database.getConnection()) {
            String query = "UPDATE accounts SET balance = ? WHERE account_num = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setDouble(1, solde);
            ps.setString(2, num_compte);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
