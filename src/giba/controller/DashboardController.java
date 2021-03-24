package giba.controller;

import giba.globals.GlobalVariables;
import giba.model.ConnectToDatabase;
import giba.model.Tasks;
import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * The DashboardController handles all activities on the dashboard including: completing tasks,
 * adding tasks, removing tasks, refreshing the tasks, viewing selected task details, and viewing
 * the history of completed tasks.
 *
 * @author Andrew Cavallaro
 */
public class DashboardController {

  // Table column in the today table view //
  @FXML private TableColumn<Tasks, String> todayColumn;

  // Table column in the upcoming table view //
  @FXML private TableColumn<Tasks, String> upcomingColumn;

  // Table column in the completed table view //
  @FXML private TableColumn<Tasks, String> completedColumn;

  // Table view that will display the tasks to be completed today //
  @FXML private TableView<Tasks> todayTable;

  // Table view that will display the tasks to be completed within the next 7 days //
  @FXML private TableView<Tasks> upcomingTable;

  // Table view that will display the tasks that are completed //
  @FXML private TableView<Tasks> completedTable;

  // Connection to the database //
  private final ConnectToDatabase connectToDatabase = new ConnectToDatabase();

  // String to be used in the SQL query //
  private String sql;

  // Parent object //
  private Parent parent;

  // Stage object //
  private Stage stage;

  // Scene object //
  private Scene scene;

  // Alert object //
  Alert alert;

  // DialogPane object //
  DialogPane dialogPane;

