package giba.model;

import giba.globals.GlobalVariables;

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
  @edu.umd.cs.findbugs.annotations.SuppressFBWarnings({
    "ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD",
    "ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD"
  })
  public boolean checkEmployee(String username, String password) {
    try {
      if (username != null && password != null) {
        String sql = "SELECT * FROM EMPLOYEES WHERE USERNAME = ?";

        ConnectToDatabase connectToDatabase = new ConnectToDatabase();
        connectToDatabase.connectToEmployees();

        PreparedStatement preparedStatement =
            connectToDatabase.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
          if (resultSet.getString("password") != null
              && resultSet.getString("password").equals(password)) {
            GlobalVariables.firstName = resultSet.getString("first_name");
            GlobalVariables.lastName = resultSet.getString("last_name");
            resultSet.close();
            preparedStatement.close();
            connectToDatabase.getConnection().close();
            return true;
          }
        }
        resultSet.close();
        preparedStatement.close();
        connectToDatabase.getConnection().close();
      }
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    return false;
  }
}
