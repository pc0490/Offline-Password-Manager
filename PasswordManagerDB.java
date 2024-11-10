import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PasswordManagerDB implements PasswordManagerInterface {
    private Connection connect() {
        String url = "jdbc:mysql://localhost:3306/password_manager_db";
        String user = "root";  // replace with your MySQL username
        String password = "Admin@123";  // replace with your MySQL password
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    @Override
    public int add_Acc(String account, String passwd) {
        String query = "INSERT INTO passwords (account_name, password) VALUES (?, ?)";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, account);
            stmt.setString(2, passwd);
            return stmt.executeUpdate(); // return 1 if successful
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Return 0 if insertion failed
    }

    @Override
    public String get_Acc(String account) {
        String query = "SELECT password FROM passwords WHERE account_name = ?";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, account);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("password"); // Return the encrypted password
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if account is not found
    }

    @Override
    public int remove_Acc(String account) {
        String query = "DELETE FROM passwords WHERE account_name = ?";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, account);
            int rows = stmt.executeUpdate();
            return rows;  // Return number of rows deleted
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
