package giba.model;

import giba.globals.GlobalVariables;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The Employee class handles all related fields corresponding to an employee. Employee's can login
 * into the system, create a new account, and track tasks (completed and unfinished).
 *
 * @author Andrew Cavallaro
 */
public class Employee {

  // First name of the employee //
  private String firstName;

  // Last name of the employee //
  private String lastName;

  // Username of the employee //
  private String username;

  // Password the employee sets //
  private String password;

  // Connection to database //
  ConnectToDatabase connectToDatabase = new ConnectToDatabase();

  // PreparedStatement object //
  PreparedStatement preparedStatement;

  // String to use in SQL queries //
  private String sql;

  /** Default constructor. */
  public Employee() {}

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

    connectToDatabase.connectToEmployees();

    sql = "INSERT INTO employees (first_name, last_name, username, password) VALUES (?, ?, ?, ?)";
    preparedStatement = connectToDatabase.getConnection().prepareStatement(sql);
    preparedStatement.setString(1, this.firstName);
    preparedStatement.setString(2, this.lastName);
    preparedStatement.setString(3, this.username);
    preparedStatement.setString(4, this.password);
    preparedStatement.executeUpdate();
    preparedStatement.close();
    connectToDatabase.getConnection().close();
  }

  /**
   * The getFromDatabase method updates variables to be used in the edit profile screen.
   *
   * @param firstName Employee first name
   * @param lastName Employee last name
   */
  public void getFromDatabase(String firstName, String lastName)
      throws SQLException, ClassNotFoundException {

    this.firstName = firstName;
    this.lastName = lastName;

    connectToDatabase.connectToEmployees();

    sql = "SELECT * FROM giba_employees.employees WHERE first_name = ? AND last_name = ?";

    preparedStatement = connectToDatabase.getConnection().prepareStatement(sql);
    preparedStatement.setString(1, firstName);
    preparedStatement.setString(2, lastName);

    ResultSet resultSet = preparedStatement.executeQuery();

    while (resultSet.next()) {
      if (resultSet.getString("first_name").equals(firstName)
          && resultSet.getString("last_name").equals(lastName)) {
        this.username = resultSet.getString("username");
        this.password = resultSet.getString("password");
      }
    }
  }

  /**
   * The updateDatabase method updates the employee's information in the database.
   *
   * @param firstName employee first name
   * @param lastName employee last name
   * @param username employee username
   * @param password employee password
   * @throws SQLException yes, it does
   * @throws ClassNotFoundException yes, it does
   */
  public void updateDatabase(String firstName, String lastName, String username, String password)
      throws SQLException, ClassNotFoundException {

    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.password = password;

    connectToDatabase.connectToEmployees();

    sql =
        "UPDATE giba_employees.employees SET first_name = ?, last_name = ?, username = ?,"
            + " password = ? WHERE first_name = ? AND last_name = ?";
    preparedStatement = connectToDatabase.getConnection().prepareStatement(sql);
    preparedStatement.setString(1, this.firstName);
    preparedStatement.setString(2, this.lastName);
    preparedStatement.setString(3, this.username);
    preparedStatement.setString(4, this.password);
    preparedStatement.setString(5, GlobalVariables.firstName);
    preparedStatement.setString(6, GlobalVariables.lastName);
    preparedStatement.executeUpdate();

    preparedStatement.close();
    connectToDatabase.getConnection().close();

    GlobalVariables.firstName = this.firstName;
    GlobalVariables.lastName = this.lastName;
  }

  /**
   * Username accessor.
   *
   * @return username
   */
  public String getUsername() {
    return username;
  }

  /**
   * Password accessor.
   *
   * @return password
   */
  public String getPassword() {
    return password;
  }
}
