package uts_pbol_2021130019;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Nathan
 */
public class DBConnection {

    public Connection dbKoneksi;
    public Statement statement;
    public PreparedStatement preparedStatement;

    public DBConnection() {
        this.dbKoneksi = null;
    }

    public void bukaKoneksi() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            dbKoneksi = DriverManager.getConnection("jdbc:mysql://localhost:3306/coba_pbol?user=root&password=");
        } catch (ClassNotFoundException | SQLException e) {
        }
    }

    public void tutupKoneksi() {
        try {
            if (statement != null) {
                statement.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (dbKoneksi != null) {
                dbKoneksi.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
