package giba.model;

import giba.globals.GlobalVariables;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The Details class is used to retrieve the details of a task from the database.
 *
 * @author Andrew Cavallaro
 */
public class Details {

  private String details;
  private Date completionDate;
  private Date nextDate;

  /**
   * The retrieveDetails method retrieves the details of the appropriate task that will be used to
   * display on the viewDetails page.
   *
   * @throws ClassNotFoundException yes,it does
   * @throws SQLException yes, it does
   */
  public void retrieveDetails() throws ClassNotFoundException, SQLException {
    final String url = "jdbc:mysql://localhost:3306/maintenance";
    final String user = "root";
    final String pass = "password";
    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection connection = DriverManager.getConnection(url, user, pass);

    String sql = "SELECT * FROM maintenance.tasks WHERE task_name = ?";
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    preparedStatement.setString(1, GlobalVariables.taskName);
    ResultSet resultSet = preparedStatement.executeQuery();

    while (resultSet.next()) {
      this.details = resultSet.getString("details");
      this.completionDate = resultSet.getDate("completion_date");
      this.nextDate = resultSet.getDate("next_date");
    }
    resultSet.close();
    preparedStatement.close();
    connection.close();
  }

  /**
   * Details getter.
   *
   * @return details
   */
  public String getDetails() {
    return details;
  }

  /**
   * Completion date getter.
   *
   * @return completionDate
   */
  public Date getCompletionDate() {
    return completionDate;
  }

  /**
   * Next date getter.
   *
   * @return nextDate
   */
  public Date getNextDate() {
    return nextDate;
  }
}
