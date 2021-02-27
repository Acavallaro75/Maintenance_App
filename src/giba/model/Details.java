package giba.model;

import giba.globals.GlobalVariables;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The Details class is used to retrieve the details of a task from the database.
 *
 * @author Andrew Cavallaro
 */
public class Details {

  // Details on a selected task //
  private String details;

  // Completion date of a selected task //
  private Date completionDate;

  // Next date for a task to be completed by //
  private Date nextDate;

  /**
   * The retrieveDetails method retrieves the details of the appropriate task that will be used to
   * display on the viewDetails page.
   *
   * @throws ClassNotFoundException yes,it does
   * @throws SQLException yes, it does
   */
  public void retrieveDetails() throws ClassNotFoundException, SQLException {
    ConnectToDatabase connectToDatabase = new ConnectToDatabase();
    connectToDatabase.connectToMaintenance();

    String sql = "SELECT * FROM maintenance.tasks WHERE task_name = ?";
    PreparedStatement preparedStatement = connectToDatabase.getConnection().prepareStatement(sql);
    preparedStatement.setString(1, GlobalVariables.taskName);
    ResultSet resultSet = preparedStatement.executeQuery();

    while (resultSet.next()) {
      this.details = resultSet.getString("details");
      this.completionDate = resultSet.getDate("completion_date");
      this.nextDate = resultSet.getDate("next_date");
    }
    resultSet.close();
    preparedStatement.close();
    connectToDatabase.getConnection().close();
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
