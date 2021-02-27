package giba.controller;

import com.jfoenix.controls.JFXTextArea;
import giba.globals.GlobalVariables;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The ViewHistoryController is responsible for displaying the contents of the
 * maintenance_history.txt file to the JFXTextArea. That file is appended to every time a task is
 * marked as completed.
 *
 * @author Andrew Cavallaro
 */
public class ViewHistoryController {

  // JFXTextArea that will display the contents of the maintenance_history.txt file //
  @FXML private JFXTextArea historyView;

  /**
   * The initialize method populates the JFXTextArea with the contents of the
   * maintenance_history.txt file which is written to each time a task is marked as complete.
   *
   * @throws FileNotFoundException yes, it does
   */
  public void initialize() throws FileNotFoundException {
    Scanner scanner = new Scanner(GlobalVariables.file);

    while (scanner.hasNext()) {
      historyView.appendText(scanner.nextLine() + "\n");
    }
  }

  /**
   * The done method returns the user to the dashboard.
   *
   * @param event Mouse click on "Done" button
   * @throws IOException yes, it does
   */
  @FXML
  private void done(ActionEvent event) throws IOException {
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
