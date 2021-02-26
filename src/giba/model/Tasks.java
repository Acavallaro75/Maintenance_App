package giba.model;

import giba.globals.GlobalVariables;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * The Tasks class is responsible for handling the tasks created and displayed on the dashboard.
 *
 * @author Andrew Cavallaro
 */
public class Tasks {

  // Integer that is determined by the option chosen in the frequency combo box //
  private int numberOfDays;

  // Name of the task entered by the user //
  private String taskName;

  // Details pertaining to the task being created //
  private String details;

  // LocalDate object is set to today //
  private LocalDate date;

  // LocalDate object is set to today plus the numberOfDays integer //
  private LocalDate nextDate;

  /** Default constructor. */
  public Tasks() {}

  /**
   * The updateDB method is responsible for updating the database anytime a change occurs. An
   * example of a change is completing a task will change the completion and next date to be
   * completed fields.
   *
   * @param today today's date
   * @param nextDay the day the task is next to be completed
   * @throws ClassNotFoundException yes, it does
   * @throws SQLException yes, it does
   */
  public void updateDatabase(Date today, Date nextDay) throws ClassNotFoundException, SQLException {
    final String url = "jdbc:mysql://localhost:3306/maintenance";
    final String user = "root";
    final String pass = "password";
    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection connection = DriverManager.getConnection(url, user, pass);
    String sql =
        "UPDATE maintenance.tasks SET completion_date = ?, next_date = ? WHERE task_name = ?";
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    preparedStatement.setDate(1, today);
    preparedStatement.setDate(2, nextDay);
    preparedStatement.setString(3, GlobalVariables.taskName);
    preparedStatement.executeUpdate();
    preparedStatement.close();
    connection.close();
  }

  /**
   * The tasks constructor that creates a new Task object. It transforms the combo box selection
   * string into an integer and initializes the nextDate object.
   *
   * @param taskName Name of the task being created
   * @param frequency Frequency the task needs to be completed at
   * @param details Details pertaining to the task being created
   * @param date Today's date
   */
  public Tasks(String taskName, String frequency, String details, LocalDate date) {
    this.taskName = taskName;
    this.details = details;
    this.date = date;

    switch (frequency) {
      case "Daily":
        numberOfDays = 1;
        break;
      case "Weekly":
        numberOfDays = 7;
        break;
      case "Bi-Weekly":
        numberOfDays = 14;
        break;
      case "Monthly":
        numberOfDays = 30;
        break;
      case "Quarterly":
        numberOfDays = 90;
        break;
      case "Semi-Annually":
        numberOfDays = 180;
        break;
      case "Annually":
        numberOfDays = 365;
        break;
      default:
        numberOfDays = 5;
    }

    this.nextDate = LocalDate.now().plusDays(numberOfDays);
  }

  /**
   * Overloaded constructor.
   *
   * @param taskName name of the task
   * @param frequency frequency of the task but in integer format
   * @param details details of the task
   * @param completionDate last completion date of the task
   * @param nextDate next date the task needs to be completed by
   */
  public Tasks(String taskName, int frequency, String details, Date completionDate, Date nextDate) {
    this.taskName = taskName;
    this.numberOfDays = frequency;
    this.details = details;
    this.date = Date.valueOf(String.valueOf(completionDate)).toLocalDate();
    this.nextDate = Date.valueOf(String.valueOf(nextDate)).toLocalDate();
  }

  /**
   * Task name getter.
   *
   * @return taskName
   */
  public String getTaskName() {
    return taskName;
  }

  /**
   * Number of days (frequency) getter.
   *
   * @return numberOfDays (frequency int format)
   */
  public int getFrequency() {
    return this.numberOfDays;
  }

  /**
   * The addToDatabase method adds the task and it's fields to the database and will be shown on the
   * dashboard landing screen.
   *
   * @throws ClassNotFoundException yes, it does
   * @throws SQLException yes, it does
   */
  public void addToDatabase() throws ClassNotFoundException, SQLException {

    final Date day = Date.valueOf(date);
    final Date nextDay = Date.valueOf(nextDate);

    final String url = "jdbc:mysql://localhost:3306/maintenance";
    final String user = "root";
    final String pass = "password";
    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection connection = DriverManager.getConnection(url, user, pass);
    String sql =
        "INSERT INTO maintenance.tasks (task_name, frequency, details, completion_date, next_date) "
            + "VALUES (?, ?, ?, ?, ?)";
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    preparedStatement.setString(1, this.taskName);
    preparedStatement.setInt(2, this.numberOfDays);
    preparedStatement.setString(3, this.details);
    preparedStatement.setDate(4, day);
    preparedStatement.setDate(5, nextDay);
    preparedStatement.executeUpdate();
    preparedStatement.close();
    connection.close();
  }
}
