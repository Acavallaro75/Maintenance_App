package giba.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * The Employee class handles all related fields corresponding to an employee. Employee's can login
 * into the system, create a new account, and track tasks (completed and unfinished).
 *
 * @author Andrew Cavallaro
 */
public class Employee {

  // First name of the employee //
  private final String firstName;

  // Last name of the employee //
  private final String lastName;

  // Username of the employee //
  private final String username;

  // Password the employee sets //
  private final String password;

  /**
   * The employee constructor.
   *
   * @param firstName first name of the employee
   * @param lastName last name of the employee
   * @param username username of the employee
   * @param password password the employee sets
   */
  public Employee(String firstName, String lastName, String username, String password) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.password = password;
  }

  /**
   * The addToDatabase method inserts the employee's first name, last name, username, and password
   * into the database. This will be used to verify login information on the landing screen.
   *
   * @throws ClassNotFoundException yes, it does
   * @throws SQLException yes, it does
   */
  public void addToDatabase() throws ClassNotFoundException, SQLException {
    final String url = "jdbc:mysql://localhost:3306/giba_employees";
    final String user = "root";
    final String pass = "password";
    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection connection = DriverManager.getConnection(url, user, pass);
    String sql =
        "INSERT INTO employees (first_name, last_name, username, password) VALUES (?, ?, ?, ?)";
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    preparedStatement.setString(1, this.firstName);
    preparedStatement.setString(2, this.lastName);
    preparedStatement.setString(3, this.username);
    preparedStatement.setString(4, this.password);
    preparedStatement.executeUpdate();
    preparedStatement.close();
    connection.close();
  }
}
