package giba.model;

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

  /**
   * The connectToMaintenance method connects the user to the Maintenance database.
   *
   * @throws ClassNotFoundException yes, it does
   * @throws SQLException yes, it does
   */
  public void connectToMaintenance() throws ClassNotFoundException, SQLException {
    final String url = "jdbc:mysql://localhost:3306/maintenance";
    final String user = "root";
    final String pass = "password";
    Class.forName("com.mysql.cj.jdbc.Driver");
    connection = DriverManager.getConnection(url, user, pass);
  }

  /**
   * The connectToEmployee method connects the user to the GIBA_Employee database.
   *
   * @throws ClassNotFoundException yes, it does
   * @throws SQLException yes, it does
   */
  public void connectToEmployees() throws ClassNotFoundException, SQLException {
    final String url = "jdbc:mysql://localhost:3306/giba_employees";
    final String user = "root";
    final String pass = "password";
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
