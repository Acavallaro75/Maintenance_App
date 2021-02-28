package giba.model;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The ConnectToDatabase class is used to handle connections to the databases for this project.
 *
 * @author Andrew Cavallaro
 */
public class ConnectToDatabase {

  // Connection object //
  private Connection connection;

  // String for username to access the database //
  private static final String user = "root";

  // String for password to access the database //
  private static final String pass = "password";

  /**
   * The connectToMaintenance method connects the user to the Maintenance database.
   *
   * @throws ClassNotFoundException yes, it does
   * @throws SQLException yes, it does
   */
  @SuppressFBWarnings("DMI_CONSTANT_DB_PASSWORD")
  public void connectToMaintenance() throws ClassNotFoundException, SQLException {
    String url = "jdbc:mysql://localhost:3306/maintenance";
    Class.forName("com.mysql.cj.jdbc.Driver");
    connection = DriverManager.getConnection(url, user, pass);
  }

  /**
   * The connectToEmployee method connects the user to the GIBA_Employee database.
   *
   * @throws ClassNotFoundException yes, it does
   * @throws SQLException yes, it does
   */
  @SuppressFBWarnings("DMI_CONSTANT_DB_PASSWORD")
  public void connectToEmployees() throws ClassNotFoundException, SQLException {
    final String url = "jdbc:mysql://localhost:3306/giba_employees";
    Class.forName("com.mysql.cj.jdbc.Driver");
    connection = DriverManager.getConnection(url, user, pass);
  }

  /**
   * The getConnection method returns the connection to the database to the calling function.
   *
   * @return connection
   */
  public Connection getConnection() {
    return connection;
  }
}
