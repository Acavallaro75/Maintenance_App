package giba.model;

import giba.globals.GlobalVariables;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The Login class is used to verify the username and password combination entered on the landing
 * screen.
 *
 * @author Andrew Cavallaro
 */
public class Login {

  /**
   * The checkEmployee method verifies the username and password combination the user enters on the
   * login screen.
   *
   * @param username username entered on the login screen
   * @param password password entered on the login screen
   * @return true if username and password combination is valid. Otherwise, return false
   */
  public boolean checkEmployee(String username, String password) {
    try {

      // If username and password are not null //
      if (username != null && password != null) {
        String getCredentials = "SELECT * FROM EMPLOYEES WHERE USERNAME = ?";
        final String url = "jdbc:mysql://localhost:3306/giba_employees";
        final String user = "root";
        final String pass = "password";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, user, pass);
        PreparedStatement preparedStatement = connection.prepareStatement(getCredentials);
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {

          // If username and password match a record in the database, return true //
          if (resultSet.getString("password") != null
              && resultSet.getString("password").equals(password)) {
            GlobalVariables.firstName = resultSet.getString("first_name");
            GlobalVariables.lastName = resultSet.getString("last_name");
            return true;
          }
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
      }
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    return false;
  }
}
