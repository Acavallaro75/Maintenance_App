package giba.controller;

import giba.model.MiscellaneousTasks;
import java.io.IOException;
import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * The MiscellaneousController is responsible for handling miscellaneous tasks.
 *
 * @author Andrew Cavallaro
 */
public class MiscellaneousController {

  // TextArea for an employee to enter details about a miscellaneous task. //
  @FXML private TextArea miscTask;

  // Parent object //
  private Parent parent;

  // Stage object //
  private Stage stage;

  // Scene object //
  private Scene scene;

  /**
   * The submit function submits the miscellaneous task to the file maintenance_history.txt but does
   * not submit it to the database for repeated tasks in the future.
   *
   * @param event Mouse click on "Submit"
   * @throws IOException yes, it does
   */
  @FXML
  private void submit(ActionEvent event) throws IOException {

    Alert alert;
    DialogPane dialogPane;

    if (miscTask.getText().isEmpty()) {
      alert = new Alert(Alert.AlertType.ERROR);
      alert.setContentText("Please enter details about the miscellaneous task.");
      dialogPane = alert.getDialogPane();
      dialogPane
          .getStylesheets()
          .add(getClass().getResource("/giba/resources/styles.css").toExternalForm());
      alert.show();
    } else {

      String details = miscTask.getText();
      MiscellaneousTasks miscellaneousTasks = new MiscellaneousTasks();
      miscellaneousTasks.addToHistory(details);

      alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setContentText("Miscellaneous task successfully recorded.");
      dialogPane = alert.getDialogPane();
      dialogPane
          .getStylesheets()
          .add(getClass().getResource("/giba/resources/styles.css").toExternalForm());
      alert.show();
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
