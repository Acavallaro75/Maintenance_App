package giba.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import giba.model.Tasks;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * The AddTaskController is responsible for adding tasks to the database that will be shown on the
 * dashboard.
 *
 * @author Andrew Cavallaro
 */
public class AddTaskController {

  // Text field for the name of the task to be created //
  @FXML private JFXTextField taskName;

  /* Combo box to select the frequency of tasks to be completed from daily, weekly, bi-weekly,
   * monthly, quarterly, half-year, and annually
   */
  @FXML private JFXComboBox<String> frequency;

  // Text area to put important details related to the created task //
  @FXML private JFXTextArea details;

  /** The initialize method populates the combo box with options to select from. */
  public void initialize() {

    // Array of words used to populate the frequency combo box //
    String[] frequencies = {
      "Daily", "Weekly", "Bi-Weekly", "Monthly", "Quarterly", "Semi-Annually", "Annually"
    };
    List<String> words = new ArrayList<>(Arrays.asList(frequencies));
    ObservableList<String> wordList = FXCollections.observableList(words);
    frequency.getItems().clear();
    frequency.setItems(wordList);
  }

  /**
   * The addToTaskSchedule method adds the created task to the database and will be displayed on the
   * dashboard screen.
   *
   * @param event Mouse click on "Add"
   * @throws IOException yes, it does
   * @throws SQLException yes, it does
   * @throws ClassNotFoundException yes, it does
   */
  @FXML
  public void addTaskToSchedule(ActionEvent event)
      throws IOException, SQLException, ClassNotFoundException {
    String task = taskName.getText();
    String schedule = frequency.getSelectionModel().getSelectedItem();
    String information = details.getText();
    LocalDate today = LocalDate.now();

    // If task name, schedule date, or details field are empty //
    if (task.equals("") || schedule.equals("") || information.equals("")) {
      Alert error = new Alert(Alert.AlertType.ERROR);
      error.setContentText("Please ensure all fields are filled correctly");
      error.show();

      // If all fields are filled correctly //
    } else {
      Tasks tasks = new Tasks(task, schedule, information, today);
      tasks.addToDatabase();

      Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
      confirm.setContentText(task + " has been successfully created");
      confirm.show();

      Parent parent =
          FXMLLoader.load(
              Objects.requireNonNull(
                  getClass().getClassLoader().getResource("giba/view/dashboard.fxml")));
      Scene scene = new Scene(parent);
      Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      stage.setScene(scene);
      stage.show();
    }
  }

  /**
   * The goBack method brings the user back to the dashboard.
   *
   * @param event Mouse click on "Back"
   * @throws IOException yes, it does
   */
  @FXML
  public void goBack(ActionEvent event) throws IOException {
    Parent parent =
        FXMLLoader.load(
            Objects.requireNonNull(
                getClass().getClassLoader().getResource("giba/view/dashboard.fxml")));
    Scene scene = new Scene(parent);
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
  }
}