  /**
   * The initialize method is responsible for populating the table views with data from the database
   * with tasks.
   *
   * @throws ClassNotFoundException yes, it does
   * @throws SQLException yes, it does
   */
  public void initialize() throws ClassNotFoundException, SQLException {

    LocalDate now = LocalDate.now();
    Date today = Date.valueOf(now);

    final ObservableList<Tasks> todayTasks = FXCollections.observableArrayList();
    final ObservableList<Tasks> upcomingTasks = FXCollections.observableArrayList();
    final ObservableList<Tasks> completedTasks = FXCollections.observableArrayList();

    todayColumn.setCellValueFactory(new PropertyValueFactory<>("taskName"));
    upcomingColumn.setCellValueFactory(new PropertyValueFactory<>("taskName"));
    completedColumn.setCellValueFactory(new PropertyValueFactory<>("taskName"));

    todayTable.setItems(todayTasks);
    upcomingTable.setItems(upcomingTasks);
    completedTable.setItems(completedTasks);

    connectToDatabase.connectToMaintenance();

    Statement statement = connectToDatabase.getConnection().createStatement();

    sql = "SELECT * FROM maintenance.tasks";

    ResultSet resultSet = statement.executeQuery(sql);
    while (resultSet.next()) {

      long difference = resultSet.getDate("next_date").getTime() - today.getTime();
      long numberOfDays = TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);

      if (resultSet.getDate("completion_date").equals(today)) {
        completedTasks.add(
            new Tasks(
                resultSet.getString("task_name"),
                resultSet.getInt("frequency"),
                resultSet.getString("details"),
                resultSet.getDate("completion_date"),
                resultSet.getDate("next_date")));
      } else if (numberOfDays == 0 || numberOfDays < 0) {
        todayTasks.add(
            new Tasks(
                resultSet.getString("task_name"),
                resultSet.getInt("frequency"),
                resultSet.getString("details"),
                resultSet.getDate("completion_date"),
                resultSet.getDate("next_date")));
      } else if (numberOfDays < 7) {
        upcomingTasks.add(
            new Tasks(
                resultSet.getString("task_name"),
                resultSet.getInt("frequency"),
                resultSet.getString("details"),
                resultSet.getDate("completion_date"),
                resultSet.getDate("next_date")));
      }
    }
    statement.close();
    resultSet.close();
    connectToDatabase.getConnection().close();
  }

  /**
   * The addTaskPressed method brings the user to the add task page to allow them to enter a new
   * task into the program.
   *
   * @param event Mouse clicked on "+"
   * @throws IOException yes, it does
   */
  @FXML
  private void addTaskPressed(MouseEvent event) throws IOException {
    parent =
        FXMLLoader.load(
            Objects.requireNonNull(
                getClass().getClassLoader().getResource("giba/view/add_task.fxml")));
    scene = new Scene(parent);
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
  }

  /**
   * The removeTask method is responsible for removing a task from the database so that it no longer
   * populates on the dashboard. It requires approval before being removed.
   *
   * @throws ClassNotFoundException yes, it does
   * @throws SQLException yes, it does
   */
  @FXML
  private void removeTask() throws ClassNotFoundException, SQLException {
    connectToDatabase.connectToMaintenance();
    PreparedStatement preparedStatement;

    alert = new Alert(Alert.AlertType.CONFIRMATION);
    Optional<ButtonType> result;
    ButtonType button;

    sql = "DELETE FROM maintenance.tasks WHERE task_name = ?";

    if (todayTable.getSelectionModel().getSelectedItem() == null
        && upcomingTable.getSelectionModel().getSelectedItem() == null
        && completedTable.getSelectionModel().getSelectedItem() == null) {
      alert = new Alert(Alert.AlertType.ERROR);
      alert.setContentText("Please make a selection");
      dialogPane = alert.getDialogPane();
      dialogPane
          .getStylesheets()
          .add(getClass().getResource("/giba/resources/styles.css").toExternalForm());
      alert.show();
    } else if (todayTable.getSelectionModel().getSelectedItem() != null) {
      alert.setContentText(
          "Confirm removal of "
              + todayTable.getSelectionModel().getSelectedItem().getTaskName()
              + "?");
      dialogPane = alert.getDialogPane();
      dialogPane
          .getStylesheets()
          .add(getClass().getResource("/giba/resources/styles.css").toExternalForm());
      result = alert.showAndWait();
      button = result.orElse(ButtonType.CANCEL);

      if (button == ButtonType.OK) {
        try {
          preparedStatement = connectToDatabase.getConnection().prepareStatement(sql);
          preparedStatement.setString(
              1, todayTable.getSelectionModel().getSelectedItem().getTaskName());
          preparedStatement.executeUpdate();
          preparedStatement.close();
          initialize();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    } else if (upcomingTable.getSelectionModel().getSelectedItem() != null) {
      alert.setContentText(
          "Confirm removal of "
              + upcomingTable.getSelectionModel().getSelectedItem().getTaskName()
              + "?");
      dialogPane = alert.getDialogPane();
      dialogPane
          .getStylesheets()
          .add(getClass().getResource("/giba/resources/styles.css").toExternalForm());
      result = alert.showAndWait();
      button = result.orElse(ButtonType.CANCEL);

      // If the OK button is selected from the alert //
      if (button == ButtonType.OK) {
        preparedStatement = connectToDatabase.getConnection().prepareStatement(sql);
        preparedStatement.setString(
            1, upcomingTable.getSelectionModel().getSelectedItem().getTaskName());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        initialize();
      }
    } else if (completedTable.getSelectionModel().getSelectedItem().getTaskName() != null) {
      alert.setContentText(
          "Confirm removal of "
              + completedTable.getSelectionModel().getSelectedItem().getTaskName()
              + "?");
      dialogPane = alert.getDialogPane();
      dialogPane
          .getStylesheets()
          .add(getClass().getResource("/giba/resources/styles.css").toExternalForm());
      result = alert.showAndWait();
      button = result.orElse(ButtonType.CANCEL);

      // If the OK button is selected from the alert //
      if (button == ButtonType.OK) {
        preparedStatement = connectToDatabase.getConnection().prepareStatement(sql);
        preparedStatement.setString(
            1, completedTable.getSelectionModel().getSelectedItem().getTaskName());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        initialize();
      }
    }
  }

  /**
   * The refresh method calls the initialize method which re-initializes the dashboard to the latest
   * information from the database.
   *
   * @throws SQLException yes, it does
   * @throws ClassNotFoundException yes, it does
   */
  @FXML
  private void refresh() throws SQLException, ClassNotFoundException {
    initialize();
    alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setContentText("Successfully updated all fields");
    dialogPane = alert.getDialogPane();
    dialogPane
        .getStylesheets()
        .add(getClass().getResource("/giba/resources/styles.css").toExternalForm());
    alert.show();
  }

  /**
   * The signOutPressed method signs the user out of the system and returns them to login page.
   *
   * @param event Mouse clicked on "Sign Out"
   * @throws IOException yes, it does
   */
  @FXML
  private void signOutPressed(ActionEvent event) throws IOException {
    parent =
        FXMLLoader.load(
            Objects.requireNonNull(
                getClass().getClassLoader().getResource("giba/view/login.fxml")));
    scene = new Scene(parent);
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
  }

  /**
   * The completeTask method brings the user to completeTask page where the user can confirm
   * completion of a selected task from the dashboard.
   *
   * @param event Mouse click on "Complete Task"
   * @throws IOException yes, it does
   */
  @FXML
  private void completeTask(ActionEvent event) throws IOException {

    if (todayTable.getSelectionModel().getSelectedItem() == null
        && upcomingTable.getSelectionModel().getSelectedItem() == null
        && completedTable.getSelectionModel().getSelectedItem() == null) {
      alert = new Alert(Alert.AlertType.ERROR);
      alert.setContentText("Please make a selection");
      dialogPane = alert.getDialogPane();
      dialogPane
          .getStylesheets()
          .add(getClass().getResource("/giba/resources/styles.css").toExternalForm());
      alert.show();
    } else if (todayTable.getSelectionModel().getSelectedItem() != null) {
      GlobalVariables.taskName = todayTable.getSelectionModel().getSelectedItem().getTaskName();
      GlobalVariables.numberOfDays =
          todayTable.getSelectionModel().getSelectedItem().getFrequency();
      parent =
          FXMLLoader.load(
              Objects.requireNonNull(
                  getClass().getClassLoader().getResource("giba/view/complete_task.fxml")));
      scene = new Scene(parent);
      stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      stage.setScene(scene);
      stage.show();
    } else if (upcomingTable.getSelectionModel().getSelectedItem() != null) {
      GlobalVariables.taskName = upcomingTable.getSelectionModel().getSelectedItem().getTaskName();
      GlobalVariables.numberOfDays =
          upcomingTable.getSelectionModel().getSelectedItem().getFrequency();
      parent =
          FXMLLoader.load(
              Objects.requireNonNull(
                  getClass().getClassLoader().getResource("giba/view/complete_task.fxml")));
      scene = new Scene(parent);
      stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      stage.setScene(scene);
      stage.show();
    } else if (completedTable.getSelectionModel().getSelectedItem() != null) {
      GlobalVariables.taskName = completedTable.getSelectionModel().getSelectedItem().getTaskName();
      GlobalVariables.numberOfDays =
          completedTable.getSelectionModel().getSelectedItem().getFrequency();
      parent =
          FXMLLoader.load(
              Objects.requireNonNull(
                  getClass().getClassLoader().getResource("giba/view/complete_task.fxml")));
      scene = new Scene(parent);
      stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      stage.setScene(scene);
      stage.show();
    }
  }

  /**
   * The viewTaskDetails method will display to the user the details of the selected task.
   *
   * @param event Mouse click on button "View Details"
   */
  @FXML
  private void viewTaskDetails(ActionEvent event) throws IOException {

    if (todayTable.getSelectionModel().getSelectedItem() != null) {
      GlobalVariables.taskName = todayTable.getSelectionModel().getSelectedItem().getTaskName();
      parent =
          FXMLLoader.load(
              Objects.requireNonNull(
                  getClass().getClassLoader().getResource("giba/view/view_details.fxml")));
      scene = new Scene(parent);
      stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      stage.setScene(scene);
      stage.show();
    } else if (upcomingTable.getSelectionModel().getSelectedItem() != null) {
      GlobalVariables.taskName = upcomingTable.getSelectionModel().getSelectedItem().getTaskName();
      parent =
          FXMLLoader.load(
              Objects.requireNonNull(
                  getClass().getClassLoader().getResource("giba/view/view_details.fxml")));
      scene = new Scene(parent);
      stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      stage.setScene(scene);
      stage.show();
    } else if (completedTable.getSelectionModel().getSelectedItem() != null) {
      GlobalVariables.taskName = completedTable.getSelectionModel().getSelectedItem().getTaskName();
      parent =
          FXMLLoader.load(
              Objects.requireNonNull(
                  getClass().getClassLoader().getResource("giba/view/view_details.fxml")));
      scene = new Scene(parent);
      stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      stage.setScene(scene);
      stage.show();
    } else {
      alert = new Alert(Alert.AlertType.ERROR);
      alert.setContentText("A task must be selected to view its details");
      dialogPane = alert.getDialogPane();
      dialogPane
          .getStylesheets()
          .add(getClass().getResource("/giba/resources/styles.css").toExternalForm());
      alert.show();
    }
  }

  /**
   * The viewHistory method shows the history of completed tasks by reading from a saved file that
   * gets appended to each time a task is marked as complete.
   *
   * @param event Mouse click on "View History"
   * @throws IOException yes, it does
   */
  @FXML
  private void viewHistory(ActionEvent event) throws IOException {
    parent =
        FXMLLoader.load(
            Objects.requireNonNull(
                getClass().getClassLoader().getResource("giba/view/view_history.fxml")));
    scene = new Scene(parent);
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
  }

  /**
   * The clearSelection method clears the upcomingTable and completedTable view selections when
   * clicking on the todayTable.
   */
  @FXML
  private void clearSelection() {
    upcomingTable.getSelectionModel().clearSelection();
    completedTable.getSelectionModel().clearSelection();
  }

  /**
   * The clearSelectionTwo method clears the todayTable and completedTable view selections when
   * clicking on the upcomingTable.
   */
  @FXML
  private void clearSelectionTwo() {
    todayTable.getSelectionModel().clearSelection();
    completedTable.getSelectionModel().clearSelection();
  }

  /**
   * The clearSelection method clears the todayTable and upcomingTable view selections when clicking
   * on the completedTable.
   */
  @FXML
  private void clearSelectionThree() {
    todayTable.getSelectionModel().clearSelection();
    upcomingTable.getSelectionModel().clearSelection();
  }

  /**
   * The editProfile brings the user to the page to edit their profile.
   *
   * @param event Mouse click on "Edit Profile"
   * @throws IOException yes, it does
   */
  @FXML
  private void editProfile(MouseEvent event) throws IOException {
    parent =
        FXMLLoader.load(
            Objects.requireNonNull(
                getClass().getClassLoader().getResource("giba/view/edit_profile.fxml")));
    scene = new Scene(parent);
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
  }

  /**
   * The goToMisc method brings the user to the miscellaneous tasks page.
   *
   * @param event Mouse click on "Misc. Tasks" button
   */
  public void goToMisc(ActionEvent event) throws IOException {
    parent =
        FXMLLoader.load(
            Objects.requireNonNull(
                getClass().getClassLoader().getResource("giba/view/miscellaneous_tasks.fxml")));
    scene = new Scene(parent);
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
  }
}
