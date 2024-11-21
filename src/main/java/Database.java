package main.java;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class Database {

    private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String USER = "ali";
    private static final String PASSWORD = "0000";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
 
}
