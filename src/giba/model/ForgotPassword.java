package giba.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The ForgotPassword class returns the user's password if a match is found in the database for the
 * given combination of first name, last name, and username.
 *
 * @author Andrew Cavallaro
 */
public class ForgotPassword {

  // Employee's first name //
  private final String first;

  // Employee's last name //
  private final String last;

  // Employee's username //
  private final String user;

  // Employee's password //
  private String password = "";

  /**
   * The ForgotPassword constructor.
   *
   * @param first employee first name
   * @param last employee last name
   * @param user employee username
   */
  public ForgotPassword(String first, String last, String user) {
    this.first = first;
    this.last = last;
    this.user = user;
  }

  /**
   * The getPassword method searches the database for a matching record with the passed first name,
   * last name, and username. If there is a match, the password for that account will be sent back
   * to the calling method. If no match is found, an empty string will be returned to the user.
   *
   * @return password
   * @throws SQLException yes, it does
   * @throws ClassNotFoundException yes, it does
   */
  public String getPassword() throws SQLException, ClassNotFoundException {
    ConnectToDatabase connectToDatabase = new ConnectToDatabase();
    connectToDatabase.connectToEmployees();

    String sql =
        "SELECT password FROM giba_employees.employees WHERE first_name = '"
            + this.first
            + "' AND last_name = '"
            + this.last
            + "' AND username = '"
            + this.user
            + "'";
    Statement statement = connectToDatabase.getConnection().prepareStatement(sql);

    ResultSet resultSet = statement.executeQuery(sql);

    while (resultSet.next()) {
      this.password = resultSet.getString("password");
    }

    statement.close();
    connectToDatabase.getConnection().close();
    return this.password;
  }
}
