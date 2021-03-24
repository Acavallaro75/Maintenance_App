package giba.controller;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import giba.globals.GlobalVariables;
import giba.model.Tasks;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * The CompleteTaskController is responsible for the completion of tasks.
 *
 * @author Andrew Cavallaro
 */
public class CompleteTaskController {

  // JFXTextArea for including notes related to the task being completed //
  @FXML private JFXTextArea notes;

  // DatePicker to select the date a task is being completed //
  @FXML private DatePicker dateSelected;

  // Name of employee completing a task //
  @FXML private JFXTextField employeeName;

  // Name of the task being completed //
  @FXML private Label taskName;

  // Parent object //
  private Parent parent;

  // Stage object //
  private Stage stage;

  // Scene object //
  private Scene scene;

  /**
   * The initialize method sets the employeeName field to the current user and sets the DatePicker
   * to today's date.
   */
  public void initialize() {
    LocalDate now = LocalDate.now();
    dateSelected.setValue(now);
    taskName.setText(GlobalVariables.taskName);
    employeeName.setText(GlobalVariables.firstName + " " + GlobalVariables.lastName);
  }

  /**
   * The completeTask method moves the task being completed into the completedTable and updates the
   * values in the database for the selected task.
   *
   * @param event Mouse click on "Complete Task"
   * @throws ClassNotFoundException yes, it does
   * @throws SQLException yes, it does
   * @throws IOException yes, it does
   */
  @FXML
  private void completeTask(ActionEvent event)
      throws ClassNotFoundException, SQLException, IOException {

    // If employee name or date selected are empty or not selected //
    if (employeeName.getText().equals("") || dateSelected.getValue() == null) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setContentText("Please ensure all fields are filled properly");
      DialogPane dialogPane = alert.getDialogPane();
      dialogPane
          .getStylesheets()
          .add(getClass().getResource("/giba/resources/styles.css").toExternalForm());
      alert.show();
    } else {
      LocalDate datePicked = dateSelected.getValue();
      Date day = Date.valueOf(datePicked);
      LocalDate nextDate = datePicked.plusDays(GlobalVariables.numberOfDays);
      Date nextDay = Date.valueOf(nextDate);

      Tasks tasks = new Tasks();
      tasks.updateDatabase(day, nextDay);

      try {
        FileWriter fileWriter = new FileWriter(GlobalVariables.file, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter
            .append(GlobalVariables.firstName)
            .append(" ")
            .append(GlobalVariables.lastName)
            .append(" completed ")
            .append(GlobalVariables.taskName)
            .append(".\nNotes related to this task: ")
            .append(notes.getText())
            .append("\n\n");
        bufferedWriter.close();
        fileWriter.close();
      } catch (IOException e) {
        e.printStackTrace();
      }

      parent =
          FXMLLoader.load(
              Objects.requireNonNull(
                  getClass().getClassLoader().getResource("giba/view/dashboard.fxml")));
      scene = new Scene(parent);
      stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      stage.setScene(scene);
      stage.show();
    }
  }

  /**
   * The back method navigates the user back to the dashboard.
   *
   * @param event Mouse click on "Back"
   * @throws IOException yes, it does
   */
  @FXML
  public void back(ActionEvent event) throws IOException {
    parent =
        FXMLLoader.load(
            Objects.requireNonNull(
                getClass().getClassLoader().getResource("giba/view/dashboard.fxml")));
    scene = new Scene(parent);
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
  }
}
