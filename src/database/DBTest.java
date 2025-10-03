package database;

import java.sql.Connection;
import java.sql.SQLException;

public class DBTest {
    public static void main(String[] args) {
        try {
            Connection conn = DBManager.getConnection();
            if (conn != null) {
                System.out.println("Connessione al database riuscita!");
                DBManager.closeConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}