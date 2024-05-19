import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {
    public static void main(String[] args) {
        System.out.println("Welcome to my SQL command executor!");

        // Example SQL command to execute
        String sql = "select * from users";

        // Database connection properties
        String dbURL = "jdbc:mysql://localhost:3306/slotify";
        String username = "root";
        String password = "Elif.sude12345!"; // Replace with your actual password

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver"); // Note the updated driver class name
            
            // Create a connection
            try (Connection dbConnect = DriverManager.getConnection(dbURL, username, password)) {
                System.out.println("Connected to the database.");

                try (Statement sqlSt = dbConnect.createStatement()) {
                    // Determine whether the SQL command is a query or update
                    if (sql.trim().toLowerCase().startsWith("select")) {
                        // Execute query
                        try (ResultSet resultSet = sqlSt.executeQuery(sql)) {
                            ResultSetMetaData rsmd = resultSet.getMetaData();
                            int columnsNumber = rsmd.getColumnCount();

                            while (resultSet.next()) {
                                for (int i = 1; i <= columnsNumber; i++) {
                                    if (i > 1) System.out.print(", ");
                                    String columnValue = resultSet.getString(i);
                                    System.out.print(rsmd.getColumnName(i) + ": " + columnValue);
                                }
                                System.out.println();
                            }
                        }
                    } else {
                        // Execute update
                        int rowsAffected = sqlSt.executeUpdate(sql);
                        System.out.println("Rows affected: " + rowsAffected);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("SQL Error: " + ex.getMessage());
                }

                System.out.println("Execution completed.");
            } catch (SQLException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Connection Error: " + ex.getMessage());
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Driver Not Found: " + ex.getMessage());
        }
    }
}
